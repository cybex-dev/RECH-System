package controllers.ApplicationSystem;

import DAO.ApplicationSystem.EntityComponent;
import DAO.ApplicationSystem.EntityEthicsApplication;
import DAO.UserSystem.EntityPerson;
import helpers.JDBCExecutor;
import models.ApplicationSystem.EthicsApplication;
import models.ApplicationSystem.EthicsApplication.ApplicationType;
import net.ddns.cyberstudios.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Entity;
import org.xml.sax.SAXException;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.*;
import scala.App;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ApplicationHandler extends Controller {

    static private Set<EthicsApplication> ethicsApplications = new HashSet<>();

    @Inject
    FormFactory formFactory;

    @Inject
    private JDBCExecutor jdbcExecutor;

    @Inject
    public ApplicationHandler(JDBCExecutor jdbcExecutor) {
        this.jdbcExecutor = jdbcExecutor;
    }

    // Uses XML document to create form, based in as Scala Squence type and processed in view
    public ApplicationHandler() {

    }

    //TODO complete all applications
    public Result allApplications() {
        EntityPerson person = EntityPerson.getPersonById(session().get("user_email"));
        List<EntityEthicsApplication> applicationsByPerson = EntityEthicsApplication.findApplicationsByPerson(person);
        return ok(views.html.ApplicationSystem.AllApplications.render(" :: Applications", applicationsByPerson));
    }

    public Result newApplication(String type){
        //TODO
        loadApplicationFromResource();
        ApplicationType applicationType = ApplicationType.valueOf(type);
        EthicsApplication application = ethicsApplications.stream()
                .filter(ethicsApplication -> ethicsApplication.getType() == applicationType)
                .findFirst()
                .orElse(new EthicsApplication());
        DynamicForm form = formFactory.form();
        return ok(views.html.ApplicationSystem.ApplicationContainer.render(" :: New Application", type.toString(), application.getRootElement(), form));
    }

    /**
     * Reads in application forms from resource root and passes them to be parsed and inserted into available application forms.
     */
    private void loadApplicationFromResource(){
        URL resource = getClass().getResource("/application_templates/rec-h.xml");
        try {
            URI rech_uri = resource.toURI();
            File rech_file = new File(rech_uri);
            parseToDocument(rech_file);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Receives a file object pointing to an XML application form file and adds the parsed document to a static container
     * @param applicationXML
     */
    private void parseToDocument(File applicationXML) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(applicationXML);
            doc.normalize();
            String sType = doc.getDocumentElement().getAttribute("type");
            ApplicationType type = ApplicationType.valueOf(sType);
            EthicsApplication ethicsApplication = new EthicsApplication(type, doc);
            ethicsApplication.parseDocumentToElement();
            ethicsApplications.add(ethicsApplication);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Post application form to server
     */
    //TODO
    public Result submitApplication() {
        DynamicForm newApplication = formFactory.form().bindFromRequest();
        if (newApplication.hasErrors()) {
            try {
                String application_type = newApplication.get("application_type");
                ApplicationType type = ApplicationType.valueOf(application_type);
                EthicsApplication application = ethicsApplications.stream()
                        .filter(ethicsApplication -> ethicsApplication.getType() == type)
                        .findFirst()
                        .orElse(new EthicsApplication());
                DynamicForm form = formFactory.form();
                return badRequest(views.html.ApplicationSystem.ApplicationContainer.render(" :: New Application", type.toString(), application.getRootElement(), form));
            } catch (Exception x) {
                return internalServerError();
            }
        }

        Map<String, Object> data = newApplication.get().getData();
        data.forEach((key, value) -> {
            EntityComponent component = new EntityComponent();
            component.s
        });

        return ok();
    }
}
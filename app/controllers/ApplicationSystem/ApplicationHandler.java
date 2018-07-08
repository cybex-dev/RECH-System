package controllers.ApplicationSystem;

import models.ApplicationSystem.EthicsApplication;
import models.ApplicationSystem.EthicsApplication.ApplicationType;
import net.ddns.cyberstudios.Element;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.*;

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
import java.util.Set;

public class ApplicationHandler extends Controller {

    static private Set<EthicsApplication> ethicsApplications = new HashSet<>();

    @Inject
    HttpExecutionContext executionContext;

    @Inject
    FormFactory formFactory;


    // Uses XML document to create form, based in as Scala Squence type and processed in view
    public ApplicationHandler() {

    }

    public Result newApplication(){
        //TODO
        loadApplicationFromResource();
        ApplicationType type = ApplicationType.valueOf("human");
        EthicsApplication application = ethicsApplications.stream().filter(ethicsApplication -> ethicsApplication.getType() == type).findFirst().orElse(new EthicsApplication());
        Element rootElement = application.getRootElement();
        DynamicForm form = formFactory.form();
        return ok(views.html.ApplicationSystem.ApplicationContainer.render(" :: New Application", type.toString(), rootElement, form));
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
        return ok();
    }
}

package controllers.ApplicationSystem;

import akka.stream.javadsl.FileIO;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.*;

import javax.inject.Inject;
import javax.print.Doc;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class ApplicationHandler extends Controller {

    static private Set<ApplicationForm> applicationFormSet = new HashSet<>();

    @Inject
    HttpExecutionContext executionContext;

    @Inject
    FormFactory formFactory;


    // Uses XML document to create form, based in as Scala Squence type and processed in view
    public ApplicationHandler() {
        loadApplicationFromResource();
    }

    public Result createApplicationForm(String type){
        switch (type.toLowerCase()) {
            case "human" : {
                return null;
            }
            case "animal" : {
                return null;
            }
            default:
                return null;
        }
    }

    /**
     * Reads in application forms from resource root and passes them to be parsed and inserted into available application forms.
     */
    private void loadApplicationFromResource(){
        try {
            URI rech_uri = ApplicationHandler.class.getResource("/application_templates/rec-h.xml").toURI();
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
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(applicationXML);
            String sType = document.getDocumentElement().getAttribute("type");
            ApplicationForm.ApplicationType type = ApplicationForm.ApplicationType.valueOf(sType);
            applicationFormSet.add(new ApplicationForm(type, document));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}

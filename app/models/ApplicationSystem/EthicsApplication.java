package models.ApplicationSystem;

import net.ddns.cyberstudios.Element;
import net.ddns.cyberstudios.XMLTools;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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

public class EthicsApplication {

    static private Set<EthicsApplication> ethicsApplications = new HashSet<>();

    private ApplicationType type;
    private Document applicationDocumentDOM;
    private Element root;

    public enum ApplicationType {
        Human, Animal
    }

    public EthicsApplication() {
        loadApplications();
    }

    private void loadApplications() {

    }

    public static EthicsApplication lookupApplication(ApplicationType type) {
        return ethicsApplications.stream()
                .filter(ethicsApplication -> ethicsApplication.getType() == type)
                .findFirst()
                .orElse(null);
    }

    public static Element lookupElement(ApplicationType type, String id) {
        EthicsApplication ethicsApplication = lookupApplication(type);
        return XMLTools.lookup(ethicsApplication.getRootElement(), id);
    }

    public EthicsApplication(ApplicationType type, Document applicationDocument) {
        this.type = type;
        this.applicationDocumentDOM = applicationDocument;
    }

    public ApplicationType getType() {
        return type;
    }

    public void setType(ApplicationType type) {
        this.type = type;
    }

    public Document getApplicationDocument() {
        return applicationDocumentDOM;
    }

    public void setApplicationDocument(Document applicationDocument) {
        this.applicationDocumentDOM = applicationDocument;
    }

    public void parseDocumentToElement() {
        root = XMLTools.parseDocument(applicationDocumentDOM);
    }

    public Element getRootElement() {
        return root;
    }

    /**
     * Reads in application forms from resource root and passes them to be parsed and inserted into available application forms.
     */
    private void loadApplicationFromResource() {
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
     *
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

    public EthicsApplication cloneWithElements() {
        EthicsApplication application = new EthicsApplication(type, null);
        application.root = root.clone(null);
        return application;
    }
}

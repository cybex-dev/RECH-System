package models.ApplicationSystem;

import org.w3c.dom.Document;
import net.ddns.cyberstudios.Element;
import net.ddns.cyberstudios.XMLTools;

public class EthicsApplication {

    private ApplicationType type;
    private Document applicationDocumentDOM;
    private Element root;

    public enum ApplicationType{
        human,
        animal;
    }

    public EthicsApplication() {
    }

    public EthicsApplication(ApplicationType type, Document applicationDocumentDOM) {
        this.type = type;
        this.applicationDocumentDOM = applicationDocumentDOM;
    }

    public ApplicationType getType() {
        return type;
    }

    public void setType(ApplicationType type) {
        this.type = type;
    }

    public Document getApplicationDocumentDOM() {
        return applicationDocumentDOM;
    }

    public void parseDocumentToElement(){
        root = XMLTools.parseDocument(applicationDocumentDOM);
    }

    public Element getRootElement() {
        return root;
    }

    public void setApplicationDocumentDOM(Document applicationDocumentDOM) {
        this.applicationDocumentDOM = applicationDocumentDOM;
    }
}

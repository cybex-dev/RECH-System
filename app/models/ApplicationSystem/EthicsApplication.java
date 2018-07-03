package models.ApplicationSystem;

import org.w3c.dom.Document;

public class EthicsApplication {

    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public enum ApplicationType{
        Human,
        Animal
    }

    public EthicsApplication() {
    }

    public EthicsApplication(ApplicationType type, Document applicationDocument) {
        this.type = type;
        this.applicationDocument = applicationDocument;
    }

    private ApplicationType type;
    private Document applicationDocument;

    public ApplicationType getType() {
        return type;
    }

    public void setType(ApplicationType type) {
        this.type = type;
    }

    public Document getApplicationDocument() {
        return applicationDocument;
    }

    public void setApplicationDocument(Document applicationDocument) {
        this.applicationDocument = applicationDocument;
    }
}

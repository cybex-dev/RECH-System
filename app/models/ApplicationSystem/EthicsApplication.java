package models.ApplicationSystem;

import org.w3c.dom.Document;

public class EthicsApplication {

    public enum ApplicationType{
        human,
        animal;
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

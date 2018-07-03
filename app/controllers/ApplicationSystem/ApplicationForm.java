package controllers.ApplicationSystem;

import org.w3c.dom.Document;

public class ApplicationForm {

    public enum ApplicationType{
        Human,
        Animal
    }

    public ApplicationForm(ApplicationType type, Document applicationDocument) {
        this.type = type;
        this.applicationDocument = applicationDocument;
    }

    private ApplicationType type;
    private Document applicationDocument;
}

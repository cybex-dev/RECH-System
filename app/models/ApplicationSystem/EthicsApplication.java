package models.ApplicationSystem;

import helpers.FileScanner;
import net.ddns.cyberstudios.Element;
import net.ddns.cyberstudios.XMLTools;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class EthicsApplication {

    static private Set<EthicsApplication> ethicsApplications = new HashSet<>();

    private ApplicationType type;
    private Document applicationDocumentDOM;
    private Element root;
    private List<Question> questionList;

    public enum ApplicationType {
        Human, Animal;

        public static ApplicationType parse(String type) {
            switch (type.toLowerCase()) {

                case "animal": {
                    return Human;
                }

                case "human":
                default: {
                    return Human;
                }
            }
        }
    }

    public EthicsApplication() {
        loadApplications();
        loadQuestionnaires();
    }

    /**
     * Reads and parses eqch questionnaire from conf/application_templates/ directory
     */
    private static void loadApplications() {
        List<File> applications;
        try {
            applications = new FileScanner().getResourceFiles("/application_templates/");
            applications.forEach(file -> createApplication(EthicsApplication.parseToDocument(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads and parses eqch questionnaire from conf/filters/ directory
     */
    private static void loadQuestionnaires() {
        List<File> questions;
        try {
            questions = new FileScanner().getResourceFiles("/filters/");
            questions.forEach(file -> createQuestionnaire(EthicsApplication.parseToDocument(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an {@link EthicsApplication} object from a {@link Document} and adds it to the local static set of ethics applications
     * @param doc
     */
    private static void createApplication(Document doc){
        if (doc == null)
            return;
        String sType = doc.getDocumentElement().getAttribute("type");
        ApplicationType type = ApplicationType.parse(sType);
        EthicsApplication ethicsApplication = new EthicsApplication(type, doc);
        ethicsApplication.parseDocumentToElement();
        ethicsApplications.add(ethicsApplication);
    }


    /**
     * Creates a questionnaire {@link Element} from a {@link Document}
     * @param doc
     */
    private static void createQuestionnaire(Document doc){
        if (doc == null)
            return;
        String sType = doc.getDocumentElement().getAttribute("type");
        ApplicationType type = ApplicationType.parse(sType);
        EthicsApplication ethicsApplication = lookupApplication(type);
        if (ethicsApplication == null) {
            System.out.printf("EthicsApplication resource not found:\nNo EthicsApplication found for questionnaire for [%s]\n", type.name());
            return;
        }

        ethicsApplication.setQuestionList(createQuestionList(XMLTools.parseDocument(doc)));
    }

    /**
     * Reads a manageable Element generated from an XML Filter file.
     *
     * Parses the element into {@link Question} and {@link Condition} objects
     * @param element
     * @return
     */
    private static List<Question> createQuestionList(Element element) {
        List<Question> questions = new ArrayList<>();
        LinkedList<Element> question_filters = element.getChildren();

        question_filters.forEach(q -> {
            Question question = new Question();
            question.setTitle(q.getAttributes().get("title"));
            q.getChildren().forEach(cond -> {
                Condition.Level level = Condition.Level.valueOf(cond.getAttributes().get("level"));
                Condition.Risk risk = Condition.Risk.valueOf(cond.getAttributes().get("risk"));
                Condition condition = new Condition(cond.getValue().toString(), risk, level);
                question.getConditionsList().add(condition);
            });
            questions.add(question);
        });

        return questions;
    }

    /**
     * Searches the local static set of {@link EthicsApplication}s and attempts to find the {@link ApplicationType} given
     * @param type
     * @return
     */
    public static EthicsApplication lookupApplication(ApplicationType type) {
        if (ethicsApplications.isEmpty())
            loadApplications();
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
     * Receives a file object pointing to an XML application form file and adds the parsed document to a static container
     *
     * @param applicationXML
     */
    private static Document parseToDocument(File applicationXML) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(applicationXML);
            doc.normalize();
            return doc;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public EthicsApplication cloneWithElements() {
        EthicsApplication application = new EthicsApplication(type, null);
        application.root = root.clone(null);
        return application;
    }


    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}

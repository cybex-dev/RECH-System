package models.ApplicationSystem;

import dao.ApplicationSystem.EntityComponentVersion;
import dao.ApplicationSystem.EntityEthicsApplication;
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
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class EthicsApplication implements Serializable {

    static private Set<EthicsApplication> ethicsApplications = new HashSet<>();

    private ApplicationType type;
    private Document applicationDocumentDOM;
    private Element root;
    private List<Question> questionList;

    public enum ApplicationType {
        Human, Animal;

        public static ApplicationType parse(String type) {
            switch (type.toLowerCase()) {

                case "a":
                case "animal": {
                    return Animal;
                }

                case "h":
                case "human":
                default: {
                    return Human;
                }
            }
        }
    }

    public EthicsApplication() {
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
            questions.forEach(file -> {
                Document document = EthicsApplication.parseToDocument(file);
                createQuestionnaire(document);
            });
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
        EthicsApplication ethicsApplication = ethicsApplications.stream()
                .filter(e -> e.getType() == type)
                .findFirst()
                .orElse(null);
        if (ethicsApplication == null) {
            System.out.printf("EthicsApplication resource not found:\nNo EthicsApplication found for questionnaire for [%s]\n", type.name());
            return;
        }

        List<Question> questionList = createQuestionList(XMLTools.parseDocument(doc));
        ethicsApplication.setQuestionList(questionList);
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
                Condition.Level level = Condition.Level.valueOf(cond.getAttributes().get("level").toLowerCase());
                Condition.Risk risk = Condition.Risk.valueOf(cond.getAttributes().get("risk").toLowerCase());
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
        if (ethicsApplications.isEmpty()) {
            loadApplications();
            loadQuestionnaires();
        }
        EthicsApplication ethicsApplication1 = ethicsApplications.stream()
                .filter(ethicsApplication -> ethicsApplication.getType() == type)
                .findFirst()
                .orElse(null);
        return (ethicsApplication1 != null) ? ethicsApplication1.cloneWithElements() : null;
    }

    public static Element lookupElement(ApplicationType type, String id) {
        EthicsApplication ethicsApplication = lookupApplication(type);
        return XMLTools.lookup(ethicsApplication.getRootElement(), id);
    }

    public EthicsApplication(EthicsApplication.ApplicationType type, Document applicationDocument) {
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

    @SuppressWarnings("unchecked")
    /**
     * Receives an element root and a map of values. These values are added back into the root element's appropriate location determinved by the XMLTools lookup function
     *
     * Supresses the cast of Object -> ArrayList[Object]
     */
    public static Element addValuesToRootElement(Element root, Map<String, Object> values){
        values.forEach((key, value) -> {
            System.out.println();
            boolean keyPartOfList = (key.matches("^\\w+[_]\\d+$"));
            String cleanedKey = (keyPartOfList) ? key.substring(0, key.lastIndexOf("_")) : key;

            Element child = XMLTools.lookup(root, cleanedKey);
            if (child == null){
                System.out.println("Key [ " + cleanedKey + " ] with value [ " + value + " ] ignored");
            } else {
                System.out.println("Key [ " + cleanedKey + " ] Found! Value [ " + value + " ], processing...");
                // Get child.value element
                Element valueElement = child.getChildren().getLast();

                // Check if ID suggests it is part of a list
                boolean isList = child.getParent().getTag().equals("list");

                System.out.println("List detected, need to add to list");

                // Check if key being part of a list matches parent being a list element
                if (keyPartOfList && isList){
                    System.out.println("Will be added to list");

                    System.out.println("Determining index: \t\t\tRAW " + key.substring(key.lastIndexOf("_") + 1));
                    String i = key.substring(key.lastIndexOf("_") + 1);
                    int index = Integer.parseInt(i) - 1;
                    System.out.println("Determining index: \t\t\tComputed (in array) " + index);

                    // Check if a list exists, if not then create it
                    if (!(valueElement.getValue() instanceof ArrayList)){
                        valueElement.setValue(new ArrayList<>());
                        System.out.println("No list found, creating...");
                    }

                    // Add list value
                    if (valueElement.getValue() instanceof ArrayList){
                        ArrayList<Object> list = (ArrayList<Object>) valueElement.getValue();
                        if (list.size() <= index){
                            List<Object> objects = Arrays.asList(Arrays.copyOf(list.toArray(), index+1));
                            valueElement.setValue(new ArrayList<Object>(objects));
                            list = (ArrayList<Object>) valueElement.getValue();
                        }
                        Object[] objects = list.toArray();
                        objects[index] = value;
                        valueElement.setValue(new ArrayList<>(Arrays.asList(objects)));
                        System.out.println("Added to List [ " + cleanedKey + " ] , Item KEY: [" + key + "] Value [ " + value + " ] INDEX [ " + index + " ] Done");
                    }
                } else {
                    if (!keyPartOfList && !isList){
                        // Add the value normally
                        valueElement.setValue(value);
                        System.out.println("Adding single item to [ " + cleanedKey + " ] Value [ " + value + " ] Done");
                    } else {
                        System.out.println("Ignored value, not added to root element");
                    }
                }
            }
        });
        return root;
    }

    public List<Question> getQuestionList() {
        if (questionList == null) {
            loadQuestionnaires();
        }
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    /**
     * Takes in an ethics application entity, gets the latest components from the database and constructs an element with its children from these values.
     * @param application
     * @return
     */
    public static Element PopulateRootElement(EntityEthicsApplication application) {
        System.out.println("[Populating Root Element]");

        // Get application type
        EthicsApplication ethicsApplication = EthicsApplication.lookupApplication(application.type());
        System.out.println("Found application type");

        System.out.println("Finding latest components...");
        // Get all latest components of an application
        List<EntityComponentVersion> latestComponents = EntityEthicsApplication.getLatestComponents(application.applicationPrimaryKey());
        System.out.println("Done");

        // Create map to store these in. This map should be identical to the map of data sent in the POST request when creating / editing an application form
        Map<String, Object> entryMap = new HashMap<>();

        System.out.println("Processing components");
        // Add each entity component version to the map of data, with its associated value
        latestComponents.forEach(entityComponentVersion -> {

            // Safety check
            if (entityComponentVersion != null) {
                System.out.println(entityComponentVersion.getComponentId() + " -> " + entityComponentVersion.getResponseType());

                // Get value dependant on the component type, and add to map
                switch (entityComponentVersion.getResponseType()) {
                    case "boolean": {
                        entryMap.put(entityComponentVersion.getComponentId(), entityComponentVersion.getBoolValue());
                        break;
                    }

                    case "date":
                    case "number":
                    case "long_text":
                    case "text": {
                        entryMap.put(entityComponentVersion.getComponentId(), entityComponentVersion.getTextValue());
                        break;
                    }

                    case "document": {
                        entryMap.put(entityComponentVersion.getComponentId() + "_title", entityComponentVersion.getDocumentName());
                        entryMap.put(entityComponentVersion.getComponentId() + "_desc", entityComponentVersion.getDocumentDescription());
                        try{
                            File file = new File(entityComponentVersion.getDocumentLocationHash());
                            if (file.exists()) {
                                String s = file.getName();
                                entryMap.put(entityComponentVersion.getComponentId() + "_document", s);
                            }
                        } catch (NullPointerException e) {
                            System.out.println("File not found for [" + application.applicationPrimaryKey().shortName() + "] - component [" + entityComponentVersion.getComponentId() + "]");
                        }
                        break;
                    }
                }

                System.out.println("Processed: " + entityComponentVersion.toString());
            }

        });

        System.out.println("Building root element");
        Element element = EthicsApplication.addValuesToRootElement(ethicsApplication.getRootElement(), entryMap);

//        System.out.println("\t=== Starting Data Integrity Test ===\t");
//        latestComponents.forEach(entityComponentVersion -> {
//            if (entityComponentVersion != null) {
//                System.out.println(entityComponentVersion.getComponentId() + " -> " + entityComponentVersion.getResponseType());
//
//                // Get value dependant on the component type, and add to map
//                switch (entityComponentVersion.getResponseType()) {
//                    case "boolean": {
//                        String componentKey = entityComponentVersion.getComponentId();
//                        Boolean componentValue = entityComponentVersion.getBoolValue();
//
//                        Element lookup = XMLTools.lookup(element, componentKey.matches("^\\w+[_]\\d+$") ? componentKey.substring(0, componentKey.lastIndexOf("_")) : componentKey);
//
//                        Boolean elementValue = null;
//                        Object o = lookup.getChildren().getLast().getValue();
//                        if (o instanceof ArrayList) {
//                            break;
////                            String i = componentKey.substring(componentKey.lastIndexOf("_") + 1);
////                            int index = Integer.parseInt(i);
////                            elementValue = (Boolean) ((ArrayList) o).get(index);
////                            System.out.println("Arraylist: Using index " + index + " with value: " + elementValue);
//                        } else {
//                            elementValue = (Boolean) o;
//                        }
//
//                        if (elementValue.equals(componentValue)){
//                            System.out.println("Comparing Boolean [" + componentKey + "] : Match!");
//                        } else {
//                            System.out.println("Comparing Boolean [" + componentKey + "] : " + componentValue + " \t\t!=\t\t " + elementValue);
//                        }
//                        break;
//                    }
//
//                    case "text": {
//                        String originalKey = entityComponentVersion.getComponentId();
//
//                        String componentValue = entityComponentVersion.getTextValue();
//
//                        Element lookup = XMLTools.lookup(element, originalKey.matches("^\\w+[_]\\d+$") ? originalKey.substring(0, originalKey.lastIndexOf("_")) : originalKey);
//
//                        String elementValue = "";
//                        Object o = lookup.getChildren().getLast().getValue();
//                        if (o instanceof ArrayList) {
//                            break;
////                            String i = originalKey.substring(originalKey.lastIndexOf("_") + 1);
////                            int index = Integer.parseInt(i);
////                            elementValue = (String) ((ArrayList) o).get(index);
////                            System.out.println("Arraylist: Using index " + index + " with value: " + elementValue);
//                        } else {
//                            elementValue = (String) o;
//                        }
//
//                        if (elementValue.equals(componentValue)){
//                            System.out.println("Comparing String [" + originalKey + "] : Match!");
//                        } else {
//                            System.out.println("Comparing String [" + originalKey + "] : " + componentValue + " \t\t!=\t\t " + elementValue);
//                        }
//
//                        break;
//                    }
//
//                    case "document": {
//
//                        String componentKey = entityComponentVersion.getComponentId()  + "_title";
//                        String componentValue = entityComponentVersion.getDocumentName();
//
//                        Element lookup = XMLTools.lookup(element, componentKey);
//                        String elementValue = (String) lookup.getChildren().getLast().getValue();
//
//                        if (elementValue.equals(componentValue)){
//                            System.out.println("Comparing Document Title [" + componentKey + "] : Match!");
//                        } else {
//                            System.out.println("Comparing Document Title [" + componentKey + "] : " + componentValue + " \t\t!=\t\t " + elementValue);
//                        }
//
//                        componentKey = entityComponentVersion.getComponentId()  + "_desc";
//                        componentValue = entityComponentVersion.getDocumentDescription();
//
//                        lookup = XMLTools.lookup(element, componentKey);
//                        elementValue = (String) lookup.getChildren().getLast().getValue();
//
//                        if (elementValue.equals(componentValue)){
//                            System.out.println("Comparing Document Description [" + componentKey + "] : Match!");
//                        } else {
//                            System.out.println("Comparing Document Description [" + componentKey + "] : " + componentValue + " \t\t!=\t\t " + elementValue);
//                        }
//
//                        componentKey = entityComponentVersion.getComponentId()  + "_document";
//                        componentValue = entityComponentVersion.getDocumentLocationHash();
//
//                        lookup = XMLTools.lookup(element, componentKey);
//                        elementValue = (String) lookup.getChildren().getLast().getValue();
//
//                        if (elementValue.equals(componentValue)){
//                            System.out.println("Comparing Document Location [" + componentKey + "] : Match!");
//                        } else {
////                            System.out.println("Comparing Document Location [" + componentKey + "] : " + componentValue + " \t\t!=\t\t " + elementValue);
//                        }
//
//
//                        break;
//                    }
//                }
//            }
//        });

        return element;
    }

    public static List<Question> GetQuestionList(ApplicationType type){
        EthicsApplication e = ethicsApplications.stream()
                .filter(ethicsApplication -> ethicsApplication.getType() == type)
                .findFirst()
                .orElse(null);
        return (e != null) ? e.questionList : new ArrayList<>();
    }

    public static List<String> getAllApplications(){
        if (ethicsApplications.size() == 0){
            loadApplications();
            loadQuestionnaires();
        }
        return ethicsApplications.stream().map(ethicsApplication -> {
            String name = ethicsApplication.getType().name();
            return name.toUpperCase().charAt(0) + name.substring(1);
        }).collect(Collectors.toList());
    }
}

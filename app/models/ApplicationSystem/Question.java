package models.ApplicationSystem;

import java.util.ArrayList;
import java.util.List;

public class Question {
    List<Condition> conditionsList = new ArrayList<>();
    String title = "";

    public Question() {
    }

    public Question(List<Condition> conditionsList, String title) {
        this.conditionsList = conditionsList;
        this.title = title;
    }

    public List<Condition> getConditionsList() {
        return conditionsList;
    }

    public void setConditionsList(List<Condition> conditionsList) {
        this.conditionsList = conditionsList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

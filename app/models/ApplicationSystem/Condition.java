package models.ApplicationSystem;

public class Condition {

    public String getConditionText() {
        return conditionText;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public enum Level {
        none,
        faculty,
        committee
    }

    public enum Risk {
        none, low, medium, high
    }

    private Level level;
    private Risk risk;
    private boolean value;
    private String conditionText;

    public Condition() {
    }

    public Condition(String text, Condition.Risk risk, Condition.Level level) {
        this.level = level;
        this.risk = risk;
        this.conditionText = text;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }
}

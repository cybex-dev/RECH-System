package models.ApplicationSystem;

public class Condition {

    public enum Level {
        None,
        Faculty,
        RECCommittee
    }

    public enum Risk {
        None, Low, Medium, High
    }

    private Level level;
    private Risk risk;
    private boolean value;

    public Condition() {
    }

    public Condition(String text, Risk risk, Level level) {
        this.level = level;
        this.risk = risk;
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

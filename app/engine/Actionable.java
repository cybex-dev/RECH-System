package engine;

public interface Actionable {

    /**
     * .Defines & describes the actions to be performed by this actionable interface
     */
    void doAction();

    /**
     * Defines and describes the notification events required for this actionable interface
     */
    void doNotify();
}

package mad.nil.taskmanager;

/**
 * Created by nilan on 2/4/2018.
 */

public enum Priority {
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low");

    private String value;

    private Priority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Priority getPriorityByValue(String value) {
        Priority[] priorityValues = Priority.values();
        Priority selectedPriority = null;

        for(Priority priority : priorityValues) {
            if(priority.getValue().equals(value)) {
                selectedPriority = priority;
            }
        }

        return selectedPriority;
    }

}

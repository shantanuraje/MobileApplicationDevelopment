package mad.nil.taskmanager;

/**
 * Created by nilan on 2/4/2018.
 */

public enum /**/RequestCodes {

    ADD_CODE(0),
    EDIT_CODE(1),

    TASK("task"),
    INDEX("index"),
    REQUEST_CODE("request_code");

    private Object value;

    private RequestCodes(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}

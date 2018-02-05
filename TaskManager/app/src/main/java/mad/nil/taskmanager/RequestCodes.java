package mad.nil.taskmanager;

/**
 * Created by nilan on 2/4/2018.
 */

public enum /**/RequestCodes {


    CANCEL_CODE(0),
    ADD_CODE(1),
    EDIT_CODE(2),

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

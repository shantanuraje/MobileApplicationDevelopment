package mad.nil.taskmanager;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nilan on 2/4/2018.
 */

public class Task implements Serializable {
    private String title;
    private Date date;
    private Date time;
    private Priority priority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}

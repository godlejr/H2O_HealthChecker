package healthcare.demand.H2O_Admin;

/**
 * Created by ㅇㅇ on 2016-12-27.
 */

public class HA_message_item {
    String name, id, date, time,flag,  content;

    public HA_message_item() {
    }

    public HA_message_item(String name, String id, String date, String time, String content, String flag) {
        this.flag = flag;
        this.name = name;
        this.id = id;
        this.date = date;
        this.time = time;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}

package healthcare.demand.H2O_Admin;

/**
 * Created by ㅇㅇ on 2016-12-27.
 */

public class HA_monitor_item {
    String num, id, name, anb, stress, cnt;

    public HA_monitor_item() {}

    public HA_monitor_item(String num, String id, String name, String anb, String stress, String cnt) {
        this.num = num;
        this.id = id;
        this.name = name;
        this.anb = anb;
        this.stress = stress;
        this.cnt = cnt;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnb() {
        return anb;
    }

    public void setAnb(String anb) {
        this.anb = anb;
    }

    public String getStress() {
        return stress;
    }

    public void setStress(String stress) {
        this.stress = stress;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }
}

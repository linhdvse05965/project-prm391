package fu.prm391.sxample.project_android;

import java.io.Serializable;

public class Calendar implements Serializable {
    private String nameEvent;
    public Calendar() {
    }
    public Calendar(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    @Override
    public String toString() {
        return nameEvent;
    }
}

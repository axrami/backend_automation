package model;

/**
 * Created by andrew on 7/21/15.
 */
public class Visitor {
    String visitorId;

    public String getVisitorId() {
        if (visitorId == null ) {
            return "1234567";
        }
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }
}

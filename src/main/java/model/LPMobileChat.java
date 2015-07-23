package model;

import java.util.List;

/**
 * Created by andrew on 7/23/15.
 */
public class LPMobileChat {
    String engagement_id;
    int messageSent = 0;
    int messageReceived = 0;
    int lastEventId;
    String sseURL;
    boolean connected = false;

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getEngagement_id() {
        return engagement_id;
    }

    public void setEngagement_id(String engagement_id) {
        this.engagement_id = engagement_id;
    }

    public int getLastEventId() {
        return lastEventId;
    }

    public void setLastEventId(int lastEventId) {
        this.lastEventId = lastEventId;
    }

    public int getMessageReceived() {
        return messageReceived;
    }

    public void addMessageReceived(int messageReceived) {
        this.messageReceived = messageReceived += 1;
    }

    public int getMessageSent() {
        return messageSent;
    }

    public void addMessageSent(int messageSent) {
        this.messageSent = messageSent += 1;
    }

    public String getSseURL() {
        return sseURL;
    }

    public void setSseURL(String sseURL) {
        this.sseURL = sseURL;
    }
}

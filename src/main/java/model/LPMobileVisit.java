package model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by andrew on 6/8/15.
 */
public class LPMobileVisit {
    boolean callDeflectionReported = false;
    String visitId;
    String continueURL;
    int messagesReceivced = 0;
    int messgaesSent = 0;
    int numberOfConintues = 0;
    int nextInterval;

    ConcurrentHashMap<String, ConcurrentHashMap<String, Skill>> skills = new ConcurrentHashMap<String, ConcurrentHashMap<String, Skill>>();

    public void addMessage() {
        this.messagesReceivced += 1;
    }

    public void addMessageSent() {
        this.messgaesSent += 1;
    }

    public int getMessagesReceivced() {
        return messagesReceivced;
    }

    public int getMessgaesSent() {
        return messgaesSent;
    }

    public int getNumberOfConintues() {
        return numberOfConintues;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getContinueURL() {
        return continueURL;
    }

    public void setContinueURL(String continueURL) {
        this.continueURL = continueURL;
    }

    public int getNextInterval() {
        return nextInterval;
    }

    public void setNextInterval(int nextInterval) {
        this.nextInterval = nextInterval;
    }

    public long getNextIntervalInMilliSeconds() {
        return getNextInterval() * 1000;
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, Skill>> getSkills() {
        return skills;
    }

    public void setSkills(ConcurrentHashMap<String, ConcurrentHashMap<String, Skill>> skills) {
        this.skills = skills;
    }

    public void forgetSkills() {
        this.skills = new ConcurrentHashMap<String, ConcurrentHashMap<String, Skill>>();
    }

    public Map<String, Skill> getDefaultSkill() {
        if (skills != null) {
            return skills.get(Skill.DEFAULT_ACCOUNT_NAME);
        }
        return null;
    }

    @Override
    public String toString() {
        return "model.LPMobileVisit{" +
                ", visitID='" + visitId + '\'' +
                ", continueURL='" + continueURL + '\'' +
                ", nextInterval=" + nextInterval +
                ", skills=" + skills +
                '}';
    }
}

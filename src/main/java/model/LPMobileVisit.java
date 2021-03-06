package model;

import json.model.VisitIntroResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andrew on 6/8/15.
 * represents the visit populated by json parser with server response
 */

public class LPMobileVisit {
    boolean callDeflectionReported = false;
    VisitIntroResponse visitIntroResponse;
    String visitorId;
    String visitId;
    String continueURL;
    String chatBaseURL;
    int nextInterval;
    String branding_mp5;
    String response;
    String ssoKey;
    String branding;
    List responses = new ArrayList<LPMobileHttpResponse>();
    HashMap<String, HashMap<String, Skill>> skills = new HashMap<String, HashMap<String, Skill>>();

    public VisitIntroResponse getVisitIntroResponse() {
        return visitIntroResponse;
    }

    public String getBranding() {
        return branding;
    }

    public void setBranding(String branding) {
        this.branding = branding;
    }

    public void setVisitIntroResponse(VisitIntroResponse visitIntroResponse) {
        this.visitIntroResponse = visitIntroResponse;
    }

    public void addResponse(LPMobileHttpResponse response) {
        if (responses.isEmpty()) {
            responses = new ArrayList<LPMobileHttpResponse>();
        }
        responses.add(response);
    }

    public List getResponses() {
        return responses;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public void setResponse(String resposne) {
        this.response = resposne;
    }

    public String getResponse() {
        return response;
    }

    public String getBranding_mp5() {
        return branding_mp5;
    }

    public void setBranding_mp5(String branding_mp5) {
        this.branding_mp5 = branding_mp5;
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

    public HashMap<String, HashMap<String, Skill>> getSkills() {
        return skills;
    }

    public void setSkills(HashMap<String, HashMap<String, Skill>> skills) {
        this.skills = skills;
    }

    public void forgetSkills() {
        this.skills = new HashMap<String, HashMap<String, Skill>>();
    }

    public String getChatBaseURL() {
        return chatBaseURL;
    }

    public void setChatBaseURL(String chatBaseURL) {
        this.chatBaseURL = chatBaseURL;
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

package json.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by andrew on 9/1/15.
 */

@XmlRootElement
public class ChatIntro {
    private String app_id;
    private String platform;
    private String skill;
    private String visit_id;
    private String site_id;
    private String language;

    public String getApp_id() {
        return app_id;
    }

    public ChatIntro setApp_id(String app_id) {
        this.app_id = app_id;
        return this;
    }

    public String getPlatform() {
        return platform;
    }

    public ChatIntro setPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    public String getSkill() {
        return skill;
    }

    public ChatIntro setSkill(String skill) {
        this.skill = skill;
        return this;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public ChatIntro setVisit_id(String visit_id) {
        this.visit_id = visit_id;
        return this;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

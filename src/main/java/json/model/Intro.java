package json.model;

import model.Skill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andrew on 7/30/15.
 */

@XmlRootElement
public class Intro {
    String visit_id;
    String button_text;
    String button_text_color;
    String button_tint;
    String continue_url;
    List <String> supported_languages;
    String visit_url;
    String mask_cc;
    String welcome_text;
    String voice_enabled;
//    List<String> skills;
    Map<String, Map<String, Skill>> skills;

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public String getButton_text() {
        return button_text;
    }

    public void setButton_text(String button_text) {
        this.button_text = button_text;
    }

    public String getButton_text_color() {
        return button_text_color;
    }

    public void setButton_text_color(String button_text_color) {
        this.button_text_color = button_text_color;
    }

    public String getButton_tint() {
        return button_tint;
    }

    public void setButton_tint(String button_tint) {
        this.button_tint = button_tint;
    }

    public String getContinue_url() {
        return continue_url;
    }

    public void setContinue_url(String continue_url) {
        this.continue_url = continue_url;
    }

    public List<String> getSupported_languages() {
        return supported_languages;
    }

    public void setSupported_languages(List<String> supported_languages) {
        this.supported_languages = supported_languages;
    }

    public String getMask_cc() {
        return mask_cc;
    }

    public void setMask_cc(String mask_cc) {
        this.mask_cc = mask_cc;
    }

    public String getVisit_url() {
        return visit_url;
    }

    public void setVisit_url(String visit_url) {
        this.visit_url = visit_url;
    }

    public String getVoice_enabled() {
        return voice_enabled;
    }

    public void setVoice_enabled(String voice_enabled) {
        this.voice_enabled = voice_enabled;
    }

    public String getWelcome_text() {
        return welcome_text;
    }

    public void setWelcome_text(String welcome_text) {
        this.welcome_text = welcome_text;
    }
}

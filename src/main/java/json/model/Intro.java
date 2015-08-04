package json.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by andrew on 7/30/15.
 */

@XmlRootElement
public class Intro {
    String visit_id;
    String button_text;
    String button_text_color;
    String button_tint;
//    Object button;
    int button_visibility;
//    Object default_availability;
    int enabled;
//    Object skills;
    int voice_enabled;
    String welcome_text;
    String default_language;
    List <String> supported_languages;
    boolean surveys_enabled;
    boolean hide_email_chat;
    int next_interval;
    String continue_url;
    int show_exit_survey_condition;
    String mask_cc;
    String visit_url;

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

    public String getWelcome_text() {
        return welcome_text;
    }

    public void setWelcome_text(String welcome_text) {
        this.welcome_text = welcome_text;
    }

    public boolean isSurveys_enabled() {
        return surveys_enabled;
    }

    public void setSurveys_enabled(boolean surveys_enabled) {
        this.surveys_enabled = surveys_enabled;
    }

    public boolean isHide_email_chat() {
        return hide_email_chat;
    }

    public void setHide_email_chat(boolean hide_email_chat) {
        this.hide_email_chat = hide_email_chat;
    }

    public int getNext_interval() {
        return next_interval;
    }

    public void setNext_interval(int next_interval) {
        this.next_interval = next_interval;
    }

    public int getShow_exit_survey_condition() {
        return show_exit_survey_condition;
    }

    public void setShow_exit_survey_condition(int show_exit_survey_condition) {
        this.show_exit_survey_condition = show_exit_survey_condition;
    }

    public int getButton_visibility() {
        return button_visibility;
    }

    public void setButton_visibility(int button_visibility) {
        this.button_visibility = button_visibility;
    }

    public String getDefault_language() {
        return default_language;
    }

    public void setDefault_language(String default_language) {
        this.default_language = default_language;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getVoice_enabled() {
        return voice_enabled;
    }

    public void setVoice_enabled(int voice_enabled) {
        this.voice_enabled = voice_enabled;
    }
}

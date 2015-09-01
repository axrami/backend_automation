package json.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by andrew on 8/21/15.
 * Used to create visit post request
 */

@XmlRootElement
public class AppSettings {
    private String version;
    private String type;
    private String ip_address;
    private String app_id;
    private String platform;
    private String sdk_version;
    private String platform_version;
    private String device_id;
    private String alternate_device_id;
    private String limit_ad_tracking;
    private String device_type;
    private String locale;
    private String language;
    private String strings_hash;
    private String view_name;
    private String skill;
//    private String custom_variables;
    private String site_id;
    private String property_id;
    private String branding_md5;
    private boolean request_visitor_id;
    private String visitor_id;
//    private String proto_response;
//    private String capabilities;
    private String host;
    private String visit_id;
    // clients sending extra stuffs
    private boolean app_foregrounded;
    private String connection_type;
    private String platform_release;

    public String getPlateform_release() {
        return platform_release;
    }

    public AppSettings setPlateform_release(String plateform_release) {
        this.platform_release = plateform_release; return this;
    }

    public String getPlatform_version() {
        return platform_version;
    }

    public void setPlatform_version(String platform_version) {
        this.platform_version = platform_version;
    }

    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
    }

    public String getApp_id() {
        return app_id;
    }

    public AppSettings setApp_id(String app_id) {
        this.app_id = app_id;
        return this;
    }

    public String getBranding_md5() {
        return branding_md5;
    }

    public AppSettings setBranding_md5(String branding_md5) {
        this.branding_md5 = branding_md5;
        return this;
    }

    public String getDevice_id() {
        return device_id;
    }

    public AppSettings setDevice_id(String device_id) {
        this.device_id = device_id;
        return this;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getLanguage() {
        return language;
    }

    public AppSettings setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getPlatform() {
        return platform;
    }

    public AppSettings setPlatform(String platform) {
        this.platform = platform;
        return this;
    }

    public String getPlatform_release() {
        return platform_release;
    }

    public void setPlatform_release(String platform_release) {
        this.platform_release = platform_release;
    }

    public String getStrings_hash() {
        return strings_hash;
    }

    public void setStrings_hash(String strings_hash) {
        this.strings_hash = strings_hash;
    }

    public boolean isRequest_visitor_id() {
        return request_visitor_id;
    }

    public AppSettings setRequest_visitor_id(boolean request_visitor_id) {
        this.request_visitor_id = request_visitor_id;
        return this;
    }

    public boolean isApp_foregrounded() {
        return app_foregrounded;
    }

    public void setApp_foregrounded(boolean app_foregrounded) {
        this.app_foregrounded = app_foregrounded;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getConnection_type() {
        return connection_type;
    }

    public void setConnection_type(String connection_type) {
        this.connection_type = connection_type;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getAlternate_device_id() {
        return alternate_device_id;
    }

    public void setAlternate_device_id(String alternate_device_id) {
        this.alternate_device_id = alternate_device_id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getLimit_ad_tracking() {
        return limit_ad_tracking;
    }

    public void setLimit_ad_tracking(String limit_ad_tracking) {
        this.limit_ad_tracking = limit_ad_tracking;
    }

    public String getProperty_id() {
        return property_id;
    }

    public void setProperty_id(String property_id) {
        this.property_id = property_id;
    }

    public String getVisitor_id() {
        return visitor_id;
    }

    public void setVisitor_id(String visitor_id) {
        this.visitor_id = visitor_id;
    }

    public String getView_name() {
        return view_name;
    }

    public void setView_name(String view_name) {
        this.view_name = view_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }
}

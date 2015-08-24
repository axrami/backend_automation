package json.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by andrew on 8/21/15.
 * Used to create visit post request
 */

@XmlRootElement
public class AppSettings {
    private String sdk_version;
    private String platform_version;
    private String platform_release;
    private String device_id;
    private String device_type;
    private String app_id;
    private String platform;
    private String language;
    private String strings_hash;
    private String branding_md5;

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

    public void setBranding_md5(String branding_md5) {
        this.branding_md5 = branding_md5;
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
}

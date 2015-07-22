package model;

/**
 * Created by andrew on 6/11/15.
 * Environment used to create postbody
 */

public class LPMobileEnvironment {
    String locale;
    String connectionType;
    Boolean push;
    Boolean jailBroken;
    Boolean locationServices;
    String appBundleVersion;
    String tzOffset;
    String distributionType;
    String longitude;
    String latitude;
    String deviceID;
    String appID;
    String platform;
    String skillId;
    String localizedHashStr;
    String brandingHashStr;
    String currentAccount;
    String currentSkill;
    String language = "en";

    private boolean isAccessibilityActive = false;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSdkVersion() {
        return "0.0.1";
    }

    public String getPlatformVersion() {
        return "21";
    }

    public String getAppBundleVersion() {
        return appBundleVersion;
    }

    public void setAppBundleVersion(String appBundleVersion) {
        this.appBundleVersion = appBundleVersion;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getBrandingHashStr() {
        return brandingHashStr;
    }

    public void setBrandingHashStr(String brandingHashStr) {
        this.brandingHashStr = brandingHashStr;
    }

    public String getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(String currentAccount) {
        this.currentAccount = currentAccount;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getCurrentSkill() {
        return currentSkill;
    }

    public void setCurrentSkill(String currentSkill) {
        this.currentSkill = currentSkill;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceType() {
        return "unknown Android SDK built for x86";
    }

    public String getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(String distributionType) {
        this.distributionType = distributionType;
    }

    public Boolean getJailBroken() {
        return jailBroken;
    }

    public void setJailBroken(Boolean jailBroken) {
        this.jailBroken = jailBroken;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocalizedHashStr() {
        return localizedHashStr;
    }

    public void setLocalizedHashStr(String localizedHashStr) {
        this.localizedHashStr = localizedHashStr;
    }

    public Boolean getLocationServices() {
        return locationServices;
    }

    public void setLocationServices(Boolean locationServices) {
        this.locationServices = locationServices;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Boolean getPush() {
        return push;
    }

    public boolean isAccessibilityActive() {
        return isAccessibilityActive;
    }

    public void setAccessibilityActive(boolean isAccessibilityActive) {
        this.isAccessibilityActive = isAccessibilityActive;
    }

    public void setPush(Boolean push) {
        this.push = push;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getTzOffset() {
        return tzOffset;
    }

    public void setTzOffset(String tzOffset) {
        this.tzOffset = tzOffset;
    }

    @Override
    public String toString() {
        return "LPMobileEnvironment{" +
                "sdkVersion='" + getSdkVersion() + '\'' +
                ", platformVersion='" + getPlatformVersion() + '\'' +
                ", locale='" + locale + '\'' +
                ", connectionType='" + connectionType + '\'' +
                ", push=" + push +
                ", jailBroken=" + jailBroken +
                ", locationServices=" + locationServices +
                ", appBundleVersion='" + appBundleVersion + '\'' +
                ", tzOffset='" + tzOffset + '\'' +
                ", distributionType='" + distributionType + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", deviceID='" + deviceID + '\'' +
                ", deviceType='" + getDeviceType() + '\'' +
                ", appID='" + appID + '\'' +
                ", platform='" + platform + '\'' +
                ", skillId='" + skillId + '\'' +
                ", isAccessibilityActive='" + isAccessibilityActive + '\'' +
                ", localizedHashStr='" + localizedHashStr + '\'' +
                ", brandingHashStr='" + brandingHashStr + '\'' +
                '}';
    }

}

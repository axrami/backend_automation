package json;


import model.LPMobileEnvironment;
import org.json.simple.JSONObject;

/**
 * Created by andrew on 6/3/15.
 */
public class JsonGenerator {

    public static String generateVisitRequest(LPMobileEnvironment env, String visitId, String visitorId, String ssoKey) {
        JSONObject obj = new JSONObject();

        obj.put("sdk_version", env.getSdkVersion());
        obj.put("platform_version" , env.getPlatformVersion());

        JSONObject extras = new JSONObject();
        obj.put("extras" , extras);

        JSONObject detected = new JSONObject();

        if (env.getLocale() != null )
            detected.put("locale" , env.getLocale());

        if (env.getConnectionType() != null)
            detected.put("connection_type" , env.getConnectionType());

        if (env.getPush() != null)
            detected.put("push" , env.getPush());

        if (env.getJailBroken() != null)
            detected.put("jailbroken" , env.getJailBroken());

        if (env.getLocationServices() != null)
            detected.put("location_services" , env.getLocationServices());

        if (env.getTzOffset() != null)
            detected.put("tz_offset", env.getTzOffset());

        if (env.getDistributionType() != null)
            detected.put("distribution_type", env.getDistributionType());

        if (env.getLongitude() != null && env.getLatitude() != null) {
            JSONObject latlong = new JSONObject();
            latlong.put("longitude", env.getLongitude());
            latlong.put("latitude", env.getLatitude());
            detected.put("location", latlong);
        }

        if (env.isAccessibilityActive()){
            detected.put("voiceover_enabled", true);
        }

        extras.put("detected_settings", detected);

        if (env.getDeviceID() != null)
            obj.put("device_id", env.getDeviceID());

        if (env.getDeviceType() != null)
            obj.put("device_type", env.getDeviceType());

        if (env.getAppID() != null)
            obj.put("app_id", env.getAppID());

        if (env.getPlatform() != null)
            obj.put("platform", env.getPlatform());

        //Need to be added only for the continue request
        if (visitId != null){
            obj.put("visit_id", visitId);
        }

        if (env.getCurrentSkill() != null) {
            obj.put("skill", env.getCurrentSkill());
            obj.put("site_id", env.getCurrentAccount());
        }

        obj.put("language" , env.getLanguage());
        obj.put("strings_hash", env.getLocalizedHashStr());
        obj.put("branding_md5", env.getBrandingHashStr());
        obj.put("platform_release","5.0.2");

        return obj.toString();

    }

    public static String generateIntoJson(String app_id, String platform, String visit_id, String skill) {
        JSONObject obj = new JSONObject();
        obj.put("app_id" , app_id);
        obj.put("platform" , platform);
        obj.put("skill" , skill);
        obj.put("visit_id" , visit_id);

        return obj.toString();
    }
}

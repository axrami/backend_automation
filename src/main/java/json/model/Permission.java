package json.model;

/**
 * Created by andrew on 8/19/15.
 */
public class Permission {
    String permission;
    String asset;

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}

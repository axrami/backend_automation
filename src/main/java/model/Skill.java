package model;

/**
 * Created by andrew on 6/8/15.
 */
public class Skill {
    static public String DEFAULT_ACCOUNT_NAME = "defaultAccount";

    boolean isDefault = false;
    boolean isEnabled = false;
    boolean isStateChanged = true;

    public Skill(boolean isDefault, boolean isEnabled) {
        this.isDefault = isDefault;
        this.isEnabled = isEnabled;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public void setEnabled(boolean enabled) {
        isStateChanged = true;
        isEnabled = enabled;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public boolean isStateChanged() {
        return isStateChanged;
    }

    public void setStateChanged(boolean isStateChanged) {
        this.isStateChanged = isStateChanged;
    }

    @Override
    public String toString() {
        return "model.Skill{" +
                "isDefault=" + isDefault +
                ", isEnabled=" + isEnabled +
                ", isStateChanged=" + isStateChanged +
                '}';
    }
}

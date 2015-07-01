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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Skill)) return false;

        Skill skill = (Skill) obj;

        if (isDefault != skill.isDefault) return false;
        if (isEnabled !=skill.isEnabled) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (isDefault ? 1 : 0);
        result = 31 * result + (isEnabled ? 1 : 0);
        return result;
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

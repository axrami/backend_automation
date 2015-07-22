package model;


/**
 * Created by andrew on 7/21/15.
 */
public class Instance {
    private Visitor visitor;
    private LPMobileVisit visit;
    private LPMobileEnvironment env;
    private Skill skill;
    private LPMobileHttpResponse[] responses;

    public LPMobileEnvironment getEnv() {
        return env;
    }

    public void setEnv(LPMobileEnvironment env) {
        this.env = env;
    }

    public LPMobileHttpResponse[] getResponses() {
        return responses;
    }

    public void setResponses(LPMobileHttpResponse[] responses) {
        this.responses = responses;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public LPMobileVisit getVisit() {
        return visit;
    }

    public void setVisit(LPMobileVisit visit) {
        this.visit = visit;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
}

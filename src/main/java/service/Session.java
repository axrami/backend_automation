package service;


import model.*;
import networking.ContinueRequestHandler;
import networking.VisitHandler;

/**
 * Created by andrew on 7/21/15.
 */
public class Session {
    private Visitor visitor = new Visitor();
    private LPMobileVisit visit;
    private LPMobileEnvironment env;

    public void setEnv(LPMobileEnvironment env) {
        this.env = env;
    }

    public void beginVisit() {
        if (env == null) {
            env = SetEnvironment.createBaseEnv();
        }
        VisitHandler visitHandler = new VisitHandler();
        visitHandler.launch(env , visitor);
        this.visit = visitHandler.getVisit();
    }


    public void continuedVisit() {
        if (this.visit == null) {
            beginVisit();
        }
        ContinueRequestHandler c = new ContinueRequestHandler();
        c.launchContinue(env, visit, visitor);

    }

    public void beginChat() {
        if (this.visit == null ) {
            beginVisit();
        }

    }


}

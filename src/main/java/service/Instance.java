package service;


import model.*;
import service.VisitHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 7/21/15.
 */
public class Instance {
    private Visitor visitor = new Visitor();
    private LPMobileVisit visit;
    private Skill skill;
    private List responses = new ArrayList<LPMobileHttpResponse>();
    private List continueModel = new ArrayList<LPMobileContinue>();
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
        System.out.println(visit);
    }


    public void continuedVisit(int numofContinues) {
        if (this.visit == null) {
            beginVisit();
        }


    }

    public void beginChat() {

    }

    public void beginChat(LPMobileEnvironment env,Visitor visitor, LPMobileVisit visit) {

    }



}

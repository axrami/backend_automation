package service;

import model.LPMobileHttpResponse;
import networking.VisitHandler;
import service.chat.ChatHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by andrew on 9/21/15.
 */
public class VisitStart implements Callable {
    private Session session;
    private GenConfig genConfig;
    private List<LPMobileHttpResponse> result = new ArrayList<>();


    @Override
    public LPMobileHttpResponse call() {
        Session session = new Session();
        session.setConfig("staging", 1, false);
        VisitHandler visit = session.beginVisit();
        result.add(visit.response);
        ChatHandler chat = session.beginChat();
        return visit.response;
    }

}

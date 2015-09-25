package service;

import model.LPMobileHttpResponse;
import networking.VisitHandler;
import service.chat.ChatHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by andrew on 9/21/15.
 */
public class ChatRun implements Callable {
    private Session session;
    private GenConfig genConfig;
    private List<LPMobileHttpResponse> result = new ArrayList<>();

    public ChatRun() {}

    public ChatRun(Session session) {
        this.session = session;
    }

    public ChatRun(Session session, GenConfig genConfig) {
        this.session = session;
        this.genConfig = genConfig;
    }

    @Override
    public LPMobileHttpResponse call() {
        Session session = new Session();
        session.setConfig("staging", 1, false);
        VisitHandler visit = session.beginVisit();
        result.add(visit.response);
        ChatHandler chat = session.beginChat();

        return visit.response;
    }

    Runnable task = () -> {
        VisitHandler visit = session.beginVisit();
        ChatHandler chat = session.beginChat();
        while(genConfig.getEndTime() > System.currentTimeMillis()) {
            try {
                chat.sendLinePostRequest("Hello World");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    };

}

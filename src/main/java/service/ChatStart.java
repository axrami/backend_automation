package service;

import model.LPMobileHttpResponse;
import networking.VisitHandler;
import service.chat.ChatHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by andrew on 9/25/15.
 */
public class ChatStart implements Callable {
    private List<LPMobileHttpResponse> responseList = new ArrayList<>();

    @Override
    public List<LPMobileHttpResponse> call() {
        Session session = new Session();
        VisitHandler visit = session.beginVisit();
        responseList.add(visit.response);
        ChatHandler chat = session.beginChat();
        return responseList;
    }
}

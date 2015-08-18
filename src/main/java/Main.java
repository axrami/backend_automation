import org.apache.http.HttpResponse;
import service.Session;
import service.chat.ChatHandler;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by andrew on 6/16/15.
 */
public class Main {

    public static void main(String args[]) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            try {
                Session session = new Session();
                session.somthing();
                session.beginVisit();
                ChatHandler chat = session.beginChat();
                chat.sendLinePostRequest();
                chat.sendLinePostRequest();
                chat.sendLinePostRequest();
                chat.sendOutroPostRequest(session.getVisit(), null);
                ChatHandler chat2 = session.beginChat();
                chat2.sendLinePostRequest();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        int intDelay = 1;
        int period = 1000;
        executorService.scheduleAtFixedRate(task, intDelay, period, TimeUnit.MILLISECONDS);

        ExecutorService executerService = new ExecutorService();
        executerService.beginCounter();
    }

}

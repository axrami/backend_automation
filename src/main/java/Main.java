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
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(20);
        Runnable task = () -> {
            Session session = new Session();
            session.beginVisit();
        };
        executorService.scheduleAtFixedRate(task, 0 , 100 , TimeUnit.MILLISECONDS);

    }

}


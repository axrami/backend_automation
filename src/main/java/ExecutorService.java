import model.LPMobileVisit;
import service.Session;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by andrew on 7/28/15.
 */
public class ExecutorService {

    public void beginCounter() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(80);
        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(80);

        Runnable task = () -> {
            Session session = new Session();
            session.beginVisit();
        };

        int intDelay = 0;
        int period = 1;
        executor.scheduleAtFixedRate(task, intDelay, period, TimeUnit.MILLISECONDS);
        executor2.scheduleAtFixedRate(task, intDelay, period, TimeUnit.MILLISECONDS);
    }

}

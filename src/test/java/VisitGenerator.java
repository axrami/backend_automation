import model.LPMobileEnvironment;
import model.SetEnvironment;
import org.junit.Test;
import service.Session;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by andrew on 7/28/15.
 */
public class VisitGenerator {

    @Test
    public void oneVisitPerSecond() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            Session session = new Session();
            session.beginVisit();
        };
        int delay = 0;
        int period = 1000;
        executor.scheduleAtFixedRate(task , delay, period, TimeUnit.MILLISECONDS);
    }

}

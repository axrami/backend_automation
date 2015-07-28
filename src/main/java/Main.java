import service.Session;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by andrew on 6/16/15.
 */
public class Main {

    public static void main(String args[]) {
        ExecutorService e = new ExecutorService();
        e.beginCounter();

    }

}

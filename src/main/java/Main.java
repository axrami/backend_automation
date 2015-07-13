import model.LPMobileEnvironment;
import model.LPMobileLogger;
import model.SetEnvironment;
import networking.VisitRequestHandler;
import org.joda.time.Interval;
import org.joda.time.ReadableDuration;
import service.Visit;

/**
 * Created by andrew on 6/16/15.
 */
public class Main implements Runnable {

    // 10 threads x 100 runs = 1000 visits

    public void run() {
        for (int i = 0; i < 1; i++) {
            Visit visit = new Visit();
            visit.launch();
        }
    }

    public static void main(String args[]) {
        (new Thread(new Main())).start();
    }
}

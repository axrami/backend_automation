import model.LPMobileEnvironment;
import model.LPMobileLogger;
import model.SetEnvironment;
import networking.VisitRequestHandler;
import service.Visit;

/**
 * Created by andrew on 6/16/15.
 */
public class Main implements Runnable {

    // 10 threads x 100 runs = 1000 visits

    public void run() {
        int x = 0;
        while(x < 1) {
            Visit visit = new Visit();
            visit.launch();
            x++;
        }
    }

    public static void main(String args[]) {
       int i = 0;
        while(i < 1) {
            (new Thread(new Main())).start();
            i++;
        }

    }
}

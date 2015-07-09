import model.LPMobileEnvironment;
import model.LPMobileLogger;
import model.SetEnvironment;
import networking.VisitRequestHandler;
import service.Visit;

/**
 * Created by andrew on 6/16/15.
 */
public class Main implements Runnable {

    public void run() {
        int x = 0;
        while(x < 10) {
            Visit visit = new Visit();
            visit.launch();
            x++;
        }
    }

    public static void main(String args[]) {
        (new Thread(new Main())).start();

    }
}

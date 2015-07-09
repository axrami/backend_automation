import model.LPMobileEnvironment;
import model.LPMobileLogger;
import model.SetEnvironment;
import networking.VisitRequestHandler;
import service.Visit;

/**
 * Created by andrew on 6/16/15.
 */
public class Main {
    public static void main(String args[]) {
        int x = 0;

        while(x < 10) {
            Visit visit = new Visit();
            visit.launch();
            x++;
        }


    }
}

import model.LPMobileEnvironment;
import model.SetEnvironment;
import networking.VisitRequestHandler;
import service.Visit;

/**
 * Created by andrew on 6/16/15.
 */
public class Main {
    public static void main(String args[]) {
        System.out.println("ok Im running");
        Visit session = new Visit();
        session.launch();

    }
}

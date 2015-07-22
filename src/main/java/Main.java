import service.Session;

/**
 * Created by andrew on 6/16/15.
 */
public class Main implements Runnable {

    // 10 threads x 100 runs = 1000 visits

    public void run() {
        for (int x = 0; x < 1; x++) {
            Session i = new Session();
            i.continuedVisit();

        }
    }

    public static void main(String args[]) {

        (new Thread(new Main())).start();


    }

}

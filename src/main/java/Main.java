import service.Instance;

/**
 * Created by andrew on 6/16/15.
 */
public class Main implements Runnable {

    // 10 threads x 100 runs = 1000 visits

    public void run() {
        for (int x = 0; x < 1; x++) {
            Instance i = new Instance();
            i.beginVisit();

        }
    }

    public static void main(String args[]) {

        (new Thread(new Main())).start();


    }

}

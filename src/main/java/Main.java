import model.TestReporter;
import service.Generator;

/**
 * Created by andrew on 6/16/15.
 */
public class Main {

    public static void main(String args[]) {
        TestReporter test = new TestReporter();
        Generator generator = new Generator();
        generator.createChatExecutor();
    }
}





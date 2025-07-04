import Controller.SocialMediaController;
import io.javalin.Javalin;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        //controller instance
        SocialMediaController controller = new SocialMediaController();

        //start the Javalin API
        Javalin app = controller.startAPI();

        //start server
        app.start(8080);

        System.out.println("Social Media Blog API running on http://localhost:8080");
    }

}


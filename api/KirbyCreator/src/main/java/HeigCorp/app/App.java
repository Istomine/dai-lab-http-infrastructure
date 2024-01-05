package HeigCorp.app;

import HeigCorp.app.controller.KirbyController;
import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Javalin app = Javalin.create().start(7000);
        System.out.println( "Hello World!" );
        KirbyController kirbyController = new KirbyController();
        app.post("/kirby/create",kirbyController::createKirby);
        app.get("/kirby/feed/{id}", kirbyController::feedKirby);
        app.get("/kirby/get/{id}", kirbyController::getOneKirby);
        app.get("/kirby/get", kirbyController::getAllKirby);
        app.delete("/kirby/delete/{id}", kirbyController::destroyKirby);
        app.delete("/kirby/delete",kirbyController::destroyAllKirby);
    }
}

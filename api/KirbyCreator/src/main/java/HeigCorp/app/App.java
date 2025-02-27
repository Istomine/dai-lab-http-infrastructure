package HeigCorp.app;

import HeigCorp.app.controller.KirbyController;
import HeigCorp.app.model.Kirby;
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
        KirbyController kirbyController = new KirbyController();
        try{
            app.post("/kirby/create",kirbyController::createKirby);
            app.put("/kirby/feed/{id}", kirbyController::feedKirby);
            app.get("/kirby/get/{id}", kirbyController::getOneKirby);
            app.get("/kirby/get", kirbyController::getAllKirby);
            app.delete("/kirby/delete/{id}",kirbyController::deleteOneKirby);
            app.delete("/kirby/delete",kirbyController::deleteAllKirby);
            app.get("/kirby/image/{id}", kirbyController::getKirbyImage);
        }catch (Exception e){
            System.out.println(e);
        }

    }
}

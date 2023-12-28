package HeigCorp.app.controller;

import HeigCorp.app.model.Kirby;

import io.javalin.http.Context;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

public class KirbyController {

    int id;
    private ConcurrentHashMap<Integer, Kirby> kirbys = new ConcurrentHashMap<>();

    public KirbyController(){
        this.id = 0;
    }
    private void updateKirbyStat(Kirby kirby){
        Duration durationLastUpdated  = Duration.between(Instant.now(),kirby.getLastUpdated());
        int tickNumber =  (int)durationLastUpdated.getSeconds() / (60 * 10);
        kirby.setHunger(kirby.getHunger() - tickNumber * 10);
        kirby.setSwagPoint(kirby.getSwagPoint() + tickNumber * 10);
        kirby.setFunPoint(kirby.getFunPoint() - tickNumber * 10);
    }

    public void feedKirby(Context ctx){
        Kirby kirby = kirbys.get(Integer.parseInt(ctx.pathParam("id")));
        updateKirbyStat(kirby);
        kirby.setHunger(kirby.getHunger() + 10);
        System.out.println(kirby.getName() + " a ete nourri");
    }

    public void createKirby(Context ctx){
        Kirby kirby = new Kirby("René","image");
        kirbys.put(id,kirby);
        System.out.println(kirby.getName() + " created !");
    }
}

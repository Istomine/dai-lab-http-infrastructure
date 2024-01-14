package HeigCorp.app.controller;

import HeigCorp.app.model.Kirby;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
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

    public void getAllKirby(Context ctx){
        ArrayList<Kirby> kirbyList = new ArrayList<>();
        kirbys.forEach((key,value) ->{
            updateKirbyStat(value);
            kirbyList.add(value);
        });
        ctx.json(kirbyList.toArray());
    }

    public void getOneKirby(Context ctx){
        Kirby kirby = kirbys.get(Integer.parseInt(ctx.pathParam("id")));
        updateKirbyStat(kirby);
        ctx.json(kirby);
    }

    public void feedKirby(Context ctx){
        Kirby kirby = kirbys.get(Integer.parseInt(ctx.pathParam("id")));
        updateKirbyStat(kirby);
        kirby.setHunger(kirby.getHunger() + 10);
        System.out.println(kirby.getName() + " a ete nourri");
    }

    public void createKirby(Context ctx){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode object = objectMapper.readTree(ctx.body());
            Kirby kirby = new Kirby(object.get("name").asText(),object.get("image").asText());
            kirbys.put(id++,kirby);
            System.out.println(kirby.getName() + " created !");
        } catch (JsonProcessingException e) {
            ctx.status(403);
            System.err.println("Error creating Kirbys");
        }
        ctx.status(200);
    }

    public void deleteOneKirby(Context ctx){
        kirbys.remove(Integer.parseInt(ctx.pathParam("id")));
        ctx.status(200);
    }
}

package HeigCorp.app.controller;

import HeigCorp.app.App;
import HeigCorp.app.model.Kirby;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class KirbyController {

    int id;
    private ConcurrentHashMap<Integer, Kirby> kirbys = new ConcurrentHashMap<>();

    public KirbyController(){
        this.id = 0;

        Kirby test = new Kirby("Rene","0.jpg");
        kirbys.put(id++,test);
        test = new Kirby("Morgane","1.jpg");
        kirbys.put(id++,test);
        test = new Kirby("Marc","2.jpg");
        kirbys.put(id++,test);
        test = new Kirby("Paul","3.jpg");
        kirbys.put(id++,test);
        test = new Kirby("Henry","4.jpg");
        kirbys.put(id++,test);
        test = new Kirby("Lea","5.jpg");
        kirbys.put(id++,test);

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
        System.out.println("hello");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode object = objectMapper.readTree(ctx.body());
            int randomNumber = new Random().nextInt(11);
            String randomString = Integer.toString(randomNumber);
            String imageName = randomString + ".jpg";
            Kirby kirby = new Kirby(object.get("name").asText(),imageName);
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

    public void deleteAllKirby(Context ctx){
        kirbys.clear();
        ctx.status(200);
    }

    public void getKirbyImage(Context ctx) {
        Kirby kirby = kirbys.get(Integer.parseInt(ctx.pathParam("id")));
        updateKirbyStat(kirby);

        String imagePath = "/image/" + kirby.getImage();
        InputStream inputStream = App.class.getResourceAsStream(imagePath);
        System.out.println(imagePath);
        if (inputStream != null) {
            try {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[1024];

                while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }

                buffer.flush();

                System.out.println("sended");
                ctx.result(buffer.toByteArray()).contentType("image/jpeg");
            } catch (Exception e) {
                ctx.status(500).result("Internal Server Error");
            }
        } else {
            // Si l'image n'est pas trouvée, renvoyez une réponse 404
            ctx.status(404).result("Image not found");
        }
    }
}

package ch.heig.dai.lab.tools;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {
        // Créer le serveur HTTP sur le port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);

        // Créer un contexte pour le chemin "/hello" et définir un gestionnaire pour les requêtes sur ce chemin
        server.createContext("/hello", new HelloHandler());

        // Démarrer le serveur
        server.setExecutor(null); // Utiliser le gestionnaire par défaut
        server.start();

        System.out.println("Serveur HTTP démarré sur le port 8080");
    }

    // Gestionnaire pour le chemin "/hello"
    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Obtenir la méthode de la requête (GET, POST, etc.)
            String requestMethod = exchange.getRequestMethod();

            // Construire une réponse
            String response = "Bonjour, c'est une réponse HTTP pour la méthode " + requestMethod;

            // Envoyer la réponse au client
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

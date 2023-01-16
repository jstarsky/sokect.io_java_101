package com.sfera.app;

import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Hello world!
 * mvn clean compile assembly:single
 * java -cp target/sfera_pgof_client-1.0-SNAPSHOT-jar-with-dependencies.jar
 * com.sfera.app.App
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            Socket socket = IO.socket("http://127.0.0.1:3000");
            JSONObject connection = new JSONObject();
            connection.put("uuid", "8b67ca1c-0454-44c1-a733-feb2c9538578");
            socket.connect();
            // socket.emit("client_message", {uuid:uuid});
            // Listen for messages from the server
            socket.emit("connection_connection", connection);
            socket.on("pgof_request", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println(args[0]);
                    JSONObject json = new JSONObject();
                    json.put("succes", "true");
                    setTimeout(() -> socket.emit("pgof_response", json), 1000);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void setTimeout(Runnable runnable, int delay) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, delay);
    }
}

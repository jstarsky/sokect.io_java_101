package com.sfera.app;

import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Example for socket io client for java using the library socket io client
 * mvn clean compile assembly:single
 * java -cp target/sfera_pgof_client-1.0-SNAPSHOT-jar-with-dependencies.jar
 * com.sfera.app.App
 * IntPtr
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            Socket socket = IO.socket("http://127.0.0.1:3000");
            JSONObject connection = new JSONObject();
            connection.put("uuid", "8b67ca1c-0454-44c1-a733-feb2c9538578");
            socket.connect();
            // Event that is execute once the connection is establishe and send the uuid/id
            // of the counter where the dlls are installed
            socket.emit("connection_connection", connection);
            // Lisening event that is expeting a request from the server wit the data
            // required for the dlls
            socket.on("pgof_request", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println(args[0]);
                    JSONObject json = new JSONObject();
                    json.put("succes", "true");
                    // Once the request to the SIFARE is done, te response is sent again to the
                    // server
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

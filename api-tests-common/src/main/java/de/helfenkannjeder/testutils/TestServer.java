package de.helfenkannjeder.testutils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public class TestServer {

    private static int PORT;
    private static HttpServer SERVER = null;

    public static int startHttpServer() throws IOException {
        if (SERVER == null) {
            ServerSocket serverSocket = new ServerSocket(0);
            PORT = serverSocket.getLocalPort();
            serverSocket.close();
            SERVER = HttpServer.create(new InetSocketAddress(PORT), 0);
            SERVER.setExecutor(null); // creates a default executor
            SERVER.start();
        }
        return getPort();
    }

    public static int getPort() {
        return PORT;
    }

    public static void addResource(String path, HttpHandler httpHandler) {
        SERVER.createContext(path, httpHandler);
    }

    public static class StringHttpHandler implements HttpHandler {

        private final String result;

        public StringHttpHandler(String result) {
            this.result = result;
        }

        public void handle(HttpExchange httpExchange) throws IOException {
            byte[] result = this.result.getBytes();
            httpExchange.getResponseHeaders().add("Content-Type", "application/json");
            httpExchange.sendResponseHeaders(200, result.length);

            OutputStream responseBody = httpExchange.getResponseBody();
            responseBody.write(result);
            responseBody.flush();
            responseBody.close();

            httpExchange.close();
        }
    }

    public static class NoContentHttpHandler implements HttpHandler {
        public void handle(HttpExchange httpExchange) throws IOException {
            httpExchange.sendResponseHeaders(204, -1);
            httpExchange.close();
        }
    }

}

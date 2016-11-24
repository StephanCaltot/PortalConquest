package portalconquest.test;

/**
 * Created by Screetts on 27/04/2016.
 */

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.tyrus.client.ClientManager;
import portalconquest.websocket.CWebsocketClient;

import javax.websocket.DeploymentException;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;



public class CServer {

    private static CWebsocketClient mWSClient;


    private static int getPort(int defaultPort) {
        String httpPort = System.getProperty("jersey.test.port");
        if (null != httpPort) {
            try {
                return Integer.parseInt(httpPort);
            } catch (NumberFormatException e) {
            }
        }
        return defaultPort;
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://0.0.0.0/").port(getPort(9998)).build();
    }

    public static final URI BASE_URI = getBaseURI();

    protected static HttpServer startServer() throws IOException {
        ResourceConfig resourceConfig = new PackagesResourceConfig("portalconquest");
        return GrizzlyServerFactory.createHttpServer(BASE_URI, resourceConfig);
    }

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = startServer();

        System.out.println(String.format("Jersey app started " + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));

        System.in.read();
        httpServer.stop();
    }

}
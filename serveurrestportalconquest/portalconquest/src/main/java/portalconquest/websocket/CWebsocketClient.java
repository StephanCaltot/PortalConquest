package portalconquest.websocket;

import org.glassfish.tyrus.client.ClientManager;
import websocketmessages.androidrequests.CWebsocketRequest;
import websocketmessages.androidrequests.EWebsocketRequestType;
import websocketmessages.serverresponses.CWebsocketResponse;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

/**
 * Created by ltonnet637 on 11/06/16.
 */

@ClientEndpoint(encoders = {CWebsocketRequest.CWebsocketRequestCoder.class},
                decoders = {CWebsocketResponse.CWebsocketResponseCoder.class})
    public class CWebsocketClient extends Thread {
//
//    public final static String SERVER_IP = "10.21.199.198";
//    public final static int SERVER_PORT = 8025;
//    public final static String SERVER_ID = "/echo";
//
//    private static Session mSession;
//    private static ClientManager mClientManager;
//
//    private static Object waitLock = new Object();
//
//
//    @OnOpen
//    public void onOpen(final Session pSession, EndpointConfig pEndpointConfig) {
//        mSession = pSession;
//        System.out.println("Rest connected to Websocket");
//    }
//
//
//    @OnMessage
//    public void onMessage(CWebsocketResponse pResponse) { return; }
//
//
//    @OnClose
//    public void onClose(final Session mSession, EndpointConfig pEndpointConfig) {
//        System.out.println("Rest disconnected from Websocket");
//    }
//
//
    public static void broadcastToAndroid(CWebsocketRequest pMessage) {
//        pMessage.addRequestType(EWebsocketRequestType.BROADCAST_ANDROID);
//        if (mSession != null) {
//            try {
//
//                mSession.getBasicRemote().sendObject(pMessage);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (EncodeException e) {
//                e.printStackTrace();
//            }
//        }
    }
//
//
    public static void connectToWSServer(CWebsocketClient pWebsocketClient) {
//        URI uri = URI.create("ws://192.168.43.59:8025/echo");
//        mClientManager = new ClientManager();
//        System.out.println("connect to server");
//        try {
//            System.out.println("try");
//            mClientManager.connectToServer(CWebsocketClient.class, uri);
//            System.out.println("try2");
//        } catch (Exception e) {
//            System.out.println("Exception");
////        } catch (DeploymentException e) {
////        } catch (IOException e) {
//        }
    }
}

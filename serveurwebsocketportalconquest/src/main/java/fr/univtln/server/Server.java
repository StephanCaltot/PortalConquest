package fr.univtln.server;

import classes.CField;
import classes.CLink;
import classes.CMapItem;
import classes.CPortal;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import websocketmessages.androidrequests.CWebsocketRequest;
import websocketmessages.androidrequests.EWebsocketRequestType;
import websocketmessages.serverresponses.CWebsocketResponse;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@ServerEndpoint(value = "/echo",
        encoders = {CWebsocketResponse.CWebsocketResponseCoder.class},
        decoders = {CWebsocketRequest.CWebsocketRequestCoder.class})
public class Server {
    private static List<Session> mSessions = new ArrayList<Session>();
    public final static String SERVER_IP;
    public final static int SERVER_PORT;


    private static WebResource mWebResource;

    static {
        String ip = "192.168.1.11";
//        String ip = "localhost";

        int port = 8025;

        SERVER_IP = ip;
        SERVER_PORT = port;
        System.out.println("Server IP:" + SERVER_IP + " Port: " + SERVER_PORT);
    }


    @OnOpen
    public void onOpen(final Session session, EndpointConfig endpointConfig) {
        System.out.println("new Client connected in session " + session.getId());
        mSessions.add(session);
    }

    @OnMessage
    public void OnMessage(CWebsocketRequest pRequest, Session peer) throws IOException, EncodeException {
        if (pRequest.getRequestType().contains(EWebsocketRequestType.INITIALIZE_MAP_REQUEST)) {
            initializeMap(peer);
        } else if (pRequest.getRequestType().contains(EWebsocketRequestType.INITIALIZE_PORTAL_REQUEST)) {
            if (pRequest.getPortals().get(0) != null)
                initializePortal(peer);
        } else if (pRequest.getRequestType().contains(EWebsocketRequestType.BROADCAST_ANDROID)) {
            CWebsocketResponse lWebsocketResponse = new CWebsocketResponse(pRequest.getRequestType());
            broadcastMessageToClients(lWebsocketResponse);
        }

    }

    @OnClose
    public void onClose(final Session session, EndpointConfig endpointConfig) {
        System.out.println(session.getId() + " leaved.");
        mSessions.remove(session);
    }


    public static void main(String[] args) {

        ClientConfig cc = new DefaultClientConfig();
        cc.getClasses().add(JacksonJsonProvider.class);
        Client c = Client.create(cc);
        mWebResource = c.resource("http://localhost:9998/");


        System.out.println("Server starting...");
        org.glassfish.tyrus.server.Server server =
                new org.glassfish.tyrus.server.Server(SERVER_IP, SERVER_PORT, "/", null, Server.class);
        try {
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please press a key to stop the server...");
            reader.readLine();
            while (true) ;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }


    public void initializeMap(Session peer) throws IOException, EncodeException {

        List<CPortal> mAllPortals = new ArrayList<CPortal>();
        List<CLink> mAllLinks = new ArrayList<CLink>();
        List<CField> mAllFields = new ArrayList<CField>();
        List<CMapItem> mAllMapItems = new ArrayList<CMapItem>();

        CWebsocketResponse mRequestResponse = new CWebsocketResponse();

        mAllPortals.addAll(mWebResource.path("portals").type(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<CPortal>>() {
        }));
        mAllLinks.addAll(mWebResource.path("links").type(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<CLink>>() {
        }));
        mAllFields.addAll(mWebResource.path("fields").type(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<CField>>() {
        }));
        mAllMapItems.addAll(mWebResource.path("mapitems").type(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<CMapItem>>(){
        }));

        mRequestResponse.setFields(mAllFields);
        mRequestResponse.setLinks(mAllLinks);
        mRequestResponse.setPortals(mAllPortals);
        mRequestResponse.setMapItems(mAllMapItems);

        sendMessageToClient(mRequestResponse, peer);
    }


    private void initializePortal(Session pSession) throws IOException, EncodeException {

        CWebsocketResponse mResponse = new CWebsocketResponse();
        mResponse.setPortals(mWebResource.path("portals").type(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<CPortal>>(){}));

        sendMessageToClient(mResponse, pSession);
    }


    public static void sendMessageToClient(CWebsocketResponse pWebsocketResponse, Session pClient) throws IOException, EncodeException {
        pClient.getBasicRemote().sendObject(pWebsocketResponse);
    }


    public static void broadcastMessageToClients(CWebsocketResponse pWebsocketResponse) {
        System.out.println("Fonction bien appelée: " + pWebsocketResponse.getResponseType());
        for (Session lSession : mSessions)
            try {
                lSession.getBasicRemote().sendObject(pWebsocketResponse);
                System.out.println("Objet envoyé");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
    }
}
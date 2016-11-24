package portalconquest.services;

import classes.CMapItem;
import messages.CGetMapItemAction;
import portalconquest.dao.CCrudServiceBean;
import portalconquest.dao.ICrudService;
import portalconquest.dao.QueryParameter;
import portalconquest.websocket.CWebsocketClient;
import websocketmessages.androidrequests.CWebsocketRequest;
import websocketmessages.serverresponses.CWebsocketResponse;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import java.util.List;

import static fr.univtln.server.Server.broadcastMessageToClients;
import static portalconquest.dao.CCrudServiceBean.em;

/**
 * Created by Screetts on 11/05/2016.
 */

@Path("/mapitems")
@Produces("application/json")
@Consumes("application/json")
public class CMapItemServices {

    public static ICrudService<CMapItem> sCrudMapItems = new CCrudServiceBean<CMapItem>();

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public CMapItem getMapItems(@PathParam("id") final int id) {
        return (CMapItem) sCrudMapItems.findWithNamedQuery(CMapItem.MAP_ITEM_BY_ID, QueryParameter.with("Pid", id).parameters()).get(0);
    }

    @GET
    @Produces("application/json")
    public static List<CMapItem> getMapItemsAll() {
        return (List<CMapItem>) sCrudMapItems.findWithNamedQuery(CMapItem.MAP_ITEM_BY_ALL);
    }

    @PUT
    @Produces("application/json")
    public void putMapItems(CGetMapItemAction mapItems) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        mapItems.pickUpMapItem();
        transac.commit();

        CWebsocketRequest cWebsocketRequest = new CWebsocketRequest();
        cWebsocketRequest.setMapItems(getMapItemsAll());
        CWebsocketClient.broadcastToAndroid(cWebsocketRequest);
    }


    @POST
    @Produces("application/json")
    public void postMapItems(CMapItem mapItems) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudMapItems.create(mapItems);
        transac.commit();
    }


    @DELETE
    @Path("/{id}")
    public void deleteMapItems(@PathParam("id") final int id) {
        sCrudMapItems.delete(CMapItem.class, id);
    }
}



package portalconquest.services;

import classes.CInventory;
import messages.CDropItem;
import portalconquest.dao.CCrudServiceBean;
import portalconquest.dao.ICrudService;
import portalconquest.dao.QueryParameter;
import portalconquest.websocket.CWebsocketClient;
import websocketmessages.androidrequests.CWebsocketRequest;
import websocketmessages.serverresponses.CWebsocketResponse;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;

import static portalconquest.dao.CCrudServiceBean.em;
import static portalconquest.services.CMapItemServices.getMapItemsAll;

/**
 * Created by Screetts on 03/05/2016.
 */

@Path("/inventories")
@Produces("application/json")
@Consumes("application/json")
public class CInventoryServices {

    public static ICrudService<CInventory> sCrudInventory = new CCrudServiceBean<CInventory>();


//    public CInventoryServices() {
//        if(sCrudInventory == null)
//        sCrudInventory = new CCrudServiceBean<CInventory>();
//    }
//
//
//    public CInventoryServices(ICrudService pCrudInventory) {
//        sCrudInventory = pCrudInventory;
//    }


    @GET
    @Produces("application/json")
    @Path("/{playerid}/{itemid}")
    public static CInventory getInventory(@PathParam("playerid") final int playerid, @PathParam("itemid") final int itemid) {
        return (CInventory) sCrudInventory.findWithNamedQuery(CInventory.BY_ID, QueryParameter.with("Pplayerid", playerid).and("Pitemid",itemid).parameters()).get(0);
    }


    @PUT
    @Produces("application/json")
    public static void putInventory(CInventory inventory) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudInventory.update(inventory);
        transac.commit();
    }


    @POST
    @Produces("application/json")
    public void postInventory(CInventory inventory) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudInventory.create(inventory);
        transac.commit();
    }

    @POST
    @Produces("application/json")
    @Path("/dropitem")
    public void dropItem(CDropItem pDropItem) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        pDropItem.dropItem();
        transac.commit();

        CWebsocketRequest cWebsocketRequest = new CWebsocketRequest();
        cWebsocketRequest.setMapItems(getMapItemsAll());
        CWebsocketClient.broadcastToAndroid(cWebsocketRequest);
    }

//    @DELETE
//    @Path("/{playerid}/{itemid}")
//    public void deletePIA(@PathParam("playerid") final int playerid, @PathParam("itemid") final int itemid) {
//        sCrudInventory.findWithNamedQuery(CInventory.DELETE, QueryParameter.with("Pplayerid", playerid).and("Pitemid",itemid).parameters());}
}

package portalconquest.services;

import messages.CSetUpgradeOnPortal;
import portalconquest.websocket.CWebsocketClient;
import websocketmessages.androidrequests.CWebsocketRequest;
import websocketmessages.serverresponses.CWebsocketResponse;

import javax.persistence.EntityTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static portalconquest.dao.CCrudServiceBean.em;
import static portalconquest.services.CPortalServices.getPortalAll;

/**
 * Created by Screetts on 07/06/2016.
 */
@Path("/upgrades")
@Produces("application/json")
@Consumes("application/json")
public class CUpgradeServices {

    @POST
    @Produces("application/json")
    public void postUpgrade(CSetUpgradeOnPortal cSetUpgradeOnPortal) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        cSetUpgradeOnPortal.putUpgradeOnPortal();
        transac.commit();

        CWebsocketRequest cWebsocketRequest = new CWebsocketRequest();
        cWebsocketRequest.setPortals(getPortalAll());

        CWebsocketClient.broadcastToAndroid(cWebsocketRequest);
    }
}
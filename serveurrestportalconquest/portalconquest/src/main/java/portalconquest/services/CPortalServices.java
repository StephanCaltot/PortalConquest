package portalconquest.services;

import classes.CPortal;
import messages.CAttackAction;
import messages.CHackPortalAction;
import messages.CInfectPortalAction;
import portalconquest.dao.CCrudServiceBean;
import portalconquest.dao.ICrudService;
import portalconquest.dao.QueryParameter;
import portalconquest.websocket.CWebsocketClient;
import websocketmessages.androidrequests.CWebsocketRequest;
import websocketmessages.androidrequests.EWebsocketRequestType;
import websocketmessages.serverresponses.CWebsocketResponse;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import java.util.List;

import static org.eclipse.persistence.sessions.server.ConnectionPolicy.ExclusiveMode.Transactional;
import static portalconquest.dao.CCrudServiceBean.em;
import static portalconquest.services.CFieldServices.getFieldAll;
import static portalconquest.services.CLinkServices.getLinkAll;

/**
 * Created by Screetts on 28/04/2016.
 */

@Path("/portals")
@Produces("application/json")
@Consumes("application/json")
public class CPortalServices {

    public static ICrudService<CPortal> sCrudPortal = new CCrudServiceBean<CPortal>();


    @GET
    @Produces("application/json")
    @Path("/{id}")
    public CPortal getPortalById(@PathParam("id") final int id) {
        return (CPortal) sCrudPortal.findWithNamedQuery(CPortal.PORTAL_BY_ID, QueryParameter.with("Pid",id).parameters()).get(0);
    }

    @GET
    @Produces("application/json")
    public static List<CPortal> getPortalAll() {
        return (List<CPortal>) sCrudPortal.findWithNamedQuery(CPortal.PORTAL_BY_ALL);
    }


    @PUT
    @Produces("application/json")
    public static void putPortal(CPortal portal) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudPortal.update(portal);
        transac.commit();
    }

    @POST
    @Produces("application/json")
    @Path("/virus")
    public void infectPortal(CInfectPortalAction pInfectPortalAction) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        pInfectPortalAction.useVirus();
        transac.commit();

        CWebsocketRequest cWebsocketRequest = new CWebsocketRequest();
        cWebsocketRequest.setFields(getFieldAll());
        cWebsocketRequest.setLinks(getLinkAll());
        cWebsocketRequest.setPortals(getPortalAll());

        CWebsocketClient.broadcastToAndroid(cWebsocketRequest);
    }


    @POST
    @Produces("application/json")
    @Path("/hack")
    public void hackPortal(CHackPortalAction cHackPortalAction) {
        System.out.println("je suis dans HACK cotes server");
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        cHackPortalAction.hackPortal();
        transac.commit();
    }

    @POST
    @Produces("application/json")
    @Path("/attack")
    public int attackPortal(CAttackAction cAttackAction) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        int mFunctionReturn = cAttackAction.attack();
        transac.commit();

        CWebsocketRequest cWebsocketRequest = new CWebsocketRequest();

        if ( mFunctionReturn == 10 ) {
            cWebsocketRequest.setFields(getFieldAll());
            cWebsocketRequest.setLinks(getLinkAll());
        }
        cWebsocketRequest.setPortals(getPortalAll());
        CWebsocketClient.broadcastToAndroid(cWebsocketRequest);

        return mFunctionReturn;
    }

    @POST
    @Produces("application/json")
    public void postPortal(CPortal portal) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudPortal.create(portal);
        transac.commit();
    }


    @DELETE
    @Path("/{id}")
    public void deletePortal(@PathParam("id") final int id ) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudPortal.delete(CPortal.class, id);
        transac.commit();
    }

}

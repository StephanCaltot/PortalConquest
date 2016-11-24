package portalconquest.services;

import classes.CResonator;
import messages.CSetResonatorOnPortal;
import portalconquest.dao.CCrudServiceBean;
import portalconquest.dao.ICrudService;
import portalconquest.dao.QueryParameter;
import portalconquest.websocket.CWebsocketClient;
import websocketmessages.androidrequests.CWebsocketRequest;
import websocketmessages.serverresponses.CWebsocketResponse;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;

import static portalconquest.dao.CCrudServiceBean.em;
import static portalconquest.services.CFieldServices.getFieldAll;
import static portalconquest.services.CLinkServices.getLinkAll;
import static portalconquest.services.CPortalServices.getPortalAll;

/**
 * Created by Screetts on 23/05/2016.
 */
@Path("/resonators")
@Produces("application/json")
@Consumes("application/json")
public class CResonatorServices {

    ICrudService<CResonator> mCrudResonator = new CCrudServiceBean<CResonator>();

    @GET
    @Produces("application/json")
    @Path("/{level}")
    public CResonator get(@PathParam("level") final int level){
        return (CResonator) mCrudResonator.findWithNamedQuery(CResonator.BY_LEVEL, QueryParameter.with("Plevel",level).parameters()).get(0);
    }

    @POST
    @Produces("application/json")
    public void postResonator (CSetResonatorOnPortal cSetResonatorOnPortal){
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        System.out.println("post resonator");
//        int mFunctionReturn = cSetResonatorOnPortal.putResonatorOnPortal();
        cSetResonatorOnPortal.putResonatorOnPortal();
        transac.commit();

//        CWebsocketRequest cWebsocketResponse = new CWebsocketRequest();

//        if ( mFunctionReturn == 10 ) {
//            cWebsocketResponse.setFields(getFieldAll());
//            cWebsocketResponse.setLinks(getLinkAll());
//        }
//        cWebsocketResponse.setPortals(getPortalAll());
//        CWebsocketClient.broadcastToAndroid(cWebsocketResponse);

//        return mFunctionReturn;
    }
}

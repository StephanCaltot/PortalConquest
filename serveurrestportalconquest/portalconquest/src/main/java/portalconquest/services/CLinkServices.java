package portalconquest.services;

import classes.CField;
import classes.CLink;
import classes.CPortal;
import messages.CSetLink;
import portalconquest.dao.CCrudServiceBean;
import portalconquest.dao.ICrudService;
import portalconquest.dao.QueryParameter;
import portalconquest.utils.CPortalFieldValidator;
import portalconquest.websocket.CWebsocketClient;
import websocketmessages.androidrequests.CWebsocketRequest;
import websocketmessages.serverresponses.CWebsocketResponse;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static portalconquest.dao.CCrudServiceBean.em;
import static portalconquest.services.CFieldServices.getFieldAll;
import static portalconquest.services.CFieldServices.sCrudField;
import static portalconquest.services.CPortalServices.getPortalAll;
import static portalconquest.services.CPortalServices.sCrudPortal;
import static portalconquest.services.CTeamServices.sCrudTeam;

//import static fr.univtln.server.Server.broadcastMessageToClients;

/**
 * Created by Screetts on 10/05/2016.
 */
@Path("/links")
@Produces("application/json")
@Consumes("application/json")
public class CLinkServices {

    public static ICrudService<CLink> sCrudLink = new CCrudServiceBean<CLink>();

    @GET
    @Produces("application/json")
    @Path("/{link_id}")
    public static CLink getLink(@PathParam("link_id") final int id) {
        return (CLink) sCrudLink.findWithNamedQuery(CLink.BY_ID, QueryParameter.with("Plinkid", id).parameters()).get(0);
    }

    @GET
    @Produces("application/json")
    public static List<CLink> getLinkAll() {
        return (List<CLink>) sCrudLink.findWithNamedQuery(CLink.BY_ALL);
    }

    @PUT
    @Produces("application/json")
    public void putLink(CLink link) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        if ((!link.getFirstPortal().equals(null)) && (!link.getSecondPortal().equals(null))) {
            link.getFirstPortal().addLink(link);
            link.getSecondPortal().addLink(link);
            sCrudPortal.update(link.getFirstPortal());
            sCrudPortal.update(link.getSecondPortal());
        } else
            sCrudLink.update(link);
        transac.commit();
    }


    @POST
    @Produces("application/json")
    public static void postLink(CSetLink cSetLink) throws InterruptedException, ExecutionException, IOException {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        int mFunctionResult = cSetLink.setLink();
        transac.commit();

        CWebsocketRequest cWebsocketRequest = new CWebsocketRequest();
        if ( mFunctionResult == 1 ) {
            cWebsocketRequest.setLinks(getLinkAll());
            cWebsocketRequest.setPortals(getPortalAll());
        }
        else if ( mFunctionResult == 2 ) {
            cWebsocketRequest.setLinks(getLinkAll());
            cWebsocketRequest.setFields(getFieldAll());
            cWebsocketRequest.setPortals(getPortalAll());
        }

        CWebsocketClient.broadcastToAndroid(cWebsocketRequest);
    }


    @DELETE
    @Path("/{id}")
    public static void deleteLink ( @PathParam("id") final int id){
        CLink link = getLink(id);

        link.getFirstPortal().getLinks().remove(link);
        link.getSecondPortal().getLinks().remove(link);

        sCrudPortal.update(link.getSecondPortal());
        sCrudPortal.update(link.getFirstPortal());

        for ( CField mField : getFieldAll()){
            if (mField.getAllPortals().contains(link.getFirstPortal()) && mField.getAllPortals().contains(link.getSecondPortal())){
                for(CPortal mPortal : mField.getAllPortals()) {
                    mPortal.getFields().remove(mField);
                    sCrudPortal.update(mPortal);
                }
                sCrudField.delete(CField.class,mField.getId());
                mField.getTeam().setScore(mField.getTeam().getScore() - CPortalFieldValidator.getINSTANCE().getScore(mField));
                sCrudTeam.update(mField.getTeam());
            }
        }
        sCrudLink.delete(CLink.class, id);
    }
}


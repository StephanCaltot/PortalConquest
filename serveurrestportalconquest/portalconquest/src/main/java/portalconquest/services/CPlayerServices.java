package portalconquest.services;

import classes.*;
import com.google.maps.model.LatLng;
import messages.CPasscodeAction;
import portalconquest.dao.CCrudServiceBean;
import portalconquest.dao.ICrudService;
import portalconquest.dao.QueryParameter;
import portalconquest.utils.CDistanceCalculator;
import websocketmessages.serverresponses.CWebsocketResponse;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

import static fr.univtln.server.Server.broadcastMessageToClients;
import static portalconquest.dao.CCrudServiceBean.em;
import static portalconquest.services.CInventoryServices.sCrudInventory;
import static portalconquest.services.CMapItemServices.getMapItemsAll;
import static portalconquest.services.CSlotServices.sCrudSlot;
import static portalconquest.services.CPortalServices.sCrudPortal;

/**
 * Created by Screetts on 27/04/2016.
 */

@Path("/players")
@Produces("application/json")
@Consumes("application/json")
public class CPlayerServices {

    public static ICrudService<CPlayer> sCrudPlayer = new CCrudServiceBean<CPlayer>();

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public static CPlayer getPlayer(@PathParam("id") final int id) {
        return (CPlayer) sCrudPlayer.findWithNamedQuery(CPlayer.PLAYER_BY_ID, QueryParameter.with("Pid",id).parameters()).get(0);
    }


    @GET
    @Produces("application/json")
    @Path("/emails/{email}")
    public static CPlayer getPlayerByEmail(@PathParam("email") final String email) {
        return (CPlayer) sCrudPlayer.findWithNamedQuery(CPlayer.PLAYER_BY_EMAIL, QueryParameter.with("Pemail",email).parameters()).get(0);
    }


    @GET
    @Produces("application/json")
    @Path("/{id}/inventories")
    public static List<CInventory> getInventoryByPlayer(@PathParam("id") final int id) {
        return (List<CInventory>) sCrudInventory.findWithNamedQuery(CInventory.BY_PLAYER,QueryParameter.with("Pplayerid",id).parameters());
    }


    @GET
    @Produces("application/json")
    @Path("/{id}/slots")
    public static List<CSlot> getSlotsByPlayer(@PathParam("id") final int id) {
        return (List<CSlot>) sCrudSlot.findWithNamedQuery(CSlot.CSLOT_BY_PLAYER,QueryParameter.with("Pplayerid",id).parameters());
    }


    @GET
    @Produces("application/json")
    @Path("/status")
    public static List<CPlayer> getPlayerByStatus() {
        return (List<CPlayer>) sCrudPlayer.findWithNamedQuery(CPlayer.PLAYER_BY_STATUS);
    }

    @GET
    @Produces("application/json")
    @Path("/inportalrange")
    public static List<CPlayer> getPlayerByPortalRange() {
        List<CPlayer> mConnectedPlayers = sCrudPlayer.findWithNamedQuery(CPlayer.PLAYER_BY_STATUS);
        List<CPortal> mPortals = sCrudPortal.findWithNamedQuery(CPortal.PORTAL_BY_ALL);

        List<CPlayer> mPlayersInPortalRange = new ArrayList<CPlayer>();

        if(! mConnectedPlayers.equals(new ArrayList<CPlayer>()) && ! mPortals.equals(new ArrayList<CPortal>()))
            for(CPlayer mPlayer : mConnectedPlayers)
                for(CPortal mPortal : mPortals)
                    if( CDistanceCalculator.getINSTANCE().distanceBetween(  new LatLng(mPlayer.getLatitude(), mPlayer.getLongitude()),
                                                                            new LatLng(mPortal.getLatitude(), mPortal.getLongitude()))
                        <= CDistanceCalculator.getINSTANCE().getPortalRange(mPortal))
                        mPlayersInPortalRange.add(mPlayer);

        return mPlayersInPortalRange;
    }

    @GET
    @Produces("application/json")
    public static List<CPlayer> playerAll(){
        return (List<CPlayer>) sCrudPlayer.findWithNamedQuery(CPlayer.PLAYER_BY_ALL);
    }


    @PUT
    @Produces("application/json")
    @Path("/passcode")
    public void usePasscode(CPasscodeAction pPasscodeAction) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        pPasscodeAction.usePasscode();
        transac.commit();
    }


    @PUT
    @Produces("application/json")
    public void putPlayer(CPlayer player){
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudPlayer.update(player);
        transac.commit();
    }


    @POST
    @Produces("application/json")
    public void postPlayer(CPlayer cPlayer) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudPlayer.create(cPlayer);
        transac.commit();
    }


    @DELETE
    @Path("/{id}")
    public void deletePlayer(@PathParam("id") final int id ) {
        sCrudPlayer.delete(CPlayer.class, id);
    }
}
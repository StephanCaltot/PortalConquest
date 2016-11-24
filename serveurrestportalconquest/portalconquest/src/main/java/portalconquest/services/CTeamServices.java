package portalconquest.services;

import classes.*;
import portalconquest.dao.CCrudServiceBean;
import portalconquest.dao.ICrudService;
import portalconquest.dao.QueryParameter;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import java.util.List;

import static portalconquest.dao.CCrudServiceBean.em;
import static portalconquest.services.CFieldServices.sCrudField;
import static portalconquest.services.CLinkServices.sCrudLink;
import static portalconquest.services.CPlayerServices.sCrudPlayer;
import static portalconquest.services.CPortalServices.sCrudPortal;


/**
 * Created by Screetts on 07/03/2016.
 */

@Path("/teams")
@Produces("application/json")
@Consumes("application/json")
public class CTeamServices {

    public static ICrudService<CTeam> sCrudTeam = new CCrudServiceBean<CTeam>();

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public static CTeam getTeam(@PathParam("id") final int id){
        return (CTeam) sCrudTeam.findWithNamedQuery(CTeam.TEAM_BY_ID, QueryParameter.with("Pid",id).parameters()).get(0);
    }

    @GET
    @Produces("application/json")
    @Path("/{id}/players")
    public List<CPlayer> getPlayerByTeam(@PathParam("id") final int id) {
        return (List<CPlayer>) sCrudPlayer.findWithNamedQuery(CPlayer.PLAYER_BY_TEAM, QueryParameter.with("Pid",id).parameters());
    }

    @GET
    @Produces("application/json")
    @Path("/{id}/portals")
    public List<CPortal> getPortalByTeam(@PathParam("id") final int id) {
        return (List<CPortal>) sCrudPortal.findWithNamedQuery(CPortal.PORTAL_BY_TEAM, QueryParameter.with("Pid",id).parameters());
    }


    @GET
    @Produces("application/json")
    @Path("/{id}/fields")
    public List<CField> getFieldByTeam(@PathParam("id") final int id) {
        return (List<CField>) sCrudField.findWithNamedQuery(CField.FIELD_BY_TEAM, QueryParameter.with("Pid",id).parameters());
    }

    @GET
    @Produces("application/json")
    @Path("/{id}/links")
    public List<CLink> getLinksByTeam(@PathParam("id") final int id) {
        return (List<CLink>) sCrudLink.findWithNamedQuery(CLink.BY_TEAM, QueryParameter.with("Pteamid",id).parameters());
    }


    @GET
    @Produces("application/json")
    public List<CTeam> getTeamAll() {
        return (List<CTeam>) sCrudTeam.findWithNamedQuery(CTeam.TEAM_BY_ALL);
    }


    @PUT
    @Produces("application/json")
    public void putTeam(CTeam cTeam) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudTeam.update(cTeam);
        transac.commit();
    }


    @POST
    @Produces("application/json")
    public void postTeam(CTeam cTeam ) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudTeam.create(cTeam);
        transac.commit();
    }


    @DELETE
    @Path("/{id}")
    public void deleteTeam(@PathParam("id") final int id ) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudTeam.delete(CTeam.class, id);
        transac.commit();
    }

}
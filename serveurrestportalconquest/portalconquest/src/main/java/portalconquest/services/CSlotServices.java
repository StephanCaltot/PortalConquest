package portalconquest.services;

import classes.CSlot;
import portalconquest.dao.CCrudServiceBean;
import portalconquest.dao.ICrudService;
import portalconquest.dao.QueryParameter;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import java.util.List;

import static portalconquest.dao.CCrudServiceBean.em;

/**
 * Created by Screetts on 28/04/2016.
 */

@Path("/slots")
@Produces("application/json")
@Consumes("application/json")
public class CSlotServices {

    public static ICrudService<CSlot> sCrudSlot = new CCrudServiceBean<CSlot>();

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public CSlot getSlotById(@PathParam("id") final int id) {
        return (CSlot) sCrudSlot.findWithNamedQuery(CSlot.CSLOT_BY_ID, QueryParameter.with("Pid", id).parameters()).get(0);
    }


    @GET
    @Produces("application/json")
    public List<CSlot> getSlotsAll() {
        return (List<CSlot>) sCrudSlot.findWithNamedQuery(CSlot.CSLOT_BY_ALL);
    }


    @PUT
    @Produces("application/json")
    public static void putSlot(CSlot slot) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudSlot.update(slot);
        transac.commit();
    }


    @POST
    @Produces("application/json")
    public void postSlot(CSlot slot) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudSlot.create(slot);
        transac.commit();
    }


    @DELETE
    @Path("/{id}")
    public void deleteSlot(@PathParam("id") final int id) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudSlot.delete(CSlot.class, id);
        transac.commit();
    }
}


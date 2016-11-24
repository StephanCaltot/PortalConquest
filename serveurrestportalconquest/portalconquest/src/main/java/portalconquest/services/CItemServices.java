package portalconquest.services;


import classes.AItem;
import portalconquest.dao.CCrudServiceBean;
import portalconquest.dao.ICrudService;
import portalconquest.dao.QueryParameter;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import java.util.List;

import static portalconquest.dao.CCrudServiceBean.em;


/**
 * Created by Screetts on 05/05/2016.
 */

@Path("/items")
@Produces("application/json")
@Consumes("application/json")
public class CItemServices {

    public static ICrudService<AItem> sCrudItem = new CCrudServiceBean<AItem>();

//    ========== TESTS UNITAIRES ============
//    public CItemServices() {
//
//        if(sCrudItem == null)
//            sCrudItem = new CCrudServiceBean<AItem>();
//    }
//
//
//    public CItemServices(ICrudService pCrudItem) {
//        sCrudItem = pCrudItem;
//    }
//

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public static AItem getItems(@PathParam("id") final int id) {
        return (AItem) sCrudItem.findWithNamedQuery(AItem.ITEM_BY_ID, QueryParameter.with("Pid",id).parameters()).get(0);
    }


    @GET
    @Produces("application/json")
    public static List<AItem> getItemsAll() {
        return (List<AItem>) sCrudItem.findWithNamedQuery(AItem.ITEM_BY_ALL);
    }


    @PUT
    @Produces("application/json")
    public void putItem(AItem item) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudItem.update(item);
        transac.commit();
    }


    @POST
    @Produces("application/json")
    public void postItem(AItem item) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudItem.create(item);
        transac.commit();
    }


    @DELETE
    @Path("/{id}")
    public void deleteItem(@PathParam("id") final int id ) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudItem.delete(AItem.class, id);
        transac.commit();
    }

}
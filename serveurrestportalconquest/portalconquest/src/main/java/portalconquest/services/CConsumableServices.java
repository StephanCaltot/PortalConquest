package portalconquest.services;

import classes.CConsumable;
import portalconquest.dao.CCrudServiceBean;
import portalconquest.dao.ICrudService;
import portalconquest.dao.QueryParameter;

import javax.ws.rs.*;

/**
 * Created by Screetts on 23/05/2016.
 */
@Path("/consumables")
@Produces("application/json")
@Consumes("application/json")
public class CConsumableServices {

     ICrudService<CConsumable> mCrudConsumable = new CCrudServiceBean<CConsumable>();

//      ========== TESTS UNITAIRES ============
//
//    public CConsumableServices() {
//        if(mCrudConsumable == null)
//            mCrudConsumable = new CCrudServiceBean<CConsumable>();
//    }
//
//    public CConsumableServices(ICrudService pCrudConsumable){
//        mCrudConsumable = pCrudConsumable;
//    }

    @GET
    @Produces("application/json")
    @Path("/{level}")
    public CConsumable get(@PathParam("level") final int level){
        return (CConsumable) mCrudConsumable.findWithNamedQuery(CConsumable.BY_LEVEL, QueryParameter.with("Plevel",level).parameters()).get(0);
    }

}

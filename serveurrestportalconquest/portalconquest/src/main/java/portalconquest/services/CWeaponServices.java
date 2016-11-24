package portalconquest.services;

import classes.CWeapon;
import portalconquest.dao.CCrudServiceBean;
import portalconquest.dao.ICrudService;
import portalconquest.dao.QueryParameter;

import javax.ws.rs.*;

/**
 * Created by Screetts on 23/05/2016.
 */
@Path("/weapons")
@Produces("application/json")
@Consumes("application/json")
public class CWeaponServices {

    static ICrudService<CWeapon> mCrudWeapon = new CCrudServiceBean<CWeapon>();

    @GET
    @Produces("application/json")
    @Path("/{level}")
    public static CWeapon get(@PathParam("level") final int level){
        return (CWeapon) mCrudWeapon.findWithNamedQuery(CWeapon.BY_LEVEL, QueryParameter.with("Plevel",level).parameters()).get(0);
    }

}
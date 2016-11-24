package portalconquest.services;

import classes.CField;
import portalconquest.dao.CCrudServiceBean;
import portalconquest.dao.ICrudService;
import portalconquest.dao.QueryParameter;

import javax.persistence.EntityTransaction;
import javax.ws.rs.*;
import java.util.List;

import static portalconquest.dao.CCrudServiceBean.em;
import static portalconquest.services.CPortalServices.sCrudPortal;

/**
 * Created by Screetts on 28/04/2016.
 */
@Path("/fields")
@Produces("application/json")
@Consumes("application/json")
public class CFieldServices {

    public static ICrudService<CField> sCrudField = new CCrudServiceBean<CField>();

//          ========== TESTS UNITAIRES ============
//
//    public CFieldServices() {
//        if(sCrudField == null)
//            sCrudField = new CCrudServiceBean<CField>();
//    }
//
//    public CFieldServices(ICrudService pCrudField) {
//        this.sCrudField = pCrudField;
//    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public CField getField(@PathParam("id") final int id) {
        return (CField) sCrudField.findWithNamedQuery(CField.FIELD_BY_ID, QueryParameter.with("Pid",id).parameters()).get(0);
    }

    @GET
    @Produces("application/json")
    public static List<CField> getFieldAll() {
        return (List<CField>) sCrudField.findWithNamedQuery(CField.FIELD_ALL);
    }

    @PUT
    @Produces("application/json")
    public static void putField(CField field) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        if ((!field.getFirstPortal().equals(null)) && (!field.getSecondPortal().equals(null)) && (!field.getThirdPortal().equals(null)))
            {
                field.getFirstPortal().addField(field);
                field.getSecondPortal().addField(field);
                field.getThirdPortal().addField(field);
                sCrudPortal.update(field.getFirstPortal());
                sCrudPortal.update(field.getSecondPortal());
                sCrudPortal.update(field.getThirdPortal());
            }
        else
            sCrudField.update(field);
        transac.commit();
    }


    @POST
    @Produces("application/json")
    public static void postField(CField field) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        if ((!field.getFirstPortal().equals(null)) && (!field.getSecondPortal().equals(null)) && (!field.getThirdPortal().equals(null)))
            {
                field.getFirstPortal().addField(field);
                field.getSecondPortal().addField(field);
                field.getThirdPortal().addField(field);
                sCrudPortal.update(field.getFirstPortal());
                sCrudPortal.update(field.getSecondPortal());
                sCrudPortal.update(field.getThirdPortal());
            }
        else
            sCrudField.create(field);
        transac.commit();
    }


    @DELETE
    @Path("/{id}")
    public static void deleteField(@PathParam("id") final int id ) {
        EntityTransaction transac = em.getTransaction();
        transac.begin();
        sCrudField.delete(CField.class, id);
        transac.commit();
    }

}

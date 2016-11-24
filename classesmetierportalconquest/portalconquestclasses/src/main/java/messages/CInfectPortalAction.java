package messages;

import classes.*;
import portalconquest.services.*;
import java.util.ArrayList;
import static portalconquest.services.CInventoryServices.sCrudInventory;
import static portalconquest.services.CLinkServices.deleteLink;
import static portalconquest.services.CPortalServices.sCrudPortal;

/**
 * Created by stephane on 10/06/16.
 */

/**
 * Action's Entity to use virus on one portal.
 */
public class CInfectPortalAction {
    private CPortal mPortal;
    private CInventory mInventory;

    public CPortal getPortal() {
        return mPortal;
    }

    public void setPortal(CPortal mPortal) {
        this.mPortal = mPortal;
    }

    public CInventory getInventory() {
        return mInventory;
    }

    public void setInventory(CInventory mInventory) {
        this.mInventory = mInventory;
    }


    /**
     * Delete links and fields of infected portal.
     */
    public void useVirus(){
        if(mInventory.getQuantity() > 1)
            mInventory.setQuantity(mInventory.getQuantity() - 1);
        else
            mInventory.setQuantity(0);
        sCrudInventory.update(mInventory);

        if(! mPortal.getLinks().equals(new ArrayList<CLink>())){
            for(CLink mLink : mPortal.getLinks())
                deleteLink(mLink.getId());
            mPortal.getLinks().clear();
            mPortal.getFields().clear();
        }

        if(! mPortal.getSlots().equals(new ArrayList<CSlot>()))
            for(CSlot mSlot : mPortal.getSlots()) {
                mSlot.setResonator(null);
                mSlot.setEnergy(0);
                mSlot.setPlayer(null);
                CSlotServices.sCrudSlot.update(mSlot);
            }

        mPortal.getUpgradesList().clear();
        mPortal.setTeam(CTeamServices.getTeam(3));
        mPortal.setLevel(0);
        sCrudPortal.update(mPortal);
    }
}

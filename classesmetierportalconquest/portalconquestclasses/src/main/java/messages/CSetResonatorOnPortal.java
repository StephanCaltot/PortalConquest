package messages;

import classes.*;
import portalconquest.services.CInventoryServices;
import portalconquest.services.CTeamServices;

import java.util.ArrayList;
import java.util.List;

import static portalconquest.services.CInventoryServices.sCrudInventory;
import static portalconquest.services.CLinkServices.deleteLink;
import static portalconquest.services.CPortalServices.sCrudPortal;
import static portalconquest.services.CSlotServices.sCrudSlot;

/**
 * Created by Screetts on 05/06/2016.
 */

/**
 * Action's Entity to set resonator on portal .
 */
public class CSetResonatorOnPortal {

    public final static int BLUE_TEAM    = 1;
    public final static int RED_TEAM     = 2;
    public final static int NEUTRAL_TEAM = 3;

    private int mSlotPosition;
    private CPortal mPortal;
    private CPlayer mPlayer;
    private CResonator mResonator;
    private int mValueToReturn = 0;


    public int getSlotPosition() {
        return mSlotPosition;
    }

    public void setSlotPosition(int mSlotPosition) {
        this.mSlotPosition = mSlotPosition;
    }

    public CPortal getPortal() {
        return mPortal;
    }

    public void setPortal(CPortal mPortal) {
        this.mPortal = mPortal;
    }

    public CPlayer getPlayer() {
        return mPlayer;
    }

    public void setPlayer(CPlayer mPlayer) {
        this.mPlayer = mPlayer;
    }

    public CResonator getResonator() {
        return mResonator;
    }

    public void setResonator(CResonator mResonator) {
        this.mResonator = mResonator;
    }



    /**
     * Change the team of portal set in parameter
     * @param CPortal portal
     * @param int newTeam
     * @return Portal with new Team
     */
    private CPortal changeTeam(CPortal portal, int newTeam) {
        CTeam team = CTeamServices.getTeam(newTeam);
        if (team != null){
            portal.setTeam(team);
        }
        mValueToReturn = 10;
        return portal;
    }

    /**
     * Delete links and fields of portal
     * @param CPortal portal
     * @return Portal whitout links and fields. It's a reset of portal.
     */
    private CPortal deleteLinks(CPortal portal) {
        List<CLink> links = portal.getLinks();
        if (!links.equals(new ArrayList<CLink>())) {
            for (CLink lLink : links ) {
                deleteLink(lLink.getId());
            }
            portal.setLinks(new ArrayList<CLink>());
        }
        return portal;
    }


//    private CPortal deleteFields(CPortal portal) {
//        List<CField> fields = portal.getFields();
//        List<CPortal> otherportals = null;
//        if (!fields.equals(new ArrayList<CField>())) {
//            for (int i = 0; i < fields.size(); i++) {
//                otherportals = fields.get(i).getAllPortals();
//                otherportals.remove(portal);
//                otherportals.get(0).getFields().remove(fields.get(i));
//                otherportals.get(1).getFields().remove(fields.get(i));
//                sCrudPortal.update(otherportals.get(0));
//                sCrudPortal.update(otherportals.get(1));
//                sCrudField.delete(CField.class, fields.get(i).getId());
//            }
//            portal.setFields(new ArrayList<CField>());
//        }
//        return portal;
//    }


    /**
     * Check if the portal has to change team based on the sum of the resonators.
     * @param portal
     * @return Portal updated
     */
    private CPortal portalUpdate(CPortal portal) {
        int blueSum = 0;
        int redSum = 0;
        int i ;
        for ( i = 0; i < portal.getSlots().size(); i++) {
            if (portal.getSlots().get(i).getResonator() != (null)) {
                if (portal.getSlots().get(i).getPlayer().getTeam().getId() == BLUE_TEAM) {
                    blueSum += portal.getSlots().get(i).getResonator().getLevel();
                } else if (portal.getSlots().get(i).getPlayer().getTeam().getId() == RED_TEAM) {
                    redSum += portal.getSlots().get(i).getResonator().getLevel();
                }
            }
        }
        if (redSum > blueSum) {
            portal.setLevel(redSum / 8);
            if (portal.getLevel() == 0)
                portal.setLevel(1);
            if (portal.getTeam().getId() != RED_TEAM) {
                portal = changeTeam(portal, RED_TEAM);
                portal = deleteLinks(portal);
//                portal = deleteFields(portal);
                portal.setUpgradesList(new ArrayList<AUpgrade>());
            }
        } else if (blueSum > redSum && portal.getTeam().getId() != BLUE_TEAM) {
            portal.setLevel(blueSum / 8);
            if (portal.getLevel() == 0)
                portal.setLevel(1);
            if (portal.getTeam().getId() != BLUE_TEAM) {
                portal = changeTeam(portal, BLUE_TEAM);
                portal = deleteLinks(portal);
//                portal = deleteFields(portal);
                portal.setUpgradesList(new ArrayList<AUpgrade>());
            }
        } else if (blueSum == redSum && portal.getTeam().getId() != NEUTRAL_TEAM) {
            portal.setLevel(0);
            if (portal.getTeam().getId() != NEUTRAL_TEAM) {
                portal = changeTeam(portal, NEUTRAL_TEAM);
                portal = deleteLinks(portal);
//                portal = deleteFields(portal);
                portal.setUpgradesList(new ArrayList<AUpgrade>());
            }
        }
        return portal;
    }

    /**
     * Check if it's possible to set player's resonator on portal before update portal and inventory's player.
     * @return int value which is representing all types of possible errors
     */
    public int putResonatorOnPortal() {
        int mValueToReturn = 0;
        CInventory mInventory = CInventoryServices.getInventory(mPlayer.getId(), mResonator.getId());
        CSlot mSlot = mPortal.getSlotByPosition(mSlotPosition);

        if (mSlot.getResonator() != null) {
            if ((mSlot.getResonator().getLevel() < mResonator.getLevel()) && (mResonator.getLevel() <= mPlayer.getLevel())) {

                mSlot.setResonator(mResonator);
                mSlot.setPlayer(mPlayer);
                mSlot.setEnergy(mResonator.getLevel() * 100);

                if (mInventory.getQuantity() > 0) {
                    mInventory.setQuantity(mInventory.getQuantity() - 1);
                }
            } else if (mResonator.getLevel() > mPlayer.getLevel()) {
                mValueToReturn = 1;// "Vous ne pouvez pas poser un résonateur de niveau superieur au votre";
            } else {
                mValueToReturn = 2;//"Le résonateur a un niveau trop faible pour l'amelioration";
            }
        } else {
            if (mResonator.getLevel() <= mPlayer.getLevel()) {
                mSlot.setResonator(mResonator);
                mSlot.setPlayer(mPlayer);
                mSlot.setEnergy(mResonator.getLevel() * 100);

                if (mInventory.getQuantity() > 0) {
                    mInventory.setQuantity(mInventory.getQuantity() - 1);
                }
            } else if (mResonator.getLevel() > mPlayer.getLevel()) {
                mValueToReturn = 3;// "Vous ne pouvez pas poser un résonateur de niveau superieur au votre";
            }
        }

        mPortal = portalUpdate(mPortal);

        sCrudInventory.update(mInventory);
        sCrudSlot.update(mSlot);
        sCrudPortal.update(mPortal);

        return mValueToReturn;
    }
}

package messages;

import classes.*;
import portalconquest.services.CPlayerServices;
import portalconquest.services.CTeamServices;
import portalconquest.utils.CDistanceCalculator;

import java.util.ArrayList;
import java.util.List;

import static portalconquest.services.CInventoryServices.sCrudInventory;
import static portalconquest.services.CLinkServices.deleteLink;
import static portalconquest.services.CPlayerServices.sCrudPlayer;
import static portalconquest.services.CPortalServices.sCrudPortal;
import static portalconquest.services.CSlotServices.sCrudSlot;

/**
 * Created by sorvats on 07/06/2016.
 */


/**
 * Action's Entity for Portals Attacks
 */
public class CAttackAction {

    public final static int BLUE_TEAM    = 1;
    public final static int RED_TEAM     = 2;
    public final static int NEUTRAL_TEAM = 3;

    private int ENERGY_COST = 10;
    private CPlayer mPlayer;
    private CPortal mPortal;
    private CInventory mInventoryOfWeapon;
    private int mValueToReturn = 0;


    public CPlayer getPlayer() {
        return mPlayer;
    }

    public void setPlayer(CPlayer mPlayer) {
        this.mPlayer = mPlayer;
    }

    public CPortal getPortal() {
        return mPortal;
    }

    public void setPortal(CPortal mPortal) {
        this.mPortal = mPortal;
    }

    public CInventory getInventoryOfWeapon() {
        return mInventoryOfWeapon;
    }

    public void setInventoryOfWeapon(CInventory mInventoryOfWeapon) {
        this.mInventoryOfWeapon = mInventoryOfWeapon;
    }


    /**
     * Change the team of portal set in parameter
     * @param portal
     * @param newTeam
     * @return Portal with new Team
     */
    private CPortal changeTeam(CPortal portal, int newTeam) {
        CTeam team = CTeamServices.getTeam(newTeam);
        if (!team.equals(null))
            portal.setTeam(team);
        mValueToReturn = 10;
        return portal;
    }

    /**
     * Delete links and fields of portal
     * @param portal
     * @return
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
     * Check if portal's attack is possible, if at least one weapon available and update portal, inventory and player.
     * @return int value which is representing all types of possible errors
     */
    public int attack() {
        int mReceivedDamages = 0;
        int mAttackedNumber = 0;
        int mDamages;
        List<CSlot> slots = mPortal.getSlots();
//        if(CDistanceCalculator.getINSTANCE().isPortalInPlayerRange(mPlayer,mPortal)) {
            if (mPlayer.getEnergy() >= ENERGY_COST) {
                if (mInventoryOfWeapon.getItem().getClass().equals(CWeapon.class)) {
                    if (((CWeapon) mInventoryOfWeapon.getItem()).getLevel() <= mPlayer.getLevel()) {
                        mDamages = ((CWeapon) mInventoryOfWeapon.getItem()).getAttackValue();
                        if (!mPortal.getUpgradesList().equals(new ArrayList<AUpgrade>()))
                            for (AUpgrade upgrade : mPortal.getUpgradesList()) {
                                if (upgrade.getClass().equals(CShield.class)) {
                                    mDamages -= ((CShield) upgrade).getDefenseValue();
                                } else if (upgrade.getClass().equals(CTurret.class)) {
                                    mReceivedDamages += ((CTurret) upgrade).getAttackValue();
                                }
                            }
                        if (!slots.equals(new ArrayList<CSlot>()))
                            for (int i = 0; i < slots.size(); i++) {
                                if (slots.get(i).getResonator() != null) {
                                    if (slots.get(i).getPlayer().getTeam().getId() != mPlayer.getTeam().getId()) {
                                        slots.get(i).setEnergy(slots.get(i).getEnergy() - mDamages);
                                        mAttackedNumber += 1;
                                        if (slots.get(i).getEnergy() <= 0) {
                                            slots.get(i).setResonator(null);
                                            slots.get(i).setEnergy(0);
                                            slots.get(i).setPlayer(null);
                                        }
                                    }
                                }
                            }
                        if (mAttackedNumber == 0)
                            mValueToReturn = 1;// "Il n'y a pas de résonateurs ennemis";
                        else if (mPlayer.getEnergy() - ENERGY_COST - mReceivedDamages < 0)
                            mValueToReturn = 2;//"le joueur n'a pas assez d'energie";
                        else {
                            mPlayer.setEnergy(mPlayer.getEnergy() - ENERGY_COST - mReceivedDamages);
                            mPlayer.setActionPoints(mPlayer.getActionPoints()+(mDamages*mAttackedNumber));
                            mInventoryOfWeapon.setQuantity(mInventoryOfWeapon.getQuantity() - 1);

                            for (CPlayer lPlayer : CPlayerServices.getPlayerByStatus())
                                if (lPlayer.getTeam() != mPlayer.getTeam() && CDistanceCalculator.getINSTANCE().isPlayerInPortalRange(lPlayer, mPortal)) {
                                    lPlayer.setEnergy(lPlayer.getEnergy() - mDamages);
                                    if (lPlayer.getEnergy() < 0)
                                        lPlayer.setEnergy(0);
                                    sCrudPlayer.update(lPlayer);
                                }

                            mPortal = portalUpdate(mPortal);
                            sCrudPortal.update(mPortal);
                            sCrudInventory.update(mInventoryOfWeapon);
                            sCrudPlayer.update(mPlayer);
                            for (CSlot slot : slots)
                                sCrudSlot.update(slot);
                            mValueToReturn = 0;//"Attaque reussie";
                        }
                    } else
                        mValueToReturn = 3 ;//"Le joueur n'a pas le niveau pour utiliser cette arme";
                } else
                    mValueToReturn = 4 ;//"L'objet n'est pas une arme";
            } else
                mValueToReturn = 5;//"Le joueur n'a pas assez d'energie";
//        } else
//            mValueToReturn = 6;// "le portail n'est pas dans le champ du joueur";

        System.out.println("Attack return: " + mValueToReturn);
        return mValueToReturn;
    }
}
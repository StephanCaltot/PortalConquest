package messages;

import classes.*;
import portalconquest.services.CItemServices;

import java.util.*;

import static portalconquest.services.CInventoryServices.getInventory;
import static portalconquest.services.CInventoryServices.sCrudInventory;
import static portalconquest.services.CPlayerServices.sCrudPlayer;

/**
 * Created by Informatique on 08/06/2016.
 */


/**
 * Action's Entity for Portals Hacks
 */
public class CHackPortalAction {
    private static final String VIRUS = "CVirus" ;
    private static CPlayer mPlayer;
    private static CPortal mPortal;

    private static final String WEAPON      = "CWeapon";
    private static final String RESONATOR   = "CResonator";
    private static final String CONSUMABLE  = "CConsumable";
    private static final String SHIELD      = "CShield";
    private static final String TURRET      = "CTurret";
    private static final String RANGE_BOOST = "CRangeBoost";
    private static final String FREQUENCY_HACK_BOOST = "CFrequencyHackBoost";


    public CPortal getPortal() {
        return mPortal;
    }

    public void setPortal(CPortal mPortal) {
        CHackPortalAction.mPortal = mPortal;
    }

    public CPlayer getPlayer() {
        return mPlayer;
    }

    public void setPlayer(CPlayer mPlayer) {
        this.mPlayer = mPlayer;
    }



    /**
     * Update player's inventory with many items got on differents types of portal.
     * @return List of items's toString
     */
    public List<String> hackPortal() {
        System.out.println("HACK PORTAL ACTION");
        List<String> mValuesToReturn = new ArrayList<String>();
        List<AItem> mListAItem = new ArrayList<AItem>();
        mListAItem.addAll(CItemServices.getItemsAll());
        CInventory mInventory = new CInventory();

        // Liste améliorations
        Map<Integer, AItem> mMapItem = new Hashtable();
        // List items
        Map<Integer, AItem> mMapUpgrade = new Hashtable();

        String[] split;

        for (AItem lItem : mListAItem) {
            split = lItem.getClass().toString().split("\\.");
            if (split[split.length - 1].equals(WEAPON)) {
                CWeapon mWeapon = new CWeapon();
                mWeapon = (CWeapon) CItemServices.getItems(lItem.getId());
                if (mWeapon.getLevel() >= mPlayer.getLevel() - 2 && mWeapon.getLevel() <= mPlayer.getLevel() + 2)
                    mMapItem.put(mMapItem.size(), mWeapon);

            } else if (split[split.length - 1].equals(RESONATOR)) {
                CResonator mResonator = new CResonator();
                mResonator = (CResonator) CItemServices.getItems(lItem.getId());
                if (mResonator.getLevel() >= mPlayer.getLevel() - 2 && mResonator.getLevel() <= mPlayer.getLevel() + 2)
                    mMapItem.put(mMapItem.size(), mResonator);

            }  else if (split[split.length - 1].equals(VIRUS)) {
                CVirus mVirus = new CVirus();
                mVirus = (CVirus) CItemServices.getItems(lItem.getId());
                mMapItem.put(mMapItem.size(), mVirus);

        } else if (split[split.length - 1].equals(CONSUMABLE)) {
                CConsumable mConsumable = new CConsumable();
                mConsumable = (CConsumable) CItemServices.getItems(lItem.getId());
                if (mConsumable.getLevel() >= mPlayer.getLevel() - 2 && mConsumable.getLevel() <= mPlayer.getLevel() + 2)
                    mMapItem.put(mMapItem.size(), mConsumable);

            } else if (split[split.length - 1].equals(SHIELD)) {
                CShield mShield = new CShield();
                mShield = (CShield) CItemServices.getItems(lItem.getId());
                mMapUpgrade.put(mMapUpgrade.size(), mShield);

            } else if (split[split.length - 1].equals(TURRET)) {
                CTurret mTurret = new CTurret();
                mTurret = (CTurret) CItemServices.getItems(lItem.getId());
                mMapUpgrade.put(mMapUpgrade.size(), mTurret);

            } else if (split[split.length - 1].equals(RANGE_BOOST)) {
                CRangeBoost mRangeBoost = new CRangeBoost();
                mRangeBoost = (CRangeBoost) CItemServices.getItems(lItem.getId());
                mMapUpgrade.put(mMapUpgrade.size(), mRangeBoost);

            } else if (split[split.length - 1].equals(FREQUENCY_HACK_BOOST)) {
                CFrequencyHackBoost mFrequence = new CFrequencyHackBoost();
                mFrequence = (CFrequencyHackBoost) CItemServices.getItems(lItem.getId());
                mMapUpgrade.put(mMapUpgrade.size(), mFrequence);

            }
        }

        if (mPortal.getTeam().getId() == (mPlayer.getTeam().getId())) {
            System.out.println("portail allié");
            Random mRandom = new Random();
            int mNbRandom;
            mNbRandom = mRandom.nextInt(mMapItem.size() - 1);
            CInventory mInventoryItem = new CInventory();
            try {
                mInventoryItem = getInventory(mPlayer.getId(), mMapItem.get(mNbRandom).getId());
                mInventoryItem.setQuantity(mInventoryItem.getQuantity() + 1);
                mInventoryItem.setPlayer(mPlayer);
            } catch (Exception e) {
                mInventoryItem.setItem(mMapItem.get(mNbRandom));
                mInventoryItem.setQuantity(1);
                mInventoryItem.setPlayer(mPlayer);
            }
            mValuesToReturn.add(mInventoryItem.toString());
            sCrudInventory.update(mInventoryItem);
            mNbRandom = mRandom.nextInt(mMapUpgrade.size() - 1);
            try {
                mInventoryItem = getInventory(mPlayer.getId(), mMapUpgrade.get(mNbRandom).getId());
                mInventoryItem.setQuantity(mInventoryItem.getQuantity() + 1);
            } catch (Exception e) {
                mInventoryItem.setItem(mMapUpgrade.get(mNbRandom));
                mInventoryItem.setQuantity(1);
                mInventoryItem.setPlayer(mPlayer);
            }
            mValuesToReturn.add(mInventoryItem.toString());
            sCrudInventory.update(mInventoryItem);
            try{
                mInventoryItem = getInventory(mPlayer.getId(),mPortal.getKey().getId());
                System.out.println("l inventaire de cle + portail -> " + mInventory);

            }catch (Exception e){
                System.out.println("je n'ai pas la cle");
                mInventoryItem.setItem(mPortal.getKey());
                mInventoryItem.setQuantity(1);
                mInventoryItem.setPlayer(mPlayer);
                mValuesToReturn.add(mInventoryItem.toString());
                System.out.println("j'ajoute cet inventaire -> " + mValuesToReturn);
                System.out.println("PUTAIN DE PORTAIL"+mPortal.toString());
                sCrudInventory.update(mInventoryItem);
            }

        } else {
            int mNbRandom;
            Random mRandom = new Random();
            mNbRandom = mRandom.nextInt(1);
            if (mNbRandom == 0) {
                mNbRandom = mRandom.nextInt(mMapItem.size() - 1);
                try {
                    mInventory = getInventory(mPlayer.getId(), mMapItem.get(mNbRandom).getId());
                    mInventory.setQuantity(mInventory.getQuantity() + 1);
                    mInventory.setPlayer(mPlayer);
                } catch (Exception e) {
                    mInventory.setItem(mMapItem.get(mNbRandom));
                    mInventory.setQuantity(1);
                    mInventory.setPlayer(mPlayer);
                }
            } else {
                mNbRandom = mRandom.nextInt(mMapUpgrade.size() - 1);
                try {
                    mInventory = getInventory(mPlayer.getId(), mMapUpgrade.get(mNbRandom).getId());
                    mInventory.setQuantity(mInventory.getQuantity() + 1);
                    mInventory.setPlayer(mPlayer);
                } catch (Exception e) {
                    mInventory.setItem(mMapUpgrade.get(mNbRandom));
                    mInventory.setQuantity(1);
                    mInventory.setPlayer(mPlayer);
                }
            }
            System.out.println(mInventory.toString());

            if(mPortal.getTeam().getId() == 3)
                mPlayer.setActionPoints(mPlayer.getActionPoints() + 50);
            else
                mPlayer.setActionPoints(mPlayer.getActionPoints() + 100);
            mValuesToReturn.add(mInventory.toString());
            sCrudInventory.update(mInventory);
            sCrudPlayer.update(mPlayer);
        }
        return mValuesToReturn;
    }


}

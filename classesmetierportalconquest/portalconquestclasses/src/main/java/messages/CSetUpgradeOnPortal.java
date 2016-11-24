package messages;

import classes.AUpgrade;
import classes.CInventory;
import classes.CPlayer;
import classes.CPortal;
import portalconquest.services.CInventoryServices;

import java.util.ArrayList;
import java.util.List;

import static portalconquest.services.CInventoryServices.sCrudInventory;
import static portalconquest.services.CPortalServices.sCrudPortal;

/**
 * Created by Screetts on 07/06/2016.
 */

/**
 * Action's Entity to set upgrade on portal .
 */
public class CSetUpgradeOnPortal {

    private AUpgrade mUpgrade;
    private CPortal mPortal;
    private CPlayer mPlayer;


    public AUpgrade getUpgrade() {
        return mUpgrade;
    }

    public void setUpgrade(AUpgrade mUpgrade) {
        this.mUpgrade = mUpgrade;
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



    /**
     * Check if it's possible to set player's upgrade on portal before update portal and inventory's player.
     * @return int value which is representing all types of possible errors
     */
    public String putUpgradeOnPortal() {
        String mValueToReturn = "";
        List<AUpgrade> currentUpgradeList = mPortal.getUpgradesList();
        CInventory mInventory = CInventoryServices.getInventory(mPlayer.getId(), mUpgrade.getId());

        if (!currentUpgradeList.equals(new ArrayList<AUpgrade>())) {
            for (AUpgrade upgrade : currentUpgradeList) {
                if (upgrade.equals(mUpgrade)) {
                    mValueToReturn = "Vous ne pouvez poser deux fois la même amelioration";
                }
            }
        }
        if (mValueToReturn.equals("")) {
            if (mInventory.getQuantity() > 0) {
                mPortal.addUpgrade(mUpgrade);
                mInventory.setQuantity(mInventory.getQuantity() - 1);
                sCrudInventory.update(mInventory);
                sCrudPortal.update(mPortal);
            }
        }
        return mValueToReturn;
    }

}

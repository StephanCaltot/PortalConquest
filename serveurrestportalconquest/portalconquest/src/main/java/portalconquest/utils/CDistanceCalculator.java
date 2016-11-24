package portalconquest.utils;

import classes.*;
import com.google.maps.model.LatLng;

/**
 * Created by stephane on 26/05/16.
 */


/**
 * Class contains all methods based on distance .
 */
public class CDistanceCalculator {

    private static CDistanceCalculator INSTANCE = null;

    public static CDistanceCalculator getINSTANCE(){
        if(INSTANCE == null)
            INSTANCE = new CDistanceCalculator();
        return INSTANCE;
    }

    private CDistanceCalculator(){};

    public float distanceBetween(LatLng pL1, LatLng pL2){

        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(pL2.lat-pL1.lat);
        double dLng = Math.toRadians(pL2.lng-pL1.lng);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(pL1.lat)) * Math.cos(Math.toRadians(pL2.lat)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    /**
     * Test if portal is in player range.
     * @param pPlayer
     * @param pPortal
     * @return boolean
     */
    public boolean isPortalInPlayerRange(CPlayer pPlayer, CPortal pPortal){
        return distanceBetween(
                new LatLng(pPlayer.getLatitude(), pPlayer.getLongitude()),
                new LatLng(pPortal.getLatitude(), pPortal.getLongitude()))
                <= getPlayerRange(pPlayer);
    }

    /**
     * Test if mapItem is in player range.
     * @param pPlayer
     * @param pMapItem
     * @return boolean
     */
    public boolean isMapItemInPlayerRange(CPlayer pPlayer, CMapItem pMapItem){
        return distanceBetween(
                new LatLng(pPlayer.getLatitude(), pPlayer.getLongitude()),
                new LatLng(pMapItem.getLatitude(), pMapItem.getLongitude()))
                <= getPlayerRange(pPlayer);
    }

    /**
     * Test if player is in portal range.
     * @param pPlayer
     * @param pPortal
     * @return boolean
     */
    public boolean isPlayerInPortalRange(CPlayer pPlayer, CPortal pPortal){
        return distanceBetween(
                new LatLng(pPlayer.getLatitude(), pPlayer.getLongitude()),
                new LatLng(pPortal.getLatitude(), pPortal.getLongitude()))
                <= getPortalRange(pPortal);
    }

    /**
     * Test if player is near to another player .
     * @param pSourcePlayer
     * @param pTargetPlayer
     * @return
     */
    public boolean isPlayerInPlayerRange(CPlayer pSourcePlayer, CPlayer pTargetPlayer){
        return distanceBetween(
                new LatLng(pSourcePlayer.getLatitude(), pSourcePlayer.getLongitude()),
                new LatLng(pTargetPlayer.getLatitude(), pTargetPlayer.getLongitude()))
                <= getPlayerRange(pSourcePlayer);
    }

    /**
     * Test if portal is in range of another portal.
     * @param pSourcePortal
     * @param pTargetPortal
     * @return
     */
    public boolean isPortalInPortalRange(CPortal pSourcePortal, CPortal pTargetPortal){
        return distanceBetween(
                new LatLng(pSourcePortal.getLatitude(), pSourcePortal.getLongitude()),
                new LatLng(pTargetPortal.getLatitude(), pTargetPortal.getLongitude()))
                <= getPortalRange(pSourcePortal);
    }

    /**
     * Return the range of player based on his level .
     * @param pPlayer
     * @return int value of range
     */
    public int getPlayerRange(CPlayer pPlayer){return 12+3*pPlayer.getLevel();}

    /**
     * Return the range of portal based on his level .
     * @param pPortal
     * @return
     */
    public int getPortalRange(CPortal pPortal){
        int mTotal = pPortal.getLevel()*20;
        for(AUpgrade mUpgrade : pPortal.getUpgradesList())
            if(mUpgrade.getClass().equals(CRangeBoost.class))
                mTotal += ((CRangeBoost)mUpgrade).getRangeValue();
        return mTotal;
    }
}

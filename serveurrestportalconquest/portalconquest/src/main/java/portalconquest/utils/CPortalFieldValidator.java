package portalconquest.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import classes.CField;
import classes.CLink;
import classes.CPortal;
import com.google.maps.model.LatLng;
import portalconquest.services.CFieldServices;

/**
 * Created by stephane on 10/05/16.
 */

/**
 * Class contains all methods based on portals, fields and links modifications .
 */
public class CPortalFieldValidator {
    private static CPortalFieldValidator INSTANCE = null;

    public static CPortalFieldValidator getINSTANCE(){
        if(INSTANCE==null)
            INSTANCE = new CPortalFieldValidator();
        return INSTANCE;
    }

    private CPortalFieldValidator(){}


    /**
     * Check if 2 links make an intersection before create link.
     * @param pLinkA
     * @param pLinkB
     * @return boolean
     */
    public Boolean isIntersectedLink(CLink pLinkA, CLink pLinkB) {

        Double mDet1, mDet2, mDet3, mDet4;
        Boolean mVerify1 = false, mVerify2 = false;

        if (pLinkA != null && pLinkB != null) {

            mDet1 = determinant(pLinkB.getFirstPortal(), pLinkA.getFirstPortal(), pLinkA.getSecondPortal());
            mDet2 = determinant(pLinkB.getSecondPortal(), pLinkA.getFirstPortal(), pLinkA.getSecondPortal());

            mDet3 = determinant(pLinkA.getFirstPortal(), pLinkB.getFirstPortal(), pLinkB.getSecondPortal());
            mDet4 = determinant(pLinkA.getSecondPortal(), pLinkB.getFirstPortal(), pLinkB.getSecondPortal());

            if (mDet1*mDet2 < 0)
                    mVerify1 = true;
            if (mDet3*mDet4 < 0)
                    mVerify2 = true;

        }

        return mVerify1 && mVerify2;
    }

    /**
     * Check if the link make a new field based on all links in BDD .
     * @param pLink
     * @return List of all fields which had to be created.
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException
     */
    public List<CField> createFieldsIfPossible(CLink pLink) throws InterruptedException, ExecutionException, IOException {

        List<CField> fields = new ArrayList<CField>();

        for(CLink mLinkPortalA : pLink.getFirstPortal().getLinks())
            for(CPortal mALinkedPortal :mLinkPortalA.getAllPortals())
                if(!mALinkedPortal.equals(pLink.getFirstPortal()) && !mALinkedPortal.equals(pLink.getSecondPortal()))
                    for(CLink mLinkPortalB : pLink.getSecondPortal().getLinks())
                        for(CPortal mBLinkedPortal : mLinkPortalB.getAllPortals())
                            if(!mBLinkedPortal.equals(pLink.getFirstPortal()) && !mBLinkedPortal.equals(pLink.getFirstPortal()))
                                if(mALinkedPortal.equals(mBLinkedPortal)) {
                                    CField field = new CField();
                                    field.setFirstPortal(mALinkedPortal);
                                    field.setSecondPortal(pLink.getFirstPortal());
                                    field.setThirdPortal(pLink.getSecondPortal());
                                    field.setTeam(pLink.getFirstPortal().getTeam());
                                    if(!isFieldContained(field)){
                                        fields.add(field);
                                    }
                                }
        return fields;
    }

    /**
     * Check if one field is contained before created new field
     * @param pField
     * @return boolean
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException
     */
    public boolean isFieldContained(CField pField) throws InterruptedException, ExecutionException, IOException {
        boolean isContained = false;
        if (CFieldServices.getFieldAll() != null){
            for(CField mField : CFieldServices.getFieldAll())
                    for (CPortal mPortalA : pField.getAllPortals())
                        for(CPortal mPortalB : mField.getAllPortals())
                            if (mPortalA != mPortalB && PointInTriangle(mPortalA, mField.getFirstPortal(), mField.getSecondPortal(), mField.getThirdPortal())) {
                                isContained = true;
                            }
            }

        return isContained;
    }

    private double sign (CPortal pPortal1, CPortal pPortal2, CPortal pPortal3){
        return  (pPortal1.getLatitude() - pPortal3.getLatitude()) *
                (pPortal2.getLongitude() - pPortal3.getLongitude()) - (pPortal2.getLatitude() - pPortal3.getLatitude()) *
                (pPortal1.getLongitude() - pPortal3.getLongitude());
    }

    private boolean PointInTriangle (CPortal pPortal, CPortal pFieldPortal1, CPortal pFieldPortal2, CPortal pFieldPortal3) {
        boolean b1, b2, b3;

        b1 = sign(pPortal, pFieldPortal1, pFieldPortal2) < 0;
        b2 = sign(pPortal, pFieldPortal2, pFieldPortal3) < 0;
        b3 = sign(pPortal, pFieldPortal3, pFieldPortal1) < 0;

        return ((b1 == b2) && (b2 == b3));
    }

    private Double determinant(CPortal pPortal1, CPortal pPortal2, CPortal pPortal3) {

        Double total1 = 0d, total2 = 0d;

        if (pPortal1 != null && pPortal2 != null && pPortal3 != null) {

            total1 = pPortal1.getLatitude() * pPortal2.getLongitude();
            total1 += pPortal1.getLongitude() * pPortal3.getLatitude();
            total1 += pPortal2.getLatitude() * pPortal3.getLongitude();

            total2 = pPortal3.getLatitude() * pPortal2.getLongitude();
            total2 += pPortal3.getLongitude() * pPortal1.getLatitude();
            total2 += pPortal2.getLatitude() * pPortal1.getLongitude();
        }

        return total1 - total2;
    }

//    public Boolean isClockWiseRotation(CPortal pPortal1, CPortal pPortal2, CPortal pPortal3) {
//
//        if (pPortal1 != null && pPortal2 != null && pPortal3 != null) {
//
//            if (pPortal1.getLatitude() < pPortal2.getLatitude() && pPortal1.getLongitude() < pPortal2.getLongitude())
//                if (determinant(pPortal1, pPortal2, pPortal3) > 0)
//                    return true;
//                else
//                    return false;
//
//            if (pPortal1.getLatitude() > pPortal2.getLatitude() && pPortal1.getLongitude() < pPortal2.getLongitude())
//                if (determinant(pPortal1, pPortal2, pPortal3) > 0)
//                    return true;
//                else
//                    return false;
//
//            if (pPortal1.getLatitude() > pPortal2.getLatitude() && pPortal1.getLongitude() > pPortal2.getLongitude())
//                if (determinant(pPortal1, pPortal2, pPortal3) > 0)
//                    return true;
//                else
//                    return false;
//
//            if (pPortal1.getLatitude() < pPortal2.getLatitude() && pPortal1.getLongitude() > pPortal2.getLongitude())
//                if (determinant(pPortal1, pPortal2, pPortal3) > 0)
//                    return true;
//                else
//                    return false;
//        }
//        return null;
//    }

    /**
     * This function calcul the dimension of one field .
     * @param pField
     * @return the value of new field
     */
    public int getScore(CField pField){
        float mFirstSide, mSecondSide, mThirdSide, s, mArea = 0;

        mFirstSide = CDistanceCalculator.getINSTANCE().distanceBetween(
                new LatLng(pField.getFirstPortal().getLatitude(), pField.getFirstPortal().getLongitude()),
                new LatLng(pField.getSecondPortal().getLatitude(), pField.getSecondPortal().getLongitude()));

        mSecondSide = CDistanceCalculator.getINSTANCE().distanceBetween(
                new LatLng(pField.getSecondPortal().getLatitude(), pField.getSecondPortal().getLongitude()),
                new LatLng(pField.getThirdPortal().getLatitude(), pField.getThirdPortal().getLongitude()));

        mThirdSide = CDistanceCalculator.getINSTANCE().distanceBetween(
                new LatLng(pField.getThirdPortal().getLatitude(), pField.getThirdPortal().getLongitude()),
                new LatLng(pField.getFirstPortal().getLatitude(), pField.getFirstPortal().getLongitude()));

        // calcul de s
        s = (mFirstSide + mSecondSide + mThirdSide) / 2;

        // calcul de l'aire
        mArea += (float) Math.sqrt(s * (s - mFirstSide) * (s - mSecondSide) * (s - mThirdSide));

        return (int)mArea/3;
    }


}


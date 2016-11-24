package messages;

import classes.CField;
import classes.CKey;
import classes.CLink;
import classes.CPortal;
import portalconquest.utils.CDistanceCalculator;
import portalconquest.utils.CPortalFieldValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.eclipse.persistence.sessions.server.ConnectionPolicy.ExclusiveMode.Transactional;
import static portalconquest.services.CFieldServices.sCrudField;
import static portalconquest.services.CLinkServices.getLinkAll;
import static portalconquest.services.CPortalServices.sCrudPortal;
import static portalconquest.services.CTeamServices.sCrudTeam;

/**
 * Created by Screetts on 05/06/2016.
 */


/**
 * Action's Entity to linked 2 portals.
 */
public class CSetLink {
    private CPortal mPortal;
    private CKey mKey;
    int mValueToReturn = 0;


    public CPortal getPortal() {
        return mPortal;
    }

    public void setPortal(CPortal mPortal) {
        this.mPortal = mPortal;
    }

    public CKey getKey() {
        return mKey;
    }

    public void setKey(CKey mKey) {
        this.mKey = mKey;
    }


    /**
     * Fuction which is setting link between 2 portals, and field if it's necessary .
     * @return int (1) if only one link set and (2) if a field is also created .
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException
     */
    public int setLink() throws InterruptedException, ExecutionException, IOException {
        CPortalFieldValidator mPortalFieldValidator = CPortalFieldValidator.getINSTANCE();
        boolean mIsValide = true;

        CLink mLink = new CLink();
        mLink.setFirstPortal(mPortal);
        mLink.setSecondPortal(mKey.getPortal());

        if (!CDistanceCalculator.getINSTANCE().isPortalInPortalRange(mLink.getFirstPortal(), mLink.getSecondPortal()) ||
                !CDistanceCalculator.getINSTANCE().isPortalInPortalRange(mLink.getSecondPortal(), mLink.getFirstPortal()))
            mIsValide = false;

        for (CLink mOtherLink : getLinkAll())
            if (mPortalFieldValidator.isIntersectedLink(mLink, mOtherLink))
                mIsValide = false;

        mIsValide = !getLinkAll().contains(mLink);

        if (mIsValide) {
            List<CField> mFields = mPortalFieldValidator.createFieldsIfPossible(mLink);
            if (!mFields.equals(new ArrayList<CField>())) {
                for (CField mField : mFields)
                    if ((!mField.getFirstPortal().equals(null)) && (!mField.getSecondPortal().equals(null)) && (!mField.getThirdPortal().equals(null))) {

                        mLink.getFirstPortal().addLink(mLink);
                        mLink.getSecondPortal().addLink(mLink);
                        sCrudPortal.update(mLink.getFirstPortal());
                        sCrudPortal.update(mLink.getSecondPortal());

                        if (!mField.getAllPortals().equals(new ArrayList<CPortal>())) {
                            for (CPortal mPortal : mField.getAllPortals()) {
                                mPortal.addField(mField);
                                sCrudPortal.update(mPortal);
                            }
                        } else
                            sCrudField.update(mField);

                        mField.getTeam().setScore(mField.getTeam().getScore() + CPortalFieldValidator.getINSTANCE().getScore(mField));
                        sCrudTeam.update(mField.getTeam());
                        mValueToReturn = 2;
                    }
            } else if ((!mLink.getFirstPortal().equals(null)) && (!mLink.getSecondPortal().equals(null))) {
                mLink.getFirstPortal().addLink(mLink);
                mLink.getSecondPortal().addLink(mLink);
                sCrudPortal.update(mLink.getFirstPortal());
                sCrudPortal.update(mLink.getSecondPortal());
                mValueToReturn = 1;
            }
        }
        return mValueToReturn;
    }
}

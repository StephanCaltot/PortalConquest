package messages;

import classes.CPlayer;

import static portalconquest.services.CPlayerServices.sCrudPlayer;

/**
 * Created by stephane on 07/06/16.
 */

/**
 * Action's Entity for cheats code
 */
public class CPasscodeAction {

    private String mPasscode;
    private CPlayer mPlayer;


    public String getPasscode() {
        return mPasscode;
    }

    public void setPasscode(String mPasscode) {
        this.mPasscode = mPasscode;
    }

    public CPlayer getPlayer() {
        return mPlayer;
    }

    public void setPlayer(CPlayer mPlayer) {
        this.mPlayer = mPlayer;
    }


    /**
     * Update player with 4 differents cheats code .
     */
    public void usePasscode () {

        mPasscode = mPasscode.toLowerCase();

        if (mPasscode.equals("pit-stop") || mPasscode.equals("pitstop") || mPasscode.equals("pit stop")) {
            if (mPlayer.getEnergy() + 100 <= mPlayer.getLevel() * 100)
                mPlayer.setEnergy(mPlayer.getEnergy() + 100);
            else
                mPlayer.setEnergy(mPlayer.getLevel() * 100);
        } else if (mPasscode.equals("may the force be with you") || mPasscode.equals("maytheforcebewithyou") || mPasscode.equals("mtfbwy")) {
            if (mPlayer.getLevel() < 8) {
                mPlayer.setLevel(mPlayer.getLevel() + 1);
                mPlayer.setActionPoints(0);
            }
        } else if (mPasscode.equals("machine learning") || mPasscode.equals("machine-learning") || mPasscode.equals("machinelearning")) {
            mPlayer.addActionPoints(1000);
        }

        sCrudPlayer.update(mPlayer);
    }

}

package websocketmessages;

import classes.CPlayer;
import jsoncoder.AJSONCoder;

import java.io.Serializable;

/**
 * Created by ltonnet637 on 07/06/16.
 */
public abstract class AMessage implements Serializable {
    public final static class CMessageCoder extends AJSONCoder<AMessage> {}

    protected CPlayer mPlayer;


    public CPlayer getPlayer() {
        return mPlayer;
    }


    public void setPlayer(CPlayer mPlayer) {
        this.mPlayer = mPlayer;
    }
}

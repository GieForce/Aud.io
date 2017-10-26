package aud.io;

import java.io.Serializable;

public class TemporaryUser extends User implements Serializable{

    /**
     * New temporary user
     * @param nickname Nickname the User wants to use
     */
    public TemporaryUser(String nickname) {
        super(nickname);
    }
}

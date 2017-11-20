package aud.io;

import java.io.Serializable;

public abstract class User implements Serializable  {
    private String nickname;

    /**
     * New User
     * @param nickname Nickname User wants to use
     */
    public User(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

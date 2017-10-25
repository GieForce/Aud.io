package aud.io;

public abstract class User {
    private String nickname;

    /**
     * New User
     * @param nickname Nickname User wants to use
     */
    public User(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return nickname;
    }
}

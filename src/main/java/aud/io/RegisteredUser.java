package aud.io;

import java.util.Objects;

public class RegisteredUser extends User {
    private String email;
    private String password;

    /**
     * Create a Registered User
     * @param email Email of the User
     * @param nickname Nickname the User wants to use
     * @param password Password of the User
     */
    public RegisteredUser(String email, String nickname, String password) {
        super(nickname);
        this.email = email;
        this.password = password;
    }

    /**
     * Check if login details are correct
     * @param email Email of the User
     * @param password Password of the User
     * @return true wether or not details are correct
     */
    public boolean checkLogin(String email, String password) {
        return Objects.equals(this.email, email) && Objects.equals(this.password, password);
    }
}

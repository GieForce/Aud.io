package aud.io;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.io.Serializable;
import java.util.Objects;

public class RegisteredUser extends User implements Serializable{

    //MongoDB van User
    private static final String MONGO_EMAIL = "Email";
    private static final String MONGO_PASSWORD = "Password";
    private static final String MONGO_NICKNAME = "Nickname";

    private String email;
    private String password;

    @MongoObjectId
    private String _id;


    @JsonCreator
    public RegisteredUser(@JsonProperty(MONGO_EMAIL) String email,
                @JsonProperty(MONGO_PASSWORD) String password,
                @JsonProperty(MONGO_NICKNAME) String nickname)
    {
        super(nickname);
        this.email   = email;
        this.password = password;
    }

    public String getMongoPassword() {
        return password;
    }

    /**
     * Create a Registered User
     * @param email Email of the User
     * @param nickname Nickname the User wants to use
     * @param password Password of the User
     */
    /*public RegisteredUser(String email, String nickname, String password) {
        super(nickname);
        this.email = email;
        this.password = password;
    }*/


    /**
     * Set email
     * @param email email of the user
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Get email
     * @return email of the user
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * Check if login details are correct
     * @param email Email of the User
     * @param password Password of the User
     * @return true wether or not details are correct
     */
    public boolean checkLogin(String email, String password) {
        return this.email.equals(email)&& this.password.equals(password);
    }
}

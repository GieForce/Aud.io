package Hashing;

import org.mindrot.jbcrypt.BCrypt;

public class Hash {

    public String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public boolean checkPass(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword)) {
            System.out.println("The password matches.");
            return true;
        }
        else
            System.out.println("The password does not match.");
        return false;
    }


}

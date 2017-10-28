package Demo.Shared;

import java.io.Serializable;

public class RegisteredUser extends User implements Serializable{

    private static String[] existingUsers = {"Nick","Ruud","Stefan"};

    public static boolean loginUser(String name){

        for (String user : existingUsers){
            if (user.equals(name)) return true;
        }

        return false;
    }

    public RegisteredUser(String name) {
        super(name);
    }
}

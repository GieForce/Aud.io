package Demo.Shared;

import java.io.Serializable;

public class TemporaryUser extends User implements Serializable{
    public TemporaryUser(String name) {
        super(name);
    }
}

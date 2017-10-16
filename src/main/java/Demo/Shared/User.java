package Demo.Shared;

import java.io.Serializable;

public abstract class User implements Serializable{

    protected String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

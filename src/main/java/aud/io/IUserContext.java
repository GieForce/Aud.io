package aud.io;

public interface IUserContext {

    User loginUser(String name, String password);

    boolean createUser(String name, String nickname, String password);

}

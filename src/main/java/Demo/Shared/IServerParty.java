package Demo.Shared;

import java.util.List;

public interface IServerParty {

    void addUser(User user);

    List<User> getUsers();

    RegisteredUser getHost();

    void addSong(String song);

    String getPartyKey();

    void setPartyMessage(String message);
}

package aud.io;

import java.util.List;

public interface IVotableContext {

    List<Votable> getSongsWithSearchterm(String searchterm);
}

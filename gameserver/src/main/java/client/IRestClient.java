package client;

import model.Token;
import model.User;

import java.util.List;
import java.util.Set;

public interface IRestClient {
    boolean register(String user, String password);
    Long login(String user, String password);
    boolean logout(Long token);
    boolean changeName(Long token, String newName);
    List getUsers(Long token);
}

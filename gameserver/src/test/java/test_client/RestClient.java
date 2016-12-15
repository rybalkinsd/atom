package test_client;

import server.model.user.User;

import java.util.List;

public interface RestClient {
    boolean register(String user, String password);
    Long login(String user, String password);
    boolean logout(Long token);
    boolean changeName(Long token, String newName);
    List<User> getUsers();
}

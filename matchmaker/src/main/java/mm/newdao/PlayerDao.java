package mm.newdao;

import org.springframework.data.repository.CrudRepository;

public interface PlayerDao extends CrudRepository<Player, Integer> {
    /**
     * Return the user having the passed login or null if no player is found.
     *
     * @param login the player login.
     */
    Player findByLogin(String login);

    Player findByLoginAndPassword(String login, String password);
}

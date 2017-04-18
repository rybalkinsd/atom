package ru.atom.dbhackaton.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "results", schema = "mm")
public class Aftermath {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;	
	
    @Column(name = "game_id", unique = false, nullable = false, length = 20)
    private String gameId;
    
    @Column(name = "username", unique = false, nullable = false, length = 20)
    private String username;
    
    @Column(name = "result", unique = false, nullable = false, length = 20)
    private String result;

    public Aftermath() {
    }

    public Aftermath(String gameId, String username, String result) {
    	setGameId(gameId);
    	setUsername(username);
    	setResult(result);
    }
    
    public String getGameId() {
    	return gameId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setGameId(String gameId) {
    	this.gameId = gameId;
    }
    
    public void setUsername(String username) {
    	this.username = username;
    }
    
    public void setResult(String result) {
    	this.result = result;
    }
    
    @Override
    public String toString() {
        return getGameId() + " " + getUsername() + " " + getResult();
    }
    
}

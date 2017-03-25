package ru.atom;

import java.util.Random;
import ru.atom.ChatResource;

public class Token {
	
	protected long token;
	String name;
	
	public Token(String name) {
		this.name = name;
		generateToken();
	}
	
	public void generateToken() {
		Random rand = new Random();
		token = rand.nextLong();
	}
	
	public User getUser() {
		return ChatResource.getUser(name);
	}

}

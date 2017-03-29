package ru.atom;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by Fella on 29.03.2017.
 */
public class Token {

    private long token;
    private static ConcurrentHashMap<String , Token> stupidMap = new ConcurrentHashMap<>();

    public static Token getTokenfromString(String token) {
        return stupidMap.get(token);

    }

    public static Token createToken(User user) {
        Token toc = new Token(user);
        stupidMap.put(toc.toString(), toc) ;
        return toc;
    }

    public boolean deleteToken() {
        boolean answer = stupidMap.remove(this.toString(), this) && StorageToken.remove(this);
        return answer;
    }





    protected Token(User user) {
        this.token = 4L * user.hashCode();
    }

    public long getToken() {
        return token;
    }


    @Override
    public String toString() {
        return  Long.toString(token);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;

        Token token1 = (Token) o;

        return getToken() == token1.getToken();
    }

    @Override
    public int hashCode() {
        return (int) (getToken() ^ (getToken() >>> 32));
    }
}

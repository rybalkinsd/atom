package ru.atom.resource;

/**
 * Created by Юля on 27.03.2017.
 */


public class Token {

    private Long token;


    public Token() {
        java.util.Random r = new java.util.Random();
        token = new Long(r.nextLong());
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return this.token.equals(token.getToken());
    }

    @Override
    public int hashCode(){
        return token.hashCode();
    }

    public Token(String newToken){
        token = Long.parseLong (newToken.substring(7));

    }

    public Long getToken() {
        return token;
    }

    @Override
    public String toString(){
        return token.toString();
    }



}

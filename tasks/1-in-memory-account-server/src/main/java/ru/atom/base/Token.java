package ru.atom.base;


public class Token {
    private String valueToken;

    public Token(long valueToken) {
        this.valueToken = Long.toString(valueToken);
    }

    public String getValueToken() {
        return valueToken;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || (getClass() != obj.getClass() && String.class != obj.getClass())) return false;


        if (getClass() == obj.getClass()) {
            Token token = (Token) obj;
            return valueToken.equals(token.valueToken);
        }
        if (String.class == obj.getClass()) {
            String valueToken1 = (String) obj;
            return valueToken.equals(valueToken1);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return valueToken.hashCode();
    }
}

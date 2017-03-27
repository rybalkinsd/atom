package tokens;

/**
 * Created by kinetik on 26.03.17.
 */
public class Token<E> {
    private E value;

    public Token(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token<?> token = (Token<?>) o;

        return value.equals(token.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public void setValue(E value) {
        this.value = value;
    }
}

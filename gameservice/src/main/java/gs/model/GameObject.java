package gs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gs.geometry.Bar;
import gs.geometry.Point;

public abstract class GameObject {
    protected transient final GameSession session;
    protected final int id;
    protected Point position;
    private transient final int width;
    private transient final int height;
    private static final int widthBox = 32;
    private static final int heightBox = 32;
    private String type;

    @JsonCreator
    public GameObject(GameSession session, @JsonProperty Point position, @JsonProperty String type, int width, int height) {
        this.position = position;
        this.type = type;
        this.id = session.getNewId();
        this.session = session;
        this.width = width;
        this.height = height;
    }

    public Bar getBar() {
        //return new Bar(position.getX(), position.getY(), position.getX() + 1, position.getY() + 1);
        return new Bar(position.getX(), position.getY(), position.getX() + width, position.getY() + height);
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public String getType() {return type;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameObject that = (GameObject) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = session != null ? session.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public static int getWidthBox() {
        return widthBox;
    }

    public static int getHeightBox() {
        return heightBox;
    }
}

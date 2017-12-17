package bomber.connectionhandler.json;

public class Possess {

    private final Topic topic = Topic.POSSESS;
    private Integer data;

    public Possess() {
    }


    public Topic getTopic() {
        return topic;
    }

    public Integer getData() {
        return data;
    }


    public void setData(Integer data) {
        this.data = data;
    }
}

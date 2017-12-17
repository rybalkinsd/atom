package bomber.connectionhandler.json;

public class HandleInputJson {

    private Topic topic;
    private DataHandleInputJson data;

    public HandleInputJson() {
    }

    public HandleInputJson(Topic topic, DataHandleInputJson data) {
        this.topic = topic;
        this.data = data;
    }

    public Topic getTopic() {
        return topic;
    }

    public DataHandleInputJson getData() {
        return data;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setData(DataHandleInputJson data) {
        this.data = data;
    }


}



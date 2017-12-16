package client;

import dto.MessageDataDto;

public class Message {
    private Topic topic;
    private MessageDataDto data;

    public Message(Topic topicInfo, MessageDataDto dataInfo) {
        this.topic = topicInfo;
        this.data = dataInfo;
    }

    public void setTopic(Topic topicInfo) {
        topic = topicInfo;
    }

    public void setData(MessageDataDto dataInfo) {
        data = dataInfo;
    }

    public Topic getTopic() {
        return topic;
    }

    public MessageDataDto getData() {
        return data;
    }
}

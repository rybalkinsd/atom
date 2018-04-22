package ru.atom.chat.socket.message.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.atom.chat.socket.message.request.SocketMessage;
import ru.atom.chat.socket.topics.IncomingTopic;
import ru.atom.chat.socket.topics.MailingType;
import ru.atom.chat.socket.topics.OutgoingTopic;
import ru.atom.chat.socket.topics.ResponseTopic;
import ru.atom.chat.socket.util.JsonHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResponseData extends ArrayList<Mail> {
    private static Logger log = LoggerFactory.getLogger(ResponseData.class);

    private IncomingTopic curOperation;
    private ResponseTopic status;

    public ResponseData(IncomingTopic curOperation) {
        super();
        this.curOperation = curOperation;
        status = null;
    }

    public Mail buildMail(MailingType type, String mail) {
        return new Mail(type, mail);
    }

    public Mail buildMail(MailingType type, IncomingTopic topic, String data) {
        SocketMessage message = new SocketMessage(topic, data);
        return new Mail(type, JsonHelper.toJson(message));
    }

    public Mail buildMail(MailingType type, OutgoingTopic topic, String data) {
        ResponseMessage message = new ResponseMessage(topic, data);
        return new Mail(type, JsonHelper.toJson(message));
    }

    public void addSenderError(String errorBody) {
        OperationResponse res = new OperationResponse(ResponseTopic.ERROR, errorBody);
        setStatus(ResponseTopic.ERROR);
        Mail mail = buildMail(MailingType.BACK_TO_SENDER, curOperation, JsonHelper.toJson(res));
        add(mail);
    }

    public void addSenderOkStatus() {
        OperationResponse res = new OperationResponse(ResponseTopic.OK, "");
        setStatus(ResponseTopic.OK);
        Mail mail = buildMail(MailingType.BACK_TO_SENDER, curOperation, JsonHelper.toJson(res));
        add(mail);
    }

    private void setStatus(ResponseTopic status) {
        if (this.status != null)
            log.warn("Will be updated status for operation " + curOperation +
                    ", from " + this.status + " to " + status);
        this.status = status;
    }

    public ResponseTopic getStatus() {
        return status;
    }

    public IncomingTopic getCurOperation() {
        return curOperation;
    }
}

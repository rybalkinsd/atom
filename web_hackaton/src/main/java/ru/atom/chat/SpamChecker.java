package ru.atom.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class SpamChecker {
    private static final Logger log = LogManager.getLogger(SpamChecker.class);
    private String user;
    private static final int TTL = 15; // TTL in seconds
    private static final int MAX_COUNT = 5; // Max count of allowed messages for TTL
    private static final int MAX_COUNT_SAME_MESSAGES = 3; // Max count of allowed the same consecutive messages
    private Date firstMessage;
    private int msgCount = 0;
    private int theSameMsg = 0;
    private String lastMessage = "";

    public SpamChecker(String user, Date firstMessage) {
        this.user = user;
        this.firstMessage = firstMessage;
    }

    public boolean isSpamming(String msg) {
        return theSameMessageChecker(msg) || ttlSpamChecker();
    }

    private boolean theSameMessageChecker(String msg) {
        if (msg.equals(lastMessage)) {
            theSameMsg++;
        } else {
            lastMessage = msg;
            theSameMsg = 0;
        }

        if (theSameMsg >= MAX_COUNT_SAME_MESSAGES) {
            log.info("User \'" + this.user + "\' is spamming via the same messages!");
            theSameMsg = 0;
            lastMessage = "";
            return true;
        }
        return false;
    }

    private boolean ttlSpamChecker() {
        long timeDiff = (new Date().getTime() - this.firstMessage.getTime()) / 1000;

        if (msgCount >= MAX_COUNT) {
            if (timeDiff < TTL) {
                log.info("User \'" + this.user + "\' is spamming! Lots of messages for TTL");
                this.msgCount = 0;
                this.firstMessage = new Date();
                return true;
            } else {
                this.msgCount = 0;
                this.firstMessage = new Date();
            }
        } else {
            msgCount++;
        }
        return false;
    }
}
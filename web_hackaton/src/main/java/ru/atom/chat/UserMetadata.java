package ru.atom.chat;


public class UserMetadata {
    private long lastMessageTime;
    private int numberOfMessages;

    public long getLastMessageTime() {
        return lastMessageTime;
    }

    public int getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setLastMessageTime() {
        this.lastMessageTime = System.nanoTime();
    }

    public void setNumberOfMessagesToZero(){
        numberOfMessages = 0;
    }

    UserMetadata(){
        lastMessageTime = System.nanoTime();
        numberOfMessages = 0;
    }

    void incNumberOfMesssages(){
        numberOfMessages++;
    }
}

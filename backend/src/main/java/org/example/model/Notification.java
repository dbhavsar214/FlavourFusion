package org.example.model;

public class Notification {
    private Long notificationID;
    private Long receiverID;
    private String body;
    private String title;

    // Constructors
    public Notification() {}

    public Notification(Long notificationID, Long receiverID, String body, String title) {
        this.notificationID = notificationID;
        this.receiverID = receiverID;
        this.body = body;
        this.title = title;
    }

    // Getters and Setters
    public Long getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(Long notificationID) {
        this.notificationID = notificationID;
    }

    public Long getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(Long receiverID) {
        this.receiverID = receiverID;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationID=" + notificationID +
                ", receiverID=" + receiverID +
                ", body='" + body + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}


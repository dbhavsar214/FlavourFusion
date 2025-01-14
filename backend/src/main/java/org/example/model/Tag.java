package org.example.model;

public class Tag {

    private int tagID;
    private String tagName;

    // Constructors
    public Tag() {
        // Default constructor
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }
    public Tag(int tagID, String tagName) {
        this.tagID = tagID;
        this.tagName = tagName;
    }

    // Getters and Setters
    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    // Methods
    public void createTag() {
        // Logic for creating a tag
        System.out.println("Tag created: " + tagName);
    }

    public void editTag(String newTagName) {
        // Logic for editing a tag
        this.tagName = newTagName;
        System.out.println("Tag edited: " + tagName);
    }

    public Tag getTag() {
        // Logic to fetch tag information
        return this;
    }

    public void removeTag() {
        // Logic for removing a tag
        System.out.println("Tag removed: " + tagName);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagID=" + tagID +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}

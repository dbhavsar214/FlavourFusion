package org.example.model;

import java.util.List;

public class Category {

    private Long categoryID;
    private String categoryName;
    private List<Tag> includedTags;

    // Getters and Setters

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Tag> getIncludedTags() {
        return includedTags;
    }

    public void setIncludedTags(List<Tag> includedTags) {
        this.includedTags = includedTags;
    }

    @Override
    public String toString() {
        return "Category [categoryID=" + categoryID + ", categoryName=" + categoryName + ", includedTags="
                + includedTags + "]";
    }
}
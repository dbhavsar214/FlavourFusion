package org.example.model;

import java.time.LocalDateTime;
import java.util.List;

public class Recipe {

    private Long creatorID;
    private Long recipeID;
    private String name;
    private List<String> ingredients;
    private String description;
    private Integer cookingTime;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdated;
    private List<String> tags;
    private List<byte[]> images;
    private String direction;

    public Recipe() {
    }

    public Recipe(Long creatorID, String name, List<String> ingredients, String description, List<String> tags, String direction) {
        this.creatorID = creatorID;
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.tags = tags;
        this.direction = direction;
    }

    public Recipe(Long creatorID, String name, List<String> ingredients, String description, List<String> tags, List<byte[]> images, String direction) {
        this.creatorID = creatorID;
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.tags = tags;
        this.images = images;
        this.direction = direction;
    }

    // Getters and Setters

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Long getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(Long creatorID) {
        this.creatorID = creatorID;
    }

    public Long getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(Long recipeID) {
        this.recipeID = recipeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Recipe{" +
                "creatorID=" + creatorID +
                ", recipeID=" + recipeID +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", description='" + description + '\'' +
                ", cookingTime=" + cookingTime +
                ", creationDate=" + creationDate +
                ", lastUpdated=" + lastUpdated +
                ", tags=" + tags +
                ", images=" + images +
                ", direction='" + direction + '\'' +
                '}';
    }
}

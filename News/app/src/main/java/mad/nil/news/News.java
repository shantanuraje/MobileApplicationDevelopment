package mad.nil.news;

import java.util.Date;

/**
 * Inclass Assignment 6
 * File name: News.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */


public class News {
    String title;
    String publishedAt;
    String imageUrl;
    String description;

    public News() {
    }

    public News(String title, String publishedAt, String imageUrl, String description) {
        this.title = title;
        this.publishedAt = publishedAt;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        if(title == null) {
            this.title = "";
        }
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
        if(publishedAt == null) {
            this.publishedAt = "";
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        if(description == null) {
            this.description = "";
        }
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", publishedAt=" + publishedAt +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

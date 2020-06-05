package example.com.events;

import java.io.Serializable;

/**
 * Created by Joko Wandiro on 20/01/2020.
 */

public class Event implements Serializable {
    String id, title, image, start_date, end_date, start_time, end_time, url;

    public Event(String id, String title, String image, String start_date, String end_date,
                 String start_time, String end_time, String url) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.start_date = start_date;
        this.end_date = end_date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getStartDate() {
        return start_date;
    }

    public String getEndDate() {
        return end_date;
    }

    public String getStartTime() {
        return start_time;
    }

    public String getEndTime() {
        return end_time;
    }

    public String getUrl() {
        return url;
    }

}
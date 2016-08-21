package rocks.evesan.creatnapp.domain;

import java.io.Serializable;

/**
 * Created by evesan on 8/21/16.
 */
public class History implements Serializable{

    private String name;
    private String latitude;
    private String longitude;
    private String url;
    private String likes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }



}

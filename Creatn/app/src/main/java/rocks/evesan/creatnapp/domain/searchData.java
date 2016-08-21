package rocks.evesan.creatnapp.domain;

/**
 * Created by evesan on 8/21/16.
 */
public class searchData {

    private String latitude;
    private String longitude;

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

    public String getPurged() {
        return purged;
    }

    public void setPurged(String purged) {
        this.purged = purged;
    }

    private String purged;
}

package rocks.evesan.creatnapp.domain;

/**
 * Created by evesan on 8/21/16.
 */
public class searchData {
    private info user_info;

    public searchData(String lat, String lon, String purged) {
        user_info = new info();
        user_info.setPurged(purged);
        user_info.setLatitude(lat);
        user_info.setLongitude(lon);
    }


    public class info{

        private String latitude;
        private String longitude;
        private String purged;

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
    }
}

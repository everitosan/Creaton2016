package rocks.evesan.creatnapp.Service;

import android.location.Criteria;
import android.location.LocationManager;

/**
 * Created by evesan on 8/30/16.
 */
public class LocationSrv {
    private LocationManager mLocationManager = null;

    public LocationSrv(LocationManager lm) {
        this.mLocationManager = lm;
    }

    public String getProviderName() {
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);

        return mLocationManager.getBestProvider(criteria, true);
    }

    public LocationManager getLocationManager(){
        return  this.mLocationManager;
    }
}

package com.kooloco.ui.visitor.home.temp;

import com.kooloco.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlink44 on 14/9/17.
 */

public class Temp {
    public static List<Integer> integersDrwable;
    public static List<Integer> serviceDrwable;
    public static List<Integer> serviceFourDrwable;
    public static List<Integer> certificates;

    public static List<Integer> getDrwableList() {
        if (integersDrwable == null) {
            integersDrwable = new ArrayList<>();
            integersDrwable.add(R.drawable.temp_1);
            integersDrwable.add(R.drawable.temp_2);
            integersDrwable.add(R.drawable.temp_3);
            integersDrwable.add(R.drawable.temp_4);
        }
        return integersDrwable;
    }

    public static List<Integer> getServiceDrwableDrwableList() {
        if (serviceDrwable == null) {
            serviceDrwable = new ArrayList<>();
            serviceDrwable.add(R.drawable.home_climbiln);
            serviceDrwable.add(R.drawable.home_hiking);
            serviceDrwable.add(R.drawable.home_skate);
            serviceDrwable.add(R.drawable.home_skiing);
            serviceDrwable.add(R.drawable.home_surfing);
            serviceDrwable.add(R.drawable.home_map_snow);
            serviceDrwable.add(R.drawable.home_mountainering);
        }
        return serviceDrwable;
    }

    public static List<Integer> getServiceDrwableFourDrwableList() {
        if (serviceFourDrwable == null) {
            serviceFourDrwable = new ArrayList<>();
            serviceFourDrwable.add(R.drawable.home_climbiln);
            serviceFourDrwable.add(R.drawable.home_hiking);
            serviceFourDrwable.add(R.drawable.home_skate);
            serviceFourDrwable.add(R.drawable.home_skiing);
            serviceFourDrwable.add(R.drawable.home_surfing);
        }
        return serviceFourDrwable;
    }

    public static List<Integer> getServiceCertficates() {
        if (certificates == null) {
            certificates = new ArrayList<>();
            certificates.add(R.drawable.temp_certificat);
            certificates.add(R.drawable.temp_certificat);
            certificates.add(R.drawable.temp_certificat);
            certificates.add(R.drawable.temp_certificat);
        }
        return certificates;
    }

}

package com.kooloco.util;

import android.content.Context;

import com.kooloco.R;

/**
 * Created by hlink44 on 27/12/17.
 */

public class StaticMap {

    /**
     * It is used to get static image get url
     *
     * @param context
     * @param lat     latitude
     * @param lng
     * @param iconUrl icon Url
     * @return Return static path
     */

    public static String getUrl(Context context, String lat, String lng, String iconUrl) {

        return "http://maps.googleapis.com/maps/api/staticmap?\n" +
                "&size=250x150\n" +
                "&format=png\n" +
                "&maptype=roadmap\n" +
                "&style=element:geometry|saturation:-45&style=feature:administrative.land_parcel|saturation:100&style=feature:landscape|saturation:-100&style=feature:landscape.natural.terrain|saturation:-100&style=feature:poi|element:labels|visibility:off&style=feature:poi.park|color:0xc7d6ca|saturation:-5&style=feature:road|element:labels|visibility:off&style=feature:transit|visibility:off&style=feature:water|color:0x86ced2\n" +
                "&zoom=15\n" +
                "&markers=icon:" + iconUrl + "|" + lat + "," + lng + "\n" +
                "&key=" + context.getString(R.string.mapkey) + "";
    }

}

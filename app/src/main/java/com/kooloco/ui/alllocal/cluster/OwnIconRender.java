package com.kooloco.ui.alllocal.cluster;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.kooloco.R;

/**
 * Created by hlink on 27/2/18.
 */

public class OwnIconRender extends DefaultClusterRenderer<MyItem> {
    public OwnIconRender(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        final BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.map_local_location_icon);

        markerOptions.icon(markerDescriptor).title(item.getTitle()).snippet(item.getSnippet());

        super.onBeforeClusterItemRendered(item, markerOptions);
    }



}

package com.ltudttbdd.project.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ltudttbdd.project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    Toolbar toolbarcontact;
    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        viewFlipper = findViewById(R.id.viewflipper);
        ActionViewFlipper();
        toolbarcontact = findViewById(R.id.toolbarcontact);
        ActionBar();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void ActionBar() {
        setSupportActionBar(toolbarcontact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarcontact.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng ctxhuit = new LatLng(10.870128, 106.803565);
        mMap.addMarker(new MarkerOptions().position(ctxhuit).title("Đội CTXH trường ĐH CNTT").snippet("Khu phố 6, Phường Linh Trung, Quận Thủ Đức, Thành phố Hồ Chí Minh, Việt Nam").icon(BitmapDescriptorFactory.defaultMarker()));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(ctxhuit).zoom(15).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://i.ibb.co/R9dwLgT/22047973-1538336126223539-5205918986123174599-o.jpg");
        mangquangcao.add("https://i.ibb.co/Gnm38D6/69502430-2519373094786499-4438127691371118592-o.jpg");
        mangquangcao.add("https://i.ibb.co/1LXfxW4/29351659-1748325571891259-912106256589011434-o.png");
        mangquangcao.add("https://i.ibb.co/zVPzckW/32130359-1793076794082803-1249016866664349696-o.jpg");
        mangquangcao.add("https://i.ibb.co/0CtZvHv/56483255-2269799063077238-1030531296201277440-o.jpg");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }
}





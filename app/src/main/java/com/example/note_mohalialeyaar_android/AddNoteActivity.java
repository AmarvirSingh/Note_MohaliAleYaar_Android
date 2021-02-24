package com.example.note_mohalialeyaar_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.note_mohalialeyaar_android.HelperClass.DatabaseHelperClass;
//import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;

public class AddNoteActivity extends AppCompatActivity {

    EditText title, description;
    Button btnSave;
    Button btn_image;
    DatabaseHelperClass db;
    TextView location;
   // FusedLocationProviderClient mFusedLocationClient;

    // Initializing other items
    // from layout file
    int PERMISSION_ID = 44;
    ArrayList<NotesModelClass> notesDesc = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
//        getLastLocation();
        db = new DatabaseHelperClass(this);
        title = findViewById(R.id.title_note);
        description = findViewById(R.id.desc_note);
        btnSave = findViewById(R.id.save);
        btn_image = findViewById(R.id.btnpickimage);
        location = findViewById(R.id.location);

        btnSave.setOnClickListener(view -> {
            if(validate())
            {


                long insert =  db.insertNote(title.getText().toString(),description.getText().toString(),loc,"23");
                if(insert!=0)
                {
                    finish();
                }
            }
        });

    }


    public boolean validate()
    {
        if(title.getText().toString().isEmpty())
        {
            title.setError("Please enter value");
        }
        else if(description.getText().toString().isEmpty())
        {
            description.setError("Please enter value");
        }

        return true;
    }

    String loc = "";
//    private void getLastLocation() {
//        // check if permissions are given
//        if (checkPermissions()) {
//
//            // check if location is enabled
//            if (isLocationEnabled()) {
//
//                // getting last
//                // location from
//                // FusedLocationClient
//                // object
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//
//                }
//                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        Location location = task.getResult();
//                        if (location == null) {
//                            requestNewLocationData();
//                        } else {
////                            latitudeTextView.setText(location.getLatitude() + "");
////                            longitTextView.setText(location.getLatitude()) + "");
//
//                            String s = String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());
//                            loc = s;
//                        }
//                    }
//                });
//            } else {
//                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
//            }
//        } else {
//            // if permissions aren't available,
//            // request for permissions
//            requestPermissions();
//        }
//    }
//
//
//    @SuppressLint("MissingPermission")
//    private void requestNewLocationData() {
//
//        // Initializing LocationRequest
//        // object with appropriate methods
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(5);
//        mLocationRequest.setFastestInterval(0);
//        mLocationRequest.setNumUpdates(1);
//
//        // setting LocationRequest
//        // on FusedLocationClient
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
//    }
//
//    private LocationCallback mLocationCallback = new LocationCallback() {
//
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
//            Location mLastLocation = locationResult.getLastLocation();
////            latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
////            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");
//        }
//    };
//
//    // method to check for permissions
//    private boolean checkPermissions() {
//        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
//
//        // If we want background location
//        // on Android 10.0 and higher,
//        // use:
//        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
//    }
//
//    // method to request for permissions
//    private void requestPermissions() {
//        ActivityCompat.requestPermissions(this, new String[]{
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
//    }
//
//    // method to check
//    // if location is enabled
//    private boolean isLocationEnabled() {
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//    }
//
//    // If everything is alright then
//    @Override
//    public void
//    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == PERMISSION_ID) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLastLocation();
//            }
//        }
//    }
}
package ua.in.zeusapps.snapsafari.activities;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.opengl.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.in.zeusapps.snapsafari.MainActivity;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.controls.ARCamera;
import ua.in.zeusapps.snapsafari.controls.AROverlayView;
import ua.in.zeusapps.snapsafari.models.CardAnimation;
import ua.in.zeusapps.snapsafari.models.Card;
import ua.in.zeusapps.snapsafari.models.Event;
import ua.in.zeusapps.snapsafari.models.EventRequest;


public class ARActivity extends ActivityBase implements SensorEventListener, LocationListener {

    final static String TAG = "ARActivity";
    private SurfaceView surfaceView;
    private FrameLayout cameraContainerLayout;
    private AROverlayView arOverlayView;
    private Camera camera;
    private ARCamera arCamera;
    private TextView tvCurrentLocation;
    private TextView arPointLocationTextView;
    ImageView elephantView;
    private AnimationDrawable animatedElephant;

    private SensorManager sensorManager;
    private final static int REQUEST_CAMERA_PERMISSIONS_CODE = 11;
    public static final int REQUEST_LOCATION_PERMISSIONS_CODE = 0;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 0;//1000 * 60 * 1; // 1 minute

    private LocationManager locationManager;
    public Location location;
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
    boolean locationServiceAvailable;

    private List<Event> _events;
    private Card _card;
    DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        cameraContainerLayout = (FrameLayout) findViewById(R.id.camera_container_layout);
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        tvCurrentLocation = (TextView) findViewById(R.id.tv_current_location);
        arPointLocationTextView = (TextView) findViewById(R.id.ar_point_location);
        arOverlayView = new AROverlayView(this);

        elephantView = (ImageView) findViewById(R.id.animated_elephant);
        elephantView.setBackgroundResource(R.drawable.animated_elephant);
        animatedElephant = (AnimationDrawable) elephantView.getBackground();
        elephantView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestLocationPermission();
        requestCameraPermission();
        registerSensors();
        initAROverlayView();
        animatedElephant.start();
    }

    @Override
    public void onPause() {
        releaseCamera();
        super.onPause();
        animatedElephant.stop();
    }

    public void changeElephantCoords(float x, float y) {
        elephantView.setVisibility(View.VISIBLE);
        elephantView.setY(y);
        elephantView.setX(x);
    }

    public void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                this.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSIONS_CODE);
        } else {
            initARCameraView();
        }
    }

    public void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSIONS_CODE);
        } else {
            initLocationService();
        }
    }

    public void initAROverlayView() {
        if (arOverlayView.getParent() != null) {
            ((ViewGroup) arOverlayView.getParent()).removeView(arOverlayView);
        }
        cameraContainerLayout.addView(arOverlayView);
    }

    public void initARCameraView() {
        reloadSurfaceView();

        if (arCamera == null) {
            arCamera = new ARCamera(this, surfaceView);
        }
        if (arCamera.getParent() != null) {
            ((ViewGroup) arCamera.getParent()).removeView(arCamera);
        }
        cameraContainerLayout.addView(arCamera);
        arCamera.setKeepScreenOn(true);
        initCamera();
    }

    private void initCamera() {
        int numCams = Camera.getNumberOfCameras();
        if(numCams > 0){
            try{
                camera = Camera.open();
                camera.startPreview();
                arCamera.setCamera(camera);
            } catch (RuntimeException ex){
                Toast.makeText(this, "Camera not found", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void reloadSurfaceView() {
        if (surfaceView.getParent() != null) {
            ((ViewGroup) surfaceView.getParent()).removeView(surfaceView);
        }

        cameraContainerLayout.addView(surfaceView);
    }

    private void releaseCamera() {
        if(camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            arCamera.setCamera(null);
            camera.release();
            camera = null;
        }
    }

    private void registerSensors() {
        sensorManager.registerListener(this,  sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float[] rotationMatrixFromVector = new float[16];
            float[] projectionMatrix = new float[16];
            float[] rotatedProjectionMatrix = new float[16];

            SensorManager.getRotationMatrixFromVector(rotationMatrixFromVector, sensorEvent.values);

            if (arCamera != null) {
                projectionMatrix = arCamera.getProjectionMatrix();
            }

            Matrix.multiplyMM(rotatedProjectionMatrix, 0, projectionMatrix, 0, rotationMatrixFromVector, 0);
            this.arOverlayView.updateRotatedProjectionMatrix(rotatedProjectionMatrix);
        }
    }

    public void doCatch(View view) {
        Intent intent = new Intent(this, ElephantActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //do nothing
    }

    private void initLocationService() {

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }

        try   {
            this.locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);

            // Get GPS and network status
            this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            this.isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isNetworkEnabled && !isGPSEnabled)    {
                // cannot get location
                this.locationServiceAvailable = false;
            }

            this.locationServiceAvailable = true;

            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                if (locationManager != null)   {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        updateLatestLocation();
                    }
                }
            }

            if (isGPSEnabled)  {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                if (locationManager != null)  {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        updateLatestLocation();
                    }
                }
            }
        } catch (Exception ex)  {
            Log.e(TAG, ex.getMessage());

        }
    }

    private void updateLatestLocation() {
        if (arOverlayView !=null) {
            arOverlayView.updateCurrentLocation(location);
            tvCurrentLocation.setText(String.format("lat: %s \nlon: %s \naltitude: %s \n",
                    location.getLatitude(), location.getLongitude(), location.getAltitude()));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null && this.location == null) {
            updateCards(location);
        }
        this.location = location;
        updateLatestLocation();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void updateCards(Location location) {

//        EventRequest request = new EventRequest(location.getLongitude(), location.getLatitude(), 1000);
        EventRequest request = new EventRequest((float)(50.51576066295344), (float)(30.60552879457229), 1000);

        getApp().getService()
                .getEvents(getToken(), request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Event>>() {
                    @Override
                    public void accept(@NonNull List<Event> events) throws Exception {
                        _events = events;
                        arOverlayView.events = events;
//                        ArrayList<CardAnimation> cardAnimations = new ArrayList<CardAnimation>();
//                        for (final Event event: events) {
//                            String url = "cards/get_animal_animation/" + event.getCard().getKindID() + "&" + event.getCard().getElement()+"/";
//
//                            getApp().getService().getAnimation(getToken(), url)
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .subscribeOn(Schedulers.io())
//                                    .subscribe(new Consumer<CardAnimation>() {
//                                        @Override
//                                        public void accept(@NonNull CardAnimation cardAnimation) throws Exception {
//                                            event.setAnimationURL(cardAnimation.getAnimationURL());
//                                            Uri imageUri = Uri.parse(getApp().BASE_URL + cardAnimation.getAnimationURL());
//                                            downloadData(imageUri);
//                                        }
//                                    }, new Consumer<Throwable>() {
//                                        @Override
//                                        public void accept(@NonNull Throwable throwable) throws Exception {
//                                            Log.d(ElephantActivity.class.getSimpleName(), throwable.getMessage());
//                                        }
//                                    });
//                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(ElephantActivity.class.getSimpleName(), throwable.getMessage());
                    }
                });

    }

//    private long downloadData (Uri uri) {
//
//        long downloadReference;
//
//        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setTitle("Data Download");
//        request.setDescription("Android Data download using DownloadManager.");
//        request.setDestinationInExternalFilesDir(ARActivity.this, Environment.DIRECTORY_DOWNLOADS,"AndroidTutorialPoint.mp3");
//        downloadReference = downloadManager.enqueue(request);
//
//        return downloadReference;
//    }
}

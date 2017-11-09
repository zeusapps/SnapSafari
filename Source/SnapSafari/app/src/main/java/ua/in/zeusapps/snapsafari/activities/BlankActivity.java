package ua.in.zeusapps.snapsafari.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.opengl.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.common.Layout;
import ua.in.zeusapps.snapsafari.controls.ARCamera;
import ua.in.zeusapps.snapsafari.controls.AROverlayView;
import ua.in.zeusapps.snapsafari.controls.AnimationsContainer;
import ua.in.zeusapps.snapsafari.controls.Menu;
import ua.in.zeusapps.snapsafari.models.Card;
import ua.in.zeusapps.snapsafari.models.Event;
import ua.in.zeusapps.snapsafari.models.EventRequest;

@Layout(R.layout.activity_menu)
public class BlankActivity extends ActivityBase implements SensorEventListener, LocationListener {

    final static String TAG = "BlankActivity";
    private SurfaceView surfaceView;
    private FrameLayout cameraContainerLayout;
    private AROverlayView arOverlayView;
    private Camera camera;
    private ARCamera arCamera;
    private TextView tvCurrentLocation;
    private TextView arPointLocationTextView;
    HashMap<Integer, ImageView> animatedViews;

    private SensorManager sensorManager;
    private final static int REQUEST_CAMERA_PERMISSIONS_CODE = 11;
    private final static int REQUEST_LOCATION_PERMISSIONS_CODE = 0;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 0;//1000 * 60 * 1; // 1 minute

    private LocationManager locationManager;
    private Location location;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private boolean locationServiceAvailable;
    private boolean isARPointsUpdated = false;

    private List<Event> _events;
    private List<AnimationsContainer> _containers;
    private Card _card;

    private boolean firstrun = true;

    @BindView(R.id.activity_menu_menu)
    Menu _menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        cameraContainerLayout = (FrameLayout) findViewById(R.id.camera_container_layout);
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        arOverlayView = new AROverlayView(this);
    }

    @OnClick({ R.id.activity_menu_bottom_menu, R.id.activity_menu_menu})
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.menu_hamburger) {
            finish();
        } else if (id == R.id.bottom_menu_left) {
            Intent intent = new Intent(this, SnapCardsActivity.class);
            startActivity(intent);
        } else if (id == R.id.bottom_menu_center) {
            for (Event event : _events) {
                if (this.location.distanceTo(event.getArPoint().getLocation()) < 50) {
                    Intent intent = new Intent(this, ElephantActivity.class);
                    startActivity(intent);
                    break;
                }
            }
        } else if (id == R.id.bottom_menu_right) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requestLocationPermission();
        requestCameraPermission();
        registerSensors();
        initAROverlayView();
    }

    @Override
    public void onPause() {
        releaseCamera();
        super.onPause();
    }

    public void moveAnimationTo(float x, float y, float scale, int eventID) {
        ImageView animatedView = animatedViews.get(eventID);
        if (animatedView == null) {
            return;
//            animatedView = animatedViews.get("elephant");
        }
        animatedView.setTranslationX(x);
        animatedView.setTranslationY(y);
        animatedView.setScaleX(scale);
        animatedView.setScaleY(scale);
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
        cameraContainerLayout.addView(arOverlayView, 1);
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
                    //SK: customer location
//                    location.setLatitude(-1.28611111);
//                    location.setLongitude(36.77944444);
                    //dstudio
//                    this.location.setLatitude(-1.28611111);
//                    this.location.setLongitude(36.7794444);
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
//            tvCurrentLocation.setText(String.format("lat: %s \nlon: %s \naltitude: %s \n", location.getLatitude(), location.getLongitude(), location.getAltitude()));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        //SK: customer location
//        this.location.setLatitude(-1.28611111);
//        this.location.setLongitude(36.77944444);
        //dstudio
//        this.location.setLatitude(-1.28611111);
//        this.location.setLongitude(36.7794444);
        if (this.location != null && !isARPointsUpdated ) {
            updateCards(location);
            isARPointsUpdated = true;
        }
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

        EventRequest request = new EventRequest((float)(location.getLatitude()), (float)(location.getLongitude()), 1000);
        getApp().getService()
                .getEvents(getToken(), request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Event>>() {
                    @Override
                    public void accept(@NonNull List<Event> events) throws Exception {
                        _events = events;
                        arOverlayView.events = events;
                        isARPointsUpdated = true;

                        _containers = new ArrayList<AnimationsContainer>();

                        if (events.size() > 0) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    initializeAnimation();
                                }
                            });
                        }
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

    private void initializeAnimation() {

        animatedViews = new HashMap<Integer, ImageView>();

        FrameLayout mainLayout = (FrameLayout) findViewById(R.id.camera_container_layout);
        LayoutInflater inflater = getLayoutInflater();

        for (Event event : _events) {
            String kind = event.getCard().getKindID();
            if (animatedViews.get(kind) != null) {
                continue;
            }
            View view = (View) inflater.inflate(R.layout.animation_container, mainLayout, false);
            mainLayout.addView(view);
            ImageView imageView = (ImageView)view.findViewById(R.id.animation_frame);
            addDrawable(imageView, kind);
            animatedViews.put(event.getId(), imageView);
        }
    }

    private void addDrawable(ImageView view, String kind) {
        String fileName = null;
        if (kind.equals("buffalo")) {
            fileName = "al_buffalo";
        } else if (kind.equals("crocodile")) {
            fileName = "al_crocodile";
        } else if (kind.equals("elephant")) {
            fileName = "al_elephant";
        } else if (kind.equals("frog")) {
            fileName = "al_frog";
        } else if (kind.equals("giraffe")) {
//            fileName  = "al_giraffe";
        } else if (kind.equals("hippo")) {
//            fileName  = "al_hippo";
        } else if (kind.equals("leopard")) {
            fileName.equals("al_leopard");
        } else if (kind.equals("lion")) {
//            fileName  = "al_lion";
        } else if (kind.equals("owl")) {
//            fileName  = "al_owl";
        } else if (kind.equals("rhino")) {
            fileName = "al_rhino";
        } else if (kind.equals("wild_dog")) {
            fileName = "al_wild_dog";
        }

        if (fileName == null) {
            return;
        }

        int fileID = getResources().getIdentifier(kind, "array", getPackageName());
        String[] frameNames = getResources().getStringArray(fileID);
        int count = frameNames.length;
        int[] frameIDs = new int[frameNames.length];

        for (int i = 0; i < count; i++) {
            frameIDs[i] = getResources().getIdentifier(frameNames[i], "drawable", getPackageName());
        }
        AnimationsContainer container = new AnimationsContainer(view);
        container.addAllFrames(frameIDs, 50);
        container.start();
        _containers.add(container);
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

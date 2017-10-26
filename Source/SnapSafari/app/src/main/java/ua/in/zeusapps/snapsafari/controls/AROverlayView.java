package ua.in.zeusapps.snapsafari.controls;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.location.Location;
import android.opengl.Matrix;
import android.support.v7.widget.AppCompatImageView;

import java.util.List;

import ua.in.zeusapps.snapsafari.activities.BlankActivity;
import ua.in.zeusapps.snapsafari.common.LocationHelper;
import ua.in.zeusapps.snapsafari.models.Event;


public class AROverlayView extends AppCompatImageView {

    Context context;
    private float[] rotatedProjectionMatrix = new float[16];
    private Location currentLocation;
    public List<Event> events;


    public AROverlayView(Context context) {
        super(context);

        this.context = context;
    }

    public void updateRotatedProjectionMatrix(float[] rotatedProjectionMatrix) {
        this.rotatedProjectionMatrix = rotatedProjectionMatrix;
        this.invalidate();
    }

    public void updateCurrentLocation(Location currentLocation){
//        this.currentLocation = currentLocation;
        //SK: customer location
        this.currentLocation = new Location("-1.28611111,36.77944444,1000");
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        final int radius = 60;
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.GREEN);
//        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
//        paint.setTextSize(60);
//
//        canvas.drawCircle(100, 100, radius, paint);
//        canvas.drawText(arPoints.get(i).getName(), x - (30 * arPoints.get(i).getName().length() / 2), y - 80, paint);

        if (currentLocation == null || events == null || events.size() == 0) {
            return;
        }

        for (Event event: events) {
            Location pointLocation = event.getArPoint().getLocation();
            if (pointLocation == null) {
                continue;
            }
            float[] currentLocationInECEF = LocationHelper.WSG84toECEF(currentLocation);
            float[] pointInECEF = LocationHelper.WSG84toECEF(pointLocation);
            float[] pointInENU = LocationHelper.ECEFtoENU(currentLocation, currentLocationInECEF, pointInECEF);

            float[] cameraCoordinateVector = new float[4];
            Matrix.multiplyMV(cameraCoordinateVector, 0, rotatedProjectionMatrix, 0, pointInENU, 0);

            // cameraCoordinateVector[2] is z, that always less than 0 to display on right position
            // if z > 0, the point will display on the opposite
            if (cameraCoordinateVector[2] < 0) {
                float x  = (0.5f + cameraCoordinateVector[0]/cameraCoordinateVector[3]) * canvas.getWidth();
                float y = (0.5f - cameraCoordinateVector[1]/cameraCoordinateVector[3]) * canvas.getHeight();

//                canvas.drawCircle(x, y, radius, paint);
//                canvas.drawText(arPoints.get(i).getName(), x - (30 * arPoints.get(i).getName().length() / 2), y - 80, paint);

//                Paint p = new Paint();
//                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.elephant_00001);
//                canvas.drawBitmap(b, 0, 0, p);

                Context context = getContext();
                while (context instanceof ContextWrapper) {
                    if (context instanceof BlankActivity) {
                        ((BlankActivity) context).moveAnimationTo(x, y, event.getId());
                        break;
                    }
                    context = ((ContextWrapper)context).getBaseContext();
                }
            } else {
                //SK: fro debug
                Context context = getContext();
                while (context instanceof ContextWrapper) {
                    if (context instanceof BlankActivity) {
                        ((BlankActivity) context).moveAnimationTo(160, 200, event.getCard().getId());
                        break;
                    }
                    context = ((ContextWrapper) context).getBaseContext();
                }
            }
        }
    }
}

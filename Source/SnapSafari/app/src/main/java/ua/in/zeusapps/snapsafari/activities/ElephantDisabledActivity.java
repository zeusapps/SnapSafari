package ua.in.zeusapps.snapsafari.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.controls.BottomMenu;
import ua.in.zeusapps.snapsafari.controls.Menu;

public class ElephantDisabledActivity extends AppCompatActivity {
    private final static double IMAGE_ALFA = 0.2;

    @BindView(R.id.activity_elephant_disabled_menu)
    Menu _menu;
    @BindView(R.id.activity_elephant_disabled_bottom_menu)
    BottomMenu _bottomMenu;
    @BindView(R.id.activity_elephant_disabled_label)
    TextView _message;
    @BindView(R.id.activity_elephant_disabled_image)
    ImageView _image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elephant_disabled);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        init();
    }

    private void init() {
        Bitmap elephant = BitmapFactory.decodeResource(getResources(), R.drawable.elephant);
        Bitmap disabledElephant = doColorFilter(elephant, IMAGE_ALFA, IMAGE_ALFA, IMAGE_ALFA);

        _image.setImageBitmap(disabledElephant);
    }

    public static Bitmap doColorFilter(Bitmap src, double red, double green, double blue) {
       // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;
        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                // apply filtering on each channel R, G, B
                A = Color.alpha(pixel);
                R = (int)(Color.red(pixel) * red);
                G = (int)(Color.green(pixel) * green);
                B = (int)(Color.blue(pixel) * blue);
                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        // return final image
        return bmOut;
    }
}

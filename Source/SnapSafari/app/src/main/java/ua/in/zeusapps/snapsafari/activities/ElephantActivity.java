package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.controls.BottomMenu;
import ua.in.zeusapps.snapsafari.controls.Menu;

public class ElephantActivity extends AppCompatActivity {

    @BindView(R.id.activity_elephant_menu)
    Menu _menu;
    @BindView(R.id.activity_elephant_bottom_menu)
    BottomMenu _bottomMenu;
    @BindView(R.id.activity_elephant_content)
    LinearLayout _linearLayout;
    @BindView(R.id.activity_elephant_image)
    ImageView _imageView;
    @BindView(R.id.activity_elephant_label)
    TextView _labelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elephant);

        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.activity_elephant_menu,
            R.id.activity_elephant_bottom_menu
    })
    public void onClick(View view){
        Intent intent = new Intent(this, SnapActivity.class);

        startActivity(intent);
    }
}

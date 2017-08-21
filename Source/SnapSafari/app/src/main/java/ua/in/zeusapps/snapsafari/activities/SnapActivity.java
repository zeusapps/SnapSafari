package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.in.zeusapps.snapsafari.R;

public class SnapActivity extends AppCompatActivity {
    @BindView(R.id.activity_snap_image)
    ImageView _imageImageView;
    @BindView(R.id.activity_snap_icon)
    ImageView _iconImageView;
    @BindView(R.id.activity_snap_label)
    TextView _labelTextView;
    @BindView(R.id.activity_snap_level)
    TextView _levelTextView;
    @BindView(R.id.activity_snap_description)
    TextView _descriptionTextView;
    @BindView(R.id.activity_snap_snap_it_button)
    Button _snapButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.activity_snap_snap_it_button)
    public void onClick(){
        Intent intent = new Intent(this, ElephantDisabledActivity.class);

        startActivity(intent);
    }
}

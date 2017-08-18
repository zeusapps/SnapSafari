package ua.in.zeusapps.snapsafari.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.OnClick;
import ua.in.zeusapps.snapsafari.R;

public class MenuActivity extends AppCompatActivity {
    @BindView(R.id.activity_menu_menu)
    ImageButton _menuButton;
    @BindView(R.id.activity_menu_card_collection)
    ImageButton _cardCollectionButton;
    @BindView(R.id.activity_menu_take_picture)
    ImageButton _TakePictureButton;
    @BindView(R.id.activity_menu_position)
    ImageButton _positionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @OnClick({
            R.id.activity_menu_menu,
            R.id.activity_menu_card_collection,
            R.id.activity_menu_take_picture,
            R.id.activity_menu_position
    })
    public void onClick(){
        
    }
}

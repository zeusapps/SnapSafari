package ua.in.zeusapps.snapsafari.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.controls.BottomMenu;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);
    }

    @OnClick({ R.id.activity_menu_bottom_menu, R.id.activity_menu_menu})
    public void onClick(View view){
        String text = "Clicked ";

        switch (view.getId()){
            case R.id.bottom_menu_card_collection:
                text += "left button";
                break;
            case R.id.bottom_menu_position:
                text += "center button";
                break;
            case R.id.bottom_menu_take_picture:
                text += "right button";
                break;
            case R.id.menu_hamburger:
                text += "hamburger button";
                break;
        }

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}

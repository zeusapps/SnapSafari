package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.in.zeusapps.snapsafari.R;

public class BlankActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);
    }

    @OnClick({ R.id.activity_menu_bottom_menu, R.id.activity_menu_menu})
    public void onClick(View view){
        int id = view.getId();

        Class cls = id == R.id.bottom_menu_card_collection
                ? SnapCardsActivity.class
                : ElephantActivity.class;

        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}

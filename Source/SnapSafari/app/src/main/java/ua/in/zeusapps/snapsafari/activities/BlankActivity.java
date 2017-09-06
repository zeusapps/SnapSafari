package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.common.Layout;
import ua.in.zeusapps.snapsafari.controls.Menu;

@Layout(R.layout.activity_menu)
public class BlankActivity extends ActivityBase {
    @BindView(R.id.activity_menu_menu)
    Menu _menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _menu.setOnBackPressedListener(new Menu.IMenuBackPressedListener() {
            @Override
            public void onBackPressed() {
                Intent intent = new Intent(BlankActivity.this, SnapCardsActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick({ R.id.activity_menu_bottom_menu, R.id.activity_menu_menu})
    public void onClick(View view){
        int id = view.getId();

        if (id == R.id.menu_hamburger){
            finish();
            return;
        }

        if (id == R.id.bottom_menu_center){
//            Intent intent = new Intent(this, ElephantActivity.class);
            Intent intent = new Intent(this, ARActivity.class);
            startActivity(intent);
        }
    }
}

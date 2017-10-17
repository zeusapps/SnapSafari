package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import ua.in.zeusapps.snapsafari.App;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.common.Layout;

@Layout(R.layout.activity_register)
public class RegisterActivity extends ActivityBase {
    @BindView(R.id.activity_register_profile_photo)
    ImageView profilePhoto;
    @BindView(R.id.activity_register_name)
    EditText name;
    @BindView(R.id.activity_register_phone)
    EditText phone;
    @BindView(R.id.activity_register_day)
    EditText day;
    @BindView(R.id.activity_register_month)
    EditText month;
    @BindView(R.id.activity_register_year)
    EditText year;
    @BindView(R.id.activity_register_register)
    Button registerButton;

    @OnClick(R.id.activity_register_register)
    public void onRegister(){
        getSharedPreferences(App.TAG, MODE_APPEND)
                .edit()
                .putBoolean(App.REGISTERED, true)
                .apply();

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}

package ua.in.zeusapps.snapsafari.controls;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.in.zeusapps.snapsafari.R;

public class Menu extends FrameLayout {

    @BindView(R.id.menu_hamburger)
    ImageButton _menuButton;

    public Menu(Context context) {
        super(context);
        init();
    }

    public Menu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Menu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Menu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.menu, this);

        ButterKnife.bind(this, this);
    }
}

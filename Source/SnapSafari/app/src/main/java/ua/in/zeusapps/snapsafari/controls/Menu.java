package ua.in.zeusapps.snapsafari.controls;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.in.zeusapps.snapsafari.R;

public class Menu extends FrameLayout {

    private IMenuBackPressedListener _backPressedListener;

    @BindView(R.id.menu_hamburger)
    ImageButton _menuButton;
    @BindView(R.id.menu_back)
    ImageButton _backButton;
    @BindView(R.id.menu_title)
    TextView _titleTextView;

    public Menu(Context context) {
        super(context);
        init(null);
    }

    public Menu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public Menu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Menu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        boolean isDark = false;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.menu, this);

        ButterKnife.bind(this, this);

        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.Menu);
            int value =array.getInt(R.styleable.Menu_style, 0);
            isDark = value == 1;

            String title = array.getString(R.styleable.Menu_title);
            _titleTextView.setText(title);
        }

        if (!isDark){
            return;
        }

        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorMenuDark));
        _titleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorLabel));
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.menu_dark);
        _menuButton.setImageBitmap(icon);
        Bitmap back = BitmapFactory.decodeResource(getResources(), R.drawable.menu_back_dark);
        _backButton.setImageBitmap(back);

        _backPressedListener = new IMenuBackPressedListener() {
            @Override
            public void onBackPressed() {
                try {
                    Activity activity = (Activity) getContext();
                    if (activity != null) {
                        activity.finish();
                    }
                } catch (Throwable throwable) {
                    //
                }
            }
        };
    }

    @OnClick(R.id.menu_back)
    public void onBackPressed(){
        if (_backPressedListener != null){
            _backPressedListener.onBackPressed();
        }
    }

    public void setOnClickListener(OnClickListener listener){
        _menuButton.setOnClickListener(listener);
    }

    public ImageButton getHamburgerButton() {
        return _menuButton;
    }

    public TextView getTitleTextView(){
        return _titleTextView;
    }

    public void setTitle(String title) {
        _titleTextView.setText(title);
    }

    public void setOnBackPressedListener(IMenuBackPressedListener listener){
        _backPressedListener = listener;
    }

    public interface IMenuBackPressedListener {
        void onBackPressed();
    }
}

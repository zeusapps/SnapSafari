package ua.in.zeusapps.snapsafari.controls;

import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.in.zeusapps.snapsafari.R;

public class BottomMenu extends FrameLayout {
    @BindView(R.id.bottom_menu_card_collection)
    ImageButton _cardCollectionButton;
    @BindView(R.id.bottom_menu_take_picture)
    ImageButton _takePictureButton;
    @BindView(R.id.bottom_menu_position)
    ImageButton _positionButton;

    public BottomMenu(@NonNull Context context) {
        super(context);

        init();
    }

    public BottomMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public BottomMenu(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BottomMenu(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(getContext());

        inflater.inflate(R.layout.botton_menu, this);

        ButterKnife.bind(this, this);
    }
}

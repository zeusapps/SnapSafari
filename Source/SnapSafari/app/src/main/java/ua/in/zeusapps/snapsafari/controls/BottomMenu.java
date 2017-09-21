package ua.in.zeusapps.snapsafari.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Attr;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.in.zeusapps.snapsafari.R;

public class BottomMenu extends FrameLayout {
    @BindView(R.id.bottom_menu_left)
    ImageButton _cardCollectionButton;
    @BindView(R.id.bottom_menu_right)
    ImageButton _takePictureButton;
    @BindView(R.id.bottom_menu_center)
    ImageButton _positionButton;

    public BottomMenu(@NonNull Context context) {
        super(context);

        init(null);
    }

    public BottomMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public BottomMenu(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BottomMenu(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(AttributeSet attrs){
        LayoutInflater inflater = LayoutInflater.from(getContext());

        inflater.inflate(R.layout.botton_menu, this);

        ButterKnife.bind(this, this);

        boolean isNormal = false;

        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BottomMenu);
            int value = array.getInt(R.styleable.BottomMenu_type, 1);
            isNormal= value == 0;
        }

        if (isNormal){
            Picasso.with(getContext())
                    .load(R.drawable.collection)
                    .into(getCardCollectionButton());
            Picasso.with(getContext())
                    .load(R.drawable.position)
                    .into(getPositionButton());
            Picasso.with(getContext())
                    .load(R.drawable.take_photo)
                    .into(getTakePictureButton());
        }
    }

    public void setOnClickListener(OnClickListener listener){
        _cardCollectionButton.setOnClickListener(listener);
        _takePictureButton.setOnClickListener(listener);
        _positionButton.setOnClickListener(listener);
    }

    public ImageButton getCardCollectionButton() {
        return _cardCollectionButton;
    }

    public ImageButton getTakePictureButton() {
        return _takePictureButton;
    }

    public ImageButton getPositionButton() {
        return _positionButton;
    }
}

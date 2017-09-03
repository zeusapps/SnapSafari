package ua.in.zeusapps.snapsafari.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ua.in.zeusapps.snapsafari.App;
import ua.in.zeusapps.snapsafari.common.Layout;

public abstract class FragmentBase extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Class cls = getClass();
        if (cls.isAnnotationPresent(Layout.class)){
            Layout layout = (Layout) cls.getAnnotation(Layout.class);
            View view = inflater.inflate(layout.value(), container, false);
            ButterKnife.bind(this, view);
            init();
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void init() {

    }

    public App getApp() {
        return (App) getActivity().getApplication();
    }
}

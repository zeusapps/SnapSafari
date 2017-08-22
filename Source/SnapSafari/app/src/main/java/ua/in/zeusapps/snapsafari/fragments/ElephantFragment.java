package ua.in.zeusapps.snapsafari.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.in.zeusapps.snapsafari.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ElephantFragment extends Fragment {
    @BindView(R.id.fragment_elephant_recyclerView)
    RecyclerView _recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_elephant, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }
    private void init(){
        _recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        _recyclerView.setAdapter(new Adapter());
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(getContext()).inflate(R.layout.item_template_elephant, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 9;
        }
    }

}

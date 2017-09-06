package ua.in.zeusapps.snapsafari.fragments;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.activities.PromoActivity;
import ua.in.zeusapps.snapsafari.common.Layout;
import ua.in.zeusapps.snapsafari.models.SnappedCard;

@Layout(R.layout.fragment_promo)
public class PromoFragment extends FragmentBase {
    @BindView(R.id.fragment_promo_recyclerView)
    RecyclerView _recyclerView;
    Adapter _adapter = new Adapter();

    @Override
    protected void init() {
        _recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        _recyclerView.setAdapter(_adapter);
    }

    public void addCards(List<SnappedCard> cards){
        _adapter.addCards(cards);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_template_promo_card_image)
        ImageView _cardImage;
        @BindView(R.id.item_template_promo_image)
        ImageView _image;
        @BindView(R.id.item_template_promo_title)
        TextView _title;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void update(final SnappedCard card){
            Picasso
                    .with(getContext())
                    .load(getApp().getUri(card.getCard().getImage()))
                    .into(_cardImage);
            Picasso
                    .with(getContext())
                    .load(getApp().getUri(card.getCard().getPromo().getImage()))
                    .into(_image);
            _title.setText(card.getCard().getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), PromoActivity.class);
                    intent.putExtra(PromoActivity.CARD_EXTRA, card.getCard());

                    getActivity().startActivity(intent);
                }
            });
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<SnappedCard> _snappedCards = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(getContext()).inflate(R.layout.item_template_promo, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.update(_snappedCards.get(position));
        }

        @Override
        public int getItemCount() {
            return _snappedCards.size();
        }

        public void addCards(List<SnappedCard> cards){
            _snappedCards.clear();
            for (SnappedCard card:cards) {
                if (card.getCard().getPromo() != null){
                    _snappedCards.add(card);
                }
            }

            notifyDataSetChanged();
        }
    }
}

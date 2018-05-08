package zhengc.bcit.ca.benehome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PlaceViewHolder>{

    private MainActivity context;
    private ArrayList<Place> places;

    RecyclerView mRecyclerView;

    RVAdapter( MainActivity context, ArrayList formlist){
        this.context = context;
        places = formlist;
    }




    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView news_photo;
        TextView news_title;
        TextView news_desc;
        Button map;
        Button readMore;

        public PlaceViewHolder(final View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            news_photo = (ImageView) itemView.findViewById(R.id.location_photo);
            news_title = (TextView) itemView.findViewById(R.id.location_name);
            news_desc = (TextView) itemView.findViewById(R.id.location_desc);
            map = (Button) itemView.findViewById(R.id.btn_map);
            readMore = (Button) itemView.findViewById(R.id.btn_more);
            //Set the TextView background -->opacity
            news_title.setBackgroundColor(Color.argb(20, 0, 0, 0));

        }
    }
    @Override
    public RVAdapter.PlaceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.cardview,viewGroup,false);
        PlaceViewHolder nvh=new PlaceViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(RVAdapter.PlaceViewHolder placeViewHolder, int i) {
        final int j=i;

        Picasso.get().load(places.get(i).getPicUrl()).fit().centerCrop().into(placeViewHolder.news_photo);
        placeViewHolder.news_title.setText(places.get(i).getName());
        placeViewHolder.news_desc.setText(places.get(i).getDesc());

        //set the on clilk listener to the btn_share btn_readMore(cardView)
        placeViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        placeViewHolder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.set_item_uncheck(0);
                context.pass_to_map(places.get(j));
            }
        });

        placeViewHolder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(context,House_detail.class);
//
//                intent.putExtra("house", places.get(5));
//
//                context.startActivity(intent);



                context.slide_expanded(new House_detail(),places.get(j));
            }
        });


    }

    @Override
    public int getItemCount() {
        return places.size();
    }
}

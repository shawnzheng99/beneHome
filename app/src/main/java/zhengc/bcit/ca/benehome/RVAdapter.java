package zhengc.bcit.ca.benehome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import static java.lang.Thread.sleep;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PlaceViewHolder>{

    private MainActivity context;
    private ArrayList<Place> places;

    private RecyclerView mRecyclerView;

    RVAdapter( MainActivity context, ArrayList<Place> formlist){
        this.context = context;
        places = formlist;

    }




    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }

    static class PlaceViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView news_photo;
        TextView news_title;
        TextView news_desc;
        TextView total_unit;
        Button map;

        PlaceViewHolder(final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            news_photo = itemView.findViewById(R.id.location_photo);
            news_title = itemView.findViewById(R.id.location_name);
            news_desc = itemView.findViewById(R.id.location_location);
            total_unit = itemView.findViewById(R.id.location_total_unit);
        }
    }
    @Override
    public RVAdapter.PlaceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.cardview,viewGroup,false);
        return new PlaceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RVAdapter.PlaceViewHolder placeViewHolder, int i) {
        // get first image with name
        Picasso.get().load(places.get(i).getUrl().get("a")).placeholder(R.drawable.animated_rotate_drawable).fit().centerCrop().into(placeViewHolder.news_photo);

        placeViewHolder.news_title.setText(places.get(i).getName());
        placeViewHolder.news_desc.setText(places.get(i).getLocation() + ", " + places.get(i).getPC());
//        placeViewHolder.total_unit.setText(String.format("Total unit: %s", places.get(i).getTotalUnit()));
        //context.getSupportFragmentManager().beginTransaction().detach(f).attach(f).commitAllowingStateLoss();
        //set the on clilk listener to the cardView
        placeViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.show_pass(new House_detail(), null, places.get(placeViewHolder.getAdapterPosition()));
                context.setTitle("House Detail");
            }
        });

//        placeViewHolder.map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.set_item_uncheck(0);
//                context.pass_to_map(places.get(j));
//            }
//        });
//
//        placeViewHolder.readMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.slide_expanded(new House_detail(),places.get(j));
//            }
//        });


    }

    @Override
    public int getItemCount() {
        if(places!=null){
            return places.size();
        }else{
            return 0;
        }

    }
}

package zhengc.bcit.ca.benehome;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


public class DetailAdapter extends BaseAdapter {

    private ArrayList<String> fillin;
    private int [] imageId;
    private static LayoutInflater inflater=null;

    DetailAdapter(@NonNull Activity context, ArrayList<String> input, int[] id) {

        this.fillin = input;
        this.imageId = id;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fillin.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView==null) {

            vi = inflater.inflate(R.layout.housedetaillist, null);
        }
        ImageView iv = vi.findViewById(R.id.appIconIV);
        TextView tv = vi.findViewById(R.id.aNametxt);
        iv.setImageResource(imageId[position % 4]);
        tv.setText(fillin.get(position));
        return vi;
    }


}

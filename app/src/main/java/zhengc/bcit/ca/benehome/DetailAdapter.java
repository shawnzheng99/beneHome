package zhengc.bcit.ca.benehome;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class DetailAdapter extends BaseAdapter {

    Activity context;
    ArrayList<String> fillin;
    int [] imageId;
    private static LayoutInflater inflater=null;

    public DetailAdapter(@NonNull Activity context, ArrayList<String> input, int [] id) {


        this.context = context;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView==null) {

            vi = inflater.inflate(R.layout.housedetaillist, null);
        }
        ImageView iv = (ImageView) vi.findViewById(R.id.appIconIV);
        TextView tv = (TextView) vi.findViewById(R.id.aNametxt);
        iv.setImageResource(imageId[position]);
        tv.setText(fillin.get(position));
        return vi;
    }


}

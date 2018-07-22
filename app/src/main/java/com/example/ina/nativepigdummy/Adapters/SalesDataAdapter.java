package com.example.ina.nativepigdummy.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ina.nativepigdummy.Data.MortalityData;
import com.example.ina.nativepigdummy.Data.SalesData;
import com.example.ina.nativepigdummy.R;

import java.util.ArrayList;

public class SalesDataAdapter extends ArrayAdapter<SalesData> {
    private static final String TAG = "SalesDataAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView sales_id;
        TextView date_sold;
        TextView weight;
        TextView age;
    }

    public SalesDataAdapter(Context context, int resource, ArrayList<SalesData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String sales_id = getItem(position).getSales_reg_id();
        String date_sold = getItem(position).getDate_sold();
        String weight = getItem(position).getWeight();
        String age = getItem(position).getAge();


        SalesData sales = new SalesData(sales_id,date_sold,weight,age);

        final View result;

        SalesDataAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new SalesDataAdapter.ViewHolder();
            holder.sales_id = (TextView) convertView.findViewById(R.id.textView1);
            holder.date_sold = (TextView) convertView.findViewById(R.id.textView2);
            holder.weight = (TextView) convertView.findViewById(R.id.textView3);
            holder.age = (TextView) convertView.findViewById(R.id.textView4);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (SalesDataAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.sales_id.setText(sales.getSales_reg_id());
        holder.date_sold.setText(sales.getDate_sold());
        holder.weight.setText(sales.getWeight());
        holder.age.setText(sales.getAge());

        return convertView;
    }
}

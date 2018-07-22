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
import com.example.ina.nativepigdummy.Data.OthersData;
import com.example.ina.nativepigdummy.R;

import java.util.ArrayList;

public class OthersDataAdapter extends ArrayAdapter<OthersData> {

    private static final String TAG = "OthersDataAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView others_id;
        TextView date_removed;
        TextView reason;
        TextView age;
    }

    public OthersDataAdapter(Context context, int resource, ArrayList<OthersData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String others_id = getItem(position).getOthers_reg_id();
        String date_removed = getItem(position).getDate_removed();
        String reason = getItem(position).getReason();
        String age = getItem(position).getAge();


        OthersData others = new OthersData(others_id,date_removed,reason,age);

        final View result;

        OthersDataAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new OthersDataAdapter.ViewHolder();
            holder.others_id = (TextView) convertView.findViewById(R.id.textView1);
            holder.date_removed = (TextView) convertView.findViewById(R.id.textView2);
            holder.reason = (TextView) convertView.findViewById(R.id.textView3);
            holder.age = (TextView) convertView.findViewById(R.id.textView4);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (OthersDataAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.others_id.setText(others.getOthers_reg_id());
        holder.date_removed.setText(others.getDate_removed());
        holder.reason.setText(others.getReason());
        holder.age.setText(others.getAge());

        return convertView;
    }

}

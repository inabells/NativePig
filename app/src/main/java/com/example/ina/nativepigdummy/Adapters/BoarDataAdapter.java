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

import com.example.ina.nativepigdummy.Data.BoarData;
import com.example.ina.nativepigdummy.R;

import java.util.ArrayList;

public class BoarDataAdapter extends ArrayAdapter<BoarData> {

    private static final String TAG = "BoarDataAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView boar_id;
    }

    public BoarDataAdapter(Context context, int resource, ArrayList<BoarData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String boar_id = getItem(position).getBoar_reg_id();


        BoarData boar = new BoarData(boar_id);

        final View result;

        BoarDataAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new BoarDataAdapter.ViewHolder();
            holder.boar_id = (TextView) convertView.findViewById(R.id.textView1);


            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (BoarDataAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.boar_id.setText(boar.getBoar_reg_id());



        return convertView;
    }
}

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

import com.example.ina.nativepigdummy.Data.BreedingRecordData;
import com.example.ina.nativepigdummy.Data.MortalityData;
import com.example.ina.nativepigdummy.R;

import java.util.ArrayList;

public class BreedingRecordDataAdapter extends ArrayAdapter<BreedingRecordData> {
    private static final String TAG = "BreedingRecordDataAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView sow_id;
        TextView boar_id;
        TextView date_bred;
    }

    public BreedingRecordDataAdapter(Context context, int resource, ArrayList<BreedingRecordData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String sow_id = getItem(position).getSow_id();
        String boar_id = getItem(position).getBoar_id();
        String date_bred = getItem(position).getDate_bred();

        BreedingRecordData breedingrecord = new BreedingRecordData(sow_id,boar_id,date_bred);

        final View result;

        BreedingRecordDataAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new BreedingRecordDataAdapter.ViewHolder();
            holder.sow_id = (TextView) convertView.findViewById(R.id.textView1);
            holder.boar_id = (TextView) convertView.findViewById(R.id.textView2);
            holder.date_bred = (TextView) convertView.findViewById(R.id.textView3);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (BreedingRecordDataAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.sow_id.setText(breedingrecord.getSow_id());
        holder.boar_id.setText(breedingrecord.getBoar_id());
        holder.date_bred.setText(breedingrecord.getDate_bred());

        return convertView;
    }
}

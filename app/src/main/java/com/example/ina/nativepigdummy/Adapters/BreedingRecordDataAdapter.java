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

import org.w3c.dom.Text;

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
        TextView edf;
        TextView status;
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
        String edf = getItem(position).getEdf();
        String status = getItem(position).getStatus();

        BreedingRecordData breedingrecord = new BreedingRecordData(sow_id,boar_id,date_bred,edf,status);

        final View result;

        BreedingRecordDataAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new BreedingRecordDataAdapter.ViewHolder();
            holder.sow_id = (TextView) convertView.findViewById(R.id.textView1);
            holder.boar_id = (TextView) convertView.findViewById(R.id.textView2);
            holder.date_bred = (TextView) convertView.findViewById(R.id.textView3);
            holder.edf = (TextView) convertView.findViewById(R.id.textView4);
            holder.status = (TextView) convertView.findViewById(R.id.textView5);


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
        holder.edf.setText(breedingrecord.getEdf());
        holder.status.setText(breedingrecord.getStatus());

        return convertView;
    }
}

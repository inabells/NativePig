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
import com.example.ina.nativepigdummy.Data.OffspringData;
import com.example.ina.nativepigdummy.R;

import java.util.ArrayList;

public class OffspringDataAdapter extends ArrayAdapter<OffspringData> {

    private static final String TAG = "OffspringDataAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView offspring_id;
        TextView sex;
        TextView birthweight;
        TextView weaningweight;
    }

    public OffspringDataAdapter(Context context, int resource, ArrayList<OffspringData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String offspring_id = getItem(position).getOffspring_id();
        String sex = getItem(position).getSex();
        String birthweight = getItem(position).getBirthweight();
        String weaningweight = getItem(position).getWeaningweight();


        OffspringData mortality = new OffspringData(offspring_id,sex,birthweight,weaningweight);

        final View result;

        OffspringDataAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new OffspringDataAdapter.ViewHolder();
            holder.offspring_id = (TextView) convertView.findViewById(R.id.textView1);
            holder.sex = (TextView) convertView.findViewById(R.id.textView2);
            holder.birthweight = (TextView) convertView.findViewById(R.id.textView3);
            holder.weaningweight = (TextView) convertView.findViewById(R.id.textView4);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (OffspringDataAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.offspring_id.setText(mortality.getOffspring_id());
        holder.sex.setText(mortality.getSex());
        holder.birthweight.setText(mortality.getBirthweight());
        holder.weaningweight.setText(mortality.getWeaningweight());

        return convertView;
    }

}

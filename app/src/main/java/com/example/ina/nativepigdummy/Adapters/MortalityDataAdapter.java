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
import com.example.ina.nativepigdummy.R;

import java.util.ArrayList;

public class MortalityDataAdapter extends ArrayAdapter<MortalityData> {
    private static final String TAG = "MortalityDataAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView mortality_id;
        TextView date_of_death;
        TextView cause_of_death;
        TextView age;
    }

    public MortalityDataAdapter(Context context, int resource, ArrayList<MortalityData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String mortality_id = getItem(position).getMortality_reg_id();
        String date_of_death = getItem(position).getDate_of_death();
        String cause_of_death = getItem(position).getCause_of_death();
        String age = getItem(position).getAge();


        MortalityData mortality = new MortalityData(mortality_id,date_of_death,cause_of_death,age);

        final View result;

        MortalityDataAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new MortalityDataAdapter.ViewHolder();
            holder.mortality_id = (TextView) convertView.findViewById(R.id.textView1);
            holder.date_of_death = (TextView) convertView.findViewById(R.id.textView2);
            holder.cause_of_death = (TextView) convertView.findViewById(R.id.textView3);
            holder.age = (TextView) convertView.findViewById(R.id.textView4);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (MortalityDataAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.mortality_id.setText(mortality.getMortality_reg_id());
        holder.date_of_death.setText(mortality.getDate_of_death());
        holder.cause_of_death.setText(mortality.getCause_of_death());
        holder.age.setText(mortality.getAge());

        return convertView;
    }
}

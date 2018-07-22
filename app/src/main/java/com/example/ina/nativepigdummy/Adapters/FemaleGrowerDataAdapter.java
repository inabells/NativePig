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
import com.example.ina.nativepigdummy.Data.FemaleGrowerData;
import com.example.ina.nativepigdummy.Data.MaleGrowerData;
import com.example.ina.nativepigdummy.R;

import java.util.ArrayList;

public class FemaleGrowerDataAdapter extends ArrayAdapter<FemaleGrowerData>  {

    private static final String TAG = "FemaleGrowerDataAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView female_grower_id;
    }

    public FemaleGrowerDataAdapter(Context context, int resource, ArrayList<FemaleGrowerData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String female_grower_id = getItem(position).getFemale_grower_reg_id();

        FemaleGrowerData male_grower = new FemaleGrowerData(female_grower_id);

        final View result;

        FemaleGrowerDataAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new FemaleGrowerDataAdapter.ViewHolder();
            holder.female_grower_id = (TextView) convertView.findViewById(R.id.textView1);


            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (FemaleGrowerDataAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.female_grower_id.setText(male_grower.getFemale_grower_reg_id());

        return convertView;
    }
}

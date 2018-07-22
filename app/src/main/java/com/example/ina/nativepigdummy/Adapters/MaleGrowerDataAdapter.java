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
import com.example.ina.nativepigdummy.Data.MaleGrowerData;
import com.example.ina.nativepigdummy.R;

import java.util.ArrayList;

public class MaleGrowerDataAdapter extends ArrayAdapter<MaleGrowerData>  {

    private static final String TAG = "MaleGrowerDataAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView male_grower_id;
    }

    public MaleGrowerDataAdapter(Context context, int resource, ArrayList<MaleGrowerData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String male_grower_id = getItem(position).getMale_grower_reg_id();

        MaleGrowerData male_grower = new MaleGrowerData(male_grower_id);

        final View result;

        MaleGrowerDataAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new MaleGrowerDataAdapter.ViewHolder();
            holder.male_grower_id = (TextView) convertView.findViewById(R.id.textView1);


            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (MaleGrowerDataAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.male_grower_id.setText(male_grower.getMale_grower_reg_id());



        return convertView;
    }
}

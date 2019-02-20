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
import com.example.ina.nativepigdummy.Data.GetAllPigsData;
import com.example.ina.nativepigdummy.R;

import java.util.ArrayList;

public class GetAllPigsAdapter extends ArrayAdapter<GetAllPigsData> {

    private static final String TAG = "GetAllPigsData";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView pig_id;
    }

    public GetAllPigsAdapter(Context context, int resource, ArrayList<GetAllPigsData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String pig_id = getItem(position).getPig_id();


        GetAllPigsData pig = new GetAllPigsData(pig_id);

        final View result;

        GetAllPigsAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new GetAllPigsAdapter.ViewHolder();
            holder.pig_id = (TextView) convertView.findViewById(R.id.textView1);


            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (GetAllPigsAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.pig_id.setText(pig.getPig_id());



        return convertView;
    }
}

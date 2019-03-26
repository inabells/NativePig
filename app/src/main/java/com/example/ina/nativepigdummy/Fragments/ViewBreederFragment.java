package com.example.ina.nativepigdummy.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Database.DatabaseHelper;
import com.example.ina.nativepigdummy.Dialog.ViewBreederDialog;
import com.example.ina.nativepigdummy.R;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

import static android.app.Activity.RESULT_OK;


public class ViewBreederFragment extends Fragment implements ViewBreederDialog.ViewBreederListener{

    public ViewBreederFragment() {
        // Required empty public constructor
    }
    private DatabaseHelper dbHelper;
    private TextView TextViewbirthday, TextViewsex, TextViewbirthweight, TextViewweaningweight, TextViewlittersizebornweight, TextViewageatfirstmating, TextViewageatweaning, TextViewpedigreemother, TextViewpedigreefather;
    private ImageView edit_profile;
    private TextView registration_id;
    private String pigRegIdHolder;
    private String birthday, sex, birthweight, weaningweight, littersizebornweight, ageatfirstmating, ageatweaning, pedigreemother, pedigreefather;

    private ImageView imageView;
    private Bitmap bitmap_foto;
    private RoundedBitmapDrawable roundedBitmapDrawable;
    private byte[] bytes;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_breeder, container, false);

        imageView = view.findViewById(R.id.user_image);
        bitmap_foto = BitmapFactory.decodeResource(getResources(),R.drawable.image);
        roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap_foto);
        roundedBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(roundedBitmapDrawable);
        edit_profile = view.findViewById(R.id.edit_view_breeder);
        TextViewbirthday = (TextView) view.findViewById(R.id.textViewBirthday);
        TextViewsex = (TextView) view.findViewById(R.id.textViewSexRatio);
        TextViewbirthweight = (TextView) view.findViewById(R.id.textViewBirthWeight);
        TextViewweaningweight = (TextView) view.findViewById(R.id.textViewWeaningWeight);
        TextViewlittersizebornweight = (TextView) view.findViewById(R.id.textViewLitterAlive);
        TextViewageatfirstmating = (TextView) view.findViewById(R.id.textViewAgeAtMating);
        TextViewageatweaning = (TextView) view.findViewById(R.id.textViewAgeWeaning);
        TextViewpedigreemother = (TextView) view.findViewById(R.id.textViewMotherPedigree);
        TextViewpedigreefather = (TextView) view.findViewById(R.id.textViewFatherPedigree);
        registration_id = view.findViewById(R.id.registration_id);

        dbHelper = new DatabaseHelper(getContext());
        pigRegIdHolder = getActivity().getIntent().getStringExtra("ListClickValue");
        registration_id.setText(pigRegIdHolder);
        RequestParams params = buildParams();


        if(ApiHelper.isInternetAvailable(getContext()))
            api_getSinglePig(params);
        else
            local_getSinglePig(pigRegIdHolder);


        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewBreederDialog(pigRegIdHolder);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = null;
                if(Build.VERSION.SDK_INT < 19){
                    i = new Intent();
                    i.setAction(Intent.ACTION_GET_CONTENT);
                }else{
                    i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                }
                i.setType("image/*");
                startActivityForResult(i, 1);
            }
        });

        return view;
    }

    private RequestParams buildParams() {
        RequestParams params = new RequestParams();
        params.add("pig_registration_id", pigRegIdHolder);
        return params;
    }

    @SuppressLint("SetTextI18n")
    private void local_getSinglePig(String reg_id){
        String birthDate="", weaningDate="";
        Cursor data = dbHelper.getSinglePig(reg_id);
        while (data.moveToNext()) {
            switch(data.getString(data.getColumnIndex("property_id"))) {
                case "3":   birthDate = data.getString(data.getColumnIndex("value"));
                            TextViewbirthday.setText(setDefaultTextIfNull(birthDate));
                            break;
                case "6":   weaningDate = data.getString(data.getColumnIndex("value"));
                            break;
                case "53":  TextViewsex.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                            break;
                case "5":   TextViewbirthweight.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                            break;
                case "7":   TextViewweaningweight.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                            break;
                case "50":  TextViewlittersizebornweight.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                            break;
                case "8":   TextViewpedigreemother.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                            break;
                case "9":   TextViewpedigreefather.setText(setDefaultTextIfNull(data.getString(data.getColumnIndex("value"))));
                            break;
            }

            if(birthDate != null && weaningDate != null && !birthDate.isEmpty() && !weaningDate.isEmpty()){
                if(birthDate.equals("Not specified") || weaningDate.equals("Not specified")){
                    TextViewageatweaning.setText(setDefaultTextIfNull(""));
                }else{
                    try {
                        TextViewageatweaning.setText(computeAgeWeaning(birthDate, weaningDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                TextViewageatweaning.setText(setDefaultTextIfNull(""));
            }
        }
    }

    private String computeAgeWeaning(String birthDate, String weaningDate) throws ParseException {
        String date = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date firstDate = format.parse(birthDate);
        Date secondDate = format.parse(weaningDate);

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        if((Long.toString(diff/30) ).equals("1") && (Long.toString(diff%30)).equals("1")){
            date = Long.toString(diff/30) + " month, " + Long.toString(diff%30) + " day";
        }else if((Long.toString(diff/30).equals("1"))){
            date = Long.toString(diff/30) + " month, " + Long.toString(diff%30) + " days";
        }else if((Long.toString(diff%30).equals("1"))){
            date = Long.toString(diff/30) + " months, " + Long.toString(diff%30) + " day";
        }else{
            date = Long.toString(diff/30) + " months, " + Long.toString(diff%30) + " days";
        }
        return date;
    }

    private void api_getSinglePig(RequestParams params) {
        ApiHelper.getSinglePig("getSinglePig", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                TextViewbirthday.setText(setDefaultTextIfNull(birthday));
                TextViewsex.setText(setDefaultTextIfNull(sex));
                TextViewbirthweight.setText(setDefaultTextIfNull(birthweight));
                TextViewweaningweight.setText(setDefaultTextIfNull(weaningweight));
                TextViewlittersizebornweight.setText(setDefaultTextIfNull(littersizebornweight));
                TextViewageatfirstmating.setText(setDefaultTextIfNull(ageatfirstmating));
                TextViewageatweaning.setText(setDefaultTextIfNull(ageatweaning));
                TextViewpedigreemother.setText(setDefaultTextIfNull(pedigreemother));
                TextViewpedigreefather.setText(setDefaultTextIfNull(pedigreefather));
                Log.d("ViewBreeder", "Successfully added data");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("ViewBreeder", "Error: " + String.valueOf(statusCode));
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONObject jsonObject = new JSONObject(rawJsonData);
                birthday = jsonObject.get("pig_birthdate").toString();
                sex = jsonObject.get("sex_ratio").toString();
                birthweight = jsonObject.get("pig_birthweight").toString();
                weaningweight = jsonObject.get("pig_weaningweight").toString();
                littersizebornweight = jsonObject.get("litter_size_born_alive").toString();
                ageatfirstmating = jsonObject.get("age_first_mating").toString();
                ageatweaning = jsonObject.get("age_at_weaning").toString();
                pedigreemother = jsonObject.get("pig_mother_earnotch").toString();
                pedigreefather = jsonObject.get("pig_father_earnotch").toString();
                return null;
            }
        });
    }

    private String setDefaultTextIfNull(String text) {
        return ((text==null || text.equals("null") || text.equals("") || text.isEmpty()) ? "Not specified" : text);
    }

    public void openViewBreederDialog(String pigRegId){
        ViewBreederDialog dialog = new ViewBreederDialog(pigRegId);
        dialog.setTargetFragment(ViewBreederFragment.this, 1);
        dialog.show(getFragmentManager(),"ViewBreederDialog");
    }

    private byte[] imageToByte(ImageView image) {
        Bitmap bitmapFoto = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapFoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == 1){
            imageView.setImageURI(data.getData());
            bytes = imageToByte(imageView);

            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            imageView.setImageDrawable(roundedBitmapDrawable);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}



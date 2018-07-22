package com.example.ina.nativepigdummy.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.DialogFragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ina.nativepigdummy.Dialog.ViewBreederDialog;
import com.example.ina.nativepigdummy.Dialog.ViewProfileDialog;
import com.example.ina.nativepigdummy.R;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;


public class ViewBreederFragment extends Fragment implements ViewBreederDialog.ViewBreederListener{

    public ViewBreederFragment() {
        // Required empty public constructor
    }
    private TextView TextViewbirthday;
    private TextView TextViewsex;
    private TextView TextViewbirthweight;
    private TextView TextViewweaningweight;
    private TextView TextViewlittersizebornweight;
    private TextView TextViewageatfirstmating;
    private TextView TextViewageatweaning;
    private TextView TextViewpedigreemother;
    private TextView TextViewpedigreefather;
    private ImageView edit_profile;
    private TextView registration_id;

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

        String tempholder = getActivity().getIntent().getStringExtra("ListClickValue");
        registration_id.setText(tempholder);


        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewBreederDialog();
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

    public void openViewBreederDialog(){
        ViewBreederDialog dialog = new ViewBreederDialog();
        dialog.setTargetFragment(ViewBreederFragment.this, 1);
        dialog.show(getFragmentManager(),"ViewBreederDialog");
    }

    @Override public void applyBirthdayText(String birthday){ TextViewbirthday.setText(birthday); }

    @Override public void applySexText(String sex){ TextViewsex.setText(sex); }

    @Override public void applyBirthWeightText(String birthweight){ TextViewbirthweight.setText(birthweight); }

    @Override public void applyWeaningWeight(String weaningweight){ TextViewweaningweight.setText(weaningweight); }

    @Override public void applyLitterSize(String littersizebornweight){ TextViewlittersizebornweight.setText(littersizebornweight); }

    @Override public void applyAgeMating(String ageatfirstmating){ TextViewageatfirstmating.setText(ageatfirstmating); }

    @Override public void applyAgeWeaning(String ageatweaning){ TextViewageatweaning.setText(ageatweaning); }

    @Override public void applyMother(String pedigreemother){ TextViewpedigreemother.setText(pedigreemother); }

    @Override public void applyFather(String pedigreefather){ TextViewpedigreefather.setText(pedigreefather); }

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



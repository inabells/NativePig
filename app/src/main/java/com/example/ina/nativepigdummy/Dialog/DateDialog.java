package com.example.ina.nativepigdummy.Dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    EditText birthdate;
    EditText weandate;
    EditText datecollected;
    EditText dateoffarrowing;
    EditText datefarrowed;
    EditText dateofdeath;
    EditText datesold;
    EditText dateremoved;

    public DateDialog(){

    }

    public DateDialog(View view){
        birthdate = (EditText) view;
        weandate = (EditText) view;
        datecollected = (EditText) view;
        dateoffarrowing = (EditText) view;
        datefarrowed = (EditText) view;
        dateofdeath = (EditText) view;
        datesold = (EditText) view;
        dateremoved = (EditText) view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day){
        String date = year + "-" + (month+1) + "-" + day;
        birthdate.setText(date);
        weandate.setText(date);
        datecollected.setText(date);
        dateoffarrowing.setText(date);
        datefarrowed.setText(date);
        dateofdeath.setText(date);
        datesold.setText(date);
        dateremoved.setText(date);
    }
}

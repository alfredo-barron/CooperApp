package com.alfredobarron.examen;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alfredobarron.examen.model.Counts;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class NewCount extends Activity {

    ActionBar actionBar;
    @InjectView(R.id.name)
    EditText txtName;
    @InjectView(R.id.lot)
    EditText txtLot;
    @InjectView(R.id.txtDate)
    TextView tvDisplayDate;
    @InjectView(R.id.txtTime)
    TextView tvDisplayTime;

    private int year, month, day, hour, minute;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_count);
        ButterKnife.inject(this);
        actionBar = getActionBar();
        setCurrentDateView();
        setCurrentTimeView();
        addListenerDateView();
        addListenerTimeView();
    }

    public void setCurrentDateView() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        String m = "";
        switch (month + 1){
            case 1: m = "Enero";
                break;
            case 2: m = "Febrero";
                break;
            case 3: m = "Marzo";
                break;
            case 4: m = "Abril";
                break;
            case 5: m = "Mayo";
                break;
            case 6: m = "Junio";
                break;
            case 7: m = "Julio";
                break;
            case 8: m = "Agosto";
                break;
            case 9: m = "Septiembre";
                break;
            case 10: m = "Octubre";
                break;
            case 11: m = "Noviembre";
                break;
            case 12: m = "Diciembre";
                break;
        }
        day = c.get(Calendar.DAY_OF_MONTH);

        tvDisplayDate.setText(new StringBuilder()
                .append(day).append(" de ").append(m).append(", ")
                .append(year).append(" "));
    }

    public void setCurrentTimeView() {
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        tvDisplayTime.setText(new StringBuilder()
                .append(hour).append(":").append(minute)
                .append(" "));
    }

    public void addListenerDateView() {
        tvDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    public void addListenerTimeView() {
        tvDisplayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener, year, month, day);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, timePickerListener, hour, minute, false);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth,
                              int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            String m = "";
            switch (month + 1){
                case 1: m = "Enero";
                    break;
                case 2: m = "Febrero";
                    break;
                case 3: m = "Marzo";
                    break;
                case 4: m = "Abril";
                    break;
                case 5: m = "Mayo";
                    break;
                case 6: m = "Junio";
                    break;
                case 7: m = "Julio";
                    break;
                case 8: m = "Agosto";
                    break;
                case 9: m = "Septiembre";
                    break;
                case 10: m = "Octubre";
                    break;
                case 11: m = "Noviembre";
                    break;
                case 12: m = "Diciembre";
                    break;
            }
            day = selectedDay;

            tvDisplayDate.setText(new StringBuilder()
                    .append(day).append(" de ").append(m).append(", ")
                    .append(year).append(" "));
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;
            tvDisplayTime.setText(new StringBuilder()
                    .append(hour).append(":").append(minute)
                    .append(" "));
        }
    };

    public void saveCount(View v) {
        if (txtName.getText().length() == 0 || txtLot.getText().length() == 0 ||
                tvDisplayDate.getText().length() == 0 || tvDisplayTime.getText().length() == 0) {
            Crouton.showText(this, "Rellene todos los datos", Style.ALERT);
        } else {
            String name = txtName.getText().toString();
            int lot = Integer.parseInt(txtLot.getText().toString());
            String date = tvDisplayDate.getText().toString();
            String time = tvDisplayTime.getText().toString();
            boolean available = false;
            Counts c = new Counts(name, lot, date, time, available);
            c.save();
            Toast.makeText(this, "Cuenta guardada", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}

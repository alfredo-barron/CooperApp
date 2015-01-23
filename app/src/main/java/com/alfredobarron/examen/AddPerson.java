package com.alfredobarron.examen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alfredobarron.examen.model.Users;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class AddPerson extends Activity {

    @InjectView(R.id.name_person)
    EditText editTextNamePerson;
    @InjectView(R.id.lot_person)
    EditText editTextLotPerson;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        ButterKnife.inject(this);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            id = extras.getLong("id");
        }
    }

    public void savePerson(View v) {
        if (editTextNamePerson.getText().length() == 0 || editTextLotPerson.getText().length() == 0) {
            Crouton.showText(this, "Rellene todos los datos", Style.ALERT);
        } else {
            String name = editTextNamePerson.getText().toString();
            int lot = Integer.parseInt(editTextLotPerson.getText().toString());
            Users us = new Users(name, lot, id);
            us.save();
            Toast.makeText(this, "Persona agregada", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


}

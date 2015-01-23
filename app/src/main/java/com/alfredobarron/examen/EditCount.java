package com.alfredobarron.examen;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.alfredobarron.examen.model.Counts;


import butterknife.ButterKnife;
import butterknife.InjectView;

public class EditCount extends Activity {

    @InjectView(R.id.name_edit)
    EditText editTextName;
    @InjectView(R.id.lot_edit)
    EditText editTextLot;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_count);
        ButterKnife.inject(this);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            id = extras.getLong("id");
        }
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Editar");
        actionBar.setIcon(R.drawable.ic_action_edit);
        Counts count = Counts.findById(Counts.class, id);
        editTextName.setText(count.getName());
        editTextLot.setText(Integer.toString(count.getLot()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_count, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            long id1 = 0;
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                id1 = extras.getLong("id");
            }
            Counts count = Counts.findById(Counts.class, id1);
            count.setName(editTextName.getText().toString());
            count.setLot(Integer.parseInt(editTextLot.getText().toString()));
            count.save();
            Intent i = new Intent(this, DetailCount.class);
            i.putExtra("id",id1);
            startActivity(i);
            finish();
            return true;
        }
        if (id == R.id.action_cancel) {
            long id1 = 0;
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                id1 = extras.getLong("id");
            }
            Intent i = new Intent(this, DetailCount.class);
            i.putExtra("id",id1);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            long id1 = 0;
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                id1 = extras.getLong("id");
            }
            Intent i = new Intent(this, DetailCount.class);
            i.putExtra("id",id1);
            startActivity(i);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}

package com.alfredobarron.examen;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alfredobarron.examen.adapter.UsersAdapter;
import com.alfredobarron.examen.dialogs.AlertDialogFragment;
import com.alfredobarron.examen.dialogs.AlertInformation;
import com.alfredobarron.examen.model.Counts;
import com.alfredobarron.examen.model.Users;
import com.alfredobarron.examen.util.AlertDialogUtil;
import com.alfredobarron.examen.util.AlertInformationUtil;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.alfredobarron.examen.R.*;


public class DetailCount extends Activity {
    private final String TAG = Main.class.getSimpleName();
    ActionBar actionBar;
    long id;
    private UsersAdapter adapter;
    @InjectView(R.id.total)
    TextView totalTextView;
    @InjectView(R.id.list)
    ListView list;

    @Override
    protected void onResume() {
        super.onResume();
        List<Users> users = Select.from(Users.class).where(Condition.prop("count").eq(id)).list();
        adapter.setUsers(users);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_detail_count);
        ButterKnife.inject(this);
        actionBar = getActionBar();
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            id = extras.getLong("id");
            Counts count = Counts.findById(Counts.class, id);
            totalTextView.setText("$ "+count.getLot());
            actionBar.setTitle(count.getName());
            actionBar.setSubtitle(count.getDate());
        }
       List<Users> users = Select.from(Users.class).where(Condition.prop("count").eq(id)).list();
       adapter = new UsersAdapter(this, users, id);
       list.setEmptyView(findViewById(android.R.id.empty));
       list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Users item = (Users) parent.getItemAtPosition(position);
                final AlertInformation.Callback questionCallback;
                final Users user = Users.findById(Users.class, item.getId());
                TextView lotText = (TextView) view.findViewById(R.id.lot_must);
                String cant = lotText.getText().toString();
                String frase = "";
                String m = "";
                char n = '-';
                if(cant != " ") {
                    if(n == cant.charAt(0)){
                        int total = Integer.parseInt(cant.replace("- $ ", m));
                        frase = "Usted debe: "+total+ " pesos";
                    } else {
                        int total1 = Integer.parseInt(cant.replace("+ $ ", m));
                        frase = "A usted le debemos: "+total1+ " pesos";
                    }
                } else{
                    frase = "Usted no debe nada";
                }
                questionCallback = new AlertInformation.Callback() {
                    @Override
                    public void onPositiveButtonClicked() {

                    }
                };

                AlertInformationUtil.showDialog(
                        getFragmentManager(),
                        TAG,
                        questionCallback,
                        getString(string.name_count).replace("{name}", user.getName()),
                        getString(string.lot_user).replace("{lot}", Integer.toString(user.getLot())).replace("{cant}", frase),
                        getString(string.ok));
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Users item = (Users) parent.getItemAtPosition(position);
                final AlertDialogFragment.Callback questionCallback;
                final Users user = Users.findById(Users.class, item.getId());
                questionCallback = new AlertDialogFragment.Callback() {
                    @Override
                    public void onPositiveButtonClicked() {
                        user.delete();
                        adapter.remove(position);
                    }

                    @Override
                    public void onCancel() {
                        // just dismiss, to nothing.
                    }
                };

                AlertDialogUtil.showDialog(
                        getFragmentManager(),
                        TAG,
                        questionCallback,
                        getString(string.tCountDeleteTitle),
                        getString(string.tUserDeleteMessage).replace("{count}", user.getName()),
                        getString(string.tCountDeletePositive),
                        getString(string.tCountDeleteNegative));

                return true;
                }

            });
    }

    public void addPerson(View v) {
        Intent intent = new Intent(this, AddPerson.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_count, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            deleteCount();
            return true;
        }
        if (id == R.id.action_accept) {
            acceptCount();
            return true;
        }
        if (id == R.id.action_edit) {
            long id1 = 0;
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
               id1 = extras.getLong("id");
            }
            Intent intent = new Intent(this, EditCount.class);
            intent.putExtra("id", id1);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteCount(){
        final AlertDialogFragment.Callback questionCallback;
        final Counts count = Counts.findById(Counts.class, id);
        questionCallback = new AlertDialogFragment.Callback() {
            @Override
            public void onPositiveButtonClicked() {
                count.delete();
                finish();
            }

            @Override
            public void onCancel() {
                // just dismiss, to nothing.
            }
        };

        AlertDialogUtil.showDialog(
                getFragmentManager(),
                TAG,
                questionCallback,
                getString(string.tCountDeleteTitle),
                getString(string.tCountDeleteMessage).replace("{count}", count.getName()),
                getString(string.tCountDeletePositive),
                getString(string.tCountDeleteNegative));
    }

    public void acceptCount() {
        Counts count = Counts.findById(Counts.class, id);
        count.setAvailable(true);
        count.save();

        final AlertInformation.Callback questionCallback;
        questionCallback = new AlertInformation.Callback() {
            @Override
            public void onPositiveButtonClicked() {
                finish();
            }
        };

        AlertInformationUtil.showDialog(
                getFragmentManager(),
                TAG,
                questionCallback,
                getString(string.name_count).replace("{name}", count.getName()),
                getString(string.message),
                getString(string.ok));

    }

   /*  public void editCount() {

       final Counts count = Counts.findById(Counts.class, id);
        final DialogEditCount.Callback questionCallback;
        questionCallback = new DialogEditCount.Callback() {
            @Override
            public void onPositiveButtonClicked() {
                count.save();
            }

            @Override
            public void onCancel() {

            }
        };

        DialogEditCountUtil.showDialog(
                getFragmentManager(),
                TAG,
                questionCallback,
                getString(string.id).replace("{id}", Long.toString(count.getId())),
                getString(string.name_count).replace("{name}", count.getName()),
                getString(string.lot_count).replace("{lot}", Integer.toString(count.getLot())),
                getString(string.tCountUpdatePositive),
                getString(string.tCountUpdateNegative));
    } */

}

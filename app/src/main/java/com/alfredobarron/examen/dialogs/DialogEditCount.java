package com.alfredobarron.examen.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.alfredobarron.examen.R;
import com.alfredobarron.examen.model.Counts;


public class DialogEditCount extends DialogFragment{

    public interface Callback {

        void onPositiveButtonClicked();

        void onCancel();

    }

    public EditText editTextName;
    public EditText editTextLot;

    private static final String TITLE = "Editar";
    private Callback callback;
    private static final String ID = "argument.id";
    private static final String NAME = "argument.name";
    private static final String LOT = "argument.lot";
    private static final String POSITIVE_TEXT = "argument.positive.text";
    private static final String NEGATIVE_TEXT = "argument.negative.text";
    String name = null;
    String lot = null;
    String positiveText = null;
    String negativeText = null;
    long id = 0;

    public static DialogEditCount newInstance(
            final String id,
            final String name,
            final String lot,
            final String positiveText,
            final String negativeText) {

        final Bundle args = new Bundle();
        args.putString(ID, id);
        args.putString(NAME, name);
        args.putString(LOT, lot);
        args.putString(POSITIVE_TEXT, positiveText);
        args.putString(NEGATIVE_TEXT, negativeText);

        final DialogEditCount dialogEditCount = new DialogEditCount();
        dialogEditCount.setArguments(args);

        return dialogEditCount;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        if (getArguments() != null) {
            if (getArguments().containsKey(ID)) {
                id = Long.parseLong(getArguments().getString(ID));
            }
            if (getArguments().containsKey(NAME)) {
                name = getArguments().getString(NAME);
            }
            if (getArguments().containsKey(LOT)) {
                lot = getArguments().getString(LOT);
            }
            if (getArguments().containsKey(POSITIVE_TEXT)) {
                positiveText = getArguments().getString(POSITIVE_TEXT);
            }
            if (getArguments().containsKey(NEGATIVE_TEXT)) {
                negativeText = getArguments().getString(NEGATIVE_TEXT);
            }
        }

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_edit_count, null);

        editTextName = (EditText) view.findViewById(R.id.name_edit);
        editTextLot = (EditText) view.findViewById(R.id.lot_edit);

        editTextName.setText(name);
        editTextLot.setText(lot);

        final DialogInterface.OnClickListener positiveButtonListener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    Counts count = Counts.findById(Counts.class, id);
                    count.setName(editTextName.getText().toString());
                    count.setLot(Integer.parseInt(editTextLot.getText().toString()));
                    count.save();
                    callback.onPositiveButtonClicked();
                }
            }
        };

        final DialogInterface.OnClickListener negativeButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    callback.onCancel();
                }
            }
        };

        return new AlertDialog.Builder(getActivity())
            .setView(view)
            .setTitle(TITLE)
            .setIcon(R.drawable.ic_action_edit_1)
            .setPositiveButton(positiveText, positiveButtonListener)
            .setNegativeButton(negativeText, negativeButtonListener)
            .create();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}

package com.alfredobarron.examen.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.alfredobarron.examen.R;

public class AlertInformation extends DialogFragment {

    public interface Callback {

        void onPositiveButtonClicked();

    }

    private static final String TITLE = "argument.title";

    private static final String MESSAGE = "argument.message";

    private static final String POSITIVE_TEXT = "argument.positive.text";

    private Callback callback;

    public static AlertInformation newInstance(
            final String title,
            final String message,
            final String positiveText) {

        final Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(MESSAGE, message);
        args.putString(POSITIVE_TEXT, positiveText);

        final AlertInformation alertInformation = new AlertInformation();
        alertInformation.setArguments(args);

        return alertInformation;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        String title = null;
        String message = null;
        String positiveText = null;

        if (getArguments() != null) {
            if (getArguments().containsKey(TITLE)) {
                title = getArguments().getString(TITLE);
            }

            if (getArguments().containsKey(MESSAGE)) {
                message = getArguments().getString(MESSAGE);
            }

            if (getArguments().containsKey(POSITIVE_TEXT)) {
                positiveText = getArguments().getString(POSITIVE_TEXT);
            }

        }

        final DialogInterface.OnClickListener positiveButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    callback.onPositiveButtonClicked();
                }
            }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setIcon(R.drawable.ic_action_person)
                .setMessage(message)
                .setPositiveButton(positiveText, positiveButtonListener)
                .create();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}

package com.alfredobarron.examen.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.alfredobarron.examen.dialogs.AlertInformation;

import static com.alfredobarron.examen.dialogs.AlertInformation.newInstance;

public class AlertInformationUtil {

    public static void showDialog(
            final FragmentManager fragmentManager,
            final String tag,
            final AlertInformation.Callback callback,
            final String title,
            final String message,
            final String positiveButtonText) {

        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final Fragment prev = fragmentManager.findFragmentByTag(tag);

        if (prev != null) {
            fragmentTransaction.remove(prev);
        }

        fragmentTransaction.commit();

        final AlertInformation alertInformation = newInstance(title, message, positiveButtonText);
        alertInformation.setCallback(callback);
        alertInformation.show(fragmentManager, tag);
    }

}

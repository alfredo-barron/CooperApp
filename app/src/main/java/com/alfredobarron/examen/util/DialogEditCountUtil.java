package com.alfredobarron.examen.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.alfredobarron.examen.dialogs.DialogEditCount;

import static com.alfredobarron.examen.dialogs.DialogEditCount.newInstance;

public class DialogEditCountUtil {

    public static void showDialog(
          final FragmentManager fragmentManager,
          final String tag,
          final DialogEditCount.Callback callback,
          final String id,
          final String name,
          final String lot,
          final String positiveButtonText,
          final String negativeButtonText) {

        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final Fragment prev = fragmentManager.findFragmentByTag(tag);

        if (prev != null) {
            fragmentTransaction.remove(prev);
        }

        fragmentTransaction.commit();

        final DialogEditCount dialogEditCount = newInstance(id, name, lot, positiveButtonText, negativeButtonText);
        dialogEditCount.setCallback(callback);
        dialogEditCount.show(fragmentManager, tag);
    }
}

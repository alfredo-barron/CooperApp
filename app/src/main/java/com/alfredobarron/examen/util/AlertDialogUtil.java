package com.alfredobarron.examen.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.alfredobarron.examen.dialogs.AlertDialogFragment;

import static com.alfredobarron.examen.dialogs.AlertDialogFragment.Callback;
import static com.alfredobarron.examen.dialogs.AlertDialogFragment.newInstance;


public class AlertDialogUtil {
  /**
   * Create {@link com.alfredobarron.examen.dialogs.AlertDialogFragment} with a given #title, #message,
   * #positiveButtonText, #negativeButtonText and #tag
   *
   * @param fragmentManager    {@link android.support.v4.app.FragmentManager}
   * @param tag                {@link String} to tag the {@link android.support.v4.app.FragmentManager} with
   * @param callback           {@link com.alfredobarron.examen.dialogs.AlertDialogFragment.Callback} for
   *                                                                                     buttons
   * @param title              Title of the dialog as {@link String}
   * @param message            Message of the dialog as {@link String}
   * @param positiveButtonText Message to show on the positive button as {@link String}
   * @param negativeButtonText Message to show on the negative button as {@link String}
   */
  public static void showDialog(
      final FragmentManager fragmentManager,
      final String tag,
      final Callback callback,
      final String title,
      final String message,
      final String positiveButtonText,
      final String negativeButtonText) {

    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    final Fragment prev = fragmentManager.findFragmentByTag(tag);

    if (prev != null) {
      fragmentTransaction.remove(prev);
    }

    fragmentTransaction.commit();

    final AlertDialogFragment alertDialogFragment = newInstance(title, message, positiveButtonText, negativeButtonText);
    alertDialogFragment.setCallback(callback);
    alertDialogFragment.show(fragmentManager, tag);
  }
}

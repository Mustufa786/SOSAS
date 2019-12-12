package edu.aku.hassannaqvi.uen_sosas.otherClasses;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.ViewGroup;

import edu.aku.hassannaqvi.uen_sosas.R;

/**
 * Created by ali.azaz on 22/04/2019.
 */

public class SnackUtils {

    public static Snackbar showLoadingSnackbar(Activity ctx, String message) {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) ctx.findViewById(android.R.id.content)).getChildAt(0);
        Snackbar bar = Snackbar.make(viewGroup, message, Snackbar.LENGTH_INDEFINITE);
        bar.getView().setBackgroundColor(ctx.getResources().getColor(R.color.colorPrimaryDark));
        bar.show();
        return bar;
    }
}
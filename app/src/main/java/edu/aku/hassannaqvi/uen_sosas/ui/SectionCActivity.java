package edu.aku.hassannaqvi.uen_sosas.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivitySectionCBinding;
import edu.aku.hassannaqvi.uen_sosas.ui.other.EndingActivity;
import edu.aku.hassannaqvi.uen_sosas.validator.ValidatorClass;

public class SectionCActivity extends AppCompatActivity {


    ActivitySectionCBinding bi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c);
        bi.setCallback(this);

    }

    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                finish();
                startActivity(new Intent(this, SectionEActivity.class));
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                finish();
                startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private boolean UpdateDB() {

//        DatabaseHelper db = new DatabaseHelper(this);
//        long updcount = db.addForm(MainApp.fc);
//
//        MainApp.fc.set_ID(String.valueOf(updcount));
//        if (updcount != 0) {
//            MainApp.fc.set_UID(
//                    (MainApp.fc.getDeviceID() + MainApp.fc.get_ID()));
//            db.updateFormID();
//            return true;
//        } else {
//            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
//
//        }

        return false;

    }


    private void SaveDraft() throws JSONException {


    }


    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.ll03);
    }

}

package edu.aku.hassannaqvi.uen_sosas.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivitySectionDaBinding;
import edu.aku.hassannaqvi.uen_sosas.ui.other.EndingActivity;
import edu.aku.hassannaqvi.uen_sosas.validator.ClearClass;
import edu.aku.hassannaqvi.uen_sosas.validator.ValidatorClass;

public class SectionDAActivity extends AppCompatActivity {

    ActivitySectionDaBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_da);
        bi.setCallback(this);

        setListeners();
    }

    private void setListeners() {

        bi.td07.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.td07h.isChecked()) {
                    ClearClass.ClearAllFields(bi.llsos02, null);
                }
            }
        });

        bi.td08.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.td08b.isChecked()) {
                    ClearClass.ClearAllFields(bi.td09, null);
                }
            }
        });

        bi.td10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.td10b.isChecked()) {
                    ClearClass.ClearAllFields(bi.llsos03, null);
                }
            }
        });

        bi.td12.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.td12a.isChecked()) {
                    ClearClass.ClearAllFields(bi.td13, null);
                }
            }
        });
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
                startActivity(new Intent(this, SectionCActivity.class));
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
//            return false;
//        }

        return true;

    }


    private void SaveDraft() throws JSONException {


    }


    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.ll02);
    }
}

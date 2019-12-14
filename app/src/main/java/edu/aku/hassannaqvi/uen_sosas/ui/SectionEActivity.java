package edu.aku.hassannaqvi.uen_sosas.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_sosas.core.MainApp;
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivitySectionEBinding;
import edu.aku.hassannaqvi.uen_sosas.ui.other.EndingActivity;
import edu.aku.hassannaqvi.uen_sosas.validator.ClearClass;
import edu.aku.hassannaqvi.uen_sosas.validator.ValidatorClass;

public class SectionEActivity extends AppCompatActivity {

    ActivitySectionEBinding bi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_e);
        bi.setCallback(this);

        setListeners();
    }

    private void setListeners() {

        bi.te06.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.te06b.isChecked()) {
                    ClearClass.ClearAllFields(bi.llsos04, null);
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

        DatabaseHelper db = new DatabaseHelper(this);
        long updcount = db.addForm(MainApp.fc);

        MainApp.fc.set_ID(String.valueOf(updcount));
        if (updcount != 0) {
            MainApp.fc.set_UID(
                    (MainApp.fc.getDeviceID() + MainApp.fc.get_ID()));
            db.updateFormID();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void SaveDraft() throws JSONException {

        JSONObject SE = new JSONObject();

        //checkbox Te05
        SE.put("Te05a", bi.te05a.isChecked() ? "1" : "0");
        SE.put("Te05b", bi.te05b.isChecked() ? "2" : "0");
        SE.put("Te05c", bi.te05c.isChecked() ? "3" : "0");
        SE.put("Te05d", bi.te05d.isChecked() ? "4" : "0");
        SE.put("Te05e", bi.te05e.isChecked() ? "5" : "0");
        SE.put("Te05f", bi.te05f.isChecked() ? "6" : "0");
        SE.put("Te05g", bi.te05g.isChecked() ? "7" : "0");
        SE.put("Te05h", bi.te05h.isChecked() ? "8" : "0");

        //te06
        SE.put("te06", bi.te06a.isChecked() ? "1"
                : bi.te06b.isChecked() ? "2"
                : "0");

        //te07
        SE.put("te07", bi.te07a.isChecked() ? "1"
                : bi.te07b.isChecked() ? "2"
                : bi.te07c.isChecked() ? "3"
                : bi.te07d.isChecked() ? "4"
                : bi.te07e.isChecked() ? "5"
                : bi.te07f.isChecked() ? "6"
                : bi.te07g.isChecked() ? "7"
                : bi.te07h.isChecked() ? "8"
                : bi.te07i.isChecked() ? "9"
                : "0");

        //te09
        SE.put("te09", bi.te09a.isChecked() ? "1"
                : bi.te09b.isChecked() ? "2"
                : "0");

        //te10
        SE.put("te10", bi.te10a.isChecked() ? "1"
                : bi.te10b.isChecked() ? "2"
                : bi.te10c.isChecked() ? "3"
                : bi.te10d.isChecked() ? "4"
                : bi.te10e.isChecked() ? "5"
                : "0");

        //te08
        SE.put("te08", bi.te08a.isChecked() ? "1"
                : bi.te08b.isChecked() ? "2"
                : bi.te08c.isChecked() ? "3"
                : bi.te08d.isChecked() ? "4"
                : "0");

        //te12
        SE.put("te12", bi.te12a.isChecked() ? "1"
                : bi.te12b.isChecked() ? "2"
                : bi.te12c.isChecked() ? "3"
                : bi.te12d.isChecked() ? "4"
                : bi.te12e.isChecked() ? "5"
                : bi.te1296.isChecked() ? "96"
                : "0");
        SE.put("te1296x", bi.te1296x.getText().toString());

        //te11
        SE.put("te11", bi.te11a.isChecked() ? "1"
                : bi.te11b.isChecked() ? "2"
                : bi.te11c.isChecked() ? "3"
                : bi.te11d.isChecked() ? "4"
                : "0");

        //checkbox Te13
        SE.put("te13a", bi.te13a.isChecked() ? "1" : "0");
        SE.put("te13b", bi.te13b.isChecked() ? "2" : "0");
        SE.put("te13c", bi.te13c.isChecked() ? "3" : "0");
        SE.put("te13d", bi.te13d.isChecked() ? "4" : "0");
        SE.put("te13e", bi.te13e.isChecked() ? "5" : "0");


        MainApp.fc.setsE(String.valueOf(SE));

    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.ll04);
    }

}

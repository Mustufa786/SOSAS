package edu.aku.hassannaqvi.uen_sosas.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.contracts.ChildContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.ProblemContract;
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_sosas.core.MainApp;
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivitySectionEBinding;
import edu.aku.hassannaqvi.uen_sosas.ui.other.EndingActivity;
import edu.aku.hassannaqvi.uen_sosas.validator.ClearClass;
import edu.aku.hassannaqvi.uen_sosas.validator.ValidatorClass;

import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.cc;
import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.pc;

public class SectionEActivity extends AppCompatActivity {

    ActivitySectionEBinding bi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_e);
        bi.setCallback(this);

        setListeners();

        bi.heading.setText(MainApp.problemType == 1 ? "EYES PROBLEM"
                : MainApp.problemType == 2 ? "EARS PROBLEM"
                : MainApp.problemType == 3 ? "FACE PROBLEM"
                : MainApp.problemType == 4 ? "NECK PROBLEM"
                : MainApp.problemType == 5 ? "HEAD PROBLEM"
                : MainApp.problemType == 6 ? "CHEST PROBLEM"
                : MainApp.problemType == 7 ? "BACK PROBLEM"
                : MainApp.problemType == 8 ? "ABDOMEN PROBLEM"
                : MainApp.problemType == 9 ? "BUTTOCKS/GROIN/GENETALIA PROBLEM"
                : "EXTREMITIES PROBLEM");

        if (MainApp.problemType == 1) {
            bi.te05i.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
        }

        if (MainApp.problemType == 2) {
            bi.te05g.setText(getString(R.string.teo5g1));
            bi.te05h.setVisibility(View.GONE);
            bi.te05i.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
        }

        if (MainApp.problemType == 3) {
            bi.te05g.setVisibility(View.GONE);
            bi.te05h.setVisibility(View.GONE);
            bi.te05i.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
        }

        if (MainApp.problemType == 4) {
            bi.te05g.setVisibility(View.GONE);
            bi.te05h.setVisibility(View.GONE);
            bi.te05i.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
        }

        if (MainApp.problemType == 5) {
            bi.te05g.setVisibility(View.GONE);
            bi.te05h.setVisibility(View.GONE);
            bi.te05i.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
        }

        if (MainApp.problemType == 6) {
            bi.te05e.setText(getString(R.string.te05e1));
            bi.te05f.setText(getString(R.string.te05f1));
            bi.te05g.setText(getString(R.string.te05g2));
            bi.te05h.setText(getString(R.string.te05h1));
            bi.te05i.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
        }

        if (MainApp.problemType == 7) {
            bi.te05g.setVisibility(View.GONE);
            bi.te05h.setVisibility(View.GONE);
            bi.te05i.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
        }

        if (MainApp.problemType == 8) {
            bi.te05d.setText(getString(R.string.te05d1));
            bi.te05e.setText(getString(R.string.te05e2));
            bi.te05f.setText(getString(R.string.te05f2));
            bi.te05g.setText(getString(R.string.te05g3));
            bi.te05h.setText(getString(R.string.te05h2));
        }

        if (MainApp.problemType == 9) {
            bi.te05e.setText(getString(R.string.te05e3));
            bi.te05f.setText(getString(R.string.te05f3));
            bi.te05g.setText(getString(R.string.te05g4));
            bi.te05h.setText(getString(R.string.te05h3));
            bi.te05i.setText(getString(R.string.te05i1));
            bi.te05j.setText(getString(R.string.te05j1));
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
        }


    }

    private void setListeners() {


        bi.te06.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.te06b.isChecked()) {
                    ClearClass.ClearAllFields(bi.te07cv, null);
                }
            }
        });

        bi.te09.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.te09b.isChecked()) {
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
                if (MainApp.problemCount > 0) {
                    finish();
                    startActivity(new Intent(this, SectionEActivity.class));
                } else {
                    finish();
                    startActivity(new Intent(this, MainApp.problemType != 10 ? SectionCActivity.class : EndingActivity.class).putExtra("complete", true));
                }
            } else {
                Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        finish();
        startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false));

    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);
        long updcount = db.addProblems(pc);

        pc.set_id(String.valueOf(updcount));
        if (updcount != 0) {
            pc.setUid(
                    (MainApp.pc.getDeviceID() + MainApp.pc.get_id()));
            db.updateProblemFormID();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void SaveDraft() throws JSONException {

        JSONObject SE = new JSONObject();
        SharedPreferences preferences = getSharedPreferences("tagName", MODE_PRIVATE);
        pc = new ProblemContract();
        pc.setFormdate(DateFormat.format("dd-MM-yyyy HH:mm", new Date()).toString());
        pc.setProblemType(String.valueOf(MainApp.problemType));
        pc.setDeviceID(MainApp.deviceId);
        pc.setUser(MainApp.userName);
        pc.setUuid(MainApp.fc.get_UID());
        pc.setCuid(MainApp.cc.getUid());
        pc.setDevicetagID(preferences.getString("tagName", null));
        SE.put("te05", bi.te05a.isChecked() ? "1"
                : bi.te05b.isChecked() ? "2"
                : bi.te05c.isChecked() ? "3"
                : bi.te05d.isChecked() ? "4"
                : bi.te05e.isChecked() ? "5"
                : bi.te05f.isChecked() ? "6"
                : bi.te05g.isChecked() ? "7"
                : bi.te05h.isChecked() ? "8"
                : bi.te05i.isChecked() ? "9"
                : bi.te05j.isChecked() ? "10"
                : bi.te05k.isChecked() ? "11"
                : bi.te05l.isChecked() ? "12"
                : "0");
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

        //te08
        SE.put("te08", bi.te08a.isChecked() ? "1"
                : bi.te08b.isChecked() ? "2"
                : bi.te08c.isChecked() ? "3"
                : bi.te08d.isChecked() ? "4"
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

        //te11
        SE.put("te11", bi.te11a.isChecked() ? "1"
                : bi.te11b.isChecked() ? "2"
                : bi.te11c.isChecked() ? "3"
                : bi.te11d.isChecked() ? "4"
                : "0");

        //te12
        SE.put("te12", bi.te12a.isChecked() ? "1"
                : bi.te12b.isChecked() ? "2"
                : bi.te12c.isChecked() ? "3"
                : bi.te12d.isChecked() ? "4"
                : bi.te12e.isChecked() ? "5"
                : bi.te12f.isChecked() ? "6"
                : bi.te1296.isChecked() ? "96"
                : "0");
        SE.put("te1296x", bi.te1296x.getText().toString());

        //checkbox Te13
        SE.put("te13a", bi.te13a.isChecked() ? "1" : "0");
        SE.put("te13b", bi.te13b.isChecked() ? "2" : "0");
        SE.put("te13c", bi.te13c.isChecked() ? "3" : "0");
        SE.put("te13d", bi.te13d.isChecked() ? "4" : "0");
        SE.put("te13e", bi.te13e.isChecked() ? "5" : "0");

        pc.setdA(String.valueOf(SE));
        --MainApp.problemCount;

        MainApp.fc.setsE(String.valueOf(SE));

    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.ll04);
    }

}

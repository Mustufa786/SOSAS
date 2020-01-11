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
import edu.aku.hassannaqvi.uen_sosas.contracts.DeceasedChildContract;
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_sosas.core.MainApp;
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivitySectionDaBinding;
import edu.aku.hassannaqvi.uen_sosas.ui.other.EndingActivity;
import edu.aku.hassannaqvi.uen_sosas.validator.ClearClass;
import edu.aku.hassannaqvi.uen_sosas.validator.ValidatorClass;

import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.dcc;

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

        /*bi.td10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == bi.td10b.getId()) {
                    bi.td11cv.setVisibility(View.GONE);
                    bi.td12cv.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(bi.td11cv, null);
                    ClearClass.ClearAllFields(bi.td12cv, null);
                } else {
                    bi.td11cv.setVisibility(View.VISIBLE);
                    bi.td12cv.setVisibility(View.VISIBLE);
                }
            }
        });*/

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

                if (bi.td12b.isChecked () || bi.td12c.isChecked () || bi.td12d.isChecked ()) {
                    ClearClass.ClearAllFields(bi.td13cv, null);
                }
            }
        });

        /*bi.td12.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != bi.td12a.getId()) {
                    bi.td13cv.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(bi.td13cv, null);
                } else {
                    bi.td13cv.setVisibility(View.VISIBLE);
                }
            }
        });*/
    }

    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                if (MainApp.childCount > 0) {
                    finish();
                    startActivity(new Intent(this, SectionDAActivity.class));
                } else {
                    finish();
                    startActivity(new Intent(this, ChildListActivity.class));
                }
            }

        }
    }

    public void BtnEnd() {
        finish();
        startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false));
    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);
        long updcount = db.addChildDeceaseForm(dcc);

        MainApp.dcc.set_id(String.valueOf(updcount));
        if (updcount != 0) {
            MainApp.dcc.setUid(
                    (MainApp.dcc.getDeviceID() + MainApp.dcc.get_id()));
            db.updateChildDeceaseFormID();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void SaveDraft() throws JSONException {

        JSONObject SD = new JSONObject();
        SharedPreferences preferences = getSharedPreferences("tagName", MODE_PRIVATE);

        dcc = new DeceasedChildContract();
        dcc.setLuid(MainApp.motherData.getUid());
        dcc.setFormdate(DateFormat.format("dd-MM-yyyy HH:mm", new Date()).toString());
        dcc.setMotherId(MainApp.motherData.getSerialno());
        dcc.setSerialNo(String.valueOf(MainApp.childCount));
        dcc.setDeviceID(MainApp.deviceId);
        dcc.setUser(MainApp.userName);
        dcc.setUuid(MainApp.fc.get_UID());
        dcc.setDevicetagID(preferences.getString("tagName", null));

        //td03
        SD.put("td02b", bi.td02b.getText().toString());

        //td04
        SD.put("td04aodc", bi.td04aodc.getText().toString());

        //td05
        SD.put("td05", bi.td05a.isChecked() ? "1"
                : bi.td05b.isChecked() ? "2"
                : "0");

        //td06
        SD.put("td06dd", bi.td06dd.getText().toString());
        SD.put("td06mm", bi.td06mm.getText().toString());
        SD.put("td06dyy", bi.td06yy.getText().toString());

        //td07
        SD.put("td07", bi.td07a.isChecked() ? "1"
                : bi.td07b.isChecked() ? "2"
                : bi.td07c.isChecked() ? "3"
                : bi.td07d.isChecked() ? "4"
                : bi.td07e.isChecked() ? "5"
                : bi.td07f.isChecked() ? "6"
                : bi.td07g.isChecked() ? "7"
                : bi.td07h.isChecked() ? "8"
                : "0");

        //td08
        SD.put("td08", bi.td08a.isChecked() ? "1"
                : bi.td08b.isChecked() ? "2"
                : "0");

        //td09
        SD.put("td09", bi.td09a.isChecked() ? "1"
                : bi.td09b.isChecked() ? "2"
                : bi.td09c.isChecked() ? "3"
                : bi.td09d.isChecked() ? "4"
                : bi.td09e.isChecked() ? "5"
                : bi.td09f.isChecked() ? "6"
                : bi.td09g.isChecked() ? "7"
                : bi.td09h.isChecked() ? "8"
                : bi.td09i.isChecked() ? "9"
                : "0");

        //td10
        SD.put("td10", bi.td10a.isChecked() ? "1"
                : bi.td10b.isChecked() ? "2"
                : "0");

        //td11
        SD.put("td11", bi.td11a.isChecked() ? "1"
                : bi.td11b.isChecked() ? "2"
                : bi.td11c.isChecked() ? "3"
                : bi.td11d.isChecked() ? "4"
                : bi.td11e.isChecked() ? "5"
                : "0");

        //td12
        SD.put("td12", bi.td12a.isChecked() ? "1"
                : bi.td12b.isChecked() ? "2"
                : bi.td12c.isChecked() ? "3"
                : bi.td12d.isChecked() ? "4"
                : "0");

        //td13
        SD.put("td13", bi.td13a.isChecked() ? "1"
                : bi.td13b.isChecked() ? "2"
                : bi.td13c.isChecked() ? "3"
                : bi.td13d.isChecked() ? "4"
                : bi.td13e.isChecked() ? "5"
                : bi.td13f.isChecked() ? "6"
                : bi.td1396.isChecked() ? "96"
                : "0");
        SD.put("td1396x", bi.td1396x.getText().toString());

        //td14
        SD.put("td14", bi.td14a.isChecked() ? "1"
                : bi.td14b.isChecked() ? "2"
                : bi.td14c.isChecked() ? "3"
                : bi.td14d.isChecked() ? "4"
                : "0");

        dcc.setdA(String.valueOf(SD));

        --MainApp.childCount;


    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.ll02);
    }
}

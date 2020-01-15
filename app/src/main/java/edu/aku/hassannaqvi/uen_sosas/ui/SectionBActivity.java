package edu.aku.hassannaqvi.uen_sosas.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.contracts.MotherContract;
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_sosas.core.MainApp;
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivitySectionBBinding;
import edu.aku.hassannaqvi.uen_sosas.ui.other.EndingActivity;
import edu.aku.hassannaqvi.uen_sosas.validator.ClearClass;
import edu.aku.hassannaqvi.uen_sosas.validator.ValidatorClass;

import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.mc;

public class SectionBActivity extends AppCompatActivity {

    ActivitySectionBBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_b);
        bi.setCallback(this);


        MainApp.motherData = getIntent().getParcelableExtra(MainApp.motherInfo);
        setListeners();
    }

    private void setListeners() {

        bi.td01.setOnCheckedChangeListener((group, checkedId) -> {

            if (bi.td01b.isChecked()) {
                ClearClass.ClearAllFields(bi.td02, null);
            }
        });

        bi.td02a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty()) {
                    MainApp.childCount = Integer.parseInt(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void BtnContinue() {
        if (formValidation()) {
            if (bi.td01b.isChecked()) {
                MainApp.openCountDialog(this);
                MainApp.setCountItemClick(() -> savingContinue());
            } else savingContinue();
        }
    }

    private void savingContinue() {
        try {
            SaveDraft();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            if (bi.td01b.isChecked()) {
                finish();
                startActivity(new Intent(this, ChildListActivity.class));
            } else {
                finish();
                startActivity(new Intent(this, SectionDAActivity.class));
            }

        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }

    public void BtnEnd() {
        finish();
        startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false));
    }

    private boolean UpdateDB() {
        DatabaseHelper db = new DatabaseHelper(this);
        long updcount = db.addMotherForm(mc);

        mc.set_id(String.valueOf(updcount));
        if (updcount != 0) {
            mc.setUid(
                    (mc.getDeviceID() + mc.get_id()));
            db.updateMotherFormID();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();

        }

        return false;
    }

    private void SaveDraft() throws JSONException {

        mc = new MotherContract();
        JSONObject SA = new JSONObject();

        SharedPreferences preferences = getSharedPreferences("tagName", MODE_PRIVATE);
        mc.setLuid(MainApp.motherData.getUid());
        mc.setFormdate(DateFormat.format("dd-MM-yyyy HH:mm", new Date()).toString());
//        mc.setMotherId(MainApp.motherData.getSerialno());
        mc.setSerialNo(MainApp.motherData.getSerialno());
        mc.setDeviceID(MainApp.deviceId);
        mc.setUser(MainApp.userName);
        mc.setUuid(MainApp.fc.get_UID());
        mc.setDevicetagID(preferences.getString("tagName", null));

        //td01
        SA.put("hhno", MainApp.fc.getHhno());
        SA.put("cluster_code", MainApp.fc.getClusterCode());
        SA.put("td01", bi.td01a.isChecked() ? "1"
                : bi.td01b.isChecked() ? "2"
                : "0");
        //td02a
        SA.put("td02a", bi.td02a.getText().toString());
        mc.setdA(String.valueOf(SA));


    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.ll01);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "can not go back", Toast.LENGTH_SHORT).show();
    }
}

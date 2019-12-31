package edu.aku.hassannaqvi.uen_sosas.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.contracts.ChildContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_sosas.core.MainApp;
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivitySectionCBinding;
import edu.aku.hassannaqvi.uen_sosas.ui.other.EndingActivity;
import edu.aku.hassannaqvi.uen_sosas.validator.ClearClass;
import edu.aku.hassannaqvi.uen_sosas.validator.ValidatorClass;

import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.cc;
import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.dcc;

public class SectionCActivity extends AppCompatActivity {


    ActivitySectionCBinding bi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c);
        bi.setCallback(this);

        MainApp.problemType++;
        bi.headingTitle.setText(MainApp.problemType == 1 ? "EYES"
                : MainApp.problemType == 2 ? "EARS"
                : MainApp.problemType == 3 ? "FACE"
                : MainApp.problemType == 4 ? "NECK"
                : MainApp.problemType == 5 ? "HEAD"
                : MainApp.problemType == 6 ? "CHEST"
                : MainApp.problemType == 7 ? "BACK"
                : MainApp.problemType == 8 ? "ABDOMEN"
                : MainApp.problemType == 9 ? "BUTTOCKS/GROIN/GENETALIA"
                : "EXTREMITIES");
        setListeners();


    }

    private void setListeners() {

        bi.te03.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (bi.te03b.isChecked()) {
                    ClearClass.ClearAllFields(bi.te04sos, null);
                }
            }
        });

        bi.te04.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty())
                    MainApp.problemCount = Integer.parseInt(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                if (bi.te03b.isChecked()) {
                    finish();
                    startActivity(new Intent(this, MainApp.problemType != 10 ? SectionCActivity.class
                            : EndingActivity.class).putExtra("complete", true).putExtra(MainApp.motherInfo, MainApp.childData));
                } else {
                    finish();
                    startActivity(new Intent(this, SectionEActivity.class));
                }
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void BtnEnd() {
        finish();
        startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false));

    }

    private boolean UpdateDB() {
        DatabaseHelper db = new DatabaseHelper(this);
        long updcount = db.addChildForm(cc);

        cc.set_id(String.valueOf(updcount));
        if (updcount != 0) {
            cc.setUid(
                    (cc.getDeviceID() + cc.get_id()));
            db.updateChildFormID();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();

        }

        return false;

    }

    private void SaveDraft() throws JSONException {
        JSONObject SC = new JSONObject();
        SharedPreferences preferences = getSharedPreferences("tagName", MODE_PRIVATE);
        cc = new ChildContract();
        cc.setLuid(MainApp.childData.getUid());
        cc.setFormdate(DateFormat.format("dd-MM-yyyy HH:mm", new Date()).toString());
        cc.setMotherId(MainApp.motherData.getSerialno());
        cc.setSerialNo(MainApp.childData.getSerialno());
        cc.setDeviceID(MainApp.deviceId);
        cc.setUser(MainApp.userName);
        cc.setUuid(MainApp.fc.get_UID());
        cc.setDevicetagID(preferences.getString("tagName", null));
        //te03
        SC.put("te03", bi.te03a.isChecked() ? "1"
                : bi.te03b.isChecked() ? "2"
                : "0");

        //te04
        SC.put("te04", bi.te04.getText().toString());

        cc.setdA(String.valueOf(SC));


    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.ll03);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "can not go back", Toast.LENGTH_SHORT).show();
    }

}

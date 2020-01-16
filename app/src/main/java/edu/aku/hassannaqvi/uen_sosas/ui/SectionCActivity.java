package edu.aku.hassannaqvi.uen_sosas.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.contracts.ChildContract;
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_sosas.core.MainApp;
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivitySectionCBinding;
import edu.aku.hassannaqvi.uen_sosas.ui.other.EndingActivity;
import edu.aku.hassannaqvi.uen_sosas.validator.ClearClass;
import edu.aku.hassannaqvi.uen_sosas.validator.ValidatorClass;

import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.cc;

public class SectionCActivity extends AppCompatActivity {


    ActivitySectionCBinding bi;
    public static final int CHILD_DIS = 2;
    public static final int CHILD_MAIN_C = 201;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c);
        bi.setCallback(this);


        MainApp.problemType++;
        bi.headingTitle.setText(getProblemName(MainApp.problemType));

        bi.te03a1.setText(MainApp.problemType == 1 ? getString(R.string.te03a1)
                : MainApp.problemType == 2 ? getString(R.string.te03a2)
                : MainApp.problemType == 3 ? getString(R.string.te03a3)
                : MainApp.problemType == 4 ? getString(R.string.te03a4)
                : MainApp.problemType == 5 ? getString(R.string.te03a5)
                : MainApp.problemType == 6 ? getString(R.string.te03a6)
                : MainApp.problemType == 7 ? getString(R.string.te03a7)
                : MainApp.problemType == 8 ? getString(R.string.te03a8)
                : MainApp.problemType == 9 ? getString(R.string.te03a9)
                : MainApp.problemType == 10 ? getString(R.string.te03a10)
                : MainApp.problemType == 11 ? getString(R.string.te03a11)
                : MainApp.problemType == 12 ? getString(R.string.te03a12)
                : MainApp.problemType == 13 ? getString(R.string.te03a13)
                : MainApp.problemType == 14 ? getString(R.string.te03a14)
                : MainApp.problemType == 15 ? getString(R.string.te03a15)
                : getString(R.string.te03a16));

        setListeners();

    }

    private String getProblemName(int probe) {
        return probe == 1 ? "EYES"
                : probe == 2 ? "EARS"
                : probe == 3 ? "FACE"
                : probe == 4 ? "NECK"
                : probe == 5 ? "HEAD"
                : probe == 6 ? "CHEST"
                : probe == 7 ? "BACK"
                : probe == 8 ? "ABDOMEN"
                : probe == 9 ? "BUTTOCKS/GROIN/GENETALIA"
                : probe == 10 ? "FINGERS EXTREMITIES"
                : probe == 11 ? "THUMB/HAND EXTREMITIES"
                : probe == 12 ? "LOWER ARM EXTREMITIES"
                : probe == 13 ? "UPPER ARM EXTREMITIES"
                : probe == 14 ? "FOOT EXTREMITIES"
                : probe == 15 ? "LOWER LEG EXTREMITIES"
                : probe == 16 ? "UPPER LEG EXTREMITIES"
                : "";
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
            if (bi.te03a.isChecked()) {
                MainApp.openCountDialog(this, Integer.valueOf(bi.te04.getText().toString()));
                MainApp.setCountItemClick(this::savingContinue);
            } else savingContinue();
        }
    }

    private void savingContinue() {
        try {
            SaveDraft();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            if (bi.te03b.isChecked()) {
                if (MainApp.problemType != 16) {
                    startActivityForResult(new Intent(this, SectionCActivity.class).putExtra(MainApp.motherInfo, MainApp.childData), CHILD_MAIN_C);
                } else {
                    setResult(RESULT_OK);
                    finish();
                }
            } else {
                startActivityForResult(new Intent(this, SectionEActivity.class), CHILD_DIS);
            }
        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHILD_DIS) {
            if (resultCode == RESULT_OK) {
                if (MainApp.problemCount == SectionEActivity.problem_counter) {
                    finishActivity(CHILD_DIS);
                    startActivityForResult(new Intent(this, SectionEActivity.class), CHILD_DIS);
                } else if (MainApp.problemType != 16) {
                    finishActivity(CHILD_MAIN_C);
                    startActivityForResult(new Intent(this, SectionCActivity.class).putExtra(MainApp.motherInfo, MainApp.childData), CHILD_MAIN_C);
                } else {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        } else if (requestCode == CHILD_MAIN_C) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    public void BtnEnd() {
//        finish();
        startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
        SC.put("muid", MainApp.mc.getUid());
        SC.put("hhno", MainApp.fc.getHhno());
        SC.put("cluster_code", MainApp.fc.getClusterCode());
        SC.put("p_type", String.valueOf(MainApp.problemType));
        SC.put("p_name", getProblemName(MainApp.problemType));

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

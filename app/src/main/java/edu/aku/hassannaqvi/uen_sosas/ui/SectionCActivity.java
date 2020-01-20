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
import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.extLst;
import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.problemType;

public class SectionCActivity extends AppCompatActivity {

    ActivitySectionCBinding bi;
    public static final int CHILD_DIS = 2;
    public static final int CHILD_MAIN_C = 201;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c);
        bi.setCallback(this);

        bi.headingTitle.setText(getProblemName(MainApp.problemType));

        bi.te03a1.setText(getProblemHead(MainApp.problemType));

        setListeners();

    }

    private String getProblemName(int probe) {
        return probe == 1 ? getString(R.string.eye) + " (EYE)"
                : probe == 2 ? getString(R.string.ear) + " (EAR)"
                : probe == 3 ? getString(R.string.face) + " (FACE)"
                : probe == 4 ? getString(R.string.neck) + " (NECK)"
                : probe == 5 ? getString(R.string.head) + " (HEAD)"
                : probe == 6 ? getString(R.string.chest) + " (CHEST)"
                : probe == 7 ? getString(R.string.back) + " (BACK)"
                : probe == 8 ? getString(R.string.abdomen) + " (ABDOMEN)"
                : probe == 9 ? getString(R.string.groin) + " (Groin/Genitalia/Buttocks)"
                : probe == 10 ? getString(R.string.finger) + " (FINGER)"
                : probe == 11 ? getString(R.string.thumb) + " (THUMB/HAND)"
                : probe == 12 ? getString(R.string.lowerarm) + " (LOWER ARM)"
                : probe == 13 ? getString(R.string.upperarm) + " (UPPER ARM)"
                : probe == 14 ? getString(R.string.foot) + " (FOOT)"
                : probe == 15 ? getString(R.string.lowerleg) + " (LOWER LEG)"
                : probe == 16 ? getString(R.string.upperleg) + " (UPPER LEG)"
                : "";
    }

    private String getProblemHead(int probe) {
        return probe == 1 ? getString(R.string.te03a1)
                : probe == 2 ? getString(R.string.te03a2)
                : probe == 3 ? getString(R.string.te03a3)
                : probe == 4 ? getString(R.string.te03a4)
                : probe == 5 ? getString(R.string.te03a5)
                : probe == 6 ? getString(R.string.te03a6)
                : probe == 7 ? getString(R.string.te03a7)
                : probe == 8 ? getString(R.string.te03a8)
                : probe == 9 ? getString(R.string.te03a9)
                : probe == 10 ? getString(R.string.te03a10)
                : probe == 11 ? getString(R.string.te03a11)
                : probe == 12 ? getString(R.string.te03a12)
                : probe == 13 ? getString(R.string.te03a13)
                : probe == 14 ? getString(R.string.te03a14)
                : probe == 15 ? getString(R.string.te03a15)
                : getString(R.string.te03a16);
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

                if (!s.toString().isEmpty()) {
                    MainApp.problemCount = Integer.parseInt(s.toString());
                    SectionEActivity.problem_counter = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        problemConditions(false);

    }

    private void problemConditions(boolean childDSC) {

        if (childDSC || getIntent().getBooleanExtra("flag", false)) {
            if (extLst != null) {
                boolean flag = false;
                for (int i = problemType; i < 17; i++) {
                    int j = -1;
                    for (Integer item : extLst) {
                        j++;
                        if (problemType == item) {
                            extLst.remove(j);
                            bi.te03.clearCheck();
                            flag = true;
                            break;
                        }
                    }
                    if (flag) break;
                    else {
                        bi.te03b.setChecked(true);
                        try {
                            SaveDraft();
                            UpdateDB();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (flag) {
                    finishActivity(CHILD_MAIN_C);
                    startActivityForResult(new Intent(this, SectionCActivity.class), CHILD_MAIN_C);
                } else {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        }
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
                if (MainApp.problemType <= 9) {
                    finishActivity(CHILD_MAIN_C);
                    startActivityForResult(new Intent(this, SectionCActivity.class), CHILD_MAIN_C);
                } else if (MainApp.problemType == 10) {
                    finishActivity(CHILD_MAIN_C);
                    startActivityForResult(new Intent(this, SectionB02Activity.class), CHILD_MAIN_C);
                } else problemConditions(true);
            } else {
//                finishActivity(CHILD_MAIN_C);
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
                if (MainApp.problemCount != SectionEActivity.problem_counter) {
//                    finishActivity(CHILD_DIS);
                    startActivityForResult(new Intent(this, SectionEActivity.class), CHILD_DIS);
                } else if (MainApp.problemType <= 9) {
                    finishActivity(CHILD_MAIN_C);
                    startActivityForResult(new Intent(this, SectionCActivity.class), CHILD_MAIN_C);
                } else if (MainApp.problemType == 10) {
                    finishActivity(CHILD_MAIN_C);
                    startActivityForResult(new Intent(this, SectionB02Activity.class), CHILD_MAIN_C);
                } else problemConditions(true);
            }
        } else if (requestCode == CHILD_MAIN_C) {
            if (resultCode == RESULT_OK) {
                if (MainApp.problemType <= 9) {
                    finishActivity(CHILD_MAIN_C);
                    startActivityForResult(new Intent(this, SectionCActivity.class), CHILD_MAIN_C);
                } else if (MainApp.problemType == 10) {
                    finishActivity(CHILD_MAIN_C);
                    startActivityForResult(new Intent(this, SectionB02Activity.class), CHILD_MAIN_C);
                } else problemConditions(true);
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


        MainApp.problemType++;

    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.ll03);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "can not go back", Toast.LENGTH_SHORT).show();
    }

}

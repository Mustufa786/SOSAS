package edu.aku.hassannaqvi.uen_sosas.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.contracts.ProblemContract;
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_sosas.core.MainApp;
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivitySectionEBinding;
import edu.aku.hassannaqvi.uen_sosas.validator.ClearClass;
import edu.aku.hassannaqvi.uen_sosas.validator.ValidatorClass;

import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.extLst;
import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.pc;
import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.problemType;

public class SectionEActivity extends AppCompatActivity {

    ActivitySectionEBinding bi;
    public static int problem_counter = 0;
    private static int problem_type = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_e);
        bi.setCallback(this);

        setListeners();

        setValues();

    }

    private void setValues() {

        problem_type = MainApp.problemType;

        problem_counter++;

        String strHeading = (problem_type == 1 ? getString(R.string.eye) + " (EYE)"
                : problem_type == 2 ? getString(R.string.ear) + " (EAR)"
                : problem_type == 3 ? getString(R.string.face) + " (FACE)"
                : problem_type == 4 ? getString(R.string.neck) + " (NECK)"
                : problem_type == 5 ? getString(R.string.head) + " (HEAD)"
                : problem_type == 6 ? getString(R.string.chest) + " (CHEST)"
                : problem_type == 7 ? getString(R.string.back) + " (BACK)"
                : problem_type == 8 ? getString(R.string.abdomen) + " (ABDOMEN)"
                : problem_type == 9 ? getString(R.string.groin) + " (Groin/Genitalia/Buttocks)"
                : problem_type == 10 ? getString(R.string.finger) + " (FINGER)"
                : problem_type == 11 ? getString(R.string.thumb) + " (THUMB/HAND)"
                : problem_type == 12 ? getString(R.string.lowerarm) + " (LOWER ARM)"
                : problem_type == 13 ? getString(R.string.upperarm) + " (UPPER ARM)"
                : problem_type == 14 ? getString(R.string.foot) + " (FOOT)"
                : problem_type == 15 ? getString(R.string.lowerleg) + " (LOWER LEG)"
                : getString(R.string.upperleg) + " (UPPER LEG)") + " (" + problem_counter + " out of " + MainApp.problemCount + ")";
        bi.heading.setText(strHeading);

        if (problem_type == 1) {
            bi.te05i.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
            bi.te05m.setVisibility(View.GONE);
            bi.te05n.setVisibility(View.GONE);
            bi.te05o.setVisibility(View.GONE);
            bi.te05p.setVisibility(View.GONE);
            bi.te05q.setVisibility(View.GONE);
            bi.te05r.setVisibility(View.GONE);
            bi.te05s.setVisibility(View.GONE);
            bi.te05t.setVisibility(View.GONE);
            bi.te05u.setVisibility(View.GONE);
            bi.te15cv.setVisibility(View.GONE);
            bi.te11e.setVisibility(View.GONE);
            bi.te11f.setVisibility(View.GONE);
        }

        if (problem_type == 2) {
            bi.te05g.setVisibility(View.GONE);
            bi.te05h.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
            bi.te15cv.setVisibility(View.GONE);
            bi.te11e.setVisibility(View.GONE);
            bi.te11f.setVisibility(View.GONE);
            bi.te05m.setVisibility(View.GONE);
            bi.te05n.setVisibility(View.GONE);
            bi.te05o.setVisibility(View.GONE);
            bi.te05p.setVisibility(View.GONE);
            bi.te05q.setVisibility(View.GONE);
            bi.te05r.setVisibility(View.GONE);
            bi.te05s.setVisibility(View.GONE);
            bi.te05t.setVisibility(View.GONE);
            bi.te05u.setVisibility(View.GONE);
            bi.te05i.setVisibility(View.VISIBLE);
        }

        if (problem_type == 3) {
            bi.te05g.setVisibility(View.GONE);
            bi.te05h.setVisibility(View.GONE);
            bi.te05i.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
            bi.te05m.setVisibility(View.GONE);
            bi.te05n.setVisibility(View.GONE);
            bi.te05o.setVisibility(View.GONE);
            bi.te05p.setVisibility(View.GONE);
            bi.te05q.setVisibility(View.GONE);
            bi.te05r.setVisibility(View.GONE);
            bi.te05s.setVisibility(View.GONE);
            bi.te05t.setVisibility(View.GONE);
            bi.te05u.setVisibility(View.GONE);
            bi.te15cv.setVisibility(View.GONE);
            bi.te11e.setVisibility(View.GONE);
            bi.te11f.setVisibility(View.GONE);
        }

        if (problem_type == 4) {
            bi.te05j.setVisibility(View.VISIBLE);
            bi.te05g.setVisibility(View.GONE);
            bi.te05h.setVisibility(View.GONE);
            bi.te05i.setVisibility(View.GONE);
            bi.te05d.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
            bi.te05m.setVisibility(View.GONE);
            bi.te05n.setVisibility(View.GONE);
            bi.te05o.setVisibility(View.GONE);
            bi.te05p.setVisibility(View.GONE);
            bi.te05q.setVisibility(View.GONE);
            bi.te05r.setVisibility(View.GONE);
            bi.te05s.setVisibility(View.GONE);
            bi.te05t.setVisibility(View.GONE);
            bi.te05u.setVisibility(View.GONE);
            bi.te15cv.setVisibility(View.GONE);
            bi.te11e.setVisibility(View.GONE);
            bi.te11f.setVisibility(View.GONE);

        }

        if (problem_type == 5) {
            bi.te05g.setVisibility(View.GONE);
            bi.te05h.setVisibility(View.GONE);
            bi.te05i.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
            bi.te05m.setVisibility(View.GONE);
            bi.te05n.setVisibility(View.GONE);
            bi.te05o.setVisibility(View.GONE);
            bi.te05p.setVisibility(View.GONE);
            bi.te05q.setVisibility(View.GONE);
            bi.te05r.setVisibility(View.GONE);
            bi.te05s.setVisibility(View.GONE);
            bi.te05t.setVisibility(View.GONE);
            bi.te05u.setVisibility(View.GONE);
            bi.te15cv.setVisibility(View.GONE);
            bi.te11e.setVisibility(View.GONE);
            bi.te11f.setVisibility(View.GONE);
        }

        if (problem_type == 6) {
            bi.te05g.setVisibility(View.GONE);
            bi.te05h.setVisibility(View.GONE);
            bi.te05i.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05m.setVisibility(View.GONE);
            bi.te05n.setVisibility(View.GONE);
            bi.te05o.setVisibility(View.GONE);
            bi.te05p.setVisibility(View.GONE);
            bi.te05q.setVisibility(View.GONE);
            bi.te05r.setVisibility(View.GONE);
            bi.te05s.setVisibility(View.GONE);
            bi.te05t.setVisibility(View.GONE);
            bi.te05u.setVisibility(View.GONE);
            bi.te15cv.setVisibility(View.GONE);
            bi.te11e.setVisibility(View.GONE);
            bi.te11f.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.VISIBLE);
            bi.te05l.setVisibility(View.VISIBLE);
        }

        if (problem_type == 7) {
            bi.te05g.setVisibility(View.GONE);
            bi.te05h.setVisibility(View.GONE);
            bi.te05i.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
            bi.te05m.setVisibility(View.GONE);
            bi.te05n.setVisibility(View.GONE);
            bi.te05o.setVisibility(View.GONE);
            bi.te05p.setVisibility(View.GONE);
            bi.te05q.setVisibility(View.GONE);
            bi.te05r.setVisibility(View.GONE);
            bi.te05s.setVisibility(View.GONE);
            bi.te05t.setVisibility(View.GONE);
            bi.te05u.setVisibility(View.GONE);
            bi.te15cv.setVisibility(View.GONE);
            bi.te11e.setVisibility(View.GONE);
            bi.te11f.setVisibility(View.GONE);
        }

        if (problem_type == 8) {
            bi.te05d.setVisibility(View.GONE);
            bi.te05h.setVisibility(View.GONE);
            bi.te05t.setVisibility(View.GONE);
            bi.te05u.setVisibility(View.GONE);
            bi.te15cv.setVisibility(View.GONE);
            bi.te11e.setVisibility(View.GONE);
            bi.te11f.setVisibility(View.GONE);
            bi.te05i.setVisibility(View.GONE);
            bi.te05g.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05f.setVisibility(View.VISIBLE);
            bi.te05e.setVisibility(View.VISIBLE);
            bi.te05m.setVisibility(View.VISIBLE);
            bi.te05n.setVisibility(View.VISIBLE);
            bi.te05o.setVisibility(View.VISIBLE);
            bi.te05p.setVisibility(View.VISIBLE);
            bi.te05q.setVisibility(View.VISIBLE);
            bi.te05r.setVisibility(View.VISIBLE);
            bi.te05s.setVisibility(View.VISIBLE);
        }

        if (problem_type == 9) {
            bi.te05d.setVisibility(View.GONE);
            bi.te05h.setVisibility(View.GONE);
            bi.te05i.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
            bi.te05n.setVisibility(View.GONE);
            bi.te05o.setVisibility(View.GONE);
            bi.te05p.setVisibility(View.GONE);
            bi.te05q.setVisibility(View.GONE);
            bi.te05u.setVisibility(View.GONE);
            bi.te15cv.setVisibility(View.GONE);
            bi.te11e.setVisibility(View.GONE);
            bi.te11f.setVisibility(View.GONE);
            bi.te05e.setVisibility(View.VISIBLE);
            bi.te05f.setVisibility(View.VISIBLE);
            bi.te05g.setVisibility(View.VISIBLE);
            bi.te05m.setVisibility(View.VISIBLE);
            bi.te05r.setVisibility(View.VISIBLE);
            bi.te05s.setVisibility(View.VISIBLE);
            bi.te05t.setVisibility(View.VISIBLE);
        }

        if (problem_type > 9) {
            bi.te05g.setVisibility(View.GONE);
            bi.te05h.setVisibility(View.GONE);
            bi.te05i.setVisibility(View.GONE);
            bi.te05j.setVisibility(View.GONE);
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
            bi.te05m.setVisibility(View.GONE);
            bi.te05n.setVisibility(View.GONE);
            bi.te05o.setVisibility(View.GONE);
            bi.te05p.setVisibility(View.GONE);
            bi.te05q.setVisibility(View.GONE);
            bi.te05r.setVisibility(View.GONE);
            bi.te05s.setVisibility(View.GONE);
            bi.te05t.setVisibility(View.GONE);
            bi.te05u.setVisibility(View.VISIBLE);
            bi.te11e.setVisibility(View.VISIBLE);
            bi.te11f.setVisibility(View.VISIBLE);
            bi.te15cv.setVisibility(View.VISIBLE);
        }
    }

    private void setListeners() {

        bi.te06.setOnCheckedChangeListener((group, checkedId) -> {

            if (bi.te06b.isChecked()) {
                ClearClass.ClearAllFields(bi.te07cv, null);
            }
        });

        bi.te09.setOnCheckedChangeListener((group, checkedId) -> {

            if (bi.te09b.isChecked()) {
                ClearClass.ClearAllFields(bi.llsos04, null);
            }
        });

        bi.te11.setOnCheckedChangeListener((group, checkedId) -> {

            if (bi.te11b.isChecked()
                    || bi.te11c.isChecked()
                    || bi.te11d.isChecked()
                    || bi.te11e.isChecked()
                    || bi.te11f.isChecked()) {
                ClearClass.ClearAllFields(bi.te12cv, null);
            }

        });

       /* bi.te11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (!bi.te11a.isChecked()) {
                    ClearClass.ClearAllFields(bi.te12cv, null);
                }
            }
        });*/
    }

    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        problemType = 17;
        problem_counter = MainApp.problemCount;
        if (extLst == null) extLst = new ArrayList<>();
        setResult(RESULT_OK);
        finish();
    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);
        long updcount = db.addProblems(pc);

        pc.set_id(String.valueOf(updcount));
        if (updcount > 0) {
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
        pc.setFormdate(MainApp.fc.getFormDate());
        pc.setProblemType(String.valueOf(problem_type));
        pc.setDeviceID(MainApp.deviceId);
        pc.setUser(MainApp.userName);
        pc.setMuid(MainApp.mc.getUid());
        pc.setUuid(MainApp.fc.get_UID());
        pc.setCuid(MainApp.cc.getUid());
        pc.setDevicetagID(preferences.getString("tagName", null));

        SE.put("count", problem_counter);
        SE.put("mother_id", MainApp.motherData.getSerialno());
        SE.put("child_id", MainApp.cc.getSerialNo());
        SE.put("hhno", MainApp.fc.getHhno());
        SE.put("cluster_no", MainApp.fc.getClusterCode());
        SE.put("luid", MainApp.cc.getUuid());
        SE.put("Appver", MainApp.appInfo.getAppVersion());
        SE.put("serialNo", MainApp.cc.getSerialNo());
        SE.put("_fmuid", MainApp.cc.getUid());
        SE.put("motherid", MainApp.cc.getMotherId());

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
                : bi.te05m.isChecked() ? "13"
                : bi.te05n.isChecked() ? "14"
                : bi.te05o.isChecked() ? "15"
                : bi.te05p.isChecked() ? "16"
                : bi.te05q.isChecked() ? "17"
                : bi.te05r.isChecked() ? "18"
                : bi.te05s.isChecked() ? "19"
                : bi.te05t.isChecked() ? "20"
                : bi.te05u.isChecked() ? "21"
                : "0");

       /* SE.put("te05a", bi.te05a.isChecked() ? "1" : "0");
        SE.put("te05b", bi.te05b.isChecked() ? "2" : "0");
        SE.put("te05c", bi.te05c.isChecked() ? "3" : "0");
        SE.put("te05d", bi.te05d.isChecked() ? "4" : "0");
        SE.put("te05e", bi.te05e.isChecked() ? "5" : "0");
        SE.put("te05f", bi.te05f.isChecked() ? "6" : "0");
        SE.put("te05g", bi.te05g.isChecked() ? "7" : "0");
        SE.put("te05h", bi.te05h.isChecked() ? "8" : "0");
        SE.put("te05i", bi.te05i.isChecked() ? "9" : "0");
        SE.put("te05j", bi.te05j.isChecked() ? "10" : "0");
        SE.put("te05k", bi.te05k.isChecked() ? "11" : "0");
        SE.put("te05l", bi.te05l.isChecked() ? "12" : "0");*/


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

        //te15
        SE.put("te15", bi.te15a.isChecked() ? "1"
                : bi.te15b.isChecked() ? "2"
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
                : bi.te11e.isChecked() ? "5"
                : bi.te11f.isChecked() ? "6"
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
//        --MainApp.problemCount;

        MainApp.fc.setsE(String.valueOf(SE));

    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.ll04);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "can not go back", Toast.LENGTH_SHORT).show();
    }

}

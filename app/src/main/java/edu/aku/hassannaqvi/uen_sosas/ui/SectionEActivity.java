package edu.aku.hassannaqvi.uen_sosas.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
            bi.te05g.setText("ٻوڙو ٿيڻ");
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
            bi.te05e.setText("ساهه جي ناليءَ ۾ ڪا شئي ڦاسڻ");
            bi.te05f.setText("پئدائشي نقص");
            bi.te05g.setText("حادثاتي نقص");
            bi.te05h.setText("دل جو مسئلو (پئدائشي)");
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
            bi.te05d.setText("جسم ۾ غير ضروري واڌ، سخت ڳڙ");
            bi.te05e.setText("جسم ۾ غير ضروري واڌ، گهٽجندڙ ڳڙ");
            bi.te05f.setText("پئدائشي نقص");
            bi.te05g.setText("حادثاتي نقص");
            bi.te05h.setText("پيٽ ۾ سور يا مروڙ پوڻ يا پيٽ سڄڻ");
        }

        if (MainApp.problemType == 9) {
            bi.te05e.setText("جسم ۾ غير ضروري واڌ، گهٽجندڙ ڳڙ");
            bi.te05f.setText("پئدائشي نقص");
            bi.te05g.setText("حادثاتي نقص");
            bi.te05h.setText("پائخاني يا پيشاب جو هر وقت نڪري وڃڻ");
            bi.te05i.setText("پائخاني ۾ رت اچڻ");
            bi.te05j.setText("پيشاب جي نالي مان رت اچڻ");
            bi.te05k.setVisibility(View.GONE);
            bi.te05l.setVisibility(View.GONE);
        }
    }

    private void setListeners() {

        --MainApp.problemCount;
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
            if (MainApp.problemCount > 0) {
                finish();
                startActivity(new Intent(this, SectionEActivity.class));
            } else {
                startActivity(new Intent(this, MainApp.problemType != 10 ? SectionCActivity.class : EndingActivity.class).putExtra("complete", true));
            }
//            try {
//                SaveDraft();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            if (UpdateDB()) {
//
//            } else {
//                Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show();
//            }
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

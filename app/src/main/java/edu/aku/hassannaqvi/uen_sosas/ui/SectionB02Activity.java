package edu.aku.hassannaqvi.uen_sosas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivitySectionB02Binding;
import edu.aku.hassannaqvi.uen_sosas.ui.other.EndingActivity;
import edu.aku.hassannaqvi.uen_sosas.validator.ClearClass;
import edu.aku.hassannaqvi.uen_sosas.validator.ValidatorClass;

import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.mc;

public class SectionB02Activity extends AppCompatActivity {

    ActivitySectionB02Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_b_02);
        bi.setCallback(this);

        setListeners();
    }

    private void setListeners() {

        bi.td15.setOnCheckedChangeListener((group, checkedId) -> {

            if (bi.td15b.isChecked()) {
                ClearClass.ClearAllFields(bi.td16sos, null);
            }
        });

    }

    public void BtnContinue() {
        if (formValidation()) {
            savingContinue();
        }
    }

    private void savingContinue() {
        try {
            SaveDraft();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            if (bi.td15b.isChecked()) {
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

        JSONObject SB = new JSONObject();
        SB.put("td15", bi.td15a.isChecked() ? "1"
                : bi.td15b.isChecked() ? "2"
                : "0");
        //td02a
//        SB.put("td02a", bi.td02a.getText().toString());


//        mc.setdA(String.valueOf(SA));


    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.ll03);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "can not go back", Toast.LENGTH_SHORT).show();
    }
}

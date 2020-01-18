package edu.aku.hassannaqvi.uen_sosas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivitySectionB02Binding;
import edu.aku.hassannaqvi.uen_sosas.ui.other.EndingActivity;
import edu.aku.hassannaqvi.uen_sosas.validator.ClearClass;
import edu.aku.hassannaqvi.uen_sosas.validator.ValidatorClass;

import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.extLst;
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
        if (true) {
            if (bi.td15a.isChecked()) {
                finish();
                startActivity(new Intent(this, SectionCActivity.class));
            } else {
                setResult(RESULT_OK);
                finish();
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

        Map<Integer, String> extMap = new HashMap<Integer, String>() {
            {
                put(10, "FINGERS EXTREMITIES");
                put(11, "THUMB/HAND EXTREMITIES");
                put(12, "LOWER ARM EXTREMITIES");
                put(13, "UPPER ARM EXTREMITIES");
                put(14, "FOOT EXTREMITIES");
                put(15, "LOWER LEG EXTREMITIES");
                put(16, "UPPER LEG EXTREMITIES");
            }
        };

        extLst = new ArrayList<>();

        for (int i = 0; i < bi.td16sosChk.getChildCount(); i++) {
            View view = bi.td16sosChk.getChildAt(i);
            if (view instanceof CheckBox) {
                if (((CheckBox) view).isChecked())
                    extLst.add(10 + i);
            }
        }

    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.ll03);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "can not go back", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}

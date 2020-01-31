package edu.aku.hassannaqvi.uen_sosas.ui.other;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_sosas.core.MainApp;
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivityEndingBinding;
import edu.aku.hassannaqvi.uen_sosas.validator.ValidatorClass;

public class EndingActivity extends AppCompatActivity {

    ActivityEndingBinding bi;

    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_ending);
        bi.setCallback(this);

        boolean check = getIntent().getExtras().getBoolean("complete");

        if (check) {
            bi.istatus1.setEnabled(true);
            bi.istatus5.setEnabled(false);
        } else {
            //fldGrpmn0823Reason.setVisibility(View.GONE);
            bi.istatus1.setEnabled(false);
        }

        MainApp.problemType = 0;
        MainApp.childCount = 0;


    }

    public void BtnContinue() {
        if (formValidation()) {
            SaveDraft();
            if (UpdateDB()) {
                finish();
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveDraft() {
        MainApp.fc.setIstatus(bi.istatus1.isChecked() ? "1"
                : bi.istatus5.isChecked() ? "5"
                : "0");
        MainApp.fc.setEndingdatetime(dtToday);
    }

    private boolean UpdateDB() {
        DatabaseHelper db = new DatabaseHelper(this);
        int updcount = db.updateEnding();
        return updcount == 1;
    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.llend);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "You Can't go back", Toast.LENGTH_LONG).show();
    }


}

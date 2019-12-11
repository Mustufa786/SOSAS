package edu.aku.hassannaqvi.rsvstudy.ui.form1;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.rsvstudy.R;
import edu.aku.hassannaqvi.rsvstudy.contracts.ChildList;
import edu.aku.hassannaqvi.rsvstudy.contracts.FormsContract;
import edu.aku.hassannaqvi.rsvstudy.core.DatabaseHelper;
import edu.aku.hassannaqvi.rsvstudy.core.MainApp;
import edu.aku.hassannaqvi.rsvstudy.databinding.ActivityF1Section01Binding;
import edu.aku.hassannaqvi.rsvstudy.ui.other.EndingActivity;
import edu.aku.hassannaqvi.rsvstudy.utils.DateUtils;
import edu.aku.hassannaqvi.rsvstudy.validator.ValidatorClass;

public class Section01Activity extends AppCompatActivity {

    public static String DOB;
    ActivityF1Section01Binding bi;
    private List<String> dssID, motherName, fatherName, hHhead, studyId;
    private DatabaseHelper db;
    private ChildList cContract;
    private String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());

    ChildList childData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_f1_section01);
        bi.setCallback(this);

        childData = getIntent().getParcelableExtra("data");

        setupViews();
    }

    private void setupViews() {

        bi.dssID.setText(childData.getDssid());
        bi.studyID.setText(childData.getStudy_id());
        bi.fatherName.setText(childData.getFather_name());
        bi.motherName.setText(childData.getMother_name());
        bi.dob.setText(childData.getDob());
        MainApp.DOB = childData.getDob();
        bi.gender.setText(childData.equals("1") ? "Male" : "Female");
        bi.genderImage.setImageResource(childData.getGender().equals("1") ? R.drawable.boy : R.drawable.girl);
        bi.months.setText(String.valueOf(DateUtils.ageInMonthsByDOB(DateUtils.getDate(childData.getDob()))));


        bi.RS16.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == bi.RS16c.getId() || checkedId == bi.RS16b.getId()) {
                    MainApp.status = 3;
                }
                if (checkedId == bi.RS16d.getId()) {
                    MainApp.status = 5;
                }
                if (checkedId == bi.RS16e.getId()) {
                    MainApp.status = 6;
                }
            }
        });

        bi.RS15.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //calculating age in months w.r.t visit date
                Long afterVisitDate = DateUtils.dobDiff(DateUtils.getCalDate(childData.getDob()), DateUtils.getCalDate(bi.RS15.getText().toString()));
//                Long actualDOB = DateUtils.ageInMonthsByDOB(DateUtils.getCalDate(childData.getDob()));
                bi.dobInMonths.setText(afterVisitDate + " month(s)");


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
                if (bi.RS16a.isChecked()) {
                    finish();
                    startActivity(new Intent(this, EndingActivity.class));
                } else {
                    Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
                }
            }
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

//        DatabaseHelper db = new DatabaseHelper(this);
//        long updcount = db.addForm(MainApp.fc);
//
//        MainApp.fc.set_ID(String.valueOf(updcount));
//        if (updcount != 0) {
//            MainApp.fc.set_UID(
//                    (MainApp.fc.getDeviceID() + MainApp.fc.get_ID()));
//            db.updateFormID();
//            return true;
//        } else {
//            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        return true;
    }


    private void SaveDraft() throws JSONException {



    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.ll01);
    }

}


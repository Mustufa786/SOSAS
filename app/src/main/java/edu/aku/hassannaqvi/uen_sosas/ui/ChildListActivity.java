package edu.aku.hassannaqvi.uen_sosas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.adapter.ChildListAdapter;
import edu.aku.hassannaqvi.uen_sosas.contracts.ChildList;
import edu.aku.hassannaqvi.uen_sosas.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_sosas.core.MainApp;
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivityChildListBinding;
import edu.aku.hassannaqvi.uen_sosas.ui.other.EndingActivity;

import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.motherData;
import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.problemType;

public class ChildListActivity extends AppCompatActivity {


    int areaCode;
    ActivityChildListBinding bi;
    DatabaseHelper db;
    ChildListAdapter adapter;
    List<FamilyMembersContract> list;
    ArrayList<String> dssids;
    ArrayList<ChildList> filteredItems;
    private TextView dssID;
    public static final int CHILD_MAIN = 1;
    int childCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_child_list);

        this.setTitle("Child List");
        db = new DatabaseHelper(this);
        setupViews();
    }

    private void setupViews() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        bi.childlist.setLayoutManager(manager);
        bi.childlist.setHasFixedSize(true);
        list = db.getChildrenList(motherData.getHhno(), motherData.getClusterno(), motherData.getSerialno());
        setupRecyclerView(list);
    }

    private void setupRecyclerView(List<FamilyMembersContract> list) {
        adapter = new ChildListAdapter(this, list, false);
        bi.childlist.setAdapter(adapter);
        adapter.setItemClicked((item, position, isMother) -> {

            MainApp.openDialog(ChildListActivity.this, item, isMother);
            MainApp.setItemClick(() -> {

                childCount++;

                startActivityForResult(new Intent(ChildListActivity.this, SectionCActivity.class), CHILD_MAIN);
                MainApp.childData = item;

            });

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHILD_MAIN) {
            if (resultCode == RESULT_OK) {
                if (childCount == list.size()) {
                    finish();
                    startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
                } else
                    problemType = 0;
            }
        }
    }
}

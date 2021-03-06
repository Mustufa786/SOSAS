package edu.aku.hassannaqvi.uen_sosas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.adapter.ChildListAdapter;
import edu.aku.hassannaqvi.uen_sosas.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_sosas.core.MainApp;
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivityChildListBinding;

import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.motherData;
import static edu.aku.hassannaqvi.uen_sosas.core.MainApp.problemType;

public class ChildListActivity extends AppCompatActivity {

    ActivityChildListBinding bi;
    DatabaseHelper db;
    ChildListAdapter adapter;
    List<FamilyMembersContract> list;
    public static final int CHILD_MAIN = 1;
    public static final int CHILD_MAIN_END = 101;
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
        adapter.setItemClicked((item, position, isMother, holder) -> {

            MainApp.openDialog(ChildListActivity.this, item, isMother);
            MainApp.setItemClick(() -> {

                holder.parentLayout.setEnabled(false);
                holder.parentLayout.setBackgroundColor(getResources().getColor(R.color.gray));

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
                    problemType = 0;
                    finish();
                } else
                    problemType = 0;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "can not go back", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*if (childCount == list.size()) {
            finish();
            if (MainApp.fc.getIstatus().equals("")) return;
            startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
        } else
            problemType = 0;*/
    }
}

package edu.aku.hassannaqvi.uen_sosas.ui.other;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.aku.hassannaqvi.uen_sosas.R;
import edu.aku.hassannaqvi.uen_sosas.contracts.AreasContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.FormsContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.VersionAppContract;
import edu.aku.hassannaqvi.uen_sosas.core.AndroidDatabaseManager;
import edu.aku.hassannaqvi.uen_sosas.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_sosas.core.MainApp;
import edu.aku.hassannaqvi.uen_sosas.databinding.ActivityMainBinding;
import edu.aku.hassannaqvi.uen_sosas.get.GetFamilyMembersData;
import edu.aku.hassannaqvi.uen_sosas.ui.InfoActivity;
import edu.aku.hassannaqvi.uen_sosas.ui.SectionBActivity;
import edu.aku.hassannaqvi.uen_sosas.ui.SectionCActivity;
import edu.aku.hassannaqvi.uen_sosas.ui.SectionDAActivity;
import edu.aku.hassannaqvi.uen_sosas.ui.SectionEActivity;
import edu.aku.hassannaqvi.uen_sosas.ui.sync.SyncActivity;

public class MainActivity extends AppCompatActivity {

    static File file;
    private final String TAG = "MainActivity";
    ActivityMainBinding bi;
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    String dtToday1 = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    AlertDialog.Builder builder;
    String m_Text = "";
    ProgressDialog mProgressDialog;
    ArrayList<String> lablesAreas;
    Collection<AreasContract> AreasList;
    Map<String, String> AreasMap;
    SharedPreferences sharedPrefDownload;
    SharedPreferences.Editor editorDownload;
    DownloadManager downloadManager;
    String preVer = "", newVer = "";
    VersionAppContract versionAppContract;
    DatabaseHelper db;
    Long refID;
    private ProgressDialog pd;
    private Boolean exit = false;
    private String rSumText = "";

    private void loadTagDialog() {
        sharedPref = getSharedPreferences("tagName", MODE_PRIVATE);
        editor = sharedPref.edit();
        if (!sharedPref.contains("tagName") && sharedPref.getString("tagName", null) == null) {

            builder = new AlertDialog.Builder(MainActivity.this);
            ImageView img = new ImageView(getApplicationContext());
            img.setImageResource(R.drawable.tagimg);
            img.setPadding(0, 15, 0, 15);
            builder.setCustomTitle(img);

            final EditText input = new EditText(MainActivity.this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();
                    if (!m_Text.equals("")) {
                        editor.putString("tagName", m_Text);
                        editor.commit();
                        dialog.dismiss();

                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(sharedPrefDownload.getLong("refID", 0));

                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Cursor cursor = downloadManager.query(query);
                if (cursor.moveToFirst()) {
                    int colIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(colIndex)) {

                        editorDownload.putBoolean("flag", true);
                        editorDownload.commit();

                        Toast.makeText(context, "New App downloaded!!", Toast.LENGTH_SHORT).show();
                        bi.lblAppVersion.setText("TMK APP New Version " + newVer + "  Downloaded.");

                        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

                        if (taskInfo.get(0).topActivity.getClassName().equals(MainActivity.class.getName())) {
//                                InstallNewApp(newVer, preVer);
                            showDialog(newVer, preVer);
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bi.setCallback(this);


        db = new DatabaseHelper(this);

        Collection<FormsContract> todaysForms = db.getTodayForms();
        Collection<FormsContract> unsyncedForms = db.getUnsyncedForms();

        rSumText += "TODAY'S RECORDS SUMMARY\r\n";

        rSumText += "=======================\r\n";
        rSumText += "\r\n";
        rSumText += "Total Forms Today" + "(" + dtToday1 + "): " + todaysForms.size() + "\r\n";
        rSumText += "\r\n";
        if (todaysForms.size() > 0) {
            rSumText += "\tFORMS' LIST: \r\n";
            String iStatus;
            rSumText += "--------------------------------------------------\r\n";
            rSumText += "[ DSS ID ] \t[Form Status] \t[Sync Status]\r\n";
            rSumText += "--------------------------------------------------\r\n";

            for (FormsContract fc : todaysForms) {
                if (fc.getIstatus() != null) {
                    switch (fc.getIstatus()) {
                        case "1":
                            iStatus = "Complete";
                            break;
                        case "2":
                            iStatus = "Incomplete";
                            break;
                        case "3":
                            iStatus = "Refused";
                            break;
                        case "4":
                            iStatus = "Refused";
                            break;
                        default:
                            iStatus = "N/A";
                    }
                } else {
                    iStatus = "N/A";
                }

                rSumText += fc.getDSSID();
                rSumText += "\t\t\t\t\t";

                rSumText += iStatus;
                rSumText += "\t\t\t\t\t";

                rSumText += (fc.getSynced() == null ? "Not Synced" : "Synced");
                rSumText += "\r\n";
                rSumText += "--------------------------------------------------\r\n";
            }
        }


        if (MainApp.admin) {
            bi.adminsec.setVisibility(View.VISIBLE);
            bi.databaseBtn.setVisibility(View.VISIBLE);
            SharedPreferences syncPref = getSharedPreferences("SyncInfo", Context.MODE_PRIVATE);
            rSumText += "Last Data Download: \t" + syncPref.getString("LastDownSyncServer", "Never Updated");
            rSumText += "\r\n";
            rSumText += "Last Data Upload: \t" + syncPref.getString("LastUpSyncServer", "Never Synced");
            rSumText += "\r\n";
            rSumText += "\r\n";
            rSumText += "Unsynced Forms: \t" + unsyncedForms.size();
            rSumText += "\r\n";
        } else {
            bi.adminsec.setVisibility(View.GONE);
            bi.databaseBtn.setVisibility(View.GONE);
        }
        Log.d(TAG, "onCreate: " + rSumText);
        bi.recordSummary.setText(rSumText);

        sharedPrefDownload = getSharedPreferences("appDownload", MODE_PRIVATE);
        editorDownload = sharedPrefDownload.edit();
        versionAppContract = db.getVersionApp();
        if (versionAppContract.getVersioncode() != null) {

            preVer = MainApp.versionName + "." + MainApp.versionCode;
            newVer = versionAppContract.getVersionname() + "." + versionAppContract.getVersioncode();

            if (MainApp.versionCode < Integer.valueOf(versionAppContract.getVersioncode())) {
                bi.lblAppVersion.setVisibility(View.VISIBLE);

                String fileName = DatabaseHelper.DATABASE_NAME.replace(".db", "-New-Apps");
                file = new File(Environment.getExternalStorageDirectory() + File.separator + fileName, versionAppContract.getPathname());

                if (file.exists()) {
                    bi.lblAppVersion.setText("SOSAS App New Version " + newVer + "  Downloaded.");
//                    InstallNewApp(newVer, preVer);
                    showDialog(newVer, preVer);
                } else {
                    NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        bi.lblAppVersion.setText("SOSASAPP New Version " + newVer + " Downloading..");
                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        Uri uri = Uri.parse(MainApp._UPDATE_URL + versionAppContract.getPathname());
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setDestinationInExternalPublicDir(fileName, versionAppContract.getPathname())
                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                .setTitle("Downloading SOSAS App new App ver." + newVer);
                        refID = downloadManager.enqueue(request);

                        editorDownload.putLong("refID", refID);
                        editorDownload.putBoolean("flag", false);
                        editorDownload.commit();

                    } else {
                        bi.lblAppVersion.setText("SOSAS App New Version " + newVer + "  Available..\n(Can't download.. Internet connectivity issue!!)");
                    }
                }

            } else {
                bi.lblAppVersion.setVisibility(View.GONE);
                bi.lblAppVersion.setText(null);
            }
        }
        registerReceiver(broadcastReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

//        Testing visibility
        if (Integer.valueOf(MainApp.versionName.split("\\.")[0]) > 0) {
            bi.testing.setVisibility(View.GONE);
        } else {
            bi.testing.setVisibility(View.VISIBLE);
        }

        loadTagDialog();

    }

    void showDialog(String newVer, String preVer) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = MyDialogFragment.newInstance(newVer, preVer);
        newFragment.show(ft, "dialog");

    }

    public void OpenFormFunc() {
        Intent oF = new Intent();
        if (!MainApp.userName.equals("0000")) {
            oF = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(oF);
        } else {
            Toast.makeText(getApplicationContext(), "Please login Again!", Toast.LENGTH_LONG).show();
        }


    }

    public void openDB() {
        Intent dbmanager = new Intent(getApplicationContext(), AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }


    public void CheckCluster(View v) {
//        Intent cluster_list = new Intent(getApplicationContext(), FormsList.class);
//        cluster_list.putExtra("dssid", MainApp.regionDss);
//        startActivity(cluster_list);

    }

    public void syncServer() {
        // Require permissions INTERNET & ACCESS_NETWORK_STATE
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            startActivity(new Intent(this, SyncActivity.class));
        } else {
            Toast.makeText(this, "No network connection available!", Toast.LENGTH_SHORT).show();
        }

    }

    public void syncFamilyMembers() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            // Sync Random
            new GetFamilyMembersData(this).execute();
            SharedPreferences syncPref = getSharedPreferences("SyncInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = syncPref.edit();
            editor.putString("LastDownSyncServer", dtToday);

            editor.apply();
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity

            startActivity(new Intent(this, LoginActivity.class));

        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.onSync:
                startActivity(new Intent(MainActivity.this, SyncActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void openB(View v) {
        Intent oF = new Intent(MainActivity.this, SectionBActivity.class);
        startActivity(oF);
    }

    public void openC(View v) {
        Intent oF = new Intent(MainActivity.this, SectionCActivity.class);
        startActivity(oF);
    }

    public void openD(View v) {
        Intent oF = new Intent(MainActivity.this, SectionDAActivity.class);
        startActivity(oF);
    }

    public void openE(View v) {
        Intent oF = new Intent(MainActivity.this, SectionEActivity.class);
        startActivity(oF);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public static class MyDialogFragment extends DialogFragment {

        String newVer, preVer;

        static MyDialogFragment newInstance(String newVer, String preVer) {
            MyDialogFragment f = new MyDialogFragment();

            Bundle args = new Bundle();
            args.putString("newVer", newVer);
            args.putString("preVer", preVer);
            f.setArguments(args);

            return f;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            newVer = getArguments().getString("newVer");
            preVer = getArguments().getString("preVer");

            return new AlertDialog.Builder(getActivity())
                    .setIcon(R.drawable.exclamation)
                    .setTitle("SOSAS APP is available!")
                    .setCancelable(false)
                    .setMessage("SOSAS App " + newVer + " is now available. Your are currently using older version " + preVer + ".\nInstall new version to use this app.")
                    .setPositiveButton("INSTALL!!",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                    )
                    .create();
        }

    }

}

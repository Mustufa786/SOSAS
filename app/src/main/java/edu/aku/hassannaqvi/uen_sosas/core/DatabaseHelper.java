package edu.aku.hassannaqvi.uen_sosas.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.uen_sosas.contracts.AreasContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.AreasContract.singleAreas;
import edu.aku.hassannaqvi.uen_sosas.contracts.BLRandomContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.BLRandomContract.singleChild;
import edu.aku.hassannaqvi.uen_sosas.contracts.ChildList;
import edu.aku.hassannaqvi.uen_sosas.contracts.ChildrenContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.FamilyMembersContract.singleMember;
import edu.aku.hassannaqvi.uen_sosas.contracts.FormsContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.FormsContract.FormsTable;
import edu.aku.hassannaqvi.uen_sosas.contracts.LHWContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.MWRAContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.TalukasContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.UCsContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.UsersContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.VersionAppContract;
import edu.aku.hassannaqvi.uen_sosas.contracts.VillagesContract;
import edu.aku.hassannaqvi.uen_sosas.otherClasses.MotherLst;


/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String SQL_CREATE_USERS = "CREATE TABLE " + UsersContract.singleUser.TABLE_NAME + "("
            + UsersContract.singleUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UsersContract.singleUser.ROW_USERNAME + " TEXT,"
            + UsersContract.singleUser.ROW_PASSWORD + " TEXT,"
            + UsersContract.singleUser.FULL_NAME + " TEXT"
            + " );";
    public static final String DATABASE_NAME = "rsvStudy.db";
    public static final String DB_NAME = "rsvStudy_copy.db";
    public static final String PROJECT_NAME = "DMU-RSVSTUDY";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_FORMS = "CREATE TABLE "
            + FormsTable.TABLE_NAME + "("
            + FormsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FormsTable.COLUMN_PROJECT_NAME + " TEXT,"
            + FormsTable.COLUMN_UID + " TEXT," +
            FormsTable.COLUMN_FORMDATE + " TEXT," +
            FormsTable.COLUMN_APPVERSION + " TEXT," +
            FormsTable.COLUMN_STATUS + " TEXT," +
            FormsTable.COLUMN_FORMTYPE + " TEXT," +
            FormsTable.COLUMN_DSSID + " TEXT," +
            FormsTable.COLUMN_NEXT_VISIT + " TEXT," +
            FormsTable.COLUMN_USER + " TEXT," +
            FormsTable.COLUMN_SA + " TEXT," +
            FormsTable.COLUMN_SB + " TEXT," +
            FormsTable.COLUMN_SC + " TEXT," +
            FormsTable.COLUMN_SD + " TEXT," +
            FormsTable.COLUMN_SE + " TEXT," +
            FormsTable.COLUMN_SF + " TEXT," +
            FormsTable.COLUMN_ISTATUS + " TEXT," +
            FormsTable.COLUMN_ISTATUS88x + " TEXT," +
            FormsTable.COLUMN_ENDINGDATETIME + " TEXT," +
            FormsTable.COLUMN_GPSLAT + " TEXT," +
            FormsTable.COLUMN_GPSLNG + " TEXT," +
            FormsTable.COLUMN_GPSDATE + " TEXT," +
            FormsTable.COLUMN_GPSACC + " TEXT," +
            FormsTable.COLUMN_DEVICEID + " TEXT," +
            FormsTable.COLUMN_DEVICETAGID + " TEXT," +
            FormsTable.COLUMN_SYNCED + " TEXT," +
            FormsTable.COLUMN_SYNCED_DATE + " TEXT"
            + " );";
    private static final String SQL_SELECT_MOTHER_BY_CHILD =
            "SELECT c.agem cm, c.agey cy, c.aged cd, c.gender, c.serial serial, m.serial serialm, c.name child_name, c.dss_id_member child_id, m.name mother_name, c.dss_id_member mother_id, c.dob date_of_birth FROM census C join census m on c.dss_id_m = m.dss_id_member where c.member_type =? and c.uuid = m.uuid and c.current_status IN ('1', '2') and c.uuid = ? group by mother_id order by substr(c.dob, 7) desc, substr(c.dob, 4,2) desc, substr(c.dob, 1,2) desc;";
    private static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS " + UsersContract.singleUser.TABLE_NAME;
    private static final String SQL_DELETE_FORMS =
            "DROP TABLE IF EXISTS " + FormsTable.TABLE_NAME;
    private static final String SQL_SELECT_CHILD =
            "SELECT * from census where member_type =? and dss_id_hh =? and uuid =? and current_status IN ('1', '2')";
    private static final String SQL_SELECT_MWRA =
            "SELECT * from census where member_type =? and dss_id_hh =? and uuid =? and current_status IN ('1', '2')";
    private static final String SQL_DELETE_CHILDREN = "DROP TABLE IF EXISTS " + ChildrenContract.singleChild.TABLE_NAME;
    private static final String SQL_DELETE_CHILDLIST = "DROP TABLE IF EXISTS " + ChildList.singleChildList.TABLE_NAME;
    private static final String SQL_DELETE_VILLAGES = "DROP TABLE IF EXISTS " + VillagesContract.singleVillage.TABLE_NAME;
    private static final String SQL_DELETE_TALUKAS = "DROP TABLE IF EXISTS " + TalukasContract.singleTalukas.TABLE_NAME;
    private static final String SQL_DELETE_UCS = "DROP TABLE IF EXISTS " + UCsContract.singleUCs.TABLE_NAME;
    private static final String SQL_DELETE_AREAS = "DROP TABLE IF EXISTS " + singleAreas.TABLE_NAME;
    final String SQL_CREATE_VERSIONAPP = "CREATE TABLE " + VersionAppContract.VersionAppTable.TABLE_NAME + " (" +
            VersionAppContract.VersionAppTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            VersionAppContract.VersionAppTable.COLUMN_VERSION_CODE + " TEXT, " +
            VersionAppContract.VersionAppTable.COLUMN_VERSION_NAME + " TEXT, " +
            VersionAppContract.VersionAppTable.COLUMN_PATH_NAME + " TEXT " +
            ");";
    final String SQL_CREATE_PSU_TABLE = "CREATE TABLE " + VillagesContract.singleVillage.TABLE_NAME + " (" +
            VillagesContract.singleVillage._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            VillagesContract.singleVillage.COLUMN_TALUKA_CODE + " TEXT, " +
            VillagesContract.singleVillage.COLUMN_TALUKA_NAME + " TEXT, " +
            VillagesContract.singleVillage.COLUMN_UC_CODE + " TEXT, " +
            VillagesContract.singleVillage.COLUMN_UC_NAME + " TEXT, " +
            VillagesContract.singleVillage.COLUMN_VILLAGE_CODE + " TEXT, " +
            VillagesContract.singleVillage.COLUMN_VILLAGE_NAME + " TEXT " +
            ");";
    final String SQL_CREATE_LHW_TABLE = "CREATE TABLE " + LHWContract.lhwEntry.TABLE_NAME + " (" +
            LHWContract.lhwEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LHWContract.lhwEntry.COLUMN_TALUKA_CODE + " TEXT, " +
            LHWContract.lhwEntry.COLUMN_TALUKA_NAME + " TEXT, " +
            LHWContract.lhwEntry.COLUMN_UC_CODE + " TEXT, " +
            LHWContract.lhwEntry.COLUMN_UC_NAME + " TEXT, " +
            LHWContract.lhwEntry.COLUMN_LHW_CODE + " TEXT, " +
            LHWContract.lhwEntry.COLUMN_LHW_NAME + " TEXT " +
            ");";

    final String SQL_CREATE_TALUKAS = "CREATE TABLE " + TalukasContract.singleTalukas.TABLE_NAME + "("
            + TalukasContract.singleTalukas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TalukasContract.singleTalukas.COLUMN_TALUKA_CODE + " TEXT,"
            + TalukasContract.singleTalukas.COLUMN_TALUKA + " TEXT );";
    final String SQL_CREATE_UCS = "CREATE TABLE " + UCsContract.singleUCs.TABLE_NAME + "("
            + UCsContract.singleUCs._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UCsContract.singleUCs.COLUMN_UCCODE + " TEXT,"
            + UCsContract.singleUCs.COLUMN_TALUKA_CODE + " TEXT,"
            + UCsContract.singleUCs.COLUMN_UCS + " TEXT );";
    final String SQL_CREATE_AREAS = "CREATE TABLE " + singleAreas.TABLE_NAME + "("
            + singleAreas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + singleAreas.COLUMN_AREACODE + " TEXT,"
            + singleAreas.COLUMN_UC_CODE + " TEXT,"
            + singleAreas.COLUMN_AREA + " TEXT );";
    final String SQL_CREATE_CHILDREN = "CREATE TABLE " + ChildrenContract.singleChild.TABLE_NAME + "("
            + ChildrenContract.singleChild._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ChildrenContract.singleChild.COLUMN_LHW_CODE + " TEXT,"
            + ChildrenContract.singleChild.COLUMN_CASEID + " TEXT,"
            + ChildrenContract.singleChild.COLUMN_CHILD_NAME + " TEXT, "
            + ChildrenContract.singleChild.COLUMN_F_NAME + " TEXT,"
            + ChildrenContract.singleChild.COLUMN_REF_DATE + " TEXT, "
            + ChildrenContract.singleChild.COLUMN_LUID + " TEXT );";


    final String SQL_CREATE_CHILDLIST = "CREATE TABLE " + ChildList.singleChildList.TABLE_NAME + "("
            + ChildList.singleChildList._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ChildList.singleChildList.COLUMN_DSSID + " TEXT,"
            + ChildList.singleChildList.COLUMN_MOTHER_NAME + " TEXT,"
            + ChildList.singleChildList.COLUMN_FATHER_NAME + " TEXT, "
            + ChildList.singleChildList.COLUMN_HHHEAD + " TEXT,"
            + ChildList.singleChildList.COLUMN_DOB + " TEXT,"
            + ChildList.singleChildList.COLUMN_GENDER + " TEXT,"
            + ChildList.singleChildList.COLUMN_AREACODE + " TEXT,"
            + ChildList.singleChildList.COLUMN_STUDY_ID + " TEXT );";

    final String SQL_CREATE_FAMILY_MEMBERS = "CREATE TABLE " + singleMember.TABLE_NAME + "("
            + singleMember._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + singleMember.COLUMN_UID + " TEXT,"
            + singleMember.COLUMN_UUID + " TEXT,"
            + singleMember.COLUMN_FORMDATE + " TEXT,"
            + singleMember.COLUMN_AGE + " TEXT,"
            + singleMember.COLUMN_CLUSTER_CODE + " TEXT,"
            + singleMember.COLUMN_HHNO + " TEXT,"
            + singleMember.COLUMN_MOTHER_ID + " TEXT,"
            + singleMember.COLUMN_NAME + " TEXT,"
            + singleMember.COLUMN_SERIAL_NO + " TEXT,"
            + singleMember.COLUMN_MOMTHER_NAME + " TEXT,"
            + singleMember.COLUMN_TYPE + " TEXT );";


    private final String TAG = "DatabaseHelper";


    public String spDateT = new SimpleDateFormat("dd-MM-yy").format(new Date().getTime());


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_FORMS);
        db.execSQL(SQL_CREATE_TALUKAS);
        db.execSQL(SQL_CREATE_UCS);
        db.execSQL(SQL_CREATE_CHILDREN);
        db.execSQL(SQL_CREATE_CHILDLIST);
        db.execSQL(SQL_CREATE_PSU_TABLE);
        db.execSQL(SQL_CREATE_AREAS);
        db.execSQL(SQL_CREATE_LHW_TABLE);
        db.execSQL(SQL_CREATE_VERSIONAPP);
        db.execSQL(SQL_CREATE_FAMILY_MEMBERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL(SQL_DELETE_USERS);
//        db.execSQL(SQL_DELETE_FORMS);
//        db.execSQL("DROP TABLE IF EXISTS " + LHWContract.lhwEntry.TABLE_NAME);
//        db.execSQL(SQL_DELETE_CHILDREN);
//        db.execSQL(SQL_DELETE_CHILDLIST);
//        db.execSQL(SQL_DELETE_VILLAGES);
//        db.execSQL(SQL_DELETE_TALUKAS);
//        db.execSQL(SQL_DELETE_UCS);
//        db.execSQL(SQL_DELETE_AREAS);

    }

    public void syncChildren(JSONArray pcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChildrenContract.singleChild.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = pcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectCC = jsonArray.getJSONObject(i);

                ChildrenContract cc = new ChildrenContract();
                cc.sync(jsonObjectCC);
                Log.i(TAG, "syncChildren: " + jsonObjectCC.toString());

                ContentValues values = new ContentValues();

                values.put(ChildrenContract.singleChild.COLUMN_LHW_CODE, cc.getLhw_code());
                values.put(ChildrenContract.singleChild.COLUMN_CASEID, cc.getCaseid());
                values.put(ChildrenContract.singleChild.COLUMN_CHILD_NAME, cc.getChild_name());
                values.put(ChildrenContract.singleChild.COLUMN_F_NAME, cc.getF_name());
                values.put(ChildrenContract.singleChild.COLUMN_REF_DATE, cc.getRef_date());
                values.put(ChildrenContract.singleChild.COLUMN_LUID, cc.getLuid());

                db.insert(ChildrenContract.singleChild.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }

    public void syncChildlist(JSONArray cList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ChildList.singleChildList.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = cList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectCL = jsonArray.getJSONObject(i);

                ChildList cl = new ChildList();
                cl.sync(jsonObjectCL);
                Log.i(TAG, "syncChildlist: " + jsonObjectCL.toString());

                ContentValues values = new ContentValues();

                values.put(ChildList.singleChildList.COLUMN_DSSID, cl.getDssid());
                values.put(ChildList.singleChildList.COLUMN_MOTHER_NAME, cl.getMother_name());
                values.put(ChildList.singleChildList.COLUMN_FATHER_NAME, cl.getFather_name());
                values.put(ChildList.singleChildList.COLUMN_HHHEAD, cl.getHhhead());
                values.put(ChildList.singleChildList.COLUMN_STUDY_ID, cl.getStudy_id());
                values.put(ChildList.singleChildList.COLUMN_DOB, cl.getDob());
                values.put(ChildList.singleChildList.COLUMN_GENDER, cl.getGender());
                values.put(ChildList.singleChildList.COLUMN_AREACODE, cl.getAreacode());

                db.insert(ChildList.singleChildList.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }

    public void syncFamilyMembers(JSONArray Areaslist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(singleMember.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = Areaslist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectCC = jsonArray.getJSONObject(i);

                FamilyMembersContract Vc = new FamilyMembersContract();
                Vc.Sync(jsonObjectCC);

                ContentValues values = new ContentValues();

                values.put(singleMember.COLUMN_UID, Vc.getUid());
                values.put(singleMember.COLUMN_UUID, Vc.getUuid());
                values.put(singleMember.COLUMN_FORMDATE, Vc.getFormdate());
                values.put(singleMember.COLUMN_AGE, Vc.getAge());
                values.put(singleMember.COLUMN_CLUSTER_CODE, Vc.getClusterno());
                values.put(singleMember.COLUMN_HHNO, Vc.getHhno());
                values.put(singleMember.COLUMN_MOTHER_ID, Vc.getMotherid());
                values.put(singleMember.COLUMN_NAME, Vc.getName());
                values.put(singleMember.COLUMN_MOMTHER_NAME, Vc.getMotherName());
                values.put(singleMember.COLUMN_SERIAL_NO, Vc.getSerialno());
                values.put(singleMember.COLUMN_TYPE, Vc.getType());

                db.insert(singleMember.TABLE_NAME, null, values);
            }
        } catch (Exception e) {
        } finally {
            db.close();
        }
    }

    public void syncVillages(JSONArray pcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VillagesContract.singleVillage.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = pcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectPSU = jsonArray.getJSONObject(i);

                VillagesContract vc = new VillagesContract();
                vc.sync(jsonObjectPSU);
                Log.i(TAG, "syncVillages: " + jsonObjectPSU.toString());

                ContentValues values = new ContentValues();

                values.put(VillagesContract.singleVillage.COLUMN_TALUKA_CODE, vc.getTalukacode());
                values.put(VillagesContract.singleVillage.COLUMN_TALUKA_NAME, vc.getTalukaname());
                values.put(VillagesContract.singleVillage.COLUMN_UC_CODE, vc.getUccode());
                values.put(VillagesContract.singleVillage.COLUMN_UC_NAME, vc.getUcname());
                values.put(VillagesContract.singleVillage.COLUMN_VILLAGE_CODE, vc.getVillagecode());
                values.put(VillagesContract.singleVillage.COLUMN_VILLAGE_NAME, vc.getVillagename());

                db.insert(VillagesContract.singleVillage.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }


    public Collection<VillagesContract> getAllPSUsByTaluka(String taluka_code, String uc_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                VillagesContract.singleVillage._ID,
                VillagesContract.singleVillage.COLUMN_TALUKA_CODE,
                VillagesContract.singleVillage.COLUMN_TALUKA_NAME,
                VillagesContract.singleVillage.COLUMN_UC_CODE,
                VillagesContract.singleVillage.COLUMN_UC_NAME,
                VillagesContract.singleVillage.COLUMN_VILLAGE_CODE,
                VillagesContract.singleVillage.COLUMN_VILLAGE_NAME

        };

        String whereClause = VillagesContract.singleVillage.COLUMN_TALUKA_CODE + " =? AND " + VillagesContract.singleVillage.COLUMN_UC_CODE + " =?";

        String[] whereArgs = {taluka_code, uc_code};
        String groupBy = null;
        String having = null;

        String orderBy =
                VillagesContract.singleVillage.COLUMN_VILLAGE_NAME + " ASC";

        Collection<VillagesContract> allPC = new ArrayList<VillagesContract>();
        try {
            c = db.query(
                    VillagesContract.singleVillage.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                VillagesContract pc = new VillagesContract();
                allPC.add(pc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPC;
    }


    public Collection<LHWContract> getAllLHWsByTaluka(String district_code, String uc_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                LHWContract.lhwEntry._ID,
                LHWContract.lhwEntry.COLUMN_TALUKA_CODE,
                LHWContract.lhwEntry.COLUMN_TALUKA_NAME,
                LHWContract.lhwEntry.COLUMN_UC_CODE,
                LHWContract.lhwEntry.COLUMN_UC_NAME,
                LHWContract.lhwEntry.COLUMN_LHW_CODE,
                LHWContract.lhwEntry.COLUMN_LHW_NAME
        };

        String whereClause = LHWContract.lhwEntry.COLUMN_TALUKA_CODE + " =? AND " + LHWContract.lhwEntry.COLUMN_UC_CODE + " =?";

        String[] whereArgs = {district_code, uc_code};
        String groupBy = null;
        String having = null;

        String orderBy =
                LHWContract.lhwEntry.COLUMN_LHW_NAME + " ASC";

        Collection<LHWContract> allPC = new ArrayList<LHWContract>();
        try {
            c = db.query(
                    LHWContract.lhwEntry.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                LHWContract pc = new LHWContract();
                allPC.add(pc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPC;
    }


    public void syncTalukas(JSONArray Talukaslist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TalukasContract.singleTalukas.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = Talukaslist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectCC = jsonArray.getJSONObject(i);

                TalukasContract Vc = new TalukasContract();
                Vc.Sync(jsonObjectCC);

                ContentValues values = new ContentValues();

                values.put(TalukasContract.singleTalukas.COLUMN_TALUKA_CODE, Vc.getTalukacode());
                values.put(TalukasContract.singleTalukas.COLUMN_TALUKA, Vc.getTaluka());

                db.insert(TalukasContract.singleTalukas.TABLE_NAME, null, values);
            }
        } catch (Exception e) {
        } finally {
            db.close();
        }
    }

    public void syncUCs(JSONArray UCslist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UCsContract.singleUCs.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = UCslist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectCC = jsonArray.getJSONObject(i);

                UCsContract Vc = new UCsContract();
                Vc.Sync(jsonObjectCC);

                ContentValues values = new ContentValues();

                values.put(UCsContract.singleUCs.COLUMN_UCCODE, Vc.getUccode());
                values.put(UCsContract.singleUCs.COLUMN_UCS, Vc.getUcs());
                values.put(UCsContract.singleUCs.COLUMN_TALUKA_CODE, Vc.getTaluka_code());

                db.insert(UCsContract.singleUCs.TABLE_NAME, null, values);
            }
        } catch (Exception e) {
        } finally {
            db.close();
        }
    }

    public void syncAreas(JSONArray Areaslist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(singleAreas.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = Areaslist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectCC = jsonArray.getJSONObject(i);

                AreasContract Vc = new AreasContract();
                Vc.Sync(jsonObjectCC);

                ContentValues values = new ContentValues();

                values.put(singleAreas.COLUMN_AREACODE, Vc.getAreacode());
                values.put(singleAreas.COLUMN_AREA, Vc.getArea());
                values.put(singleAreas.COLUMN_UC_CODE, Vc.getUc_code());

                db.insert(singleAreas.TABLE_NAME, null, values);
            }
        } catch (Exception e) {
        } finally {
            db.close();
        }
    }

    /*public Collection<VillagesContract> getVillage(String areacode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
//                singleVillages.COLUMN_ID,
                singleVillage.COLUMN_VILLAGE_NAME,
                singleVillage.COLUMN_AREA_CODE,
//                singleVillages.COLUMN_TALUKA,
                singleVillage.COLUMN_VILLAGE_CODE,
        };

        String whereClause = singleVillages.COLUMN_AREA_CODE + " =?";
        String[] whereArgs = {areacode};
        String groupBy = null;
        String having = null;

        String orderBy =
                singleVillages.COLUMN_VILLAGE_NAME + " ASC";

        Collection<VillagesContract> allDC = new ArrayList<>();
        try {
            c = db.query(
                    singleVillages.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                VillagesContract dc = new VillagesContract();
                allDC.add(dc.HydrateVillages(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allDC;
    }*/

    public Collection<UCsContract> getAllUCsbyTaluka(String taluka_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                UCsContract.singleUCs._ID,
                UCsContract.singleUCs.COLUMN_UCCODE,
                UCsContract.singleUCs.COLUMN_UCS,
                UCsContract.singleUCs.COLUMN_TALUKA_CODE
        };

        String whereClause = UCsContract.singleUCs.COLUMN_TALUKA_CODE + " = ?";
        String[] whereArgs = {taluka_code};
        String groupBy = null;
        String having = null;

        String orderBy =
                UCsContract.singleUCs.COLUMN_UCS + " ASC";

        Collection<UCsContract> allPC = new ArrayList<UCsContract>();
        try {
            c = db.query(
                    UCsContract.singleUCs.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                UCsContract pc = new UCsContract();
                allPC.add(pc.HydrateUCs(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPC;
    }

    public Collection<TalukasContract> getAllTalukas() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                TalukasContract.singleTalukas.COLUMN_TALUKA_CODE,
                TalukasContract.singleTalukas.COLUMN_TALUKA
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                TalukasContract.singleTalukas.COLUMN_TALUKA + " ASC";

        Collection<TalukasContract> allDC = new ArrayList<>();
        try {
            c = db.query(
                    TalukasContract.singleTalukas.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                TalukasContract dc = new TalukasContract();
                allDC.add(dc.HydrateTalukas(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allDC;
    }

    public Collection<LHWContract> getAllLhw() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                LHWContract.lhwEntry._ID,
                LHWContract.lhwEntry.COLUMN_LHW_CODE,
                LHWContract.lhwEntry.COLUMN_LHW_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                LHWContract.lhwEntry._ID + " ASC";

        Collection<LHWContract> allDC = new ArrayList<>();
        try {
            c = db.query(
                    LHWContract.lhwEntry.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                LHWContract dc = new LHWContract();
                allDC.add(dc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allDC;
    }

    public Collection<ChildrenContract> getChildBy(String lhw_code, String caseid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ChildrenContract.singleChild._ID,
                ChildrenContract.singleChild.COLUMN_LHW_CODE,
                ChildrenContract.singleChild.COLUMN_CASEID,
                ChildrenContract.singleChild.COLUMN_CHILD_NAME,
                ChildrenContract.singleChild.COLUMN_F_NAME,
                ChildrenContract.singleChild.COLUMN_REF_DATE,
                ChildrenContract.singleChild.COLUMN_LUID
        };

        String whereClause = ChildrenContract.singleChild.COLUMN_LHW_CODE + " = ? AND " + ChildrenContract.singleChild.COLUMN_CASEID + " = ?";
        String[] whereArgs = {lhw_code, caseid};
        String groupBy = null;
        String having = null;

        String orderBy =
                ChildrenContract.singleChild.COLUMN_LHW_CODE + " ASC";

        Collection<ChildrenContract> allCC = new ArrayList<>();
        try {
            c = db.query(
                    ChildrenContract.singleChild.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                ChildrenContract cc = new ChildrenContract();
                allCC.add(cc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allCC;
    }

    public ChildList getChildlistBy(String study_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ChildList.singleChildList._ID,
                ChildList.singleChildList.COLUMN_DSSID,
                ChildList.singleChildList.COLUMN_MOTHER_NAME,
                ChildList.singleChildList.COLUMN_FATHER_NAME,
                ChildList.singleChildList.COLUMN_HHHEAD,
                ChildList.singleChildList.COLUMN_STUDY_ID,
                ChildList.singleChildList.COLUMN_DOB,
        };

        String whereClause = ChildList.singleChildList.COLUMN_STUDY_ID + " = ? ";
        String[] whereArgs = {study_id};
        String groupBy = null;
        String having = null;

        String orderBy =
                ChildList.singleChildList.COLUMN_STUDY_ID + " ASC";

        ChildList allCL = null;
        try {
            c = db.query(
                    ChildList.singleChildList.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allCL = new ChildList().hydrate(c);
                //allCL.add(cl.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allCL;
    }


    public ChildrenContract getChildById(String lhw_code, String caseid) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ChildrenContract.singleChild._ID,
                ChildrenContract.singleChild.COLUMN_LHW_CODE,
                ChildrenContract.singleChild.COLUMN_CASEID,
                ChildrenContract.singleChild.COLUMN_CHILD_NAME,
                ChildrenContract.singleChild.COLUMN_F_NAME,
                ChildrenContract.singleChild.COLUMN_REF_DATE,
                ChildrenContract.singleChild.COLUMN_LUID,
        };

        String whereClause = ChildrenContract.singleChild.COLUMN_LHW_CODE + " =? AND " + ChildrenContract.singleChild.COLUMN_CASEID + " =?";
        String[] whereArgs = {lhw_code, caseid};
        String groupBy = null;
        String having = null;

        String orderBy = ChildrenContract.singleChild.COLUMN_LHW_CODE + " ASC";

        ChildrenContract allEB = null;

        try {
            c = db.query(
                    ChildrenContract.singleChild.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allEB = new ChildrenContract().hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allEB;
    }


    public ChildrenContract getChildById(String sType, String codeLhw, String refId) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable.COLUMN_FORMTYPE,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_DSSID,
                FormsTable.COLUMN_NEXT_VISIT,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_SA,
                FormsTable.COLUMN_SB,
        };

        String whereClause = FormsTable.COLUMN_FORMTYPE + " =? AND " + FormsTable.COLUMN_DSSID + " =? AND " + FormsTable.COLUMN_NEXT_VISIT + "=? AND " + FormsTable.COLUMN_ISTATUS + "=?";
        String[] whereArgs = {sType, codeLhw, refId, "1"};
        String groupBy = null;
        String having = null;

        String orderBy = FormsTable.COLUMN_FORMTYPE + " ASC";

        ChildrenContract allEB = null;

        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allEB = new ChildrenContract().hydrateForm(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allEB;
    }


    public Collection<UCsContract> getAllUCs(String talukaCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                UCsContract.singleUCs.COLUMN_UCCODE,
                UCsContract.singleUCs.COLUMN_UCS,
                UCsContract.singleUCs.COLUMN_TALUKA_CODE
        };

        String whereClause = UCsContract.singleUCs.COLUMN_TALUKA_CODE + "=?";
        String[] whereArgs = new String[]{talukaCode};
        String groupBy = null;
        String having = null;

        String orderBy =
                UCsContract.singleUCs.COLUMN_UCS + " ASC";

        Collection<UCsContract> allDC = new ArrayList<>();
        try {
            c = db.query(
                    UCsContract.singleUCs.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                UCsContract dc = new UCsContract();
                allDC.add(dc.HydrateUCs(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allDC;
    }

    public List<ChildList> getChildList(String areaCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ChildList.singleChildList.COLUMN_DSSID,
                ChildList.singleChildList.COLUMN_GENDER,
                ChildList.singleChildList.COLUMN_STUDY_ID,
                ChildList.singleChildList.COLUMN_DOB,
                ChildList.singleChildList.COLUMN_FATHER_NAME,
                ChildList.singleChildList.COLUMN_MOTHER_NAME,
                ChildList.singleChildList.COLUMN_AREACODE,
                ChildList.singleChildList.COLUMN_HHHEAD,

        };


        String whereClause = ChildList.singleChildList.COLUMN_AREACODE + " = ? ";
        String[] whereArgs = new String[]{areaCode};
        String groupBy = null;
        String having = null;
        String orderBy = null;

        List<ChildList> allDC = new ArrayList<>();
        try {
            c = db.query(
                    ChildList.singleChildList.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                ChildList dc = new ChildList();
                allDC.add(dc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allDC;
    }

    public List<ChildList> getList(String areaCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ChildList.singleChildList.COLUMN_DSSID,
                ChildList.singleChildList.COLUMN_GENDER,
                ChildList.singleChildList.COLUMN_STUDY_ID,
                ChildList.singleChildList.COLUMN_DOB,
                ChildList.singleChildList.COLUMN_FATHER_NAME,
                ChildList.singleChildList.COLUMN_MOTHER_NAME,
                ChildList.singleChildList.COLUMN_AREACODE,
                ChildList.singleChildList.COLUMN_HHHEAD,

        };


        String whereClause = ChildList.singleChildList.COLUMN_AREACODE + " = ? ";
        String[] whereArgs = new String[]{areaCode};
        String groupBy = null;
        String having = null;
        String orderBy = null;

        List<ChildList> allDC = new ArrayList<>();
        try {
            c = db.query(
                    ChildList.singleChildList.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                ChildList dc = new ChildList();
                allDC.add(dc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allDC;
    }

    public Collection<AreasContract> getAllAreas(String UCCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singleAreas.COLUMN_AREACODE,
                singleAreas.COLUMN_AREA,
                singleAreas.COLUMN_UC_CODE
        };

        String whereClause = singleAreas.COLUMN_UC_CODE + "=?";
        String[] whereArgs = new String[]{UCCode};
        String groupBy = null;
        String having = null;

        String orderBy =
                singleAreas.COLUMN_AREA + " ASC";

        Collection<AreasContract> allAC = new ArrayList<>();
        try {
            c = db.query(
                    singleAreas.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                AreasContract dc = new AreasContract();
                allAC.add(dc.HydrateUCs(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allAC;
    }

    public void syncVersionApp(JSONArray Versionlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VersionAppContract.VersionAppTable.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = Versionlist;
            JSONObject jsonObjectCC = jsonArray.getJSONObject(0);

            VersionAppContract Vc = new VersionAppContract();
            Vc.Sync(jsonObjectCC);

            ContentValues values = new ContentValues();

            values.put(VersionAppContract.VersionAppTable.COLUMN_PATH_NAME, Vc.getPathname());
            values.put(VersionAppContract.VersionAppTable.COLUMN_VERSION_CODE, Vc.getVersioncode());
            values.put(VersionAppContract.VersionAppTable.COLUMN_VERSION_NAME, Vc.getVersionname());

            db.insert(VersionAppContract.VersionAppTable.TABLE_NAME, null, values);
        } catch (Exception e) {
        } finally {
            db.close();
        }
    }

    public VersionAppContract getVersionApp() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                VersionAppContract.VersionAppTable._ID,
                VersionAppContract.VersionAppTable.COLUMN_VERSION_CODE,
                VersionAppContract.VersionAppTable.COLUMN_VERSION_NAME,
                VersionAppContract.VersionAppTable.COLUMN_PATH_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = null;

        VersionAppContract allVC = new VersionAppContract();
        try {
            c = db.query(
                    VersionAppContract.VersionAppTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allVC.hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVC;
    }

    public void syncUser(JSONArray userlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UsersContract.singleUser.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = userlist;
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                UsersContract user = new UsersContract();
                user.Sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(UsersContract.singleUser.ROW_USERNAME, user.getUserName());
                values.put(UsersContract.singleUser.ROW_PASSWORD, user.getPassword());
                values.put(UsersContract.singleUser.FULL_NAME, user.getFULL_NAME());
//                values.put(singleUser.REGION_DSS, user.getREGION_DSS());
                db.insert(UsersContract.singleUser.TABLE_NAME, null, values);
            }


        } catch (Exception e) {
            Log.d(TAG, "syncUser(e): " + e);
        } finally {
            db.close();
        }
    }


    public boolean Login(String username, String password) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM " + UsersContract.singleUser.TABLE_NAME + " WHERE " + UsersContract.singleUser.ROW_USERNAME + "=? AND " + UsersContract.singleUser.ROW_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {
            return mCursor.getCount() > 0;
        }
        return false;
    }


    public List<FormsContract> getFormsByDSS(String dssID) {
        List<FormsContract> formList = new ArrayList<FormsContract>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + FormsTable.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                FormsContract fc = new FormsContract();
                formList.add(fc.Hydrate(c));
            } while (c.moveToNext());
        }

        // return contact list
        return formList;
    }

    public Collection<MotherLst> getMotherByUUID(String uuid) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        // COLUMNS RETURNED: child_name, child_id, mother_name, mother_id, date_of_birth, serial
        Collection<MotherLst> memList = new ArrayList<>();
        try {

//            c = db.rawQuery(SQL_SELECT_MOTHER_BY_CHILD, new String[]{"c", "('1', '2')", uuid});
            c = db.rawQuery(SQL_SELECT_MOTHER_BY_CHILD, new String[]{"c", uuid});

            while (c.moveToNext()) {

                MotherLst mc = new MotherLst();
                memList.add(mc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return memList;
    }

    public Long addForm(FormsContract fc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_PROJECT_NAME, fc.getProjectName());
        values.put(FormsTable.COLUMN_UID, fc.get_UID());
        values.put(FormsTable.COLUMN_FORMDATE, fc.getFormDate());
        values.put(FormsTable.COLUMN_DSSID, fc.getDSSID());
        values.put(FormsTable.COLUMN_NEXT_VISIT, fc.getNextVisit());
        values.put(FormsTable.COLUMN_USER, fc.getUser());
        values.put(FormsTable.COLUMN_ISTATUS, fc.getIstatus());
        values.put(FormsTable.COLUMN_ISTATUS88x, fc.getIstatus88x());
        values.put(FormsTable.COLUMN_ENDINGDATETIME, fc.getEndingdatetime());
        values.put(FormsTable.COLUMN_SA, fc.getsA());
        values.put(FormsTable.COLUMN_SB, fc.getsB());
        values.put(FormsTable.COLUMN_SC, fc.getsC());
        values.put(FormsTable.COLUMN_SD, fc.getsD());
        values.put(FormsTable.COLUMN_SE, fc.getsE());
        values.put(FormsTable.COLUMN_SF, fc.getsF());
        values.put(FormsTable.COLUMN_GPSLAT, fc.getGpsLat());
        values.put(FormsTable.COLUMN_GPSLNG, fc.getGpsLng());
        values.put(FormsTable.COLUMN_GPSDATE, fc.getGpsDT());
        values.put(FormsTable.COLUMN_GPSACC, fc.getGpsAcc());
        values.put(FormsTable.COLUMN_DEVICETAGID, fc.getDevicetagID());
        values.put(FormsTable.COLUMN_DEVICEID, fc.getDeviceID());
        values.put(FormsTable.COLUMN_APPVERSION, fc.getAppversion());
        values.put(FormsTable.COLUMN_STATUS, fc.getStatus());
        values.put(FormsTable.COLUMN_FORMTYPE, fc.getFormType());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FormsTable.TABLE_NAME,
                FormsTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

//    public Long addFamilyMembers(FamilyMembersContract fmc) {
//
//        // Gets the data repository in write mode
//        SQLiteDatabase db = this.getWritableDatabase();
//
//// Create a new map of values, where column names are the keys
//        ContentValues values = new ContentValues();
//        values.put(familyMembers.COLUMN_PROJECT_NAME, fmc.getProjectName());
//        values.put(familyMembers.COLUMN_UID, fmc.get_UID());
//        values.put(familyMembers.COLUMN_UUID, fmc.get_UUID());
//        values.put(familyMembers.COLUMN_FORMDATE, fmc.getFormDate());
//        values.put(familyMembers.COLUMN_USER, fmc.getUser());
//        values.put(familyMembers.COLUMN_SB, fmc.getsB());
//        values.put(familyMembers.COLUMN_SERIAL_NO, fmc.getSerialNo());
//        values.put(familyMembers.COLUMN_DEVICETAGID, fmc.getDevicetagID());
//        values.put(familyMembers.COLUMN_DEVICEID, fmc.getDeviceId());
//
//        // Insert the new row, returning the primary key value of the new row
//        long newRowId;
//        newRowId = db.insert(
//                familyMembers.TABLE_NAME,
//                familyMembers.COLUMN_NAME_NULLABLE,
//                values);
//        return newRowId;
//    }


    public FormsContract isDataExists(String studyId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null;

// New value for one column
        String[] columns = {
                FormsTable.COLUMN_DSSID,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_STATUS,

        };

// Which row to update, based on the ID
        String selection = FormsTable.COLUMN_DSSID + " = ? AND "
                + FormsTable.COLUMN_ISTATUS + " = ?";
        String[] selectionArgs = new String[]{studyId, "1"};

        FormsContract allFC = new FormsContract();
        try {
            c = db.query(FormsTable.TABLE_NAME, //Table to query
                    columns,                    //columns to return
                    selection,                  //columns for the WHERE clause
                    selectionArgs,              //The values for the WHERE clause
                    null,                       //group the rows
                    null,                       //filter by row groups
                    null);                   // The sort order

            while (c.moveToNext()) {
                allFC.setDSSID(c.getString(c.getColumnIndex(FormsTable.COLUMN_DSSID)));
                allFC.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                allFC.setStatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_STATUS)));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;


    }


    public String getChildStatus(String studyID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable.COLUMN_DSSID,
                FormsTable.COLUMN_ISTATUS
        };

        String selection = FormsTable.COLUMN_DSSID + " = ? ";
        String[] selectionArgs = {studyID};

        String orderBy = null;

        String status = "";
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    selection,               // The columns for the WHERE clause
                    selectionArgs,                 // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                status = c.getString(c.getColumnIndex(FormsTable.COLUMN_STATUS));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return status;
    }

    public Long addMWRA(MWRAContract mc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MWRAContract.MWRATable.COLUMN_PROJECT_NAME, mc.getProjectName());
        values.put(MWRAContract.MWRATable.COLUMN_UID, mc.getUID());
        values.put(MWRAContract.MWRATable.COLUMN_UUID, mc.get_UUID());
        values.put(MWRAContract.MWRATable.COLUMN_FORMDATE, mc.getFormDate());
        values.put(MWRAContract.MWRATable.COLUMN_USER, mc.getUser());
        values.put(MWRAContract.MWRATable.COLUMN_SD, mc.getsD());
        values.put(MWRAContract.MWRATable.COLUMN_DEVICEID, mc.getDeviceId());
        values.put(MWRAContract.MWRATable.COLUMN_DEVICETAGID, mc.getDevicetagID());

        // SYNCED INFORMATION IS NEVER INSERTED WITH NEW RECORD.
     /*   values.put(MWRATable.COLUMN_SYNCED, mc.getSynced());
        values.put(MWRATable.COLUMN_SYNCED_DATE, mc.getSynced_date());*/


        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                MWRAContract.MWRATable.TABLE_NAME,
                MWRAContract.MWRATable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

//    public Long addChild(SectionIIMContract ims) {
//
//        // Gets the data repository in write mode
//        SQLiteDatabase db = this.getWritableDatabase();
//
//// Create a new map of values, where column names are the keys
//        ContentValues values = new ContentValues();
//
//        values.put(singleIm.COLUMN_PROJECT_NAME, ims.getProjectName());
//        values.put(singleIm.COLUMN_UUID, ims.get_UUID());
//        values.put(singleIm.COLUMN_UID, ims.getUID());
//        values.put(singleIm.COLUMN_SI, ims.getsI());
//        values.put(singleIm.COLUMN_FORMDATE, ims.getFormDate());
//        values.put(singleIm.COLUMN_USER, ims.getUser());
////        values.put(singleIm.COLUMN_MM, ims.getMm());
//        //values.put(singleIm.COLUMN_CHILDID, ims.getChildID());
////        values.put(singleIm.COLUMN_DSSID, ims.getDssID());
//        values.put(singleIm.COLUMN_DEVICEID, ims.getDeviceId());
//        values.put(singleIm.COLUMN_DEVICETAGID, ims.getDevicetagID());
//
//
//        // Insert the new row, returning the primary key value of the new row
//        long newRowId;
//        newRowId = db.insert(
//                singleIm.TABLE_NAME,
//                singleIm.COLUMN_NAME_NULLABLE,
//                values);
//        return newRowId;
//    }


//    public Long addDeceasedMother(DeceasedMotherContract dc) {
//
//        // Gets the data repository in write mode
//        SQLiteDatabase db = this.getWritableDatabase();
//
//// Create a new map of values, where column names are the keys
//        ContentValues values = new ContentValues();
//        values.put(DeceasedMother.COLUMN_PROJECT_NAME, dc.getProjectName());
//        values.put(DeceasedMother.COLUMN_UID, dc.get_UID());
//        values.put(DeceasedMother.COLUMN_UUID, dc.get_UUID());
//        values.put(DeceasedMother.COLUMN_FORMDATE, dc.getFormDate());
//        values.put(DeceasedMother.COLUMN_DEVICEID, dc.getDeviceId());
//        values.put(DeceasedMother.COLUMN_DEVICETAGID, dc.getDevicetagID());
//        values.put(DeceasedMother.COLUMN_USER, dc.getUser());
//        values.put(DeceasedMother.COLUMN_SE, dc.getsE());
//
//        long newRowId;
//        newRowId = db.insert(
//                DeceasedMother.TABLE_NAME,
//                DeceasedMother.COLUMN_NAME_NULLABLE,
//                values);
//        return newRowId;
//    }
//

//    public Long addDeceasedChild(DeceasedChildContract dc) {
//
//        // Gets the data repository in write mode
//        SQLiteDatabase db = this.getWritableDatabase();
//
//// Create a new map of values, where column names are the keys
//        ContentValues values = new ContentValues();
//        values.put(DeceasedChild.COLUMN_PROJECT_NAME, dc.getProjectName());
//        values.put(DeceasedChild.COLUMN_UID, dc.get_UID());
//        values.put(DeceasedChild.COLUMN_UUID, dc.get_UUID());
//        values.put(DeceasedChild.COLUMN_FORMDATE, dc.getFormDate());
//        values.put(DeceasedChild.COLUMN_DEVICEID, dc.getDeviceId());
//        values.put(DeceasedChild.COLUMN_DEVICETAGID, dc.getDevicetagID());
//        values.put(DeceasedChild.COLUMN_USER, dc.getUser());
//        values.put(DeceasedChild.COLUMN_SF, dc.getsF());
//
//        long newRowId;
//        newRowId = db.insert(
//                DeceasedChild.TABLE_NAME,
//                DeceasedChild.COLUMN_NAME_NULLABLE,
//                values);
//        return newRowId;
//    }


    public void updateSyncedForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SYNCED, true);
        values.put(FormsTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormsTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateMWRAs(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MWRAContract.MWRATable.COLUMN_SYNCED, true);
        values.put(MWRAContract.MWRATable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = MWRAContract.MWRATable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                MWRAContract.MWRATable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

//    public void updateFamilyMember(String id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//// New value for one column
//        ContentValues values = new ContentValues();
//        values.put(familyMembers.COLUMN_SYNCED, true);
//        values.put(familyMembers.COLUMN_SYNCED_DATE, new Date().toString());
//
//// Which row to update, based on the title
//        String where = familyMembers.COLUMN_ID + " = ?";
//        String[] whereArgs = {id};
//
//        int count = db.update(
//                familyMembers.TABLE_NAME,
//                values,
//                where,
//                whereArgs);
//    }

//    public void updateDeceasedMother(String id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//// New value for one column
//        ContentValues values = new ContentValues();
//        values.put(DeceasedMother.COLUMN_SYNCED, true);
//        values.put(DeceasedMother.COLUMN_SYNCED_DATE, new Date().toString());
//
//// Which row to update, based on the title
//        String where = DeceasedMother.COLUMN_ID + " = ?";
//        String[] whereArgs = {id};
//
//        int count = db.update(
//                DeceasedMother.TABLE_NAME,
//                values,
//                where,
//                whereArgs);
//    }

//    public void updateDeceasedChild(String id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//// New value for one column
//        ContentValues values = new ContentValues();
//        values.put(DeceasedChild.COLUMN_SYNCED, true);
//        values.put(DeceasedChild.COLUMN_SYNCED_DATE, new Date().toString());
//
//// Which row to update, based on the title
//        String where = DeceasedChild.COLUMN_ID + " = ?";
//        String[] whereArgs = {id};
//
//        int count = db.update(
//                DeceasedChild.TABLE_NAME,
//                values,
//                where,
//                whereArgs);
//    }


//    public void updateIM(String id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//// New value for one column
//        ContentValues values = new ContentValues();
//        values.put(singleIm.COLUMN_SYNCED, true);
//        values.put(singleIm.COLUMN_SYNCED_DATE, new Date().toString());
//
//// Which row to update, based on the title
//        String where = singleIm.COLUMN_ID + " = ?";
//        String[] whereArgs = {id};
//
//        int count = db.update(
//                singleIm.TABLE_NAME,
//                values,
//                where,
//                whereArgs);
//    }

//    public int updateDeceasedMotherID() {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//// New value for one column
//        ContentValues values = new ContentValues();
//        values.put(DeceasedMother.COLUMN_UID, MainApp.dcM.get_UID());
//
//// Which row to update, based on the ID
//        String selection = DeceasedMother._ID + " = ?";
//        String[] selectionArgs = {String.valueOf(MainApp.dcM.get_ID())};
//
//        int count = db.update(DeceasedMother.TABLE_NAME,
//                values,
//                selection,
//                selectionArgs);
//        return count;
//    }


//    public int updateDeceasedChildID() {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//// New value for one column
//        ContentValues values = new ContentValues();
//        values.put(DeceasedChild.COLUMN_UID, MainApp.dcC.get_UID());
//
//// Which row to update, based on the ID
//        String selection = DeceasedChild._ID + " = ?";
//        String[] selectionArgs = {String.valueOf(MainApp.dcC.get_ID())};
//
//        int count = db.update(DeceasedChild.TABLE_NAME,
//                values,
//                selection,
//                selectionArgs);
//        return count;
//    }


    public int updateFormID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_UID, MainApp.fc.get_UID());

// Which row to update, based on the ID
        String selection = FormsTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }


//    public int updateFamilyMemberID() {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//// New value for one column
//        ContentValues values = new ContentValues();
//        values.put(familyMembers.COLUMN_UID, MainApp.fmc.get_UID());
//
//// Which row to update, based on the ID
//        String selection = familyMembers.COLUMN_ID + " = ?";
//        String[] selectionArgs = {String.valueOf(MainApp.fmc.get_ID())};
//
//        int count = db.update(familyMembers.TABLE_NAME,
//                values,
//                selection,
//                selectionArgs);
//        return count;
//    }

    public int updateMWRAID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MWRAContract.MWRATable.COLUMN_UID, MainApp.mw.getUID());

// Which row to update, based on the ID
        String selection = MWRAContract.MWRATable.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.mw.get_ID())};

        int count = db.update(MWRAContract.MWRATable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

//    public int updateChildID() {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//// New value for one column
//        ContentValues values = new ContentValues();
//        values.put(singleIm.COLUMN_UID, MainApp.ims.getUID());
//
//// Which row to update, based on the ID
//        String selection = singleIm.COLUMN_ID + " = ?";
//        String[] selectionArgs = {String.valueOf(MainApp.ims.get_ID())};
//
//        int count = db.update(singleIm.TABLE_NAME,
//                values,
//                selection,
//                selectionArgs);
//        return count;
//    }

    public Collection<FormsContract> getAllForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_USER,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SA,
                FormsTable.COLUMN_SB,
                FormsTable.COLUMN_SC,
                FormsTable.COLUMN_SD,
                FormsTable.COLUMN_SE,
                FormsTable.COLUMN_SF,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,
                FormsTable.COLUMN_STATUS,
                FormsTable.COLUMN_FORMTYPE,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<FormsContract>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public Collection<FormsContract> checkFormExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_USER,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SA,
                FormsTable.COLUMN_SB,
                FormsTable.COLUMN_SC,
                FormsTable.COLUMN_SD,
                FormsTable.COLUMN_SE,
                FormsTable.COLUMN_SF,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,
                FormsTable.COLUMN_STATUS,
                FormsTable.COLUMN_FORMTYPE,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<FormsContract>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }


    public Collection<MWRAContract> getUnsyncedMWRA() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                MWRAContract.MWRATable.COLUMN_ID,
                MWRAContract.MWRATable.COLUMN_UID,
                MWRAContract.MWRATable.COLUMN_UUID,
                MWRAContract.MWRATable.COLUMN_FORMDATE,
                MWRAContract.MWRATable.COLUMN_USER,
                MWRAContract.MWRATable.COLUMN_SD,
                MWRAContract.MWRATable.COLUMN_DEVICEID,
                MWRAContract.MWRATable.COLUMN_DEVICETAGID
        };
        String whereClause = MWRAContract.MWRATable.COLUMN_SYNCED + " is null";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                MWRAContract.MWRATable.COLUMN_ID + " ASC";

        Collection<MWRAContract> allMC = new ArrayList<MWRAContract>();
        try {
            c = db.query(
                    MWRAContract.MWRATable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                MWRAContract mc = new MWRAContract();
                allMC.add(mc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allMC;
    }


//    public Collection<SectionIIMContract> getUnsyncedIM() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = null;
//        String[] columns = {
//                singleIm.COLUMN_ID,
//                singleIm.COLUMN_UUID,
//                singleIm.COLUMN_UID,
//                singleIm.COLUMN_SI,
//                singleIm.COLUMN_FORMDATE,
//                singleIm.COLUMN_USER,
////                singleIm.COLUMN_CHILDID,
//                /*singleIm.COLUMN_MM,
//                singleIm.COLUMN_DSSID,*/
//                singleIm.COLUMN_DEVICETAGID,
//                singleIm.COLUMN_DEVICEID,
//                singleIm.COLUMN_CHILD_STATUS
//
//        };
//        String whereClause = singleIm.COLUMN_SYNCED + " is null";
//        String[] whereArgs = null;
//        String groupBy = null;
//        String having = null;
//
//        String orderBy =
//                singleIm.COLUMN_ID + " ASC";
//
//        Collection<SectionIIMContract> allIM = new ArrayList<SectionIIMContract>();
//        try {
//            c = db.query(
//                    singleIm.TABLE_NAME,  // The table to query
//                    columns,                   // The columns to return
//                    whereClause,               // The columns for the WHERE clause
//                    whereArgs,                 // The values for the WHERE clause
//                    groupBy,                   // don't group the rows
//                    having,                    // don't filter by row groups
//                    orderBy                    // The sort order
//            );
//            while (c.moveToNext()) {
//                SectionIIMContract kIm = new SectionIIMContract();
//                allIM.add(kIm.Hydrate(c));
//            }
//        } finally {
//            if (c != null) {
//                c.close();
//            }
//            if (db != null) {
//                db.close();
//            }
//        }
//        return allIM;
//    }

//    public Collection<FamilyMembersContract> getUnsyncedFamilyMembers() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = null;
//        String[] columns = {
//                familyMembers.COLUMN_ID,
//                familyMembers.COLUMN_UID,
//                familyMembers.COLUMN_UUID,
//                familyMembers.COLUMN_FORMDATE,
//                familyMembers.COLUMN_DEVICEID,
//                familyMembers.COLUMN_USER,
//                familyMembers.COLUMN_SB,
//                familyMembers.COLUMN_SERIAL_NO,
//                familyMembers.COLUMN_DEVICETAGID
//        };
//        String whereClause = familyMembers.COLUMN_SYNCED + " is null";
//        String[] whereArgs = null;
//        String groupBy = null;
//        String having = null;
//
//        String orderBy =
//                familyMembers.COLUMN_ID + " ASC";
//
//        Collection<FamilyMembersContract> allCC = new ArrayList<FamilyMembersContract>();
//        try {
//            c = db.query(
//                    familyMembers.TABLE_NAME,  // The table to query
//                    columns,                   // The columns to return
//                    whereClause,               // The columns for the WHERE clause
//                    whereArgs,                 // The values for the WHERE clause
//                    groupBy,                   // don't group the rows
//                    having,                    // don't filter by row groups
//                    orderBy                    // The sort order
//            );
//            while (c.moveToNext()) {
//                FamilyMembersContract cc = new FamilyMembersContract();
//                allCC.add(cc.Hydrate(c));
//            }
//        } finally {
//            if (c != null) {
//                c.close();
//            }
//            if (db != null) {
//                db.close();
//            }
//        }
//        return allCC;
//    }

//    public Collection<DeceasedMotherContract> getUnsyncedDeceasedMother() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = null;
//        String[] columns = {
//                DeceasedMother.COLUMN_ID,
//                DeceasedMother.COLUMN_UID,
//                DeceasedMother.COLUMN_UUID,
//                DeceasedMother.COLUMN_FORMDATE,
//                DeceasedMother.COLUMN_DEVICEID,
//                DeceasedMother.COLUMN_USER,
//                DeceasedMother.COLUMN_SE,
//                DeceasedMother.COLUMN_DEVICETAGID
//
//        };
//        String whereClause = DeceasedMother.COLUMN_SYNCED + " is null";
//        //String whereClause = null;
//        String[] whereArgs = null;
//        String groupBy = null;
//        String having = null;
//
//        String orderBy =
//                DeceasedMother.COLUMN_ID + " ASC";
//
//        Collection<DeceasedMotherContract> allDC = new ArrayList<DeceasedMotherContract>();
//        try {
//            c = db.query(
//                    DeceasedMother.TABLE_NAME,  // The table to query
//                    columns,                   // The columns to return
//                    whereClause,               // The columns for the WHERE clause
//                    whereArgs,                 // The values for the WHERE clause
//                    groupBy,                   // don't group the rows
//                    having,                    // don't filter by row groups
//                    orderBy                    // The sort order
//            );
//            while (c.moveToNext()) {
//                DeceasedMotherContract dc = new DeceasedMotherContract();
//                allDC.add(dc.Hydrate(c));
//            }
//        } finally {
//            if (c != null) {
//                c.close();
//            }
//            if (db != null) {
//                db.close();
//            }
//        }
//        return allDC;
//    }


//    public Collection<DeceasedChildContract> getUnsyncedDeceasedChild() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = null;
//        String[] columns = {
//                DeceasedChild.COLUMN_ID,
//                DeceasedChild.COLUMN_UID,
//                DeceasedChild.COLUMN_UUID,
//                DeceasedChild.COLUMN_FORMDATE,
//                DeceasedChild.COLUMN_DEVICEID,
//                DeceasedChild.COLUMN_USER,
//                DeceasedChild.COLUMN_SF,
//                DeceasedChild.COLUMN_DEVICETAGID
//        };
//        String whereClause = DeceasedChild.COLUMN_SYNCED + " is null";
//        //String whereClause = null;
//        String[] whereArgs = null;
//        String groupBy = null;
//        String having = null;
//
//        String orderBy =
//                DeceasedChild.COLUMN_ID + " ASC";
//
//        Collection<DeceasedChildContract> allDC = new ArrayList<DeceasedChildContract>();
//        try {
//            c = db.query(
//                    DeceasedChild.TABLE_NAME,  // The table to query
//                    columns,                   // The columns to return
//                    whereClause,               // The columns for the WHERE clause
//                    whereArgs,                 // The values for the WHERE clause
//                    groupBy,                   // don't group the rows
//                    having,                    // don't filter by row groups
//                    orderBy                    // The sort order
//            );
//            while (c.moveToNext()) {
//                DeceasedChildContract dc = new DeceasedChildContract();
//                allDC.add(dc.Hydrate(c));
//            }
//        } finally {
//            if (c != null) {
//                c.close();
//            }
//            if (db != null) {
//                db.close();
//            }
//        }
//        return allDC;
//    }


    public Collection<FormsContract> getUnsyncedForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_USER,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_ISTATUS88x,
                FormsTable.COLUMN_DSSID,
                FormsTable.COLUMN_NEXT_VISIT,
                FormsTable.COLUMN_ENDINGDATETIME,
                FormsTable.COLUMN_SA,
                FormsTable.COLUMN_SB,
                FormsTable.COLUMN_SC,
                FormsTable.COLUMN_SD,
                FormsTable.COLUMN_SE,
                FormsTable.COLUMN_SF,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,
                FormsTable.COLUMN_STATUS,
                FormsTable.COLUMN_FORMTYPE
        };


        String whereClause = FormsTable.COLUMN_SYNCED + " is null";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<FormsContract>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public Collection<FormsContract> getTodayForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_DSSID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SYNCED,

        };
        String whereClause = FormsTable.COLUMN_FORMDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                fc.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
                fc.setDSSID(c.getString(c.getColumnIndex(FormsTable.COLUMN_DSSID)));
                fc.setFormDate(c.getString(c.getColumnIndex(FormsTable.COLUMN_FORMDATE)));
                fc.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                fc.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allFC.add(fc);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    // ANDROID DATABASE MANAGER
    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }


    public int updateSA() {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SA, MainApp.fc.getsA());

        String selection = FormsTable.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateSB() {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SB, MainApp.fc.getsB());

        String selection = FormsTable.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateSC() {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SC, MainApp.fc.getsC());

        String selection = FormsTable.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateSD() {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SD, MainApp.fc.getsD());

        String selection = FormsTable.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateSE() {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SE, MainApp.fc.getsE());

        String selection = FormsTable.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateSF() {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SF, MainApp.fc.getsF());

        String selection = FormsTable.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }


    public int updateEnding() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_ISTATUS, MainApp.fc.getIstatus());
        values.put(FormsTable.COLUMN_ISTATUS88x, MainApp.fc.getIstatus88x());
        values.put(FormsTable.COLUMN_NEXT_VISIT, MainApp.fc.getNextVisit());
        values.put(FormsTable.COLUMN_STATUS, MainApp.fc.getStatus());
        values.put(FormsTable.COLUMN_ENDINGDATETIME, MainApp.fc.getEndingdatetime());


// Which row to update, based on the ID
        String selection = FormsTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }


    public void syncLHWs(JSONArray dcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LHWContract.lhwEntry.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = dcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectDistrict = jsonArray.getJSONObject(i);

                LHWContract dc = new LHWContract();
                dc.sync(jsonObjectDistrict);

                ContentValues values = new ContentValues();

                values.put(LHWContract.lhwEntry.COLUMN_TALUKA_CODE, dc.getTalukacode());
                values.put(LHWContract.lhwEntry.COLUMN_TALUKA_NAME, dc.getTalukaname());
                values.put(LHWContract.lhwEntry.COLUMN_UC_CODE, dc.getUccode());
                values.put(LHWContract.lhwEntry.COLUMN_UC_NAME, dc.getUcname());
                values.put(LHWContract.lhwEntry.COLUMN_LHW_CODE, dc.getLhwcode());
                values.put(LHWContract.lhwEntry.COLUMN_LHW_NAME, dc.getLhwname());

                db.insert(LHWContract.lhwEntry.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }


}
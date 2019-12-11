package edu.aku.hassannaqvi.rsvstudy.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 10/31/2016.
 */

public class VillagesContract {

    private String talukacode;
    private String talukaname;
    private String uccode;
    private String ucname;
    private String villagecode;
    private String villagename;


    public VillagesContract() {
    }


    public String getTalukacode() {
        return talukacode;
    }

    public String getTalukaname() {
        return talukaname;
    }

    public String getUccode() {
        return uccode;
    }

    public String getUcname() {
        return ucname;
    }

    public String getVillagecode() {
        return villagecode;
    }

    public String getVillagename() {
        return villagename;
    }

    public VillagesContract sync(JSONObject jsonObject) throws JSONException {
        this.talukacode = jsonObject.getString(singleVillage.COLUMN_TALUKA_CODE);
        this.talukaname = jsonObject.getString(singleVillage.COLUMN_TALUKA_NAME);
        this.uccode = jsonObject.getString(singleVillage.COLUMN_UC_CODE);
        this.ucname = jsonObject.getString(singleVillage.COLUMN_UC_NAME);
        this.villagecode = jsonObject.getString(singleVillage.COLUMN_VILLAGE_CODE);
        this.villagename = jsonObject.getString(singleVillage.COLUMN_VILLAGE_NAME);

        return this;
    }

    public VillagesContract hydrate(Cursor cursor) {
        this.talukacode = cursor.getString(cursor.getColumnIndex(singleVillage.COLUMN_TALUKA_CODE));
        this.talukaname = cursor.getString(cursor.getColumnIndex(singleVillage.COLUMN_TALUKA_NAME));
        this.uccode = cursor.getString(cursor.getColumnIndex(singleVillage.COLUMN_UC_CODE));
        this.ucname = cursor.getString(cursor.getColumnIndex(singleVillage.COLUMN_UC_NAME));
        this.villagecode = cursor.getString(cursor.getColumnIndex(singleVillage.COLUMN_VILLAGE_CODE));
        this.villagename = cursor.getString(cursor.getColumnIndex(singleVillage.COLUMN_VILLAGE_NAME));

        return this;
    }


    public static abstract class singleVillage implements BaseColumns {

        public static final String TABLE_NAME = "villages";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String _ID = "_ID";
        public static final String COLUMN_TALUKA_CODE = "taluka_code";
        public static final String COLUMN_TALUKA_NAME = "taluka_name";
        public static final String COLUMN_UC_CODE = "uc_code";
        public static final String COLUMN_UC_NAME = "uc_name";
        public static final String COLUMN_VILLAGE_CODE = "village_code";
        public static final String COLUMN_VILLAGE_NAME = "village_name";

        public static final String _URI = "villages.php";
    }

}
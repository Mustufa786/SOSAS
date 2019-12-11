package edu.aku.hassannaqvi.rsvstudy.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gul.sanober on 5/9/2017.
 */

public class MWRAContract {

    private final String projectName = "Pulse Oximetry";

    private String _ID = "";
    private String UID = "";
    private String _UUID = "";
    private String deviceId = "";
    private String formDate = ""; // Date
    private String user = ""; // Interviewer
    private String sD = "";
    private String devicetagID = "";
    private String synced = "";
    private String synced_date = "";


    public MWRAContract() {
    }

    public String getProjectName() {
        return projectName;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }


    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String get_UUID() {
        return _UUID;
    }

    public void set_UUID(String _UUID) {
        this._UUID = _UUID;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getsD() {
        return sD;
    }

    public void setsD(String sD) {
        this.sD = sD;
    }

    public String getDevicetagID() {

        return devicetagID;
    }

    public void setDevicetagID(String devicetagID) {
        this.devicetagID = devicetagID;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getSynced_date() {
        return synced_date;
    }

    public void setSynced_date(String synced_date) {
        this.synced_date = synced_date;
    }

    public String getFormDate() {
        return formDate;
    }

    public void setFormDate(String formDate) {
        this.formDate = formDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public MWRAContract Sync(JSONObject jsonObject) throws JSONException {

        this._ID = jsonObject.getString(MWRATable.COLUMN_ID);
        this.UID = jsonObject.getString(MWRATable.COLUMN_UID);
        this._UUID = jsonObject.getString(MWRATable.COLUMN_UUID);
        this.formDate = jsonObject.getString(MWRATable.COLUMN_FORMDATE);
        this.deviceId = jsonObject.getString(MWRATable.COLUMN_DEVICEID);
        this.user = jsonObject.getString(MWRATable.COLUMN_USER);
        this.sD = jsonObject.getString(MWRATable.COLUMN_SD);
        this.synced = jsonObject.getString(MWRATable.COLUMN_SYNCED);
        this.synced_date = jsonObject.getString(MWRATable.COLUMN_SYNCED_DATE);
        this.devicetagID = jsonObject.getString(MWRATable.COLUMN_DEVICETAGID);

        return this;

    }

    public MWRAContract Hydrate(Cursor cursor) {

        this._ID = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_ID));
        this.UID = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_UID));
        this._UUID = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_UUID));
        this.formDate = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_FORMDATE));
        this.deviceId = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_DEVICEID));
        this.user = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_USER));
        this.sD = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SD));
        this.devicetagID = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_DEVICETAGID));

        return this;

    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();

        json.put(MWRATable.COLUMN_ID, this._ID == null ? JSONObject.NULL : this._ID);
        json.put(MWRATable.COLUMN_UID, this.UID == null ? JSONObject.NULL : this.UID);
        json.put(MWRATable.COLUMN_UUID, this._UUID == null ? JSONObject.NULL : this._UUID);
        json.put(MWRATable.COLUMN_FORMDATE, this.formDate == null ? JSONObject.NULL : this.formDate);
        json.put(MWRATable.COLUMN_DEVICEID, this.deviceId == null ? JSONObject.NULL : this.deviceId);
        json.put(MWRATable.COLUMN_USER, this.user == null ? JSONObject.NULL : this.user);
        if (!this.sD.equals("")) {

            json.put(MWRATable.COLUMN_SD, this.sD.equals("") ? JSONObject.NULL : new JSONObject(this.sD));
        }
        json.put(MWRATable.COLUMN_DEVICETAGID, this.devicetagID == null ? JSONObject.NULL : this.devicetagID);

        return json;
    }

    public static abstract class MWRATable implements BaseColumns {

        public static final String TABLE_NAME = "mother";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";

        public static final String COLUMN_PROJECT_NAME = "project_name";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "uid";
        public static final String COLUMN_UUID = "uuid";
        public static final String COLUMN_FORMDATE = "formdate";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_SD = "sd";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "sync_date";
        public static final String COLUMN_DEVICETAGID = "tagid";


        public static String _URL = "mother.php";
    }
}
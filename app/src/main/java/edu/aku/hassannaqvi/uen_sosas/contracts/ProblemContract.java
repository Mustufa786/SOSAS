package edu.aku.hassannaqvi.uen_sosas.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

public class ProblemContract {

    private String uid;
    private String uuid;
    private String cuid;
    private String problemType;
    private String dA;
    private String formdate;
    private String synced;
    private String syncedDate;
    private String _id;
    private String user = ""; // Interviewer
    private String deviceID = "";
    private String devicetagID = "";


    public ProblemContract hydrate(Cursor cursor) {
        this.cuid = cursor.getString(cursor.getColumnIndex(singleProblem.COLUMN_CUID));
        this.uid = cursor.getString(cursor.getColumnIndex(singleProblem.COLUMN_UID));
        this.problemType = cursor.getString(cursor.getColumnIndex(singleProblem.COLUMN_PROBLEM_TYPE));
        this.dA = cursor.getString(cursor.getColumnIndex(singleProblem.COLUMN_DA));
        this.formdate = cursor.getString(cursor.getColumnIndex(singleProblem.COLUMN_FORMDATE));
        this.synced = cursor.getString(cursor.getColumnIndex(singleProblem.COLUMN_SYNCED));
        this.syncedDate = cursor.getString(cursor.getColumnIndex(singleProblem.COLUMN_SYNCED_DATE));
        this._id = cursor.getString(cursor.getColumnIndex(singleProblem._ID));
        this.user = cursor.getString(cursor.getColumnIndex(singleProblem.COLUMN_USER));
        this.deviceID = cursor.getString(cursor.getColumnIndex(singleProblem.COLUMN_DEVICEID));
        this.devicetagID = cursor.getString(cursor.getColumnIndex(singleProblem.COLUMN_DEVICETAGID));
        this.uuid = cursor.getString(cursor.getColumnIndex(singleProblem.COLUMN_UUID));

        return this;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDevicetagID() {
        return devicetagID;
    }

    public void setDevicetagID(String devicetagID) {
        this.devicetagID = devicetagID;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getdA() {
        return dA;
    }

    public void setdA(String dA) {
        this.dA = dA;
    }

    public String getFormdate() {
        return formdate;
    }

    public void setFormdate(String formdate) {
        this.formdate = formdate;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getSyncedDate() {
        return syncedDate;
    }

    public void setSyncedDate(String syncedDate) {
        this.syncedDate = syncedDate;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public static abstract class singleProblem implements BaseColumns {

        public static final String TABLE_NAME = "problem_table";
        public static final String _ID = "_ID";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_CUID = "cuid";
        public static final String COLUMN_PROBLEM_TYPE = "p_type";
        public static final String COLUMN_DA = "sA";
        public static final String COLUMN_FORMDATE = "formdate";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "syncedDate";
        public static final String COLUMN_USER = "username";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "tagid";

    }
}

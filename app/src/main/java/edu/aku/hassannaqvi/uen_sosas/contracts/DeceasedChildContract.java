package edu.aku.hassannaqvi.uen_sosas.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

public class DeceasedChildContract {

    private String luid;
    private String uid;


    private String uuid;
    private String serialNo;
    private String dA;
    private String formdate;
    private String synced;
    private String syncedDate;
    private String _id;
    private String motherId;
    private String user = ""; // Interviewer
    private String deviceID = "";
    private String devicetagID = "";

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

    public DeceasedChildContract hydrate(Cursor cursor) {
        this.luid = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_luid));
        this.uid = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_UID));
        this.serialNo = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_SERIAL_NO));
        this.dA = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_DA));
        this.formdate = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_FORMDATE));
        this.synced = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_SYNCED));
        this.syncedDate = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_SYNCED_DATE));
        this.motherId = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_MOTHER_ID));
        this._id = cursor.getString(cursor.getColumnIndex(singleChild._ID));
        this.user = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_USER));
        this.deviceID = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_DEVICEID));
        this.devicetagID = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_DEVICETAGID));
        this.uuid = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_UUID));

        return this;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }


    public String getLuid() {
        return luid;
    }

    public void setLuid(String luid) {
        this.luid = luid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
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


    public static abstract class singleChild implements BaseColumns {

        public static final String TABLE_NAME = "deceasedChild";
        public static final String _ID = "_ID";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_luid = "luid";
        public static final String COLUMN_SERIAL_NO = "serial_no";
        public static final String COLUMN_MOTHER_ID = "mother_id";
        public static final String COLUMN_DA = "dA";
        public static final String COLUMN_FORMDATE = "formdate";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "syncedDate";
        public static final String COLUMN_USER = "username";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "tagid";

    }
}

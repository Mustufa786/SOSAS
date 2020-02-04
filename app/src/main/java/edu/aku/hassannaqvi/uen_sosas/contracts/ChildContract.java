package edu.aku.hassannaqvi.uen_sosas.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class ChildContract {

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
    private String fmuid;
    private String muid;


    private String user = ""; // Interviewer
    private String deviceID = "";
    private String devicetagID = "";
    private String pType1 = "";
    private String pType2 = "";
    private String pType3 = "";
    private String pType4 = "";
    private String pType5 = "";
    private String pType6 = "";
    private String pType7 = "";
    private String pType8 = "";
    private String pType9 = "";
    private String pType10 = "";
    private String pType11 = "";
    private String pType12 = "";
    private String pType13 = "";
    private String pType14 = "";
    private String pType15 = "";
    private String pType16 = "";


    public String getFmuid() {
        return fmuid;
    }

    public void setFmuid(String fmuid) {
        this.fmuid = fmuid;
    }

    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }
    public String getpType1() {
        return pType1;
    }

    public void setpType1(String pType1) {
        this.pType1 = pType1;
    }

    public String getpType2() {
        return pType2;
    }

    public void setpType2(String pType2) {
        this.pType2 = pType2;
    }

    public String getpType3() {
        return pType3;
    }

    public void setpType3(String pType3) {
        this.pType3 = pType3;
    }

    public String getpType4() {
        return pType4;
    }

    public void setpType4(String pType4) {
        this.pType4 = pType4;
    }

    public String getpType5() {
        return pType5;
    }

    public void setpType5(String pType5) {
        this.pType5 = pType5;
    }

    public String getpType6() {
        return pType6;
    }

    public void setpType6(String pType6) {
        this.pType6 = pType6;
    }

    public String getpType7() {
        return pType7;
    }

    public void setpType7(String pType7) {
        this.pType7 = pType7;
    }

    public String getpType8() {
        return pType8;
    }

    public void setpType8(String pType8) {
        this.pType8 = pType8;
    }

    public String getpType9() {
        return pType9;
    }

    public void setpType9(String pType9) {
        this.pType9 = pType9;
    }

    public String getpType10() {
        return pType10;
    }

    public void setpType10(String pType10) {
        this.pType10 = pType10;
    }

    public String getpType11() {
        return pType11;
    }

    public void setpType11(String pType11) {
        this.pType11 = pType11;
    }

    public String getpType12() {
        return pType12;
    }

    public void setpType12(String pType12) {
        this.pType12 = pType12;
    }

    public String getpType13() {
        return pType13;
    }

    public void setpType13(String pType13) {
        this.pType13 = pType13;
    }

    public String getpType14() {
        return pType14;
    }

    public void setpType14(String pType14) {
        this.pType14 = pType14;
    }

    public String getpType15() {
        return pType15;
    }

    public void setpType15(String pType15) {
        this.pType15 = pType15;
    }

    public String getpType16() {
        return pType16;
    }

    public void setpType16(String pType16) {
        this.pType16 = pType16;
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

    public ChildContract hydrate(Cursor cursor) {
        this.luid = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_luid));
        this.uid = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_UID));
        this.serialNo = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_SERIAL_NO));
//        this.dA = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_DA));
        this.formdate = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_FORMDATE));
        this.synced = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_SYNCED));
        this.syncedDate = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_SYNCED_DATE));
        this.motherId = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_MOTHER_ID));
        this._id = cursor.getString(cursor.getColumnIndex(singleChild._ID));
        this.user = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_USER));
        this.deviceID = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_DEVICEID));
        this.devicetagID = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_DEVICETAGID));
        this.uuid = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_UUID));
        this.pType1 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_1));
        this.pType2 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_2));
        this.pType3 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_3));
        this.pType4 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_4));
        this.pType5 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_5));
        this.pType6 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_6));
        this.pType7 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_7));
        this.pType8 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_8));
        this.pType9 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_9));
        this.pType10 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_10));
        this.pType11 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_11));
        this.pType12 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_12));
        this.pType13 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_13));
        this.pType14 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_14));
        this.pType15 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_15));
        this.pType16 = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_PTYPE_16));
        this.muid = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_MUID));
        this.fmuid = cursor.getString(cursor.getColumnIndex(singleChild.COLUMN_FMUID));

        return this;
    }

    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();

        json.put(singleChild.COLUMN_luid, this.luid == null ? JSONObject.NULL : this.luid);
        json.put(singleChild.COLUMN_UID, this.uid == null ? JSONObject.NULL : this.uid);
        json.put(singleChild.COLUMN_SERIAL_NO, this.serialNo == null ? JSONObject.NULL : this.serialNo);

        if (!this.pType1.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_1, this.pType1.equals("") ? JSONObject.NULL : new JSONObject(this.pType1));
        }
        if (!this.pType2.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_2, this.pType2.equals("") ? JSONObject.NULL : new JSONObject(this.pType2));
        }
        if (!this.pType3.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_3, this.pType3.equals("") ? JSONObject.NULL : new JSONObject(this.pType3));
        }
        if (!this.pType4.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_4, this.pType4.equals("") ? JSONObject.NULL : new JSONObject(this.pType4));
        }
        if (!this.pType5.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_5, this.pType5.equals("") ? JSONObject.NULL : new JSONObject(this.pType5));
        }
        if (!this.pType6.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_6, this.pType6.equals("") ? JSONObject.NULL : new JSONObject(this.pType6));
        }
        if (!this.pType7.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_7, this.pType7.equals("") ? JSONObject.NULL : new JSONObject(this.pType7));
        }
        if (!this.pType8.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_8, this.pType8.equals("") ? JSONObject.NULL : new JSONObject(this.pType8));
        }
        if (!this.pType9.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_9, this.pType9.equals("") ? JSONObject.NULL : new JSONObject(this.pType9));
        }
        if (!this.pType10.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_10, this.pType10.equals("") ? JSONObject.NULL : new JSONObject(this.pType10));
        }
        if (!this.pType11.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_11, this.pType11.equals("") ? JSONObject.NULL : new JSONObject(this.pType11));
        }
        if (!this.pType12.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_12, this.pType12.equals("") ? JSONObject.NULL : new JSONObject(this.pType12));
        }
        if (!this.pType13.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_13, this.pType13.equals("") ? JSONObject.NULL : new JSONObject(this.pType13));
        }
        if (!this.pType14.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_14, this.pType14.equals("") ? JSONObject.NULL : new JSONObject(this.pType14));
        }
        if (!this.pType15.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_15, this.pType15.equals("") ? JSONObject.NULL : new JSONObject(this.pType15));
        }
        if (!this.pType16.equals("")) {
            json.put(singleChild.COLUMN_PTYPE_16, this.pType16.equals("") ? JSONObject.NULL : new JSONObject(this.pType16));
        }

        json.put(singleChild.COLUMN_FORMDATE, this.formdate == null ? JSONObject.NULL : this.formdate);
        json.put(singleChild.COLUMN_SYNCED, this.synced == null ? JSONObject.NULL : this.synced);
        json.put(singleChild.COLUMN_SYNCED_DATE, this.syncedDate == null ? JSONObject.NULL : this.syncedDate);
        json.put(singleChild.COLUMN_MOTHER_ID, this.motherId == null ? JSONObject.NULL : this.motherId);
        json.put(singleChild._ID, this._id == null ? JSONObject.NULL : this._id);
        json.put(singleChild.COLUMN_USER, this.user == null ? JSONObject.NULL : this.user);
        json.put(singleChild.COLUMN_DEVICEID, this.deviceID == null ? JSONObject.NULL : this.deviceID);
        json.put(singleChild.COLUMN_DEVICETAGID, this.devicetagID == null ? JSONObject.NULL : this.devicetagID);
        json.put(singleChild.COLUMN_UUID, this.uuid == null ? JSONObject.NULL : this.uuid);
        json.put(singleChild.COLUMN_MUID, this.muid == null ? JSONObject.NULL : this.muid);
        json.put(singleChild.COLUMN_FMUID, this.fmuid == null ? JSONObject.NULL : this.fmuid);


        return json;
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

        public static final String TABLE_NAME = "child";
        public static final String _ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_luid = "luid";
        public static final String COLUMN_SERIAL_NO = "serial_no";
        public static final String COLUMN_MOTHER_ID = "mother_id";
        public static final String COLUMN_MUID = "muid";
        public static final String COLUMN_FMUID = "fmuid";
        //        public static final String COLUMN_DA = "dA";
        public static final String COLUMN_PTYPE_1 = "p_type_1";
        public static final String COLUMN_PTYPE_2 = "p_type_2";
        public static final String COLUMN_PTYPE_3 = "p_type_3";
        public static final String COLUMN_PTYPE_4 = "p_type_4";
        public static final String COLUMN_PTYPE_5 = "p_type_5";
        public static final String COLUMN_PTYPE_6 = "p_type_6";
        public static final String COLUMN_PTYPE_7 = "p_type_7";
        public static final String COLUMN_PTYPE_8 = "p_type_8";
        public static final String COLUMN_PTYPE_9 = "p_type_9";
        public static final String COLUMN_PTYPE_10 = "p_type_10";
        public static final String COLUMN_PTYPE_11 = "p_type_11";
        public static final String COLUMN_PTYPE_12 = "p_type_12";
        public static final String COLUMN_PTYPE_13 = "p_type_13";
        public static final String COLUMN_PTYPE_14 = "p_type_14";
        public static final String COLUMN_PTYPE_15 = "p_type_15";
        public static final String COLUMN_PTYPE_16 = "p_type_16";
        public static final String COLUMN_FORMDATE = "formdate";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "syncedDate";
        public static final String COLUMN_USER = "username";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "tagid";

    }
}

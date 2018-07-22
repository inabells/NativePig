package com.example.ina.nativepigdummy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.example.ina.nativepigdummy.Dialog.DateDialog;

import static android.os.Build.ID;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    //private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "nativepig.db";

    private static final String ADDNEWPIG_TABLE = "add_new_pig";
    private static final String addnewpig_ID = "addnewpig_ID";
    private static final String addnewpig_classification = "breeder_or_grower";
    private static final String addnewpig_animalearnotch = "animal_earnotch";
    private static final String addnewpig_sex = "newpig_sex";
    private static final String addnewpig_birthdate = "birth_date";
    private static final String addnewpig_weaningdate = "weaning_date";
    private static final String addnewpig_birthweight = "birth_weight";
    private static final String addnewpig_weaningweight = "weaning_weight";
    private static final String addnewpig_motherearnotch = "mother_earnotch";
    private static final String addnewpig_fatherearnotch = "father_earnotch";

    private static final String MORTALITY_TABLE = "mortality";
    private static final String mortality_ID = "mortality_ID";
    private static final String mortality_pig  = "mortality_pig";
    private static final String mortality_dateofdeath  = "date_of_death";
    private static final String mortality_causeofdeath  = "cause_of_death";
    private static final String mortality_age  = "mortality_age";

    private static final String SALES_TABLE = "sales";
    private static final String sales_id = "sales_ID";
    private static final String sales_pig = "sales_pig";
    private static final String sales_datesold = "date_sold";
    private static final String sales_weightsold = "weight_sold";
    private static final String sales_age  = "sales_age";

    private static final String OTHERS_TABLE = "others";
    private static final String others_id = "others_ID";
    private static final String others_pig = "others_pig";
    private static final String others_dateremoved = "date_removed";
    private static final String others_reason = "reason_for_removing";
    private static final String others_age  = "others_age";

    private static final String BREEDING_RECORDS_TABLE = "breeding_records";
    private static final String breeding_id = "breeding_ID";
    private static final String breeding_sowid = "breeding_sow";
    private static final String breeding_boarid = "breeding_boar";
    private static final String breeding_datebred = "breeding_datebred";

    private static final String OFFSPRING_RECORDS_TABLE = "offspring_records";
    private static final String offspring_id = "offspring_ID";
    private static final String offspring_regid = "offspring_regID";
    private static final String offspring_sex = "offspring_sex";
    private static final String offspring_birthweight = "offspring_birth_weight";
    private static final String offspring_weaningweight = "offspring_weaning_weight";

    private static final String PROFILE_TABLE = "farm_profile";
    private static final String profile_id = "profile_id";
    private static final String profile_farmname = "farm_name";
    private static final String profile_number = "farm_contact_no";
    private static final String profile_region = "farm_region";
    private static final String profile_province = "farm_province";
    private static final String profile_town = "farm_town";
    private static final String profile_barangay = "farm_barangay";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 10);
    }

    private static final String CREATE_TABLE_ADD_NEW_PIG = "CREATE TABLE " + ADDNEWPIG_TABLE + "("
            + addnewpig_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + addnewpig_classification + " TEXT,"
            + addnewpig_animalearnotch + " TEXT,"
            + addnewpig_sex + " TEXT,"
            + addnewpig_birthdate + " TEXT,"
            + addnewpig_weaningdate + " TEXT,"
            + addnewpig_birthweight + " TEXT,"
            + addnewpig_weaningweight + " TEXT,"
            + addnewpig_motherearnotch + " TEXT,"
            + addnewpig_fatherearnotch + " TEXT)";


    private static final String CREATE_TABLE_MORTALITY = "CREATE TABLE " + MORTALITY_TABLE + "("
            + mortality_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + mortality_pig + " TEXT,"
            + mortality_dateofdeath + " TEXT,"
            + mortality_causeofdeath + " TEXT,"
            + mortality_age + " TEXT)";


    private static final String CREATE_TABLE_SALES = "CREATE TABLE " + SALES_TABLE + "("
            + sales_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + sales_pig + " TEXT,"
            + sales_datesold + " TEXT,"
            + sales_weightsold + " TEXT,"
            + sales_age + " TEXT)";

    private static final String CREATE_TABLE_OTHERS = "CREATE TABLE " + OTHERS_TABLE + "("
            + others_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + others_pig + " TEXT,"
            + others_dateremoved + " TEXT,"
            + others_reason + " TEXT,"
            + others_age + " TEXT)";

    private static final String CREATE_TABLE_BREEDING_RECORDS = "CREATE TABLE " + BREEDING_RECORDS_TABLE + "("
            + breeding_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + breeding_sowid + " TEXT,"
            + breeding_boarid + " TEXT,"
            + breeding_datebred + " TEXT)";

    private static final String CREATE_TABLE_OFFSPRING_RECORDS = "CREATE TABLE " + OFFSPRING_RECORDS_TABLE + "("
            + offspring_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + offspring_regid + " TEXT,"
            + offspring_sex + " TEXT,"
            + offspring_birthweight + " TEXT,"
            + offspring_weaningweight + " TEXT)";

    private static final String CREATE_TABLE_PROFILE = "CREATE TABLE " + PROFILE_TABLE + "("
            + profile_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + profile_farmname + " TEXT,"
            + profile_number + " TEXT,"
            + profile_region + " TEXT,"
            + profile_province + " TEXT,"
            + profile_town + " TEXT,"
            + profile_barangay + " TEXT)";


    @Override
    public void  onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ADD_NEW_PIG);
        db.execSQL(CREATE_TABLE_MORTALITY);
        db.execSQL(CREATE_TABLE_SALES);
        db.execSQL(CREATE_TABLE_OTHERS);
        db.execSQL(CREATE_TABLE_BREEDING_RECORDS);
        db.execSQL(CREATE_TABLE_OFFSPRING_RECORDS);
        db.execSQL(CREATE_TABLE_PROFILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "  + ADDNEWPIG_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "  + MORTALITY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "  + SALES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "  + OTHERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "  + BREEDING_RECORDS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "  + OFFSPRING_RECORDS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "  + PROFILE_TABLE);
        onCreate(db);
    }

    public boolean addNewPigData(String classification, String animalearnotch, String sex, String birthdate, String weaningdate, String birthweight, String weaningweight, String motherpedigree, String fatherpedigree){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(addnewpig_classification, classification);
        contentValues.put(addnewpig_animalearnotch, animalearnotch);
        contentValues.put(addnewpig_sex, sex);
        contentValues.put(addnewpig_birthdate, birthdate);
        contentValues.put(addnewpig_weaningdate, weaningdate);
        contentValues.put(addnewpig_birthweight, birthweight);
        contentValues.put(addnewpig_weaningweight, weaningweight);
        contentValues.put(addnewpig_motherearnotch, motherpedigree);
        contentValues.put(addnewpig_fatherearnotch, fatherpedigree);

        long result = db.insert(ADDNEWPIG_TABLE, null, contentValues);

        if(result == -1) return false;
        else return true;
    }


    public Cursor getNumOfSow(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "animal_earnotch ||''|| newpig_sex" };
        String whereClause = "breeder_or_grower = ? AND newpig_sex = ?";
        String[] whereArgs = new String[]{"Breeder", "Female"};
        Cursor data = db.query(DatabaseHelper.ADDNEWPIG_TABLE, columns, whereClause , whereArgs, null, null, ID + " DESC", "1");
        return data;
    }

    //select addnewpig_animalearnotch from databasehelper where addnewpig_classification = 'Breeder';
    public Cursor getSowContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "newpig_sex || animal_earnotch" };
        String whereClause = "breeder_or_grower = ? AND newpig_sex = ?";
        String[] whereArgs = new String[]{"Breeder", "F"};
        Cursor data = db.query(DatabaseHelper.ADDNEWPIG_TABLE, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor getBoarContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "newpig_sex || animal_earnotch" };
        String whereClause = "breeder_or_grower = ? AND newpig_sex = ?";
        String[] whereArgs = new String[]{"Breeder", "M"};
        Cursor data = db.query(DatabaseHelper.ADDNEWPIG_TABLE, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor getFemaleGrowerContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "newpig_sex || animal_earnotch" };
        String whereClause = "breeder_or_grower = ? AND newpig_sex = ?";
        String[] whereArgs = new String[]{"Grower", "F"};
        Cursor data = db.query(DatabaseHelper.ADDNEWPIG_TABLE, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor getMaleGrowerContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "newpig_sex || animal_earnotch" };
        String whereClause = "breeder_or_grower = ? AND newpig_sex = ?";
        String[] whereArgs = new String[]{"Grower", "M"};
        Cursor data = db.query(DatabaseHelper.ADDNEWPIG_TABLE, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor getNewPigContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + ADDNEWPIG_TABLE, null);
        return data;
    }

    public boolean addMortalityData(String choosepig, String datedied, String causeofdeath, String age){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(mortality_pig, choosepig);
        contentValues.put(mortality_dateofdeath, datedied);
        contentValues.put(mortality_causeofdeath, causeofdeath);
        contentValues.put(mortality_age, age);

        long result = db.insert(MORTALITY_TABLE, null, contentValues);

        if(result == -1) return false;
        else return true;
    }

    public Cursor getMortalityContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + MORTALITY_TABLE, null);
        return data;
    }

    public boolean addSalesData(String choosepig, String datesold, String weightsold, String age){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(sales_pig, choosepig);
        contentValues.put(sales_datesold, datesold);
        contentValues.put(sales_weightsold, weightsold);
        contentValues.put(sales_age, age);


        long result = db.insert(SALES_TABLE, null, contentValues);

        if(result == -1) return false;
        else return true;
    }

    public Cursor getSalesContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + SALES_TABLE, null);
        return data;
    }

    public boolean addOthersData(String choosepig, String dateremoved, String reason, String age){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(others_pig, choosepig);
        contentValues.put(others_dateremoved, dateremoved);
        contentValues.put(others_reason, reason);
        contentValues.put(others_age, age);

        long result = db.insert(OTHERS_TABLE, null, contentValues);

        if(result == -1) return false;
        else return true;
    }


    public Cursor getOthersContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + OTHERS_TABLE, null);
        return data;
    }

    public boolean addBreedingRecordsData(String sowid, String boarid, String datebred){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(breeding_sowid, sowid);
        contentValues.put(breeding_boarid, boarid);
        contentValues.put(breeding_datebred, datebred);

        long result = db.insert(BREEDING_RECORDS_TABLE, null, contentValues);

        if(result == -1) return false;
        else return true;
    }

    public Cursor getBreedingRecordsContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + BREEDING_RECORDS_TABLE, null);
        return data;
    }

    public boolean addOffspringRecordsData(String offspringid, String sex, String birthweight, String weaningweight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(offspring_regid, offspringid);
        contentValues.put(offspring_sex, sex);
        contentValues.put(offspring_birthweight, birthweight);
        contentValues.put(offspring_weaningweight, weaningweight);

        long result = db.insert(OFFSPRING_RECORDS_TABLE, null, contentValues);

        if(result == -1) return false;
        else return true;
    }

    public Cursor getOffspringRecordsContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + OFFSPRING_RECORDS_TABLE, null);
        return data;
    }

    public boolean addProfileRecordData(String farmname, String contactno, String region, String province, String town, String barangay){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(profile_farmname, farmname);
        contentValues.put(profile_number, contactno);
        contentValues.put(profile_region, region);
        contentValues.put(profile_province, province);
        contentValues.put(profile_town, town);
        contentValues.put(profile_barangay, barangay);

        long result = db.insert(PROFILE_TABLE, null, contentValues);

        if(result == -1) return false;
        else return true;
    }

    public Cursor getProfileRecordsContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + PROFILE_TABLE, null);
        return data;
    }
}






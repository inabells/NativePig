package com.example.ina.nativepigdummy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.example.ina.nativepigdummy.Dialog.DateDialog;
import com.loopj.android.http.RequestParams;

import static android.os.Build.ID;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "nativepig.db";

    private static final String pig_table = "pig_table";
    private static final String pig_id = "pig_id";
    private static final String pig_registration_id = "pig_registration_id";
    private static final String pig_classification = "pig_classification";
    private static final String pig_earnotch = "pig_earnotch";
    private static final String pig_sex = "pig_sex";
    private static final String pig_birthdate = "pig_birthdate";
    private static final String pig_weaningdate = "pig_weaningdate";
    private static final String pig_birthweight = "pig_birthweight";
    private static final String pig_weaningweight = "pig_weaningweight";
    private static final String pig_mother_earnotch = "pig_mother_earnotch";
    private static final String pig_father_earnotch = "pig_father_earnotch";
    private static final String sex_ratio = "sex_ratio";
    private static final String litter_size_born_alive = "litter_size_born_alive";
    private static final String age_first_mating  = "age_first_mating";
    private static final String age_at_weaning  = "age_at_weaning";
    private static final String pig_mortality_and_sales = "pig_mortality_and_sales";
    private static final String date_removed_died = "date_removed_died";
    private static final String cause_of_death = "cause_of_death";
    private static final String weight_sold = "weight_sold";
    private static final String reason_removed = "reason_removed";
    private static final String age = "age";
    private static final String breeder_gross_morphology = "breeder_gross_morphology";
    private static final String id = "id";
    private static final String registration_id = "registration_id";
    private static final String date_collected = "date_collected";
    private static final String hair_type = "hair_type";
    private static final String hair_length  = "hair_length";
    private static final String coat_color  = "coat_color";
    private static final String color_pattern  = "color_pattern";
    private static final String head_shape  = "head_shape";
    private static final String skin_type  = "skin_type";
    private static final String ear_type  = "ear_type";
    private static final String tail_type  = "tail_type";
    private static final String backline  = "backline";
    private static final String other_marks  = "other_marks";
    private static final String breeder_morphometric_characteristics = "breeder_morphometric_characteristics";
    private static final String ear_length = "ear_length";
    private static final String head_length = "head_length";
    private static final String snout_length = "snout_length";
    private static final String body_length = "body_length";
    private static final String heart_girth = "heart_girth";
    private static final String pelvic_width = "pelvic_width";
    private static final String tail_length = "tail_length";
    private static final String height_at_withers = "height_at_withers";
    private static final String normal_teats = "normal_teats";
    private static final String pig_breeding_table = "pig_breeding_table";
    private static final String sow_registration_id = "sow_registration_id";
    private static final String boar_registration_id = "boar_registration_id";
    private static final String date_bred = "date_bred";
    private static final String expected_date_farrow = "expected_date_farrow";
    private static final String sow_status = "sow_status";
    private static final String weight_records = "weight_records";
    private static final String weight_at_45 = "weight_at_45";
    private static final String weight_at_60 = "weight_at_60";
    private static final String weight_at_90 = "weight_at_90";
    private static final String weight_at_150 = "weight_at_150";
    private static final String weight_at_180 = "weight_at_180";
    private static final String date_collected_at_45 = "date_collected_at_45";
    private static final String date_collected_at_60 = "date_collected_at_60";
    private static final String date_collected_at_90 = "date_collected_at_90";
    private static final String date_collected_at_150 = "date_collected_at_150";
    private static final String date_collected_at_180 = "date_collected_at_180";
    private static final String farm_table = "farm_profile";
    private static final String farm_id = "farm_id";
    private static final String farm_name = "farm_name";
    private static final String farm_breed = "farm_breed";
    private static final String farm_email = "farm_email";
    private static final String farm_contact_no = "farm_contact_no";
    private static final String farm_region = "farm_region";
    private static final String farm_province = "farm_province";
    private static final String farm_town = "farm_town";
    private static final String farm_barangay = "farm_barangay";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 13);
    }

    private static final String CREATE_TABLE_ADD_NEW_PIG = "CREATE TABLE " + pig_table + "("
            + pig_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + pig_registration_id + " TEXT,"
            + pig_classification + " TEXT,"
            + pig_earnotch + " TEXT,"
            + pig_sex + " TEXT,"
            + pig_birthdate + " TEXT,"
            + pig_weaningdate + " TEXT,"
            + pig_birthweight + " TEXT,"
            + pig_weaningweight + " TEXT,"
            + pig_mother_earnotch + " TEXT,"
            + pig_father_earnotch + " TEXT,"
            + sex_ratio + " TEXT,"
            + litter_size_born_alive + " TEXT,"
            + age_first_mating + " TEXT,"
            + age_at_weaning + " TEXT)";


    private static final String CREATE_TABLE_MORTALITY = "CREATE TABLE " + pig_mortality_and_sales + "("
            + pig_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + pig_registration_id + " TEXT,"
            + date_removed_died + " TEXT,"
            + cause_of_death + " TEXT,"
            + weight_sold + " TEXT,"
            + reason_removed + " TEXT,"
            + age + " TEXT)";


    private static final String CREATE_TABLE_SALES = "CREATE TABLE " + breeder_gross_morphology + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + registration_id + " TEXT,"
            + date_collected + " TEXT,"
            + hair_type + " TEXT,"
            + hair_length + " TEXT,"
            + coat_color + " TEXT,"
            + color_pattern + " TEXT,"
            + head_shape + " TEXT,"
            + skin_type + " TEXT,"
            + ear_type + " TEXT,"
            + tail_type + " TEXT,"
            + backline + " TEXT,"
            + other_marks + " TEXT)";

    private static final String CREATE_TABLE_OTHERS = "CREATE TABLE " + breeder_morphometric_characteristics + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + registration_id + " TEXT,"
            + ear_length + " TEXT,"
            + head_length + " TEXT,"
            + snout_length + " TEXT,"
            + body_length + " TEXT,"
            + heart_girth + " TEXT,"
            + pelvic_width + " TEXT,"
            + tail_length + " TEXT,"
            + height_at_withers + " TEXT,"
            + normal_teats + " TEXT,"
            + date_collected + " TEXT)";

    private static final String CREATE_TABLE_BREEDING_RECORDS = "CREATE TABLE " + weight_records + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + registration_id + " TEXT,"
            + weight_at_45 + " TEXT,"
            + weight_at_60 + " TEXT,"
            + weight_at_90 + " TEXT,"
            + weight_at_150 + " TEXT,"
            + weight_at_180 + " TEXT,"
            + date_collected_at_45 + " TEXT,"
            + date_collected_at_60 + " TEXT,"
            + date_collected_at_90 + " TEXT,"
            + date_collected_at_150 + " TEXT,"
            + date_collected_at_180 + " TEXT)";

    private static final String CREATE_TABLE_OFFSPRING_RECORDS = "CREATE TABLE " + pig_breeding_table + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + sow_registration_id + " TEXT,"
            + boar_registration_id + " TEXT,"
            + date_bred + " TEXT,"
            + sow_status + " TEXT,"
            + expected_date_farrow + " TEXT)";

    private static final String CREATE_TABLE_PROFILE = "CREATE TABLE " + farm_table + "("
            + farm_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + farm_name + " TEXT,"
            + farm_breed + " TEXT,"
            + farm_email + " TEXT,"
            + farm_contact_no + " TEXT,"
            + farm_region + " TEXT,"
            + farm_province + " TEXT,"
            + farm_town + " TEXT,"
            + farm_barangay + " TEXT)";


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
        db.execSQL("DROP TABLE IF EXISTS "  + pig_table);
        db.execSQL("DROP TABLE IF EXISTS "  + pig_mortality_and_sales);
        db.execSQL("DROP TABLE IF EXISTS "  + pig_breeding_table);
        db.execSQL("DROP TABLE IF EXISTS "  + farm_table);
        db.execSQL("DROP TABLE IF EXISTS "  + breeder_morphometric_characteristics);
        db.execSQL("DROP TABLE IF EXISTS "  + breeder_gross_morphology);
        db.execSQL("DROP TABLE IF EXISTS "  + weight_records);
        onCreate(db);
    }


    public boolean addNewPigData(String classification, String animalearnotch, String sex, String birthdate, String weaningdate,
                                 String birthweight, String weaningweight, String motherpedigree, String fatherpedigree, String sexratio,
                                 String littersizebornalive, String agefirstmating, String ageweaning, String regId){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(pig_classification, classification);
        contentValues.put(pig_earnotch, animalearnotch);
        contentValues.put(pig_sex, sex);
        contentValues.put(pig_birthdate, birthdate);
        contentValues.put(pig_weaningdate, weaningdate);
        contentValues.put(pig_birthweight, birthweight);
        contentValues.put(pig_weaningweight, weaningweight);
        contentValues.put(pig_mother_earnotch, motherpedigree);
        contentValues.put(pig_father_earnotch, fatherpedigree);
        contentValues.put(sex_ratio, sexratio);
        contentValues.put(litter_size_born_alive, littersizebornalive);
        contentValues.put(age_first_mating, agefirstmating);
        contentValues.put(age_at_weaning, ageweaning);
        contentValues.put(pig_registration_id, regId);

        long result = db.insert(pig_table, null, contentValues);

        if(result == -1) return false;
        else return true;
    }


    public Cursor getBoarContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "pig_registration_id" };
        String whereClause = "pig_classification = ? AND pig_sex = ?";
        String[] whereArgs = new String[]{"Breeder", "M"};
        Cursor data = db.query(DatabaseHelper.pig_table, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    /*
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
    }*/
}






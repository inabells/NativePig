package com.example.ina.nativepigdummy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

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
    private static final String is_synced  = "is_synced";
    private static final String pig_mortality_and_sales = "pig_mortality_and_sales";
    private static final String date_removed_died = "date_removed_died";
    private static final String cause_of_death = "cause_of_death";
    private static final String weight_sold = "weight_sold";
    private static final String reason_removed = "reason_removed";
    private static final String age = "pig_age";
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
        super(context, DATABASE_NAME, null, 22);
    }

    private static final String CREATE_TABLE_PIG = "CREATE TABLE " + pig_table + "("
            + pig_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
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
            + age_at_weaning + " TEXT,"
            + is_synced + " TEXT)";


    private static final String CREATE_TABLE_MORTALITY_SALES = "CREATE TABLE " + pig_mortality_and_sales + "("
            + pig_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + pig_registration_id + " TEXT,"
            + date_removed_died + " TEXT,"
            + cause_of_death + " TEXT,"
            + weight_sold + " TEXT,"
            + reason_removed + " TEXT,"
            + age + " TEXT, "
            + is_synced + " TEXT)";


    private static final String CREATE_TABLE_GROSS_MORPHOLOGY = "CREATE TABLE " + breeder_gross_morphology + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
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
            + other_marks + " TEXT,"
            + is_synced + " TEXT)";

    private static final String CREATE_TABLE_MORPH_CHARACTERISTICS = "CREATE TABLE " + breeder_morphometric_characteristics + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
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
            + date_collected + " TEXT,"
            + is_synced + " TEXT)";

    private static final String CREATE_TABLE_WEIGHT_RECORDS = "CREATE TABLE " + weight_records + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
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
            + date_collected_at_180 + " TEXT,"
            + is_synced + " TEXT)";

    private static final String CREATE_TABLE_PIG_BREEDING = "CREATE TABLE " + pig_breeding_table + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + sow_registration_id + " TEXT,"
            + boar_registration_id + " TEXT,"
            + date_bred + " TEXT,"
            + sow_status + " TEXT,"
            + expected_date_farrow + " TEXT,"
            + is_synced + " TEXT)";

    private static final String CREATE_TABLE_FARM = "CREATE TABLE " + farm_table + "("
            + farm_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + farm_name + " TEXT,"
            + farm_breed + " TEXT,"
            + farm_email + " TEXT,"
            + farm_contact_no + " TEXT,"
            + farm_region + " TEXT,"
            + farm_province + " TEXT,"
            + farm_town + " TEXT,"
            + farm_barangay + " TEXT,"
            + is_synced + " TEXT)";


    @Override
    public void  onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PIG);
        db.execSQL(CREATE_TABLE_MORTALITY_SALES);
        db.execSQL(CREATE_TABLE_GROSS_MORPHOLOGY);
        db.execSQL(CREATE_TABLE_MORPH_CHARACTERISTICS);
        db.execSQL(CREATE_TABLE_WEIGHT_RECORDS);
        db.execSQL(CREATE_TABLE_PIG_BREEDING);
        db.execSQL(CREATE_TABLE_FARM);
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
                                 String littersizebornalive, String agefirstmating, String ageweaning, String regId, String isSynced){
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
        contentValues.put(is_synced, isSynced);

        long result = db.insert(pig_table, null, contentValues);

        if(result == -1) return false;
        else return true;
    }

//    public boolean updateBreederProfile(String regId, String birthday, String sexRatio, String birthWeight, String weaningWeight, String litterSize,
//                                        String ageMating, String ageWeaning, String motherPedigree, String fatherPedigree, String isSynced){
//        SQLiteDatabase db =  this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(pig_registration_id, regId);
//        contentValues.put(pig_birthdate, birthday);
//        contentValues.put(pig_birthweight, birthWeight);
//        contentValues.put(pig_weaningweight, weaningWeight);
//        contentValues.put(pig_mother_earnotch, motherPedigree);
//        contentValues.put(pig_father_earnotch, fatherPedigree);
//        contentValues.put(sex_ratio, sexRatio);
//        contentValues.put(litter_size_born_alive, litterSize);
//        contentValues.put(age_first_mating, ageMating);
//        contentValues.put(age_at_weaning, ageWeaning);
//        contentValues.put(is_synced, isSynced);
//
//        long result = db.insert(pig_table, null, contentValues);
//
//        if(result == -1) return false;
//        else return true;
//    }

    public boolean addBreederDetails(String reg_id, String birthdate, String sexRatio, String birthWeight, String weaningWeight,
                                     String litterSize, String ageFirstMating, String ageWeaning, String motherEarnotch,
                                     String fatherEarnotch, String isSynced){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(pig_registration_id, reg_id);
        contentValues.put(pig_birthdate, birthdate);
        contentValues.put(sex_ratio, sexRatio);
        contentValues.put(pig_birthweight, birthWeight);
        contentValues.put(pig_weaningweight, weaningWeight);
        contentValues.put(litter_size_born_alive, litterSize);
        contentValues.put(age_first_mating, ageFirstMating);
        contentValues.put(age_at_weaning, ageWeaning);
        contentValues.put(pig_mother_earnotch, motherEarnotch);
        contentValues.put(pig_father_earnotch, fatherEarnotch);
        contentValues.put(is_synced, isSynced);
        String whereClause = "pig_registration_id = ?";
        String[] whereArgs = new String[]{reg_id};
        
        long result = db.update(pig_table, contentValues, whereClause, whereArgs);

        if(result == -1) return false;
        else return true;
    }

    public boolean addGrossMorphologyData(String regId, String datecollected, String hairtype, String hairlength, String coatcolor, String colopattern,
                                          String headshape, String skintype, String eartype, String tailtype, String back_line, String othermarks, String isSynced){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(registration_id, regId);
        contentValues.put(date_collected, datecollected);
        contentValues.put(hair_type, hairtype);
        contentValues.put(hair_length, hairlength);
        contentValues.put(coat_color, coatcolor);
        contentValues.put(color_pattern, colopattern);
        contentValues.put(head_shape, headshape);
        contentValues.put(skin_type, skintype);
        contentValues.put(ear_type, eartype);
        contentValues.put(tail_type, tailtype);
        contentValues.put(backline, back_line);
        contentValues.put(other_marks, othermarks);
        contentValues.put(is_synced, isSynced);

        long result = db.insert(breeder_gross_morphology, null, contentValues);

        if(result == -1) return false;
        else return true;
    }

    public boolean addMorphCharData(String regId, String datecollected, String earlength, String headlength,
                                    String snoutlength, String bodylength, String heartgirth, String pelvicwidth,
                                    String taillength, String heightwithers, String normalteats, String isSynced){

        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(registration_id, regId);
        contentValues.put(date_collected, datecollected);
        contentValues.put(ear_length, earlength);
        contentValues.put(head_length, headlength);
        contentValues.put(snout_length, snoutlength);
        contentValues.put(body_length, bodylength);
        contentValues.put(heart_girth, heartgirth);
        contentValues.put(pelvic_width, pelvicwidth);
        contentValues.put(tail_length, taillength);
        contentValues.put(height_at_withers, heightwithers);
        contentValues.put(normal_teats, normalteats);
        contentValues.put(is_synced, isSynced);

        long result = db.insert(breeder_morphometric_characteristics, null, contentValues);

        if(result == -1) return false;
        else return true;
    }

    public boolean addWeightRecords(String regId, String datecollected45, String datecollected60, String datecollected90, String datecollected150,
                                    String datecollected180, String weight45, String weight60, String weight90, String weight150, String weight180, String isSynced){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(registration_id, regId);
        contentValues.put(date_collected_at_45, datecollected45);
        contentValues.put(date_collected_at_60, datecollected60);
        contentValues.put(date_collected_at_90, datecollected90);
        contentValues.put(date_collected_at_150, datecollected150);
        contentValues.put(date_collected_at_180, datecollected180);
        contentValues.put(weight_at_45, weight45);
        contentValues.put(weight_at_60, weight60);
        contentValues.put(weight_at_90, weight90);
        contentValues.put(weight_at_150, weight150);
        contentValues.put(weight_at_180, weight180);
        contentValues.put(is_synced, isSynced);

        long result = db.insert(weight_records, null, contentValues);

        if(result == -1) return false;
        else return true;
    }

    public boolean addMortalitySalesData(String regId, String datedied, String causeofdeath, String weightsold, String reasonremoved, String pigAge, String isSynced){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(pig_registration_id, regId);
        contentValues.put(date_removed_died, datedied);
        contentValues.put(cause_of_death, causeofdeath);
        contentValues.put(weight_sold, weightsold);
        contentValues.put(reason_removed, reasonremoved);
        contentValues.put(age, pigAge);
        contentValues.put(is_synced, isSynced);

        long result = db.insert(pig_mortality_and_sales, null, contentValues);

        if(result == -1){
            Log.d("addMortalitySalesData", "Error in adding mortality sales to local");
            return false;
        }
        else{
            boolean success = setIsSyncedFromPigTableToDelete(regId);
            if(success) Log.d("addMortalitySalesData", "is_synced changed to delete");
            else Log.d("addMortalitySalesData", "Error in changing is_synced to delete");
            return true;
        }
    }

    public boolean addBreedingRecord(String sowRegId, String boarRegId, String dateBred, String sowStatus, String dateFarrow, String isSynced){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(sow_registration_id, sowRegId);
        contentValues.put(boar_registration_id, boarRegId);
        contentValues.put(date_bred, dateBred);
        contentValues.put(sow_status, sowStatus);
        contentValues.put(expected_date_farrow, dateFarrow);
        contentValues.put(is_synced, isSynced);

        long result = db.insert(pig_breeding_table, null, contentValues);

        if(result == -1) return false;
        else return true;
    }

    public boolean setIsSyncedFromPigTableToDelete(String regId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(is_synced, "delete");
        String whereClause = "pig_registration_id = ?";
        String[] whereArgs = new String[]{regId};

        int success = db.update("pig_table", contentValues, whereClause, whereArgs);

        if(success==1) return true;
        else return  false;
    }

    public boolean addAsBreeder(String regId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(pig_classification, "Breeder");
        String whereClause = "pig_registration_id = ?";
        String[] whereArgs = new String[]{regId};

        int success = db.update("pig_table", contentValues, whereClause, whereArgs);

        if(success==1) return true;
        else return  false;
    }

    public Cursor getBreedingContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = {"*"};
        Cursor data = db.query(DatabaseHelper.pig_breeding_table, columns, null, null, null, null, null);
        return data;
    }

    public Cursor getMortalityContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM pig_mortality_and_sales WHERE cause_of_death IS NOT NULL", null);
        return data;
    }

    public Cursor getSalesContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM pig_mortality_and_sales WHERE weight_sold IS NOT NULL", null);
        return data;
    }

    public Cursor getOthersContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM pig_mortality_and_sales WHERE reason_removed IS NOT NULL", null);
        return data;
    }

    public Cursor getBoarContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "*" };
        String whereClause = "pig_classification = ? AND pig_sex = ? AND is_synced != ?";
        String[] whereArgs = new String[]{"Breeder", "M", "delete"};
        Cursor data = db.query(DatabaseHelper.pig_table, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor getSowContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "*" };
        String whereClause = "pig_classification = ? AND pig_sex = ? AND is_synced != ?";
        String[] whereArgs = new String[]{"Breeder", "F", "delete"};
        Cursor data = db.query(DatabaseHelper.pig_table, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor getFemaleGrowerContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "*" };
        String whereClause = "pig_classification = ? AND pig_sex = ? AND is_synced != ?";
        String[] whereArgs = new String[]{"Grower", "F", "delete"};
        Cursor data = db.query(DatabaseHelper.pig_table, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor getMaleGrowerContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "*" };
        String whereClause = "pig_classification = ? AND pig_sex = ? AND is_synced != ?";
        String[] whereArgs = new String[]{"Grower", "M", "delete"};
        Cursor data = db.query(DatabaseHelper.pig_table, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public HashMap<String, Integer> local_getAllCount() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("sowCount", getSowContents().getCount());
        map.put("boarCount", getBoarContents().getCount());
        map.put("femaleGrowerCount", getFemaleGrowerContents().getCount());
        map.put("maleGrowerCount", getMaleGrowerContents().getCount());
        return map;
    }

    public Cursor getAllUnsyncedData(String table_name){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "*" };
        String whereClause = "is_synced = ? OR is_synced = ?";
        String[] whereArgs = new String[]{"false", "delete"};
        Cursor data = db.query(table_name, columns, whereClause, whereArgs, null, null, null);
        return data;
    }

    public boolean addAllUnsyncedFromLocalPigTableToServer() {
        Cursor unsyncedData = getAllUnsyncedData("pig_table");
        RequestParams params;

        while(unsyncedData.moveToNext()){
            if((unsyncedData.getString(unsyncedData.getColumnIndex("is_synced"))).equals("false")){
                params = buildParamsPigTable(unsyncedData);
                addPigToServer(params);
            } else if((unsyncedData.getString(unsyncedData.getColumnIndex("is_synced"))).equals("delete")){
                params = new RequestParams();
                params.put("pig_registration_id", unsyncedData.getString(unsyncedData.getColumnIndex("pig_registration_id")));
                deletePigFromServer(params);
            }
        }
        return true;
    }

    public boolean addAllUnsyncedFromLocalGrossMorphologyTableToServer(){
        Cursor unsyncedData = getAllUnsyncedData("breeder_gross_morphology");
        RequestParams params;

        while(unsyncedData.moveToNext()){
            params = buildParamsGrossMorphologyTable(unsyncedData);
            addGrossMorphologyDataToServer(params);
        }
        return true;
    }

    public boolean addAllUnsyncedFromLocalMortalitySalesTableToServer(){
        Cursor unsyncedData = getAllUnsyncedData("pig_mortality_and_sales");
        RequestParams params;

        while(unsyncedData.moveToNext()){
            params = buildParamsMortalitySalesTable(unsyncedData);
            addMortalitySalesToServer(params);
        }
        return true;
    }

    public boolean addAllUnsyncedFromLocalMorphCharTableToServer(){
        Cursor unsyncedData = getAllUnsyncedData("breeder_morphometric_characteristics");
        RequestParams params;

        while(unsyncedData.moveToNext()){
            params = buildParamsMorphCharTable(unsyncedData);
            addMorphCharDataToServer(params);
        }
        return true;
    }

    public boolean addAllUnsyncedFromLocalWeightRecordsTableToServer(){
        Cursor unsyncedData = getAllUnsyncedData("weight_records");
        RequestParams params;

        while(unsyncedData.moveToNext()){
            params = buildParamsWeightRecordsTable(unsyncedData);
            addWeightRecordsDataToServer(params);
        }
        return true;
    }

    private RequestParams buildParamsPigTable(Cursor data){
        RequestParams params = new RequestParams();
        final String reg_id = data.getString(data.getColumnIndex("pig_registration_id"));

        params.add("pig_registration_id", reg_id);
        params.add("pig_classification", data.getString(data.getColumnIndex("pig_classification")));
        params.add("pig_earnotch", data.getString(data.getColumnIndex("pig_earnotch")));
        params.add("pig_sex", data.getString(data.getColumnIndex("pig_sex")));
        params.add("pig_birthdate", data.getString(data.getColumnIndex("pig_birthdate")));
        params.add("pig_weaningdate", data.getString(data.getColumnIndex("pig_weaningdate")));
        params.add("pig_birthweight", data.getString(data.getColumnIndex("pig_birthweight")));
        params.add("pig_weaningweight", data.getString(data.getColumnIndex("pig_weaningweight")));
        params.add("pig_mother_earnotch", data.getString(data.getColumnIndex("pig_mother_earnotch")));
        params.add("pig_father_earnotch", data.getString(data.getColumnIndex("pig_father_earnotch")));
        params.add("sex_ratio", data.getString(data.getColumnIndex("sex_ratio")));
        params.add("litter_size_born_alive", data.getString(data.getColumnIndex("litter_size_born_alive")));
        params.add("age_first_mating", data.getString(data.getColumnIndex("age_first_mating")));
        params.add("age_at_weaning", data.getString(data.getColumnIndex("age_at_weaning")));

        return params;
    }

    private RequestParams buildParamsGrossMorphologyTable(Cursor data){
        RequestParams params = new RequestParams();
        final String reg_id = data.getString(data.getColumnIndex("registration_id"));

        params.add("registration_id", reg_id);
        params.add("date_collected", data.getString(data.getColumnIndex("date_collected")));
        params.add("hair_type", data.getString(data.getColumnIndex("hair_type")));
        params.add("hair_length", data.getString(data.getColumnIndex("hair_length")));
        params.add("coat_color", data.getString(data.getColumnIndex("coat_color")));
        params.add("color_pattern", data.getString(data.getColumnIndex("color_pattern")));
        params.add("head_shape", data.getString(data.getColumnIndex("head_shape")));
        params.add("skin_type", data.getString(data.getColumnIndex("skin_type")));
        params.add("ear_type", data.getString(data.getColumnIndex("ear_type")));
        params.add("tail_type", data.getString(data.getColumnIndex("tail_type")));
        params.add("backline", data.getString(data.getColumnIndex("backline")));
        params.add("other_marks", data.getString(data.getColumnIndex("other_marks")));

        return params;
    }

    private RequestParams buildParamsMorphCharTable(Cursor data){
        RequestParams params = new RequestParams();
        final String reg_id = data.getString(data.getColumnIndex("registration_id"));

        params.add("registration_id", reg_id);
        params.add("date_collected", data.getString(data.getColumnIndex("date_collected")));
        params.add("ear_length", data.getString(data.getColumnIndex("ear_length")));
        params.add("head_length", data.getString(data.getColumnIndex("head_length")));
        params.add("snout_length", data.getString(data.getColumnIndex("snout_length")));
        params.add("body_length", data.getString(data.getColumnIndex("body_length")));
        params.add("heart_girth", data.getString(data.getColumnIndex("heart_girth")));
        params.add("pelvic_width", data.getString(data.getColumnIndex("pelvic_width")));
        params.add("tail_length", data.getString(data.getColumnIndex("tail_length")));
        params.add("height_at_withers", data.getString(data.getColumnIndex("height_at_withers")));
        params.add("normal_teats", data.getString(data.getColumnIndex("normal_teats")));

        return params;
    }

    private RequestParams buildParamsMortalitySalesTable(Cursor data){
        RequestParams params = new RequestParams();
        final String reg_id = data.getString(data.getColumnIndex("pig_registration_id"));

        params.add("pig_registration_id", reg_id);
        params.add("date_removed_died", data.getString(data.getColumnIndex("date_removed_died")));
        params.add("cause_of_death", data.getString(data.getColumnIndex("cause_of_death")));
        params.add("weight_sold", data.getString(data.getColumnIndex("weight_sold")));
        params.add("reason_removed", data.getString(data.getColumnIndex("reason_removed")));
        params.add("age", data.getString(data.getColumnIndex("pig_age")));

        return params;
    }

    private RequestParams buildParamsWeightRecordsTable(Cursor data){
        RequestParams params = new RequestParams();
        final String reg_id = data.getString(data.getColumnIndex("registration_id"));

        params.add("registration_id", reg_id);
        params.add("date_collected_at_45", data.getString(data.getColumnIndex("date_collected_at_45")));
        params.add("date_collected_at_60", data.getString(data.getColumnIndex("date_collected_at_60")));
        params.add("date_collected_at_90", data.getString(data.getColumnIndex("date_collected_at_90")));
        params.add("date_collected_at_150", data.getString(data.getColumnIndex("date_collected_at_150")));
        params.add("date_collected_at_180", data.getString(data.getColumnIndex("date_collected_at_180")));
        params.add("weight_at_45", data.getString(data.getColumnIndex("weight_at_45")));
        params.add("weight_at_60", data.getString(data.getColumnIndex("weight_at_60")));
        params.add("weight_at_90", data.getString(data.getColumnIndex("weight_at_90")));
        params.add("weight_at_150", data.getString(data.getColumnIndex("weight_at_150")));
        params.add("weight_at_180", data.getString(data.getColumnIndex("weight_at_180")));

        return params;
    }

    private void addPigToServer(RequestParams params) {
        ApiHelper.addPig("addPig", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("pigTableLocalToServer", "Successfully added pigs from local to server");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("pigTableLocalToServer", "Error in adding");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void deletePigFromServer(RequestParams params) {
        ApiHelper.deletePig("deletePig", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("deletePigFromServer", "Successfully delete pigs from server");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("deletePigFromServer", "Error in deleting");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void addGrossMorphologyDataToServer(RequestParams params){
        ApiHelper.updateGrossMorphology("updateGrossMorphology", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("grossMorphLocalToServer", "Successfully added gross morphology from local to server");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("grossMorphLocalToServer", "Error in adding");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void addMorphCharDataToServer(RequestParams params){
        ApiHelper.updateMorphChar("updateMorphChar", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("morphCharLocalToServer", "Successfully added morph char from local to server");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("morphCharLocalToServer", "Error occurred");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void addWeightRecordsDataToServer(RequestParams params){
        ApiHelper.updateWeightRecords("updateWeightRecords", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("weightRecLocalToServer", "Successfully added weight records from local to server");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("weightRecLocalToServer", "Error occurred");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void addMortalitySalesToServer(RequestParams params){
        ApiHelper.addPigMortalitySales("addPigMortalitySales", params, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("addMortality", "Succesfully added");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("addMortality", "Error occurred");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    public void clearLocalDatabases() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + pig_table);
        db.execSQL("DELETE FROM " + breeder_gross_morphology);
        db.execSQL("DELETE FROM " + breeder_morphometric_characteristics);
        db.execSQL("DELETE FROM " + weight_records);
        db.execSQL("DELETE FROM " + pig_mortality_and_sales);
    }

    public void getAllDataFromServer() {
        ApiHelper.getAllPigs("getAllPigs", null, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("getAllDataFromServer", "Successfully added data to local from server pigs");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("getAllDataFromServer", "Error occurred");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONArray jsonArray = new JSONArray(rawJsonData);
                JSONObject jsonObject;
                for(int i = jsonArray.length()-1; i>=0; i--){
                    jsonObject = (JSONObject) jsonArray.get(i);
                    addNewPigData(
                        jsonObject.getString("pig_classification"),
                        jsonObject.getString("pig_earnotch"),
                        jsonObject.getString("pig_sex"),
                        jsonObject.getString("pig_birthdate"),
                        jsonObject.getString("pig_weaningdate"),
                        jsonObject.getString("pig_birthweight"),
                        jsonObject.getString("pig_weaningweight"),
                        jsonObject.getString("pig_mother_earnotch"),
                        jsonObject.getString("pig_father_earnotch"),
                        jsonObject.getString("sex_ratio"),
                        jsonObject.getString("litter_size_born_alive"),
                        jsonObject.getString("age_first_mating"),
                        jsonObject.getString("age_at_weaning"),
                        jsonObject.getString("pig_registration_id"), "true");
                }
                return null;
            }
        });

        ApiHelper.getAllGrossMorphProfile("getAllGrossMorphProfile", null, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("getAllDataFromServer", "Successfully added data to local from server gross");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("getAllDataFromServer", "Error occurred");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONArray jsonArray = new JSONArray(rawJsonData);
                JSONObject jsonObject;
                for(int i = jsonArray.length()-1; i>=0; i--){
                    jsonObject = (JSONObject) jsonArray.get(i);
                    addGrossMorphologyData(
                        jsonObject.getString("registration_id"),
                        jsonObject.getString("date_collected"),
                        jsonObject.getString("hair_type"),
                        jsonObject.getString("hair_length"),
                        jsonObject.getString("coat_color"),
                        jsonObject.getString("color_pattern"),
                        jsonObject.getString("head_shape"),
                        jsonObject.getString("skin_type"),
                        jsonObject.getString("ear_type"),
                        jsonObject.getString("tail_type"),
                        jsonObject.getString("backline"),
                        jsonObject.getString("other_marks"),
                        "true"
                    );
                }
                return null;
            }
        });

        ApiHelper.getAllMorphCharProfile("getAllMorphCharProfile", null, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("getAllDataFromServer", "Successfully added data to local from server morph char");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("getAllDataFromServer", "Failed add data to local from server morph char");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONArray jsonArray = new JSONArray(rawJsonData);
                JSONObject jsonObject;
                for(int i = jsonArray.length()-1; i>=0; i--){
                    jsonObject = (JSONObject) jsonArray.get(i);
                    addMorphCharData(
                        jsonObject.getString("registration_id"),
                        jsonObject.getString("date_collected"),
                        jsonObject.getString("ear_length"),
                        jsonObject.getString("head_length"),
                        jsonObject.getString("snout_length"),
                        jsonObject.getString("body_length"),
                        jsonObject.getString("heart_girth"),
                        jsonObject.getString("pelvic_width"),
                        jsonObject.getString("tail_length"),
                        jsonObject.getString("height_at_withers"),
                        jsonObject.getString("normal_teats"),
                        "true"
                    );
                }
                return null;
            }
        });

        ApiHelper.getAllWeightProfile("getAllWeightProfile", null, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("getAllDataFromServer", "Successfully added data to local from server weight records");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("getAllDataFromServer", "Failed add data to local from server weight records");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONArray jsonArray = new JSONArray(rawJsonData);
                JSONObject jsonObject;
                for(int i = jsonArray.length()-1; i>=0; i--){
                    jsonObject = (JSONObject) jsonArray.get(i);
                    addWeightRecords(
                        jsonObject.getString("registration_id"),
                        jsonObject.getString("date_collected_at_45"),
                        jsonObject.getString("date_collected_at_60"),
                        jsonObject.getString("date_collected_at_90"),
                        jsonObject.getString("date_collected_at_150"),
                        jsonObject.getString("date_collected_at_180"),
                        jsonObject.getString("weight_at_45"),
                        jsonObject.getString("weight_at_60"),
                        jsonObject.getString("weight_at_90"),
                        jsonObject.getString("weight_at_150"),
                        jsonObject.getString("weight_at_180"),
                        "true"
                    );
                }
                return null;
            }
        });

        ApiHelper.getAllMortalitySalesProfile("getAllMortalitySalesProfile", null, new BaseJsonHttpResponseHandler<Object>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                Log.d("getAllDataFromServer", "Successfully added data to local from server mortality sales");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Log.d("getAllDataFromServer", "Failed add data to local from server mortality sales");
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONArray jsonArray = new JSONArray(rawJsonData);
                JSONObject jsonObject;
                for(int i = jsonArray.length()-1; i>=0; i--){
                    jsonObject = (JSONObject) jsonArray.get(i);
                    addMortalitySalesData(
                        jsonObject.getString("pig_registration_id"),
                        jsonObject.getString("date_removed_died"),
                        jsonObject.getString("cause_of_death"),
                        jsonObject.getString("weight_sold"),
                        jsonObject.getString("reason_removed"),
                        jsonObject.getString("age"),
                        "true"
                    );
                }
                return null;
            }
        });
    }

    public Cursor getSinglePig(String reg_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "*" };
        String whereClause = "pig_registration_id = ?";
        String[] whereArgs = new String[]{reg_id};
        Cursor data = db.query("pig_table", columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor getGrossMorphProfile(String reg_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "*" };
        String whereClause = "registration_id = ?";
        String[] whereArgs = new String[]{reg_id};
        Cursor data = db.query("breeder_gross_morphology", columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor getMorphCharProfile(String reg_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "*" };
        String whereClause = "registration_id = ?";
        String[] whereArgs = new String[]{reg_id};
        Cursor data = db.query("breeder_morphometric_characteristics", columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor getWeightRecords(String reg_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "*" };
        String whereClause = "registration_id = ?";
        String[] whereArgs = new String[]{reg_id};
        Cursor data = db.query("weight_records", columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor getBreedingProfile(String sowRegId, String boarRegId){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "*" };
        String whereClause = "sow_registration_id = ? AND boar_registration_id = ?";
        String[] whereArgs = new String[]{sowRegId,boarRegId};
        Cursor data = db.query("pig_breeding_table", columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor generatePigList(String reg_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = {"pig_registration_id"};
        String whereClause = "lower(pig_registration_id) LIKE ? AND is_synced != ?";
        String[] whereArgs = new String[]{"%" + reg_id.toLowerCase() +"%", "delete"};
        Cursor data = db.query("pig_table", columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor generateSowList(String reg_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = {"pig_registration_id"};
        String whereClause = "lower(pig_registration_id) LIKE ? AND pig_classification = ? AND pig_sex = ?";
        String[] whereArgs = new String[]{"%" + reg_id.toLowerCase() +"%", "Breeder", "F"};
        Cursor data = db.query("pig_table", columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor generateBoarList(String reg_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = {"pig_registration_id"};
        String whereClause = "lower(pig_registration_id) LIKE ? AND pig_classification = ? AND pig_sex = ?";
        String[] whereArgs = new String[]{"%" + reg_id.toLowerCase() +"%", "Breeder", "M"};
        Cursor data = db.query("pig_table", columns, whereClause , whereArgs, null, null, null);
        return data;
    }
}
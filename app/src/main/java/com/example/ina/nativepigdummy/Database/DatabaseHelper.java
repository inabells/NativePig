package com.example.ina.nativepigdummy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.ina.nativepigdummy.API.ApiHelper;
import com.example.ina.nativepigdummy.Activities.AddNewPigActivity;
import com.example.ina.nativepigdummy.Activities.MyApplication;
import com.google.android.gms.flags.impl.DataUtils;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLInput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
    private static final String sow_litter_table = "sow_litter_table";
    private static final String parity = "parity";
    private static final String total_litter_size_born = "total_litter_size_born";
    private static final String total_litter_size_born_alive = "total_litter_size_born_alive";
    private static final String number_weaned = "number_weaned";
    private static final String average_birth_weight = "average_birth_weight";
    private static final String number_of_males = "number_of_males";
    private static final String number_of_females = "number_of_females";
    private static final String average_weaning_weight = "average_weaning_weight";
    private static final String number_stillborn = "number_stillborn";
    private static final String number_mummified = "number_mummified";
    private static final String abnormalities = "abnormalities";


    //------------------------- new db

    private static final String administrators = "administrators";
    private static final String admins = "admins";
    private static final String animals = "animals";
    private static final String animaltype_id = "animaltype_id";
    private static final String registryid = "registryid";
    private static final String breed_id = "breed_id";
    private static final String grossmorpho = "grossmorpho";
    private static final String morphochars = "morphochars";
    private static final String weightrecord = "weightrecord";
    private static final String status = "status";
    private static final String created_at = "created_at";
    private static final String updated_at = "updated_at";
    private static final String animal_properties = "animal_properties";
    private static final String animal_id = "animal_id";
    private static final String property_id = "property_id";
    private static final String value = "value";
    private static final String animal_types = "animal_types";
    private static final String species = "species";
    private static final String breeds = "breeds";
    private static final String breed = "breed";
    private static final String farms = "farms";
    private static final String name = "name";
    private static final String code = "code";
    private static final String region = "region";
    private static final String province = "province";
    private static final String town = "town";
    private static final String barangay = "barangay";
    private static final String breedable_id = "breedable_id";
    private static final String user_id = "user_id";
    private static final String user_type = "user_type";
    private static final String farm_animaltypes = "farm_animaltypes";
    private static final String farm_users = "farm_users";
    private static final String groupings = "groupings";
    //private static final String registryid = "registryid";
    private static final String mother_id = "mother_id";
    private static final String father_id = "father_id";
    private static final String members = "members";
    private static final String grouping_members = "grouping_members";
    private static final String grouping_id = "grouping_id";
    private static final String grouping_properties = "grouping_properties";
    private static final String migrations = "migrations";
    private static final String migration = "migration";
    private static final String batch = "batch";
    private static final String mortalities = "mortalities";
    private static final String datedied = "datedied";
    private static final String cause = "cause";
    private static final String password_resets = "password_resets";
    private static final String email = "email";
    private static final String token = "token";
    private static final String removed_animals = "removed_animals";
    private static final String dateremoved = "dateremoved";
    private static final String reason = "reason";
    private static final String roles = "roles";
    private static final String title = "title";
    private static final String role_user = "role_user";
    private static final String role_id = "role_id";
    private static final String sales = "sales";
    private static final String datesold = "datesold";
    private static final String weight = "weight";
    private static final String price = "price";
    private static final String users = "users";
    private static final String phone = "phone";
    private static final String lastseen = "lastseen";
    private static final String photo = "photo";
    private static final String isadmin = "isadmin";
    private static final String farmable_id = "farmable_id";
    private static final String deleted_at = "deleted_at";
    private static final String remember_token = "remember_token";
    private static final String weight_collections = "weight_collections";
    private static final String properties = "properties";
    private static final String fname = "fname";
    private static final String description = "description";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 32);
    }
    //------------------------------------------------------------------------------------------
    private static final String Administrators = "CREATE TABLE " + administrators + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)";

    private static final String Admins = "CREATE TABLE " + admins + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)";

    private static final String Animals = "CREATE TABLE " + animals + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + animaltype_id + " INTEGER(10),"
            + registryid + " VARCHAR(225),"
            + farm_id + " INTEGER(10),"
            + breed_id + " INTEGER(10),"
            + grossmorpho + " TINYINT(1),"
            + morphochars + " TINYINT(1),"
            + weightrecord + " TINYINT(1),"
            + status + " VARCHAR(225),"
            + created_at + " TIMESTAMP,"
            + updated_at + " TIMESTAMP,"
            + is_synced + " TEXT)";

    private static final String Animal_Properties = "CREATE TABLE " + animal_properties + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + animal_id + " INTEGER(10),"
            + property_id + " INTEGER(10),"
            + value + " VARCHAR(225),"
            + created_at + " TIMESTAMP,"
            + updated_at + " TIMESTAMP,"
            + is_synced + " TEXT)";

    private static final String Animal_Types = "CREATE TABLE " + animal_types + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + species + " VARCHAR(225),"
            + is_synced + " TEXT)";

    private static final String Breeds = "CREATE TABLE " + breeds + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + breed + " VARCHAR(225),"
            + animaltype_id + " INTEGER(10),"
            + is_synced + " TEXT)";

    private static final String Farms = "CREATE TABLE " + farms + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + name + " VARCHAR(225),"
            + registryid + " VARCHAR(225),"
            + code + " VARCHAR(225),"
            + region + " VARCHAR(225),"
            + province + " VARCHAR(225),"
            + town + " VARCHAR(225),"
            + barangay + " VARCHAR(225),"
            + breedable_id + " INTEGER(10),"
            + is_synced + " TEXT)";

    private static final String Farm_AnimalTypes = "CREATE TABLE " + farm_animaltypes + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + farm_id + " INTEGER(10),"
            + animaltype_id + " INTEGER(10),"
            + is_synced + " TEXT)";

    private static final String Farm_Users = "CREATE TABLE " + farm_users + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + user_id + " INTEGER(10),"
            + farm_id + " INTEGER(10),"
            + user_type + " VARCHAR(225),"
            + is_synced + " TEXT)";

    private static final String Groupings = "CREATE TABLE " + groupings + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + registryid + " VARCHAR(225),"
            + mother_id + " INTEGER(10),"
            + father_id + " INTEGER(10),"
            + breed_id + " INTEGER(10),"
            + members + " TINYINT(1),"
            + is_synced + " TEXT)";

    private static final String Grouping_Members = "CREATE TABLE " + grouping_members + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + grouping_id + " INTEGER(10),"
            + animal_id + " INTEGER(10),"
            + created_at + " TIMESTAMP,"
            + updated_at + " TIMESTAMP,"
            + is_synced + " TEXT)";

    private static final String Grouping_Properties = "CREATE TABLE " + grouping_properties + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + grouping_id + " INTEGER(10),"
            + property_id + " INTEGER(10),"
            + value + " VARCHAR(225),"
            + created_at + " TIMESTAMP,"
            + updated_at + " TIMESTAMP,"
            + is_synced + " TEXT)";

    private static final String Migrations = "CREATE TABLE " + migrations + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + migration + " VARCHAR(225),"
            + batch + " INTEGER(11),"
            + is_synced + " TEXT)";

    private static final String Mortalities = "CREATE TABLE " + mortalities + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + animal_id + " INTEGER(10),"
            + animaltype_id + " INTEGER(10),"
            + breed_id + " INTEGER(10),"
            + datedied + " DATE,"
            + cause + " VARCHAR(225),"
            + age + " VARCHAR(225),"
            + created_at + " TIMESTAMP,"
            + updated_at + " TIMESTAMP,"
            + is_synced + " TEXT)";

    private static final String Password_Resets = "CREATE TABLE " + password_resets + "("
            + email + " VARCHAR(225),"
            + token + " VARCHAR(225),"
            + created_at + " TIMESTAMP,"
            + is_synced + " TEXT)";

    private static final String Properties = "CREATE TABLE " + properties + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + name + " VARCHAR(225),"
            + fname + " VARCHAR(225),"
            + description + " TEXT,"
            + is_synced + " TEXT)";

    private static final String Removed_Animals = "CREATE TABLE " + removed_animals + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + animal_id + " INTEGER(10),"
            + animaltype_id + " INTEGER(10),"
            + breed_id + " INTEGER(10),"
            + dateremoved + " DATE,"
            + reason + " VARCHAR(225),"
            + age + " VARCHAR(225),"
            + created_at + " TIMESTAMP,"
            + updated_at + " TIMESTAMP,"
            + is_synced + " TEXT)";

    private static final String Roles = "CREATE TABLE " + roles + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + title + " VARCHAR(225),"
            + is_synced + " TEXT)";

    private static final String Role_User = "CREATE TABLE " + role_user + "("
            + user_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + role_id + " INTEGER(10),"
            + is_synced + " TEXT)";

    private static final String Sales = "CREATE TABLE " + sales + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + animal_id + " INTEGER(10),"
            + animaltype_id + " INTEGER(10),"
            + breed_id + " INTEGER(10),"
            + datesold + " DATE,"
            + weight + " VARCHAR(225),"
            + price + " VARCHAR(225),"
            + age + " VARCHAR(225),"
            + created_at + " TIMESTAMP,"
            + updated_at + " TIMESTAMP,"
            + is_synced + " TEXT)";

    private static final String Users = "CREATE TABLE " + users + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + name + " VARCHAR(225),"
            + email + " VARCHAR(225),"
            + phone + " VARCHAR(225),"
            + lastseen + " DATE,"
            + photo + " VARCHAR(225),"
            + isadmin + " TINYINT(1),"
            + farmable_id + " INTEGER(10),"
            + deleted_at + " TIMESTAMP,"
            + remember_token + " VARCHAR(100),"
            + created_at + " TIMESTAMP,"
            + updated_at + " TIMESTAMP,"
            + is_synced + " TEXT)";

    private static final String Weight_Collections = "CREATE TABLE " + weight_collections + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + animal_id + " INTEGER(10),"
            + weight + " DOUBLE,"
            + created_at + " TIMESTAMP,"
            + updated_at + " TIMESTAMP,"
            + is_synced + " TEXT)";

    //------------------------------------------------------------------------------------------

    private static final String CREATE_SOW_LITTER_TABLE = "CREATE TABLE " + sow_litter_table + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + sow_registration_id + " TEXT,"
            + boar_registration_id + " TEXT,"
            + date_bred + " TEXT,"
            + expected_date_farrow + " TEXT,"
            + pig_weaningdate + " TEXT,"
            + parity + " TEXT,"
            + total_litter_size_born + " TEXT,"
            + total_litter_size_born_alive + " TEXT,"
            + number_weaned + " TEXT,"
            + average_birth_weight + " TEXT,"
            + number_of_males + " TEXT,"
            + number_of_females + " TEXT,"
            + average_weaning_weight + " TEXT,"
            + number_stillborn + " TEXT,"
            + number_mummified + " TEXT,"
            + abnormalities + " TEXT,"
            + is_synced + " TEXT)";

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
        db.execSQL(CREATE_SOW_LITTER_TABLE);

//-----------------------------------------------

        db.execSQL(Administrators);
        db.execSQL(Admins);
        db.execSQL(Animals);
        db.execSQL(Animal_Properties);
        db.execSQL(Animal_Types);
        db.execSQL(Breeds);
        db.execSQL(Farms);
        db.execSQL(Farm_AnimalTypes);
        db.execSQL(Farm_Users);
        db.execSQL(Groupings);
        db.execSQL(Grouping_Members);
        db.execSQL(Grouping_Properties);
        db.execSQL(Migrations);
        db.execSQL(Mortalities);
        db.execSQL(Password_Resets);
        db.execSQL(Properties);
        db.execSQL(Removed_Animals);
        db.execSQL(Roles);
        db.execSQL(Role_User);
        db.execSQL(Sales);
        db.execSQL(Users);
        db.execSQL(Weight_Collections);

        populateFarms(db);
        populateAnimalTypes(db);
        populateBreeds(db);
        populateProperties(db);
        //populateUsers();
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
        db.execSQL("DROP TABLE IF EXISTS "  + sow_litter_table);

        db.execSQL("DROP TABLE IF EXISTS " + administrators);
        db.execSQL("DROP TABLE IF EXISTS " + admins);
        db.execSQL("DROP TABLE IF EXISTS " + animals);
        db.execSQL("DROP TABLE IF EXISTS " + animal_properties);
        db.execSQL("DROP TABLE IF EXISTS " + animal_types);
        db.execSQL("DROP TABLE IF EXISTS " + breeds);
        db.execSQL("DROP TABLE IF EXISTS " + farms);
        db.execSQL("DROP TABLE IF EXISTS " + farm_animaltypes);
        db.execSQL("DROP TABLE IF EXISTS " + farm_users);
        db.execSQL("DROP TABLE IF EXISTS " + groupings);
        db.execSQL("DROP TABLE IF EXISTS " + grouping_members);
        db.execSQL("DROP TABLE IF EXISTS " + grouping_properties);
        db.execSQL("DROP TABLE IF EXISTS " + migrations);
        db.execSQL("DROP TABLE IF EXISTS " + mortalities);
        db.execSQL("DROP TABLE IF EXISTS " + password_resets);
        db.execSQL("DROP TABLE IF EXISTS " + properties);
        db.execSQL("DROP TABLE IF EXISTS " + removed_animals);
        db.execSQL("DROP TABLE IF EXISTS " + roles);
        db.execSQL("DROP TABLE IF EXISTS " + role_user);
        db.execSQL("DROP TABLE IF EXISTS " + sales);
        db.execSQL("DROP TABLE IF EXISTS " + users);
        db.execSQL("DROP TABLE IF EXISTS " + weight_collections);

        onCreate(db);
    }

    public boolean populateUsers(){
        long result;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(name, "BAI");
        content.put(email, "baibppig@gmail.com");
        content.put(farmable_id, 1);
        result = db.insert(users, null, content);

        content.put(name, "BSU");
        content.put(email, "benguetpig@gmail.com");
        content.put(farmable_id, 2);
        result = db.insert(users, null, content);

        content.put(name, "ESSU");
        content.put(email, "siniranganpig@gmail.com");
        content.put(farmable_id, 3);
        result = db.insert(users, null, content);

        content.put(name, "IAS");
        content.put(email, "berkjalapig@gmail.com");
        content.put(farmable_id, 4);
        result = db.insert(users, null, content);

        content.put(name, "ISU");
        content.put(email, "isabelaisupig@gmail.com");
        content.put(farmable_id, 5);
        result = db.insert(users, null, content);

        content.put(name, "KSU");
        content.put(email, "yookahpig@gmail.com");
        content.put(farmable_id, 6);
        result = db.insert(users, null, content);

        content.put(name, "MSC");
        content.put(email, "marindukepig@gmail.com");
        content.put(farmable_id, 7);
        result = db.insert(users, null, content);

        content.put(name, "NVSU");
        content.put(email, "nuevaviscayapig@gmail.com");
        content.put(farmable_id, 8);
        result = db.insert(users, null, content);

        content.put(name, "MSC2");
        content.put(email, "marmscpig@gmail.com");
        content.put(farmable_id, 9);
        result = db.insert(users, null, content);

        return result != -1;
    }

    public boolean populateFarms(SQLiteDatabase db){
        long result;
//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(name, "BAI-NSPRDC");
        content.put(code, "QUEBAI");
        //content.put(region, "");
        content.put(province, "Quezon");
        //content.put(town, "");
        //content.put(barangay, "");
        content.put(breedable_id, 1);
        result = db.insert(farms, null, content);

        content.put(name, "Benguet State University");
        content.put(code, "BENBSU");
        //content.put(region, "");
        content.put(province, "Benguet");
        //content.put(town, "");
        //content.put(barangay, "");
        content.put(breedable_id, 2);
        result = db.insert(farms, null, content);

        content.put(name, "Eastern Samar State University");
        content.put(code, "EASESSU");
        content.put(region, "8");
        content.put(province, "Eastern Samar");
        content.put(town, "Borongan");
        //content.put(barangay, "");
        content.put(breedable_id, 3);
        result = db.insert(farms, null, content);

        content.put(name, "UPLB Institute of Animal Science");
        content.put(code, "LAGIAS");
        //content.put(region, "8");
        content.put(province, "Laguna");
        //content.put(town, "Borongan");
        //content.put(barangay, "");
        content.put(breedable_id, 4);
        result = db.insert(farms, null, content);

        content.put(name, "Isabela State University");
        content.put(code, "ISAISU");
        //content.put(region, "8");
        content.put(province, "Isabela");
        //content.put(town, "Borongan");
        //content.put(barangay, "");
        content.put(breedable_id, 5);
        result = db.insert(farms, null, content);

        content.put(name, "Kalinga State University");
        content.put(code, "KAKSU");
        //content.put(region, "8");
        content.put(province, "Kalinga");
        //content.put(town, "Borongan");
        //content.put(barangay, "");
        content.put(breedable_id, 6);
        result = db.insert(farms, null, content);

        content.put(name, "Marinduque State College");
        content.put(code, "MARMSC");
        //content.put(region, "8");
        content.put(province, "Marinduque");
        //content.put(town, "Borongan");
        //content.put(barangay, "");
        content.put(breedable_id, 7);
        result = db.insert(farms, null, content);

        content.put(name, "Nueva Vizcaya State University");
        content.put(code, "NUVNVSU");
        //content.put(region, "8");
        content.put(province, "Nueva Vizcaya");
        //content.put(town, "Borongan");
        //content.put(barangay, "");
        content.put(breedable_id, 8);
        result = db.insert(farms, null, content);

        content.put(name, "Marinduque State College");
        content.put(code, "MARMSC2");
        //content.put(region, "8");
        content.put(province, "Marinduque");
        content.put(town, "Torrijos");
        //content.put(barangay, "");
        content.put(breedable_id, 9);
        result = db.insert(farms, null, content);

        return result != -1;
    }

    public boolean populateBreeds(SQLiteDatabase db){
        long result;
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(breed, "BP");
        content.put(animaltype_id, 3);
        result = db.insert(breeds, null, content);

        content.put(breed, "Benguet");
        content.put(animaltype_id, 3);
        result = db.insert(breeds, null, content);

        content.put(breed, "Sinirangan");
        content.put(animaltype_id, 3);
        result = db.insert(breeds, null, content);

        content.put(breed, "Berkjala");
        content.put(animaltype_id, 3);
        result = db.insert(breeds, null, content);

        content.put(breed, "Isabela");
        content.put(animaltype_id, 3);
        result = db.insert(breeds, null, content);

        content.put(breed, "Yookah");
        content.put(animaltype_id, 3);
        result = db.insert(breeds, null, content);

        content.put(breed, "Marinduke");
        content.put(animaltype_id, 3);
        result = db.insert(breeds, null, content);

        content.put(breed, "NuevaVizcaya");
        content.put(animaltype_id, 3);
        result = db.insert(breeds, null, content);

        content.put(breed, "MarindukePig");
        content.put(animaltype_id, 3);
        result = db.insert(breeds, null, content);

        return result != -1;
    }

    public boolean populateAnimalTypes(SQLiteDatabase db){
        long result;
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(species, "Chicken");
        result = db.insert(animal_types, null, content);

        content.put(species, "Duck");
        result = db.insert(animal_types, null, content);

        content.put(species, "Pig");
        result = db.insert(animal_types, null, content);

        return result != -1;
    }

    public boolean populateProperties(SQLiteDatabase db){
        long result;
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(name,"Earnotch");
        content.put(fname,"earnotch");
        content.put(description,"Earnotch or eartag number of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Sex");
        content.put(fname,"sex");
        content.put(description,"Sex of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Date Farrowed");
        content.put(fname,"date_farrowed");
        content.put(description,"Date when pig was born");
        result = db.insert(properties, null, content);

        content.put(name,"Registration ID");
        content.put(fname,"registration_id");
        content.put(description,"Complete registration ID of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Birth Weight");
        content.put(fname,"birth_weight");
        content.put(description,"Weight of the pig when it was born");
        result = db.insert(properties, null, content);

        content.put(name,"Date Weaned");
        content.put(fname,"date_weaned");
        content.put(description,"Date when pig was weaned");
        result = db.insert(properties, null, content);

        content.put(name,"Weaning Weight");
        content.put(fname,"weaning_weight");
        content.put(description,"Weight of the pig when it was weaned");
        result = db.insert(properties, null, content);

        content.put(name,"Dam");
        content.put(fname,"dam");
        content.put(description,"Mother of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Sire");
        content.put(fname,"sire");
        content.put(description,"Father of the pig");
        result = db.insert(properties, null, content);

        //-----GROSS MORPHOLOGY-----//

        content.put(name,"Date Collected for Gross Morphology");
        content.put(fname,"date_collected_gross_morpho");
        content.put(description,"Date when gross morphology was collected");
        result = db.insert(properties, null, content);

        content.put(name,"Hair Type");
        content.put(fname,"hair_type");
        content.put(description,"Hair length classification of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Hair Length");
        content.put(fname,"hair_length");
        content.put(description,"Mother of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Coat Color");
        content.put(fname,"coat_color");
        content.put(description,"Coat color of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Color Pattern");
        content.put(fname,"color_pattern");
        content.put(description,"Color pattern of the coat of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Head Shape");
        content.put(fname,"head_shape");
        content.put(description,"Head shape of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Skin Type");
        content.put(fname,"skin_type");
        content.put(description,"Skin Type of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Ear Type");
        content.put(fname,"ear_type");
        content.put(description,"Ear type of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Tail Type");
        content.put(fname,"tail_type");
        content.put(description,"Tail type of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Backline");
        content.put(fname,"backline");
        content.put(description,"Backline of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Other Marks");
        content.put(fname,"other_marks");
        content.put(description,"Other marks that can identify the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Date Collected for Morphometric Characteristics");
        content.put(fname,"date_collected_morpho_chars");
        content.put(description,"Date when morphometric characteristics were collected");
        result = db.insert(properties, null, content);

        content.put(name,"Ear Length");
        content.put(fname,"ear_length");
        content.put(description,"Ear length of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Head Length");
        content.put(fname,"head_length");
        content.put(description,"Head length of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Snout Length");
        content.put(fname,"snout_length");
        content.put(description,"Snout length of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Body Length");
        content.put(fname,"body_length");
        content.put(description,"Body length of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Heart Girth");
        content.put(fname,"heart_girth");
        content.put(description,"Heart girth of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Pelvic Width");
        content.put(fname,"pelvic_width");
        content.put(description,"Pelvic width of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Tail Length");
        content.put(fname,"tail_length");
        content.put(description,"Tail length of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Height at Withers");
        content.put(fname,"height_at_withers");
        content.put(description,"Height at withers of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Number of Normal Teats");
        content.put(fname,"number_normal_teats");
        content.put(description,"Number of the normal teats of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Ponderal Index");
        content.put(fname,"ponderal_index");
        content.put(description,"Computed ponderal index of the pig");
        result = db.insert(properties, null, content);

        content.put(name,"Body Weight at 45 Days");
        content.put(fname,"body_weight_at_45_days");
        content.put(description,"Body weight of the pig at 45 days");
        result = db.insert(properties, null, content);

        content.put(name,"Body Weight at 60 Days");
        content.put(fname,"body_weight_at_60_days");
        content.put(description,"Body weight of the pig at 60 days");
        result = db.insert(properties, null, content);

        content.put(name,"Body Weight at 90 Days");
        content.put(fname,"body_weight_at_90_days");
        content.put(description,"Body weight of the pig at 90 days");
        result = db.insert(properties, null, content);

        content.put(name,"Body Weight at 150 Days");
        content.put(fname,"body_weight_at_150_days");
        content.put(description,"Body weight of the pig at 150 days");
        result = db.insert(properties, null, content);

        content.put(name,"Body Weight at 180 Days");
        content.put(fname,"body_weight_at_180_days");
        content.put(description,"Body weight of the pig at 180 days");
        result = db.insert(properties, null, content);

        content.put(name,"Date Colleceted at 45 Days");
        content.put(fname,"date_collected_at_45_days");
        content.put(description,"Date when body weight at 45 days was collected");
        result = db.insert(properties, null, content);

        content.put(name,"Date Colleceted at 60 Days");
        content.put(fname,"date_collected_at_60_days");
        content.put(description,"Date when body weight at 60 days was collected");
        result = db.insert(properties, null, content);

        content.put(name,"Date Colleceted at 90 Days");
        content.put(fname,"date_collected_at_90_days");
        content.put(description,"Date when body weight at 90 days was collected");
        result = db.insert(properties, null, content);

        content.put(name,"Date Colleceted at 150 Days");
        content.put(fname,"date_collected_at_150_days");
        content.put(description,"Date when body weight at 150 days was collected");
        result = db.insert(properties, null, content);

        content.put(name,"Date Colleceted at 180 Days");
        content.put(fname,"date_collected_at_180_days");
        content.put(description,"Date when body weight at 180 days was collected");
        result = db.insert(properties, null, content);

        content.put(name,"Date Bred");
        content.put(fname,"date_bred");
        content.put(description,"Date when two pigs were bred");
        result = db.insert(properties, null, content);

        content.put(name,"Expected Date of Farrowing");
        content.put(fname,"expected_date_of_farrowing");
        content.put(description,"Expected date of farrowing");
        result = db.insert(properties, null, content);

        content.put(name,"Date Aborted");
        content.put(fname,"date_aborted");
        content.put(description,"Date when litters were aborted");
        result = db.insert(properties, null, content);

        content.put(name,"Number Stillborn");
        content.put(fname,"number_stillborn");
        content.put(description,"Number of stillbirths");
        result = db.insert(properties, null, content);

        content.put(name,"Number Mummified");
        content.put(fname,"number_mummified");
        content.put(description,"Number of mummified offspring");
        result = db.insert(properties, null, content);

        content.put(name,"Abnormalities");
        content.put(fname,"abnormalities");
        content.put(description,"Abnomalities of the litter");
        result = db.insert(properties, null, content);

        content.put(name,"Parity");
        content.put(fname,"parity");
        content.put(description,"Parity of the sow");
        result = db.insert(properties, null, content);

        content.put(name,"Total Littersize Born");
        content.put(fname,"lsb");
        content.put(description,"Total litters born");
        result = db.insert(properties, null, content);

        content.put(name,"Total Littersize Born Alive");
        content.put(fname,"lsba");
        content.put(description,"Total litters born alive");
        result = db.insert(properties, null, content);

        content.put(name,"Number of Males");
        content.put(fname,"number_males");
        content.put(description,"Number of males born");
        result = db.insert(properties, null, content);

        content.put(name,"Number of Females");
        content.put(fname,"number_females");
        content.put(description,"Number of females born");
        result = db.insert(properties, null, content);

        content.put(name,"Sex Ratio");
        content.put(fname,"sex_ratio");
        content.put(description,"Sex ratio of the litters");
        result = db.insert(properties, null, content);

        content.put(name,"Weighing Option");
        content.put(fname,"weighing_option");
        content.put(description,"Weighing option of the farm");
        result = db.insert(properties, null, content);

        content.put(name,"Litter Birth Weight");
        content.put(fname,"litter_birth_weight");
        content.put(description,"Litter birth weight");
        result = db.insert(properties, null, content);

        content.put(name,"Average Birth Weight");
        content.put(fname,"average_birth_weight");
        content.put(description,"Average birth weight");
        result = db.insert(properties, null, content);

        content.put(name,"Number Weaned");
        content.put(fname,"number_weaned");
        content.put(description,"Number of pigs weaned");
        result = db.insert(properties, null, content);

        content.put(name,"Average Weaning Weight");
        content.put(fname,"average_weaning_weight");
        content.put(description,"Average weaning weight");
        result = db.insert(properties, null, content);

        content.put(name,"Preweaning Mortality");
        content.put(fname,"preweaning_mortality");
        content.put(description,"Number of pigs which died before weaning");
        result = db.insert(properties, null, content);

        content.put(name,"Status");
        content.put(fname,"status");
        content.put(description,"Any status used in the information system");
        result = db.insert(properties, null, content);

        content.put(name,"Frequency");
        content.put(fname,"frequency");
        content.put(description,"Number of times sow or boar was used in breeding");
        result = db.insert(properties, null, content);

        content.put(name,"Litter Weaning Weight");
        content.put(fname,"litter_weaning_weight");
        content.put(description,"Litter weaning weight");
        result = db.insert(properties, null, content);

        return result != -1;
    }

    public Cursor getEmailInLocalDb(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+users+" WHERE email = "+email;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public int getNoOfMales(String motherId, String fatherId){
        int numOfMales = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT substr(c.registryid, -7, 1) FROM groupings a, grouping_members b, animals c " +
            "WHERE a.id=grouping_id AND b.animal_id=c.id AND a.mother_id = ? AND a.father_id = ? ";
        String[] whereArgs = new String[]{motherId, fatherId};
        Cursor cursor = db.rawQuery(query, whereArgs);
        while(cursor.moveToNext()){
            if(cursor.getString(0).equals("M"))
                numOfMales++;
        }
        return numOfMales;
    }

    public int getNoOfFemales(String motherId, String fatherId){
        int numOfMales = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT substr(c.registryid, -7, 1) FROM groupings a, grouping_members b, animals c " +
                "WHERE a.id=grouping_id AND b.animal_id=c.id AND a.mother_id = ? AND a.father_id = ? ";
        String[] whereArgs = new String[]{motherId, fatherId};
        Cursor cursor = db.rawQuery(query, whereArgs);
        while(cursor.moveToNext()){
            if(cursor.getString(0).equals("F"))
                numOfMales++;
        }
        return numOfMales;
    }

    public long getAveBirthWeight(String motherId, String fatherId){
        long sumOfBirthWeights = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT d.value FROM groupings a, grouping_members b, animal_properties d WHERE a.id = b.grouping_id AND d.animal_id = b.animal_id AND d.property_id = 5 AND a.mother_id =? AND a.father_id = ?";
        String[] whereArgs = new String[]{motherId, fatherId};
        Cursor cursor = db.rawQuery(query, whereArgs);
        while(cursor.moveToNext()){
            sumOfBirthWeights += cursor.getLong(0);
        }
        return sumOfBirthWeights/cursor.getCount();
    }

    public long noOfPigsWeaned(String motherId, String fatherId){
        long noOfPigsWeaned = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT d.value FROM groupings a, grouping_members b, animal_properties d WHERE a.id = b.grouping_id AND d.animal_id = b.animal_id AND d.property_id = 7 AND a.mother_id =? AND a.father_id = ?";
        String[] whereArgs = new String[]{motherId, fatherId};
        Cursor cursor = db.rawQuery(query, whereArgs);
        if(cursor.moveToFirst()){
            noOfPigsWeaned = cursor.getCount();
        }
        return noOfPigsWeaned;
    }

    public long getAveWeaningWeight(String motherId, String fatherId){
        long sumOfWeaningWeights = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT d.value FROM groupings a, grouping_members b, animal_properties d WHERE a.id = b.grouping_id AND d.animal_id = b.animal_id AND d.property_id = 7 AND a.mother_id =? AND a.father_id = ?";
        String[] whereArgs = new String[]{motherId, fatherId};
        Cursor cursor = db.rawQuery(query, whereArgs);
        while(cursor.moveToNext()){
            sumOfWeaningWeights += cursor.getLong(0);
        }
        return sumOfWeaningWeights/cursor.getCount();
    }

    public long getLitterWeaningWeight(String motherId, String fatherId){
        long sumOfWeaningWeights = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT d.value FROM groupings a, grouping_members b, animal_properties d WHERE a.id = b.grouping_id AND d.animal_id = b.animal_id AND d.property_id = 7 AND a.mother_id =? AND a.father_id = ?";
        String[] whereArgs = new String[]{motherId, fatherId};
        Cursor cursor = db.rawQuery(query, whereArgs);
        while(cursor.moveToNext()){
            sumOfWeaningWeights += cursor.getLong(0);
        }
        return sumOfWeaningWeights;
    }

    public long getLitterBirthWeight(String motherId, String fatherId){
        long sumOfBirthWeights = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT d.value FROM groupings a, grouping_members b, animal_properties d WHERE a.id = b.grouping_id AND d.animal_id = b.animal_id AND d.property_id = 5 AND a.mother_id =? AND a.father_id = ?";
        String[] whereArgs = new String[]{motherId, fatherId};
        Cursor cursor = db.rawQuery(query, whereArgs);
        while(cursor.moveToNext()){
            sumOfBirthWeights += cursor.getLong(0);
        }
        return sumOfBirthWeights;
    }

    public boolean addSowLitterRecord(String sowid, String boarid, String datebred, String datefarrow, String weandate,
                                 String pig_parity, String litterborn, String litteralive, String numWean, String aveBirth,
                                 String numMales, String numFemales, String aveWean, String stillborn, String mummified, String abnormal,
                                 String isSynced){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(sow_registration_id, sowid);
        contentValues.put(boar_registration_id, boarid);
        contentValues.put(date_bred, datebred);
        contentValues.put(expected_date_farrow, datefarrow);
        contentValues.put(pig_weaningdate, weandate);
        contentValues.put(parity, pig_parity);
        contentValues.put(total_litter_size_born, litterborn);
        contentValues.put(total_litter_size_born_alive, litteralive);
        contentValues.put(number_weaned, numWean);
        contentValues.put(average_birth_weight, aveBirth);
        contentValues.put(number_of_males, numMales);
        contentValues.put(number_of_females, numFemales);
        contentValues.put(average_weaning_weight, aveWean);
        contentValues.put(number_stillborn, stillborn);
        contentValues.put(number_mummified, mummified);
        contentValues.put(abnormalities, abnormal);
        contentValues.put(is_synced, isSynced);

        long result = db.insert(sow_litter_table, null, contentValues);

        return result != -1;
    }

    public boolean isBlankOrNull(String text){
        if(text == null || text.equals(""))
            return  true;
        return false;
    }

    public boolean checkIfEarnotchAlreadyExists(String earnotch){
        SQLiteDatabase db = this.getReadableDatabase();
        String columns[] = {"registryid"};
        String whereClause = "animaltype_id = ? AND breed_id = ?";
        String[] whereArgs = new String[]{"3", Integer.toString(MyApplication.id)};
        Cursor data = db.query(animals, columns, whereClause, whereArgs, null, null, null);

        while(data.moveToNext()){
            String text = data.getString(0);
            if(earnotch.equals(text.substring(text.length() - 6)))
                return true;
        }
        return false;
    }

    public boolean addNewPigData(String classification, String animalearnotch, String sex, String birthdate, String weaningdate,
                                 String birthweight, String weaningweight, String motherpedigree, String fatherpedigree, String sexratio,
                                 String littersizebornalive, String agefirstmating, String ageweaning, String regId, String isSynced) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        addToAnimalDB(regId, classification, "false");
        String animalId = getAnimalId(regId);
        int animalIdInt = Integer.parseInt(animalId);
        insertOrReplaceInAnimalPropertyDB(1, animalIdInt, animalearnotch, "false");
        insertOrReplaceInAnimalPropertyDB(2, animalIdInt, sex, "false");
        insertOrReplaceInAnimalPropertyDB(3, animalIdInt, changeToNotSpecifiedIfNull(birthdate), "false");
        insertOrReplaceInAnimalPropertyDB(4, animalIdInt, regId, "false");
        insertOrReplaceInAnimalPropertyDB(5, animalIdInt, changeToBlankIfNull(birthweight), "false");
        insertOrReplaceInAnimalPropertyDB(6, animalIdInt, changeToNotSpecifiedIfNull(weaningdate), "false");

        if(!isBlankOrNull(weaningweight))
            insertOrReplaceInAnimalPropertyDB(7, animalIdInt, changeToBlankIfNull(weaningweight), "false");
        addDamAndSire(motherpedigree, fatherpedigree, animalId, birthdate, weaningdate, "false");

        if(birthdate.equals("")){
            birthdate = "Not specified";
        }else if(!birthdate.equals("Not specified")){
            Date firstDate = null;
            try {
                firstDate = sdf.parse(birthdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date date45 = addDays(firstDate, 45);
            Date date60 = addDays(firstDate, 60);
            Date date90 = addDays(firstDate, 90);
            Date date150 = addDays(firstDate, 150);
            Date date180 = addDays(firstDate, 180);

            String Date45 = sdf.format(date45);
            String Date60 = sdf.format(date60);
            String Date90 = sdf.format(date90);
            String Date150 = sdf.format(date150);
            String Date180 = sdf.format(date180);

            insertOrReplaceInAnimalPropertyDB(37, animalIdInt, Date45, "false");
            insertOrReplaceInAnimalPropertyDB(38, animalIdInt, Date60, "false");
            insertOrReplaceInAnimalPropertyDB(39, animalIdInt, Date90, "false");
            insertOrReplaceInAnimalPropertyDB(40, animalIdInt, Date150, "false");
            insertOrReplaceInAnimalPropertyDB(41, animalIdInt, Date180, "false");
        }

        return true;
    }

    public boolean addGrossMorphologyData(String regId, String datecollected, String hairtype, String hairlength, String coatcolor, String colorpattern,
                                          String headshape, String skintype, String eartype, String tailtype, String back_line, String othermarks, String isSynced){

        String animalId = getAnimalId(regId);
        int animalIdInt = Integer.parseInt(animalId);
        insertOrReplaceInAnimalPropertyDB(10, animalIdInt, datecollected, "false");
        insertOrReplaceInAnimalPropertyDB(11, animalIdInt, hairtype, "false");
        insertOrReplaceInAnimalPropertyDB(12, animalIdInt, hairlength, "false");
        insertOrReplaceInAnimalPropertyDB(13, animalIdInt, coatcolor, "false");
        insertOrReplaceInAnimalPropertyDB(14, animalIdInt, colorpattern, "false");
        insertOrReplaceInAnimalPropertyDB(15, animalIdInt, headshape, "false");
        insertOrReplaceInAnimalPropertyDB(16, animalIdInt, skintype, "false");
        insertOrReplaceInAnimalPropertyDB(17, animalIdInt, eartype, "false");
        insertOrReplaceInAnimalPropertyDB(18, animalIdInt, tailtype, "false");
        insertOrReplaceInAnimalPropertyDB(19, animalIdInt, back_line, "false");
        insertOrReplaceInAnimalPropertyDB(20, animalIdInt, othermarks, "false");

        updateGrossMorpho(regId);

        return true;
    }

    public boolean addMorphCharData(String regId, String datecollected, String earlength, String headlength,
                                    String snoutlength, String bodylength, String heartgirth, String pelvicwidth,
                                    String taillength, String heightwithers, String normalteats, String isSynced){

        String animalId = getAnimalId(regId);
        int animalIdInt = Integer.parseInt(animalId);
        insertOrReplaceInAnimalPropertyDB(21, animalIdInt, datecollected, "false");
        insertOrReplaceInAnimalPropertyDB(22, animalIdInt, earlength, "false");
        insertOrReplaceInAnimalPropertyDB(23, animalIdInt, headlength, "false");
        insertOrReplaceInAnimalPropertyDB(24, animalIdInt, snoutlength, "false");
        insertOrReplaceInAnimalPropertyDB(25, animalIdInt, bodylength, "false");
        insertOrReplaceInAnimalPropertyDB(26, animalIdInt, heartgirth, "false");
        insertOrReplaceInAnimalPropertyDB(27, animalIdInt, pelvicwidth, "false");
        insertOrReplaceInAnimalPropertyDB(28, animalIdInt, taillength, "false");
        insertOrReplaceInAnimalPropertyDB(29, animalIdInt, heightwithers, "false");
        insertOrReplaceInAnimalPropertyDB(30, animalIdInt, normalteats, "false");

        updateMorphChar(regId);

        return true;
    }

    public boolean addWeightRecords(String regId, String datecollected45, String datecollected60, String datecollected90, String datecollected150,
                                    String datecollected180, String weight45, String weight60, String weight90, String weight150, String weight180, String isSynced){

        String animalId = getAnimalId(regId);
        int animalIdInt = Integer.parseInt(animalId);
        insertOrReplaceInAnimalPropertyDB(32, animalIdInt, weight45, "false");
        insertOrReplaceInAnimalPropertyDB(33, animalIdInt, weight60, "false");
        insertOrReplaceInAnimalPropertyDB(34, animalIdInt, weight90, "false");
        insertOrReplaceInAnimalPropertyDB(35, animalIdInt, weight150, "false");
        insertOrReplaceInAnimalPropertyDB(36, animalIdInt, weight180, "false");
        insertOrReplaceInAnimalPropertyDB(37, animalIdInt, datecollected45, "false");
        insertOrReplaceInAnimalPropertyDB(38, animalIdInt, datecollected60, "false");
        insertOrReplaceInAnimalPropertyDB(39, animalIdInt, datecollected90, "false");
        insertOrReplaceInAnimalPropertyDB(40, animalIdInt, datecollected150, "false");
        insertOrReplaceInAnimalPropertyDB(41, animalIdInt, datecollected180, "false");

        updateWeightRecords(regId);

        return true;
    }

    public boolean addBreedingRecord(String sowRegistryId, String boarRegistryId, String dateBred, String isSynced){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = java.util.Calendar.getInstance().getTime();

        Date edf = null;
        String sowIdFromAnimal = getAnimalId(sowRegistryId);
        String boarIdFromAnimal = getAnimalId(boarRegistryId);
        addToGroupingsDB(sowRegistryId, sowIdFromAnimal, boarIdFromAnimal, "0", "false");
        String idFromGroupings = getGroupingId(sowIdFromAnimal, boarIdFromAnimal);
        int idFromGroupingsInt = Integer.parseInt(idFromGroupings);

        if(dateBred.equals("")) dateBred = sdf.format(date);

        addToGroupingsPropertyDB(42, idFromGroupings, dateBred, "false");

        try {
            edf = sdf.parse(dateBred);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dateFarrowed = addDays(edf, 114);
        String expectedDateFarrow = sdf.format(dateFarrowed);

        insertOrReplaceInGroupingsPropertyDB(43, idFromGroupingsInt, expectedDateFarrow, "false");
        insertOrReplaceInGroupingsPropertyDB(60, idFromGroupingsInt, "Bred", "false");

        return true;
    }

    public boolean addToMortalitiesDB(String regId, String dateDied, String causeofDeath, String pigAge, String isSynced){
        String animalId = getAnimalId(regId);
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(animal_id, animalId);
        contentValues.put(animaltype_id, 3);
        contentValues.put(breed_id, MyApplication.id);
        contentValues.put(datedied, dateDied);
        contentValues.put(cause, causeofDeath);
        contentValues.put(age, pigAge);
        contentValues.put(is_synced, isSynced);

        updateStatus(regId, "dead");

        long result = db.insert(mortalities,null,contentValues);
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

    public boolean addToSalesDB(String regId, String dateSold, String pigWeight, String pigPrice, String pigAge, String isSynced){
        String animalId = getAnimalId(regId);
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(animal_id, animalId);
        contentValues.put(animaltype_id, 3);
        contentValues.put(breed_id, MyApplication.id);
        contentValues.put(datesold, dateSold);
        contentValues.put(weight, pigWeight);
        contentValues.put(price, pigPrice);
        contentValues.put(age, pigAge);
        contentValues.put(is_synced, isSynced);

        updateStatus(regId, "sold");

        long result = db.insert(sales,null,contentValues);
        if(result == -1){
            Log.d("addSalesData", "Error in adding sales to local");
            return false;
        }
        else{
            boolean success = setIsSyncedFromPigTableToDelete(regId);
            if(success) Log.d("addSalesData", "is_synced changed to delete");
            else Log.d("addSalesData", "Error in changing is_synced to delete");
            return true;
        }
    }

    public boolean addToRemovedAnimalsDB(String regId, String dateRemoved, String pigReason, String pigAge, String isSynced){
        String animalId = getAnimalId(regId);
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(animal_id, animalId);
        contentValues.put(animaltype_id, 3);
        contentValues.put(breed_id, MyApplication.id);
        contentValues.put(dateremoved, dateRemoved);
        contentValues.put(reason, pigReason);
        contentValues.put(age, pigAge);
        contentValues.put(is_synced, isSynced);

        updateStatus(regId, "removed");

        long result = db.insert(removed_animals,null,contentValues);
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

    private void addDamAndSire(String motherPedigree, String fatherPedigree, String animalId, String birthdate, String weaningdate, String isSynced) {
        int founddam = 0, foundsire = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(is_synced, isSynced);

        if(!(motherPedigree).equals("") && !(fatherPedigree).equals("")){
            if(motherPedigree.length() < 6) motherPedigree = padLeftZeros(motherPedigree, 6);
            if(fatherPedigree.length() < 6) fatherPedigree = padLeftZeros(fatherPedigree, 6);

            if(checkIfPigExistsInAnimalDB("F"+motherPedigree)){
//                contentValues.put(mother_id, getAnimalId(motherPedigree));
                founddam = 1;
            }

            if(checkIfPigExistsInAnimalDB("M"+fatherPedigree)){
//                contentValues.put(father_id, getAnimalId(fatherPedigree));
                foundsire = 1;
            }

            if(founddam != 1) addToAnimalPropertyDB(8, animalId, generateRegistrationId("F", motherPedigree), "false");
            if(foundsire != 1) addToAnimalPropertyDB(9, animalId, generateRegistrationId("M", fatherPedigree), "false");

            if(founddam == 1 || foundsire == 1){
                String sowRegistryId = getAnimalRegistry(motherPedigree);
                String boarRegistryId = getAnimalRegistry(fatherPedigree);
                String boarId = getAnimalId(boarRegistryId);
                String sowId = getAnimalId(sowRegistryId);

                String groupingId = getGroupingId(sowId, boarId);
                int groupingIdInt = Integer.parseInt(groupingId);
                if(groupingId == null || groupingId.equals("")){                //not existing in grouping
                    addToGroupingsDB(sowRegistryId, sowId, boarId, "1", "false");
                    String newlyAddedGroupingId = getGroupingId(sowId, boarId);
                    addToGroupingMembersDB(newlyAddedGroupingId, animalId, "false");
                } else{                                                          //existing in grouping, just update members
                    updateMemberInGroupings(sowId, boarId);
                    addToGroupingMembersDB(groupingId, animalId, "false");
                }

                addToSowLitterDB(sowId, boarId);

                if(birthdate != null) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateBirthdate = sdf.parse(birthdate);
                        String expectedFarrowDate = sdf.format(addDays(dateBirthdate, -114));

                        insertOrReplaceInGroupingsPropertyDB(3, groupingIdInt, birthdate, "false");
                        insertOrReplaceInGroupingsPropertyDB(42, groupingIdInt, expectedFarrowDate, "false");
                        insertOrReplaceInGroupingsPropertyDB(43, groupingIdInt, birthdate, "false");
                        insertOrReplaceInGroupingsPropertyDB(60, groupingIdInt, "Farrowed", "false");
                        insertOrReplaceInGroupingsPropertyDB(6, groupingIdInt, changeToNotSpecifiedIfNull(weaningdate), "false");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void addToSowLitterDB(String sowId, String boarId){
        String lsba, tlsb, mummified, stillborn;
        String groupingId = getGroupingId(sowId, boarId);
        int groupingIdInt = Integer.parseInt(groupingId);
        int countMales = getNoOfMales(sowId, boarId);
        int countFemales = getNoOfFemales(sowId, boarId);
        String sexRatio = countMales+":"+countFemales;

        insertOrReplaceInGroupingsPropertyDB(51, groupingIdInt, Integer.toString(countMales), "false");
        insertOrReplaceInGroupingsPropertyDB(52, groupingIdInt, Integer.toString(countFemales), "false");
        insertOrReplaceInGroupingsPropertyDB(53, groupingIdInt, sexRatio, "false");

        lsba = Integer.toString(countFemales + countMales);

        if(checkGroupingMembers(groupingId)) {
            stillborn = getGroupingProperty(groupingId, 45);
            mummified = getGroupingProperty(groupingId, 46);
            tlsb = getGroupingProperty(groupingId, 49);

            if (tlsb == null || tlsb.equals("")) {
                insertOrReplaceInGroupingsPropertyDB(49, groupingIdInt, stillborn + mummified + lsba, "false");
            } else {
                updateGroupingProperty(groupingId, 49, stillborn + mummified + lsba);
            }

            if (lsba == null || lsba.equals("")) {
                insertOrReplaceInGroupingsPropertyDB(50, groupingIdInt, lsba, "false");
            } else {
                updateGroupingProperty(groupingId, 50, lsba);
            }
        }

        insertOrReplaceInGroupingsPropertyDB(56, groupingIdInt, Long.toString(getAveBirthWeight(sowId, boarId)), "false");
        insertOrReplaceInGroupingsPropertyDB(55, groupingIdInt, Long.toString(getLitterBirthWeight(sowId, boarId)), "false");
        insertOrReplaceInGroupingsPropertyDB(58, groupingIdInt, Long.toString(getAveWeaningWeight(sowId, boarId)), "false");
        insertOrReplaceInGroupingsPropertyDB(62, groupingIdInt, Long.toString(getLitterWeaningWeight(sowId, boarId)), "false");
        insertOrReplaceInGroupingsPropertyDB(57, groupingIdInt, Long.toString(noOfPigsWeaned(sowId, boarId)), "false");
    }

    public boolean updateSowLitter(String sowId, String boarId, String paritySLR, String stillbornSLR, String mummifiedSLR, String abnormalitiesSLR, String isSynced){
        String groupingId = getGroupingId(sowId, boarId);
        int groupingIdInt = Integer.parseInt(groupingId);
        insertOrReplaceInGroupingsPropertyDB(45, groupingIdInt, stillbornSLR, "false");
        insertOrReplaceInGroupingsPropertyDB(46, groupingIdInt, mummifiedSLR, "false");
        insertOrReplaceInGroupingsPropertyDB(47, groupingIdInt, abnormalitiesSLR, "false");
        insertOrReplaceInGroupingsPropertyDB(48, groupingIdInt, paritySLR, "false");
        return true;
    }

    public boolean insertOrReplaceInGroupingsPropertyDB(int propertyId, int groupingId, String value, String isSynced){
        int id = getPrimaryKeyInGroupingsPropertyDB(groupingId, propertyId);
        if(id > -1) updateGroupingsPropertyDB(id, propertyId, Integer.toString(groupingId), value, "update");
        else addToGroupingsPropertyDB(propertyId, Integer.toString(groupingId), value, "false");

        return true;
    }

    public boolean insertOrReplaceInAnimalPropertyDB(int propertyId, int animalId, String value, String isSynced){
        int id = getPrimaryKeyInAnimalPropertyDB(animalId, propertyId);
        if(id > -1) updateAnimalPropertyDB(id, propertyId, Integer.toString(animalId), value, "update");
        else addToAnimalPropertyDB(propertyId, Integer.toString(animalId), value, "false");

        return true;
    }

    private void updateGroupingsPropertyDB(int id, int propertyId, String groupingId, String valueString, String isSynced) {
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String whereClause = "id = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};

        contentValues.put(value, valueString);
        contentValues.put(is_synced, isSynced);

        long result = db.update(grouping_properties, contentValues, whereClause, whereArgs);
    }

    private void updateAnimalPropertyDB(int id, int propertyId, String groupingId, String valueString, String isSynced) {
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String whereClause = "id = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};

        contentValues.put(value, valueString);
        contentValues.put(is_synced, isSynced);

        long result = db.update(animal_properties, contentValues, whereClause, whereArgs);
    }

    public int getPrimaryKeyInGroupingsPropertyDB(int groupingId, int propertyId){
        SQLiteDatabase db = this.getReadableDatabase();
        String columns[] = {"id"};
        String whereClause = "grouping_id = ? AND property_id = ?";
        String[] whereArgs = new String[]{Integer.toString(groupingId), Integer.toString(propertyId)};
        Cursor data = db.query(grouping_properties, columns, whereClause, whereArgs, null, null, null);

        if(data.moveToFirst()) return data.getInt(0);
        else return -1;
    }

    public int getPrimaryKeyInAnimalPropertyDB(int animalId, int propertyId){
        SQLiteDatabase db = this.getReadableDatabase();
        String columns[] = {"id"};
        String whereClause = "animal_id = ? AND property_id = ?";
        String[] whereArgs = new String[]{Integer.toString(animalId), Integer.toString(propertyId)};
        Cursor data = db.query(animal_properties, columns, whereClause, whereArgs, null, null, null);

        if(data.moveToFirst()) return data.getInt(0);
        else return -1;
    }

    public Cursor getSowLitterRecords(String sowId, String boarId){
        String groupingId = getGroupingId(sowId, boarId);

        SQLiteDatabase db = this.getReadableDatabase();
        String columns[] = {"*"};
        String whereClause = "grouping_id = ?";
        String[] whereArgs = new String[]{groupingId};
        Cursor data = db.query(grouping_properties, columns, whereClause, whereArgs, null, null, null);

        return data;
    }

    public static String padLeftZeros(String str, int n) {
        return String.format("%1$" + n + "s", str).replace(' ', '0');
    }

    private String generateRegistrationId(String sex, String animalearnotch) {
        return getFarmCode() + getFarmBreed() +"-"+ sex + animalearnotch;
    }

    private Cursor getAllPigsGivenRegistryId(){
        String id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String columns[] = { "*" };
        String whereClause = "breed_id = ?";
        String[] whereArgs = new String[]{(Integer.toString(MyApplication.id))};
        Cursor data = db.query(animals, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    private boolean checkIfPigExistsInAnimalDB(String regId){
        String id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String columns[] = { "id" };
        String whereClause = "registryid LIKE ?";
        String[] whereArgs = new String[]{"%"+regId};
        Cursor data = db.query(animals, columns, whereClause , whereArgs, null, null, null);
        if(data.moveToFirst()) return true;
        else return false;
    }

    public boolean addToAnimalDB(String regId, String pig_classification, String isSynced){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if(pig_classification.equals("Grower")){
            pig_classification = "active";
        }else if(pig_classification.equals("Breeder")){
            pig_classification = "breeder";
        }else{
            pig_classification = "temporary";
        }

        contentValues.put(animaltype_id, 3);
        contentValues.put(registryid, regId);
        contentValues.put(farm_id, MyApplication.id);
        contentValues.put(breed_id, MyApplication.id);
        contentValues.put(grossmorpho, "");
        contentValues.put(morphochars, "");
        contentValues.put(weightrecord, "");
        contentValues.put(status, pig_classification);
        contentValues.put(is_synced, isSynced);

        long result = db.insert(animals,null,contentValues);
        return result != -1;
    }

    public boolean addToGroupingsDB(String regId, String motherId, String fatherId, String membersVal, String isSynced){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(registryid, regId);
        contentValues.put(mother_id, motherId);
        contentValues.put(father_id, fatherId);
        contentValues.put(breed_id, MyApplication.id);
        contentValues.put(members, membersVal);
        contentValues.put(is_synced, isSynced);

        long result = db.insert(groupings,null,contentValues);
        return result != -1;
    }

    public boolean addToGroupingMembersDB(String groupingId, String animalId, String isSynced){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(grouping_id, groupingId);
        contentValues.put(animal_id, animalId);
        contentValues.put(is_synced, isSynced);

        long result = db.insert(grouping_members,null,contentValues);
        return result != -1;
    }

    public boolean addToAnimalPropertyDB(int propertyId, String animalId, String valueString, String isSynced){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(animal_id, animalId);
        contentValues.put(property_id, propertyId);
        contentValues.put(value, valueString);
        contentValues.put(is_synced, isSynced);
        long result = db.insert(animal_properties,null,contentValues);
        return result != -1;
    }

    public boolean addToGroupingsPropertyDB(int propertyId, String groupingId, String valueString, String isSynced){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(grouping_id, groupingId);
        contentValues.put(property_id, propertyId);
        contentValues.put(value, valueString);
        contentValues.put(is_synced, isSynced);
        long result = db.insert(grouping_properties,null,contentValues);
        return result != -1;
    }

    public String getAnimalId(String regId) {
        String id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String columns[] = { "id" };
        String whereClause = "registryid = ?";
        String[] whereArgs = new String[]{regId};
        Cursor data = db.query(animals, columns, whereClause , whereArgs, null, null, null);
        if(data.moveToFirst()){
            id = data.getString(data.getColumnIndex("id"));
        }
        return id;
    }

    public String getGroupingProperty(String groupingId, int groupingPropId){
        String value = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String columns[] = {"value"};
        String whereClause = "grouping_id = ? AND property_id = ?";
        String[] whereArgs = new String[]{groupingId, Integer.toString(groupingPropId)};
        Cursor data = db.query(grouping_properties, columns, whereClause, whereArgs, null, null, null);
        if(data.moveToFirst()){
            value = data.getString(data.getColumnIndex("value"));
        }
        return value;
    }

    public boolean checkGroupingMembers(String groupingId){
        SQLiteDatabase db = this.getReadableDatabase();
        String columns[] = {"members"};
        String whereClause = "id = ?";
        String[] whereArgs = new String[]{groupingId};
        Cursor data = db.query(groupings, columns, whereClause, whereArgs, null, null, null);
        if(data.moveToFirst()){
            if(data.getString(0) == null || data.getString(0).equals("")){
                return false;
            }
        }
        return true;
    }

    private String getAnimalRegistry(String regId) {
        String id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String columns[] = { "registryid" };
        String whereClause = "registryid LIKE ?";
        String[] whereArgs = new String[]{"%"+regId};
        Cursor data = db.query(animals, columns, whereClause , whereArgs, null, null, null);
        if(data.moveToFirst()){
            id = data.getString(data.getColumnIndex("registryid"));
        }
        return id;
    }

    public String getGroupingId(String sowId, String boarId) {
        String id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String columns[] = { "*" };
        String whereClause = "mother_id = ? AND father_id = ?";
        String[] whereArgs = new String[]{sowId, boarId};
        Cursor data = db.query(groupings, columns, whereClause , whereArgs, null, null, null);
        if(data.moveToFirst()){
            id = data.getString(data.getColumnIndex("id"));
        }

        return id;
    }

    public boolean setIsSyncedFromPigTableToDelete(String regId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(is_synced, "delete");
        String whereClause = "registryid = ?";
        String[] whereArgs = new String[]{regId};

        int success = db.update(animals, contentValues, whereClause, whereArgs);

        if(success==1) return true;
        else return  false;
    }

    public boolean addAsBreeder(String regId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(status, "breeder");
        String whereClause = "registryid = ?";
        String[] whereArgs = new String[]{regId};

        int success = db.update(animals, contentValues, whereClause, whereArgs);

        if(success==1) return true;
        else return  false;
    }

    public Cursor getBreedingContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT a.registryid, c.registryid, b.value FROM groupings a, grouping_properties b, animals c where a.id=b.grouping_id AND a.father_id=c.id AND b.property_id=42" , null);
        return data;
    }

    public Cursor getMortalityContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT b.registryid, a.* FROM mortalities a, animals b where a.animal_id=b.id ", null);
        return data;
    }

    public Cursor getSalesContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT b.registryid, a.* FROM sales a, animals b where a.animal_id=b.id ", null);
        return data;
    }

    public Cursor getOthersContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT b.registryid, a.* FROM removed_animals a, animals b where a.animal_id=b.id ", null);
        return data;
    }

    public Cursor getBoarContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "*" };
        String whereClause = "substr(registryid, -7, 1) = ? AND status = ? AND is_synced != ?";
        String[] whereArgs = new String[]{"M", "breeder", "delete"};
        Cursor data = db.query(animals, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor getSowContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "*" };
        String whereClause = "substr(registryid, -7, 1) = ? AND status = ? AND is_synced != ?";
        String[] whereArgs = new String[]{"F", "breeder", "delete"};
        Cursor data = db.query(animals, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor getFemaleGrowerContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "*" };
        String whereClause = "substr(registryid, -7, 1) = ? AND status = ? AND is_synced != ?";
        String[] whereArgs = new String[]{"F", "active", "delete"};
        Cursor data = db.query(animals, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor getMaleGrowerContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = { "*" };
        String whereClause = "substr(registryid, -7, 1) = ? AND status = ? AND is_synced != ?";
        String[] whereArgs = new String[]{"M", "active", "delete"};
        Cursor data = db.query(animals, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor getOffspringContents(String groupingId){
        SQLiteDatabase db = this.getWritableDatabase();
        String query =  "SELECT b.registryid, c.property_id, c.value " +
                        "FROM grouping_members a, animals b, animal_properties c " +
                        "where a.animal_id=b.id AND b.id=c.animal_id AND a.grouping_id ="+groupingId;
        Cursor data = db.rawQuery(query, null);
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

    // TODO: 4/20/19 double check yung pag add kasi naka specific
    public boolean addAllUnsyncedFromLocalToServer(String tablename){
        Cursor unsyncedData = getAllUnsyncedData(tablename);
        RequestParams params;

        while(unsyncedData.moveToNext()){
            if((unsyncedData.getString(unsyncedData.getColumnIndex("is_synced"))).equals("false")){
                params = buildParamsPigTable(unsyncedData);
                addPigToServer(params);
            }
//            else if((unsyncedData.getString(unsyncedData.getColumnIndex("is_synced"))).equals("delete")){
//                params = new RequestParams();
//                params.put("pig_registration_id", unsyncedData.getString(unsyncedData.getColumnIndex("pig_registration_id")));
//                deletePigFromServer(params);
//            }
        }
        return true;
    }


    public boolean syncDataFromLocalToServer() {
        boolean isSuccess = addAllUnsyncedFromLocalToServer(animals) &&
                addAllUnsyncedFromLocalToServer(animal_properties) &&
                addAllUnsyncedFromLocalToServer(groupings) &&
                addAllUnsyncedFromLocalToServer(grouping_members) &&
                addAllUnsyncedFromLocalToServer(grouping_properties) &&
                addAllUnsyncedFromLocalToServer(mortalities) &&
                addAllUnsyncedFromLocalToServer(removed_animals) &&
                addAllUnsyncedFromLocalToServer(sales) &&
                addAllUnsyncedFromLocalToServer(weight_collections);
        return isSuccess;
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

    public String getFarmCode(){
        String returnString = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String columns[] = { "*" };
        String whereClause = "breedable_id = ?";
        String[] whereArgs = new String[]{Integer.toString(MyApplication.id)};
        Cursor data = db.query(farms, columns, whereClause , whereArgs, null, null, null);
        if(data.moveToFirst()){
            returnString = data.getString(data.getColumnIndex("code"));
        }
        return returnString;
    }

    public String getFarmBreed(){
        String returnString = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String columns[] = { "breed" };
        String whereClause = "id = ?";
        String[] whereArgs = new String[]{Integer.toString(MyApplication.id)};
        Cursor data = db.query("breeds", columns, whereClause , whereArgs, null, null, null);
        if(data.moveToFirst()){
            returnString = data.getString(data.getColumnIndex("breed"));
        }
        return returnString;
    }

//    select property_id, value from animal_properties as a inner join animals as b on a.animal_id = b.id
//    where b.registryid = ? ;

    public Cursor getSinglePig(String regId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = new String[]{regId};
        Cursor data = db.rawQuery("SELECT property_id, value FROM animal_properties as a INNER JOIN animals as b on a.animal_id = b.id WHERE b.registryid = ?", whereArgs);
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
        String[] whereArgs = new String[]{sowRegId, boarRegId};
        Cursor data = db.query("pig_breeding_table", columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor generatePigList(String reg_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = {"registryid"};
        String whereClause = "lower(registryid) LIKE ? AND is_synced != ?";
        String[] whereArgs = new String[]{"%" + reg_id.toLowerCase() +"%", "delete"};
        Cursor data = db.query(animals, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor generateSowList(String reg_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = {"registryid"};
        String whereClause = "lower(registryid) LIKE ? AND status = ? AND substr(registryid, -7, 1) = ?";
        String[] whereArgs = new String[]{"%" + reg_id.toLowerCase() +"%", "breeder", "F"};
        Cursor data = db.query(animals, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public Cursor generateBoarList(String reg_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = {"registryid"};
        String whereClause = "lower(registryid) LIKE ? AND status = ? AND substr(registryid, -7, 1) = ?";
        String[] whereArgs = new String[]{"%" + reg_id.toLowerCase() +"%", "breeder", "M"};
        Cursor data = db.query(animals, columns, whereClause , whereArgs, null, null, null);
        return data;
    }

    public boolean updateSowStatus(String sow_reg_id, String boar_id, String sowStatus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(sow_status, sowStatus);
        String whereClause = "sow_registration_id = ? AND boar_registration = ?";
        String[] whereArgs = new String[]{sow_reg_id, boar_id};

        long result = db.update(pig_breeding_table, contentValues, whereClause, whereArgs);
        if(result == -1) return false;
        else return true;
    }

    public boolean updateStatus(String regId, String stat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String currStatus = getStatus(regId);
        if(currStatus.equals("breeder")){
            contentValues.put(status, stat+" breeder");
        }else if(currStatus.equals("active")){
            contentValues.put(status, stat+" grower");
        }
        String whereClause = "registryid = ?";
        String[] whereArgs = new String[]{regId};

        long result = db.update(animals, contentValues, whereClause, whereArgs);
        if(result == -1) return false;
        else return true;
    }

    public String getStatus(String regId){
        SQLiteDatabase db = this.getReadableDatabase();
        String columns[] = { "status" };
        String whereClause = "registryid = ?";
        String[] whereArgs = new String[]{regId};
        Cursor data = db.query(animals, columns, whereClause, whereArgs, null, null, null);
        if(data.moveToFirst()){
            return data.getString(data.getColumnIndex("status"));
        }
        return null;
    }

    public boolean updateMorphChar(String regId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(morphochars, "1");
        String whereClause = "registryid = ?";
        String[] whereArgs = new String[]{regId};

        long result = db.update(animals, contentValues, whereClause, whereArgs);
        if(result == -1) return false;
        else return true;
    }

    public boolean updateGrossMorpho(String regId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(grossmorpho, "1");
        String whereClause = "registryid = ?";
        String[] whereArgs = new String[]{regId};

        long result = db.update(animals, contentValues, whereClause, whereArgs);
        if(result == -1) return false;
        else return true;
    }

    public boolean updateWeightRecords(String regId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(weightrecord, "1");
        String whereClause = "registryid = ?";
        String[] whereArgs = new String[]{regId};

        long result = db.update(animals, contentValues, whereClause, whereArgs);
        if(result == -1) return false;
        else return true;
    }

    public boolean updateMemberInGroupings(String sowId, String boarId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(members, "1");
        String whereClause = "mother_id = ? AND father_id = ?";
        String[] whereArgs = new String[]{sowId, boarId};

        long result = db.update(groupings, contentValues, whereClause, whereArgs);
        if(result == -1) return false;
        else return true;
    }

    public boolean updateGroupingProperty(String groupingId, int propertyId, String propertyValue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(value, propertyValue);
        String whereClause = "grouping_id = ? AND property_id = ?";
        String[] whereArgs = new String[]{groupingId, Integer.toString(propertyId)};
        long result = db.update(grouping_properties, contentValues, whereClause, whereArgs);
        if(result == -1) return false;
        else return true;
    }

    private String changeToNotSpecifiedIfNull(String value) {
        if(value == null || value.isEmpty())
            return "Not specified";
        else
            return value;
    }

    private String changeToBlankIfNull(String value) {
        if(value == null || value.isEmpty())
            return "";
        else
            return value;
    }

    private String changeToNoDataAvailableIfNull(String value) {
        if(value == null || value.isEmpty() || value.equals(""))
            return "No data available";
        else
            return value;
    }

    public static Date addDays(Date date, int days) {
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }


//    public static Date subtractDays(Date date, int days) {
//        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//        GregorianCalendar cal = new GregorianCalendar();
//        cal.setTime(date);
//        cal.add(Calendar.DATE, days);
//
//        return cal.getTime();
//    }

}
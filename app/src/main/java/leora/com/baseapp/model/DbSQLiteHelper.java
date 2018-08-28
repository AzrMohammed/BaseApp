package leora.com.baseapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import leora.com.baseapp.Constants;
import leora.com.baseapp.model.dbmodel.DbSampleModel;
import leora.com.baseapp.ormfiles.ModelBaseClass;
import leora.com.baseapp.utils.DataUtils;
import leora.com.baseapp.utils.DbUtils;
import leora.com.baseapp.utils.ValueUtils;

public class DbSQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = (((Constants.BUILD_TYPE == Constants.BUILD_TYPE_LIVE) || (Constants.BUILD_TYPE == Constants.BUILD_TYPE_LIVE_DEMO)) ? "" : ((Constants.BUILD_TYPE == Constants.BUILD_TYPE_STAGING) ? "STAGING_" : "LOCAL_")) + "vc_hospital.db";

    public DbSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists " + Constants.TBL_EXAMPLE + " (id integer primary key, slug text, ex_name text DEFAULT '', last_sync_time text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable();
        onCreate(db);
    }

    public void dropTable() {
        Log.e("camee_droptt", "tru");
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            try {
                // query to obtain the names of all tables in your database
                Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
                List<String> tables = new ArrayList<>();

                // iterate over the result set, adding every table name to a list
                while (c.moveToNext()) {
                    tables.add(c.getString(0));
                }

                // call DROP TABLE on every table name
                for (String table : tables) {
                    try {
                        //                        Log.e("elld_Ex", "=="+table);
                        String dropQuery = "DELETE FROM " + table;
                        db.execSQL(dropQuery);
                    } catch (SQLException e) {
                        //                        Log.e("elld_Ex3", "=="+e);
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                //                Log.e("elld_Ex2", "=="+e);
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DbSampleModel getExampleModel(String id) {

        Cursor cursor = getCompleteTable(Constants.TBL_EXAMPLE);
        DbSampleModel exampleModel = (DbSampleModel) setModel(DbSampleModel.class, cursor);

        return exampleModel;
    }

    public DbSampleModel getExampleModels() {
        Cursor cursor = getCompleteTable(Constants.TBL_EXAMPLE);

        DbSampleModel exampleModel = (DbSampleModel) setModel(DbSampleModel.class, cursor);

        return exampleModel;
    }

    public Object setModel(Class<?> classx, Cursor cursor) {
        return setFieldsValues(classx, cursor);
    }

    public void putResourceAsBytes(byte[] resource_bytes, String table_name, String column_name, String pk_key, String pk_value) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_name, resource_bytes);

        Cursor cursor = db.rawQuery("select * from " + table_name + " where " + pk_key + " ='" + pk_value + "'", null);

        if (cursor.moveToFirst()) {
            db.update(table_name, contentValues, pk_key + " = ? ", new String[]{pk_value});
        } else {
            db.insert(table_name, null, contentValues);
        }

    }

    //common Methods -------------------------------------------------------------------------------

    public boolean isTableExists(String tableName) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name =" + " '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void commonInsertUpdateByUk(String table_name, HashMap<String, String> values) {
        commonInsertUpdate(table_name, table_name + "_slug", values);
    }

    public void commonInsertUpdate(String table_name, String unique_key, HashMap<String, String> values) {

        String unique_key_value = values.get(unique_key);
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> column_names = getTableColumnDetails(db, table_name);
        if (column_names.size() > 0) {
            ContentValues contentValues = new ContentValues();
            for (Map.Entry<String, String> entry : values.entrySet()) {
                String value = entry.getValue();
                String key = entry.getKey();

                contentValues.put(key, value);

            }
            Cursor cursor = getDataByKey(table_name, unique_key, unique_key_value);
            ArrayList<String> arrayList = new ArrayList<>();
            if (cursor.moveToFirst()) {

                db.update(table_name, contentValues, unique_key + " = ? ", new String[]{unique_key_value});

            } else {

                db.insert(table_name, null, contentValues);
            }
            cursor.close();
        } else {
            //  //Log.e("err_table", "invalid table");
        }
    }

    public ArrayList<String> getTableColumnDetails(SQLiteDatabase db, String table) {
        ArrayList<String> column_names = new ArrayList<String>();
        Cursor table_info = db.rawQuery("PRAGMA table_info('" + table + "') ", null);
        while (table_info.moveToNext()) {
            String col_name = table_info.getString(table_info.getColumnIndex("name"));
            column_names.add(col_name);
        }
        table_info.close();
        return column_names;
    }

    public boolean checkIfExists(String TableName, String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TableName + " where " + key + " = '" + value + "'";
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public Object modelActionLoad(ModelBaseClass modelBaseClass) {

        Cursor cursor = getDataByKey(modelBaseClass.getTableName(), modelBaseClass.getPk(), modelBaseClass.getPkValue());
        Object object;
        if (cursor.moveToFirst()) {
            object = setFieldsValues(modelBaseClass, cursor);
        } else {
            object = modelBaseClass;
        }
        cursor.close();
        return object;

    }

    public int modelActionInsert(ModelBaseClass modelBaseClass) {

        Boolean proceed_insert = true;
        int status = ModelBaseClass.STATUS_FAILURE;

        if (DataUtils.isStringValueExist(modelBaseClass.getPkValue())) {
            Cursor cursor = getDataByKey(modelBaseClass.getTableName(), modelBaseClass.getPk(), modelBaseClass.getPkValue());

            if (cursor.moveToFirst()) proceed_insert = false;
            cursor.close();
        }

        if (proceed_insert) {

            modelBaseClass.setPkValue(generateLocalSlug(modelBaseClass.getTableName(), modelBaseClass.getPk()));
            status = proceedInsertUpdate(modelBaseClass);
            //            modelBaseClass.reset(modelBaseClass);
        }

        return status;


    }

    public int modelActionUpdate(ModelBaseClass modelBaseClass) {

        int status = ModelBaseClass.STATUS_FAILURE;

        if (DataUtils.isStringValueExist(modelBaseClass.getPk())) {

            Cursor cursor = getDataByKey(modelBaseClass.getTableName(), modelBaseClass.getPk(), modelBaseClass.getPkValue());

            if (cursor.moveToFirst()) {
                status = proceedInsertUpdate(modelBaseClass);
                //                modelBaseClass.reset(modelBaseClass);
            }
        }
        return status;
    }

    public int modelActionDelete(ModelBaseClass modelBaseClass) {

        int status = ModelBaseClass.STATUS_FAILURE;

        Boolean proceed = false;

        if (DataUtils.isStringValueExist(modelBaseClass.getPk())) {

            Cursor cursor = getDataByKey(modelBaseClass.getTableName(), modelBaseClass.getPk(), modelBaseClass.getPkValue());

            if (cursor.moveToFirst()) {
                status = proceedInsertUpdate(modelBaseClass);
                modelBaseClass.reset(modelBaseClass);
            }
        }

        return status;

    }

    public int proceedInsertUpdate(ModelBaseClass modelBaseClass) {
        int status = ModelBaseClass.STATUS_FAILURE;
        HashMap<String, String> values_set = new HashMap<String, String>();

        Class<?> classx = modelBaseClass.getClass();
        try {
            for (Field field : classx.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    String field_name = field.getName();
                    Object value = field.get(modelBaseClass);

                    if (DataUtils.isStringValueExist((String) value)) {
                        //  //Log.e("recc_valsett", field_name + "===" + value);

                        values_set.put(field_name, "" + value);
                    }
                }
            }
        } catch (Exception e) {
            //  //Log.e("instaexx", "e==" + e);
            e.printStackTrace();
        }

        if (values_set.containsKey(modelBaseClass.getPk())) {
            if (DataUtils.isStringValueExist(values_set.get(modelBaseClass.getPk()))) {
                commonInsertUpdate(modelBaseClass.getTableName(), modelBaseClass.getPk(), values_set);
            }
        }

        return status;
    }

    public Object setFieldsValues(Object objectx, Cursor cursor) {

        Class<?> classx = objectx.getClass();
        //        Object objectx = null;
        try {
            //            objectx = classx.newInstance();

            for (Field field : classx.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    String field_name = field.getName();

                    try {
                        //                       //  //Log.e("came", field.getType() + "==st4== " + field_name);
                        String type = "" + field.getType();
                        int index = cursor.getColumnIndex(field_name);
                        if (index != -1) {

                            String val = cursor.getString(index);
                            if (DataUtils.isStringValueExist(val)) if (type.equals("boolean")) {
                                Boolean val_bool = val.equalsIgnoreCase("True");
                                field.set(objectx, val_bool);
                            } else if (type.equals("int")) {
                                if (DataUtils.isNumeric(val)) field.set(objectx, val);
                            } else {
                                field.set(objectx, val);
                            }
                        }
                    } catch (Exception e) {
                        //  //Log.e("essset1", field.getType() + "e==" + field_name + e + "==");
                        e.printStackTrace();
                    }

                }
            }
        } catch (Exception e) {
            //  //Log.e("instaexx", "e==" + e);
            e.printStackTrace();
        }

        return objectx;


    }

    public Object setFieldsValues(Class<?> classx, Cursor cursor) {

        Object objectx = null;
        try {
            objectx = classx.newInstance();

            for (Field field : classx.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    String field_name = field.getName();

                    try {
                        //                       //  //Log.e("came", field.getType() + "==st4== " + field_name);
                        String type = "" + field.getType();
                        int index = cursor.getColumnIndex(field_name);
                        if (index != -1) {

                            String val = cursor.getString(index);
                            if (DataUtils.isStringValueExist(val)) if (type.equals("boolean")) {
                                Boolean val_bool = val.equalsIgnoreCase("True");
                                field.set(objectx, val_bool);
                            } else if (type.equals("int")) {
                                if (DataUtils.isNumeric(val)) field.set(objectx, val);
                            } else {
                                field.set(objectx, val);
                            }
                        }
                    } catch (Exception e) {
                        //  //Log.e("essset1", field.getType() + "e==" + field_name + e + "==");
                        e.printStackTrace();
                    }

                }
            }
        } catch (Exception e) {
            //  //Log.e("instaexx", "e==" + e);
            e.printStackTrace();
        }

        return objectx;


    }

    public String generateLocalSlug(String table_name, String pk_name) {

        SQLiteDatabase db = this.getReadableDatabase();
        Boolean is_slugalready_exist = true;
        String slug = DbUtils.createSlug();

        while (!is_slugalready_exist) {
            Cursor cursor = db.rawQuery("select * from " + table_name + " where " + pk_name + "= " + "'" + table_name + "_" + slug + "'", null);

            if (cursor.moveToFirst()) {
                is_slugalready_exist = true;
                slug = DbUtils.createSlug();
            } else {
                is_slugalready_exist = false;
            }
        }
        return slug;
    }

    public Cursor getDataByPermalink(String table_name, String searchValue) {

        if (!DataUtils.isStringValueExist(searchValue)) searchValue = ValueUtils.NOT_DEFINED;

        return getDataByKey(table_name, table_name + "_permalink", searchValue);
    }

    public Cursor getDataBySlug(String table_name, String searchValue) {

        if (!DataUtils.isStringValueExist(searchValue)) searchValue = ValueUtils.NOT_DEFINED;

        return getDataByKey(table_name, table_name + "_slug", searchValue);
    }

    public Cursor getDataByKey(String table_name, String searchKey, String searchValue) {

        if (!DataUtils.isStringValueExist(searchValue)) searchValue = ValueUtils.NOT_DEFINED;

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from " + table_name + " where " + searchKey + "= '" + searchValue + "'";
        return db.rawQuery(query, null);
    }

    public Cursor getDataCompleteTable(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + tableName, null);
    }

    public void deleteRow(String tableName, String searchKey, String searchValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + tableName + " where " + searchKey + "= '" + searchValue + "'", null);
    }

    public Cursor getUnsyncData(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + tableName + " where LOWER(sync_complete) <> LOWER('true')", null);

    }

    public Cursor executeQuery(String query) {

        SQLiteDatabase db = this.getReadableDatabase();
        //        //  //Log.e("rrr1", db.isOpen() + "===" + db.isReadOnly());
        //
        //        //  //Log.e("rrr2", db.isOpen() + "===" + db.isReadOnly());
        //
        //        //  //Log.e("rrr3", db.isOpen() + "===" + db.isReadOnly());

        return db.rawQuery(query, null);

    }

    public void executeAlter(String query) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);

    }

    public Cursor getCompleteTable(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + tableName, null);
    }

}

package leora.com.baseapp.ormfiles;

import android.database.Cursor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import leora.com.baseapp.utils.DataUtils;

/**
 * This class is under work in progress
 * This class communicates with
 * the database and the persistent layer for individual models. Acts as an ORM.
 */

public abstract class ModelBaseClass extends DbSupport {

    public Boolean is_load = false;

    final public static String CRITERIA_COMMAND_FIRST_ROW = "CRITERIA_COMMAND_FIRST_ROW";

    public ArrayList<String> criteria_list = new ArrayList<String>();

    public abstract int load();

    public abstract int insert();

    public abstract int update();

    public abstract int delete();

    public abstract String getTableName();

    public abstract void setPkValue(String value);

    public abstract String getPkValue();

    public abstract void reset(ModelBaseClass modelBaseClass);


    public ArrayList<String> getCriteria() {
        return criteria_list;
    }

    public void addCriteria(String criteria) {
        criteria_list.add(criteria);
    }


    public String getPk() {
        return getTableName() + "_slug";
    }

    public void setLoadComplete(Boolean is_load) {
        this.is_load = is_load;
    }


    public int modelActionLoad(ModelBaseClass modelBaseClass) {

        int status = DbSupport.STATUS_FAILURE;

        Cursor cursor;

        if (modelBaseClass.getCriteria().size() > 0) {
            String criteria = "";

            ArrayList<String> criteria_list = modelBaseClass.getCriteria();

            for (int i = 0; i < criteria_list.size(); i++) {

                if (DataUtils.isStringValueExist(criteria_list.get(i))) {
                    if (!criteria_list.get(i).equals(CRITERIA_COMMAND_FIRST_ROW)) {
                        if (!criteria.contains(" WHERE "))
                            criteria = " WHERE " + criteria_list.get(i);
                        else criteria += " AND " + criteria_list.get(i);

                    }
                }
            }

            String query = "SELECT * from " + modelBaseClass.getTableName() + criteria;

            //  //Log.e("final+q", query);
            cursor = getDbModel().executeQuery(query);

        } else {
            cursor = getDbModel().getDataByKey(modelBaseClass.getTableName(), modelBaseClass.getPk(), modelBaseClass.getPkValue());
        }

        //  //Log.e("curr_l", "==" + cursor.getCount());

        if (cursor.moveToFirst()) {

            setFieldsValues(modelBaseClass, cursor);
            status = DbSupport.STATUS_SUCCESS;

        }
        cursor.close();
        //        else {
        //            return modelBaseClass;
        //        }

        return status;

    }

    public int modelActionInsert(ModelBaseClass modelBaseClass) {

        Boolean proceed_insert = true;
        int status = ModelBaseClass.STATUS_FAILURE;

        if (DataUtils.isStringValueExist(modelBaseClass.getPkValue())) {
            Cursor cursor = getDbModel().getDataByKey(modelBaseClass.getTableName(), modelBaseClass.getPk(), modelBaseClass.getPkValue());

            if (cursor.moveToFirst()) proceed_insert = false;
            cursor.close();
        }

        if (proceed_insert) {

            modelBaseClass.setPkValue(getDbModel().generateLocalSlug(modelBaseClass.getTableName(), modelBaseClass.getPk()));
            status = getDbModel().proceedInsertUpdate(modelBaseClass);
            //            modelBaseClass.reset(modelBaseClass);
        }

        return status;


    }

    public int modelActionUpdate(ModelBaseClass modelBaseClass) {

        int status = ModelBaseClass.STATUS_FAILURE;


        if (DataUtils.isStringValueExist(modelBaseClass.getPk())) {

            Cursor cursor = getDbModel().getDataByKey(modelBaseClass.getTableName(), modelBaseClass.getPk(), modelBaseClass.getPkValue());

            if (cursor.moveToFirst()) {
                status = getDbModel().proceedInsertUpdate(modelBaseClass);

                cursor.close();
                //                modelBaseClass.reset(modelBaseClass);
            }
        }
        return status;
    }

    public int modelActionDelete(ModelBaseClass modelBaseClass) {

        int status = ModelBaseClass.STATUS_FAILURE;

        Boolean proceed = false;

        if (DataUtils.isStringValueExist(modelBaseClass.getPk())) {

            Cursor cursor = getDbModel().getDataByKey(modelBaseClass.getTableName(), modelBaseClass.getPk(), modelBaseClass.getPkValue());

            if (cursor.moveToFirst()) {
                int cursor_index = cursor.getColumnIndex("is_deleted");

                if (cursor_index != -1) {
                    HashMap<String, String> values_set = new HashMap<String, String>();
                    values_set.put("is_deleted", "true");
                    getDbModel().commonInsertUpdate(modelBaseClass.getTableName(), modelBaseClass.getPk(), values_set);
                    //  //Log.e("exx_isd", getTableName() + "===" + getPk() + "===" + getPkValue());
                } else {
                    getDbModel().deleteRow(modelBaseClass.getTableName(), modelBaseClass.getPk(), modelBaseClass.getPkValue());
                }


                status = DbSupport.STATUS_SUCCESS;

                modelBaseClass.reset(modelBaseClass);
            }
            cursor.close();
        }


        return status;

    }

    /**
     * sets the value of the columns to the model
     * @param objectx
     * @param cursor
     * @return
     */
    public Object setFieldsValues(Object objectx, Cursor cursor) {


        Class<?> classx = objectx.getClass();
        try {
            for (Field field : classx.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    String field_name = field.getName();

                    try {
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

}

package leora.com.baseapp.ormfiles;

import android.database.Cursor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import leora.com.baseapp.utils.DataUtils;

/**
 * * This class is under work in progress
 * This class communicates with
 * the database and the persistent layer for a collection. Acts as an ORM.
 */

public abstract class ModelBaseClassList extends DbSupport {

    public Boolean is_load = false;

    public abstract int load();

    public abstract int convertObjecttoModel(ArrayList<Object> object_list);

    public abstract String getTableName();


    public ArrayList<Object> modelActionLoad(ModelBaseClassList modelBaseClass_list, ModelBaseClass modelBaseClass) {

        Class<?> cc = modelBaseClass.getClass();
        ArrayList<Object> list = new ArrayList<Object>();

        String query = "SELECT * from " + modelBaseClass_list.getTableName();

        Cursor cursor = getDbModel().executeQuery(query);
        //  //Log.e("get_cc", query + "===ccontff" );
        //  //Log.e("get_cc", query + "===ccont" + cursor.getCount());

        while (cursor.moveToNext()) {
            list.add(setFieldsValues(modelBaseClass, cursor));
        }
        cursor.close();

        return list;
    }


    /**
     * sets the value of the columns to the model
     * @param objectx
     * @param cursor
     * @return
     */
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
                            if (DataUtils.isStringValueExist(val))
                                if (type.equals("boolean")) {
                                    Boolean val_bool = val.equalsIgnoreCase("True");
                                    field.set(objectx, val_bool);
                                } else if (type.equals("int")) {
                                    if (DataUtils.isNumeric(val))
                                        field.set(objectx, val);
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

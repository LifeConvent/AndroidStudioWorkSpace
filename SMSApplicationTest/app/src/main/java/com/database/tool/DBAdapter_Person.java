package com.database.tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.database.ob.Person;
import com.example.activity.search.SearchActivity;

/**
 * Created by 彪 on 2016/4/20.
 */
public class DBAdapter_Person {

    public static final String TABLE_ID = "ID";//自增编号
    public static final String PERSON_ACCOUNT = "ACCOUNT";
    public static final String PERSON_NAME = "NAME";
    public static final String PERSON_SEX = "SEX";
    public static final String PERSON_AGE = "AGE";
    public static final String PERSON_DEPARTMENT = "DEPARTMENT";
    public static final String PERSON_POSITION = "POSITION";
    public static final String PERSON_INITIAL = "INITIAL";
    public static final String PERSON_PHONE = "PHONE";
    public static final String PERSON_EMAIL = "EMAIL";

    private static final String DB_NAME = "person.db";
    private static final String DB_TABLE = "MyInfo";
    private static final int DB_VERSION = 1;

    public static final String TAG = "个人信息表数据库";

    private SQLiteDatabase db = null;
    private Context context;
    private DBOpenHelper dbOpenHelper;

    private static class DBOpenHelper extends SQLiteOpenHelper {
        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static final String DB_CREATE = "create table " + DB_TABLE + "(" + TABLE_ID +
                " integer primary key autoincrement," + PERSON_ACCOUNT + " text not null," + PERSON_NAME +
                " text," + PERSON_SEX + " text," + PERSON_AGE +
                " integer," + PERSON_DEPARTMENT + " text," + PERSON_POSITION +
                " text," + PERSON_INITIAL + " text," + PERSON_PHONE + " text," + PERSON_EMAIL + " text);";

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        //当表需要进行更新时调用
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //当创建的表存在时进行销毁
            db.execSQL("DROP TABLE IF EXISTS" + DB_TABLE);
            onCreate(db);
            //一般用做数据删除或数据转移
        }
    }

    //数据库第一次建立时被调用
    public DBAdapter_Person(Context context) {
        this.context = context;
    }

    public void open() throws SQLiteException {
        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        try {
            //建立或打开可读写的数据库实例，成功后实例被缓存
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }


    public long insert(Person person) {
        ContentValues newValues = new ContentValues();
        newValues.put(PERSON_ACCOUNT, person.getAccount());
        newValues.put(PERSON_NAME, person.getName());
        newValues.put(PERSON_SEX, person.getSex());
        newValues.put(PERSON_AGE, person.getAge());
        newValues.put(PERSON_DEPARTMENT, person.getDepartment());
        newValues.put(PERSON_POSITION, person.getPosition());
        newValues.put(PERSON_INITIAL, person.getInitial());
        newValues.put(PERSON_PHONE, person.getPhone());
        newValues.put(PERSON_EMAIL, person.getEmail());
        //将数据插入表中，无替换数据
        return db.insert(DB_TABLE, null, newValues);
    }

    public long deleteAllData() {
        return db.delete(DB_TABLE, null, null);
    }

    public long deleteOneData(long id) {
        return db.delete(DB_TABLE, TABLE_ID + "=" + id, null);
    }


    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    public Person[] queryPerson(String account) {
        Person[] persons = getOneData(account);
        Log.i(TAG, "多组查询" + persons);
        return persons;
    }

    public Person querySinglePerson(long id) {
        Person person = getSingleOneData(id);
        Log.i(TAG, "单一查询" + person);
        return person;
    }

    public Person querySinglePerson(String num, String type) {
        Person person = getSingleOneData(num, type);
        Log.i(TAG, "单一查询" + person);
        return person;
    }


    public Person[] getOneData(String account) {
        Cursor results = db.query(DB_TABLE, new String[]{TABLE_ID, PERSON_ACCOUNT, PERSON_NAME, PERSON_SEX,
                        PERSON_AGE, PERSON_DEPARTMENT, PERSON_POSITION, PERSON_INITIAL, PERSON_PHONE, PERSON_EMAIL},
                PERSON_ACCOUNT + "=" + "'" + account + "'", null, null, null, null);
        return ConvertToPeople(results);
    }

    public Person getSingleOneData(long id) {
        Cursor results = db.query(DB_TABLE, new String[]{TABLE_ID, PERSON_ACCOUNT, PERSON_NAME, PERSON_SEX,
                        PERSON_AGE, PERSON_DEPARTMENT, PERSON_POSITION, PERSON_INITIAL, PERSON_PHONE, PERSON_EMAIL},
                TABLE_ID + "=" + id, null, null, null, null);
        return ConvertToSinglePeople(results);
    }

    public Person getSingleOneData(String account, String type) {
        Cursor results;
        if (type.equals("PERSON_ACCOUNT"))
            results = db.query(DB_TABLE, new String[]{TABLE_ID, PERSON_ACCOUNT, PERSON_NAME, PERSON_SEX,
                            PERSON_AGE, PERSON_DEPARTMENT, PERSON_POSITION, PERSON_INITIAL, PERSON_PHONE, PERSON_EMAIL},
                    PERSON_ACCOUNT + "=" + "'" + account + "'", null, null, null, null);
        else if (type.equals("PERSON_NAME"))
            results = db.query(DB_TABLE, new String[]{TABLE_ID, PERSON_ACCOUNT, PERSON_NAME, PERSON_SEX,
                            PERSON_AGE, PERSON_DEPARTMENT, PERSON_POSITION, PERSON_INITIAL, PERSON_PHONE, PERSON_EMAIL},
                    PERSON_NAME + "=" + "'" + account + "'", null, null, null, null);
        else
            results = null;
        return ConvertToSinglePeople(results);
    }

    //    public static final String TABLE_ID = "ID";//自增编号
//    public static final String PERSON_ACCOUNT = "ACCOUNT";
//    public static final String PERSON_NAME = "NAME";
//    public static final String PERSON_SEX = "SEX";
//    public static final String PERSON_AGE = "AGE";
//    public static final String PERSON_DEPARTMENT = "DEPARTMENT";
//    public static final String PERSON_POSITION = "POSITION";
//    public static final String PERSON_INITIAL = "INITIAL";
//    public static final String PERSON_PHONE = "PHONE";
//    public static final String PERSON_EMAIL = "EMAIL";
    private Person[] ConvertToPeople(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        Person[] persons = new Person[resultCounts];
        for (int i = 0; i < resultCounts; i++) {
            persons[i] = new Person();
            persons[i].setAccount(cursor.getString(cursor.getColumnIndex(PERSON_ACCOUNT)));
            persons[i].setName(cursor.getString(cursor.getColumnIndex(PERSON_NAME)));
            persons[i].setSex(cursor.getString(cursor.getColumnIndex(PERSON_SEX)));
            persons[i].setAge(cursor.getLong(cursor.getColumnIndex(PERSON_AGE)));
            persons[i].setDepartment(cursor.getString(cursor.getColumnIndex(PERSON_DEPARTMENT)));
            persons[i].setPosition(cursor.getString(cursor.getColumnIndex(PERSON_POSITION)));
            persons[i].setInitial(cursor.getString(cursor.getColumnIndex(PERSON_INITIAL)));
            persons[i].setPhone(cursor.getString(cursor.getColumnIndex(PERSON_PHONE)));
            persons[i].setEmail(cursor.getString(cursor.getColumnIndex(PERSON_EMAIL)));
            cursor.moveToNext();
        }
        return persons;
    }

    private Person ConvertToSinglePeople(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        Person person = new Person();
        for (int i = 0; i < resultCounts; i++) {
            person = new Person();
            person.setAccount(cursor.getString(cursor.getColumnIndex(PERSON_ACCOUNT)));
            person.setName(cursor.getString(cursor.getColumnIndex(PERSON_NAME)));
            person.setSex(cursor.getString(cursor.getColumnIndex(PERSON_SEX)));
            person.setAge(cursor.getLong(cursor.getColumnIndex(PERSON_AGE)));
            person.setDepartment(cursor.getString(cursor.getColumnIndex(PERSON_DEPARTMENT)));
            person.setPosition(cursor.getString(cursor.getColumnIndex(PERSON_POSITION)));
            person.setInitial(cursor.getString(cursor.getColumnIndex(PERSON_INITIAL)));
            person.setPhone(cursor.getString(cursor.getColumnIndex(PERSON_PHONE)));
            person.setEmail(cursor.getString(cursor.getColumnIndex(PERSON_EMAIL)));
            cursor.moveToNext();
        }
        return person;
    }

    public void init(String account) {
        Person person = new Person();
        person.setAccount(account);
        put(person);
    }

    public void put(Person person) {
        this.insert(person);
    }

}

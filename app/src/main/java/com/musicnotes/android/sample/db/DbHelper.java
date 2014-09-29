package com.musicnotes.android.sample.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.musicnotes.android.sample.ColorSchemesApplication;
import com.musicnotes.android.sample.R;
import com.musicnotes.android.sample.model.Scheme;
import com.musicnotes.android.sample.model.SchemeColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jeff on 9/25/2014.
 * Copyright JeffInMadison.com 2014
 */
public class DbHelper extends SQLiteOpenHelper {
    @SuppressWarnings("UnusedDeclaration")
    private static final String TAG = DbHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SampleDB";

    private static final String TABLE_COLORS = "colors";
    private static final String TABLE_SCHEMES = "schemes";

    private static final String KEY_COLOR_ID = "id";
    private static final String KEY_COLOR_NAME = "name";

    private static final String KEY_SCHEME_ID = "id";
    private static final String KEY_SCHEME_NAME = "name";

    private static final String[] COLORS_COLUMNS = {KEY_COLOR_ID,KEY_COLOR_NAME};
    private static DbHelper sDbHelperInstance;
//    private static final String[] SCHEMES_COLUMNS = {KEY_SCHEME_NAME, KEY_SCHEME_ID };

    private final Context mContext;

    private DbHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static DbHelper getInstance() {
        if (sDbHelperInstance == null) {
            sDbHelperInstance = new DbHelper(ColorSchemesApplication.getAppContext());
        }
        return sDbHelperInstance;
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        String createColorsTable =
                String.format("CREATE TABLE %s (%s text PRIMARY KEY,%s text KEY)",
                        TABLE_COLORS,
                        KEY_COLOR_ID,
                        KEY_SCHEME_NAME);

        db.execSQL(createColorsTable);

        // Pre-load db with colors
        Resources resources = mContext.getResources();
        String addColor;
        addColor = String.format("INSERT INTO %s VALUES(%s, '%s')", TABLE_COLORS, resources.getColor(R.color.Black), "Black");
        db.execSQL(addColor);
//        addColor = String.format("INSERT INTO %s VALUES(%s, '%s')", TABLE_COLORS, resources.getColor(R.color.Gray), "Gray");
//        db.execSQL(addColor);
        addColor = String.format("INSERT INTO %s VALUES(%s, '%s')", TABLE_COLORS, resources.getColor(R.color.Blue), "Blue");
        db.execSQL(addColor);
        addColor = String.format("INSERT INTO %s VALUES(%s, '%s')", TABLE_COLORS, resources.getColor(R.color.Green), "Green");
        db.execSQL(addColor);
        addColor = String.format("INSERT INTO %s VALUES(%s, '%s')", TABLE_COLORS, resources.getColor(R.color.Yellow), "Yellow");
        db.execSQL(addColor);
        addColor = String.format("INSERT INTO %s VALUES(%s, '%s')", TABLE_COLORS, resources.getColor(R.color.Red), "Red");
        db.execSQL(addColor);
        addColor = String.format("INSERT INTO %s VALUES(%s, '%s')", TABLE_COLORS, resources.getColor(R.color.Orange), "Orange");
        db.execSQL(addColor);
        addColor = String.format("INSERT INTO %s VALUES(%s, '%s')", TABLE_COLORS, resources.getColor(R.color.Purple), "Purple");
        db.execSQL(addColor);

        String createSchemesTable =
                String.format("CREATE TABLE %s (%s text,%s text,PRIMARY KEY(%s, %s))",
                        TABLE_SCHEMES,
                        KEY_COLOR_NAME,
                        KEY_COLOR_ID,
                        KEY_COLOR_NAME,
                        KEY_COLOR_ID);

        db.execSQL(createSchemesTable);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

    }

    public void deleteAllSchemes() {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.delete(TABLE_SCHEMES, null, null);
            db.close();
        }
    }

    public void deleteSchemeByName(String schemeName) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            String where = String.format("%s='%s'", KEY_SCHEME_NAME, schemeName);
            db.delete(TABLE_SCHEMES, where, null);
            db.close();
        }
    }

    public List<SchemeColor> getSchemeColors() {
        List<SchemeColor> results = Collections.emptyList();

        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null) {
            results = new ArrayList<SchemeColor>();
            Cursor cursor = db.query(TABLE_COLORS, COLORS_COLUMNS, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_COLOR_ID));
                String name = cursor.getString(cursor.getColumnIndex(KEY_COLOR_NAME));
                SchemeColor schemeColor = new SchemeColor(name, id);
                results.add(schemeColor);
            }
            db.close();
        }
        return results;
    }

    public void addScheme(final List<Scheme> schemeList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null) {
            try {
                db.beginTransaction();
                for (Scheme scheme : schemeList) {
                    for (SchemeColor schemeColor : scheme.getColorList()) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(KEY_SCHEME_NAME, scheme.getName());
                        contentValues.put(KEY_SCHEME_ID, schemeColor.getId());
                        db.insert(TABLE_SCHEMES, null, contentValues);
                    }
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
                db.close();
            }
        }
    }

    public void addScheme(String name, List<SchemeColor> schemeColors) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null) {
            try {
                db.beginTransaction();

                for (SchemeColor schemeColor : schemeColors) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(KEY_SCHEME_NAME, name);
                    contentValues.put(KEY_SCHEME_ID, schemeColor.getId());
                    db.insert(TABLE_SCHEMES, null, contentValues);
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
                db.close();
            }
        }
    }

    public boolean schemeNameExists(String schemeName) {
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if (db != null) {
            String query = String.format("SELECT count(*) FROM %s where %s='%s'",
                    TABLE_SCHEMES,
                    KEY_SCHEME_NAME,
                    schemeName);
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            result = count > 0;
        }
        return result;
    }

    public List<Scheme> getAllSchemes() {
        List<Scheme> result = Collections.emptyList();
        HashMap<String,ArrayList<SchemeColor>> schemeMap = new LinkedHashMap<String, ArrayList<SchemeColor>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if (db != null) {
            result = new ArrayList<Scheme>();
            String query = "SELECT schemes.name, colors.name as color_name, colors.id from schemes join colors on schemes.id = colors.id";
            cursor = db.rawQuery(query, null);

            while (cursor.moveToNext()) {
                String schemeName = cursor.getString(cursor.getColumnIndex(KEY_SCHEME_NAME));
                SchemeColor schemeColor =
                        new SchemeColor(cursor.getString(cursor.getColumnIndex("color_name")),
                                        cursor.getInt(cursor.getColumnIndex(KEY_COLOR_ID)));

                if (schemeMap.containsKey(schemeName)) {
                    schemeMap.get(schemeName).add(schemeColor);
                } else {
                    ArrayList<SchemeColor> schemeList = new ArrayList<SchemeColor>();
                    schemeList.add(schemeColor);
                    schemeMap.put(schemeName, schemeList);
                }
            }

            for (Map.Entry<String, ArrayList<SchemeColor>> entry : schemeMap.entrySet()) {
                Scheme scheme = new Scheme();
                scheme.setName(entry.getKey());
                scheme.setColorList(entry.getValue());
                result.add(scheme);
            }

            db.close();
        }
        return result;
    }

    public List<String> getSchemeNames() {
        List<String> result = Collections.emptyList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if (db != null) {
            result = new ArrayList<String>();
            String query = String.format("SELECT DISTINCT %s FROM %s ORDER BY %s",
                    KEY_SCHEME_NAME,
                    TABLE_SCHEMES,
                    KEY_SCHEME_NAME);
            cursor = db.rawQuery(query, null);

            while (cursor.moveToNext()) {
                result.add(cursor.getString(cursor.getColumnIndex(KEY_SCHEME_NAME)));
            }
            db.close();
        }
        return result;
    }

    public List<SchemeColor> getSchemeFromName(String schemeName) {
        List<SchemeColor> result = Collections.emptyList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if (db != null) {
            result = new ArrayList<SchemeColor>();
            //"SELECT s.name, s.id, c.name FROM schemes s JOIN colors c ON s.id=c.id WHERE s.name=? ORDER BY c.name, s.id"
            String query = String.format("SELECT s.%s, s.%s, c.%s FROM %s s JOIN %s c ON s.%s=c.%s WHERE s.%s=? ORDER BY c.%s, s.%s",
                    KEY_SCHEME_NAME,
                    KEY_SCHEME_ID,
                    KEY_COLOR_NAME,
                    TABLE_SCHEMES,
                    TABLE_COLORS,
                    KEY_SCHEME_ID,
                    KEY_COLOR_ID,
                    KEY_SCHEME_NAME,
                    KEY_COLOR_NAME,
                    KEY_SCHEME_ID);
            cursor = db.rawQuery(query, new String[] {schemeName});
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_SCHEME_ID));
                String name = cursor.getString(cursor.getColumnIndex(KEY_SCHEME_NAME));
                result.add(new SchemeColor(name, id));
            }
            db.close();
        }
        return result;
    }
}

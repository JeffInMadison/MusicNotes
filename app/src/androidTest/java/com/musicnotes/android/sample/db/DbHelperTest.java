package com.musicnotes.android.sample.db;

import android.test.AndroidTestCase;

import com.musicnotes.android.sample.model.Scheme;
import com.musicnotes.android.sample.model.SchemeColor;

import java.util.List;

public class DbHelperTest extends AndroidTestCase {

    public void testDeleteAllSchemes() throws Exception {
        DbHelper dbHelper = new DbHelper(getContext());
        dbHelper.deleteAllSchemes();
    }

    public void testDeleteSchemeByName() throws Exception {
        DbHelper dbHelper = new DbHelper(getContext());
        dbHelper.deleteAllSchemes();
        List<SchemeColor> schemeColors = dbHelper.getSchemeColors();

        dbHelper.addScheme("test", schemeColors);
        dbHelper.addScheme("foo", schemeColors);

        assertEquals(2, dbHelper.getSchemeNames().size());
        dbHelper.deleteSchemeByName("test");
        assertEquals(1, dbHelper.getSchemeNames().size());

    }

    public void testGetSchemeColors() throws Exception {
        DbHelper dbHelper = new DbHelper(getContext());
        List<SchemeColor> schemeColors = dbHelper.getSchemeColors();
        assertEquals(6, schemeColors.size());

    }

    public void testSchemeNameExists() throws Exception {
        DbHelper dbHelper = new DbHelper(getContext());
        dbHelper.deleteAllSchemes();
        List<SchemeColor> schemeColors = dbHelper.getSchemeColors();

        dbHelper.addScheme("test", schemeColors);
        dbHelper.addScheme("foo", schemeColors);

        assertTrue(dbHelper.schemeNameExists("test"));
        assertTrue(dbHelper.schemeNameExists("foo"));
        assertFalse(dbHelper.schemeNameExists("jeff"));
    }

    public void testAddScheme() throws Exception {
        DbHelper dbHelper = new DbHelper(getContext());
        dbHelper.deleteAllSchemes();
        List<SchemeColor> schemeColors = dbHelper.getSchemeColors();

        dbHelper.addScheme("test", schemeColors);


    }

    public void testGetSchemes() throws Exception {
        DbHelper dbHelper = new DbHelper(getContext());
        List<SchemeColor> schemeColors = dbHelper.getSchemeColors();

        dbHelper.addScheme("test", schemeColors);
        dbHelper.addScheme("foo", schemeColors);

        List<Scheme> result = dbHelper.getAllSchemes();
        assertEquals(2, result.size());
    }

    public void testGetSchemeNames() throws Exception {
        DbHelper dbHelper = new DbHelper(getContext());


        List<Scheme> test = dbHelper.getAllSchemes();

    }

    public void testGetSchemeFromName() throws Exception {
        DbHelper dbHelper = new DbHelper(getContext());
        dbHelper.deleteAllSchemes();
        List<SchemeColor> schemeColors = dbHelper.getSchemeColors();

        dbHelper.addScheme("test", schemeColors);

        List<SchemeColor> scheme = dbHelper.getSchemeFromName("test");
        assertEquals(6, scheme.size());

    }
}
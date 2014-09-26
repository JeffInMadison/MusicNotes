package com.musicnotes.android.sample.db;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import com.musicnotes.android.sample.model.SchemeColor;

import junit.framework.TestCase;

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

    public void testGetSchemeNames() throws Exception {

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
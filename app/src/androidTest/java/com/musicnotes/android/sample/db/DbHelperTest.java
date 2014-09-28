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

    public void testFillTest() throws Exception {
        DbHelper dbHelper = new DbHelper(getContext());
        List<SchemeColor> schemeColors = dbHelper.getSchemeColors();

        dbHelper.addScheme("one", schemeColors.subList(0,1));
        dbHelper.addScheme("two", schemeColors.subList(0,2));
        dbHelper.addScheme("three", schemeColors.subList(0,3));
        dbHelper.addScheme("four", schemeColors.subList(0,4));
        dbHelper.addScheme("five", schemeColors.subList(0,5));
        dbHelper.addScheme("all", schemeColors);

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
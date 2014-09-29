package com.musicnotes.android.sample.db;

import android.test.AndroidTestCase;

import com.musicnotes.android.sample.model.Scheme;
import com.musicnotes.android.sample.model.SchemeColor;

import java.util.List;

public class DbHelperTest extends AndroidTestCase {

    public void testDeleteAllSchemes() throws Exception {
        DbHelper.getInstance().deleteAllSchemes();
    }

    public void testDeleteSchemeByName() throws Exception {
        DbHelper.getInstance().deleteAllSchemes();
        List<SchemeColor> schemeColors = DbHelper.getInstance().getSchemeColors();

        DbHelper.getInstance().addScheme("test", schemeColors);
        DbHelper.getInstance().addScheme("foo", schemeColors);

        assertEquals(2, DbHelper.getInstance().getSchemeNames().size());
        DbHelper.getInstance().deleteSchemeByName("test");
        assertEquals(1, DbHelper.getInstance().getSchemeNames().size());

    }

    public void testGetSchemeColors() throws Exception {
        List<SchemeColor> schemeColors = DbHelper.getInstance().getSchemeColors();
        assertEquals(7, schemeColors.size());

    }

    public void testSchemeNameExists() throws Exception {
        DbHelper.getInstance().deleteAllSchemes();
        List<SchemeColor> schemeColors = DbHelper.getInstance().getSchemeColors();

        DbHelper.getInstance().addScheme("test", schemeColors);
        DbHelper.getInstance().addScheme("foo", schemeColors);

        assertTrue(DbHelper.getInstance().schemeNameExists("test"));
        assertTrue(DbHelper.getInstance().schemeNameExists("foo"));
        assertFalse(DbHelper.getInstance().schemeNameExists("jeff"));
    }

    public void testAddScheme() throws Exception {
        DbHelper.getInstance().deleteAllSchemes();
        List<SchemeColor> schemeColors = DbHelper.getInstance().getSchemeColors();

        DbHelper.getInstance().addScheme("test", schemeColors);
    }

    /**
     * This isn't really a test. It's used to fill the db with some samples to test on fresh install
     * to make testing the UI easier
     * @throws Exception
     */
    public void testFillTest() throws Exception {
        List<SchemeColor> schemeColors = DbHelper.getInstance().getSchemeColors();

        DbHelper.getInstance().addScheme("one", schemeColors.subList(0,1));
        DbHelper.getInstance().addScheme("two", schemeColors.subList(0,2));
        DbHelper.getInstance().addScheme("three", schemeColors.subList(0,3));
        DbHelper.getInstance().addScheme("four", schemeColors.subList(0,4));
        DbHelper.getInstance().addScheme("five", schemeColors.subList(0,5));
        DbHelper.getInstance().addScheme("six", schemeColors.subList(0,6));
        DbHelper.getInstance().addScheme("all", schemeColors);

    }

    public void testGetSchemes() throws Exception {
        List<SchemeColor> schemeColors = DbHelper.getInstance().getSchemeColors();

        DbHelper.getInstance().addScheme("test", schemeColors);
        DbHelper.getInstance().addScheme("foo", schemeColors);

        List<Scheme> result = DbHelper.getInstance().getAllSchemes();
        assertEquals(2, result.size());
    }

    public void testGetSchemeNames() throws Exception {
        List<Scheme> test = DbHelper.getInstance().getAllSchemes();
        assertNotNull(test);
    }

    public void testGetSchemeFromName() throws Exception {
        DbHelper.getInstance().deleteAllSchemes();
        List<SchemeColor> schemeColors = DbHelper.getInstance().getSchemeColors();

        DbHelper.getInstance().addScheme("test", schemeColors);

        List<SchemeColor> scheme = DbHelper.getInstance().getSchemeFromName("test");
        assertEquals(6, scheme.size());
    }
}
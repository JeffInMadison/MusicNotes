package com.musicnotes.android.sample.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;
/**
 * Created by Jeff on 9/25/2014.
 * Copyright JeffInMadison.com 2014
 */
@SuppressWarnings("UnusedDeclaration")
public class StringUtils {

    public static final String EMPTY_STRING = "";
    public static final String NEW_LINE = System.getProperty("line.separator");

    public static boolean isNullOrEmpty(String string) {
        return (string == null || string.length() == 0);
    }

    public static boolean isNullOrEmpty(CharSequence sequence) {
        return (sequence == null || sequence.length() == 0);
    }

    public static String urlEncodeUtf8(String string) throws UnsupportedEncodingException {
        return URLEncoder.encode(string, "UTF-8");
    }

    public static String ceateDashlessUUID() {
        return UUID.randomUUID().toString().replace("-", EMPTY_STRING);
    }

    public static String getCommaSeparatedString(Iterable<String> list) {
        return getCommaSeparatedString(list, false);
    }
    public static String getCommaSeparatedString(Iterable<String> list, boolean addSpace) {
        String separator = addSpace ? ", " : ",";
        Iterator<String> iterator = list.iterator();
        StringBuilder commaStringBuilder = new StringBuilder(EMPTY_STRING);
        String current = EMPTY_STRING;
        if (iterator.hasNext()) {
            current = iterator.next();
            commaStringBuilder.append(current);
        }

        while (iterator.hasNext()) {
            String nextString = iterator.next();
            if (isNullOrEmpty(current) || isNullOrEmpty(nextString)) {
                commaStringBuilder.append(nextString);
                current = nextString;
            } else {
                commaStringBuilder.append(separator);
                commaStringBuilder.append(nextString);
                current = nextString;
            }
        }
        return commaStringBuilder.toString();
    }

    /**
     * Trims trailing whitespace. Removes any of these characters:
     * 0009, HORIZONTAL TABULATION
     * 000A, LINE FEED
     * 000B, VERTICAL TABULATION
     * 000C, FORM FEED
     * 000D, CARRIAGE RETURN
     * 001C, FILE SEPARATOR
     * 001D, GROUP SEPARATOR
     * 001E, RECORD SEPARATOR
     * 001F, UNIT SEPARATOR
     *
     * @return "" if source is null, otherwise string with all trailing whitespace removed
     */
    public static CharSequence trimTrailingWhitespace(CharSequence source) {

        if (source == null)
            return "";

        int i = source.length();

        // loop back to the first non-whitespace character
        do {
            i--;
        } while (i >= 0 && Character.isWhitespace(source.charAt(i)));

        return source.subSequence(0, i + 1);
    }

    public static String stringFromBuffer(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}

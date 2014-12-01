package org.ravenlabs.fuelpass.core;

import java.util.List;

/**
 * Created by Ravenheart on 30.11.2014 г..
 */
public class StringUtils {

    public static String Join(String separator, String[] array) {
        if (array.length == 0)
            return "";
        if (array.length == 1)
            return array[0];

        StringBuilder bld = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            bld.append(array[i]);
            bld.append(separator);
        }

        bld.delete(bld.length() - separator.length(), separator.length());
        return bld.toString();
    }

    public static String Join(String separator, List<String> array) {
        if (array.size() == 0)
            return "";
        if (array.size() == 1)
            return array.get(0);

        StringBuilder bld = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            bld.append(array.get(i));
            bld.append(separator);
        }

        bld.delete(bld.length() - separator.length(), separator.length());
        return bld.toString();
    }
}

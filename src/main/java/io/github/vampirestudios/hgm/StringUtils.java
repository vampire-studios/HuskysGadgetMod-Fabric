//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm;

import org.apache.commons.lang3.text.StrSubstitutor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

public class StringUtils {
    public StringUtils() {
    }

    public static String toLowerCase(String str) {
        return str.toLowerCase(Locale.ROOT);
    }

    public static String toUpperCase(String str) {
        return str.toUpperCase(Locale.ROOT);
    }

    public static boolean endsWith(String search, String... endings) {
        String lowerSearch = toLowerCase(search);
        Stream<String> var10000 = Stream.of(endings);
        lowerSearch.getClass();
        return var10000.anyMatch(lowerSearch::endsWith);
    }

    public static URL toURL(String string) {
        try {
            return new URL(string);
        } catch (MalformedURLException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static String parseStringFormat(String input, Map<String, String> properties) {
        return StrSubstitutor.replace(input, properties);
    }
}

package com.boilerplate.demo.helper.utils;

import com.github.slugify.Slugify;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class StringHelper {

    // put Slugify in utils class to make sure this instance is not modified by accident
    // Slugify is not thread safe, however we ca use it in multi-threaded environments if we don't modify the settings
    // at runtime.
    // We need Slugify as singleton to have good performance over time
    private static final Slugify SLUGIFY = new Slugify();

    public static String normalizeFilename(String filename) {
        if (isBlank(filename)) {
            return UUID.randomUUID().toString();
        }
        String baseName = FilenameUtils.getBaseName(filename);
        baseName = (isBlank(baseName) ? UUID.randomUUID().toString() : baseName);

        String extension = FilenameUtils.getExtension(filename);
        extension = (isBlank(extension) ? EMPTY : "." + lowerCase(extension));

        String slug = SLUGIFY.slugify(baseName) + extension;
        return slug.replaceAll("_", "-");
    }

    public static List<String> splitByComma(String s) {
        if (org.apache.commons.lang3.StringUtils.isBlank(s)) {
            return new ArrayList<>();
        }
        String[] splits = split(s, ",");
        return Arrays.stream(splits)
                .map(split -> trimToNull(split))
                .filter(split -> split != null)
                .collect(Collectors.toList());
    }

    public static String capAndRemoveAccents(String s) {
        if (StringUtils.isBlank(s)) {
            return s;
        }
        return StringUtils.stripAccents(s).toUpperCase();
    }

    public static String unescapeJson(String json) {
        String result = StringEscapeUtils.unescapeJson(json);
        result = StringUtils.removeStart(result, "\"");
        result = StringUtils.removeEnd(result, "\"");
        return result;
    }
}

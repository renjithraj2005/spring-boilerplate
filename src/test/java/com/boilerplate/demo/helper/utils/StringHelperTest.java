package com.boilerplate.demo.helper.utils;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static com.boilerplate.demo.helper.utils.StringHelper.*;

public class StringHelperTest {

    @Test
    public void test_normalizeFilename() throws Exception {

        Assertions.assertEquals("foobar-hello-world-this-is-a-funky-string.txt",
                normalizeFilename("fóòbâr\t Hello_World !! Tĥïŝ_ĩš â_fůňķŷ Šťŕĭńġ.TXT"));

        Assertions.assertEquals(40, normalizeFilename("         .TXT").length());
        Assertions.assertEquals(36, normalizeFilename("     ").length());
        Assertions.assertEquals(36, normalizeFilename(null).length());
    }

    @Test
    public void test_capAndRemoveAccents() throws Exception {

        Assertions.assertEquals("DECA", capAndRemoveAccents("deçà"));
        Assertions.assertEquals("AURELIE", capAndRemoveAccents("Aurélie"));
        Assertions.assertEquals("CEDILLE C", capAndRemoveAccents("cédille Ç"));
        Assertions.assertEquals("A E I O U", capAndRemoveAccents("â ê î ô û"));
        Assertions.assertEquals("A E U", capAndRemoveAccents("à è ù"));
        Assertions.assertEquals("E I U", capAndRemoveAccents("ë ï ü"));
    }

    @Test
    public void test_unescapeJson() throws Exception {
        Assertions.assertEquals("{\"companyId\":\"38859638900030\"}", unescapeJson("{\"companyId\":\"38859638900030\"}"));
        Assertions.assertEquals("{\"companyId\":\"38859638900030\"}", unescapeJson("\"{\\\"companyId\\\":\\\"38859638900030\\\"}\""));
    }

}

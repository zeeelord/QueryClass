import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryJUnitTest {

    private Query q;

    @BeforeEach
    void setUp() {

        q = new Query("name=Sam+Lee&age=20&password=abc%21");
    }

    @Test
    void hasParameter_existingParameter_returnsTrue() {

        assertTrue(q.hasParameter("password"));
    }

    @Test
    void hasParameter_missingParameter_returnsFalse() {

        assertFalse(q.hasParameter("email"));
    }

    @Test
    void getParameterOffset_firstParameter_returnsZero() {

        assertEquals(0, q.getParameterOffset("name"));
    }

    @Test
    void getParameterOffset_middleParameter() {

        assertEquals(13, q.getParameterOffset("age"));
    }

    @Test
    void getParameterOffset_lastParameter() {

        assertEquals(20, q.getParameterOffset("password"));
    }

    @Test
    void getParameterOffset_missingParameter_returnsMinusOne() {

        assertEquals(-1, q.getParameterOffset("email"));
    }

    @Test
    void getParameter_decodesPlusAsSpace() {

        assertEquals("Sam Lee", q.getParameter("name"));
    }

    @Test
    void getParameter_middleParameter() {

        assertEquals("20", q.getParameter("age"));
    }

    @Test
    void getParameter_decodesPercentEncoding() {

        assertEquals("abc!", q.getParameter("password"));
    }

    @Test
    void getParameter_missingParameter_returnsNull() {

        assertNull(q.getParameter("email"));
    }

    @Test
    void decode_plainText_unchanged() {

        assertEquals("hello", Query.decode("hello"));
    }

    @Test
    void decode_plusAndPercent() {

        assertEquals("a b!", Query.decode("a+b%21"));
    }

    @Test
    void decode_emptyString() {

        assertEquals("", Query.decode(""));
    }
}
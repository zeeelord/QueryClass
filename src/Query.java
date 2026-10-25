/*
 * Represents a query string, such as "foo=bar&baz=1234".
 * See https://en.wikipedia.org/wiki/Query_string
 */
class Query {

    String data;

    Query(String data) {

        this.data = data;
    }

    /*
     * Decodes a URL-encoded string.
     * See https://en.wikipedia.org/wiki/Percent-encoding
     */
    static String decode(String s) {

        String result = "";
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c == '%') {
                // decode two hexadecimal digits after the % symbol
                String hex = s.substring(i + 1, i + 3);
                int charCode = Integer.parseInt(hex, 16);
                c = (char) charCode;
                // skip two more characters
                i += 2;
            } else if (c == '+') {
                // + symbol is decoded to a space
                c = ' ';
            }
            result += c;
            i++;
        }
        return result;
    }

    /*
     * Returns the value associated with a parameter in the query string,
     * or null if the parameter does not exist.
     */
    String getParameter(String name) {

        int offset = getParameterOffset(name);
        if (offset == -1) {
            return null;
        }
        // get the position after the next = symbol
        int begin = data.indexOf('=', offset) + 1;
        // get the position of the next & symbol
        int end = data.indexOf('&', begin);
        if (end == -1) {
            // if there is no & symbol, this is the last parameter,
            // so read to the end of the string
            end = data.length();
        }
        String value = data.substring(begin, end);
        return decode(value);
    }

    /*
     * Determines whether a parameter exists in the query string.
     */
    boolean hasParameter(String name) {

        return getParameterOffset(name) >= 0;
    }

    /*
     * Returns the character offset of the start of a parameter's name.
     * If the parameter does not exist, -1 is returned.
     */
    int getParameterOffset(String name) {

        if (data.startsWith(name + '=')) {
            // parameter appears at the start of the query string
            return 0;
        }
        int offset = data.indexOf('&' + name + '=');
        if (offset != -1) {
            // the parameter exists, so skip the & symbol
            offset++;
        }
        return offset;
    }
}
package cloud.tianai.captcha.service.util;

public class Base64Util {

    private static final String _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    //for test
    public static void main(String[] args) {
        //String encoded = "eyJiZ0ltYWdlV2lkdGgiOjMwMCwiYmdJbWFnZUhlaWdodCI6MTgwLCJzbGlkZXJJbWFnZVdpZHRoIjo1NSwic2xpZGVySW1hZ2VIZWlnaHQiOjE4MCwidHJhY2tMaXN0IjpbeyJ4IjowLCJ5IjowLCJ0eXBlIjoiZG93biIsInQiOjI4OH0seyJ4IjoxLCJ5IjowLCJ0eXBlIjoibW92ZSIsInQiOjQzOX0seyJ4Ijo0LCJ5IjowLCJ0eXBlIjoibW92ZSIsInQiOjQ0N30seyJ4IjoxMiwieSI6MCwidHlwZSI6Im1vdmUiLCJ0Ijo0NTV9LHsieCI6MjAsInkiOjAsInR5cGUiOiJtb3ZlIiwidCI6NDYzfSx7IngiOjI3LCJ5IjowLCJ0eXBlIjoibW92ZSIsInQiOjQ3MX0seyJ4IjozNSwieSI6MCwidHlwZSI6Im1vdmUiLCJ0Ijo0Nzl9LHsieCI6NDEsInkiOjAsInR5cGUiOiJtb3ZlIiwidCI6NDg3fSx7IngiOjQ3LCJ5IjowLCJ0eXBlIjoibW92ZSIsInQiOjQ5NX0seyJ4Ijo1MywieSI6MCwidHlwZSI6Im1vdmUiLCJ0Ijo1MDN9LHsieCI6NTksInkiOjEsInR5cGUiOiJtb3ZlIiwidCI6NTExfSx7IngiOjYzLCJ5IjoyLCJ0eXBlIjoibW92ZSIsInQiOjUxOX0seyJ4Ijo2NSwieSI6MiwidHlwZSI6Im1vdmUiLCJ0Ijo1Mjd9LHsieCI6NjUsInkiOjMsInR5cGUiOiJtb3ZlIiwidCI6NjMxfSx7IngiOjY2LCJ5IjozLCJ0eXBlIjoibW92ZSIsInQiOjYzOX0seyJ4Ijo2OCwieSI6MywidHlwZSI6Im1vdmUiLCJ0Ijo2NDh9LHsieCI6NzEsInkiOjMsInR5cGUiOiJtb3ZlIiwidCI6NjU1fSx7IngiOjc1LCJ5IjoyLCJ0eXBlIjoibW92ZSIsInQiOjY2M30seyJ4Ijo3NywieSI6MSwidHlwZSI6Im1vdmUiLCJ0Ijo2NzF9LHsieCI6NzksInkiOjEsInR5cGUiOiJtb3ZlIiwidCI6Njc5fSx7IngiOjgxLCJ5IjoxLCJ0eXBlIjoibW92ZSIsInQiOjY4N30seyJ4Ijo4MywieSI6MSwidHlwZSI6Im1vdmUiLCJ0Ijo2OTV9LHsieCI6ODUsInkiOjEsInR5cGUiOiJtb3ZlIiwidCI6NzAzfSx7IngiOjg2LCJ5IjoxLCJ0eXBlIjoibW92ZSIsInQiOjcxMX0seyJ4Ijo4OCwieSI6MSwidHlwZSI6Im1vdmUiLCJ0Ijo4Mzl9LHsieCI6OTAsInkiOjEsInR5cGUiOiJtb3ZlIiwidCI6ODU1fSx7IngiOjkyLCJ5IjoxLCJ0eXBlIjoibW92ZSIsInQiOjg2M30seyJ4Ijo5MywieSI6MSwidHlwZSI6Im1vdmUiLCJ0Ijo4NzF9LHsieCI6OTQsInkiOjEsInR5cGUiOiJtb3ZlIiwidCI6ODc5fSx7IngiOjk1LCJ5IjoxLCJ0eXBlIjoibW92ZSIsInQiOjg4N30seyJ4Ijo5NiwieSI6MSwidHlwZSI6Im1vdmUiLCJ0Ijo4OTV9LHsieCI6OTcsInkiOjEsInR5cGUiOiJtb3ZlIiwidCI6OTAzfSx7IngiOjk4LCJ5IjoxLCJ0eXBlIjoibW92ZSIsInQiOjkxMX0seyJ4Ijo5OSwieSI6MSwidHlwZSI6Im1vdmUiLCJ0Ijo5NzV9LHsieCI6MTAwLCJ5IjoxLCJ0eXBlIjoibW92ZSIsInQiOjk4M30seyJ4IjoxMDIsInkiOjEsInR5cGUiOiJtb3ZlIiwidCI6OTkxfSx7IngiOjEwMywieSI6MSwidHlwZSI6Im1vdmUiLCJ0Ijo5OTl9LHsieCI6MTA0LCJ5IjoxLCJ0eXBlIjoibW92ZSIsInQiOjEwMDd9LHsieCI6MTA2LCJ5IjoxLCJ0eXBlIjoibW92ZSIsInQiOjEwMTV9LHsieCI6MTA3LCJ5IjoxLCJ0eXBlIjoibW92ZSIsInQiOjEwMjN9LHsieCI6MTA4LCJ5IjoxLCJ0eXBlIjoibW92ZSIsInQiOjEwMzF9LHsieCI6MTA5LCJ5IjoxLCJ0eXBlIjoibW92ZSIsInQiOjEwNDd9LHsieCI6MTEwLCJ5IjoxLCJ0eXBlIjoibW92ZSIsInQiOjEwNzF9LHsieCI6MTEwLCJ5IjoxLCJ0eXBlIjoidXAiLCJ0IjoxMzQzfV0sInN0YXJ0VGltZSI6IjIwMjQtMDctMzFUMTA6Mjc6MzguMTczWiIsInN0b3BUaW1lIjoiMjAyNC0wNy0zMVQxMDoyNzozOS41MTZaIn0=";
        String encoded = "your encoded string here";
        String decoded = decode(encoded);
        System.out.println(decoded);
    }

    public static String decode(String input) {
        StringBuilder output = new StringBuilder();
        int n, r, i;
        int s, o, u, a;
        int f = 0;
        input = input.replaceAll("[^A-Za-z0-9+/=]", "");

        while (f < input.length()) {
            s = _keyStr.indexOf(input.charAt(f++));
            o = _keyStr.indexOf(input.charAt(f++));
            u = _keyStr.indexOf(input.charAt(f++));
            a = _keyStr.indexOf(input.charAt(f++));

            n = (s << 2) | (o >> 4);
            r = ((o & 15) << 4) | (u >> 2);
            i = ((u & 3) << 6) | a;

            output.append((char) n);

            if (u != 64) {
                output.append((char) r);
            }
            if (a != 64) {
                output.append((char) i);
            }
        }

        return utf8Decode(output.toString());
    }

    private static String utf8Decode(String input) {
        StringBuilder output = new StringBuilder();
        int i = 0;
        int c1, c2, c3;
        while (i < input.length()) {
            c1 = input.charAt(i);
            if (c1 < 128) {
                output.append((char) c1);
                i++;
            } else if (c1 > 191 && c1 < 224) {
                c2 = input.charAt(i + 1);
                output.append((char) (((c1 & 31) << 6) | (c2 & 63)));
                i += 2;
            } else {
                c2 = input.charAt(i + 1);
                c3 = input.charAt(i + 2);
                output.append((char) (((c1 & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63)));
                i += 3;
            }
        }
        return output.toString();
    }
}
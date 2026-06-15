import java.util.Arrays;

public class ReverseNumber {
    public static void main(String[] args) {

        String s = "Geeks";
        String r = "";

        for (int i = 0; i < s.length(); i++){

            // prepend each character
            r = s.charAt(i) + r;
        }
        System.out.println(r);

    }
}

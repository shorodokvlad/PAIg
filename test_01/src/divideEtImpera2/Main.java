package divideEtImpera2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        //String directions = "LFRFRFR";

        //String directions = "FLLF";

        String directions = "RF";

        boolean facingForward = true;

        boolean facingRight = false;

        boolean facingLeft = false;

        boolean movedForward = false;


        int x = 0, y = 0;
        int[] coordinates = {0, 0};

        for (int i = 0; i < directions.length(); i++) {
            char c = directions.charAt(i);

            int r = 0, l = 0;

            if (c == 'F') {
                //x++;
                movedForward = true;

            } else if (c == 'R') {
                facingRight = true;

            } else if (c == 'L') {
                facingLeft = true;
            }

            if (facingRight && movedForward) {
                y--;
                x++;
                facingRight = !facingRight;
            } else if (facingLeft && movedForward) {
                y++;
                x++;
                facingLeft = !facingLeft;
            }
        }
        System.out.println(x + " " + y);


    }
}

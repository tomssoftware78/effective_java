package be.tvde.B_methods_common_to_all_objects.A_equals.transitive_rule;

import java.awt.*;

public class PointApp {

    public static void main(String[] args) {
        Point p = new Point(1, 2);
        ColorPoint cp = new ColorPoint(1, 2, Color.BLUE);

        System.out.println(p.equals(cp));
        System.out.println(cp.equals(p));
    }
}

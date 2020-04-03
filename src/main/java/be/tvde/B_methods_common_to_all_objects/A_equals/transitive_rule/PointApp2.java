package be.tvde.B_methods_common_to_all_objects.A_equals.transitive_rule;

import java.util.Set;

public class PointApp2 {

    private static final Set<Point2> unitCircle = Set.of(
            new Point2(1, 0), new Point2(0, 1),
            new Point2(-1, 0), new Point2(0, -1));

    public static void main(String[] args) {
        Point2 p = new Point2(1, 0);
        System.out.println(onUnitCircle(p));
    }

    private static boolean onUnitCircle(Point2 p) {
        return unitCircle.contains(p);
    }
}

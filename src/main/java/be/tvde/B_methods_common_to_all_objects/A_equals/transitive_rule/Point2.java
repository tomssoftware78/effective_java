package be.tvde.B_methods_common_to_all_objects.A_equals.transitive_rule;

public class Point2 {

    private final int x;
    private final int y;

    public Point2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass())
            return false;

        Point2 p = (Point2) o;
        return p.x == x && p.y == y;
    }
}

package be.tvde.B_methods_common_to_all_objects.A_equals.transitive_rule;

import java.awt.*;
import java.util.Objects;

public class ColorPoint2 {

    private final Point point;
    private final Color color;

    public ColorPoint2(Point point, Color color) {
        this.point = point;
        this.color = Objects.requireNonNull(color);
    }

    public Point asPoint() {
        return point;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ColorPoint2))
            return false;
        ColorPoint2 cp = (ColorPoint2) o;

        return cp.point.equals(point) && cp.color.equals(color);
    }
}

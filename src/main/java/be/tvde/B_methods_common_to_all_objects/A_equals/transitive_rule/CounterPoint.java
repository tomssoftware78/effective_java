package be.tvde.B_methods_common_to_all_objects.A_equals.transitive_rule;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterPoint extends Point2 {

    private static final AtomicInteger counter = new AtomicInteger();

    public CounterPoint(int x, int y) {
        super(x, y);
        counter.incrementAndGet();
    }

    public static int numberCreated() {
        return counter.get();
    }
}

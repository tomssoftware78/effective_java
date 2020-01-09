package be.tvde.A_creating_destroying_objects.G_creating_unneccessary_objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SetTest {

    public static void main(String[] args) {
        Map<String, String> m = new HashMap<>();

        m.put("a_key", "a_value");
        m.put("b_key", "b_value");
        m.put("c_key", "c_value");

        Set<String> keySet1 = m.keySet();

        m.put("d_key", "d_value");
        Set<String> keySet2 = m.keySet();

        System.out.println(keySet1 == keySet2);
        System.out.println(keySet1.equals(keySet2));
    }
}

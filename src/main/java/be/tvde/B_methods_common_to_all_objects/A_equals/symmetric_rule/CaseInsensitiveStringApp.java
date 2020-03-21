package be.tvde.B_methods_common_to_all_objects.A_equals.symmetric_rule;

import be.tvde.B_methods_common_to_all_objects.A_equals.symmetric_rule.CaseInsensitiveString;

import java.util.ArrayList;
import java.util.List;

public class CaseInsensitiveStringApp {

    public static void main(String[] args) {
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        String s = "polish";

        System.out.println(cis.equals(s)); //true
        System.out.println(s.equals(cis)); //false


        List<CaseInsensitiveString> list = new ArrayList<>();
        list.add(cis);

        System.out.println(list.contains(s)); //false
    }
}

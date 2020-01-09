Avoid creating unneccessary objects
===================================

Often it is appropriate to reuse a single object instead of creating a new functionally equivalent object eacht time
it is needed.
When an object is immutable, it can be reused!

Extreme example:
    Use
                    String s = "my string";
    instead of
                    String s = new String("my string");
    particularly in a loop or frequently invoked method, otherwise many String instances can be created needlessly

    --> as a side-effect it is guaranteed that the object (String) will be reused by any other code running in the same
        virtual machine that happens to contain the same string literal

You often avoid creating unnecessary objects by using static factory methods in preference to constructors on
 immutable classes that provide both.
        example:    factory method          Boolean.valueOf(String)
                            - is not required to create new instance
                    constructor             Boolean(String)
                            - deprecated in Java 9
                            - constructor must create new object each time it's called

Some object creations are much more expensive than others. If you're going to need such an "expensive object" repeatedly
    it may be advisable to cache it for reuse.
    Unfortunately, it's not always obious when you're creating such an object:

        static boolean isRomanNumber(String s) {
            return s.matches("^(?=.)M*(C[MD]|D.C{0,3})([CL]|L?X{0.3})(I[XV]|V?I{0,3})$");
        }
        --> this method relies on String.matches which internally creates a Pattern instance (expensive operation) for
                the regular expression and uses it only once, after which it becomes eligible for GC

        Better is:
            public class RomanNumerals {
                private static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D.C{0,3})...$");

                static boolean isRomanNumeral(String s) {
                    return ROMAN.matcher(s).matches;
                }
            }

            Advantages: - increased performance
                        - improved readability, the Roman Pattern is now explicitly mentioned in the code


!!!There is no need to create more than 1 instance of a given adapter to a given object.
        This is because an adapter is an object that delegates to a backing object, providing an alternative
        interface. An adapter has no state beyond that of its backing object

        example: keySet() method of the Map interface returns always the same Set object, creating multiple
                    instances is unnecessary and has no benefits.

!!!Watch out with mixing primitive and boxed primitive types, because autoboxing creates unnecessary objects

    private static long sum() {
        Long sum = 0L; // (*)
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum +=i;
        }
        return sum;
    }

    (*) causes the program to construct Long instances that are garbage collected immediately
        changing to long sum reduces the runtime from 6.3 seconds to 0.59 seconds
    ==> prefer primitives to boxed primitives, and watch out for unintentional autoboxing

This item is about "Don't create a new object when you should reuse an existing one"
Couterpoint is item 50 "Don't reuse an existing object when you should create a new one"
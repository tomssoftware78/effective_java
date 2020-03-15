Enforce non-instantiability with a private constructor
======================================================

Classes with only static fields and static methods have a bad reputation because some people abuse them to
avoid thinking in terms of objects.
But they have valid uses:   - group related methods on primitive values or arrays
                                example:    java.lang.Math
                                            java.util.Arrays
                            - group static methods, including factories, for objects that implement some interface
                                example: java.util.Collections
                                --> As of Java 8, you can put such methods also in the interface assuming you
                                    own the interface (Java 8 can have static methods in interfaces)
                            - group methods on final classes, since you can't put them in a subclass

These utility classes are not designed to be instantiated: an instance would be meaningless

--> include a private constructor to make a class non-instantiable

    public class UtilityClass {
        //Suppress default constructor
        private UtilityClass() {
            throw new AssertionError(); //isn't stricty required, but warns you is accidentally invoked from within
                                                                    the class
        }
    }

This approach also prevents the class from being subclassed as all subclassed will try to call the superclass'
constructor (explicitly or implicity) which is not accessible!
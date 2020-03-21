Obey the general contract when overriding equals
================================================

The easiest way to avoid problems is not to override the equals method, in which case each instance of the class is equal only
to itself. This is the right thing to do if any of the following conditions apply:

    - Each instance of the class is inherently unique.
        example:
            Thread  --> Thread objects represent active entities rather than values

    - You don’t care whether the class provides a “logical equality” test.
        example:
            java.util.Random --> designers decided that every instance of Random is unique

    - There is no need for the class to provide a "logical equality" test
        example:
            java.util.regex.Pattern --> designers decided that clients would not need or want to
                                        check if 2 Pattern instances represent exactly the same
                                        regular expression
    - the class uses instance control (see item 1)

    - A superclass has already overridden equals in a way appropriate for this class
        example:
            most Set implementations inherit their equals implementation from AbstractSet
            most Map implementations inherit their equals implementation from AbstractMap
            most List implementations inherit their equals implementation from AbstractList

    - The class is private or package-private, and you are certain that its equals method will never be invoked.


    !!!Arguably, the equals method should be overridden under these circumstances, in case it is accidentally invoked:

        @Override public boolean equals(Object o) {
            throw new AssertionError(); // Method is never called
        }

So when is it appropriate to override Object.equals?

    When a class has a notion of logical equality that differs from mere object identity, and a superclass has not already overridden
    equals to implement the desired behavior.

    This is generally the case for value classes.
    A value class is simply a class that represents a value, such as Integer or Date.

    A programmer who compares references to value objects using the equals method expects to find out whether they are logically
    equivalent, not whether they refer to the same object.

When you override the equals method, you must adhere to its general contract. Here is the contract, copied from the specification
for Object:
    The equals() method implements an EQUIVALENCE RELATION which has these properties:

        - REFLEXIVE     for any non-null reference value x, x.equals(x) must return true

        - SYMMETRIC     for any non-null reference values x and y, x.equals(y) must return true if and only if y.equals(x) returns true

                This rules is easily violated. See CaseInsensitiveString class.
                    - the attempt to be interoperable with ordinary strings, makes the contract fail
                    - solution: remove the ill-conceived attempt to interoperate with String

        - TRANSITIVE    for any non-null reference values x, y, z,  if x.equals(y) returns true
                                                                    and y.equals(z) returns true
                                                                    then x.equals(z) must return true

                This rule is easily violated, especially when a subclass adds a new value component to its superclass = the subclass
                        adds a piece of information that affects equals comparisons

        - CONSISTENT    for any non-null reference values x and y, multiple invocations of x.equals(y) must consistently return true or
                                            consistently return false if no information used in equals comparison is modified

        - For any non-null reference value x, x.equals(null) must return false


!!!Once you have violated the equals contract, you simply don't know how other objects will behave when confronted with your object


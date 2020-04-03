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

                There is no way to extend an instantiable class and add a value component while preserving the equals contract.
                        See Point and ColorPoint example

                Replacing instanceof with getClass test only helps when equating objects that have the same implementation class.
                    This has an unacceptable consequence: an instance of a subclass of Point is still a Point, and it still needs
                        to function as one, but it fails to do so if you take this approach. Check the PointApp2 example. An
                        instance of a subclass of Point is still a Point, and it still needs to function as one, but it fails to
                        do so in this example. CounterPoint will not work.

                While there is no satisfactory way to extend an instantiable class and add a value component, there is a fine
                workaround:
                    "Favor composition over inheritance": instead of having ColorPoint extend Point, give ColorPöint a private
                        Point field and a public view method --> asPoint() method

                There are some classes in the JDK that do extend an instantiable class and add a value component.

                    Example:    java.sql.Timestamp extends java.util.Date and adds a nanoseconds field

                        The equals implementation for Timestamp does violate symmetry and can cause erratic behavior if
                                Timestamp and Date objects are used in the same collection or are otherwise intermixed.
                        The Timestamp class has a disclaimer cautioning programmers against mixing dates and timestamps.
                                While you won’t get into trouble as long as you keep them separate, there’s nothing to
                                prevent you from mixing them, and the resulting errors can be hard to debug.

                        !!! This behavior of the Timestamp class was a mistake and should not be emulated.

        - CONSISTENT    for any non-null reference values x and y, multiple invocations of x.equals(y) must consistently return true or
                                            consistently return false if no information used in equals comparison is modified

                        Immutable objects can not be equal to different objects at different times

                        Wether or not a class is immutable, do not write an equals method that depends on unreliable resources.
                        it's extrememly difficult to satisfy the consistency requirement if you violate this prohibition.
                        Example: java.net.URL relies on comparison of the IP addresses of the hosts associated wth the URL's
                                        Translating a hostname to an IP address can require network access, and it isn't
                                            guaranteed to yield the same results over time.

        - For any non-null reference value x, x.equals(null) must return false (non-nullity rule)
                        Make sure your equals method returns false in this case and not throws NullPointerException!

                        at first, you might think you have to start the equals method with the following check:
                            if (o == null)
                                return false;
                            ...
                        but this is unnecessary, because the instanceof operator before the cast of the argument to the
                        appropriate type, already returns false if its first operand is null, regardless of what appears
                        in the second operand:
                            if (!(o instanceof MyType))
                                return false;
                            MyType mt = (MyType) o;
                            ...

Equals() recipe for high-quality equals methods:

1. Use the == operator to check if the argument is a reference to this object.
    If so, return true. This is just a performance optimization but one that is worth doing if the comparison is
    potentially expensive.

2. Use the instanceof operator to check if the argument has the correct type.
    If not, return false.
    Typically, the correct type is the class in which the method occurs.
    Occasionally, it is some interface implemented by this class. Use an interface if the class implements an
    interface that refines the equals contract to permit comparisons across classes that implement the interface.
    Collection interfaces such as Set, List, Map, and Map.Entry have this property.

3. Cast the argument to the correct type. Because this cast was preceded by an instanceof test, it is guaranteed to
    succeed.

4. For each “significant” field in the class, check if that field of the argument matches the corresponding field of
    this object.
    If all these tests succeed, return true; otherwise, return false.

    If the type in Step 2 is an interface, you must access the argument’s fields via interface methods; if the type
    is a class, you may be able to access the fields directly, depending on their accessibility.

    - For primitive fields whose type is not float or double, use the == operator for comparisons;
    - For object reference fields, call the equals method recursively;
    - For float fields, use the static Float.compare(float, float) method;
    - For double fields, use Double.compare(double, double).

    --> The special treatment of float and double fields is made necessary by the existence of Float.NaN, -0.0f and
        the analogous double values;
    --> While you could compare float and double fields with the static methods Float.equals and Double.equals, this
        would entail autoboxing on every comparison, which would have poor performance.

    - For array fields, apply these guidelines to each element.
        If every element in an array field is significant, use one of the Arrays.equals methods.

    Some object reference fields may legitimately contain null. To avoid the possibility of a NullPointerException,
        check such fields for equality using the static method Objects.equals(Object, Object).

    For some classes, such as CaseInsensitiveString above, field comparisons are more complex than simple equality
        tests. If this is the case, you may want to store a canonical form of the field so the equals method can do a
        cheap exact comparison on canonical forms rather than a more costly nonstandard comparison.
        This technique is most appropriate for immutable classes; if the object can change, you must keep the canonical
        form up to date.

-> The performance of the equals method may be affected by the order in which fields are compared. For best performance,
you should first compare fields that are more likely to differ, less expensive to compare, or, ideally, both.
-> You must not compare fields that are not part of an object’s logical state, such as lock fields used to synchronize
operations.
-> You need not compare derived fields, which can be calculated from “significant fields,” but doing so may improve
the performance of the equals method.
    If a derived field amounts to a summary description of the entire object, comparing this field will save you the
    expense of comparing the actual data if the comparison fails.
    For example, suppose you have a Polygon class, and you cache the area. If two polygons have unequal areas, you
    needn’t bother comparing their edges and vertices.

-> When you are finished writing your equals method, ask yourself three questions:
        - Is it symmetric?
        - Is it transitive?
        - Is it consistent?
   And don’t just ask yourself; write unit tests to check, unless you used AutoValue to generate your equals method, in
   which case you can safely omit the tests.

   Of course your equals method must also satisfy the other two properties (reflexivity and non-nullity), but these two
   usually take care of themselves.

An equals method constructed according to the previous recipe is shown in this simplistic PhoneNumber class:
    // Class with a typical equals method
    public final class PhoneNumber {
        private final short areaCode, prefix, lineNum;

        public PhoneNumber(int areaCode, int prefix, int lineNum) {
            this.areaCode = rangeCheck(areaCode, 999, "area code");
            this.prefix = rangeCheck(prefix, 999, "prefix");
            this.lineNum = rangeCheck(lineNum, 9999, "line num");
        }

        private static short rangeCheck(int val, int max, String arg) {
            if (val < 0 || val > max)
                throw new IllegalArgumentException(arg + ": " + val);
            return (short) val;
        }

        @Override public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof PhoneNumber))
                return false;
            PhoneNumber pn = (PhoneNumber)o;
            return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
        }
        ... // Remainder omitted
    }

Here are a few final caveats:
    • Always override hashCode when you override equals (Item 11).

    • Don’t try to be too clever. If you simply test fields for equality, it’s not hard to adhere to the equals
    contract.
        If you are overly aggressive in searching for equivalence, it’s easy to get into trouble. It is generally a
        bad idea to take any form of aliasing into account.
        For example, the File class shouldn’t attempt to equate symbolic links referring to the same file. Thankfully,
        it doesn’t.

    • Don’t substitute another type for Object in the equals declaration. It is not uncommon for a programmer to write
        an equals method that looks like this and then spend hours puzzling over why it doesn’t work properly:
            // Broken - parameter type must be Object!
            public boolean equals(MyClass o) {
                ...
            }
        The problem is that this method does not override Object.equals, whose  argument is of type Object.
        Consistent use of the Override annotation, as illustrated throughout this item, will prevent you from making
        this mistake (Item 40). This equals method won’t compile, and the error message will tell you exactly what
        is wrong:
            // Still broken, but won’t compile
            @Override public boolean equals(MyClass o) {
                ...
            }

Writing and testing equals (and hashCode) methods is tedious, and the resulting code is mundane.

An excellent alternative to writing and testing these methods manually is to use Google’s open source AutoValue framework,
which automatically generates these methods for you, triggered by a single annotation on the class.

In most cases, the methods generated by AutoValue are essentially identical to those you’d write yourself.

IDEs, too, have facilities to generate equals and hashCode methods, but the resulting source code is more verbose and
less readable than code that uses AutoValue, does not track changes in the class automatically, and therefore
requires testing.
That said, having IDEs generate equals (and hashCode) methods is generally preferable to implementing them manually
because IDEs do not make careless mistakes, and humans do.

In summary, don’t override the equals method unless you have to: in many cases, the implementation inherited from
Object does exactly what you want.
If you do override equals, make sure to compare all of the class’s significant fields and to compare them in a manner
that preserves all five provisions of the equals contract.


!!!Once you have violated the equals contract, you simply don't know how other objects will behave when confronted with your object


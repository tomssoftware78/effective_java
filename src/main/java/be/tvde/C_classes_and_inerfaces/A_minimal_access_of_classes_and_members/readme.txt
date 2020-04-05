Minimize the accessibility of classes and members
=================================================

The single most important factor that distinguishes a well-designed component from a poorly designed one is the degree
to which the component hides its internal data and other implementation details from other components.

A well-designed component hides all its implementation details, cleanly separating its API from its implementation.

Components then communicate only through their APIs and are oblivious to each others’ inner workings -> DECOUPLING

This concept, known as INFORMATION HIDING or ENCAPSULATION, is a fundamental tenet of software design.

Through decoupling, components can be
    developed,
    tested,
    optimized,
    used,
    understood, and
    modified
in isolation.

Advantages:     + they can be developed in parallel
                + it eases the burden of maintenance because components can be understood more quickly and debugged or
                    replaced with little fear of harming other components.
                + they can be reused

While information hiding does not, in and of itself, cause good performance, it enables effective performance tuning:
    once a system is complete and profiling has determined which components are causing performance problems (Item 67),
    those components can be optimized without affecting the correctness of others.

Java has many facilities to aid in information hiding.

The ACCESS CONTROL MECHANISM specifies the accessibility of classes, interfaces, and members.
Proper use of the access modifiers (private, protected, and public) is essential to information hiding.

The rule of thumb is simple:    MAKE EACH CLASS OR MEMBER AS INACCESSIBLE AS POSSIBLE
    = use the lowest possible access level consistent with the proper functioning of the software that you are writing

* For top-level (non-nested) classes and interfaces, there are only two possible access levels:

        • public: the top-level class or interface will be public, will be part of the exported API
                --> you are obligated to support it foreven to maintain compatibility

        • package-private: the top-level class or interface will be part of the implementation
                --> not part of the exported API
                --> you can modify it, replace it, or eliminate it in a subsequent release without fear of harming
                        existing clients

                --> If a package-private top-level class or interface is used by only one class, consider making the
                    top-level class a private static nested class of the sole class that uses it (Item 24).
                    This reduces its accessibility from all the classes in its package to the one class that uses it.

* For members (fields, methods, nested classes, and nested interfaces), there are four possible access levels, listed
here in order of increasing accessibility:

        • private: The member is accessible only from the top-level class where it is declared.

        • package-private: The member is accessible from any class in the package where it is declared.
                --> Technically known as default access, this is the access level you get if no access modifier is
                 specified (except for interface members, which are public by default).

        • protected: The member is accessible from subclasses of the class where it is declared and from any class in
                        the package where it is declared.

        • public: The member is accessible from anywhere.

!!! After carefully designing your class’s public API, your reflex should be to make all other members private.
    - Only if another class in the same package really needs to access a member should you remove the private modifier,
        making the member package-private.
        If you find yourself doing this often, you should reexamine the design of your system to see if another
        decomposition might yield classes that are better decoupled from one another.

Both private and package-private members are part of a class’s implementation and do not normally impact its exported
API. These fields can, however, “leak” into the exported API if the class implements Serializable (Items 86 and 87).

For members of public classes, a huge increase in accessibility occurs when the access level goes from
package-private to protected.
    - A protected member is part of the class’s exported API and must be supported forever.
    - A protected member of an exported class represents a public commitment to an implementation detail (Item 19).

    The need for protected members should be relatively rare.

There is a key rule that restricts your ability to reduce the accessibility of methods. If a method overrides a
superclass method, it cannot have a more restrictive access level in the subclass than in the superclass. This is
necessary to ensure that an instance of the subclass is usable anywhere that an instance of the superclass is
usable.

To facilitate testing your code, you may be tempted to make a class, interface, or member more accessible than
otherwise necessary.
    This is fine up to a point. It is acceptable to make a private member of a public class package-private in order
    to test it, but it is not acceptable to raise the accessibility any higher.
    In other words, it is not acceptable to make a class, interface, or member a part of a package’s exported API to
    facilitate testing. Luckily, it isn’t necessary either because tests can be made to run as part of the package
    being tested, thus gaining access to its package-private elements.

Instance fields of public classes should rarely be public (Item 16).

    - If an instance field is nonfinal or is a reference to a mutable object, then by making it public, you give up the
        ability to limit the values that can be stored in the field.

    - You give up the ability to enforce invariants involving the field.

    - You give up the ability to take any action when the field is modified, so classes with public mutable fields are
        not generally thread-safe.

    - Even if a field is final and refers to an immutable object, by making it public you give up the flexibility to
        switch to a new internal data representation in which the field does not exist.

The same advice applies to static fields, with one exception.

    - You can expose constants via public static final fields, assuming the constants form an integral part of the
        abstraction provided by the class.

    - By convention, such fields have names consisting of capital letters, with words separated by underscores (Item 68)

    - !!!It is critical that these fields contain either primitive values or references to immutable objects (Item 17).
        A field containing a reference to a mutable object has all the disadvantages of a nonfinal field.
        While the reference cannot be modified, the referenced object can be modified—with disastrous results.

Note that a nonzero-length array is always mutable, so it is wrong for a class to have a public static final array
field, or an accessor that returns such a field.

    - If a class has such a field or accessor, clients will be able to modify the contents of the array. This is a
        frequent source of security holes:

        // Potential security hole!
        public static final Thing[] VALUES = { ... };

    - Beware of the fact that some IDEs generate accessors that return references to private array fields, resulting in
        exactly this problem. There are two ways to fix the problem.

            * You can make the public array private and add a public immutable list:

                private static final Thing[] PRIVATE_VALUES = { ... };

                public static final List<Thing> VALUES = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));

            * You can make the array private and add a public method that returns a copy of a private array:

                private static final Thing[] PRIVATE_VALUES = { ... };

                public static final Thing[] values() {
                    return PRIVATE_VALUES.clone();
                }

            To choose between these alternatives, think about what the client is likely to do with the result.
            Which return type will be more convenient? Which will give better performance?

As of Java 9, there are two additional, implicit access levels introduced as part of the module system. A module is a
    grouping of packages, like a package is a grouping of classes.

    A module may explicitly export some of its packages via export declarations in its module declaration (which is by
    convention contained in a source file named module-info.java).

    Public and protected members of unexported packages in a module are inaccessible outside the module; within the
    module, accessibility is unaffected by export declarations.

    Using the module system allows you to share classes among packages within a module without making them visible to
    the entire world.

    Public and protected members of public classes in unexported packages give rise to the two implicit access levels,
    which are intramodular analogues of the normal public and protected levels.

    The need for this kind of sharing is relatively rare and can often be eliminated by rearranging the classes within
    your packages.

    Unlike the four main access levels, the two module-based levels are largely advisory. If you place a module’s JAR
    file on your application’s class path instead of its module path, the packages in the module revert to their
    non-modular behavior:

        all of the public and protected members of the packages’ public classes have their normal accessibility,
        regardless of whether the packages are exported by the module.

        The one place where the newly introduced access levels are strictly enforced is the JDK itself: the unexported
        packages in the Java libraries are truly inaccessible outside of their modules.

        Not only is the access protection afforded by modules of limited utility to the typical Java programmer, and
        largely advisory in nature; in order to take advantage of it, you must group your packages into modules, make
        all of their dependencies explicit in module declarations, rearrange your source tree, and take special
        actions to accommodate any access to non-modularized packages from within your modules.
        It is too early to say whether modules will achieve widespread use outside of the JDK itself. In the meantime,
        it seems best to avoid them unless you have a compelling need.

To summarize, you should reduce accessibility of program elements as much as possible (within reason). After carefully
designing a minimal public API, you should prevent any stray classes, interfaces, or members from becoming part of
the API. With the exception of public static final fields, which serve as constants, public classes should have no
public fields. Ensure that objects referenced by public static final fields are immutable.
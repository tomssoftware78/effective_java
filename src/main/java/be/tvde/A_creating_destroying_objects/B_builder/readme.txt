Consider a builder when faced with many constructor parameters
==============================================================

Both static factory methods and constructors share a limitation: they do not scale well to large numbers of optional
parameters

Consider a class with few required fields, and many optional fields. Common mistake is to use the
    * TELESCOPING CONSTRUCTOR pattern =
        - provide a constructor with only the required fields
        - provide another constructor with the required fields and a single optional parameter
        - provide yet another constructor with only the required fields and 2 optional parameters
        ...
        - provide yet another constructor with the required fields and all the optional parameters

        Disadvantage:   - code is hard to read
                        - hard to write client code using the constructors (which one to use?, errorprone)

    * JavaBeans patterns = parameterless constructor + setter methods for required and optional fields

        Disadvantage:   - JavaBean may be in an inconsistent state partway through its construction
                            class has no option to enforce consistency during construction
                        - The use of an object in an inconsistent state may cause failures that are far removed
                            from the code containing the bug (= inconsistent state) --> difficult to debug

                        - JavaBeans pattern disables the possibility to make a class immutable

Solution: Builder pattern   -> combines the safety of the telescoping constructor pattern (= consistent state) with
                                readability of the JavaBeans pattern
                            -> the builder is typically a static member class of the class it builds
                            -> validity checks (invalid parameter values) are best performed as soon as possible (= in
                                the constructor or setter methods of the builder)

                                If checks fail, throw an IllegalArgumentException,whose detail message indicates which
                                   parameters are invalid
Advantages
----------
- Client code is easier to write and easier to read --> Builder pattern simulates named optional parameters
- The class being build can be immutable
- All default values (for optional fields) are in 1 place
- The setter methods return the builder itself -> fluent API

Disadvantages
-------------

- For performance critical situations, the fact that we first have to create a builder could be a problem
- More verbose than the telescoping constructor pattern. As of 4 or more parameters, Builder is appropriate.

Builder pattern is well suited to class hierarchies
===================================================

Use a parallel hierarchy of builders, each nested in the corresponding class
    - abstract classes have abstract builders
    - concrete classes have concrete builders

See example in the inheritance_hierarchy package
    Abstract pizza class has a abstract Pizza.Builder which is a generic type with a recursive type parameter (item 30)

    Abstract self method allows method chaining to work property in subclasses, without the need for casts

    NyPizza has a required size parameter

    Calzone has parameter to indicate sauze inside or outside

    !!! NyPizza.Builder returns a NYPizza
        Calzone.Builder returns a Calzone
        --> This technique wherein a subclass method returns a subtype of the returntype declared in the superclass
            is known as COVARIANT RETURN TYPING
            This allows clients to use these builders without the need for casting!

Conclusion:
    Builder pattern is a good choice when designing classes whose constructors or static factories would have more than
    a handful of parameters, especially if many of the parameters are optional or of identical type.
    Client code is much easier to read and write with builders than with telescoping constructors, and builders are
    much safer than JavaBeans.

Consider a builder when faced with many constructor parameters
==============================================================

Both static factory methods and constructors share a limitation: they do not scale well to large numbers of optional
parameters

Consider a class with few required fields, and many optional fields. Common mistake is to use the
    * telescoping constructor pattern =
        - provide a constructor with only the required fields
        - provide another constructor with a single optional parameter
        - provide yet another constructor with 2 optional parameters
        ...

        Disadvantage:   - code is hard to read
                        - hard to write client code using the constructors (which one to use?)

    * JavaBeans patterns = parameterless constructor + setter methods for required and optional fields

        Disadvantage:   - JavaBean may be in an inconsistent state partway through its construction
                            class has no option to enforce consistency during construction
                        - The use of an object in an inconsistent state may cause failures that are far removed
                            from the code containing the bug (= inconsistent state) --> difficult to debug

                        - JavaBeans pattern disables the possibility to make a class immutable

Solution: Builder pattern

Advantages
----------
- Client code is easier to write and easier to read --> Builder pattern simulates named optional parameters

Disadvantages
-------------

- For performance critical situations, the fact that we first have to create a builder could be a problem

Enforce the singleton with a private constructor or an enum type
================================================================

Singleton = class that is instantiated exactly once

Singleton typically represents one of the following:
    - a stateless object (such as a function (see item 24)
    - a system component that is intrinsically unique

!!! Making a class a singleton can make it difficult to test its clients because it's impossible to substitute a
singleton with a mock implementation, unless it implements an interface that serves as its type.

3 ways to implement singletons:


    * Public static field + private constructor
    -------------------------------------------
        - the lack of a public or protected constructor guarantees a single instance
        - See Elvis1

        The public field INSTANCE makes it clear that the class is a singleton!

        !!! With reflection, it would be possible to call the private constructor a 2nd time. To avoid the creation
            of a 2nd instance, you can throw an exception if asked to create a 2nd instance

    * Public static factory method + private constructor
    ----------------------------------------------------
        - See Elvis2

        The static factory method makes it easier to later move away from the singleton idea, you can remove the
            singleton concept without changing the API
            for example: you could change the implementation so that it creates 1 instance per thread (use the
                ThreadLocal mechanism)

        The static method reference can be used as a supplier, Elvis2::instance is a Supplier<Elvis2>

        !!! With reflection, it would be possible to call the private constructor a 2nd time. To avoid the creation
            of a 2nd instance, you can throw an exception if asked to create a 2nd instance

    To make the 2 above singleton implementations serializable (see Item 12):
        - implement Serializable
        - declare all instance fields transient
        - provide a readResolve method (see Item 89)
        Otherwise, each time a serialized instance is deserialized, a new instance will be created.

    * Declare a single-element enum
    -------------------------------
        - See Elvis

        This implementation:    - is similar to the public field approach (Elvis1)
                                - is more concise
                                - provides the serialization machinery for free
                                - is protected against more sophisticated serialization or reflection attacks

Conclusion:
==> Single-element enum type is often best way to implement a singleton
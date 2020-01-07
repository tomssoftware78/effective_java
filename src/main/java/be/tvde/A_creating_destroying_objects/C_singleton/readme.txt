Enforce the singleton with a private constructor or an enum type
================================================================

Singleton = class that is instantiated exactly once

3 ways to implement singletons:


    * Public static field + private constructor
    -------------------------------------------
        - the lack of a public or protected constructor guarantees a single instance
        - See Elvis1

        The public field INSTANCE makes it clear that the class is a singleton!

    * Public static factory method + private constructor
    ----------------------------------------------------
        - See Elvis2

        The static factory method makes it easier to later move away from the singleton idea, you can remove the
            singleton concept without changing the API
        The static method reference can be used as a supplier, Elvis2::instance is a Supplier<Elvis2>

    To make the 2 above singleton implementations serializable:
        - implement Serializable
        - declare all instance fields transient
        - provide a readResolve method

    * Declare a single-element enum
    -------------------------------
        - See Elvis

        This implementation:    - is more concise
                                - provides the serialization machinery for free
                                - is protected against more sophisticated serialization or reflection attacks

    ==> Single-element enum type is often best way to implement a singleton
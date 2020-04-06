Minimize mutability
===================

An immutable class is simply a class whose instances cannot be modified.

All of the information contained in each instance is fixed for the lifetime of the object, so no changes can ever be
observed.

Examples:
    - String
    - the boxed primitive classes
    - BigInteger and BigDecimal

There are many good reasons for this:

    - Immutable classes are easier to design, implement, and use than mutable classes.
    - Immutable classes are less prone to error and are more secure.

To make a class immutable, follow these five rules:

    1. Don’t provide methods that modify the object’s state (known as mutators).

    2. Ensure that the class can’t be extended.
            This prevents careless or malicious subclasses from compromising the immutable behavior of the class by
            behaving as if the object’s state has changed.
            Preventing subclassing is generally accomplished by making the class final, but there is an alternative
            that we’ll discuss later.

    3. Make all fields final.
            This clearly expresses your intent in a manner that is enforced by the system. Also, it is necessary to
            ensure correct behavior if a reference to a newly created instance is passed from one thread to another
            without synchronization, as spelled out in the memory model.

    4. Make all fields private.
            This prevents clients from obtaining access to mutable objects referred to by fields and modifying these
            objects directly.

            While it is technically permissible for immutable classes to have public final fields containing primitive
            values or references to immutable objects, it is not recommended because it precludes changing the internal
            representation in a later release.

    5. Ensure exclusive access to any mutable components.
            If your class has any fields that refer to mutable objects, ensure that clients of the class cannot obtain
            references to these objects. Never initialize such a field to a client-provided object reference or return
            the field from an accessor. Make defensive copies (Item 50) in constructors, accessors, and readObject
            methods (Item 88).

See Complex class -> represents a complex number (a number with both real and imaginary parts)

    - It has the standard Object methods
    - It has accessors for the real and imaginary parts
    - It has four basic arithmetic operations:
        * addition
        * subtraction
        * multiplication
        * division
      These arithmetic operations create and return a new Complex instance rather than modifying the existing instance.

      This pattern is known as the functional approach because methods return the result of applying a function to
      their operand, without modifying it.

      Contrast it to the procedural or imperative approach in which methods apply a procedure to their operand, causing
      its state to change.

      Note that the method names are prepositions (such as plus) rather than verbs (such as add). This emphasizes the
      fact that methods don’t change the values of the objects.

      The BigInteger and BigDecimal classes did not obey this naming convention, and it led to many usage errors.

      The functional approach may appear unnatural if you’re not familiar with it, but it enables immutability, which
      has many advantages.

Advantages
----------
- Immutable objects are simple.
- Immutable objects can be in exactly one state, the state in which it was created.
    If you make sure that all constructors establish class invariants, then it is guaranteed that these invariants will
    remain true for all time, with no further effort on your part or on the part of the programmer who uses the class.

  Mutable objects, on the other hand, can have arbitrarily complex state spaces.
    If the documentation does not provide a precise description of the state transitions performed by mutator methods,
    it can be difficult or impossible to use a mutable class reliably.

- Immutable objects are inherently thread-safe; they require no synchronization.
    They cannot be corrupted by multiple threads accessing them concurrently. This is far and away the easiest approach
    to achieve thread safety. Since no thread can ever observe any effect of another thread on an immutable object,
    immutable objects can be shared freely.

- Immutable classes should encourage clients to reuse existing instances wherever possible.
    - One easy way to do this is to provide public static final constants for commonly used values.
        For example, the Complex class might provide these constants:
                public static final Complex ZERO = new Complex(0, 0);
                public static final Complex ONE = new Complex(1, 0);
                public static final Complex I = new Complex(0, 1);

    - Another way is to provide static factories (Item 1) that cache frequently requested instances.
        * This avoids creating new instances when existing ones would do.
            All the boxed primitive classes and BigInteger do this.
        * This causes clients to share instances instead of creating new ones, reducing memory footprint and garbage
            collection costs.
        * Opting for static factories in place of public constructors when designing a new class gives you the
            flexibility to add caching later, without modifying clients.

    - You never have to make defensive copies of them (Item 50). In fact, you never have to make any copies at all
        because the copies would be forever equivalent to the originals.

        Therefore, you need not and should not provide a clone method or copy constructor (Item 13) on an immutable
            class.

- Immutable objects can share their internals.
    For example, the BigInteger class uses a sign-magnitude representation internally. The sign is represented by an
    int, and the magnitude is represented by an int array.
    The negate method produces a new BigInteger of like magnitude and opposite sign. It does not need to copy the array
    even though it is mutable; the newly created BigInteger points to the same internal array as the original.

- Immutable objects make great building blocks for other objects, whether mutable or immutable.
    It’s much easier to maintain the invariants of a complex object if you know that its component objects will not
    change underneath it.

    Example:
        Immutable objects make great map keys and set elements: you don’t have to worry about their values changing
        once they’re in the map or set, which would destroy the map or set’s invariants.

- Immutable objects provide failure atomicity for free (Item 76).
    Their state never changes, so there is no possibility of a temporary inconsistency.

Disadvantages
-------------
- The major disadvantage of immutable classes is that they require a separate object for each distinct value.
    Creating these objects can be costly, especially if they are large.

    Example: suppose that you have a million-bit BigInteger and you want to change its low-order bit:

        BigInteger moby = ...;
        moby = moby.flipBit(0);

    The flipBit method creates a new BigInteger instance, also a million bits long, that differs from the original
    in only one bit. The operation requires time and space proportional to the size of the BigInteger.

    Contrast this to java.util.BitSet. Like BigInteger, BitSet represents an arbitrarily long sequence of bits, but
    unlike BigInteger, BitSet is mutable.
    The BitSet class provides a method that allows you to change the state of a single bit of a millionbit instance
    in constant time:

        BitSet moby = ...;
        moby.flip(0);


    The performance problem is magnified if you perform a multistep operation that generates a new object at every
    step, eventually discarding all objects except the final result.

    There are two approaches to coping with this problem.
        - The first is to guess which multistep operations will be commonly required and to provide them as primitives.
                If a multistep operation is provided as a primitive, the immutable class does not have to create a
                separate object at each step.
                Internally, the immutable class can be arbitrarily clever.

                Example:

                    BigInteger has a package-private mutable “companion class” that it uses to speed up multistep
                    operations such as modular exponentiation.
                    It is much harder to use the mutable companion class than to use BigInteger, for all of the reasons
                    outlined earlier.
                    Luckily, you don’t have to use it: the implementors of BigInteger did the hard work for you.

            The package-private mutable companion class approach works fine if you can accurately predict which complex
            operations clients will want to perform on your immutable class.

        - The second is to provide a public mutable companion class.
                Example:

                    In the Java platform libraries, the String class has a mutable companion StringBuilder (and its
                        obsolete predecessor, StringBuffer)


Design alternatives
-------------------

* Avoid immutable classes being subclassed
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    1. Make the class final
    2. Make all of its constructors private or package-private and add public static factories in place of the public
        constructors

        Example: see Complex2 (= Immutable class with static factories instead of constructors)

    Approach 2 is often the best alternative.
        It is the most flexible because it allows the use of multiple package-private implementation classes.
        To its clients that reside outside its package, the immutable class is effectively final because it is
        impossible to extend a class that comes from another package and that lacks a public or protected constructor.

        Besides allowing the flexibility of multiple implementation classes, this approach makes it possible to tune
        the performance of the class in subsequent releases by improving the object-caching capabilities of the static
        factories.

* No method may produce an externally visible change in the object's state
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    The list of rules for immutable classes says that no methods may modify the object and that all its fields must be
    final.

    In fact these rules are a bit stronger than necessary and can be relaxed to improve performance.

    In truth, no method may produce an externally visible change in the object’s state.

        Some immutable classes have one or more nonfinal fields in which they cache the results of expensive
        computations the first time they are needed.
        If the same value is requested again, the cached value is returned, saving the cost of recalculation.

        This trick works precisely because the object is immutable, which guarantees that the computation would yield
        the same result if it were repeated.

        Example
            The hashCode method of an immutable class can compute the hash code the first time it’s invoked and cache
            it in case it’s invoked again.

* Serialization
~~~~~~~~~~~~~~~
    If you choose to have your immutable class implement Serializable and it contains one or more fields that refer to
    mutable objects, you must provide an explicit readObject or readResolve method, or use the
    ObjectOutputStream.writeUnshared and ObjectInputStream.readUnshared methods, even if the default serialized form
    is acceptable.
    Otherwise an attacker could create a mutable instance of your class.

    This topic is covered in detail in Item 88.

Conclusion:
    - Resist the urge to write a setter for every getter.

    - Classes should be immutable unless there’s a very good reason to make them mutable.

    - Immutable classes provide many advantages, and their only disadvantage is the potential for performance problems
        under certain circumstances.

    - You should always make small value objects, such as PhoneNumber and Complex, immutable.
            (There are several classes in the Java platform libraries, such as java.util.Date and java.awt.Point, that
            should have been immutable but aren’t.)

    - You should seriously consider making larger value objects, such as String and BigInteger, immutable as well.

    - You should provide a public mutable companion class for your immutable class only once you’ve confirmed that it’s
        necessary to achieve satisfactory performance (Item 67).

    - There are some classes for which immutability is impractical. If a class cannot be made immutable, limit its
        mutability as much as possible.

        Reducing the number of states in which an object can exist makes it easier to reason about the object and
        reduces the likelihood of errors.

        Therefore, make every field final unless there is a compelling reason to make it nonfinal.

    - Constructors should create fully initialized objects with all of their invariants established.
        Don’t provide a public initialization method separate from the constructor or static factory unless there is a
        compelling reason to do so.
        Similarly, don’t provide a “reinitialize” method that enables an object to be reused as if it had been
        constructed with a different initial state.
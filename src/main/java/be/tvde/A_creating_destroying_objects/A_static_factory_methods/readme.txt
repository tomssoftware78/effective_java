Consider static factory methods instead of constructors
=======================================================

static factory method = a static method that returns an instance of the class
    (is not the same as the Factory Method design pattern!!!)

Advantages
----------
- static factory method has a (well-chosen) name --> code is easier to read

- you can have only a single constructor with a given signature. Programmers solve this by providing 2 constructors
    whose parameter list differ only in the order of their parameter types --> user of such an API will never be
    able to remember which constructor is which and mistakes will happen.

    Solution is different static factory methods with carefully chosen names

- unlike constructors, static factory methods are not required to create a new object each time they're invoked
     -> preconstructed instances can be used
     -> caching of instances after they are created is possible


     By using static factory methods, classes are able to strictly control over what instances exist at any time
        = instance-controlled

- unlike constructors, static factory methods can return an object of any subtype of their return type.
    -> flexibility in choosing the class of the returned object
    -> an API can return objects without making their classes public by using interface types as return
        types for static factory methods = hiding implementations

    Prior to Java 8, interfaces couldn't have static factory methods
        --> by convetion, static factory methods for an interface named 'Type' were put in a non-instantiable
            companion class named 'Types'

            example: See java.util.Collections witch exports nearly all 45 implementations of its interfaces
                            the classes of the returned objects are all non-public

    As of Java 8, interfaces can contain static methods, so no more reason to provide a non-instantiable companion
        class for an interface

- the class of the returned object can vary from call to call depending on the input parameters
    alternatively, the class of the returned object, can also vary from release to release

    example: EnumSet has no public constructors, only static factory methods
        The OpenJDK implementation returns an instance of one of 2 subclasses, depending on the size of underlying
            enum type

            size <= 64 elements --> static factory methods return RegularEnumSet, which is backed by a single long
            size >= 65 elements --> static factory methods return JumboEnumSet, backed by a long array

            The 2 implementation classes are invisible to clients

- The class of the returned object need not exist when the class containing the static method is written
    This forms the basis for service provider frameworks = a system in which providers implement a service, and the
        system makes the implementations available to clients, decoupling the clients from the implementations

            3 essential components in a service provider framework:
                - a service interface: represents an implementation
                - a provider registration API: used by providers to register implementations
                - a service access API: used by clients to obtain instances of the service
                        -- may allow clients to specify criteria for choosing an implementation
                        -- implemented as static factory method
                        -- can return a richer service interface to clients than the one furnished by providers
                                (Bridge design pattern)

         Sice Java 6, general purpose service provider framework: java.util.ServiceLoader

Disadvantages
-------------

- Classes without public or protected constructors cannot be subclassed

- Static factory methods are hard to find for programmers as they do not stand out in API documentation in the
    way that constructors do --> difficult to figure out how to instantiate a class that provides factory methods
    instead of constructors

    !!One day, the javadoc may draw attention to static factory methods

    Adhering to common naming conventions can reduce the problem. Some examples:
        from()  type-conversion method
                example: Data d = Date.from(instant);

        of()    aggregation method that takes multiple parameters and returns an instance of this type that
                    incorporates them
                example: Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);

        valueOf()   a more verbose alternative to from() and of()
                    example: BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);

        instance() or getInstance()
                    returns an instance that is described by its parameters (if any) but cannot be said to have
                        the same value
                    example: StackWalker luke = StackWalker.getInstance(options);

        create() or newInstance()
                    like instance() or getInstance(), except that the method guarantees that each call returns a NEW
                    instance
                    example: Object new Array = Array.newInstance(classObject, arrayLen);


Item 20: Prefer interfaces to abstract classes
==============================================

Java has two mechanisms to define a type that permits multiple implementations:
    - interfaces
    - abstract classes

Since the introduction of default methods for interfaces in Java 8, both mechanisms allow you to provide implementations
 for some instance methods.

A major difference is that to implement the type defined by an abstract class, a class must be a subclass of the
abstract class.
    => Because Java permits only single inheritance, this restriction on abstract classes severely constrains their use
        as type definitions.

Existing classes can easily be retrofitted to implement a new interface.
    All you have to do is to add the required methods, if they don’t yet exist, and to add an implements clause to the
    class declaration. (they do not have to be part of the class hierarchy)

    Example: many existing classes were retrofitted to implement the Comparable, Iterable, and Autocloseable
        interfaces when they were added to the platform.

Existing classes cannot, in general, be retrofitted to extend a new abstract class.
    If you want to have two classes extend the same abstract class, you have to place it high up in the type hierarchy
    where it is an ancestor of both classes.

    Unfortunately, this can cause great collateral damage to the type hierarchy, forcing all descendants of the new
    absract class to subclass it, whether or not it is appropriate.


Interfaces are ideal for defining mixins.

    What is a MIXIN ?
        It is a type that a class can implement in addition to its “primary type,” to declare that it provides some
        optional behavior.

        Example:    Comparable is a mixin interface that allows a class to declare that its instances are ordered with
                    respect to other mutually comparable objects.

        Such an interface is called a mixin because it allows the optional functionality to be “mixed in” to the type’s
        primary functionality.

        Abstract classes can’t be used to define mixins for the same reason that they can’t be retrofitted onto
        existing classes:

            a class cannot have more than one parent, and there is no reasonable place in the class hierarchy to insert
            a mixin.

Interfaces allow for the construction of nonhierarchical type frameworks.

    Type hierarchies are great for organizing some things, but other things don’t fall neatly into a rigid hierarchy.

    Example:    Suppose we have an interface representing a singer and another representing a songwriter:

                public interface Singer {
                    AudioClip sing(Song s);
                }

                public interface Songwriter {
                    Song compose(int chartPosition);
                }

                Some singers are also songwriters. Because we used interfaces rather than abstract classes to define
                these types, it is perfectly permissible for a single class to implement both Singer and Songwriter.
                In fact, we can define a third interface that extends both Singer and Songwriter and adds new methods
                that are appropriate to the combination:

                public interface SingerSongwriter extends Singer, Songwriter {
                    AudioClip strum();
                    void actSensitive();
                }

    You don’t always need this level of flexibility, but when you do, interfaces are a lifesaver.
    The alternative is a bloated class hierarchy containing a separate class for every supported combination of
    attributes:

        If there are n attributes in the type system, there are 2n possible combinations that you might have to support.
                = COMBINARORIAL EXPLOSION
        --> Bloated class hierarchies can lead to bloated classes with many methods that differ only in the type of
        their arguments because there are no types in the class hierarchy to capture common behaviors.

Interfaces enable safe, powerful functionality enhancements via the wrapper class idiom (Item 18).

    If you use abstract classes to define types, you leave the programmer who wants to add functionality with no
    alternative but inheritance.
    The resulting classes are less powerful and more fragile than wrapper classes.

    When there is an obvious implementation of an interface method in terms of other interface methods, consider
    providing implementation assistance to programmers in the form of a default method.

    Example: removeIf() method of Collection interface in Java 8

        interface Collection {
            default boolean removeIf(Predicate<? super E> filter) {
                Objects.requireNonNull(filter);
                boolean result = false;
                for (Iterator<E> it = iterator(); it.hasNext(); ) {
                    if (filter.test(it.next())) {
                        it.remove();
                        result = true;
                    }
                }
                return result;
            }
        }

        !!! If you provide default methods, be sure to document them for inheritance using the @implSpec Javadoc
        tag (Item 19).

    There are limits on how much implementation assistance you can provide with default methods.

        * Although many interfaces specify the behavior of Object methods such as equals() and hashCode(), you are not
            permitted to provide default methods for them.
        * Interfaces are not permitted to contain instance fields or nonpublic static members (with the exception of
            private static methods).
        * You can’t add default methods to an interface that you don’t control.
            You can, however, combine the advantages of interfaces and abstract classes by providing an abstract
            skeletal implementation class to go with an interface.

                - The interface defines the type, perhaps providing some default methods
                - The skeletal implementation class implements the remaining non-primitive interface methods atop the
                    primitive interface methods.
                Extending a skeletal implementation takes most of the work out of implementing an interface.
                This is the Template Method pattern [Gamma95].

    - By convention, skeletal implementation classes are called AbstractInterface, where Interface is the name of the
        interface they implement.

        Example:    the Collections Framework provides a skeletal implementation to go along with each main collection
                    interface:
                        AbstractCollection      for the Collection interface
                        AbstractSet             for the Set interface
                        AbstractList            for the List interface
                        AbstractMap             for the Map interface

        Arguably it would have made sense to call them SkeletalCollection, SkeletalSet, SkeletalList, and SkeletalMap,
        but the Abstract convention is now firmly established.

    - When properly designed, skeletal implementations (whether a separate abstract class, or consisting solely of
        default methods on an interface) can make it very easy for programmers to provide their own implementations of
        an interface.

        Example:    here’s a static factory method containing a complete, fully functional List implementation
                    atop AbstractList:

                    // Concrete implementation built atop skeletal implementation
                    static List<Integer> intArrayAsList(int[] a) {
                        Objects.requireNonNull(a);

                        // The diamond operator is only legal here in Java 9 and later
                        // If you're using an earlier release, specify <Integer>
                        return new AbstractList<>() {
                            @Override public Integer get(int i) {
                                return a[i]; // Autoboxing (Item 6)
                            }

                            @Override public Integer set(int i, Integer val) {
                                int oldVal = a[i];
                                a[i] = val; // Auto-unboxing
                                return oldVal; // Autoboxing
                            }

                            @Override public int size() {
                                return a.length;
                            }
                        };
                    }

                    When you consider all that a List implementation does for you, this example is an impressive
                    demonstration of the power of skeletal implementations. Incidentally, this example is an
                    Adapter [Gamma95] that allows an int array to be viewed as a list of Integer instances.
                    Because of all the translation back and forth between int values and Integer instances (boxing and
                    unboxing), its performance is not terribly good.
                    Note that the implementation takes the form of an anonymous class (Item 24).

    - For most implementors of an interface with a skeletal implementation class, extending this class is the obvious
        choice, but it is strictly optional.
        * If a class cannot be made to extend the skeletal implementation, the class can always implement the interface
            directly. The class still benefits from any default methods present on the interface itself.

        * The skeletal implementation can still aid the implementor’s task. The class implementing the interface can
            forward invocations of interface methods to a contained instance of a private inner class that extends the
            skeletal implementation.

            This technique, known as SIMULATED MULTIPLE INHERITANCE, is closely related to the wrapper class idiom
            discussed in Item 18.
            It provides many of the benefits of multiple inheritance, while avoiding the pitfalls.


Writing a skeletal implementation is a relatively simple, if somewhat tedious, process.

    1. Study the interface and decide which methods are the primitives in terms of which the others can be implemented.
        These primitives will be the abstract methods in your skeletal implementation.

    2. Provide default methods in the interface for all of the methods that can be implemented directly atop the
        primitives, but recall that you may not provide default methods for Object methods such as equals and hashCode.

    3. If the primitives and default methods cover the interface, you’re done, and have no need for a skeletal
        implementation class.
        Otherwise, write a class declared to implement the interface, with implementations of all of the remaining
        interface methods.
        The class may contain any nonpublic fields ands methods appropriate to the task.

    Example:    Map.Entry interface
                    The obvious primitives are  getKey(),
                                                getValue(), and
                                                (optionally) setValue()

                    The interface specifies the behavior of equals and hashCode, and there is an obvious implementation
                    of toString in terms of the primitives.
                    Since you are not allowed to provide default implementations for the Object methods, all
                    implementations are placed in the skeletal implementation class:

                    // Skeletal implementation class
                    public abstract class AbstractMapEntry<K,V> implements Map.Entry<K,V> {

                        // Entries in a modifiable map must override this method
                        @Override public V setValue(V value) {
                            throw new UnsupportedOperationException();
                        }

                        // Implements the general contract of Map.Entry.equals
                        @Override public boolean equals(Object o) {
                            if (o == this)
                                return true;
                            if (!(o instanceof Map.Entry))
                                return false;

                            Map.Entry<?,?> e = (Map.Entry) o;
                            return Objects.equals(e.getKey(), getKey()) && Objects.equals(e.getValue(), getValue());
                        }

                        // Implements the general contract of Map.Entry.hashCode
                        @Override public int hashCode() {
                            return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
                        }

                        @Override public String toString() {
                            return getKey() + "=" + getValue();
                        }
                    }


Because skeletal implementations are designed for inheritance, you should follow all of the design and documentation
guidelines in Item 19.

For brevity’s sake, the documentation comments were omitted from the previous example, but good documentation is
absolutely essential in a skeletal implementation, whether it consists of default methods on an interface or a
separate abstract class.


A minor variant on the skeletal implementation is the simple implementation, exemplified by AbstractMap.SimpleEntry.
    A simple implementation is like a skeletal implementation in that it implements an interface and is designed for
    inheritance, but it differs in that it isn’t abstract:

        it is the simplest possible working implementation.

    You can use it as it stands or subclass it as circumstances warrant.


Conclusion:
    - An interface is generally the best way to define a type that permits multiple implementations

    - If you export a nontrivial interface, you should strongly consider providing a skeletal implementation to go
        with it.

    - To the extent possible, you should provide the skeletal implementation via default methods on the interface so
        that all implementors of the interface can make use of it.
        Restrictions on interfaces typically mandate that a skeletal implementation take the form of an abstract class.
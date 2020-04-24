Item 41: Use marker interfaces to define types
==============================================

A marker interface is an interface that contains no method declarations but merely designates (or “marks”) a class that
implements the interface as having some property.

    Example:    Serializable interface

                By implementing this interface, a class indicates that its instances can be written to an
                ObjectOutputStream (or “serialized”).

You may hear it said that marker annotations (Item 39) make marker interfaces obsolete.

    This is incorrect. Marker interfaces have two advantages over marker annotations:

        - Marker interfaces define a type that is implemented by instances of the marked class; marker annotations do
            not.
            The existence of a marker interface type allows you to catch errors at compile time that you couldn’t catch
            until runtime if you used a marker annotation.

            Java’s serialization facility uses the Serializable marker interface to indicate that a type is
            serializable:

                The ObjectOutputStream.writeObject(Object) method, which serializes the object that is passed to it,
                requires that its argument be serializable. Had the argument of this method been of type Serializable,
                an attempt to serialize an inappropriate object would have been detected at compile time (by type
                checking).

                Unfortunately, the ObjectOutputStream.write API does not take advantage of the Serializable interface:
                its argument is declared to be of type Object, so attempts to serialize an unserializable object won’t
                fail until runtime.

        - Marker interfaces can be targeted more precisely than marker annotations.
            If an annotation type is declared with target ElementType.TYPE, it can be applied to any class or interface.
            Suppose you have a marker that is applicable only to implementations of a particular interface.

                If you define it as a marker interface, you can have it extend the sole interface to which it is
                applicable, guaranteeing that all marked types are also subtypes of the sole interface to which it is
                applicable.

Arguably, the Set interface is just such a restricted marker interface. It is applicable only to Collection subtypes,
but it adds no methods beyond those defined by Collection. It is not generally considered to be a marker interface
because it refines the contracts of several Collection methods, including add, equals, and hashCode.

But it is easy to imagine a marker interface that is applicable only to subtypes of some particular interface and does
not refine the contracts of any of the interface’s methods.
Such a marker interface might describe some invariant of the entire object or indicate that instances are eligible for
processing  by a method of some other class (in the way that the Serializable interface indicates that instances are
eligible for processing by ObjectOutputStream).

The chief advantage of marker annotations over marker interfaces is that they are part of the larger annotation facility:

    Therefore, marker annotations allow for consistency in annotation-based frameworks.

So when should you use a marker annotation and when should you use a marker interface?

    - You must use an annotation if the marker applies to any program element other than a class or interface, because
        only classes and interfaces can be made to implement or extend an interface.

        If the marker applies only to classes and interfaces, ask yourself the question

            “Might I want to write one or more methods that accept only objects that have this marking?”

            * If so, you should use a marker interface in preference to an annotation.
            This will make it possible for you to use the interface as a parameter type for the methods in question,
            which will result in the benefit of compile-time type checking.

            * If you can convince yourself that you’ll never want to write a method that accepts only objects with the
            marking, then you’re probably better off using a marker annotation.

    - If the marking is part of a framework that makes heavy use of annotations, then a marker annotation is the clear
        choice.

Conclusion:
    marker interfaces and marker annotations both have their uses

        If you want to define a type that does not have any new methods associated with it, a marker interface is the
        way to go.

        If you want to mark program elements other than classes and interfaces or to fit the marker into a framework
        that already makes heavy use of annotation types, then a marker annotation is the correct choice.

        If you find yourself writing a marker annotation type whose target is ElementType.TYPE, take the time to figure
        out whether it really should be an annotation type or whether a marker interface would be more appropriate.

        In a sense, this item is the inverse of Item 22, which says, “If you don’t want to define a type, don’t use an
        interface.”
        To a first approximation, this item says, "If you do want to define a type, do use an interface."
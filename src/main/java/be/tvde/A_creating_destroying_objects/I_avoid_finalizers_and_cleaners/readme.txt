Avoid finalizers and cleaners
=============================

Finalizers have following characteristics:
    - unpredictable, often dangerous, and generally unnecessary
    - they can cause erratic behavior, poor performance, and portability problems.
    - they don't guarantee that they will be executed promptly

Finalizers have a few valid uses, which we’ll cover later in this item, but as a rule of thumb, you should avoid finalizers.

- One shortcoming of finalizers is that there is no guarantee they’ll be executed or that they will be executed promptly.
    * It can take arbitrarily long between the time that an object becomes unreachable and the time that its finalizer is executed.

    This means that you should never do anything time-critical in a finalizer.
        For example, it is a grave error to depend on a finalizer to close files, because open file descriptors are a limited
        resource. If many files are left open because the JVM is tardy in executing finalizers, a program may fail because it
        can no longer open files.

    * The promptness with which finalizers are executed is primarily a function of the garbage collection algorithm, which varies
        widely from JVM implementation to JVM implementation.
        The behavior of a program that depends on the promptness of finalizer execution may likewise vary.
        It is entirely possible that such a program will run perfectly on the JVM on which you test it and then fail miserably on
            the JVM in production.

    * Providing a finalizer for a class can, under rare conditions, arbitrarily delay reclamation of its instances.
        A colleague debugged a long-running GUI application that was mysteriously dying with an OutOfMemoryError. Analysis revealed
        that at the time of its death, the application had thousands of graphics objects on its finalizer queue just waiting to
        be finalized and reclaimed.
        Unfortunately, the finalizer thread was running at a lower priority than another application thread, so objects weren’t
        getting finalized at the rate they became eligible for finalization.
        ==> The language specification makes no guarantees as to which thread will execute finalizers, so there is no portable way
            to prevent this sort of problem other than to refrain from using finalizers.

    * It is entirely possible, even likely, that a program terminates without executing finalizers on some objects that are no longer
        reachable.
        As a consequence, you should never depend on a finalizer to update critical persistent state.
        For example, depending on a finalizer to release a persistent lock on a shared resource such as a database is a good way to
        bring your entire distributed system to a grinding halt.

    * Don’t be seduced by the methods System.gc and System.runFinalization.
        They may increase the odds of finalizers getting executed, but they don’t guarantee it.

        The only methods that claim to guarantee finalization are System.runFinalizersOnExit and its evil twin, Runtime.runFinalizersOnExit.
        These methods are fatally flawed and have been deprecated.

- If an uncaught exception is thrown during finalization, the exception is ignored, and finalization of that object terminates.
    Uncaught exceptions can leave objects in a corrupt state.
    If another thread attempts to use such a corrupted object, arbitrary nondeterministic behavior may result.

    Normally, an uncaught exception will terminate the thread and print a stack trace, but not if it occurs in a finalizer — it won’t even
    print a warning.

- There is a severe performance penalty for using finalizers.
    On my machine, the time to create and destroy a simple object is about 5.6 ns.
    Adding a finalizer increases the time to 2,400 ns. In other words, it is about 430 times slower to create and destroy objects with
    finalizers.

If you have objects with resources that require termination (files, threads, ...), instead of writing finalizers, you do the following:
    - provide an explicit termination method
    - require clients of the class to invoke this method on each instance when it is no longer needed

    !!! In this case, the instance must keep track of whether it has been terminated: the explicit termination method must record in a
        private field that the object is no longer valid, and other methods must check this field and throw an IllegalStateException
        if they are called after the object has been terminated.

    Examples:
        - close methods on InputStream, OutputStream, and java.sql.Connection
        - cancel method on java.util.Timer (performs the necessary state change so the thread associated with a Timer instance will
                terminate itself gently)
        - Graphics.dispose() and Window.dispose() (see java.awt)
            These methods are often overlooked, with predictably horrible performance consequences.
        - Image.flush() deallocates all the resources associated with an Image instance but leaves it in a state where it can still be
                used, reallocating the resources if necessary.

    Explicit termination methods are typically used in combination with the try-finally construct to ensure termination. Invoking the explicit
    termination method inside the finally clause ensures that it will get executed even if an exception is thrown while the object is being used:

        // try-finally block guarantees execution of termination method
        Foo foo = new Foo(...);

        try {
            // Do what must be done with foo
            ...
        } finally {
            foo.terminate(); // Explicit termination method
        }

So what, if anything, are finalizers good for? There are perhaps two legitimate uses.

    - “safety net” in case the owner of an object forgets to call its explicit termination method.
            While there’s no guarantee that the finalizer will be invoked promptly, it may be better to free the resource late than never, in
            those (hopefully rare) cases when the client fails to call the explicit termination method.

            But the finalizer should log a warning if it finds that the resource has not been terminated, as this indicates a bug in the client
            code, which should be fixed. If you are considering writing such a safety-net finalizer, think long and hard about whether the extra
            protection is worth the extra cost.

            The mentioned examples: FileInputStream, FileOutputStream, Timer, and Connection have finalizers that serve as safety nets in case their
            termination methods aren’t called.
            Unfortunately these finalizers do not log warnings. Such warnings generally can’t be added after an API is published, as it would appear to
            break existing clients.

    - A second legitimate use of finalizers concerns objects with native peers.
            A native peer is a native object to which a normal object delegates via native methods. Because a native peer is not a normal object, the
            garbage collector doesn’t know about it and can’t reclaim it when its Java peer is reclaimed.
            A finalizer is an appropriate vehicle for performing this task, assuming the native peer holds no critical resources.

            If the native peer holds resources that must be terminated promptly, the class should have an explicit termination method, as described above.

            The termination method should do whatever is required to free the critical resource.
            The termination method can be a native method, or it can invoke one.

“finalizer chaining” is not performed automatically!

    If a class (other than Object) has a finalizer and a subclass overrides it, the subclass finalizer must invoke the superclass finalizer manually.
    You should finalize the subclass in a try block and invoke the superclass finalizer in the corresponding finally block.
    This ensures that the superclass finalizer gets executed even if the subclass finalization throws an exception and vice versa.

    Example:
        // Manual finalizer chaining
        @Override
        protected void finalize() throws Throwable {
            try {
                ... // Finalize subclass state
            } finally {
                super.finalize();
            }
        }

    If a subclass implementor overrides a superclass finalizer but forgets to invoke it, the superclass finalizer will never be invoked.
    It is possible to defend against such a careless or malicious subclass at the cost of creating an additional object for every object to be finalized.
    Instead of putting the finalizer on the class requiring finalization, put the finalizer on an anonymous class (Item 22) whose sole purpose is to
    finalize its enclosing instance.
    A single instance of the anonymous class, called a finalizer guardian, is created for each instance of the enclosing class.
    The enclosing instance stores the sole reference to its finalizer guardian in a private instance field so the finalizer guardian becomes eligible for
    finalization at the same time as the enclosing instance. When the guardian is finalized, it performs the finalization activity desired for the
    enclosing instance, just as if its finalizer were a method on the enclosing class:

        // Finalizer Guardian idiom
        public class Foo {

            // Sole purpose of this object is to finalize outer Foo object
            private final Object finalizerGuardian = new Object() {
                @Override protected void finalize() throws Throwable {
                    ... // Finalize outer Foo object
                }
            };
            ... // Remainder omitted
        }

Note that the public class, Foo, has no finalizer (other than the trivial one it
inherits from Object), so it doesn’t matter whether a subclass finalizer calls
super.finalize or not. This technique should be considered for every nonfinal
public class that has a finalizer.
In summary, don’t use finalizers except as a safety net or to terminate
noncritical native resources. In those rare instances where you do use a finalizer,
remember to invoke super.finalize. If you use a finalizer as a safety net,
remember to log the invalid usage from the finalizer. Lastly, if you need to
associate a finalizer with a public, nonfinal class, consider using a finalizer
guardian, so finalization can take place even if a subclass finalizer fails to invoke
super.finalize.
Eliminate obsolete object references
====================================

Consider code in class MyStack. There is a memory leak --> reduced performance due to increased GC activity or
    increased memory footprint. In extreme cases, such memory leaks can cause disk paging and even program
    failure with an OutOfMemoryError.
    If stack grows and then shrinks, objects that were popped off the stack, will not be garbage collected. This is
    because the stack maintains obsolete references to these objects.

Obsolete reference = a reference that will never be dereferenced again. Any objects referenced by an obsolete object,
    are also excluded from garbage collection -- >potentially large effects on performance.

Solution: null out references once they become obsolete:

    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        Object result = elements[--size];
        elements[size] = null;
        return result;
    }

Nulling out object references should be the exception rather than the norm.
    It clutters up the program
    Best way to eliminate an obsolete reference is to let the variable that contained the reference fall out of scope.
        To do this, define each variable in the narrowest possible scope.

Although nulling out object references is not the norm, it has 1 benefit: if you nullout a reference accidentally, your
    program will generate soon a NullPointerException, instead of doing the wrong thing without notifying you about the
    error.



Generally speaking, whenever a class manages its own memory, the programmer should be alert for memory leaks. Whenever an
element is freed, any object references contained in the element should be nulled out.

Another common source of memory leaks is CACHES. Once you put an object reference into a cache, it’s easy to forget that
it’s there and leave it in the cache long after it becomes irrelevant.
    There are several solutions to this problem.
        If you’re lucky enough to implement a cache for which an entry is relevant exactly so long as there are references
            to its key outside of the cache, represent the cache as a WeakHashMap; --> entries will be removed automatically
            after they become obsolete.

            Remember that WeakHashMap is useful only if the desired lifetime of cache entries is determined by external
            references to the key, not the value.

        More commonly, the useful lifetime of a cache entry is less well defined, with entries becoming less valuable over
            time. Under these circumstances, the cache should occasionally be cleansed of entries that have fallen into disuse.

            This can be done by a background thread (perhaps a Timer or ScheduledThreadPoolExecutor) or as a side effect of
            adding new entries to the cache.
            The LinkedHashMap class facilitates the latter approach with its removeEldestEntry method.

        For more sophisticated caches, you may need to use java.lang.ref directly.

Yet another common source of memory leaks is LISTENERS and other CALLBACKS.
    If you implement an API where clients register callbacks but don’t deregister them explicitly, they will accumulate unless
    you take some action.
    The best way to ensure that callbacks are garbage collected promptly is to store only weak references to them, for instance,
    by storing them only as keys in a WeakHashMap.

Memory leaks can stay hidden in a system for years. You can discover them with:
    - a careful CODE INSPECTION
    - the aid of a debugging tool known as a HEAP PROFILER

Therefore, it is very desirable to learn to anticipate problems like this before they occur and prevent them from happening.
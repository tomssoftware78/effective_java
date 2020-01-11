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
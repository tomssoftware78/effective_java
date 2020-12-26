filter(predicate) returns a stream of elements matching the given predicate

the operation is lazy meaning filter() does not actually perform any filtering but instead creates a new
   stream that, when traversed contains the elements of the initial stream that matched the predicate


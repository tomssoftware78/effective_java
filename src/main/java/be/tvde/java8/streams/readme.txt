A stream is a sequence of objects that supports various methods which can be pipelined to produce the desired
result

java.util.Stream supoort map-filter-reduce transformation on collections

A stream is not a data structure. It takes input from    - collections
                                                         - arrays
                                                         - I/O channels

A stream does not change the original data structure from which it is constructed, it only returns the
   results as per the pipeline operations

Each intermediate operation is lazily executed and return stream which can be pipelined with further operations

Features of streams:
   - parallel operations are easy to perform with stream api without having to spawn multiple threads explicitly
   - fork-join which got introduced in Java 7 made it easy to perform parallel operation but stream api
      made it really simple

Intermediate Operations on Streams
==================================

Map
---
   return a stream consisting of the results of applying the given FUNCTION to the elements of this stream

Filter
------
   used to filter the results based on PREDICATE passed in the filter

Sorted
------
   used to sort the stream based on COMPARATOR

Terminal Operations on Streams
==============================

Collect
-------
   used to return the result of the terminal operations performed on the stream

forEach
-------
   used to iterate over the stream

Reduce
------
   performs a reduction on the elements of the stream


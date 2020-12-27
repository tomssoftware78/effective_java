counting() method in the Collectors class counts the number of elements in a Stream

   Stream.of(1, 2, 3, 4, 5).collect(Collectors.counting());
   is same as
   Stream.of(1, 2, 3, 4, 5).count();

Helpful in downstream operations
   groupingBy(String::length, counting());
mapping() method in Collectors class takes a function and another collector,
   and creates a new collector which apply the function and then collects the mapped elements
      using the given collector

   Collectors.mapping(Instructor::getName, Collectors.toList());
   is same as
   .map(Instructor::getName).collect(Collectors.toList());

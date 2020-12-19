Lambda expression = anonymous function, without a name and does not belong to any class

A method in Java has:
   - a name
   - parameter list
   - a body
   - a return type

A lambda expression in Java has:
   - no name, it is an anonymous function
   - parameter list
   - body
   - no return type, java compiler is able to infer the return type

(  ) -> {  }

Lambda is mainly used to implement functional interfaces (Single Abstract Method)
   ex:   @FunctionalInterface
         public interface Runnable {
            public abstract void run();
         }

         @FunctionalInterface
         public interface Comparator<T> {
            int compare(T o1, T o2);
         }



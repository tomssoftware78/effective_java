package be.tvde.java8.lambdaexpressions.example3;

public class ConcatenateLambda {

   public static void main(String[] args) {
      ConcatenateInterface ct = (a, b) -> {
         return a + b;
      };

      System.out.println(ct.concat("Hello ", "World"));
   }
}

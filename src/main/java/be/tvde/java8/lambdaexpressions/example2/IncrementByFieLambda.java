package be.tvde.java8.lambdaexpressions.example2;

public class IncrementByFieLambda {

   public static void main(String[] args) {
      IncrementByFiveInterface i = (x) -> {
         return x + 5;
      };

      System.out.println(i.incrementByFive(20));
   }

}

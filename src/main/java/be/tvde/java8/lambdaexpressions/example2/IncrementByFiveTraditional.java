package be.tvde.java8.lambdaexpressions.example2;

public class IncrementByFiveTraditional implements IncrementByFiveInterface {

   @Override
   public int incrementByFive(final int a) {
      return a + 5;
   }

   public static void main(String[] args) {
      IncrementByFiveTraditional o = new IncrementByFiveTraditional();
      System.out.println(o.incrementByFive(10));
   }
}

package be.tvde.java8.lambdaexpressions.example3;

public class ConcatenateTraditional implements ConcatenateInterface {

   public static void main(String[] args) {
      ConcatenateTraditional ct = new ConcatenateTraditional();
      System.out.println(ct.concat("Hello ", "World"));
   }

   @Override
   public String concat(final String a, final String b) {
      return a + b;
   }
}

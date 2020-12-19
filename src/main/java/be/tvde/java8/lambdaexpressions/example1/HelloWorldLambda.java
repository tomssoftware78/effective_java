package be.tvde.java8.lambdaexpressions.example1;

public class HelloWorldLambda {

   public static void main(String[] args) {
      HelloWorldInterface helloWorld = () -> {
         return "Hello World from a lambda";
      };

      System.out.println(helloWorld.sayHelloWorld());
   }
}

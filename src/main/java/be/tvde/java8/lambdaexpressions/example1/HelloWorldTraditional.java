package be.tvde.java8.lambdaexpressions.example1;

public class HelloWorldTraditional implements HelloWorldInterface {

   @Override
   public String sayHelloWorld() {
      return "Hello World";
   }

   public static void main(String[] args) {
      HelloWorldTraditional helloWorldTraditional = new HelloWorldTraditional();
      System.out.println(helloWorldTraditional.sayHelloWorld());
   }
}

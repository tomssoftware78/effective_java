package be.tvde.java8.functionalinterfaces;

@FunctionalInterface
public interface MyFunctionalInterface {

   public void subString(); //the abstract unimplemented method

   //Before java 8, interfaces could have only abstract methods
   public default void print(String text) {
      System.out.println(text);
   }

   public static void print(String text1, String text2) {
      System.out.println(text1 + " " + text2);
   }
}

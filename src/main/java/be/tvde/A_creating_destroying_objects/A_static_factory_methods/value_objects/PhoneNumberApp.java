package be.tvde.A_creating_destroying_objects.A_static_factory_methods.value_objects;

public class PhoneNumberApp {

   public static void main(String[] args) {
      try {
         PhoneNumber invalid = PhoneNumber.of(1, 2);
      } catch (IllegalArgumentException e) {
         System.out.println(e.getMessage());
      }

      PhoneNumber common = PhoneNumber.of(123, 1234);
      PhoneNumber common2 = PhoneNumber.of(123, 1234);

      //due to Flyweight pattern, we do not have to use equals method
      System.out.println(common == common2);

   }

}

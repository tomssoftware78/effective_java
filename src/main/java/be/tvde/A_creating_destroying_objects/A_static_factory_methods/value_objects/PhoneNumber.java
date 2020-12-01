package be.tvde.A_creating_destroying_objects.A_static_factory_methods.value_objects;

import static com.google.common.base.Preconditions.checkArgument;

public class PhoneNumber {

   private static final PhoneNumber COMMON = new PhoneNumber(123, 1234);

   private final int areaCode;
   private final int number;

   private PhoneNumber(final int areaCode, final int number) {
      this.areaCode = areaCode;
      this.number = number;
   }

   public static PhoneNumber of(int areaCode, int number) {
      //Input validation with guava, will throw IllegalArgumentException if not ok
      checkArgument(areaCode > 100, "Area code should be > 100");
      checkArgument(number > 1000, "Number should be > 1000");

      if (areaCode == 123 && number == 1234) {
         return COMMON;
      }

      return new PhoneNumber(areaCode, number);
   }
}

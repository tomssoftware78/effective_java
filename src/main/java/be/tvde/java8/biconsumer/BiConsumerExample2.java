package be.tvde.java8.biconsumer;

import java.sql.SQLOutput;
import java.util.List;
import java.util.function.BiConsumer;
import be.tvde.java8.model.Instructors;

public class BiConsumerExample2 {

   public static void main(String[] args) {
      BiConsumer<String, String> biConsumer = (name, gender) -> {
         System.out.println("name is " + name + " and gender is: " + gender);
      };

      Instructors.getAll().forEach(
            instructor ->biConsumer.accept(
                                       instructor.getName(),
                                       instructor.getGender()
                                          )
                                  );

      System.out.println("------------------");
      BiConsumer<String, List<String>> biConsumer1 = (name, courses) -> {
         System.out.println("name is " + name + " courses: " + courses);
      };
      Instructors.getAll().forEach(
            instructor ->biConsumer1.accept(
                                             instructor.getName(),
                                             instructor.getCourses()
                                           )
                                  );
      System.out.println("------------------");
      //print name and gender of all instructors who teach online
      System.out.println("name and gender of all instructors who teach online: ");
      Instructors.getAll()
                 .forEach(
                       instructor -> {
                          if (instructor.isOnlineCourses()) {
                             biConsumer.accept(
                                   instructor.getName(),
                                   instructor.getGender()
                                              );
                          }
                       }
                 );
   }
}

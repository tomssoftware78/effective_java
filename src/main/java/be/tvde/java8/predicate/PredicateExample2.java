package be.tvde.java8.predicate;

import java.util.function.Predicate;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class PredicateExample2 {

   public static void main(String[] args) {
      //instructor teaches online
      Predicate<Instructor> teachesOnline = instructor -> {
         return instructor.isOnlineCourses() == true;
      };

      Predicate<Instructor> tenYearsExperience = instructor -> {
         return instructor.getYearsOfExperience() > 10;
      };

      Instructors.getAll()
                 .forEach( instructor ->{
                    if (teachesOnline.and(tenYearsExperience).test(instructor)) {
                       System.out.println(instructor);
                    }
                 });
   }
}

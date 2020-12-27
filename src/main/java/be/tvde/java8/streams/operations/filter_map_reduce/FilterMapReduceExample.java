package be.tvde.java8.streams.operations.filter_map_reduce;

import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class FilterMapReduceExample {

   public static void main(String[] args) {
      //total years of experience b/w instructors
      final Integer totalYearsOfExperience = Instructors.getAll()
                                                        .stream()
                                                        .filter(instructor->instructor.isOnlineCourses())
                                                        .map(Instructor::getYearsOfExperience)
                                                        //.reduce(0, Integer::sum)
                                                        .reduce(0, (a, b)->a + b);

      System.out.println(totalYearsOfExperience);

   }
}

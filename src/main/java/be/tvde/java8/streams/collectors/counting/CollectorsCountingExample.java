package be.tvde.java8.streams.collectors.counting;

import java.util.stream.Collectors;
import be.tvde.java8.model.Instructors;

public class CollectorsCountingExample {

   public static void main(String[] args) {
      //counting numbers of instructors who teach online courses
      final Long nrTeachingOnline = Instructors.getAll()
                                      .stream()
                                      .filter(instructor->instructor.isOnlineCourses())
                                      .collect(Collectors.counting());
      System.out.println(nrTeachingOnline);

      final long nrTeachingOnline1 = Instructors.getAll()
                                    .stream()
                                    .filter(instructor->instructor.isOnlineCourses())
                                    .count();
      System.out.println(nrTeachingOnline1);

   }
}

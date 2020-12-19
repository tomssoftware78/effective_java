package be.tvde.java8.model;

import java.util.List;

public class Instructor {

   String name;
   int yearsOfExperience;
   String title;
   String gender;
   boolean onlineCourses;
   List<String> courses;

   public Instructor(final String name, final int yearsOfExperience, final String title, final String gender, final boolean onlineCourses, final List<String> courses) {
      this.name = name;
      this.yearsOfExperience = yearsOfExperience;
      this.title = title;
      this.gender = gender;
      this.onlineCourses = onlineCourses;
      this.courses = courses;
   }

   @Override
   public String toString() {
      return "Instructor{" +
             "name='" + name + '\'' +
             ", yearsOfExperience=" + yearsOfExperience +
             ", title='" + title + '\'' +
             ", gender='" + gender + '\'' +
             ", onlineCourses=" + onlineCourses +
             ", courses=" + courses +
             '}';
   }

   public String getName() {
      return name;
   }

   public Instructor setName(final String name) {
      this.name = name;
      return this;
   }

   public int getYearsOfExperience() {
      return yearsOfExperience;
   }

   public Instructor setYearsOfExperience(final int yearsOfExperience) {
      this.yearsOfExperience = yearsOfExperience;
      return this;
   }

   public String getTitle() {
      return title;
   }

   public Instructor setTitle(final String title) {
      this.title = title;
      return this;
   }

   public String getGender() {
      return gender;
   }

   public Instructor setGender(final String gender) {
      this.gender = gender;
      return this;
   }

   public boolean isOnlineCourses() {
      return onlineCourses;
   }

   public Instructor setOnlineCourses(final boolean onlineCourses) {
      this.onlineCourses = onlineCourses;
      return this;
   }

   public List<String> getCourses() {
      return courses;
   }

   public Instructor setCourses(final List<String> courses) {
      this.courses = courses;
      return this;
   }
}

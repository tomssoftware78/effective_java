package be.tvde.A_creating_destroying_objects.G_creating_unneccessary_objects;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Person_InEfficient {

    private final Date birthDate;

    // Other fields, and methods

    public Person_InEfficient(Date birthDate) {
        this.birthDate = birthDate;
        //other fields omitted
    }

    public boolean isBabyBoomer() {
        //Unnecessary allocation of expensive object
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        gmtCal.set(1946, Calendar.JANUARY, 1, 0, 0, 0);

        Date boomStart = gmtCal.getTime();

        gmtCal.set(1965, Calendar.JANUARY, 1, 0, 0, 0);

        Date boomEnd = gmtCal.getTime();

        return birthDate.compareTo(boomStart) >= 0 && birthDate.compareTo(boomEnd) < 0;
    }


}

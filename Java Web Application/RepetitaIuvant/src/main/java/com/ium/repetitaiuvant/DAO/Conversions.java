package com.ium.repetitaiuvant.DAO;

public class Conversions {
    public static Role stringToRole(String string)
    {
        if(string.equals("Admin")) return Role.ADMIN;
        if(string.equals("Student")) return Role.STUDENT;
        else  return null;
    }

    public static Day stringToDay(String string) {
        if(string.equals("Monday")) return Day.MONDAY;
        if(string.equals("Tuesday")) return Day.TUESDAY;
        if(string.equals("Wednesday")) return Day.WEDNESDAY;
        if(string.equals("Thursday")) return Day.THURSDAY;
        if(string.equals("Friday")) return Day.FRIDAY;
        if(string.equals("Saturday")) return Day.SATURDAY;
        if(string.equals("Sunday")) return Day.SUNDAY;
        else return null;
    }

    public static Time stringToTime(String string) {
        if(string.equals("08:00")) return Time.EIGHT;
        if(string.equals("09:00")) return Time.NINE;
        if(string.equals("10:00")) return Time.TEN;
        if(string.equals("11:00")) return Time.ELEVEN;
        if(string.equals("12:00")) return Time.TWELVE;
        if(string.equals("13:00")) return Time.THIRTEEN;
        if(string.equals("14:00")) return Time.FOURTEEN;
        if(string.equals("15:00")) return Time.FIFTEEN;
        if(string.equals("16:00")) return Time.SIXTEEN;
        if(string.equals("17:00")) return Time.SEVENTEEN;
        if(string.equals("18:00")) return Time.EIGHTEEN;
        if(string.equals("19:00")) return Time.NINETEEN;
        else return null;
    }
}

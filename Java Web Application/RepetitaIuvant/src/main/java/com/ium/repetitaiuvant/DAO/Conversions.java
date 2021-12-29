package com.ium.repetitaiuvant.DAO;

public class Conversions {
    public static Role stringToRole(String string) {
        if (string.equals("Admin")) return Role.ADMIN;
        if (string.equals("Student")) return Role.STUDENT;
        else return null;
    }

    public static String roleToString(Role role) {
        if (role == Role.ADMIN) return "Admin";
        if (role == Role.STUDENT) return "Student";
        else return null;
    }

    public static Day stringToDay(String string) {
        if (string.equals("Monday")) return Day.MONDAY;
        if (string.equals("Tuesday")) return Day.TUESDAY;
        if (string.equals("Wednesday")) return Day.WEDNESDAY;
        if (string.equals("Thursday")) return Day.THURSDAY;
        if (string.equals("Friday")) return Day.FRIDAY;
        if (string.equals("Saturday")) return Day.SATURDAY;
        if (string.equals("Sunday")) return Day.SUNDAY;
        else return null;
    }

    public static String dayToString(Day day) {
        if (day == Day.MONDAY) return "Monday";
        if (day == Day.TUESDAY) return "Tuesday";
        if (day == Day.WEDNESDAY) return "Wednesday";
        if (day == Day.THURSDAY) return "Thursday";
        if (day == Day.FRIDAY) return "Friday";
        if (day == Day.SATURDAY) return "Saturday";
        if (day == Day.SUNDAY) return "Sunday";
        else return null;
    }


    public static Time stringToTime(String string) {
        if (string.equals("08:00")) return Time.EIGHT;
        if (string.equals("09:00")) return Time.NINE;
        if (string.equals("10:00")) return Time.TEN;
        if (string.equals("11:00")) return Time.ELEVEN;
        if (string.equals("12:00")) return Time.TWELVE;
        if (string.equals("13:00")) return Time.THIRTEEN;
        if (string.equals("14:00")) return Time.FOURTEEN;
        if (string.equals("15:00")) return Time.FIFTEEN;
        if (string.equals("16:00")) return Time.SIXTEEN;
        if (string.equals("17:00")) return Time.SEVENTEEN;
        if (string.equals("18:00")) return Time.EIGHTEEN;
        if (string.equals("19:00")) return Time.NINETEEN;
        else return null;
    }

    public static String timeToString(Time time) {
        if (time == Time.EIGHT) return "08:00";
        if (time == Time.NINE) return "09:00";
        if (time == Time.TEN) return "10:00";
        if (time == Time.ELEVEN) return "11:00";
        if (time == Time.TWELVE) return "12:00";
        if (time == Time.THIRTEEN) return "13:00";
        if (time == Time.FOURTEEN) return "14:00";
        if (time == Time.FIFTEEN) return "15:00";
        if (time == Time.SIXTEEN) return "16:00";
        if (time == Time.SEVENTEEN) return "17:00";
        if (time == Time.EIGHTEEN) return "18:00";
        if (time == Time.NINETEEN) return "19:00";
        else return null;
    }

    public static State stringToState(String string){
        if(string.equals("Active")) return State.ACTIVE;
        if(string.equals("Done")) return State.DONE;
        if(string.equals("Cancelled")) return State.CANCELLED;
        else return null;
    }

    public static String stateToString(State state)
    {
        if (state == State.ACTIVE) return "Active";
        if (state == State.DONE) return "Done";
        if (state == State.CANCELLED) return "Cancelled";
        else return null;
    }
}

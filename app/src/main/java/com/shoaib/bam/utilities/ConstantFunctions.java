package com.shoaib.bam.utilities;

public class ConstantFunctions {

    public static String getratingRandomId()
    {
        String assignmentId = "";
        int max = 999999999;
        int min = 1;
        int random = (int )(Math.random() * max + min);
        String timeStemp = String.valueOf(System.currentTimeMillis());
        return assignmentId = timeStemp+"_"+String.valueOf(random);
        //return assignmentId = String.valueOf(random);
    }

    public static long gettingCurrentTimeStepm() {
        return System.currentTimeMillis();
    }
}

package com.example.teleg.programm.serverSide.Schedule;


public class TimeCalculated {
    static String timeStart;
    static String timeEnd;

    public static String calculateStart(int number) {
        switch (number) {
            case 1: {
                timeStart = "8:10";
                return timeStart;
            }

            case 2: {
                timeStart = "9:55";
                return timeStart;
            }

            case 3: {
                timeStart = "11:40";
                return timeStart;
            }

            case 4: {
                timeStart = "13:35";
                return timeStart;
            }

            case 5: {
                timeStart = "15:20";
                return timeStart;
            }

            case 6: {
                timeStart = "17:05";
                return timeStart;
            }

            case 7: {
                timeStart = "18:50";
                return timeStart;
            }

            case 8: {
                timeStart = "20:25";
                return timeStart;
            }
            default:
                return "00:00";
        }
    }

    public static String calculateEnd(int numb) {
        switch (numb) {
            case 1: {
                timeEnd = "9:45";
                return timeEnd;
            }

            case 2: {
                timeEnd = "11:30";
                return timeEnd;
            }

            case 3: {
                timeEnd = "13:15";
                return timeEnd;
            }

            case 4: {
                timeEnd = "15:10";
                return timeEnd;
            }

            case 5: {
                timeEnd = "16:55";
                return timeEnd;
            }

            case 6: {
                timeEnd = "18:40";
                return timeEnd;
            }

            case 7: {
                timeEnd = "20:15";
                return timeEnd;
            }

            case 8: {
                timeEnd = "21:50";
                return timeEnd;
            }
            default:  return "00:00";
        }
    }
}


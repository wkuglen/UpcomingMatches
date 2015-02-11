package com.wkuglen.upcomingmatches.matchmanager;

import java.util.Calendar;

/**
 * Created by wkuglen on 2/10/15.
 */
public class Match {

    public static final boolean STATUS_IS_UPCOMING = true;
    public static final boolean STATUS_IS_PLAYED = false;
    public static final boolean ALLIANCE_COLOR_BLUE = true;
    public static final boolean ALLIANCE_COLOR_RED = false;

    private int matchNumber;
    private int timeHour;
    private int timeMinute;
    private boolean allianceColor;
    private boolean status;

    public Match(){
        matchNumber = 0;
        timeHour = 0;
        timeMinute = 0;
        allianceColor = ALLIANCE_COLOR_BLUE;
        status = STATUS_IS_UPCOMING;
    }

    public Match(int matchNumber, int timeHour, int timeMinute, boolean allianceColor, boolean status) {
        this.matchNumber = matchNumber;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.allianceColor = allianceColor;
        this.status = status;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public int getTimeHour() {
        return timeHour;
    }

    public void setTimeHour(int timeHour) {
        this.timeHour = timeHour;
    }

    public int getTimeMinute() {
        return timeMinute;
    }

    public void setTimeMinute(int timeMinute) {
        this.timeMinute = timeMinute;
    }

    public boolean getAllianceColor() {
        return allianceColor;
    }

    public void setAllianceColor(boolean allianceColor) {
        this.allianceColor = allianceColor;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String toReturn = "The Upcoming Match is " +
                            "#" + matchNumber +
                            " @ " + timeHour +
                            ":" + timeMinute;
        if(allianceColor == ALLIANCE_COLOR_BLUE)
            toReturn += " on the Blue Alliance.";
        if(allianceColor == ALLIANCE_COLOR_RED)
            toReturn += " on the Red Alliance.";
        return toReturn;
    }
}

package com.example.teleg.programm.CalendarClasses;

import android.graphics.drawable.Drawable;

import com.example.teleg.programm.CalendarClasses.model.CalendarItemStyle;
import com.example.teleg.programm.CalendarClasses.model.HorizontalCalendarConfig;


public class ConfigBuilder {

    /* Format & Font Sizes*/
    private float sizeTopText;
    private float sizeMiddleText;
    private float sizeBottomText;
    private Integer selectorColor;
    private String formatTopText;
    private String formatMiddleText;
    private String formatBottomText;
    private boolean showTopText = true;
    private boolean showBottomText = true;

    /* Colors and Background*/
    private int colorTextTop, colorTextTopSelected;
    private int colorTextMiddle, colorTextMiddleSelected;
    private int colorTextBottom, colorTextBottomSelected;
    private Drawable selectedItemBackground;

    private final HorizontalCalendar.Builder calendarBuilder;

    public ConfigBuilder(HorizontalCalendar.Builder calendarBuilder) {
        this.calendarBuilder = calendarBuilder;
    }


    public ConfigBuilder textSize(float sizeTopText, float sizeMiddleText,
                                  float sizeBottomText) {
        this.sizeTopText = sizeTopText;
        this.sizeMiddleText = sizeMiddleText;
        this.sizeBottomText = sizeBottomText;
        return this;
    }


    public ConfigBuilder sizeTopText(float size) {
        this.sizeTopText = size;
        return this;
    }


    public ConfigBuilder sizeMiddleText(float size) {
        this.sizeMiddleText = size;
        return this;
    }


    public ConfigBuilder sizeBottomText(float size) {
        this.sizeBottomText = size;
        return this;
    }

    public ConfigBuilder selectorColor(Integer selectorColor) {
        this.selectorColor = selectorColor;
        return this;
    }

    public ConfigBuilder formatTopText(String format) {
        this.formatTopText = format;
        return this;
    }

    public ConfigBuilder formatMiddleText(String format) {
        this.formatMiddleText = format;
        return this;
    }

    public ConfigBuilder formatBottomText(String format) {
        this.formatBottomText = format;
        return this;
    }

    public ConfigBuilder showTopText(boolean value) {
        this.showTopText = value;
        return this;
    }

    public ConfigBuilder showBottomText(boolean value) {
        this.showBottomText = value;
        return this;
    }

    public ConfigBuilder textColor(int textColorNormal, int textColorSelected) {
        colorTextTop = textColorNormal;
        colorTextMiddle = textColorNormal;
        colorTextBottom = textColorNormal;

        colorTextTopSelected = textColorSelected;
        colorTextMiddleSelected = textColorSelected;
        colorTextBottomSelected = textColorSelected;
        return this;
    }

    public ConfigBuilder colorTextTop(int textColorNormal, int textColorSelected) {
        colorTextTop = textColorNormal;
        colorTextTopSelected = textColorSelected;
        return this;
    }

    public ConfigBuilder colorTextMiddle(int textColorNormal, int textColorSelected) {
        colorTextMiddle = textColorNormal;
        colorTextMiddleSelected = textColorSelected;
        return this;
    }

    public ConfigBuilder colorTextBottom(int textColorNormal, int textColorSelected) {
        colorTextBottom = textColorNormal;
        colorTextBottomSelected = textColorSelected;
        return this;
    }

    public ConfigBuilder selectedDateBackground(Drawable background) {
        this.selectedItemBackground = background;
        return this;
    }

    public HorizontalCalendar.Builder end() {
        if (formatMiddleText == null) {
            formatMiddleText = HorizontalCalendarConfig.DEFAULT_FORMAT_TEXT_MIDDLE;
        }
        if ((formatTopText == null) && showTopText) {
            formatTopText = HorizontalCalendarConfig.DEFAULT_FORMAT_TEXT_TOP;
        }
        if ((formatBottomText == null) && showBottomText) {
            formatBottomText = HorizontalCalendarConfig.DEFAULT_FORMAT_TEXT_BOTTOM;
        }
        return calendarBuilder;
    }

    HorizontalCalendarConfig createConfig() {
        HorizontalCalendarConfig config = new HorizontalCalendarConfig(sizeTopText, sizeMiddleText, sizeBottomText, selectorColor);
        config.setFormatTopText(formatTopText);
        config.setFormatMiddleText(formatMiddleText);
        config.setFormatBottomText(formatBottomText);
        config.setShowTopText(showTopText);
        config.setShowBottomText(showBottomText);

        return config;
    }

    CalendarItemStyle createDefaultStyle() {
        return new CalendarItemStyle(colorTextTop, colorTextMiddle, colorTextBottom, null);
    }

    CalendarItemStyle createSelectedItemStyle() {
        return new CalendarItemStyle(colorTextTopSelected, colorTextMiddleSelected, colorTextBottomSelected, selectedItemBackground);
    }
}

package com.example.teleg.programm.MainCalendarClasses.dao;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.teleg.programm.R;


public class SectionTitleViewHolder extends RecyclerView.ViewHolder {
    private TextView txtSection;

    public TextView getTxtSection() {
        return txtSection;
    }

    public void setTxtSection(TextView txtSection) {
        this.txtSection = txtSection;
    }

    public SectionTitleViewHolder(View v) {
        super(v);
        txtSection = (TextView) v.findViewById(R.id.txtSection);
    }
}

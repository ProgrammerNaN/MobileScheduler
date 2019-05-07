package com.example.teleg.programm.serverSide.Schedule;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teleg.programm.R;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private List<Lesson> numerator;


    public PostsAdapter(List<Lesson> numerator) {
        this.numerator = numerator;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_schedule_main_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostsAdapter.ViewHolder holder, int position){

        Lesson lesson = numerator.get(position);

        holder.title.setText(lesson.getTitle());
        holder.type.setText(lesson.getType());
        holder.room.setText(lesson.getRoom());
        holder.teachers.setText(lesson.getTeachers());

        switch (lesson.getTimeId()) {
            case 1:{
                holder.timeStart.setText("8:10");
                holder.timeEnd.setText("9:45");
                break;
            }

            case 2: {
                holder.timeStart.setText("9:55");
                holder.timeEnd.setText("11:30");
                break;
            }

            case 3: {
                holder.timeStart.setText("11:40");
                holder.timeEnd.setText("13:15");
                break;
            }

            case 4: {
                holder.timeStart.setText("13:35");
                holder.timeEnd.setText("15:10");
                break;
            }

            case 5: {
                holder.timeStart.setText("15:20");
                holder.timeEnd.setText("16:55");
                break;
            }

            case 6: {
                holder.timeStart.setText("17:05");
                holder.timeEnd.setText("18:40");
                break;
            }

            case 7: {
                holder.timeStart.setText("18:50");
                holder.timeEnd.setText("20:15");
                break;
            }

            case 8: {
                holder.timeStart.setText("20:25");
                holder.timeEnd.setText("21:50");
                break;
            }

            case 9: {
                holder.timeStart.setText("9:00");
                holder.timeEnd.setText("17:00");
                break;
            }

        }

    }

    @Override
    public int getItemCount() {
        if (numerator == null) {
            return 0;
        }

        return numerator.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView scheduleCardView;
        TextView title;
        TextView type;
        TextView room;
        TextView teachers;
        TextView timeStart;
        TextView timeEnd;

        public ViewHolder(View itemView) {
            super(itemView);

            scheduleCardView = (CardView) itemView.findViewById(R.id.scheduleCardView);
            title = (TextView) itemView.findViewById(R.id.postitem_title);
            type = (TextView) itemView.findViewById(R.id.postitem_type);
            room = (TextView) itemView.findViewById(R.id.postitem_room);
            teachers = (TextView) itemView.findViewById(R.id.postitem_teachers);
            timeStart = (TextView) itemView.findViewById(R.id.timeStart);
            timeEnd = (TextView) itemView.findViewById(R.id.timeEnd);

        }
    }
}

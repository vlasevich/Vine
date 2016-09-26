package com.home.vlas.vine.activity.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.vlas.vine.R;
import com.home.vlas.vine.activity.realm.model.Reminder;

import java.util.List;

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.ReminderViewHolder> {
    private List<Reminder> reminderList;
    private Context context;

    public RemindersAdapter(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    public RemindersAdapter(Context context, List<Reminder> reminderList) {
        this.context = context;
        this.reminderList = reminderList;
    }

    @Override
    public ReminderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder_list_row, parent, false);

        return new ReminderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReminderViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);
        holder.time.setText(reminder.getDate());
        holder.count.setText(reminder.getBottleCount());
        holder.name.setText(reminder.getWineName());
        holder.comment.setText(reminder.getText());

        holder.box.setVisibility(View.VISIBLE);
        holder.bottle.setVisibility(View.VISIBLE);

        if (reminder.getReminderType().equals("1")) {
            holder.colorBorder.setBackgroundColor(context.getResources().getColor(R.color.colorLoginButton));
            holder.bottle.setBackgroundResource(R.drawable.shape);
            holder.box.setVisibility(View.INVISIBLE);
        } else {
            holder.colorBorder.setBackgroundColor(context.getResources().getColor(R.color.redColor));
            holder.box.setBackgroundResource(R.drawable.boxes_pictogram);
            holder.bottle.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public class ReminderViewHolder extends RecyclerView.ViewHolder {
        TextView time, comment, name, count, colorBorder;
        ImageView bottle, box;

        public ReminderViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.reminderTime);
            comment = (TextView) itemView.findViewById(R.id.comment);
            name = (TextView) itemView.findViewById(R.id.name);
            count = (TextView) itemView.findViewById(R.id.count);
            colorBorder = (TextView) itemView.findViewById(R.id.leftColorBorder);
            bottle = (ImageView) itemView.findViewById(R.id.bottle);
            box = (ImageView) itemView.findViewById(R.id.boxreminderBox);
        }
    }
}

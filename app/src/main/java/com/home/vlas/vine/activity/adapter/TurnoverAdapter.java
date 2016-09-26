package com.home.vlas.vine.activity.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.vlas.vine.R;
import com.home.vlas.vine.activity.model.TurnoverPair;
import com.home.vlas.vine.activity.realm.model.Turnover;

import java.util.List;

public class TurnoverAdapter extends RecyclerView.Adapter<TurnoverAdapter.TurnoverViewHolder> {
    private List<TurnoverPair> turnoverPairList;
    private Context context;

    public TurnoverAdapter(Context context, List<TurnoverPair> turnoverPairList) {
        this.context = context;
        this.turnoverPairList = turnoverPairList;
    }

    public TurnoverAdapter(List<TurnoverPair> turnoverPairList) {
        this.turnoverPairList = turnoverPairList;
    }

    @Override
    public TurnoverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.turnover_list_row, parent, false);

        return new TurnoverViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TurnoverViewHolder holder, int position) {
        TurnoverPair turnoverPair = turnoverPairList.get(position);

        holder.leftCardView.setVisibility(View.INVISIBLE);
        holder.rightCardView.setVisibility(View.INVISIBLE);

        if (turnoverPair.getTurnoverList().size() == 2) {
            for (Turnover t : turnoverPair.getTurnoverList()) {
                switch (t.getStatusId()) {
                    case "1": {
                        holder.leftBottleCount.setText(t.getBottleCount());
                        holder.leftBoxCount.setText(t.getBoxCount());
                        holder.leftDate.setText(t.getDate());
                        holder.leftName.setText(t.getWineName());

                        holder.leftCardView.setVisibility(View.VISIBLE);
                        break;
                    }
                    case "0": {
                        holder.rightBoxCount.setText(t.getBoxCount());
                        holder.rightBottleCount.setText(t.getBottleCount());
                        holder.rightDate.setText(t.getDate());
                        holder.rightName.setText(t.getWineName());

                        holder.rightCardView.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        } else {
            Turnover t = turnoverPair.getTurnoverList().get(0);
            switch (t.getStatusId()) {
                case "1": {
                    holder.leftBottleCount.setText(t.getBottleCount());
                    holder.leftBoxCount.setText(t.getBoxCount());
                    holder.leftDate.setText(t.getDate());
                    holder.leftName.setText(t.getWineName());

                    holder.leftCardView.setVisibility(View.VISIBLE);
                    break;
                }
                case "0": {
                    holder.rightBoxCount.setText(t.getBoxCount());
                    holder.rightBottleCount.setText(t.getBottleCount());
                    holder.rightDate.setText(t.getDate());
                    holder.rightName.setText(t.getWineName());

                    holder.rightCardView.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return turnoverPairList.size();
    }

    public class TurnoverViewHolder extends RecyclerView.ViewHolder {

        TextView leftDate, leftBoxCount, leftName, leftBottleCount;
        TextView rightDate, rightBoxCount, rightName, rightBottleCount;
        CardView leftCardView, rightCardView;

        ImageView leftCalendarIcon, greyCircle, greyLine, leftBoxIcon, leftBottleIcon;
        ImageView rightCalendarIcon, rightBoxIcon, rightBottleIcon;

        public TurnoverViewHolder(View v) {
            super(v);
            leftDate = (TextView) v.findViewById(R.id.leftDate);
            leftBoxCount = (TextView) v.findViewById(R.id.leftBoxCount);
            leftName = (TextView) v.findViewById(R.id.leftName);
            leftBottleCount = (TextView) v.findViewById(R.id.leftBottleCount);

            rightDate = (TextView) v.findViewById(R.id.rightDate);
            rightBoxCount = (TextView) v.findViewById(R.id.rightBoxCount);
            rightName = (TextView) v.findViewById(R.id.rightName);
            rightBottleCount = (TextView) v.findViewById(R.id.rightBottleCount);

            leftCardView = (CardView) v.findViewById(R.id.leftCardView);
            rightCardView = (CardView) v.findViewById(R.id.rightCardView);

            leftCalendarIcon = (ImageView) v.findViewById(R.id.leftCalendarIcon);
            leftCalendarIcon.setBackgroundResource(R.drawable.calendar_icon);

            greyCircle = (ImageView) v.findViewById(R.id.greyCircle);
            greyCircle.setBackgroundResource(R.drawable.circle_grey);

            greyLine = (ImageView) v.findViewById(R.id.greyLine);
            greyLine.setBackgroundResource(R.drawable.vertical_line);

            leftBoxIcon = (ImageView) v.findViewById(R.id.leftBoxIcon);
            leftBoxIcon.setBackgroundResource(R.drawable.boxes_pictogram);

            leftBottleIcon = (ImageView) v.findViewById(R.id.leftBottleIcon);
            leftBottleIcon.setBackgroundResource(R.drawable.shape);

            rightCalendarIcon = (ImageView) v.findViewById(R.id.rightCalendarIcon);
            rightCalendarIcon.setBackgroundResource(R.drawable.calendar_icon);

            rightBoxIcon = (ImageView) v.findViewById(R.id.rightBoxIcon);
            rightBoxIcon.setBackgroundResource(R.drawable.boxes_pictogram);

            rightBottleIcon = (ImageView) v.findViewById(R.id.rightBottleIcon);
            rightBottleIcon.setBackgroundResource(R.drawable.shape);

        }
    }
}

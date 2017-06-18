package com.example.captain.customview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.captain.customview.R;
import com.example.captain.customview.bean.CustomView;
import com.example.captain.customview.widget.DragBubbleView;

import java.util.List;

/**
 * Created by captain on 2017/6/13.
 */

public class CustomViewAdapter extends RecyclerView.Adapter<CustomViewAdapter.ViewHolder> {
    public List<CustomView> data;
    private int layoutId;
    private OnItemClickListener onItemClickListener;

    public CustomViewAdapter(List<CustomView> data, int layoutId) {
        this.data = data;
        this.layoutId = layoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CustomView customView = data.get(position);
        holder.tvName.setText(customView.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(holder.getAdapterPosition(), customView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, CustomView customView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        DragBubbleView dragBubbleView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
//            dragBubbleView = (DragBubbleView) itemView.findViewById(R.id.dragBubbleView);
        }
    }
}

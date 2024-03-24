package com.yimt;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {
    private final List<String> languages;
    private final language activity;

    public LanguageAdapter(language activity) {
        this.activity = activity;
        // 生成50种固定的语言
        languages = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            languages.add("语言 " + i);
        }
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 为每一项创建一个新的视图，并设置其样式以匹配最近使用的语言的样式
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setPadding(16, 8, 8, 8);
        textView.setTextSize(16);
        textView.setCompoundDrawablePadding(8);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_transparent, 0, 0, 0);

        // 为 TextView 设置一个点击事件
        textView.setOnClickListener(activity::toggleCheckmark);

        return new LanguageViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        // 为每一项设置正确的数据
        holder.textView.setText(languages.get(position));
        // 将位置存储在视图的标签中
        holder.textView.setTag(position);
        // 如果这个语言被选中，那么就设置它的 drawable 为打勾的图标，否则设置为透明的图标
        int selectedPosition = activity.getSelectedPosition();
        if (position == selectedPosition) {
            holder.textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_checkmark, 0, 0, 0);
        } else {
            holder.textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_transparent, 0, 0, 0);
        }
    }

    @Override
    public int getItemCount() {
        // 返回语言列表的大小
        return languages.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelectedPosition(Integer position) {
        // 设置被选中的语言的位置
        activity.setSelectedPosition(position);
        // 通知适配器数据已经改变
        notifyDataSetChanged();
    }

    static class LanguageViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public LanguageViewHolder(@NonNull TextView itemView) {
            super(itemView);
            textView = itemView;
        }
    }
}
package com.guoj.roomdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WordAdpter extends RecyclerView.Adapter<WordAdpter.WordViewHolder> {
    List<Word> words=new ArrayList<>();
    WordViewModel wordViewModel;

    public WordAdpter(WordViewModel wordViewModel) {
        this.wordViewModel = wordViewModel;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item2_layout, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  WordAdpter.WordViewHolder holder, int position) {
        Word word = words.get(position);
        holder.aSwitch.setOnCheckedChangeListener(null);//防止乱序
        holder.tv_num.setText(position+1+"");
        holder.tv_english.setText(word.getWord());
        holder.tv_chinese.setText(word.getChinese());
        holder.aSwitch.setChecked(word.isShowMean());
        if (word.isShowMean()){
            holder.tv_chinese.setVisibility(View.VISIBLE);
            word.setShowMean(true);
        }else {
            holder.tv_chinese.setVisibility(View.GONE);
            word.setShowMean(false);
        }

        holder.aSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked){
                holder.tv_chinese.setVisibility(View.VISIBLE);
                word.setShowMean(true);
            }else {
                holder.tv_chinese.setVisibility(View.GONE);
                word.setShowMean(false);
            }
            wordViewModel.updateWords(word);
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder{
        TextView tv_num;
        TextView tv_english;
        TextView tv_chinese;
        Switch aSwitch;
        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_num=itemView.findViewById(R.id.tv_num);
            tv_english=itemView.findViewById(R.id.tv_english);
            tv_chinese=itemView.findViewById(R.id.tv_chinese);
            aSwitch=itemView.findViewById(R.id.switch_word);
        }
    }
}

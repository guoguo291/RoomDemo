package com.guoj.roomdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

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

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item2_layout, parent, false);
        WordViewHolder wordViewHolder=new WordViewHolder(itemView);

        itemView.setOnClickListener(view -> {
            Word word = (Word) wordViewHolder.itemView.getTag(R.id.word_for_item);
            Toast.makeText(parent.getContext(), "id:"+word.getId()+"="+word.getWord(),Toast.LENGTH_SHORT).show();
        });
        wordViewHolder.aSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            Word word = (Word) wordViewHolder.itemView.getTag(R.id.word_for_item);
            if (isChecked){
                wordViewHolder.tv_chinese.setVisibility(View.VISIBLE);
                word.setShowMean(true);
            }else {
                wordViewHolder.tv_chinese.setVisibility(View.GONE);
                word.setShowMean(false);
            }
            wordViewModel.updateWords(word);
        });
        return wordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  WordAdpter.WordViewHolder holder, int position) {
        Word word = words.get(position);
        holder.itemView.setTag(R.id.word_for_item,word);
//        holder.aSwitch.setOnCheckedChangeListener(null);//????????????
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
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder{
        TextView tv_num;
        TextView tv_english;
        TextView tv_chinese;
        SwitchMaterial aSwitch;
        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_num=itemView.findViewById(R.id.tv_num);
            tv_english=itemView.findViewById(R.id.tv_english);
            tv_chinese=itemView.findViewById(R.id.tv_chinese);
            aSwitch=itemView.findViewById(R.id.switch_word);
        }
    }
}

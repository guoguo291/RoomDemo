package com.guoj.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    WordViewModel wordViewModel;
    RecyclerView recyclerView;
    WordAdpter wordAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(WordViewModel.class);
        initView();

    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerViewWord);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        wordAdpter = new WordAdpter(wordViewModel);
        recyclerView.setAdapter(wordAdpter);
        wordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                if (wordAdpter.getItemCount() != words.size()) {
                    wordAdpter.setWords(words);
                    wordAdpter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(wordAdpter.getItemCount() - 1);
                }
            }
        });
        Button button_insert = findViewById(R.id.btn_insert);
        Button button_delete = findViewById(R.id.btn_delete);
        Button button_update = findViewById(R.id.btn_update);
        Button button_deleteAll = findViewById(R.id.btn_clear);
        button_insert.setOnClickListener(view -> {
            Word word1 = new Word("Hello", "你好");
            Word word2 = new Word("Word", "世界");
            wordViewModel.insertWords(word1, word2);
        });
        button_delete.setOnClickListener(view -> {
            Word word = new Word("Hello", "你好啊");
            word.setId(2);
            wordViewModel.deleteWords(word);
        });
        button_deleteAll.setOnClickListener(view -> {
            wordViewModel.deleteAllWords();
        });
        button_update.setOnClickListener(view -> {
            Word word = new Word("Hello", "你好啊");
            word.setId(2);
            wordViewModel.updateWords(word);
        });

    }
}
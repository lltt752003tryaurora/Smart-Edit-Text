package com.example.smartedittext;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextScript;
    private Button buttonCheck;
    private TextView textViewMessage;
    private List<String> badWords = new ArrayList<>();
    private Button buttonAddBadWord;
    private EditText editTextBadWord;
    private TextView textViewBadWordsList;
    private Button buttonDeleteBadWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextScript = findViewById(R.id.editTextScript);
        buttonCheck = findViewById(R.id.buttonCheck);
        textViewMessage = findViewById(R.id.textViewMessage);
        editTextBadWord = findViewById(R.id.editTextBadWord);
        buttonAddBadWord = findViewById(R.id.buttonAddBadWord);
        textViewBadWordsList = findViewById(R.id.textViewBadWordsList);
        buttonDeleteBadWord = findViewById(R.id.buttonDeleteBadWord);


        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String script = editTextScript.getText().toString();
                if (containsBadWord(script)) {
                    textViewMessage.setText("You cannot implement this script. Please remove inappropriate words.");
                    editTextScript.setText(replaceBadWords(script));
                } else {
                    textViewMessage.setText("Script looks good!");
                }
            }
        });

        buttonAddBadWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newBadWord = editTextBadWord.getText().toString().trim();
                if (!newBadWord.isEmpty() && !badWords.contains(newBadWord)) {
                    badWords.add(newBadWord);
                    updateBadWordsList();
                    editTextBadWord.setText(""); // Clear the input
                    Toast.makeText(MainActivity.this, "Bad word added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Word already in the list or empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonDeleteBadWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordToDelete = editTextBadWord.getText().toString().trim();
                if (badWords.contains(wordToDelete)) {
                    badWords.remove(wordToDelete);
                    updateBadWordsList();
                    editTextBadWord.setText(""); // Clear the input
                    Toast.makeText(MainActivity.this, "Bad word deleted successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Word not found in the list!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean containsBadWord(String script) {
        for (String word : badWords) {
            if (script.contains(word)) {
                return true;
            }
        }
        return false;
    }

    private String replaceBadWords(String script) {
        for (String word : badWords) {
            script = script.replaceAll(word, "****");
        }
        return script;
    }

    private void updateBadWordsList() {
        StringBuilder builder = new StringBuilder();
        for (String word : badWords) {
            builder.append(word).append("\n");
        }
        textViewBadWordsList.setText(builder.toString());
    }
}

package com.example.dictionary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, String> dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeDictionary();

        final EditText editText = findViewById(R.id.editText1);
        Button buttonSearch = findViewById(R.id.buttonSearch1);
        final TextView textViewResult = findViewById(R.id.textViewResult);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = editText.getText().toString().toLowerCase();
                String definition = dictionary.get(word);
                if (definition != null) {
                    textViewResult.setText(definition);
                } else {
                    textViewResult.setText("Word not found in the dictionary.");
                }
            }
        });
    }

    private void initializeDictionary() {
        dictionary = new HashMap<>();

        try {
            InputStream inputStream = getAssets().open("dictionary.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Modify the delimiter if necessary
                if (parts.length == 2) {
                    String word = parts[0].trim().toLowerCase();
                    String meaning = parts[1].trim();
                    dictionary.put(word, meaning);
                }
            }

            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.skncoe.androidtextrecognition;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class first extends AppCompatActivity implements TextToSpeech.OnInitListener {

    public String text;
    TextView textView;
    public EditText editText;
    public Button button, btnspeak,save,history,back, btn1;
    TextToSpeech textToSpeech;
    ImageView imageView;
    int res;
    database mdb;

    public void init() {
        button = (Button) findViewById(R.id.copy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(first.this, second.class);
                startActivity(toy);
            }
        });
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        editText = (EditText) findViewById(R.id.editText);
        editText.setTextIsSelectable(true);
        mdb=new database(this);
        textView=(TextView)findViewById(R.id.tw);
        imageView=(ImageView)findViewById(R.id.bg);
        btnspeak = (Button) findViewById(R.id.btnspeak);
        save = (Button) findViewById(R.id.btnAdd);
        history = (Button) findViewById(R.id.btnview);
        back = (Button) findViewById(R.id.back);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(first.this,"No data",Toast.LENGTH_SHORT).show();
                } else {
                    boolean flag = mdb.insertdata(editText.getText().toString());
                    if (flag == true)
                        Toast.makeText(first.this, "Data inserted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(first.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
                btnspeak.setVisibility(View.INVISIBLE);
                save.setVisibility(View.INVISIBLE);
                history.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                editText.setVisibility(View.INVISIBLE);
                back.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.INVISIBLE);
                getdata();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                btnspeak.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                history.setVisibility(View.VISIBLE);
                textView.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.VISIBLE);
                back.setVisibility(View.INVISIBLE);
                btn1.setVisibility(View.VISIBLE);
            }
        });
        Intent intent = getIntent();
        text = getIntent().getStringExtra("text");
        editText.setText(text);
        init();
        textToSpeech = new TextToSpeech(this, this);
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                finish();
                System.exit(0);
            }
        });


    }
    void getdata()
    {
        textView.setText("");
        Cursor c=mdb.getalldata();
        while (c.moveToNext())
        {
            int id=c.getInt(0);
            String s=c.getString(1);
            textView.append(id+" "+ s +"\n");
        }
    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            res = textToSpeech.setLanguage(Locale.US);
        }

    }

    public void onClickSpeak(View view) {
        if (res == TextToSpeech.LANG_NOT_SUPPORTED || res == TextToSpeech.LANG_MISSING_DATA) {
            Toast.makeText(this, "Language not supported", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Speaking", Toast.LENGTH_LONG).show();
            text = editText.getText().toString();
            textToSpeech.setPitch(1);
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}


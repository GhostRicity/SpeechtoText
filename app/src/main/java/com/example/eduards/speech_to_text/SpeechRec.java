
package com.example.eduards.speech_to_text;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;




public class SpeechRec extends AppCompatActivity {


    private DatabaseHelper myDB;
    private Button button;
    private TextView textView;
    private Button buttonView;
    private Button buttonSav;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speach_rrec);

        buttonSav = (Button) findViewById(R.id.buttonSav);
        buttonView = (Button) findViewById(R.id.buttonView);
        myDB = new DatabaseHelper(this);

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.microphone);
        Drawable drawable2 = res.getDrawable(R.drawable.ldb);

        textView = (TextView) this.findViewById(R.id.textView);
        button = (Button) this.findViewById(R.id.button);   /* defining button */

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); /* intent = what i whant */
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "lv_LV"); /* implementing Latvian languege */
                try {
                    startActivityForResult(intent, 200);  /*just a number but hase to be the smae number */
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(), "Intent problem", Toast.LENGTH_SHORT).show();
                }
            }
        });


        buttonSav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   
                String newEntry = textView.getText().toString();
                if(textView.length()!= 0){
                    AddData(newEntry);
                    textView.setText("");
                }else{
                    Toast.makeText(SpeechRec.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
                }
            }
        });




        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpeechRec.this, ViewListContents.class);
                startActivity(intent);
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   /*dispaly string from api to textview */
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                textView.append(" " + result.get(0));

            }
        }
    }

    public void AddData(String newEntery) {

        boolean insertData = myDB.addData(newEntery);

        if(insertData==true){
            Toast.makeText(this, "Veiksmigi!", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "kaut kas nav :(", Toast.LENGTH_LONG).show();
        }
    }


}




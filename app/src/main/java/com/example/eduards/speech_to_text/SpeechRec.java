

package com.example.eduards.speech_to_text;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;



public class SpeechRec extends AppCompatActivity {


    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speach_rrec);

        textView = (TextView) this.findViewById(R.id.textView);
        button = (Button) this.findViewById(R.id.button);   /* defining button */

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); /* intent = what i whant */
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "lv_LV"); /* implementing LAtvian languege */
                try{
                    startActivityForResult(intent,200);  /*just a number but hase to be the smae number */
                }catch (ActivityNotFoundException a){
                    Toast.makeText(getApplicationContext(),"Intent problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   /*dispaly string from api to textview */
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                textView.setText(result.get(0));
            }
        }
    }



}

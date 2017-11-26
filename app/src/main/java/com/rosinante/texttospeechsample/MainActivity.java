package com.rosinante.texttospeechsample;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    @BindView(R.id.edit_text_text_to_speech)
    EditText editTextTextToSpeech;
    @BindView(R.id.button_speakout)
    Button buttonSpeakout;

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        textToSpeech = new TextToSpeech(this, this);
    }

    @OnClick(R.id.button_speakout)
    public void onClick() {
        speakOut();
    }

    private void speakOut() {
        String textresultfromedittext = editTextTextToSpeech.getText().toString();
        textToSpeech.speak(textresultfromedittext, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language isn't supported", Toast.LENGTH_SHORT).show();
            } else {
                buttonSpeakout.setEnabled(true);
                speakOut();
            }
        } else {
            Toast.makeText(this, "Initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}

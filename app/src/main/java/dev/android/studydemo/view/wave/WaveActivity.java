package dev.android.studydemo.view.wave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dev.android.studydemo.R;

public class WaveActivity extends AppCompatActivity {
    WaveView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        wv = findViewById(R.id.wv);
        wv.startAnim();
    }
}

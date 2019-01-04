package dev.android.studydemo.view.wave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dev.android.studydemo.R;
import dev.android.studydemo.view.wave.java.WaveView;
import dev.android.studydemo.view.wave.kt.KtWaveView;

public class WaveActivity extends AppCompatActivity {
    WaveView wv;
    KtWaveView kt_wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        wv = findViewById(R.id.wv);
        kt_wv=findViewById(R.id.kt_wv);
        kt_wv.startAnim();
        wv.startAnim();
    }
}

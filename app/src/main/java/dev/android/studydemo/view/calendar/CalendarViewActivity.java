package dev.android.studydemo.view.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import dev.android.studydemo.R;
import dev.android.studydemo.view.calendar.java.CalendarView;

public class CalendarViewActivity extends AppCompatActivity {
    CalendarView cdv;
    TextView tv_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        cdv = findViewById(R.id.cdv);
        tv_time=findViewById(R.id.tv_time);
        findViewById(R.id.tv_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdv.delMonth();
                tv_time.setText(cdv.getTime());
            }
        });
        findViewById(R.id.tv_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdv.addMonth();
                tv_time.setText(cdv.getTime());
            }
        });
    }
}

package adrienlebret.studio.fr.mystopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimerWatchAct extends AppCompatActivity {

    //TextView tvSplash, tvSubSplash;
    Button btnstart, btnstop;
    Animation roundingalone;
    ImageView icanchor;
    TextView timerHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        btnstart = findViewById(R.id.btnstart);
        btnstop = findViewById(R.id.btnstop);
        icanchor = findViewById(R.id.icanchor);
        timerHere = findViewById(R.id.timerHere);

        // initialize timer duration
        long duration = TimeUnit.MINUTES.toMillis(1);

        // initialize coutdown timer
        new CountDownTimer(duration, 5000) {
            @Override
            public void onTick(long l) {
                // when tick

                // convert millisecond to minute and second
                String sDuration = String.format(Locale.FRANCE,"%02d:%02d"
                        ,TimeUnit.MILLISECONDS.toMinutes(l)
                        ,TimeUnit.MILLISECONDS.toSeconds(l) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                // set converted string on text view
                timerHere.setText(sDuration);

            }

            @Override
            public void onFinish() {
                // when finish
                // hide text view
                timerHere.setVisibility(View.GONE);

                // display toast
                Toast.makeText(getApplicationContext()
                    ,"Coutdown timer has ended", Toast.LENGTH_LONG).show();
            }
        }.start();

        // rotate the canchor
        icanchor.setRotation(33);

        // create optional animation
        btnstop.setAlpha(0);

        // load animations
        roundingalone = AnimationUtils.loadAnimation(this, R.anim.roundingalone );

        // import font
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MMedium.ttf");

        // customize font
        btnstart.setTypeface(MMedium);
        btnstop.setTypeface(MMedium);


        // passing event
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // passing animation
                icanchor.startAnimation(roundingalone);
                btnstop.animate().alpha(1).translationY(-80).setDuration(300).start();
                btnstart.animate().alpha(0).setDuration(300).start();
                // start time
                //timerHere.setBase(SystemClock.elapsedRealtime());
                //timerHere.start();
            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // stop animation
                icanchor.clearAnimation();
                btnstart.animate().alpha(1).translationY(-80).setDuration(300).start();
                btnstop.animate().alpha(0).setDuration(300).start();
                // start time
                //timerHere.stop();
            }
        });
    }
}

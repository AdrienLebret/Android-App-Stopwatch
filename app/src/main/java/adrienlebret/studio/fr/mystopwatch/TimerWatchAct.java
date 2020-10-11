package adrienlebret.studio.fr.mystopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TimerWatchAct extends AppCompatActivity {

    //TextView tvSplash, tvSubSplash;
    Button btnstart, btnstop;
    //Animation roundingalone;
    ImageView icanchor;
    TextView timerHere;
    CountDownTimer countDownTimer;

    ObjectAnimator icanchorAnimation; // animation

    private boolean timerRunning = false;
    private long workTimeLeft = 1500000; // 25 minutes
    //private long workTimeLeft = 10000; // 10 secondes TEST
    private long breakTimeLeft = 300000; // 5 minutes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        btnstart = findViewById(R.id.btnstart);
        btnstop = findViewById(R.id.btnstop);
        icanchor = findViewById(R.id.icanchor);
        timerHere = findViewById(R.id.timerHere);

        // rotate the canchor
        icanchor.setRotation(33);

        // create optional animation
        //btnstop.setAlpha(0);

        // load animations
        //roundingalone = AnimationUtils.loadAnimation(this, R.anim.roundingalone );
        icanchorAnimation = ObjectAnimator.ofFloat(icanchor,
                View.ROTATION, 0.0f, 360.0f);

        icanchorAnimation.setDuration(4000);
        icanchorAnimation.setRepeatCount(Animation.INFINITE);
        icanchorAnimation.setInterpolator(new LinearInterpolator());

        icanchorAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                icanchor.animate()
                        .alpha(0.0f)
                        .setDuration(1000);
            }
        });

        // import font
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MMedium.ttf");

        // customize font
        btnstart.setTypeface(MMedium);
        btnstop.setTypeface(MMedium);


        // passing event
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPause();
            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // stop animation
                icanchorAnimation.end();
                workTimeLeft = 1500000;
                timerRunning = false;
                btnstart.setText("Start Timer");
                countDownTimer.cancel();
                icanchor.setRotation(33);
                //countDownTimer.start();
                updateTimer();
            }
        });
    }

    public void startPause() {
        if(timerRunning){
            pauseTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(workTimeLeft, 1000) {
            @Override
            public void onTick(long l) {
                workTimeLeft = l;
                updateTimer();
            }

            @Override
            public void onFinish() {

                // display notification
                //Toast.makeText(TimerWatchAct.this, "Coutdown timer has ended", Toast.LENGTH_LONG).show();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(TimerWatchAct.this,"My Notification")
                        .setSmallIcon(R.drawable.ic_timer_app_notification_foreground)
                        .setContentTitle("End of the working session")
                        .setContentText("Let's have a little break")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(TimerWatchAct.this);
                managerCompat.notify(1, builder.build());

                // IDEA : PUT A SNOOZE BUTTON
                    // https://developer.android.com/training/notify-user/build-notification#Actions


            }
        }.start();

        btnstart.setText("Pause Timer");

        if (workTimeLeft == 1500000 ){
            icanchorAnimation.start();
        } else if (!timerRunning) {
            icanchorAnimation.resume();
        }

        timerRunning = true;
    }

    public void updateTimer() {
        int minutes = (int) workTimeLeft / 60000;
        int seconds = (int) workTimeLeft % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText +=  ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        timerHere.setText(timeLeftText);
    }

    public void pauseTimer() {
        countDownTimer.cancel();
        btnstart.setText("Start Timer");
        timerRunning = false;
        icanchorAnimation.pause();
    }
}

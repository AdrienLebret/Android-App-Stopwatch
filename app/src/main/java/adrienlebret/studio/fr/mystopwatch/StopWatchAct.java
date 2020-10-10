package adrienlebret.studio.fr.mystopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

public class StopWatchAct extends AppCompatActivity {

    //TextView tvSplash, tvSubSplash;
    Button btnstart, btnstop;
    Animation roundingalone;
    ImageView icanchor;
    Chronometer timerHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);

        btnstart = findViewById(R.id.btnstart);
        btnstop = findViewById(R.id.btnstop);
        icanchor = findViewById(R.id.icanchor);
        timerHere = findViewById(R.id.timerHere);

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
                timerHere.setBase(SystemClock.elapsedRealtime());
                timerHere.start();
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
                timerHere.stop();
            }
        });
    }
}

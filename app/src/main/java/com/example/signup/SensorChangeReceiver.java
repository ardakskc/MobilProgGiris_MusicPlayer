package com.example.signup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;


public class SensorChangeReceiver extends BroadcastReceiver {
    private AudioManager audioManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        Context c =context.getApplicationContext();
        audioManager = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        if ("com.example.sensor".equals(intent.getAction())) {
            String change = intent.getStringExtra("player");
            if(change.equals("on")){
                audioManager.adjustVolume(AudioManager.ADJUST_UNMUTE,AudioManager.FLAG_PLAY_SOUND);
            }else{
                audioManager.adjustVolume(AudioManager.ADJUST_MUTE,AudioManager.FLAG_PLAY_SOUND);
            }
        }

    }
}

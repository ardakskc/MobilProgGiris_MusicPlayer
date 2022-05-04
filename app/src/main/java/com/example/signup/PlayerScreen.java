package com.example.signup;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;

public class PlayerScreen extends AppCompatActivity {

    protected MediaPlayer music;
    protected AudioFile playing_music;
    protected Button exit;
    protected SeekBar seekBar;
    protected Handler hand;
    protected Runnable runnable;
    protected TextView t_title;
    protected TextView t_artist;
    protected TextView start_time,stop_time;
    protected ArrayList<AudioFile> list;
    protected ImageButton play_but;
    protected ImageView kapak;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_screen);

        kapak = (ImageView) findViewById(R.id.kapak);
        play_but = (ImageButton)findViewById(R.id.play);
        start_time = (TextView)findViewById(R.id.start_time);
        stop_time = (TextView)findViewById(R.id.stop_time);
        music = MediaPlayerAccess.getInstance();
        t_artist = (TextView)findViewById(R.id.sanatci);
        t_title = (TextView)findViewById(R.id.baslik);
        exit = (Button) findViewById(R.id.exit);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        list = (ArrayList<AudioFile>)getIntent().getSerializableExtra("list");


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicstop(view);
                finish();

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override //Seekba ayarları
            public void onProgressChanged(SeekBar seekBar, int i, boolean bool) {
                if(bool){
                    if(music.isPlaying()){
                        music.pause();
                        music.seekTo(i);
                        music.start();
                    }else{
                        music.seekTo(i);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        hand = new Handler();
        runnable= new Runnable() {
            @Override
            public void run() {
                if (music.isPlaying()){//Müzik çalarken anlık değişimler için.
                    seekBar.setProgress((int) music.getCurrentPosition());
                    long dur = music.getCurrentPosition();
                    int mns = (int) ((dur / 60000) % 60000);
                    int scs = (int) (dur % 60000 / 1000);
                    String time = String.format("%02d:%02d",mns, scs);
                    start_time.setText(time);
                }
                hand.postDelayed(runnable,500);
            }
        };
        hand.post(runnable);

        MetaDataFetch();
        if (!music.isPlaying()){
            musicstart(this.getCurrentFocus());
        }else{
            seekBar.setMax((int) music.getDuration());
            seekBar.setProgress(music.getCurrentPosition());
        }

    }

    public void MetaDataFetch() {//Metadata çekilir.
        playing_music = list.get(MediaPlayerAccess.currentIndex);
        t_artist.setText(playing_music.getArtist());
        t_title.setText(playing_music.getName());
        Uri kapakUri = Uri.parse("content://media/external/audio/media/"+playing_music.getID()+"/albumart");
        if(kapakUri!=null){
            kapak.setImageURI(kapakUri);
        }
        long dur = playing_music.getDur();
        int mns = (int) ((dur / 60000) % 60000);
        int scs = (int) (dur % 60000 / 1000);

        String time = String.format("%02d:%02d",mns, scs);
        stop_time.setText(time);

    }


    public void musicstart(View v){
        music.reset();
        try {
            music.setDataSource(playing_music.getpath());
            music.prepare();
            music.start();
            seekBar.setMax((int) music.getDuration());
            seekBar.setProgress(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void musicplay(View v){
        if(music.isPlaying()){
            music.pause();
            play_but.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        }else{
            music.start();
            play_but.setImageResource(R.drawable.ic_baseline_pause_24);
        }
    }
    public void musicstop(View v){
        if(music.isPlaying()) {
            music.stop();
            play_but.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            try {
                music.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void musicskip(View v){
        int index = MediaPlayerAccess.currentIndex;
        if(index== list.size()-1)
            return;
        MediaPlayerAccess.currentIndex =MediaPlayerAccess.currentIndex+1;
        music.reset();
        MetaDataFetch();
        musicstart(v);
    }
    public void musicprev(View v){
        int index = MediaPlayerAccess.currentIndex;
        if(index== 0)
            return;
        MediaPlayerAccess.currentIndex =MediaPlayerAccess.currentIndex-1;
        music.reset();
        MetaDataFetch();
        musicstart(v);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_menu_24);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item1:
                if(music.isPlaying()){
                    music.stop();
                    try {
                        music.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                this.finish();
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                return true;
            case R.id.item2:
                if(music.isPlaying()){
                    music.stop();
                    try {
                        music.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                this.finish();
                Intent i2 = new Intent(this,DisplaySignUpActivity.class);
                startActivity(i2);
                return true;
            case R.id.item3:
                this.finish();
                Intent i3 = new Intent(this,Music_List.class);
                startActivity(i3);
                return true;
            case R.id.item4:
                Toast.makeText(this,"Already in player screen.",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
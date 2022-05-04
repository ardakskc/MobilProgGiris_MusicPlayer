package com.example.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Music_List extends AppCompatActivity {

    protected MediaPlayer msc;
    protected Button exit2;
    protected RecyclerView recyclerView;
    protected TextView noSong,textView11,textView12,textView13;
    protected ArrayList<AudioFile> list = new ArrayList<>();
    protected ImageButton play_pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        play_pause = (ImageButton)findViewById(R.id.imageButton_list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        noSong = (TextView) findViewById(R.id.nosong);
        textView11 = (TextView) findViewById(R.id.textView11);
        textView12 = (TextView) findViewById(R.id.textView12);
        textView13 = (TextView) findViewById(R.id.textView13);
        msc = MediaPlayerAccess.getInstance();
        exit2 = (Button) findViewById(R.id.exit_2);
        //Meta data çektim.
        String[] inf = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION};
        String selection = MediaStore.Audio.Media.IS_MUSIC +" != 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,inf,selection,null,sortOrder);
        while(cursor.moveToNext()){
            AudioFile metadata = new AudioFile(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getLong(4));
            if(new File(metadata.getpath()).exists())
                list.add(metadata);
        }
        if(list.size()==0){
            recyclerView.setVisibility(View.GONE);
            noSong.setVisibility(View.VISIBLE);
        }else{
            //recyclerview
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MyListAdapter(getApplicationContext(),list));
        }

        exit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(msc.isPlaying()) {
                    msc.stop();
                    try {
                        msc.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                finish();

            }
        });

        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(msc.isPlaying()){
                    msc.pause();
                    play_pause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                }else{
                    msc.start();
                    PlayingFetch();
                    play_pause.setImageResource(R.drawable.ic_baseline_pause_24);
                }
            }
        });
        PlayingFetch();
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
                if(msc.isPlaying()){
                    msc.stop();
                    try {
                        msc.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                this.finish();
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                return true;
            case R.id.item2:
                if(msc.isPlaying()){
                    msc.stop();
                    try {
                        msc.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                this.finish();
                Intent i2 = new Intent(this,DisplaySignUpActivity.class);
                startActivity(i2);
                return true;
            case R.id.item3:
                Toast.makeText(getApplicationContext(), "Music List already shown.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item4:
                if(msc.isPlaying()){
                    Intent intent = new Intent(this,PlayerScreen.class);//Player ekranına geçiliyor.
                    intent.putExtra("list",list);//İntenle beraber müzik listesini gönderdik.
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Music is not playing.", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void PlayingFetch(){//Çalan müzik bilgisi
        if (!msc.isPlaying()){
            textView11.setText("No Title");
            textView12.setText("No Artist");
            textView13.setVisibility(View.GONE);
        }else{
            AudioFile temp = list.get(MediaPlayerAccess.currentIndex);
            textView11.setText(temp.getName());
            textView12.setText(temp.getArtist());
            textView13.setVisibility(View.VISIBLE);//Now Playing
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        PlayingFetch();
        if(recyclerView!=null){
            recyclerView.setAdapter(new MyListAdapter(getApplicationContext(),list));//Seçilen müziğinin renginin değişmesi için
        }
        if(msc.isPlaying()){
            play_pause.setImageResource(R.drawable.ic_baseline_pause_24);
        }else{
            play_pause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        }
    }

    public void msc_player(View V){//Kapak resmine basılınca player açılır.
        if(msc.isPlaying()){
            Intent intent = new Intent(this,PlayerScreen.class);//Player ekranına geçiliyor.
            intent.putExtra("list",list);//İntenle beraber müzik listesini gönderdik.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}
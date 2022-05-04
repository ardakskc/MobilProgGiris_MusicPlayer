package com.example.signup;

import android.media.MediaPlayer;

public class MediaPlayerAccess {//MediaPlayer objesinin hem listede hem playerda kullanılabilmesi için bir classta tuttum.
    public static MediaPlayer instance;
    public static int currentIndex = -1;//Müziğe tıklanmamış.

    public static MediaPlayer getInstance(){
        if(instance == null){
            instance = new MediaPlayer();
        }
        return instance;
    }
}

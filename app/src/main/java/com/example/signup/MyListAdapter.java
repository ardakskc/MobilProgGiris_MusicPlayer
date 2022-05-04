package com.example.signup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private Context context;
    private ArrayList<AudioFile> list;

    public MyListAdapter(Context context, ArrayList<AudioFile> list) {
        this.context=context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        AudioFile metadata = list.get(position);
        holder.i̇mageView.setImageResource(R.drawable.mscphoto);
        holder.title.setText(metadata.getName());
        holder.artist.setText(metadata.getArtist());
        holder.share.setImageResource(R.drawable.ic_baseline_share_24);
        holder.delete.setImageResource(R.drawable.ic_baseline_delete_24);
        long dur = metadata.getDur();
        int mns = (int) ((dur / 60000) % 60000);
        int scs = (int) (dur % 60000 / 1000);

        String time = String.format("%02d:%02d",mns, scs);
        holder.dur.setText(time);//DURATİON CONVERT


        if(MediaPlayerAccess.currentIndex==position){
            holder.title.setTextColor(Color.parseColor("#FFBB86FC"));
            holder.artist.setTextColor(Color.parseColor("#FFBB86FC"));
        }else{
            holder.title.setTextColor(Color.parseColor("#000000"));
            holder.artist.setTextColor(Color.parseColor("#000000"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MediaPlayerAccess.getInstance().reset();//Müziği resetle
                MediaPlayerAccess.currentIndex = holder.getAdapterPosition();//Müziğe tıklandı
                Intent intent = new Intent(context,PlayerScreen.class);//Player ekranına geçiliyor.
                intent.putExtra("list",list);//İntentle beraber müzik listesini gönderdik.
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {//Müzik paylaşma
            @Override
            public void onClick(View view) {
                AudioFile temp = list.get(holder.getAdapterPosition());

                String sharePath = temp.getpath();
                Uri uri = Uri.parse(sharePath);
                Intent share_music = new Intent(Intent.ACTION_SEND);
                share_music.setType("audio/*");
                share_music.putExtra(Intent.EXTRA_STREAM, uri);

                Intent chooserIntent = Intent.createChooser(share_music, "Share sound with:");
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(chooserIntent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {//Müzik silme
            @Override
            public void onClick(View view) {
                AudioFile temp = list.get(holder.getAdapterPosition());
                File file = new File(temp.getpath());
                if (file.exists()){
                    file.delete();
                    Toast.makeText(context,"File deleted.",Toast.LENGTH_SHORT).show();
                    list.remove(temp);
                    notifyItemRemoved(holder.getAdapterPosition());
                }else{
                    Toast.makeText(context,"Error occured.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView i̇mageView;
        public TextView title;
        public TextView artist;
        public TextView dur;
        public ImageButton share;
        public ImageButton delete;
        public ViewHolder(View itemView) {
            super(itemView);
            i̇mageView = (ImageView) itemView.findViewById(R.id.icon_mus);
            title = (TextView) itemView.findViewById(R.id.mus_tit);
            artist = (TextView) itemView.findViewById(R.id.mus_art);
            dur = (TextView) itemView.findViewById(R.id.mus_dur);
            share = (ImageButton) itemView.findViewById(R.id.share);
            delete = (ImageButton) itemView.findViewById(R.id.delete);
        }

    }
}

package com.example.quinielarandom;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayerUtil {
    private static MediaPlayer mediaPlayer;

    public static void iniciarMusica(Context context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.opiodei);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public static boolean estaSonando(){
        if (mediaPlayer.isPlaying()) {
            MusicPlayerUtil.detenerMusica(); // Pausar la música si está reproduciéndose
            return true;
        } else {
            MusicPlayerUtil.resumirMusica(); // Reanudar la reproducción si está pausada
            return false;
        }
    }

    public static void resumirMusica(){
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public static void detenerMusica() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public static void liberarRecursos() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}


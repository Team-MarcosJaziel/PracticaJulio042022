package com.marcowerjr.practicajulio042022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String channelID = "4";
    String channelName = "Canal 4";
    String descriptionChannel = "Descripción del canal de notificaciones";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel(channelID, channelName, descriptionChannel);
    }


    private void createNotificationChannel(String channelID, String channelName, String descriptionChannel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = channelName;
            String description = descriptionChannel;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void newNotification(View view) {
        Intent webPage = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.utch.edu.mx"));

        Intent maps = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:28.6428875,-106.1475117?q=Utch"));

        PendingIntent pendingIntentWebPage;
        PendingIntent pendingIntentMaps;
        if (Build.VERSION.SDK_INT >= 31) {
            pendingIntentWebPage = PendingIntent.getActivity(this, 0, webPage, PendingIntent.FLAG_IMMUTABLE);
            pendingIntentMaps = PendingIntent.getActivity(this, 0, maps, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntentWebPage = PendingIntent.getActivity(this, 0, webPage, 0);
            pendingIntentMaps = PendingIntent.getActivity(this, 0, maps, 0);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.drawable.ic_school_round)
                .setContentTitle("Universidad Tecnológica de Chihuahua")
                .setContentText("Práctica del 4 de julio del 2022")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntentWebPage)
                .setContentIntent(pendingIntentMaps)
                .addAction(R.drawable.ic_school_round, "Página Web de la UTCH", pendingIntentWebPage)
                .addAction(R.drawable.ic_school_round, "Mapa de la UTCH", pendingIntentMaps);

        int id = 1;

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(id, builder.build());
    }

}
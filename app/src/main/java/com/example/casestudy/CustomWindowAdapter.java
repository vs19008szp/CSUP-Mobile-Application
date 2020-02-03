package com.example.casestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View windowInfo;
    private Context contextInfo;

    public CustomWindowAdapter(Context contextInfo) {
        this.contextInfo = contextInfo;
        windowInfo = LayoutInflater.from(contextInfo).inflate(R.layout.activity_custom_window_adapter, null);
    }

    private void postaviText(Marker marker, View view){

        String title = marker.getTitle();
        TextView ime = (TextView) view.findViewById(R.id.titlee);

        if (!title.equals("")){
            ime.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView opis = (TextView) view.findViewById(R.id.snippett);

        if (!snippet.equals("")){
            opis.setText(snippet);
        }

    }

    @Override
    public View getInfoWindow(Marker marker) {
        postaviText(marker, windowInfo);
        return windowInfo;
    }

    @Override
    public View getInfoContents(Marker marker) {
        postaviText(marker, windowInfo);
        return windowInfo;
    }
}

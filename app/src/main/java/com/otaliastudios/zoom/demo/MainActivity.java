package com.otaliastudios.zoom.demo;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import com.otaliastudios.zoom.Alignment;
import com.otaliastudios.zoom.ZoomImageView;
import com.otaliastudios.zoom.ZoomLayout;
import com.otaliastudios.zoom.ZoomLogger;
import com.otaliastudios.zoom.ZoomSurfaceView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ArrayList<Integer> drawableList;
    int currentDrawableIndex;

    ZoomImageView zoomImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ZoomLogger.setLogLevel(ZoomLogger.LEVEL_VERBOSE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zoomImage = findViewById(R.id.zoom_image);
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(this);

        final Button centerButton = findViewById(R.id.centerPanButton);
        centerButton.setOnClickListener(centerImage);

        createDrawableList();
        currentDrawableIndex = 0;
    }

    @Override
    public void onClick(View view) {
        zoomImage.getEngine().clear();
        zoomImage.setVisibility(View.VISIBLE);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                drawableList.get(currentDrawableIndex));

        int x = bitmap.getWidth();
        int y = bitmap.getHeight();

        bitmap.getWidth();

        zoomImage.setImageBitmap(bitmap);
        zoomImage.zoomTo(1, false);

        if(currentDrawableIndex < drawableList.size() - 2){
            currentDrawableIndex++;
        } else{
            currentDrawableIndex = 0;
        }
    }

    private void createDrawableList(){
        drawableList = new ArrayList<>();

        drawableList.add(R.drawable.test_crop_100_x_50);
        drawableList.add(R.drawable.test_crop_100_x_100);
        drawableList.add(R.drawable.test_crop_100_x_200);
        drawableList.add(R.drawable.test_crop_1440_x_300);
        drawableList.add(R.drawable.test_crop_1440_x_1440);
    }

    private View.OnClickListener centerImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            float x  = zoomImage.getEngine().getContentWidth();
            float y  = zoomImage.getEngine().getContentHeight();
            float z  = zoomImage.getZoom();
            float zr  = zoomImage.getRealZoom();
            int lz = zoomImage.getLeft();
            int rz = zoomImage.getRight();

            zoomImage.getEngine().setAlignment(Alignment.RIGHT);


            float xE = zoomImage.getEngine().getPanX();
            float yE = zoomImage.getEngine().getPanY();

            float xS = zoomImage.getPanX();
            float yS = zoomImage.getPanX();


            if(x > y){
                x = x;
                y = 0;
            }else{
                x = 0;
                y = y;
            }

            zoomImage.getEngine().panTo(-x/2, -y/2, false);

            if(x > y){
                x = -x/4;
                y = 0;
            }else{
                x = 0;
                y = -y/4;
            }

            zoomImage.moveTo(1, x, y, false);
        }
    };
}

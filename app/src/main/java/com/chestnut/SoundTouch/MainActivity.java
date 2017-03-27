package com.chestnut.SoundTouch;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ndktest.chestnut.huiyu.com.ndktest.R;

public class MainActivity extends AppCompatActivity {

    private Button button = null;
    private SoundTouch soundTouch = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        soundTouch = new SoundTouch();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundTouch.setPitchSemiTones(1)
                        .setPitchSemiTones(2)
                        .setSpeed(10)
                        .processFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/hehe.wav",
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/hehexxx.wav",
                                new SoundTouch.CallBack() {
                                    @Override
                                    public void onStart(String inputFile, String outputFile) {
                                        Log.e("SoundTouch-onStart","input:"+inputFile+",output:"+outputFile);
                                    }

                                    @Override
                                    public void onSuccess(String inputFile, String outputFile) {

                                    }

                                    @Override
                                    public void onFail(String inputFile, String outputFile, int errorCode) {

                                    }
                                });
            }
        });
    }

}

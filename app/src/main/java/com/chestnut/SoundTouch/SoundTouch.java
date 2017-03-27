package com.chestnut.SoundTouch;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SoundTouch
{
    // Native interface function that returns SoundTouch version string.
    // This invokes the native c++ routine defined in "soundtouch-jni.cpp".
    public native final static String getVersionString();
    
    private native final void setTempo(long handle, float tempo);

    private native final void setPitchSemiTones(long handle, float pitch);
    
    private native final void setSpeed(long handle, float speed);

    private native final int processFile(long handle, String inputFile, String outputFile);

    public native final static String getErrorString();

    private native final static long newInstance();
    
    private native final void deleteInstance(long handle);
    
    long handle = 0;
    private ExecutorService singleThreadExecutor = null;
    
    public SoundTouch()
    {
    	handle = newInstance();
        singleThreadExecutor = Executors.newSingleThreadExecutor();
    }
    
    
    public void close()
    {
    	deleteInstance(handle);
    	handle = 0;
        if (singleThreadExecutor!=null)
            singleThreadExecutor.shutdown();
    }


    public SoundTouch setTempo(float tempo)
    {
    	setTempo(handle, tempo);
        return this;
    }


    public SoundTouch setPitchSemiTones(float pitch)
    {
    	setPitchSemiTones(handle, pitch);
        return this;
    }

    
    public SoundTouch setSpeed(float speed)
    {
    	setSpeed(handle, speed);
        return this;
    }


    public int processFile(String inputFile, String outputFile)
    {
    	return processFile(handle, inputFile, outputFile);
    }

    // Load the native library upon startup
    static
    {
        System.loadLibrary("SoundTouchUtils");
    }

    //Interface
    public interface CallBack {
        void onStart(String inputFile, String outputFile);
        void onSuccess(String inputFile, String outputFile);
        void onFail(String inputFile, String outputFile, int errorCode);
    }

    public void processFile(final String inputFile, final String outputFile, final CallBack callBack) {
        callBack.onStart(inputFile,outputFile);
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int result = processFile(handle, inputFile, outputFile);
                Log.e("SoundTouch:changeVoice:",result+"");
                if (result==0)
                    callBack.onSuccess(inputFile,outputFile);
                else
                    callBack.onFail(inputFile,outputFile,result);
            }
        });
    }
}

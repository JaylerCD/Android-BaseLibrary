package com.commonutils.media.audio.play;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @描述：     @音频文件左右声道播放
 * @作者：     @Bin
 * @创建时间： @2018/12/17 19:07
 *
 * https://github.com/kongqw/AndroidSoundManagerDemo
 * http://kongqw.com/2016/08/26/2016-08-26-Android%E5%B7%A6%E5%8F%B3%E5%A3%B0%E9%81%93%E7%9A%84%E6%8E%A7%E5%88%B6/
 */
final class AudioTrackThread extends Thread {

    // 采样率
    private int mSampleRateInHz = 16000;
    // 单声道
    private int mChannelConfig = AudioFormat.CHANNEL_OUT_MONO;
    // 双声道（立体声）
    // private int mChannelConfig = AudioFormat.CHANNEL_OUT_STEREO;

    private static final String TAG = "AudioTrackThread";

    private AudioTrack mAudioTrack;
    private byte[] data;

    private InputStream mInputStream;

    public AudioTrackThread(InputStream inputStream) {

        this.mInputStream = inputStream;
        int bufferSize = AudioTrack.getMinBufferSize(mSampleRateInHz, mChannelConfig, AudioFormat.ENCODING_PCM_16BIT);

        mAudioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                mSampleRateInHz,
                mChannelConfig,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize,
                AudioTrack.MODE_STREAM);
    }

    @Override
    public void run() {
        super.run();
        try {
            if (null != mAudioTrack)
                mAudioTrack.play();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


            // 缓冲区
            byte[] buffer = new byte[1024];
            // 播放进度
            int playIndex = 0;
            // 是否缓冲完成
            boolean isLoaded = false;
            // 缓冲 + 播放
            while (null != mAudioTrack && AudioTrack.PLAYSTATE_STOPPED != mAudioTrack.getPlayState()) {
                // 字符长度
                int len;
                if (-1 != (len = mInputStream.read(buffer))) {
                    byteArrayOutputStream.write(buffer, 0, len);
                    data = byteArrayOutputStream.toByteArray();
                    Log.i(TAG, "run: 已缓冲 : " + data.length);
                } else {
                    // 缓冲完成
                    isLoaded = true;
                }

                if (AudioTrack.PLAYSTATE_PAUSED == mAudioTrack.getPlayState()) {
                    // TODO 已经暂停
                }
                if (AudioTrack.PLAYSTATE_PLAYING == mAudioTrack.getPlayState()) {
                    Log.i(TAG, "run: 开始从 " + playIndex + " 播放");
                    playIndex += mAudioTrack.write(data, playIndex, data.length - playIndex);
                    Log.i(TAG, "run: 播放到了 : " + playIndex);
                    if (isLoaded && playIndex == data.length) {
                        Log.i(TAG, "run: 播放完了");
                        mAudioTrack.stop();
                    }

                    if (playIndex < 0) {
                        Log.i(TAG, "run: 播放出错");
                        mAudioTrack.stop();
                        break;
                    }
                }
            }
            Log.i(TAG, "run: play end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置播放文件的InputStream
     * @param inputStream
     */
    public void setInputStream(InputStream inputStream){
        this.mInputStream = inputStream;
    }

    /**
     * 设置左右声道平衡
     *
     * @param max     最大值
     * @param balance 当前值
     */
    public void setBalance(int max, int balance) {
        float b = (float) balance / (float) max;
        Log.i(TAG, "setBalance: b = " + b);
        if (null != mAudioTrack)
            mAudioTrack.setStereoVolume(1 - b, b);
    }

    /**
     * 设置左右声道是否可用
     *
     * @param left  左声道
     * @param right 右声道
     */
    public void setChannel(boolean left, boolean right) {
        if (null != mAudioTrack) {
            mAudioTrack.setStereoVolume(left ? 1 : 0, right ? 1 : 0);
            mAudioTrack.play();
        }
    }

    public void pause() {
        if (null != mAudioTrack)
            mAudioTrack.pause();
    }

    public void play() {
        if (null != mAudioTrack)
            mAudioTrack.play();
    }

    public void stopp() {
        releaseAudioTrack();
    }

    private void releaseAudioTrack() {
        if (null != mAudioTrack) {
            mAudioTrack.stop();
            mAudioTrack.release();
            mAudioTrack = null;
        }
    }
}
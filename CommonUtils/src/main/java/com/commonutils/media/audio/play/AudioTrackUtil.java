package com.commonutils.media.audio.play;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @描述：     @音频文件左右声道播放，主要用来播放已经存在的音频文件
 * @作者：     @Bin
 * @创建时间： @2018/12/17 17:58
 */
public final class AudioTrackUtil {

    private AudioTrackThread mAudioTrackThread;
    private AudioTrackThread mChannelLeftPlayer;
    private AudioTrackThread mChannelRightPlayer;

    /*****************单利实现**************************/
    private static volatile  AudioTrackUtil instance = null;

    private AudioTrackUtil(){

    }

    public static synchronized AudioTrackUtil getInstance(){
        if (null == instance){
            synchronized (AudioTrackUtil.class){
                if (null == instance){
                    instance = new AudioTrackUtil();
                }
            }
        }
        return instance;
    }

    /*****************播放功能**************************/

    /**
     * 开始播放
     *
     * @param fileName 文件绝对路径
     */
    public void start(String fileName){
        try{
            if (null != mAudioTrackThread) {
                mAudioTrackThread.stopp();
                mAudioTrackThread = null;
            }
            File initialFile = new File(fileName);
            mAudioTrackThread = new AudioTrackThread(new DataInputStream(new FileInputStream(initialFile)));
            mAudioTrackThread.start();
        } catch (FileNotFoundException e){
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 暂停播放
     */
    public void pause(){
        if (null != mAudioTrackThread) {
            mAudioTrackThread.pause();
        }
    }

    /**
     * 继续播放
     */
    public void continuePlay(){
        if (null != mAudioTrackThread) {
            mAudioTrackThread.play();
        }
    }

    /**
     * 停止播放
     */
    public void stop(){
        if (null != mAudioTrackThread) {
            mAudioTrackThread.stopp();
            mAudioTrackThread = null;
        }
    }

    /**
     * 禁用左声道
     */
    public void disableChannelLeft(){
        if (null != mAudioTrackThread){
            mAudioTrackThread.setChannel(false, true);
        }
    }


    /**
     * 禁用右声道
     */
    public void disableChannelRight(){
        if (null != mAudioTrackThread){
            mAudioTrackThread.setChannel(true, false);
        }
    }


    /**
     * 恢复双声道
     */
    public void restoreDualChannels(){
        if (null != mAudioTrackThread){
            mAudioTrackThread.setChannel(true, true);
        }
    }

    /**
     * 左右声道播放不同的数据
     * @param inputStreamLeft
     * @param inputStreamRight
     */
    public void playDifferentFiles(InputStream inputStreamLeft,InputStream inputStreamRight){
        if (null != mChannelLeftPlayer) {
            mChannelLeftPlayer.stopp();
            mChannelLeftPlayer = null;
        }
        if (null != mChannelRightPlayer) {
            mChannelRightPlayer.stopp();
            mChannelRightPlayer = null;
        }

        mChannelLeftPlayer = new AudioTrackThread(inputStreamLeft);
        mChannelRightPlayer = new AudioTrackThread(inputStreamRight);

        mChannelLeftPlayer.setChannel(true, false);
        mChannelRightPlayer.setChannel(false, true);

        mChannelLeftPlayer.start();
        mChannelRightPlayer.start();
    }

    /**
     * 设置左右声道平衡
     * @param max
     * @param balance
     */
    public void  setBalance(int max,int balance){
        if (null != mAudioTrackThread){
            mAudioTrackThread.setBalance(max,balance);
        }
    }
}
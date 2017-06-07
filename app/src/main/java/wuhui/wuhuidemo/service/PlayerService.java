package wuhui.wuhuidemo.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wuhui.wuhuidemo.ac.ActivityPlayer;
import wuhui.wuhuidemo.entity.MusicBean;

public class PlayerService extends Service {
    private static final String TAG = "PlayerService";
    public static final int MSG_PERPREA = 0;
    /**
     * 传递messenger
     */
    public static final int MSG_MESSENGER = 0x10;
    /**
     * 播放暂停
     */
    public static final int MSG_PLAY = 1;
    /**
     * 多动播放
     */
    public static final int MSG_SEEKPLAY = 2;
    /**
     * 停止
     */
    public static final int MSG_STOP = 3;
    private List<MusicBean> musics = new ArrayList<>();
    private MediaPlayer mp;
    private Messenger clientMessenger;
    private Handler timeHandler = new Handler();
    private TimeRunnable timeRunnable = new TimeRunnable();
    private HandlerThread thread;
    /**
     * 当前播放的歌曲位置
     */
    private int currentPosition = -1;
    private volatile boolean isRunning = true;
    private Handler serviceHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            try {
                switch (msg.what) {
                    case MSG_MESSENGER:
                        clientMessenger = msg.replyTo;
                        break;
                    case MSG_PERPREA:
                        if (!musics.contains(msg.obj)) {
                            musics.add((MusicBean) msg.obj);
                        }
                        break;
                    case MSG_PLAY:
                        if (currentPosition != msg.arg1) {
                            _initMediaPlayer(msg.arg1);
                            doPlay();
                        } else {
                            doPause();
                        }
                        break;
                    case MSG_SEEKPLAY:
                        stopCount();
                        mp.seekTo(msg.arg1);
                        break;
                    case MSG_STOP:
                        stopCount();
                        mp.stop();
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
    private Messenger messenger = new Messenger(serviceHandler);

    private void _initMediaPlayer(int arg1) throws IOException, RemoteException {
        Log.e(TAG, "当前播放的位置:" + arg1);
        currentPosition = arg1;
        resetMp(currentPosition);
        Message message = Message.obtain();
        message.arg1 = mp.getDuration();
        message.what = ActivityPlayer.MSG_TOTALTIME;
        clientMessenger.send(message);
        //拖拉完成
        mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                startCount();
                mp.start();
            }
        });
        //当前歌曲播放结束
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try {
                    if (currentPosition == musics.size() - 1) {
                        Log.e(TAG, "列表已经播放结束了");
                    } else {
                        currentPosition++;
                        _initMediaPlayer(currentPosition);
                        mp.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 播放下一曲
     */
    private void doPlay() throws IOException, RemoteException {
        mp.start();
        startCount();
    }

    /**
     * 改变当前播放歌曲的状态
     */
    private void doPause() throws InterruptedException {
        if (mp.isPlaying()) {
            stopCount();
            mp.pause();
        } else {
            startCount();
            mp.start();
        }
    }


    /**
     * @param arg1 当前播放的歌曲的位置
     * @throws IOException
     */
    private void resetMp(int arg1) throws IOException {
        if (mp == null) {
            mp = new MediaPlayer();
        }
        mp.reset();
        mp.setDataSource(musics.get(arg1).getMusicPath());
        mp.prepare();
    }

    /**
     * 开始计时
     */
    private void startCount() {
        thread = new HandlerThread("TimeThread");
        thread.start();
        timeHandler = new Handler(thread.getLooper());
        timeHandler.post(timeRunnable);
        isRunning = true;
    }

    /**
     * 结束计时
     */
    private void stopCount() {
        thread.quit();
        isRunning = false;
        timeHandler.removeCallbacks(timeRunnable);
        thread = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopCount();
        if (mp != null) {
            mp.release();
        }
    }

    class TimeRunnable implements Runnable {
        @Override
        public void run() {
            while (isRunning) {
                Message timemessage = Message.obtain();
                timemessage.what = ActivityPlayer.MSG_CURRENTTIME;
                timemessage.arg1 = mp.getCurrentPosition();
                try {
                    clientMessenger.send(timemessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

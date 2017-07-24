package wuhui.wuhuidemo.ac;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import wuhui.library.WuHuiTest;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.adapter.MusicAapter;
import wuhui.wuhuidemo.annotation.TestAnnotation;
import wuhui.wuhuidemo.entity.MusicBean;
import wuhui.wuhuidemo.service.PlayerService;

/**
 * Created by wuhui on 2017/4/12.
 */

public class ActivityPlayer extends BaseActivity implements MusicAapter.ItemClickListenser {
    private static final String TAG = "ActivityPlayer";
    /**
     * 歌曲总时间标示
     */
    public static final int MSG_TOTALTIME = 0x10;
    /**
     * 歌曲当前播放时间标示
     */
    public static final int MSG_CURRENTTIME = 0x11;
    @BindView(R.id.getmusic)
    TextView getmusic;
    @BindView(R.id.musiclist)
    RecyclerView musiclist;
    @BindView(R.id.musicProgressBar)
    SeekBar musicProgressBar;
    @TestAnnotation(target = "ActivityPlayer")
    private Messenger mService;
    private Messenger clientMessenger;
    private MusicAapter mAdapter;
    private List<MusicBean> musics;
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case MSG_TOTALTIME:
                    musicProgressBar.setMax(msg.arg1);
                    break;
                case MSG_CURRENTTIME:
                    musicProgressBar.setProgress(msg.arg1);
                    break;
            }
//            Log.e(TAG, "我从service中接受到的:" + msg.arg1);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        init();
        Intent intent = new Intent(this, PlayerService.class);
//        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void init() {
        musics = new ArrayList<>();
        musiclist.setLayoutManager(new LinearLayoutManager(this));
        musicProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    Message message = Message.obtain();
                    message.arg1 = seekBar.getProgress();
                    message.what = PlayerService.MSG_SEEKPLAY;
                    try {
                        mService.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @OnClick(R.id.getmusic)
    public void onViewClicked() {
        getList();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "连接成功");
            mService = new Messenger(service);
            clientMessenger = new Messenger(handler);
            Message message = Message.obtain();
            message.what = PlayerService.MSG_MESSENGER;
            message.replyTo = clientMessenger;
            try {
                mService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "连接断开");
            mService = null;
        }
    };

    private void getList() {
        String rootPaht = Environment.getExternalStorageDirectory() + "/Music";
        File file = new File(rootPaht);
        final MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        File[] tempList = file.listFiles();
        Observable.from(tempList).subscribe(new Action1<File>() {
            @Override
            public void call(File file) {
                MusicBean music = null;
                mmr.setDataSource(file.getPath());
                String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                String author = mmr.extractMetadata((MediaMetadataRetriever.METADATA_KEY_AUTHOR));
                String path = file.getPath();
                music = new MusicBean(title, path, author, album);
                Message message = Message.obtain(null, PlayerService.MSG_PERPREA, music);
                musics.add(music);
                Log.e(TAG, music.toString());
                try {
                    mService.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        }, new Action0() {
            @Override
            public void call() {
                Log.e(TAG, "完成");
                musiclist.setAdapter(mAdapter = new MusicAapter(ActivityPlayer.this));
                mAdapter.setOnItemClickListenser(ActivityPlayer.this);
                mAdapter.setData(musics);
            }
        });

    }

    @Override
    public void onclick(int position) {
        Message message = Message.obtain();
        message.what = PlayerService.MSG_PLAY;
        message.arg1 = position;
        try {
            mService.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);


    }


}

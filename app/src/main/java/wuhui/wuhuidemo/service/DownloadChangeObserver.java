package wuhui.wuhuidemo.service;

import android.database.ContentObserver;
import android.os.Handler;

/**
 * Created by wuhui on 2017/7/26.
 */

public class DownloadChangeObserver extends ContentObserver {
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    private Handler handler;

    public DownloadChangeObserver(Handler handler) {
        super(handler);
        this.handler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
//        handler.
    }
}

package wuhui.library.network.download;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by wuhui on 2017/3/23.
 * 下载
 */

public class DownloadResponseBody extends ResponseBody {
    private ResponseBody responseBody;
    private BufferedSource bufferedSource;
    private DownloadListener downloadListener;

    public DownloadResponseBody(ResponseBody responseBody, DownloadListener downloadListener) {
        this.responseBody = responseBody;
        this.downloadListener = downloadListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(getSource(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source getSource(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
//                Log.e("Response", "read");
                long readBytes = super.read(sink, byteCount);
                //当readBytes=1时候说明加载完成 就不用再加了
                totalBytesRead += readBytes != -1 ? readBytes : 0;
                downloadListener.download(totalBytesRead, responseBody.contentLength(), readBytes == -1);
                return readBytes;
            }
        };
    }
}

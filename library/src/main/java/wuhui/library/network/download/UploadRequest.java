package wuhui.library.network.download;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Timeout;

/**
 * Created by wuhui on 2017/5/26.
 * 上传
 */

public class UploadRequest extends RequestBody {
    private RequestBody requestBody;

    @Nullable
    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Okio.buffer(new ForwardingSink(sink) {
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
            }

            @Override
            public void flush() throws IOException {
                super.flush();
            }

            @Override
            public Timeout timeout() {
                return super.timeout();
            }

            @Override
            public void close() throws IOException {
                super.close();
            }
        });

    }
}

package wuhui.wuhuidemo;

import android.util.Base64;
import android.util.Log;

import org.junit.Test;

import java.security.KeyPair;

import wuhui.wuhuidemo.uitl.RsaUtils;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testStatic() {
        final Test1 test = new Test1();
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    for (int j = 0; j < 100000; j++)
                        test.increase();
                }

                ;
            }.start();
        }
        while (Thread.activeCount() > 2) { //保证前面的线程都执行完
            Thread.yield();
        }
        System.out.println(test.inc);
    }

    @Test
    public void testSingltor() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Test1.getDefault());
        }


    }

    @Test
    public void testKeyPair() {
        String test = "test";
        KeyPair keyPair = RsaUtils.generateRSAKeyPair(1024);
        try {
            //公钥加密
            byte[] encrypt_public_msg = RsaUtils.encryptByPublicKey(test.getBytes(), keyPair.getPublic().getEncoded());
            System.out.println("公钥：" + Base64.encodeToString(keyPair.getPublic().getEncoded(), Base64.DEFAULT));
            System.out.println("私钥：" + new String(keyPair.getPrivate().getEncoded()));
            //   String msg = Base64.encodeToString(encrypt_public_msg, Base64.DEFAULT);
            System.out.println("加密之后：" + new String(encrypt_public_msg));
            //私钥解密
            byte[] decrypt_public_msg = RsaUtils.decryptByPrivateKey(encrypt_public_msg, keyPair.getPrivate().getEncoded());
//            byte[] decrypt_public_msg = RsaUtils.decryptByPrivateKey(Base64.decode(msg, Base64.DEFAULT), keyPair.getPrivate().getEncoded());
            System.out.println("解密之后：" + new String(decrypt_public_msg));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class Test1 {
    public int inc = 0;
    static Test1 test;

    public static Test1 getDefault() {
        if (test == null) {
            test = new Test1();

        }
        return test;
    }


    public synchronized void increase() {
        inc++;
    }
}
package wuhui.wuhuidemo.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wuhui on 2016/12/29.
 */
@Entity
public class UserTest {
    @Id
    private long id;
    private String UserName;
    private String psd;

    public UserTest(String UserName, String psd) {
        this.UserName = UserName;
        this.psd = psd;
    }

    @Generated(hash = 1561252655)
    public UserTest(long id, String UserName, String psd) {
        this.id = id;
        this.UserName = UserName;
        this.psd = psd;
    }

    @Generated(hash = 1935344780)
    public UserTest() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPsd() {
        return this.psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }
}

package wuhui.wuhuidemo.entity;

import java.util.List;

/**
 * Created by wuhui on 2016/12/28.
 */

public class ZhiShuEntity {


    /**
     * Success : 1
     * Message : 获取价格指数成功！
     * Info : [{"PinZhong":"1","Point":"85.8300","Price":"3712.36","OldIndex":"86.5300","OldPrice":"3742.74","Date":"2017-06-05"},{"PinZhong":"2","Point":"106.537","Price":"3769.07","OldIndex":"107.93","OldPrice":"3818.36","Date":"2017-06-05"},{"PinZhong":"3","Point":"71.3415","Price":"3296.91","OldIndex":"71.7722","OldPrice":"3316.81","Date":"2017-06-05"},{"PinZhong":"4","Point":"81.15","Price":"56.55","OldIndex":"81.32","OldPrice":"56.54","Date":"2017-06-05"},{"PinZhong":"5","Point":"160.041","Price":"1636.48","OldIndex":"160.344","OldPrice":"1639.59","Date":"2017-06-05"}]
     */

    private String Success;
    private String Message;
    private List<InfoBean> Info;

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String Success) {
        this.Success = Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<InfoBean> getInfo() {
        return Info;
    }

    public void setInfo(List<InfoBean> Info) {
        this.Info = Info;
    }

    public static class InfoBean {
        /**
         * PinZhong : 1
         * Point : 85.8300
         * Price : 3712.36
         * OldIndex : 86.5300
         * OldPrice : 3742.74
         * Date : 2017-06-05
         */

        private String PinZhong;
        private String Point;
        private String Price;
        private String OldIndex;
        private String OldPrice;
        private String Date;

        public String getPinZhong() {
            return PinZhong;
        }

        public void setPinZhong(String PinZhong) {
            this.PinZhong = PinZhong;
        }

        public String getPoint() {
            return Point;
        }

        public void setPoint(String Point) {
            this.Point = Point;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getOldIndex() {
            return OldIndex;
        }

        public void setOldIndex(String OldIndex) {
            this.OldIndex = OldIndex;
        }

        public String getOldPrice() {
            return OldPrice;
        }

        public void setOldPrice(String OldPrice) {
            this.OldPrice = OldPrice;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }
    }
}

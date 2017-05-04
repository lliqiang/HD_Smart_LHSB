package com.hengda.linhai.m.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by lenovo on 2017/4/28.
 */

public class CityBean  {

    /**
     * status : 1
     * data : [{"province_id":"477","province_name":"直辖市","city_info":[{"city_id":"11","city_name":"北京"},{"city_id":"31","city_name":"上海"},{"city_id":"12","city_name":"天津"},{"city_id":"50","city_name":"重庆"}]},{"province_id":"478","province_name":"自治区","city_info":[{"city_id":"15","city_name":"内蒙古自治区"},{"city_id":"65","city_name":"维吾尔自治区"},{"city_id":"54","city_name":"西藏自治区"},{"city_id":"64","city_name":"宁夏回族自治区"},{"city_id":"45","city_name":"广西壮族自治区"}]},{"province_id":"479","province_name":"特别行政区","city_info":[{"city_id":"81","city_name":"香港特别行政区"},{"city_id":"82","city_name":"澳门特别行政区"}]},{"province_id":"23","province_name":"黑龙江","city_info":[{"city_id":"23","city_name":"伊春市 "},{"city_id":"23","city_name":"牡丹江 "},{"city_id":"23","city_name":"大庆市"},{"city_id":"23","city_name":" 鸡西市"},{"city_id":"23","city_name":"鹤岗市"},{"city_id":"23","city_name":" 绥化市"},{"city_id":"23","city_name":"双鸭山 "},{"city_id":"23","city_name":"七台河"},{"city_id":"23","city_name":"佳木斯 "},{"city_id":"23","city_name":"黑河市 "},{"city_id":"23","city_name":"齐齐哈尔市 "}]},{"province_id":"22","province_name":"吉林","city_info":[{"city_id":"22","city_name":"吉林市 "},{"city_id":"22","city_name":"通化市 "},{"city_id":"22","city_name":"白城市"},{"city_id":"22","city_name":"四平市"},{"city_id":"22","city_name":"辽源市"},{"city_id":"22","city_name":"松原市 "},{"city_id":"22","city_name":"白山市"}]},{"province_id":"21","province_name":"辽宁","city_info":[{"city_id":"21","city_name":" 盘锦市"},{"city_id":"21","city_name":"鞍山市"},{"city_id":"21","city_name":"抚顺市"},{"city_id":"21","city_name":" 本溪市"},{"city_id":"21","city_name":"铁岭市 "},{"city_id":"21","city_name":"锦州市"},{"city_id":"21","city_name":"丹东市"},{"city_id":"21","city_name":"辽阳市"},{"city_id":"21","city_name":"葫芦岛"},{"city_id":"21","city_name":"阜新市 "},{"city_id":"21","city_name":"朝阳市"},{"city_id":"21","city_name":"营口市"}]},{"province_id":"13","province_name":"河北","city_info":[{"city_id":"13","city_name":"石家庄 "},{"city_id":"13","city_name":"保定市 "},{"city_id":"13","city_name":"秦皇岛 "},{"city_id":"13","city_name":"唐山市 "},{"city_id":"13","city_name":"邯郸市 "},{"city_id":"13","city_name":"邢台市"},{"city_id":"13","city_name":"沧州市"},{"city_id":"13","city_name":" 承德市 "},{"city_id":"13","city_name":"廊坊市"},{"city_id":"13","city_name":" 衡水市"},{"city_id":"13","city_name":"张家口 "}]},{"province_id":"14","province_name":"山西","city_info":[{"city_id":"14","city_name":"太原市"},{"city_id":"14","city_name":"大同市"},{"city_id":"14","city_name":" 阳泉市"},{"city_id":"14","city_name":"长治市 "},{"city_id":"14","city_name":"临汾市"},{"city_id":"14","city_name":"晋中市"},{"city_id":"14","city_name":"运城市"},{"city_id":"14","city_name":"晋城市"},{"city_id":"14","city_name":"忻州市"},{"city_id":"14","city_name":"朔州市"},{"city_id":"14","city_name":"吕梁市"}]},{"province_id":"63","province_name":"青海","city_info":[{"city_id":"63","city_name":"西宁市 "},{"city_id":"63","city_name":"海东市"}]},{"province_id":"37","province_name":"山东","city_info":[{"city_id":"37","city_name":"潍坊市"},{"city_id":"37","city_name":"淄博市"},{"city_id":"37","city_name":"威海市 "},{"city_id":"37","city_name":"枣庄市"},{"city_id":"37","city_name":"泰安市"},{"city_id":"37","city_name":"临沂市"},{"city_id":"37","city_name":"东营市"},{"city_id":"37","city_name":" 济宁市"},{"city_id":"37","city_name":"烟台市"},{"city_id":"37","city_name":"菏泽市"},{"city_id":"37","city_name":"日照市 "},{"city_id":"37","city_name":"德州市"},{"city_id":"37","city_name":"聊城市"},{"city_id":"37","city_name":"滨州市"},{"city_id":"37","city_name":"莱芜市"}]},{"province_id":"41","province_name":"河南","city_info":[{"city_id":"41","city_name":"郑州市 "},{"city_id":"41","city_name":"洛阳市"},{"city_id":"41","city_name":"焦作市"},{"city_id":"41","city_name":"商丘市"},{"city_id":"41","city_name":"信阳市"},{"city_id":"41","city_name":"新乡市"},{"city_id":"41","city_name":"安阳市 "},{"city_id":"41","city_name":"开封市"},{"city_id":"41","city_name":"漯河市"},{"city_id":"41","city_name":"南阳市 "},{"city_id":"41","city_name":"鹤壁市"},{"city_id":"41","city_name":"平顶山"},{"city_id":"41","city_name":"濮阳市"},{"city_id":"41","city_name":"许昌市"},{"city_id":"41","city_name":"周口市"},{"city_id":"41","city_name":"三门峡"},{"city_id":"41","city_name":" 驻马店 "}]},{"province_id":"32","province_name":"江苏","city_info":[{"city_id":"32","city_name":" 无锡市"},{"city_id":"32","city_name":"常州市"},{"city_id":"32","city_name":"扬州市"},{"city_id":"32","city_name":"徐州市"},{"city_id":"32","city_name":"苏州市"},{"city_id":"32","city_name":"连云港"},{"city_id":"32","city_name":"盐城市"},{"city_id":"32","city_name":"淮安市"},{"city_id":"32","city_name":"宿迁市"},{"city_id":"32","city_name":"镇江市"},{"city_id":"32","city_name":"南通市"},{"city_id":"32","city_name":"泰州市"}]},{"province_id":"34","province_name":"安徽","city_info":[{"city_id":"34","city_name":"芜湖市"},{"city_id":"34","city_name":"合肥市"},{"city_id":"34","city_name":"六安市　"},{"city_id":"34","city_name":"宿州市"},{"city_id":"34","city_name":"阜阳市"},{"city_id":"34","city_name":"安庆市　"},{"city_id":"34","city_name":"马鞍山市"},{"city_id":"34","city_name":"蚌埠市"},{"city_id":"34","city_name":"淮北市　"},{"city_id":"34","city_name":"淮南市　"},{"city_id":"34","city_name":"宣城市"},{"city_id":"34","city_name":"黄山市"},{"city_id":"34","city_name":"铜陵市"},{"city_id":"34","city_name":"亳州市"},{"city_id":"34","city_name":"池州市　"},{"city_id":"34","city_name":"巢湖市"},{"city_id":"34","city_name":"滁州市　"}]},{"province_id":"33","province_name":"浙江","city_info":[{"city_id":"33","city_name":"温州市"},{"city_id":"33","city_name":"宁波市"},{"city_id":"33","city_name":"杭州市　"},{"city_id":"33","city_name":"台州市"},{"city_id":"33","city_name":"嘉兴市"},{"city_id":"33","city_name":"金华市　"},{"city_id":"33","city_name":"湖州市"},{"city_id":"33","city_name":"绍兴市　"},{"city_id":"33","city_name":"舟山市"},{"city_id":"33","city_name":"丽水市"},{"city_id":"33","city_name":"衢州市　"}]},{"province_id":"35","province_name":"福建","city_info":[{"city_id":"35","city_name":"漳州市"},{"city_id":"35","city_name":"泉州市　"},{"city_id":"35","city_name":"厦门市　"},{"city_id":"35","city_name":"福州市　"},{"city_id":"35","city_name":"莆田市"},{"city_id":"35","city_name":"宁德市"},{"city_id":"35","city_name":"三明市"},{"city_id":"35","city_name":"南平市　"},{"city_id":"35","city_name":"龙岩市"}]},{"province_id":"36","province_name":"江西","city_info":[{"city_id":"36","city_name":"南昌市"},{"city_id":"36","city_name":"赣州市"},{"city_id":"36","city_name":"上饶市"},{"city_id":"36","city_name":" 吉安市　"},{"city_id":"36","city_name":"九江市　"},{"city_id":"36","city_name":"新余市　"},{"city_id":"36","city_name":"抚州市"},{"city_id":"36","city_name":"宜春市　"},{"city_id":"36","city_name":"景德镇市"},{"city_id":"36","city_name":" 萍乡市"},{"city_id":"36","city_name":"鹰潭市　"}]},{"province_id":"43","province_name":"湖南","city_info":[{"city_id":"43","city_name":"长沙市"},{"city_id":"43","city_name":"邵阳市"},{"city_id":"43","city_name":"常德市"},{"city_id":"43","city_name":" 衡阳市"},{"city_id":"43","city_name":"株洲市"},{"city_id":"43","city_name":"湘潭市　"},{"city_id":"43","city_name":"永州市"},{"city_id":"43","city_name":"岳阳市　"},{"city_id":"43","city_name":"怀化市"},{"city_id":"43","city_name":"郴州市"},{"city_id":"43","city_name":"娄底市"},{"city_id":"43","city_name":"益阳市"},{"city_id":"43","city_name":"张家界市　"},{"city_id":"43","city_name":"湘西州"}]},{"province_id":"42","province_name":"湖北","city_info":[{"city_id":"42","city_name":"武汉市"},{"city_id":"42","city_name":"宜昌市"},{"city_id":"42","city_name":"襄樊市"},{"city_id":"42","city_name":" 荆州市"},{"city_id":"42","city_name":"恩施州"},{"city_id":"42","city_name":"孝感市"},{"city_id":"42","city_name":" 黄冈市"},{"city_id":"42","city_name":"十堰市"},{"city_id":"42","city_name":"咸宁市"},{"city_id":"42","city_name":"黄石市"},{"city_id":"42","city_name":" 仙桃市"},{"city_id":"42","city_name":"随州市"},{"city_id":"42","city_name":" 天门市"},{"city_id":"42","city_name":"荆门市"},{"city_id":"42","city_name":"潜江市"},{"city_id":"42","city_name":" 鄂州市　"},{"city_id":"42","city_name":" 神农架林区　"}]},{"province_id":"44","province_name":"广东","city_info":[{"city_id":"44","city_name":"东莞市"},{"city_id":"44","city_name":"广州市"},{"city_id":"44","city_name":"中山市"},{"city_id":"44","city_name":"深圳市　"},{"city_id":"44","city_name":" 惠州市　"},{"city_id":"44","city_name":"江门市"},{"city_id":"44","city_name":" 珠海市"},{"city_id":"44","city_name":"汕头市　"},{"city_id":"44","city_name":"佛山市"},{"city_id":"44","city_name":"湛江市"},{"city_id":"44","city_name":"河源市"},{"city_id":"44","city_name":"肇庆市"},{"city_id":"44","city_name":"潮州市"},{"city_id":"44","city_name":"清远市"},{"city_id":"44","city_name":"韶关市"},{"city_id":"44","city_name":"揭阳市"},{"city_id":"44","city_name":"阳江市"},{"city_id":"44","city_name":"云浮市　 "},{"city_id":"44","city_name":"茂名市"},{"city_id":"44","city_name":"梅州市"},{"city_id":"44","city_name":"汕尾市　"}]},{"province_id":"71","province_name":"台湾","city_info":[{"city_id":"71","city_name":"台北市"},{"city_id":"71","city_name":" 高雄市"},{"city_id":"71","city_name":" 台中市"},{"city_id":"71","city_name":"新竹市　"},{"city_id":"71","city_name":"基隆市"},{"city_id":"71","city_name":"台南市"},{"city_id":"71","city_name":"嘉义市　"}]},{"province_id":"46","province_name":"海南","city_info":[{"city_id":"46","city_name":"三亚市"},{"city_id":"46","city_name":"海口市"},{"city_id":"46","city_name":" 琼海市"},{"city_id":"46","city_name":"文昌市　"},{"city_id":"46","city_name":" 东方市"},{"city_id":"46","city_name":"昌江县　"},{"city_id":"46","city_name":"陵水县"},{"city_id":"46","city_name":"乐东县"},{"city_id":"46","city_name":"五指山市　"},{"city_id":"46","city_name":"保亭县"},{"city_id":"46","city_name":"澄迈县"},{"city_id":"46","city_name":"万宁市"},{"city_id":"46","city_name":"儋州市　"},{"city_id":"46","city_name":"临高县"},{"city_id":"46","city_name":"白沙县"},{"city_id":"46","city_name":"定安县　"},{"city_id":"46","city_name":"琼中县"},{"city_id":"46","city_name":"屯昌县　"}]},{"province_id":"62","province_name":"甘肃","city_info":[{"city_id":"62","city_name":"兰州市"},{"city_id":"62","city_name":"天水市"},{"city_id":"62","city_name":"庆阳市"},{"city_id":"62","city_name":"武威市"},{"city_id":"62","city_name":" 酒泉市"},{"city_id":"62","city_name":" 张掖市"},{"city_id":"62","city_name":"陇南地区"},{"city_id":"62","city_name":"白银市"},{"city_id":"62","city_name":"定西地区"},{"city_id":"62","city_name":" 平凉市"},{"city_id":"62","city_name":"嘉峪关市"},{"city_id":"62","city_name":"临夏回族自治州"},{"city_id":"62","city_name":"金昌市"},{"city_id":"62","city_name":"甘南州"}]},{"province_id":"51","province_name":"四川","city_info":[{"city_id":"51","city_name":"成都市"},{"city_id":"51","city_name":"绵阳市"},{"city_id":"51","city_name":"广元市　"},{"city_id":"51","city_name":"达州市"},{"city_id":"51","city_name":"南充市"},{"city_id":"51","city_name":"德阳市"},{"city_id":"51","city_name":"广安市"},{"city_id":"51","city_name":"阿坝州"},{"city_id":"51","city_name":"巴中市"},{"city_id":"51","city_name":"遂宁市"},{"city_id":"51","city_name":"内江市"},{"city_id":"51","city_name":"凉山州"},{"city_id":"51","city_name":"攀枝花市"},{"city_id":"51","city_name":"乐山市"},{"city_id":"51","city_name":"自贡市"},{"city_id":"51","city_name":" 泸州市"},{"city_id":"51","city_name":"雅安市"},{"city_id":"51","city_name":"宜宾市"},{"city_id":"51","city_name":"资阳市"},{"city_id":"51","city_name":"眉山市"},{"city_id":"51","city_name":"甘孜州　"}]},{"province_id":"52","province_name":"贵州","city_info":[{"city_id":"52","city_name":"贵阳市"},{"city_id":"52","city_name":"黔东南州"},{"city_id":"52","city_name":"黔南州"},{"city_id":"52","city_name":"遵义市"},{"city_id":"52","city_name":"黔西南州"},{"city_id":"52","city_name":"毕节地区"},{"city_id":"52","city_name":" 铜仁地区"},{"city_id":"52","city_name":" 安顺市"},{"city_id":"52","city_name":"六盘水市　"}]},{"province_id":"53","province_name":"云南","city_info":[{"city_id":"53","city_name":"昆明市"},{"city_id":"53","city_name":"红河州"},{"city_id":"53","city_name":"大理州"},{"city_id":"53","city_name":"文山州"},{"city_id":"53","city_name":"德宏州"},{"city_id":"53","city_name":"曲靖市　"},{"city_id":"53","city_name":" 昭通市"},{"city_id":"53","city_name":"楚雄州"},{"city_id":"53","city_name":"保山市"},{"city_id":"53","city_name":"玉溪市"},{"city_id":"53","city_name":"丽江地区"},{"city_id":"53","city_name":"临沧地区"},{"city_id":"53","city_name":"思茅地区"},{"city_id":"53","city_name":"西双版纳州"},{"city_id":"53","city_name":"怒江州"},{"city_id":"53","city_name":"迪庆州　"}]}]
     * msg : 操作成功
     */

    private int status;
    private String msg;
    /**
     * province_id : 477
     * province_name : 直辖市
     * city_info : [{"city_id":"11","city_name":"北京"},{"city_id":"31","city_name":"上海"},{"city_id":"12","city_name":"天津"},{"city_id":"50","city_name":"重庆"}]
     */

    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }



    public static class DataBean implements IPickerViewData{
        private String province_id;
        private String province_name;
        /**
         * city_id : 11
         * city_name : 北京
         */

        private List<CityInfoBean> city_info;

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public List<CityInfoBean> getCity_info() {
            return city_info;
        }

        public void setCity_info(List<CityInfoBean> city_info) {
            this.city_info = city_info;
        }

        @Override
        public String getPickerViewText() {
            return getProvince_name();
        }

        public static class CityInfoBean {
            private String city_id;
            private String city_name;

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }
        }
    }
}

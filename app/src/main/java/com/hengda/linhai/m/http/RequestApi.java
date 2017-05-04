package com.hengda.linhai.m.http;

import android.text.TextUtils;
import android.util.Log;


import com.hengda.linhai.m.app.HdAppConfig;
import com.hengda.linhai.m.app.HdApplication;
import com.hengda.linhai.m.app.HdConstant;
import com.hengda.linhai.m.bean.ChatRecord;
import com.hengda.linhai.m.bean.CheckResponse;
import com.hengda.linhai.m.bean.CityBean;
import com.hengda.linhai.m.bean.CollectBean;
import com.hengda.linhai.m.bean.CommentBean;
import com.hengda.linhai.m.bean.CommentDetailBean;
import com.hengda.linhai.m.bean.CreateBean;
import com.hengda.linhai.m.bean.DetailBean;
import com.hengda.linhai.m.bean.DetailExhibit;
import com.hengda.linhai.m.bean.DeviceBean;
import com.hengda.linhai.m.bean.ExhibitListBean;
import com.hengda.linhai.m.bean.ExistBean;
import com.hengda.linhai.m.bean.FloorBean;
import com.hengda.linhai.m.bean.GetGroupBean;
import com.hengda.linhai.m.bean.HeadBean;
import com.hengda.linhai.m.bean.JoinGroup;
import com.hengda.linhai.m.bean.MessageBean;
import com.hengda.linhai.m.bean.MessageDetailBean;
import com.hengda.linhai.m.bean.NotifyBean;
import com.hengda.linhai.m.bean.PersonBean;
import com.hengda.linhai.m.bean.PersonalInfo;
import com.hengda.linhai.m.bean.RecordBean;
import com.hengda.linhai.m.bean.RegisterBean;
import com.hengda.linhai.m.bean.SearchBean;
import com.hengda.linhai.m.bean.SearchContent;
import com.hengda.linhai.m.bean.SecondBean;
import com.hengda.linhai.m.bean.TransBean;
import com.hengda.linhai.m.tools.AppUtil;
import com.hengda.linhai.m.tools.ScreenUtil;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.File;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：Tailyou （祝文飞）
 * 时间：2016/6/11 16:17
 * 邮箱：tailyou@163.com
 * 描述：
 */
public class RequestApi {

    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private IRequestService iRequestService;
    private volatile static RequestApi instance;
    private static Hashtable<String, RequestApi> mRequestApiTable;

    static {
        mRequestApiTable = new Hashtable<>();
    }

    /**
     * 私有构造函数
     *
     * @param baseHttpUrl
     */
    private RequestApi(String baseHttpUrl) {

        /*
        * OkHttpClient client = new OkHttpClient.Builder()
  .addInterceptor(new ChuckInterceptor(context))
  .build();*/
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new ChuckInterceptor(HdApplication.mContext));
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseHttpUrl)
                .build();
        iRequestService = retrofit.create(IRequestService.class);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static RequestApi getInstance() {
        String baseHttpUrl = getBaseHttpUrl();
        instance = mRequestApiTable.get(baseHttpUrl);
        if (instance == null) {
            synchronized (RequestApi.class) {
                if (instance == null) {
                    instance = new RequestApi(baseHttpUrl);
                    mRequestApiTable.clear();
                    mRequestApiTable.put(baseHttpUrl, instance);
                }
            }
        }
        return instance;
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static RequestApi getInstance(String baseHttpUrl) {
        instance = mRequestApiTable.get(baseHttpUrl);
        if (instance == null) {
            synchronized (RequestApi.class) {
                if (instance == null) {
                    instance = new RequestApi(baseHttpUrl);
                    mRequestApiTable.clear();
                    mRequestApiTable.put(baseHttpUrl, instance);
                }
            }
        }
        return instance;
    }

    /**
     * 获取网络请求基地址
     *
     * @return http://192.168.10.158/lhbwg/
     */
    public static String getBaseHttpUrl() {
        return "http://" + HdAppConfig.getDefaultIpPort() + "/lhbwg/";
    }


    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    private class HttpResultFunc<T> implements Func1<HttpResponse<T>, T> {
        @Override
        public T call(HttpResponse<T> httpResponse) {
            if (httpResponse.getStatus() != 1) {
                throw new ApiException(httpResponse.getStatus(), httpResponse.getMsg());
            } else {
                if (httpResponse.getData() == null) {
                    return (T) httpResponse.getMsg();
                } else if (httpResponse.getData().toString().equals("[]")) {
                    return null;
                } else {
                    return httpResponse.getData();
                }
            }
        }
    }

    /**
     * 检查App版本更新
     *
     * @param subscriber
     */
    public void checkVersion(Subscriber<CheckResponse> subscriber) {
        Observable<CheckResponse> observable = iRequestService.checkVersion(HdConstant.APP_KEY,
                HdConstant.APP_SECRET, 1, AppUtil.getVersionCode(HdApplication.mContext),
                "机器号");
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void bindDevice(Subscriber<HttpResponse> subscriber, String deviceno, String client_id) {
        Observable<HttpResponse> observable = iRequestService.bindDevice(deviceno, client_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 浏览次数上传
    * */
    public void lookCount(Subscriber<HttpResponse> subscriber, String exhibit_id) {
        Observable<HttpResponse> observable = iRequestService.lookCount(exhibit_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 请求机器号 01
     *
     * @param subscriber
     * @param app_kind
     */
    public void reqDeviceNo(Subscriber<DeviceBean.DataBean> subscriber, String app_kind) {
        Observable<Response<DeviceBean.DataBean>> observable = iRequestService.reqDeviceNo(app_kind);
        doSubscribe(subscriber, observable);
    }

    /*
    *  展品列表 02

    * */
    public void getListInfo(Subscriber<List<ListBean.DataBean>> subscriber, String id, String language) {
        Observable<Response<List<ListBean.DataBean>>> observable = iRequestService.getListInfo(id, language);
        doSubscribe(subscriber, observable);
    }

    /*
    * 展品详情 03
    * */
    public void getListDetail(Subscriber<SecondBean> subscriber, String id, String language) {
        Observable<Response<SecondBean>> observable = iRequestService.getListDetail(id, language);
        doSubscribe(subscriber, observable);
    }

    /*
    * 展品列表 04
    *
    * */
    public void getExhibitInfo(Subscriber<List<ExhibitListBean>> subscriber, String language) {
        Observable<Response<List<ExhibitListBean>>> observable = iRequestService.getexhibitInfo(language);
        doSubscribe(subscriber, observable);
    }

    /*
    * 临展咨询
    * */
    public void getMessageInfo(Subscriber<MessageBean> subscriber, String language) {
        Observable<MessageBean> observable = iRequestService.getMessage(language);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 用户注册
    * */
    public void RegisterInfo(Subscriber<RegisterBean> subscriber, String username, String pass, String device_id) {
        Observable<RegisterBean> observable = iRequestService.Register(username, pass, device_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 用户登录 index.php?g=mapi&m=User&a=login
    *
    * */
    public void loginInfo(Subscriber<PersonBean> subscriber, String username, String pass, String device_id) {
        Observable<PersonBean> observable = iRequestService.Login(username, pass, device_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    *
    * 加入群组
    * */
    public void joinGroup(Subscriber<JoinGroup> subscriber, String group, String device_id) {
        Observable<JoinGroup> observable = iRequestService.joinGroup(group, device_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    *
    * 退出群组
    * */
    public void createGroup(Subscriber<CreateBean> subscriber, String device_id) {
        Observable<CreateBean> observable = iRequestService.createGroupInfo(device_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 获取群组
    * */
    public void getGroupInfo(Subscriber<GetGroupBean> subscriber, String device_id) {
        Observable<GetGroupBean> observable = iRequestService.getGroupInfo(device_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 退出群组
    * */
    public void existGroup(Subscriber<ExistBean> subscriber, String device_id, String group) {
        Observable<ExistBean> observable = iRequestService.existGroup(device_id, group);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 头像上传
    *
    * */
    public void uploadPic(Subscriber<HeadBean> subscriber, String device_id, String language, String path) {
        File file = new File(path);

        if (file.exists()) {
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        Observable<Response<String>> observable = iRequestService.postHeadImg(device_id,language,requestBody);
            Observable<HeadBean> observable = iRequestService.postHeadImg(device_id, language, requestBody);
            Log.i("requestBody", "----------------------requestBody" + requestBody);
            Log.i("file", "----------------------file" + file);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
//        doSubscribe(subscriber,observable);
        } else {
        }
    }

    /*
    * 位置上传
    * */
    public void uploadLocation(Subscriber<String> subscriber, String blueteeth_id) {
        Observable<String> observable = iRequestService.uploadLocation(blueteeth_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 点赞
    * */
    public void clickOk(Subscriber<RegisterBean> subscriber, String exhibit_id, String device_id) {
        Observable<RegisterBean> observable = iRequestService.clickOk(exhibit_id, device_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 详情页面
    * */
    public void getDetailInfo(Subscriber<DetailBean> subscriber, String language, String id, String device_id) {
        Observable<DetailBean> observable = iRequestService.getDetailInfo(language, id, device_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 修改昵称
    * */
    public void modifyName(Subscriber<RegisterBean> subscriber, String nick_name, String device_id) {
        Observable<RegisterBean> observable = iRequestService.modifyName(nick_name, device_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 获取收藏记录
    * */
    public void getRecordInfo(Subscriber<CollectBean> subscriber, String device_id, String language) {
        Observable<CollectBean> observable = iRequestService.getRecordInfo(device_id, language);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
     * 获取评论记录
     * */
    public void getCommentInfo(Subscriber<CommentBean> subscriber, String device_id, String language) {
        Observable<CommentBean> observable = iRequestService.getCommentInfo(device_id, language);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    *
    * 用户浏览记录
    * */
    public void getScanInfo(Subscriber<RecordBean> subscriber, String device_id, String language) {
        Observable<RecordBean> observable = iRequestService.getScanInfo(device_id, language);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 发表评论
    * */
    public void makeComment(Subscriber<RegisterBean> subscriber, String device_id, String id, String desc) {
        Observable<RegisterBean> observable = iRequestService.makeComment(device_id, id, desc);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 修改个人信息
    *
    * */
    public void modifyInfo(Subscriber<RegisterBean> subscriber, String device_id, String key, String value) {
        Observable<RegisterBean> observable = iRequestService.modifyInfo(device_id, key, value);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 获取个人信息
    * */
    public void moidifyPersonalInfo(Subscriber<PersonalInfo> subscriber, String device_id) {
        Observable<PersonalInfo> observable = iRequestService.getPersonalInfo(device_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 获取城市信息 province_id
    *
    * */
    public void getCityInfo(Subscriber<CityBean> subscriber) {
        Observable<CityBean> observable = iRequestService.getCityInfo();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 获取搜索历史
    *
    * */
    public void getSearchHistory(Subscriber<SearchBean> subscriber, String device_id) {
        Observable<SearchBean> observable = iRequestService.getSearchHistory(device_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 搜索内容
    *
    * */
    public void getSearchContent(Subscriber<SearchContent> subscriber, String device_id, String content) {
        Observable<SearchContent> observable = iRequestService.getSearchContent(device_id, content);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
/*
* 发送聊天记录
* */
public void sendMsg(Subscriber<RegisterBean> subscriber,String device_id,String dst_id,String content){
    Observable<RegisterBean> observable = iRequestService.sendMsg(device_id,dst_id,content);
    observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber);
}
/*
* 获取聊天记录
* */
    public void getMsgRecord(Subscriber<ChatRecord> subscriber,String device_id,String dst_id,String content,String page_id,String page_size){
        Observable<ChatRecord> observable = iRequestService.getChatRecord(device_id,dst_id,content,page_id,page_size);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 修改用户密码
    *
    * */

    public void modifyPwd(Subscriber<RegisterBean> subscriber,String device_id,String old_pawd,String new_pawd){
        Observable<RegisterBean> observable = iRequestService.modifyPwd(device_id,old_pawd,new_pawd);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

/*
* 临展咨询详情
* */
    public void getDetailMessage(Subscriber<MessageDetailBean> subscriber,String language,String zixun_id){
        Observable<MessageDetailBean> observable=iRequestService.getMessageDetail(language,zixun_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
/*
* 通知公告
*
* */
    public void getNotifyDetail(Subscriber<NotifyBean> subscriber,String language,String device_id){
        Observable<NotifyBean> observable=iRequestService.getNocity(language,device_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 通知公告详情 index.php?g=mapi&m=Introduction&a=notice_detail_info
    *
    * */
    public void getDetailNotify(Subscriber<MessageDetailBean> subscriber,String language,String notice_id){
        Observable<MessageDetailBean> observable=iRequestService.getNocityDetail(language,notice_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
/**
 * 标记某一信息为已读
* */
    public void getReadInfo(Subscriber<RegisterBean>subscriber,String device_id,String type,String source_id){
        Observable<RegisterBean> observable=iRequestService.getReadInfo(device_id,type,source_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
/*
* 交通信息
* */
    public void getTransInfo(Subscriber<TransBean> subscriber,String language){
        Observable<TransBean> observable=iRequestService.getTransInfo(language);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*
    * 馆厅地图
    * */
    public void getMapInfo(Subscriber<FloorBean> subscriber,String floor_num,String language,String type){
        Observable<FloorBean> observable=iRequestService.getMapInfo(floor_num,language,type);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /*
    * 展品详情评论
    * */
    public void getCommentDetail(Subscriber<CommentDetailBean> subscriber,String exhibit_id){
        Observable<CommentDetailBean> observable=iRequestService.getCommentDetail(exhibit_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
     * 订阅（抽取公共部分）
     *
     * @param subscriber
     * @param observable
     */


    private <T> void doSubscribe(Subscriber<T> subscriber, Observable<Response<T>> observable) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Response<T>, T>() {
                    @Override
                    public T call(Response<T> response) {
                        if (TextUtils.equals(HdConstant.HTTP_STATE, response.getStatus())) {
                            return response.getData();
                        } else {
                            throw new RequestException(response.getMsg());
                        }
                    }
                })
                .subscribe(subscriber);
    }


}

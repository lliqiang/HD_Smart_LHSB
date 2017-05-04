package com.hengda.linhai.m.http;

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
import com.hengda.linhai.m.bean.TileBean;
import com.hengda.linhai.m.bean.TransBean;

import java.io.File;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 作者：Tailyou （祝文飞）
 * 时间：2016/6/11 16:17
 * 邮箱：tailyou@163.com
 * 描述：
 */
public interface IRequestService {

    /**
     * 检查App版本更新
     *
     * @param appKey
     * @param appSecret
     * @param appKind
     * @param versionCode
     * @param deviceId
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?g=&m=Api&a=checkVersion")
    Observable<CheckResponse> checkVersion(@Field("appKey") String appKey,
                                           @Field("appSecret") String appSecret,
                                           @Field("appKind") int appKind,
                                           @Field("versionCode") int versionCode,
                                           @Field("deviceId") String deviceId);

    /**
     * 请求机器号
     *
     * @param app_kind 01
     * @return http://192.168.10.158/lhbwg/index.php?g=mapi&m=User&a=generate_deviceno
     */

    @GET("index.php?g=mapi&m=User&a=generate_deviceno")
    Observable<Response<DeviceBean.DataBean>> reqDeviceNo(@Query("app_kind") String app_kind);

    /*
    * 展品列表 二级页面跳转
    *
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=Exhibit&a=exhibits_by_exhibitroom")
    Observable<Response<List<ListBean.DataBean>>> getListInfo(@Field("id") String id,
                                                              @Field("language") String language);


    /*
    * 展厅详情接口   二级界面
    *index.php?g=mapi&m=Exhibit&a=exhibit_detail
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=Exhibit&a=exhibitroom_detail")
    Observable<Response<SecondBean>> getListDetail(@Field("id") String id, @Field("language") String language);

    /*
    * 馆厅列表  一级页面
    * */
    @GET("index.php?g=mapi&m=Exhibit&a=exhibitroom_list")
    Observable<Response<List<ExhibitListBean>>> getexhibitInfo(@Query("language") String language);

    /*
    展品列表
    * index.php?g=mapi&m=Exhibit&a=exhibits_by_exhibitroom
    *
    * */
//    @FormUrlEncoded
//    @POST("index.php?g=mapi&m=Exhibit&a=exhibits_by_exhibitroom")
//    Observable<Response<List<ListBean.DataBean>>> getListInfo(@Field("id") String id,
//                                                              @Field("language") String language);


    /*
    *
    * 更新资源
    * */
    @GET("index.php?g=mapi&m=Resource&a=update_zip")
    Observable<ResUpdate> getRes(@Query("version") int version);


    /*
    * 绑定机器号
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=Friend&a=bind_deviceno")
    Observable<HttpResponse> bindDevice(@Field("device_id") String device_id,
                                        @Field("client_id") String client_id);

    /*
    * 浏览次数上传
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=positions&a=upload_looks")
    Observable<HttpResponse> lookCount(@Field("exhibit_id") String exhibit_id);

    /*
    * 临展咨询
    *  index.php?g=mapi&m=Introduction&a=exhibit_info
    * */
    @GET("index.php?g=mapi&m=Introduction&a=exhibit_info")
    Observable<MessageBean> getMessage(@Query("language") String language);

    /*
    * 用户注册 index.php?g=mapi&m=User&a=register
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=User&a=register")
    Observable<RegisterBean> Register(@Field("username") String username,
                                      @Field("pass") String pass,
                                      @Field("device_id") String device_id);

    /*
    * 用户登录
    * index.php?g=mapi&m=User&a=login
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=User&a=login")
    Observable<PersonBean> Login(@Field("username") String username,
                                 @Field("pass") String pass,
                                 @Field("device_id") String device_id);

    /*
    * 加入群组 index.php?g=mapi&m=Friend&a=add_group
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=Friend&a=add_group")
    Observable<JoinGroup> joinGroup(@Field("group") String group,
                                    @Field("device_id") String device_id);

    /*
    * 创建群组  index.php?g=mapi&m=Friend&a=request_group
    *
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=Friend&a=request_group")
    Observable<CreateBean> createGroupInfo(@Field("device_id") String device_id);

    /*
    * 获取群组列表 index.php?g=mapi&m=Friend&a=get_group_member
    *
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=Friend&a=get_group_member")
    Observable<GetGroupBean> getGroupInfo(@Field("device_id") String device_id);

    //    /*
//    * 退出登录
//    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=Friend&a=del_group")
    Observable<ExistBean> existGroup(@Field("device_id") String device_id, @Field("group") String group);

    /*
    *
    * 头像上传  index.php?g=mapi&m=User&a=edit_avatar
    * */
    @Multipart
    @POST("index.php?g=mapi&m=User&a=edit_avatar")
    Observable<HeadBean> postHeadImg(@Part("device_id") String device_id, @Part("language") String language, @Part("avatar\"; filename=\"head_icon.png") RequestBody imgs);

    /*
    * 上传位置
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=Friend&a=position")
    Observable<String> uploadLocation(@Field("blueteeth_id") String blueteeth_id);

    /*
    * 点赞 index.php?g=mapi&m=Exhibit&a=like_exhibit
    * */
    @GET("index.php?g=mapi&m=Exhibit&a=like_exhibit")
    Observable<RegisterBean> clickOk(@Query("exhibit_id") String exhibit_id, @Query("device_id") String device_id);

    /*
    * 我的评论  index.php?g=mapi&m=Exhibit&a=my_comments
    * */

    /*
    * 修改昵称
    * */
        /*
    * 用户注册 index.php?g=mapi&m=User&a=register
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=User&a=edit_nick_name")
    Observable<RegisterBean> modifyName(@Field("nick_name") String nick_name,
                                        @Field("device_id") String device_id);
/*
* 详情页面 index.php?g=mapi&m=Exhibit&a=exhibit_detail
* */

    @GET("index.php?g=mapi&m=Exhibit&a=exhibit_detail")
    Observable<DetailBean> getDetailInfo(@Query("language") String language,
                                         @Query("id") String id,
                                         @Query("device_id") String device_id);

    /*
    * 用户收藏 device_id	string
    机器号

    language  index.php?g=mapi&m=Exhibit&a=collect_records
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=Exhibit&a=collect_records")
    Observable<CollectBean> getRecordInfo(@Field("device_id") String device_id,
                                          @Field("language") String language);

    /*
    * 评论列表 index.php?g=mapi&m=Exhibit&a=my_comments
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=Exhibit&a=my_comments")
    Observable<CommentBean> getCommentInfo(@Field("device_id") String device_id,
                                           @Field("language") String language);

    /*
    * 浏览记录  index.php?g=mapi&m=Exhibit&a=visit_records
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=Exhibit&a=visit_records")
    Observable<RecordBean> getScanInfo(@Field("device_id") String device_id,
                                       @Field("language") String language);

    /*
    * 发表评论 index.php?g=mapi&m=Exhibit&a=exhibit_comment
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=Exhibit&a=exhibit_comment")
    Observable<RegisterBean> makeComment(@Field("device_id") String device_id,
                                         @Field("id") String id,
                                         @Field("desc") String desc);

    /*
    *
    * 修改密码 index.php?g=mapi&m=User&a=mod_passwd
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=User&a=mod_passwd")
    Observable<RegisterBean> modifyPwd(@Field("device_id") String device_id,
                                       @Field("old_pawd") String old_pawd,
                                       @Field("new_pawd") String new_pawd);

    /*
    * 修改特定个人信息   index.php?g=mapi&m=User&a=personal_info_update
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=User&a=personal_info_update")
    Observable<RegisterBean> modifyInfo(@Field("device_id") String device_id,
                                        @Field("key") String key,
                                        @Field("value") String value);

    /*
    * 获取个人信息   index.php?g=mapi&m=User&a=get_person_info
    * */
    @GET("index.php?g=mapi&m=User&a=get_person_info")
    Observable<PersonalInfo> getPersonalInfo(@Query("device_id") String device_id);

    /*
    * 获取城市信息 index.php?g=mapi&m=User&a=get_province_info
    * */
    @GET("index.php?g=mapi&m=User&a=get_province_info")
    Observable<CityBean> getCityInfo();

    /*
    * 搜索历史  index.php?g=mapi&m=User&a=search_historys device_id
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=User&a=search_historys")
    Observable<SearchBean> getSearchHistory(@Field("device_id") String device_id);

    /*
    * 搜索接口 index.php?g=mapi&m=User&a=get_search_content content
    * */
    @GET("index.php?g=mapi&m=User&a=get_search_content")
    Observable<SearchContent> getSearchContent(@Query("device_id") String device_id, @Query("content") String content);

    /*
    * 发送聊天记录
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=Friend&a=chat_on")
    Observable<RegisterBean> sendMsg(@Field("device_id") String device_id, @Field("dst_id") String dst_id, @Field("content") String content);

    /*
    * 获取聊天记录 index.php?g=mapi&m=Friend&a=get_chat_content
    *
    * device_id	string

    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=Friend&a=get_chat_content")
    Observable<ChatRecord> getChatRecord(@Field("device_id") String device_id, @Field("dst_id") String dst_id, @Field("content") String content, @Field("page_id") String page_id, @Field("page_size") String page_size);

    //    /*
//    * 获取咨询详情  index.php?g=mapi&m=Introduction&a=exhibit_detail_info
//    * */
    @GET("index.php?g=mapi&m=Introduction&a=exhibit_detail_info")
    Observable<MessageDetailBean> getMessageDetail(@Query("language") String language, @Query("zixun_id") String zixun_id);

    /*
    * 通知公告  index.php?g=mapi&m=Introduction&a=notices
    * */
    @GET("index.php?g=mapi&m=Introduction&a=notices")
    Observable<NotifyBean> getNocity(@Query("language") String language, @Query("device_id") String device_id);


    /*
    * 通知公告详情  index.php?g=mapi&m=Introduction&a=notice_detail_info
    * */
    @GET("index.php?g=mapi&m=Introduction&a=notice_detail_info")
    Observable<MessageDetailBean> getNocityDetail(@Query("language") String language, @Query("notice_id") String notice_id);

    /*
    * 标记某一信息为已读 index.php?g=mapi&m=Introduction&a=read_notices
    *
    * */
    @GET("index.php?g=mapi&m=Introduction&a=read_notices")
    Observable<RegisterBean> getReadInfo(@Query("device_id") String device_id, @Query("type") String type, @Query("source_id") String source_id);

    /*
    * 交通信息 index.php?g=mapi&m=Introduction&a=travel
    * */
    @GET("index.php?g=mapi&m=Introduction&a=travel")
    Observable<TransBean> getTransInfo(@Query("language") String language);

    /*
    * 馆厅地图  index.php?g=mapi&m=Exhibit&a=exhibits_by_room
    * */
    @FormUrlEncoded
    @POST("index.php?g=mapi&m=Exhibit&a=exhibits_by_room")
    Observable<FloorBean> getMapInfo(@Field("floor_num") String floor_num, @Field("language") String language, @Field("type") String type);

    /*
    * 展品详情评论 index.php?g=mapi&m=Exhibit&a=comments_by_exhibit
    * */
    @GET("index.php?g=mapi&m=Exhibit&a=comments_by_exhibit")
    Observable<CommentDetailBean> getCommentDetail(@Query("exhibit_id") String exhibit_id);

    /*id展示地图
    *    index.php?g=mapi&m=Exhibit&a=show_exhibits_by_ids
    * */
    @GET("index.php?g=mapi&m=Exhibit&a=show_exhibits_by_ids")
    Observable<TileBean> getMapFromId(@Query("exhibit_id") String exhibit_id,@Query("language") String language);
}

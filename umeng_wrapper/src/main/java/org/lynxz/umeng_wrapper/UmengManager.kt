package org.lynxz.umeng_wrapper

import android.app.Activity
import android.app.Application
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import org.lynxz.umeng_wrapper.observer.EmptyUMAuthListener

/**
 * 文档: https://developer.umeng.com/docs/66632/detail/66639
 * */
object UmengManager {
    lateinit var appliation: Application
    lateinit var shareApi: UMShareAPI

    /**
     * 初始化common库 在  application#onCreate() 中调用
     * 参数1:上下文，不能为空
     * 参数2:【友盟+】 AppKey 非必须参数，如果Manifest文件中已配置AppKey，该参数可以传空，则使用Manifest中配置的AppKey，否则该参数必须传入
     * 参数3:【友盟+】 Channel，非必须参数，如果Manifest文件中已配置Channel，该参数可以传空，则使用Manifest中配置的Channel，否则该参数必须传入，Channel命名请详见Channel渠道命名规范
     * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
     * 参数3:Push推送业务的secret,需要集成Push功能时必须传入Push的secret，否则传空
     *
     * androidManifest.xml配置
     * <pre>
     *  <manifest>
     *       <application>
     *           <meta-data android:value="YOUR_APP_KEY" android:name="UMENG_APPKEY"/>
     *           <meta-data android:value="Channel ID" android:name="UMENG_CHANNEL"/>
     *       </application>
     *   </manifest>
     * </pre>
     */
    fun init(
        app: Application,
        appKey: String? = null,
        channel: String? = null,
        secret: String = ""
    ) {
        appliation = app
        shareApi = UMShareAPI.get(app)
        setLogEnable()
        UMConfigure.init(app, appKey, channel, UMConfigure.DEVICE_TYPE_PHONE, secret);
    }

    /**
     * 设置组件化的Log开关, 默认根据当前 buildType 设置，如需查看LOG设置为true
     * 之后通过 logcat tag: UMLog 来查看日志
     */
    fun setLogEnable(enable: Boolean = BuildConfig.DEBUG) {
        UMConfigure.setLogEnabled(true);
    }

    fun setWeixinConfig(wxAppId: String, wxAppSecret: String) {
        PlatformConfig.setWeixin(wxAppId, wxAppSecret);
    }

    fun setAlipayConfig(key: String) {
        PlatformConfig.setAlipay(key);
    }

    // 设置各平台的 key 和 secretKey 示例代码
//    fun setPlatform() {
//        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
//        //豆瓣RENREN平台目前只能在服务器端配置
//        PlatformConfig.setSinaWeibo(
//            "3921700954",
//            "04b48b094faeb16683c32669824ebdad",
//            "http://sns.whalecloud.com"
//        );
//        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
//        PlatformConfig.setTwitter(
//            "3aIN7fuF685MuZ7jtXkQxalyi",
//            "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO"
//        );
//        PlatformConfig.setAlipay("2015111700822536");
//        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
//    }

    /**
     * [通过微信登录并获取用户资料](https://developer.umeng.com/docs/66632/detail/66639#h3-u83B7u53D6u7528u6237u8D44u6599)
     * @param listener 登录回调
     * */
    fun getWeixinPlatformInfo(
        act: Activity,
        listener: EmptyUMAuthListener = EmptyUMAuthListener()
    ) {
        getPlatformInfo(act, SHARE_MEDIA.WEIXIN, listener)
    }

    /**
     * [登录并获取用户资料](https://developer.umeng.com/docs/66632/detail/66639#h3-u83B7u53D6u7528u6237u8D44u6599)
     * @param listener 登录回调
     * @param platform 平台
     * */
    fun getPlatformInfo(
        act: Activity,
        platform: SHARE_MEDIA,
        listener: EmptyUMAuthListener = EmptyUMAuthListener()
    ) {
        shareApi.getPlatformInfo(act, platform, listener)
    }

}
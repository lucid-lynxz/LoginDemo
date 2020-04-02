# 友盟微信登录分享功能二次封装库
[官方集成文档](https://developer.umeng.com/docs/66632/detail/66639#h2-u624Bu52A8u96C6u62107)
导入友盟时,资源文件太多, 与主module容易混淆, 不易管理, 因此单独抽出一个库

## 初始化
在 application#onCreate() 中调用:
```kotlin
import android.app.Application
import org.lynxz.umeng_lib.UmengManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        with(UmengManager) {
            init(this@MyApplication)
            
            // 设置各平台的key和secretKey, 以下参数为示例,按实际修改
            setWeixinConfig("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
            setAlipayConfig("2015111700822536");
        }
    }
}
```

## 权限申请
已在 androidManifest.xml 中添加了如下权限, 请在主module中申请 READ_PHONE_STATE 等权限;

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="org.lynxz.umeng_lib">
    <!-- 必须的权限 -->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />

    <!-- 推荐的权限 -->
    <!-- 添加如下权限，以便使用更多的第三方SDK和更精准的统计数据 -->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
</manifest>
```


## Channel渠道的使用规范
每台设备仅记录首次安装激活的渠道，在其他渠道再次安装不会重复计量。 所以在测试不同的渠道的时候，请使用不同的设备来分别测试不要改变’UMENG_CHANNEL’。

## Channel渠道的命名规范
1.可以由英文字母、阿拉伯数字、下划线、中划线、空格、括号组成，可以含汉字以及其他明文字符，但是不建议使用中文命名，会出现乱码
2.首尾字符不可以为空格
3.不要使用纯数字作为渠道ID
4.最多256个字符
5.”unknown” 及其各种大小写形式，作为【友盟+】保留的字段，不可以作为渠道名
在您查看数据时，渠道会作为一个数据细分的维度
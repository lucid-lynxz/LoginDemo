# 第三方登录工具库
P.S. 友盟二次封装库 `umeng_wrapper` 使用了kotlin,请在调用方module中添加kotlin支持

## 集成操作

```gradle
// 1. 添加jcneter仓库
buildscript {
    repositories {
        google()
        jcenter()
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

// 2. 在主module中添加依赖:
apply plugin: 'com.android.application'

apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
apply plugin: 'kotlin-kapt'

android {
    defaultConfig {
        // 添加注解声明
        javaCompileOptions {
            annotationProcessorOptions {
                includeCompileClasspath = true
            }
        }
    }
}

dependencies {
    // 导入友盟微信登录分享相关依赖库
    implementation 'org.lynxz.umeng_wrapper:umeng_wrapper:1.0.1'

    // 导入自动生成指定空类注解
    implementation 'org.lynxz.third_generation:third_generation:1.0.0'
    kapt 'org.lynxz.third_generation:third_generation:1.0.0'
}

// 3. 在主module中任意类上添加 ThirdActivityAutoGenerator 注解,格式如下:
// 编译时, 会自动创建 {applicationId}.wxapi.WXEntryActivity 类
// 目前一个注解仅支持单个空类的创建
@ThirdActivityAutoGenerator(
    getApplicationId = BuildConfig.APPLICATION_ID,
    getSubPackageName = "wxapi",
    getTargetActivityName = "WXEntryActivity",
    getSupperClass = WXTemplateActivity::class
)
class MainActivity : AppCompatActivity() {
}

// 4. 在 applicaton 中进行友盟相关参数的初始化
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        with(UmengManager) {
            init(this@MyApplication,appKey = "your_umeng_appkey")

            // 设置各平台的key和secretKey, 以下参数为示例,按实际修改
            setWeixinConfig("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
            setAlipayConfig("2015111700822536");
        }
    }
}
```

## `umeng_wrapper`: 友盟微信登录分享功能二次封装库
[官方集成文档](https://developer.umeng.com/docs/66632/detail/66639#h2-u624Bu52A8u96C6u62107)
导入友盟时,资源文件太多, 与主module容易混淆, 不易管理, 因此单独抽出一个库

### 初始化
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

### 权限申请
已在 androidManifest.xml 中添加了如下权限, 请在主module中申请 READ_PHONE_STATE 等权限;

### androidManifest.xml回调Activity声明
已在 `umeng_wrapper/AndroidmMnifest.xml` 中声明了微信回调Activity, 使用时, 用户无需再次声明

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="org.lynxz.umeng_wrapper">
       <application>
        <!--   微信回调activity -->
        <activity
            android:name="${applicationId}.wxapi.WXEntryActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:exported="true"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
    </application>
</manifest>
```

## `third_generation`: 自动创建指定空类注解
部分第三方库需要特定activity,用于回调和app间通讯,如微信回调需要 WXCallbackActivity的特定子类: WXEntryActivity
本模块用于自动生成所需空类代码, 至于该空类的xml注册已在 `umeng_wrapper` 中添加过;

空类activity的绝对路径可分成三部分: {applicationId}.{subPackageName}.{targetActivityName}
并且将其继承的父类记为: {superClass}
<p>
比如微信登录功能:
1. subPackageName 为: wxapi
2. targetActivityName 为: WXEntryActivity
3. superClass 为: WXCallbackActivity
由此, 注解所需参数已一目了然,具体如下:

```java
@Target(ElementType.TYPE) // 声明注解作用范围是作用在类,接口，注解，枚举上
@Retention(RetentionPolicy.SOURCE) //声明注解的有效期为源码期
public @interface ThirdActivityAutoGenerator {
    /**
     * @return app包名
     */
    String getApplicationId();

    /**
     * @return 子包名, 位于 applicationId 包的下一级
     */
    String getSubPackageName();

    /**
     * @return 要生成的activity名称
     */
    String getTargetActivityName();

    /**
     * @return 生成类的父类
     */
    Class<?> getSupperClass() default Object.class;
}
```

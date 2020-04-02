package org.lynxz.login

import android.app.Application
//import org.lynxz.umeng_lib.UmengManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        with(UmengManager) {
//            init(this@MyApplication)
//
//            // 设置各平台的key和secretKey, 以下参数为示例,按实际修改
//            setWeixinConfig("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
//            setAlipayConfig("2015111700822536");
//        }
    }
}
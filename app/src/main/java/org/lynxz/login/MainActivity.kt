package org.lynxz.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.weixin.view.WXCallbackActivity
import org.lynxz.third_generation.annotation.ThirdActivityAutoGenerator
import org.lynxz.umeng_wrapper.UmengManager
import org.lynxz.umeng_wrapper.observer.EmptyUMAuthListener
import org.lynxz.umeng_wrapper.temple.WXTemplateActivity

@ThirdActivityAutoGenerator(
    getApplicationId = BuildConfig.APPLICATION_ID,
    getSubPackageName = "wxapi",
    getTargetActivityName = "WXEntryActivity",
    getSupperClass = WXTemplateActivity::class
)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun loginByWechat() {
        UmengManager.getWeixinPlatformInfo(this, object : EmptyUMAuthListener() {
            override fun onComplete(
                platform: SHARE_MEDIA?,
                action: Int,
                data: MutableMap<String, String>?
            ) {
                super.onComplete(platform, action, data)
            }
        })
    }
}

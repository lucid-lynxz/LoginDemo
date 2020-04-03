package org.lynxz.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.umeng.socialize.weixin.view.WXCallbackActivity
import org.lynxz.third_generation.annotation.ThirdActivityAutoGenerator
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
}

package org.lynxz.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.lynxz.third_generation.annotation.ThirdActivityAutoGenerator

@ThirdActivityAutoGenerator(
    getApplicationId = BuildConfig.APPLICATION_ID,
    getSubPackageName = "wxapi",
    getTargetActivityName = "WXEntryActivity",
    getSupperClass = AppCompatActivity::class
)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}

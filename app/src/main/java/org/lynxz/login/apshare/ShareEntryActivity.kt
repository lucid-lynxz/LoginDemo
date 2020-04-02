package org.lynxz.login.apshare

import android.app.Activity

/**
 * todo 支付宝回调activity,需要继承 ShareCallbackActivity
 *
 *  * 并需要在 AndroidManifest.xml 中配置如下
 *
 * <pre>
 *   <activity
 *      android:name=".apshare.ShareEntryActivity"
 *      android:configChanges="keyboardHidden|orientation|screenSize"
 *      android:exported="true"
 *      android:theme="@android:style/Theme.Translucent.NoTitleBar" />
 * </pre>
 * */
class ShareEntryActivity : Activity() {
}
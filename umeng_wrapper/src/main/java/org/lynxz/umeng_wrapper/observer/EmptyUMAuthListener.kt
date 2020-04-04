package org.lynxz.umeng_wrapper.observer

import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.bean.SHARE_MEDIA

/**
 * 文档: https://developer.umeng.com/docs/66632/detail/66639#h3-u83B7u53D6u7528u6237u8D44u6599
 * 友盟登录回调
 * */
open class EmptyUMAuthListener : UMAuthListener {
    /**
     * @desc 授权成功的回调
     * @param platform 平台名称
     * @param action 行为序号，开发者用不上
     * @param data 用户资料返回,相关字段含义及对应关系如下
     * <pre>
     *   key        QQ                  微信                新浪                 含义           备注
     *   uid        openid	            unionid	            id	                用户唯一标识	uid能否实现Android与iOS平台打通，目前QQ只能实现同APPID下用户ID匹配
     *   name       screen_name	        screen_name	        screen_name	        用户昵称
     *   gender     gender	            gender	            gender	            用户性别	    该字段会直接返回男女
     *   iconurl    profile_image_url	profile_image_url	profile_image_url	用户头像
     * </pre>
     */
    override fun onComplete(
        platform: SHARE_MEDIA?,
        action: Int,
        data: MutableMap<String, String>?
    ) {

    }

    /**
     * @desc 授权取消的回调
     * @param platform 平台名称
     * @param action 行为序号，开发者用不上
     */
    override fun onCancel(platform: SHARE_MEDIA?, action: Int) {
    }

    /**
     * @desc 授权失败的回调
     * @param platform 平台名称
     * @param action 行为序号，开发者用不上
     * @param t 错误原因
     */
    override fun onError(platform: SHARE_MEDIA?, action: Int, t: Throwable?) {
    }

    /**
     * @desc 授权开始的回调
     * @param platform 平台名称
     */
    override fun onStart(platform: SHARE_MEDIA?) {
    }
}
package com.glwlc.lulu.framework.Flow.loader.user;

import com.glwlc.lulu.framework.Flow.extension.CommonExtension;

/**
 * 用户状态搜索器, 用于spi扩展
 * 可扩展 redis, mysql, mongodb
 *
 * @Author: Gavin
 * @Date: 2019-03-05 14:26
 */
public interface UserStateSearcher extends CommonExtension, DefaultUserSearcher {

}

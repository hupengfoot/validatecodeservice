package com.dianping.validatecode.config;

import java.util.Map;

import com.dianping.validatecode.constant.ConfigKey;

public interface DynamicConfigable {
    /**
     * 配置变更回调函数
     * 
     * @param event
     *            配置变更事件
     */
    public void onConfigChanged(ConfigChangedEvent event);

    /**
     * <p>
     * 初始化前准备操作。
     * </p>
     * <p>
     * 需要往<tt>keyDefaultValueMap</tt>中放入本实现需要关注的配置key和value的默认值。
     * </p>
     * 
     * @param keyDefaultValueMap
     *            关注的动态配置项的key和value默认值Map
     */
    public void prepare(Map<ConfigKey, Object> keyDefaultValueMap);

    /**
     * <p>
     * 初始化
     * </p>
     * 
     * @param keyValueMap
     *            包含了配置项的key和初始化value(value没有初始值则使用<tt>prepare</tt>
     *            阶段传入的value默认值)
     */
    public void init(Map<ConfigKey, Object> keyValueMap);
}
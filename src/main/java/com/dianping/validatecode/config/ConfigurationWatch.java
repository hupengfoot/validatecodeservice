package com.dianping.validatecode.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dianping.lion.EnvZooKeeperConfig;
import com.dianping.lion.client.ConfigCache;
import com.dianping.lion.client.ConfigChange;
import com.dianping.lion.console.ZKClient;
import com.dianping.validatecode.constant.ConfigKey;
import com.dianping.validatecode.utils.MonitorUtils;


public class ConfigurationWatch {
    private static ConfigurationWatch instance          = new ConfigurationWatch();

    private Set<DynamicConfigable>    watchedConfigList = new HashSet<DynamicConfigable>();

    public static ConfigurationWatch getInstance() {
        return instance;
    }

    public void addWatchedConfig(DynamicConfigable config) {
        if (config != null) {
            watchedConfigList.add(config);
        }
    }

    public boolean init() {
        boolean flag = true;
        for (DynamicConfigable configable : watchedConfigList) {
            try {
                Map<ConfigKey, Object> keyValueMap = new HashMap<ConfigKey, Object>();
                configable.prepare(keyValueMap);
                for (Map.Entry<ConfigKey, Object> entry : keyValueMap.entrySet()) {
                    Object value = ConfigCache.getInstance(EnvZooKeeperConfig.getZKAddress()).getProperty(entry.getKey().value());
                    if (value == null) {
                        value = entry.getValue();
                    }
                    keyValueMap.put(entry.getKey(), value);
                }
                configable.init(keyValueMap);
            } catch (Exception e) {
                MonitorUtils.traceError("init Config failed.", e);
                flag = false;
            }
        }
        return flag;
    }

    public boolean start() {
        try {
            ConfigCache.getInstance().addChange(new ConfigChange() {

                public void onChange(String key, String value) {
                    if (ConfigKey.strValues().contains(key)) {
                        ConfigKey configkey = ConfigKey.getConfigKey(key);
                        if (configkey != null) {
                            ConfigChangedEvent event = new ConfigChangedEvent(configkey, value);
                            for (DynamicConfigable configable: watchedConfigList){
                                try{
                                    configable.onConfigChanged(event);
                                }catch(Exception e){
                                    MonitorUtils.traceError("Notify dynamic configurable failed.", e);
                                }
                            }
                        }
                    }
                }
            });
            return true;
        } catch (Exception e) {
            MonitorUtils.traceError("Add config watch failed.", e);
            return false;
        }
    }
}
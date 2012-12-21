package com.dianping.validatecode.config;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import com.dianping.validatecode.constant.ConfigKey;
import com.dianping.validatecode.utils.Pair;
import com.dianping.validatecode.utils.SerializationUtils;
import com.dianping.validatecode.utils.StringUtils;
import com.dianping.validatecode.config.DynamicConfigable;
import com.dianping.validatecode.config.Dumpable;
import com.google.gson.reflect.TypeToken;

public class PolicyConfig implements Dumpable, DynamicConfigable {

    private static final PolicyConfig                        instance                = new PolicyConfig();

    private AtomicReference<Set<String>>                     acceptIPAddressPrefix   = new AtomicReference<Set<String>>(
                                                                                             new HashSet<String>());
    private AtomicReference<Set<String>>                     acceptHttpMethod        = new AtomicReference<Set<String>>(
                                                                                             new HashSet<String>());
    private AtomicReference<Set<String>>                     denyUserAgent           = new AtomicReference<Set<String>>(
                                                                                             new HashSet<String>());
    private AtomicReference<Set<String>>                     denyUserAgentPrefix     = new AtomicReference<Set<String>>(
                                                                                             new HashSet<String>());
    private AtomicReference<Set<String>>                     denyIPAddress           = new AtomicReference<Set<String>>(
                                                                                             new HashSet<String>());
    private AtomicReference<Set<String>>                     denyIPAddressPrefix     = new AtomicReference<Set<String>>(
                                                                                             new HashSet<String>());
    private AtomicReference<Map<String, Date>>               denyIPAddressRate       = new AtomicReference<Map<String, Date>>(
                                                                                             new HashMap<String, Date>());
    private AtomicReference<Set<List<String>>>               denyUserAgentContainAnd = new AtomicReference<Set<List<String>>>(
                                                                                             new HashSet<List<String>>());
    private AtomicReference<Map<Pair<String, String>, Date>> denyIPVidRate           = new AtomicReference<Map<Pair<String, String>, Date>>(
                                                                                             new HashMap<Pair<String, String>, Date>());
    private AtomicReference<Map<String, Date>>               denyIPVidRateStr        = new AtomicReference<Map<String, Date>>(
                                                                                             new HashMap<String, Date>());
    private AtomicReference<Map<String, String>>             denyNOVisitorIDURL      = new AtomicReference<Map<String, String>>(
                                                                                             new HashMap<String, String>());

    private PolicyConfig() {

    }

    public static PolicyConfig getInstance() {
        return instance;
    }

    public Map<String, String> getDenyNOVisitorIDURL() {
        return denyNOVisitorIDURL == null ? null : denyNOVisitorIDURL.get();
    }

    public Map<Pair<String, String>, Date> getDenyIPVidRate() {
        return denyIPVidRate == null ? null : denyIPVidRate.get();
    }

    public Set<List<String>> getDenyUserAgentContainAnd() {
        return denyUserAgentContainAnd == null ? null : denyUserAgentContainAnd.get();
    }

    public Set<String> getAcceptIPAddressPrefix() {
        return acceptIPAddressPrefix == null ? null : acceptIPAddressPrefix.get();
    }

    public Set<String> getAcceptHttpMethod() {
        return acceptHttpMethod == null ? null : acceptHttpMethod.get();
    }

    public Set<String> getDenyUserAgent() {
        return denyUserAgent == null ? null : denyUserAgent.get();
    }

    public Set<String> getDenyUserAgentPrefix() {
        return denyUserAgentPrefix == null ? null : denyUserAgentPrefix.get();
    }

    public Set<String> getDenyIPAddress() {
        return denyIPAddress == null ? null : denyIPAddress.get();
    }

    public Set<String> getDenyIPAddressPrefix() {
        return denyIPAddressPrefix == null ? null : denyIPAddressPrefix.get();
    }

    public Map<String, Date> getDenyIPAddressRate() {
        return denyIPAddressRate == null ? null : denyIPAddressRate.get();
    }

    public void dump(Map<String, Object> statsInfo) {
        if (statsInfo != null) {
            statsInfo.put(ConfigKey.POLICY_ACCEPT_IP_PREFIX.value(), acceptIPAddressPrefix == null ? null
                    : acceptIPAddressPrefix.get());
            statsInfo.put(ConfigKey.POLICY_ACCEPT_HTTP_METHOD.value(), acceptHttpMethod == null ? null
                    : acceptHttpMethod.get());
            statsInfo.put(ConfigKey.POLICY_DENY_USERAGENT.value(), denyUserAgent == null ? null : denyUserAgent.get());
            statsInfo.put(ConfigKey.POLICY_DENY_USERAGENT_PREFIX.value(), denyUserAgentPrefix == null ? null
                    : denyUserAgentPrefix.get());
            statsInfo.put(ConfigKey.POLICY_DENY_IP.value(), denyIPAddress == null ? null : denyIPAddress.get());
            statsInfo.put(ConfigKey.POLICY_DENY_IP_PREFIX.value(), denyIPAddressPrefix == null ? null
                    : denyIPAddressPrefix.get());
            statsInfo.put(ConfigKey.POLICY_DENY_IP_RATE.value(), denyIPAddressRate == null ? null : denyIPAddressRate
                    .get());
            statsInfo.put(ConfigKey.POLICY_DENY_USERAGENT_CONTAIN_AND.value(), denyUserAgentContainAnd == null ? null
                    : denyUserAgentContainAnd.get());
            statsInfo.put(ConfigKey.POLICY_DENY_IP_VID_RATE.value(), denyIPVidRateStr == null ? null : denyIPVidRateStr
                    .get());
            statsInfo.put(ConfigKey.POLICY_DENY_NO_VISITORID_URL.value(), denyNOVisitorIDURL == null ? null
                    : denyNOVisitorIDURL.get());

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dianping.alpaca.client.config.DynamicConfigable#onConfigChanged(com
     * .dianping.alpaca.client.config.ConfigChangedEvent)
     */
    public void onConfigChanged(ConfigChangedEvent event) {
        if (event == null) {
            return;
        }

        propertySet(event.getKey(), event.getValue());
    }

    @SuppressWarnings("unchecked")
    private void propertySet(ConfigKey key, Object value) {
        switch (key) {
            case POLICY_ACCEPT_HTTP_METHOD:
                if (value instanceof String) {
                    Set<String> newAcceptHttpMethod = SerializationUtils.deSerialize((String) value,
                            new TypeToken<Set<String>>() {
                            }.getType());
                    if (newAcceptHttpMethod != null) {
                        acceptHttpMethod.set(newAcceptHttpMethod);
                    }
                } else if (value instanceof Set) {
                    acceptHttpMethod.set((Set<String>) value);
                }

                break;
            case POLICY_ACCEPT_IP_PREFIX:
                if (value instanceof String) {
                    Set<String> newAcceptIPPrefix = SerializationUtils.deSerialize((String) value,
                            new TypeToken<Set<String>>() {
                            }.getType());
                    if (newAcceptIPPrefix != null) {
                        acceptIPAddressPrefix.set(newAcceptIPPrefix);
                    }
                } else if (value instanceof Set) {
                    acceptIPAddressPrefix.set((Set<String>) value);
                }
                break;
            case POLICY_DENY_IP:
                if (value instanceof String) {
                    Set<String> newDenyIP = SerializationUtils.deSerialize((String) value,
                            new TypeToken<Set<String>>() {
                            }.getType());
                    if (newDenyIP != null) {
                        denyIPAddress.set(newDenyIP);
                    }
                } else if (value instanceof Set) {
                    denyIPAddress.set((Set<String>) value);
                }
                break;
            case POLICY_DENY_IP_PREFIX:
                if (value instanceof String) {
                    Set<String> newDenyIPPrefix = SerializationUtils.deSerialize((String) value,
                            new TypeToken<Set<String>>() {
                            }.getType());
                    if (newDenyIPPrefix != null) {
                        denyIPAddressPrefix.set(newDenyIPPrefix);
                    }
                } else if (value instanceof Set) {
                    denyIPAddressPrefix.set((Set<String>) value);
                }
                break;
            case POLICY_DENY_IP_RATE:
                if (value instanceof String) {
                    Map<String, Date> newDenyIPRate = SerializationUtils.deSerialize((String) value,
                            new TypeToken<Map<String, Date>>() {
                            }.getType(), "yyyy-MM-dd HH:mm:ss");
                    if (newDenyIPRate != null) {
                        denyIPAddressRate.set(newDenyIPRate);
                    }
                } else if (value instanceof Map) {
                    denyIPAddressRate.set((Map<String, Date>) value);
                }
                break;
            case POLICY_DENY_IP_VID_RATE:
                if (value instanceof String) {
                    Map<String, Date> newDenyIPVidRateStr = SerializationUtils.deSerialize((String) value,
                            new TypeToken<Map<String, Date>>() {
                            }.getType(), "yyyy-MM-dd HH:mm:ss");
                    if (newDenyIPVidRateStr != null) {
                        Map<Pair<String, String>, Date> newDenyIPVidRate = parseDenyIPVidRateStrMap(newDenyIPVidRateStr);

                        denyIPVidRate.set(newDenyIPVidRate);
                        denyIPVidRateStr.set(newDenyIPVidRateStr);
                    }
                } else if (value instanceof Map) {
                    denyIPVidRateStr.set((Map<String, Date>) value);
                    Map<Pair<String, String>, Date> newDenyIPVidRate = parseDenyIPVidRateStrMap(denyIPVidRateStr.get());

                    denyIPVidRate.set(newDenyIPVidRate);
                }
                break;
            case POLICY_DENY_NO_VISITORID_URL_NEW:
                if (value instanceof String) {
                    Map<String, String> newDenyNoVisitorUrl = SerializationUtils.deSerialize((String) value,
                            new TypeToken<Map<String, String>>() {
                            }.getType());
                    if (newDenyNoVisitorUrl != null) {
                        denyNOVisitorIDURL.set(newDenyNoVisitorUrl);
                    }
                } else if (value instanceof Map) {
                    denyNOVisitorIDURL.set((Map<String, String>) value);
                }
                break;
            case POLICY_DENY_USERAGENT:
                if (value instanceof String) {
                    Set<String> newDenyUserAgent = SerializationUtils.deSerialize((String) value,
                            new TypeToken<Set<String>>() {
                            }.getType());
                    if (newDenyUserAgent != null) {
                        denyUserAgent.set(newDenyUserAgent);
                    }
                } else if (value instanceof Set) {
                    denyUserAgent.set((Set<String>) value);
                }
                break;
            case POLICY_DENY_USERAGENT_PREFIX:
                if (value instanceof String) {
                    Set<String> newDenyUserAgentPrefix = SerializationUtils.deSerialize((String) value,
                            new TypeToken<Set<String>>() {
                            }.getType());
                    if (newDenyUserAgentPrefix != null) {
                        denyUserAgentPrefix.set(newDenyUserAgentPrefix);
                    }
                } else if (value instanceof Set) {
                    denyUserAgentPrefix.set((Set<String>) value);
                }
                break;
            case POLICY_DENY_USERAGENT_CONTAIN_AND:
                if (value instanceof String) {
                    Set<List<String>> newDenyUserAgentContainAnd = SerializationUtils.deSerialize((String) value,
                            new TypeToken<Set<List<String>>>() {
                            }.getType());
                    if (newDenyUserAgentContainAnd != null) {
                        denyUserAgentContainAnd.set(newDenyUserAgentContainAnd);
                    }
                } else if (value instanceof Set) {
                    denyUserAgentContainAnd.set((Set<List<String>>) value);
                }
                break;
            default:
                break;
        }
    }

    private Map<Pair<String, String>, Date> parseDenyIPVidRateStrMap(Map<String, Date> value) {
        Map<Pair<String, String>, Date> newDenyIPVidRate = new HashMap<Pair<String, String>, Date>();
        for (Map.Entry<String, Date> entry : value.entrySet()) {
            if (!StringUtils.isBlank(entry.getKey())) {
                String[] split = entry.getKey().split(",");
                if (split != null && split.length == 2) {
                    newDenyIPVidRate.put(new Pair<String, String>(split[0], split[1]), entry.getValue());
                }
            }
        }
        return newDenyIPVidRate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dianping.alpaca.client.config.DynamicConfigable#init(java.util.Map)
     */
    public void init(Map<ConfigKey, Object> keyValueMap) {
        if (keyValueMap != null) {
            for (Map.Entry<ConfigKey, Object> entry : keyValueMap.entrySet()) {
                propertySet(entry.getKey(), entry.getValue());
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dianping.alpaca.client.config.DynamicConfigable#prepare(java.util
     * .Map)
     */
    public void prepare(Map<ConfigKey, Object> keyDefaultValueMap) {
        if (keyDefaultValueMap != null) {
            keyDefaultValueMap.put(ConfigKey.POLICY_ACCEPT_HTTP_METHOD, new HashSet<String>());
            keyDefaultValueMap.put(ConfigKey.POLICY_ACCEPT_IP_PREFIX, new HashSet<String>());
            keyDefaultValueMap.put(ConfigKey.POLICY_DENY_IP, new HashSet<String>());
            keyDefaultValueMap.put(ConfigKey.POLICY_DENY_IP_PREFIX, new HashSet<String>());
            keyDefaultValueMap.put(ConfigKey.POLICY_DENY_IP_RATE, new HashMap<String, Date>());
            keyDefaultValueMap.put(ConfigKey.POLICY_DENY_USERAGENT, new HashSet<String>());
            keyDefaultValueMap.put(ConfigKey.POLICY_DENY_USERAGENT_PREFIX, new HashSet<String>());
            keyDefaultValueMap.put(ConfigKey.POLICY_DENY_USERAGENT_CONTAIN_AND, new HashSet<List<String>>());
            keyDefaultValueMap.put(ConfigKey.POLICY_DENY_IP_VID_RATE, new HashMap<Pair<String, String>, Date>());
            keyDefaultValueMap.put(ConfigKey.POLICY_DENY_NO_VISITORID_URL_NEW, new HashMap<String, String>());
        }

    }
}

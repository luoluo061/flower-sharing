package org.dromara.common.mybatis.handler;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实现 查询结果 column1 为 key column2 为 value
 * @param <K>
 * @param <V>
 */
public class MapResultHandler<K,V> implements ResultHandler<Map<K,V>> {
    private final Map<K,V> mappedResults = new ConcurrentHashMap<>();

    @Override
    public void handleResult(ResultContext context) {
        Map map = (Map) context.getResultObject();
        mappedResults.put((K)map.get("key"), (V)map.get("value"));
    }

    public Map<K,V> getMappedResults() {
        return mappedResults;
    }
}

package net.qqxh.sunflow.exception;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Copyright (C), 2019-2020, sunflow开发团队
 * 实体类存在异常
 *
 * @author jwy
 * @fileName: EntityExistException
 * @date: 23/05/2019 14:45
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class EntityExistException extends RuntimeException {

    public EntityExistException(Class clazz, Object... saveBodyParamsMap) {
        super(EntityExistException.generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, saveBodyParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> saveBodyParams) {
        return StringUtils.capitalize(entity) +
                " 已存在 " +
                saveBodyParams;
    }

    private static <K, V> Map<K, V> toMap(
            Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1) {
            throw new IllegalArgumentException("Invalid entries");
        }
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                        Map::putAll);
    }
}
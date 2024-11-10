package com.zbn.zbnbackendcommon.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL 工具
 *
 * @author zbn
 * @version 1.0
 * @date 2024/9/25
 */
public class SqlUtils {

    /**
     * 校验排序字段是否合法（防止 SQL 注入）
     *
     * @param sortField
     * @return
     */
    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}

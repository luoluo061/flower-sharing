package org.dromara.flower.constant;

/**
 * 分布式锁key
 *
 * @author: chazonglin
 * @date: 2024/12/30 10:59
 */
public interface LockKeyString {

    /**
     * 生成课程编号锁key
     */
    String COURSES_CODE_LOCK_KEY = "COURSES_CODE_LOCK_KEY";

    /**
     * 生成课程编号redis字符串key 例子：COURSES_CODE_REDIS_KEY_20241230
     */
    String COURSES_CODE_REDIS_KEY = "COURSES_CODE_REDIS_KEY";

    /**
     * 生成课程编号锁key
     */
    String COURSES_CODE = "CV";

}

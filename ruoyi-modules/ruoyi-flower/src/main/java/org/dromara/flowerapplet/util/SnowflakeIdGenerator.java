package org.dromara.flowerapplet.util;


import org.springframework.beans.factory.annotation.Value;

public class SnowflakeIdGenerator {
    @Value("${snowflake.dataCenterId}")
    private static long dataCenterId;// 数据中心ID（可根据实际情况配置）
    @Value("${snowflake.machineId}") // 机器ID（可根据实际情况配置）
    private static long machineId;
    // 起始的时间戳，可以根据实际情况调整
    private static final long START_TIMESTAMP = 1609459200000L; // 2021-01-01 00:00:00

    // 每部分占用的位数
    private static final long SEQUENCE_BIT = 10; // 序列号占用的位数
    private static final long MACHINE_BIT = 1;   // 机器标识占用的位数
    private static final long DATA_CENTER_BIT = 1; // 数据中心标识占用的位数

    // 每部分的最大值
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    private static final long MAX_MACHINE_ID = ~(-1L << MACHINE_BIT);
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_BIT);

    // 每部分向左的位移
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    private static final long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    /*// 数据中心ID（可根据实际情况配置）
    private final long dataCenterId;
    // 机器ID（可根据实际情况配置）
    private final long machineId;*/
    // 序列号
    private long sequence = 0L;
    // 上次生成ID的时间戳
    private long lastTimestamp = -1L;

    /**
     * 初始化 SnowflakeIdGenerator 对象，并设置数据中心标识和机器标识
     * @param dataCenterId 数据中心标识
     * @param machineId 机器标识
     */
    public SnowflakeIdGenerator(long dataCenterId , long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) { // 进行合法性检查，确保传入的数据中心标识和机器标识在合理范围内，否则抛出 IllegalArgumentException。
            throw new IllegalArgumentException("Data center ID can't be greater than " + MAX_DATA_CENTER_ID + " or less than 0");
        }
        if (machineId > MAX_MACHINE_ID || machineId < 0) {
            throw new IllegalArgumentException("Machine ID can't be greater than " + MAX_MACHINE_ID + " or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 生成雪花算法的唯一ID
     * @return 最终的64位ID
     */
    //确保在多线程环境下生成的ID是唯一的，防止并发冲突。如果您的应用是单线程或者在多线程环境下并发要求不高，可以适当简化同步逻辑。
    public synchronized long generateId() {
        long currentTimestamp = System.currentTimeMillis(); // 获取当前时间戳 currentTimestamp，以毫秒为单位。

        if (currentTimestamp < lastTimestamp) { // 检查当前时间戳是否小于上一次生成ID的时间戳 lastTimestamp，如果是，说明发生了时钟回拨，抛出 RuntimeException。
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID for " + (lastTimestamp - currentTimestamp) + " milliseconds.");
        }

        if (currentTimestamp == lastTimestamp) { // 如果当前时间戳与上一次时间戳相等，则递增序列号 sequence，并通过位运算确保序列号不超过最大值。如果序列号归零，表示在同一毫秒内生成的ID数量超过限制，调用 waitNextMillis 方法等待下一毫秒。
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                currentTimestamp = waitNextMillis(currentTimestamp);
            }
        } else {
            sequence = 0L; // 如果当前时间戳大于上一次时间戳，将序列号重置为零。
        }

        lastTimestamp = currentTimestamp;

       /* System.out.println( ((currentTimestamp) << TIMESTAMP_LEFT) |
                (dataCenterId << DATA_CENTER_LEFT) |
                (machineId << MACHINE_LEFT) |
                sequence);*/
        // 将当前时间戳、数据中心标识、机器标识和序列号按照雪花算法的规则组合起来，生成最终的64位ID。
        return ((currentTimestamp - START_TIMESTAMP) << TIMESTAMP_LEFT) |
            (dataCenterId << DATA_CENTER_LEFT) |
            (machineId << MACHINE_LEFT) |
            sequence;
    }

    /**
     * 时钟回拨策略：发生时钟回拨时等待下一个合适的时间戳。
     * @param currentTimestamp 方法被调用时的当前时间戳
     * @return 返回新的时间戳，以确保生成的ID是在递增的时间戳基础上生成的。
     */
    private long waitNextMillis(long currentTimestamp) {
        long timestamp = System.currentTimeMillis();
        // 判断 timestamp 是否小于等于上一次生成ID的时间戳 lastTimestamp。如果是，说明当前时间戳仍然没有超过上一次生成ID的时间戳，继续在循环内等待。
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis(); // 不断更新 timestamp 为当前时间戳，直到 timestamp 大于 lastTimestamp。
        }
        return timestamp; // 返回新的时间戳，以确保生成的ID是在递增的时间戳基础上生成的。
    }

    public synchronized static long getId() {
        SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(dataCenterId, machineId);
        return idGenerator.generateId();
    }

    public static void main(String[] args) {
        // 根据情况决定
        long dataCenterId = 1L;  // 数据中心标识
        long machineId = 1L;     // 机器标识

        SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(dataCenterId, machineId);
        // 使用 idGenerator 生成唯一ID
        long uniqueId = idGenerator.generateId();

        System.out.print(String.valueOf(uniqueId));

    }
}

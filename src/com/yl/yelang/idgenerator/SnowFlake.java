package com.yl.yelang.idgenerator;

/**
 * twitter的SnowFlake算法
 * <p>
 * ID(64位) = 0(符号位) + 41(时间戳差值) + 10(节点编号) + 12(毫秒内sequenceId)
 * <p>
 * Created by wangxinxing on 2017/2/23.
 */
public class SnowFlake {

    //机器节点位数
    private final long nodeBits       = 10L;
    //最大节点数
    private final long maxNode        = -1L ^ (-1L ^ nodeBits);
    //毫秒内sequenceID位数
    private final long sequenceBits   = 12L;
    //毫秒内最大sequenceID支持数
    private final long maxSequence    = -1L ^ (-1L ^ sequenceBits);
    //开始时间
    private final long startTimestamp = 1487836247225L;

    private long sequence      = 0L;
    private long node          = 0l;
    private long lastTimestamp = 0l;

    private static SnowFlake snowFlake;

    public SnowFlake(long node) {
        this.node = node;
    }

    public synchronized long generateID() {
        long timestamp = System.currentTimeMillis();
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) % maxSequence;
            if (sequence == 0) {
                timestamp = nextMill();
            }
        } else {
            sequence = 0l;
        }
        lastTimestamp = timestamp;
        long id = (timestamp - startTimestamp) << 22L
                | node << nodeBits
                | sequence;
        return id;
    }

    private long nextMill() {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
    
    public static void main(String[] args) {
    	SnowFlake snowFlake = new SnowFlake(20l);
        System.out.println(snowFlake.generateID());
        System.out.println(snowFlake.generateID());
        System.out.println(snowFlake.generateID());
        System.out.println(snowFlake.generateID());
	}
}
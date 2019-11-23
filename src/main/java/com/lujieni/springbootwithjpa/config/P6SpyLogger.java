package com.lujieni.springbootwithjpa.config;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * @Auther ljn
 * @Date 2019/11/23
 * p6spy打印日志输出格式修改类s
 *
 */
public class P6SpyLogger implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category,
                                String prepared, String sql, String url) {
        return (sql!=null && sql.trim().length()>0)? " Consume Time：" + elapsed + " ms " + now +
                "\n Execute SQL：" + sql.replaceAll("[\\s]+", " ") + "\n" : null;
    }
}

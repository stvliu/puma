package com.dianping.puma.alarm.service;

import com.dianping.puma.alarm.model.strategy.AlarmStrategy;

/**
 * Created by xiaotian.li on 16/3/16.
 * Email: lixiaotian07@gmail.com
 */
public interface ClientAlarmStrategyService {

    AlarmStrategy find(String clientName);
}
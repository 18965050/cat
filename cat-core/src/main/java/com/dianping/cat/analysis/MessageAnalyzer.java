/*
 * Copyright (c) 2011-2018, Meituan Dianping. All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dianping.cat.analysis;

import com.dianping.cat.message.spi.MessageQueue;
import com.dianping.cat.message.spi.MessageTree;
import com.dianping.cat.report.ReportManager;

/**
 * 消息分析器(Business, Transaction, Cross, Event, Problem, HeartBeat等等)
 */
public interface MessageAnalyzer {

	public boolean isEligable(MessageTree tree);

	public void analyze(MessageQueue queue);

	public void destroy();

	public void doCheckpoint(boolean atEnd);

	public long getStartTime();

	public void initialize(long startTime, long duration, long extraTime);

	/**
	 * <pre>
	 * 根据此值创建此名称Analyzer的个数
	 * 消息上报后, 根据domain的hash值% count 来获取对应的analyzer进行上报处理
	 * 如果忙不过来, 再用此名称的后个analyzer处理上报数据. 这样可以:
	 * 1. 不同domain上报的数据处理的Analyzer不是同一个Analyzer
	 * 2. 同一个domain上报的数据尽量同一个Analyzer处理, 忙不过来再用下一个处理
	 * </pre>
	 */

	public int getAnanlyzerCount(String name);

	public void setIndex(int index);

	public ReportManager<?> getReportManager();
}

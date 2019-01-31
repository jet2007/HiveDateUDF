/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jet.hive.udf.date;

import org.apache.hive.pdk.HivePdkUnitTest;
import org.apache.hive.pdk.HivePdkUnitTests;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import com.jet.utils.date.DateUtils;

/**
 * <code>DateDeltaFormat(date,offsets,format)</code>. 
 * 仿python relativedelta实现日期相加操作,返回日期String
 *  
 */

@Description(name = "DateDelta",
    value = "_FUNC_(date,offsets,format) - 仿python relativedelta实现日期相加操作,返回日期String",
    extended = "Params:\n"
    		+ "	> date: String类型,支持yyyyMMddHHmmss和yyyy-MM-dd HH:mm:ss\n"
    		+ "	> offsets: delta日期的单位与长度,分隔符分别为逗号与等号\n"
    		+ "		单位支持有year,month,day,hour,minute,second(或y,m,d,h,n,s)\n"
    		+ "		长度支持+N,-N,N\n"
    		+ "		示例： offsetsStr：'year=+2,month=8,day=-16'  年份加2，月份为8，日期减16\n"
    		+ "	> format: 输出的日期格式，如yyyyMMddHHmmss和yyyy-MM-dd HH:mm:ss\n"
    + "Example:\n"
    + "  > SELECT DateDeltaFormat('20180102030405','day=+3','yyyyMMddHHmmss') FROM src LIMIT 1;\n" + "  '20180105030405'\n"
    + "  > SELECT DateDeltaFormat('20180102','day=+3','yyyyMMddHHmmss') FROM src LIMIT 1;\n" + "  '20180105000000'\n"
    )
@HivePdkUnitTests(
	setup = "", cleanup = "",
    cases = {
      @HivePdkUnitTest(
        query = "SELECT '20180102030405','day=+3','yyyyMMddHHmmss' FROM onerow;",
        result = "20180105030405"),
    }
  )


public class DateDeltaFormat extends UDF {
	private Text result = new Text();
	
	public Text evaluate(Text dateText,Text offsetsText,Text formatText) {
		String re = DateUtils.dateDeltaStr(dateText.toString(),offsetsText.toString(),formatText.toString());
		result.set(re);
		return result;
	}
}

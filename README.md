# Hive Date UDF

## About
User defined functions for Hive By Jet

Date/datetime delta calculation In a easy way! 

Get any day you wanted!

## License
[Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

## Quickstart
```mysql
1.download jet-hive-date-udf-x.jar(x>=0.2) or compile jar using source code


2.put jar to hdfs(Or Local file)
hadoop fs -put jet-hive-date-udf-0.2.jar 'hdfs:///tmp/hive' 

3.create function 
create temporary function DateDelta
  as 'com.jet.hive.udf.date.DateDelta' 
  using jar 'hdfs:///tmp/hive/jet-hive-date-udf-0.2.jar';
  
  create temporary function CurrentDateTimeFormatDelta
  as 'com.jet.hive.udf.date.CurrentDateTimeFormatDelta' 
  using jar 'hdfs:///tmp/hive/jet-hive-date-udf-0.2.jar';
  
    create temporary function CurrentDateDelta
  as 'com.jet.hive.udf.date.CurrentDateDelta' 
  using jar 'hdfs:///tmp/hive/jet-hive-date-udf-0.2.jar';
  
      create temporary function CurrentDateDelta2
  as 'com.jet.hive.udf.date.CurrentDateDelta2' 
  using jar 'hdfs:///tmp/hive/jet-hive-date-udf-0.2.jar';
  
      create temporary function CurrentDateTimeDelta
  as 'com.jet.hive.udf.date.CurrentDateTimeDelta' 
  using jar 'hdfs:///tmp/hive/jet-hive-date-udf-0.2.jar';
  
        create temporary function CurrentDateTimeDelta2
  as 'com.jet.hive.udf.date.CurrentDateTimeDelta2' 
  using jar 'hdfs:///tmp/hive/jet-hive-date-udf-0.2.jar';
```




## Usage of Hive UDFs

### DateDelta

- DateDelta(date,offsets [,format]):  
  - Desc: As python relativedelta function, get the date/datetime delta calculation in a easy way! 
    - With this function, you can get any **date/datetime** wanted,  ex. the end day of this month, three months ago, 90 days ago...
    - Include year, month, day , hour, minute, second calculation(add, sub, and set)
  - Params:
    - date: the input date/datetime String
      - support: "yyyyMMdd, yyyy-MM-dd, yyyyMMddHHmmss, yyyy-MM-dd HH:mm:ss"
      - demo:  '20180102','20180102030405','2018-01-02 03:04:05'
    - offsets: delta String, Format M1=D1,M2=D2,...... , Separter by ',' and '='
      - M: year,month,day,hour,minute,second(Or y,m,d,h,n,s); Or week/w:day of week(monday is 1st day of week)
      - D: +N,-N,N(N is a Number); when M:week/w then D:N work,bug +N/-N is not working(exg, w=1);
      - demo: year=+2,month=8,day=-16,week=1
      - Sepecial Way(date cal): DateDelta('20180102','+3')==DateDelta('20180102','day=+3'),  DateDelta('20180102','3')=DateDelta('20180102','day=3');
    - \[Format\]: Option, Date/Datetime Format, Exg."yyyyMMdd, yyyy-MM-dd, yyyyMMddHHmmss, yyyy-MM-dd HH:mm:ss"
      - If this para is blank, the output value format likes the input date/datetime string format, see demo
- DEMO:

| DESC                          | CODE                                                         | RESULT              |
| ----------------------------- | ------------------------------------------------------------ | ------------------- |
| day +3                        | SELECT DateDelta('20180102','+3')                            | 20180105            |
| day +3                        | SELECT DateDelta('20180102','day=+3')                        | 20180105            |
| day +3                        | SELECT DateDelta('2018-01-02','day=+3')                      | 2018-01-05          |
| datetime +3day                | SELECT DateDelta('20180102030405','day=+3')                  | 20180105030405      |
| datetime +3day                | SELECT DateDelta('2018-01-02 03:04:05','day=+3')             | 2018-01-05 03:04:05 |
| end day of this year          | SELECT DateDelta('2018-09-06','year=+1,month=1,day=1,day=-1') | 2018-12-31          |
| end day of last month         | SELECT DateDelta('20180906','day=1,day=-1')                  | 20180831            |
| end day of this year          | SELECT DateDelta('2018-09-06','y=+1,m=1,d=1,d=-1')           | 2018-12-31          |
| Monday of week                | SELECT DateDelta('2020-12-17','w=1')                         | 2020-12-14          |
| end day of last month(Format) | SELECT DateDelta('2018-09-06','day=1,day=-1','<u>**yyyyMMdd**</u>') | 20180831            |



### CurrentDateTimeFormatDelta

- CurrentDateTimeFormatDelta(offsets [,format]):  

  - Desc: Base on System Current Time, get the date/datetime delta calculation in a easy way! 
    - With this function, you can get any date/datetime wanted,  ex. the end day of this month, three months ago, 90 days ago...
    - Include year, month, day , hour, minute, second calculation(add, sub, and set)
  - Params: offsets [,format]
    - see DateDelta's paras above


- DEMO( current time =[2019-02-01 11:06:31])

| DESC     | CODE                                                         | RESULT         |
| -------- | ------------------------------------------------------------ | -------------- |
| now+3day | SELECT CurrentDateTimeFormatDelta('+3','yyyyMMdd')           | 20190204       |
| now+3day | SELECT CurrentDateTimeFormatDelta('day=+3','yyyyMMddHHmmss') | 20190204110631 |
|          |                                                              |                |



### CurrentDateDelta

- CurrentDateDelta(offsets):  
  - Desc: Base on System Current Time, get the **date** delta calculation in a easy way! 
  - The output date value format is yyyy-MM-dd , equals CurrentDateTimeFormatDelta(offsets,'<u>yyyy-MM-dd</u>')

  - Params: offsets
    - see DateDelta's paras above

- DEMO( current time =[2019-02-01 11:06:31])

| DESC                  | CODE                                       | RESULT     |
| --------------------- | ------------------------------------------ | ---------- |
| now+3day              | SELECT CurrentDateDelta('+3')              | 2019-02-04 |
| now+3day              | SELECT CurrentDateDelta('day=+3')          | 2019-02-04 |
| end day of this month | SELECT CurrentDateDelta('month=+1,day=-1') | 2019-02-28 |



### CurrentDateDelta2

- CurrentDateDelta2(offsets):  
  - Desc: Base on System Current Time, get the **date** delta calculation in a easy way! 
  - The output date value format is yyyyMMdd , Equals CurrentDateTimeFormatDelta(offsets,'<u>yyyyMMdd</u>')
  - Params: offsets
    - see DateDelta's paras above
    
- DEMO( current time =[2019-02-01 11:06:31])

| DESC                  | CODE                                        | RESULT   |
| --------------------- | ------------------------------------------- | -------- |
| now+3day              | SELECT CurrentDateDelta2('+3')              | 20190204 |
| now+3day              | SELECT CurrentDateDelta2('day=+3')          | 20190204 |
| end day of this month | SELECT CurrentDateDelta2('month=+1,day=-1') | 20190228 |





### CurrentDateTimeDelta

- CurrentDateDelta(offsets):  
  - Desc: Base on System Current Time, get the **datetime** delta calculation in a easy way! 
  - The output date value format is \[yyyy-MM-dd hh:mm:ss\], Equals CurrentDateTimeFormatDelta(offsets,'<u>yyyy-MM-dd</u> hh:mm:ss')
  - Params: offsets
    - see DateDelta's paras above
    
- DEMO( current time =[2019-02-01 11:06:31])

| DESC                  | CODE                                           | RESULT              |
| --------------------- | ---------------------------------------------- | ------------------- |
| now+3day              | SELECT CurrentDateTimeDelta('+3')              | 2019-02-04 03:04:05 |
| now+3day              | SELECT CurrentDateTimeDelta('day=+3')          | 2019-02-04 03:04:05 |
| end day of this month | SELECT CurrentDateTimeDelta('month=+1,day=-1') | 2019-02-28 03:04:05 |



### CurrentDateTimeDelta2

- CurrentDateDelta(offsets):  
  - Desc: Base on System Current Time, get the **datetime** delta calculation in a easy way! 
  - The output date value format is yyyyMMddhhmmss, Equals CurrentDateTimeFormatDelta(offsets,'<u>yyyyMMddhhmmss</u>')
  - Params: offsets
    - see DateDelta's paras above

- DEMO( current time =[2019-02-01 11:06:31])

| DESC                  | CODE                                            | RESULT         |
| --------------------- | ----------------------------------------------- | -------------- |
| now+3day              | SELECT CurrentDateTimeDelta2('+3')              | 20190204030405 |
| now+3day              | SELECT CurrentDateTimeDelta2('day=+3')          | 20190204030405 |
| end day of this month | SELECT CurrentDateTimeDelta2('month=+1,day=-1') | 20190228030405 |









## References

* [Apache Hive](http://hive.apache.org/)



## Note

* 20201217 add day of week 


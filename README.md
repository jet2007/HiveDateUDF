# Hive Date UDF

## About
User defined functions for Hive By Jet

Date/datetime delta calculation In a easy way! 

Get any day you wanted!

## License
[Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

## Quickstart
    1.download jet-hive-date-udf-0.1.jar
    
    2.put jar to hdfs
    hadoop fs -put jet-hive-date-udf-0.1.jar 'hdfs:///tmp/hive' 
    
    3.create function 
    create temporary function DateDelta
      as 'com.jet.hive.udf.date.DateDelta' 
      using jar 'hdfs:///tmp/hive/jet-hive-date-udf-0.1.jar';
    create temporary function DateDeltaFormat
      as 'com.jet.hive.udf.date.DateDeltaFormat' 
      using jar 'hdfs:///tmp/hive/jet-hive-date-udf-0.1.jar';
    



## Usage of Hive UDFs

### DateDelta

- DateDelta(date,offsets):  
  - Desc: As python relativedelta function, get the date/datetime delta calculation in a easy way! 
    - With this function, you can get any date/datetime wanted,  ex. the end day of this month, three months ago, 90 days ago...
    - Include year, month, day , hour, minute, second calculation(add, sub, and set)
  - Params:
    - date: date/datetime String
      - support: "yyyyMMdd, yyyy-MM-dd, yyyyMMddHHmmss, yyyy-MM-dd HH:mm:ss"
      - demo:  '20180102','20180102030405','2018-01-02 03:04:05'
    - offsets: delta String, Format M1=D1,M2=D2,...... , Separter by ',' and '='
      - M: year,month,day,hour,minute,second(Or y,m,d,h,n,s)
      - D: +N,-N,N(N is a Number)
      - demo: year=+2,month=8,day=-16
      - Sepecial Way(date cal): DateDelta('20180102','+3')==DateDelta('20180102','day=+3'),  DateDelta('20180102','3')=DateDelta('20180102','day=3');
- DEMO:

| DESC                  | CODE                                                         | RESULT              |
| --------------------- | ------------------------------------------------------------ | ------------------- |
| day +3                | SELECT DateDelta('20180102','+3')                            | 20180105            |
| day +3                | SELECT DateDelta('20180102','day=+3')                        | 20180105            |
| day +3                | SELECT DateDelta('2018-01-02','day=+3')                      | 2018-01-05          |
| datetime +3day        | SELECT DateDelta('20180102030405','day=+3')                  | 20180105030405      |
| datetime +3day        | SELECT DateDelta('2018-01-02 03:04:05','day=+3')             | 2018-01-05 03:04:05 |
| end day of this year  | SELECT DateDelta('2018-09-06','year=+1,month=1,day=1,day=-1') | 2018-12-31          |
| end day of last month | SELECT DateDelta('2018-09-06','day=1,day=-1')                | 2018-08-31          |



### DateDeltaFormat

- DateDeltaFormat(date,offsets,format):  

  - Desc: Like DateDelta get the date/datetime delta calculation in format ! See Above
  - Params:
    - date:  see above
    - offsets : see above
    - format  date/datetime output format, Ex. "yyyyMMdd, yyyy-MM-dd, yyyyMMddHHmmss, yyyy-MM-dd HH:mm:ss"

- DEMO:

  > SELECT DateDeltaFormat('20180102','day=+3','yyyy-MM-dd')      2018-01-05
  >
  > SELECT DateDeltaFormat('2018-01-02','day=+3','yyyyMMdd')      20180105



## References
* [Apache Hive](http://hive.apache.org/)

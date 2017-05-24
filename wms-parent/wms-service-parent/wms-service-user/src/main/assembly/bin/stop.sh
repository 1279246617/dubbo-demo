#!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`

#配置文件路径
CONF_DIR=$DEPLOY_DIR/conf

#日志目录
LOGS_DIR=$DEPLOY_DIR/logs
START_REPORT_FILE=$LOGS_DIR/shell.log

# 用来打印正常日志到START_REPORT_FILE
reportTo()
{
   echo $* >> "$START_REPORT_FILE" 
}
# 用来打印错误日志到
echoReport()
{
   echo $* | tee -a "$START_REPORT_FILE"
}

#应用名
SERVER_NAME=`sed '/^app.process.name/!d;s/.*=//' conf/dubbo.properties | tr -d '\r'`

if [ -z "$SERVER_NAME" ]; then
    SERVER_NAME=`hostname`
fi

reportTo "================ Time: `date '+%Y-%m-%d %H:%M:%S'` ================"
#logs目录不存在就创建一个
if [ ! -d "$LOGS_DIR" ]; then
    mkdir -p "$LOGS_DIR"
fi

#根据应用名查找PID,如果不存在就退出，表示没有应用在执行
PIDS=`ps -ef -ww | grep "java" | grep " -DappName=$SERVER_NAME " | awk '{print $2}'`
if [ -z "$PIDS" ]; then
    echoReport "ERROR: The $SERVER_NAME does not started!"
    exit 1
fi

#如果传递参数dump，就用来输出dump文件
if [ "$1" == "dump" ]; then
    $BIN_DIR/dump.sh
fi

reportTo "Stopping the $SERVER_NAME ..."
echo -e "Stopping the $SERVER_NAME ...\c"
KILL_PS=""

#将进程一个一个停止
for PID in $PIDS ; do
    kill $PID >/dev/null &
    KILL_PS="$! $KILL_PS"
done

# 等10s内存回收
sleep 10
for kPID in $KILL_PS; do
    wait $kPID
done

# 强制杀死进程
for kPID in $PIDS; do
    kill -9 $kPID >/dev/null 2>&1
done

#再检测一下是不是结束
REMAIN_PIDS=`ps -ef -ww | grep "java" | grep " -DappName=$SERVER_NAME " | awk '{print $2}'`
if [ -z "$REMAIN_PIDS" ]; then
    echoReport "OK!"
    echoReport "PID: $PIDS"
    exit 0
else
    echoReport "ERROR!"
    echoReport "PID: ${REMAIN_PIDS} is still alive."
    exit 1
fi

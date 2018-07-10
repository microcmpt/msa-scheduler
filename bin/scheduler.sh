#!/bin/sh
# 启动类
export JAR_NAME=msa-scheduler-1.0.1.jar

# start scheduler center
if [ "$1" = "start" ] ; then
    echo -------------------------------------------
    echo start scheduler center
    echo -------------------------------------------

    # 设置配置文件路径
    export BASE_PATH=$(dirname "$PWD")

    # 设置启动类jar
    export CLASSPATH="$BASE_PATH/lib/$JAR_NAME"

    # java可执行文件位置
    export _EXECJAVA="$JAVA_HOME/bin/java"

    # JVM启动参数
    export JAVA_OPTS="-server -Xms128m -Xmx256m -Xss256k -XX:MaxDirectMemorySize=128m -Dconfig.path.prefix=$BASE_PATH"

    $_EXECJAVA $JAVA_OPTS -jar $CLASSPATH &

# stop scheduler center
elif [ "$1" = "stop" ] ; then
    echo -------------------------------------------
    echo stop scheduler center

    #所有相关进程
    PIDs=`jps -l | grep $JAR_NAME | awk '{print $1}'`
    #停止进程
    if [ -n "$PIDs" ]; then
      for PID in $PIDs; do
          kill $PID
          echo "kill $PID"
      done
    fi

    #等待50秒
    for i in 1 10; do
      PIDs=`jps -l | grep $JAR_NAME | awk '{print $1}'`
      if [ ! -n "$PIDs" ]; then
        echo "stop scheduler center success"
        echo -------------------------------------------
        break
      fi
      echo "sleep 5s"
      sleep 5
    done

    #如果等待50秒还没有停止完，直接杀掉
    PIDs=`jps -l | grep $MAIN_CLASS | awk '{print $1}'`
    if [ -n "$PIDs" ]; then
      for PID in $PIDs; do
          kill -9 $PID
          echo "kill -9 $PID"
      done
    fi
fi

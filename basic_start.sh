#!/bin/sh
# 基础版本
export BASIC_CLOUD_VERSION=1.0.0
# abs server
export ABS_DEFAULT=basic.abs.service.$BASIC_CLOUD_VERSION.jar
# gateway service
export GATEWAY_DEFAULT=basic.gateway.service.$BASIC_CLOUD_VERSION.jar
# uaa service
export UAA_DEFAULT=basic.uaa.service.$BASIC_CLOUD_VERSION.jar
# uums service
export UUMS_DEFAULT=basic.uum.service.$BASIC_CLOUD_VERSION.jar

# conf
export ABS_DEFAULT_CONF=/home/project/abs.yml
export GATEWAY_DEFAULT_CONF=/home/project/gateway.yml
export UAA_DEFAULT_CONF=/home/project/uaa.yml
export UUMS_DEFAULT_CONF=/home/project/uums.yml

# port
export ABS_PORT=8100
export GATEWAY_PORT=8761
export UAA_PORT=8081
export UUMS_PORT=8083

case "$1" in
 
start)
        ## 启动abs
        echo "--------abs_8100 开始启动--------------"
        nohup java -Xms128m -Xmx256m -jar $ABS_DEFAULT --spring.config.location=$ABS_DEFAULT_CONF --spring.profiles.active=$ABS_PORT >/home/project/logs/abs.out &
        ABS_pid=`lsof -i:$ABS_PORT|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$ABS_pid" ]
            do
              ABS_pid=`lsof -i:$ABS_PORT|grep "LISTEN"|awk '{print $2}'`  
            done
        echo "ABS pid is $ABS_pid" 
        echo "--------abs_8100 启动成功--------------"

        ## 启动gateway
        echo "--------gateway_8761 开始启动--------------"
        nohup java -Xms128m -Xmx256m -jar $GATEWAY_DEFAULT --spring.config.location=$GATEWAY_DEFAULT_CONF --server.port=$GATEWAY_PORT >/home/project/logs/gateway.out &
        GATEWAY_pid=`lsof -i:$GATEWAY_PORT|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$GATEWAY_pid" ]
            do
              GATEWAY_pid=`lsof -i:$GATEWAY_PORT|grep "LISTEN"|awk '{print $2}'`  
            done
        echo "GATEWAY pid is $GATEWAY_pid" 
        echo "--------gateway_8761 启动成功--------------"

        ## 启动uaa
        echo "--------uaa_8081 开始启动--------------"
        nohup java -Xms128m -Xmx256m -jar $UAA_DEFAULT --spring.config.location=$UAA_DEFAULT_CONF --spring.profiles.active=$UAA_PORT >/home/project/logs/uaa.out &
        UAA_pid=`lsof -i:$UAA_PORT|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$UAA_pid" ]
            do
              UAA_pid=`lsof -i:$UAA_PORT|grep "LISTEN"|awk '{print $2}'`  
            done
        echo "UAA pid is $UAA_pid" 
        echo "--------uaa_8081 启动成功--------------"


        ## 启动abs
        echo "--------uums_8083 开始启动--------------"
        nohup java -Xms128m -Xmx256m -jar $UUMS_DEFAULT --spring.config.location=$UUMS_DEFAULT_CONF --spring.profiles.active=$UUMS_PORT >/home/project/logs/uums.out &
        UUMS_pid=`lsof -i:$UUMS_PORT|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$UUMS_pid" ]
            do
              UUMS_pid=`lsof -i:$UUMS_PORT|grep "LISTEN"|awk '{print $2}'`  
            done
        echo "UUMS pid is $UUMS_pid" 
        echo "--------uums_8083 启动成功--------------"

        echo "===startAll success==="  
        ;;
 
 stop)
        P_ID=`ps -ef | grep -w $ABS_PORT | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===ABS_8100 process not exists or stop success"
        else
            kill -9 $P_ID
            echo "ABS_8100 killed success"
        fi
        P_ID=`ps -ef | grep -w $GATEWAY_PORT | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===GATEWAY_8761 process not exists or stop success"
        else
            kill -9 $P_ID
            echo "GATEWAY_8761 killed success"
        fi
        P_ID=`ps -ef | grep -w $UAA_PORT | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===UAA_8081 process not exists or stop success"
        else
            kill -9 $P_ID
            echo "UAA_8081 killed success"
        fi
        P_ID=`ps -ef | grep -w $UUMS_PORT | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===UUMS_8083 process not exists or stop success"
        else
            kill -9 $P_ID
            echo "UUMS_8083 killed success"
        fi
        
        echo "===stop success==="
        ;;   
 
restart)
        $0 stop
        sleep 2
        $0 start
        echo "===restart success==="
        ;;   
esac	
exit 0


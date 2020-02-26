@echo off
set JAVA_HOME=C:\pub\install\java\jdk-13.0.2.8-hotspot
set JAVA_EXE=%JAVA_HOME%\bin\java.exe
set APP_HOME=D:/pub/apps/chat
set CHAT_APP_JAR=%APP_HOME%/chatapp.jar
set CHAT_PORT=5678

set LOG_CFG=-Djava.util.logging.config.file=%APP_HOME%/logging.properties

echo "Starting chat server ..."
START "Chat Server"  %JAVA_EXE% %LOG_CFG% -cp %CHAT_APP_JAR% chat.server.ChatServer %CHAT_PORT%

set CHAT_USER_OPTION=-Dchat.user=ChatUser_1

echo "Starting chat client ..."
START "Chat Client 1" %JAVA_EXE% -client %LOG_CFG% %CHAT_USER_OPTION% -cp %CHAT_APP_JAR% chat.client.ChatClient %CHAT_PORT%

echo "Starting 'Chatterbox' Client"
%JAVA_EXE% -client %LOG_CFG% %CHAT_USER_OPTION% -cp %CHAT_APP_JAR% chat.client.ChatClient %CHAT_PORT%
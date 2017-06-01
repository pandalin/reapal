import ch.qos.logback.classic.Level

import java.nio.charset.Charset

scan("60 seconds")
appender("STDOUT",ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "[%thread] %d %highlight(%-5level) - %msg %n"
        charset = Charset.forName("utf-8")
    }
}
appender("FILE",RollingFileAppender) {
    file = "../logs/reapal.log"
    append = true
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "../logs/reapal.%d{yyyy-MM-dd}.log"
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%date %level [%thread] %10logger [%file:%line] %msg%n"
        charset = Charset.forName("utf-8")
    }
}
root(Level.INFO,["STDOUT"])
/*root(Level.INFO,["STDOUT","FILE"])*/
logger("com.jvv",Level.INFO)
logger("org.hibernate",Level.ERROR)
logger("org.springframework",Level.ERROR)


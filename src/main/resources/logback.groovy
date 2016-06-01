appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %highlight(%-5level) %logger{40}: %msg%n"
    }
}

root(INFO, ["STDOUT"])

logger("gesha", INFO)
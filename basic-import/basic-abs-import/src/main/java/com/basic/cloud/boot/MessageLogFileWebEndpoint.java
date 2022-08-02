package com.basic.cloud.boot;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.boot.actuate.logging.LogFileWebEndpoint;
import org.springframework.boot.logging.LogFile;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Configuration
@WebEndpoint(id = "logfile")
public class MessageLogFileWebEndpoint extends LogFileWebEndpoint {

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());


    private final Environment environment;

    public MessageLogFileWebEndpoint(Environment environment) {
        super(environment);
        this.environment = environment;
    }

    @ReadOperation(produces = "text/plain; charset=UTF-8")
    @Override
    public Resource logFile() {
        return getLogFileResource();
    }


    private Resource getLogFileResource() {
        LogFile logFile = LogFile.get(this.environment);
        if (logFile == null) {
            logger.debug("Missing 'logging.file' or 'logging.path' properties");
            return null;
        }
        String file = logFile.toString();
        String fileName = FilenameUtils.getName(file);
        LocalDate now = LocalDate.now();
        if (StringUtils.hasLength(fileName)) {
            String newFileName = Arrays.stream(fileName.split("-")).map(item -> {
                String[] split = item.split("\\.");
                if (split.length > 1) {
                    item = Arrays.stream(split).map(cItem -> replace(cItem, now)).collect(Collectors.joining("."));
                } else {
                    item = replace(item, now);
                }
                return item;
            }).collect(Collectors.joining("-"));
            file = file.replace(fileName, newFileName);
        }
        return new FileSystemResource(file);
    }

    private String replace(String item, LocalDate now) {
        switch (item) {
            case "%yyyy":
                item = String.valueOf(now.getYear());
                break;
            case "%MM":
                int monthValue = now.getMonthValue();
                if (monthValue > 9) {
                    item = String.valueOf(monthValue);
                } else {
                    item = "0" + monthValue;
                }
                break;
            case "%dd":
                int dayOfMonth = now.getDayOfMonth();
                if (dayOfMonth > 9) {
                    item = String.valueOf(dayOfMonth);
                } else {
                    item = "0" + dayOfMonth;
                }
                break;
        }
        return item;
    }
}

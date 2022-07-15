package com.basic.cloud.file.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.file.entity.FileSharding;
import com.basic.cloud.file.mapper.FileShardingMapper;
import com.basic.cloud.file.service.FileShardingService;
import org.springframework.stereotype.Service;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class FileShardingServiceImpl extends BaseBeanServiceImpl<FileShardingMapper, FileSharding> implements FileShardingService {
}

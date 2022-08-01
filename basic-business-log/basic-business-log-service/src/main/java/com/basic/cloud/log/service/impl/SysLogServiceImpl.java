package com.basic.cloud.log.service.impl;

import com.basic.cloud.common.bean.BaseBeanServiceImpl;
import com.basic.cloud.log.entity.SysLog;
import com.basic.cloud.log.mapper.SysLogMapper;
import com.basic.cloud.log.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Service
public class SysLogServiceImpl extends BaseBeanServiceImpl<SysLogMapper, SysLog> implements SysLogService {
}

package com.basic.cloud.uums.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.basic.cloud.common.bean.BisDataEntity;
import com.basic.cloud.common.transfer.ModelMapper;
import com.basic.cloud.common.vo.ResultData;
import com.basic.cloud.uums.dto.BlackIpsDTO;
import com.basic.cloud.uums.entity.BlackIps;
import com.basic.cloud.uums.service.BlackIpsService;
import lombok.AllArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@AllArgsConstructor
@RestController
@RequestMapping("/blackIps")
public class BlackIpsController {

    private final BlackIpsService blackIpsService;

    /**
     * 新增黑名单IP
     *
     * @param blackIpsDTO
     * @return
     */
    @PostMapping("/add")
    public ResultData<Boolean> add(@Validated @RequestBody BlackIpsDTO blackIpsDTO) {
        BlackIps blackIps = new BlackIps();
        ModelMapper.map(blackIps, blackIpsDTO);
        return ResultData.ok(blackIpsService.saveIp(blackIps));
    }

    /**
     * ip是否是黑名单
     *
     * @param ip
     * @return
     */
    @GetMapping("/exits")
    public ResultData<Boolean> exits(String ip) {
        if (ObjectUtils.isEmpty(ip)) {
            return ResultData.error("请求参数ip不能为空!");
        }
        int count = blackIpsService.count(blackIpsService.getLambdaQueryWrapper()
                .eq(BlackIps::getIp, ip).
                eq(BisDataEntity::getDelFlag, false));
        return ResultData.ok(count > 0);
    }

    /**
     * 从黑名单限制中移除IP
     *
     * @param ip
     * @return
     */
    @DeleteMapping("/remove")
    public ResultData<Void> remove(String ip) {
        if (ObjectUtils.isEmpty(ip)) {
            return ResultData.error("请求参数ip不能为空!");
        }
        blackIpsService.update(Wrappers.lambdaUpdate(new BlackIps())
                .eq(BlackIps::getIp, ip)
                .eq(BisDataEntity::getDelFlag, false)
                .set(BisDataEntity::getDelFlag, true));
        return ResultData.ok();
    }
}

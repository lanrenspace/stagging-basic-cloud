package com.basic.cloud.uums.vo;

import com.basic.cloud.uums.entity.UserInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "用户详情vo")
public class UserInfoVO extends UserInfo {
}

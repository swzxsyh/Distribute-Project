package com.distribute.domain.action;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * 批量操作数据响应类
 * @author lijiahao
 * @date 2020年12月4日 下午3:51:50 
 * @ClassName: DistributionDto
 * @Description: 
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchResultVo implements Serializable {
	
    @ApiModelProperty(value = "操作成功数量")
    private int successCount;

    @ApiModelProperty(value = "操作失败数量")
    private int failCount;

    @ApiModelProperty(value = "无法操作数量")
    private int unableOperate;
    
    @ApiModelProperty(value = "失败集合失败原因")
    private List<Outcome> failList;
    
    @ApiModelProperty(value = "无法操作集合失败原因")
    private List<String> unableOperateList;
}

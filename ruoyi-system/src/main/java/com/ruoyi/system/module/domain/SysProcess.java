package com.ruoyi.system.module.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 流程对象 sys_process
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
public class SysProcess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** KEY */
    private Long id;


    /** 流程名称 */
    @Valid
    @NotBlank(message = "流程名称不能为空")
    @Excel(name = "流程名称")
    private String processName;

    /** 流程标识  */
    @Valid
    @NotBlank(message = "流程标识不能为空")
    @Excel(name = "流程标识 ")
    private String processMark;


    /** 对应表单名称 */
    @Excel(name = "对应表单名称")
    private String formName;

    /** 审批表单页面地址 */
    @Excel(name = "审批表单页面地址")
    private String approvalFormUrl;

    /** 修改表单页面地址 */
    @Excel(name = "修改表单页面地址")
    private String updateFormUrl;

    /** 删除标识（0代表存在 2代表删除） */
    private Integer delFlag;

    /** 状态（0关闭 1开启） */
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProcessMark(String processMark) 
    {
        this.processMark = processMark;
    }

    public String getProcessMark() 
    {
        return processMark;
    }
    public void setProcessName(String processName) 
    {
        this.processName = processName;
    }

    public String getProcessName() 
    {
        return processName;
    }
    public void setFormName(String formName) 
    {
        this.formName = formName;
    }

    public String getFormName() 
    {
        return formName;
    }
    public void setApprovalFormUrl(String approvalFormUrl) 
    {
        this.approvalFormUrl = approvalFormUrl;
    }

    public String getApprovalFormUrl() 
    {
        return approvalFormUrl;
    }
    public void setUpdateFormUrl(String updateFormUrl) 
    {
        this.updateFormUrl = updateFormUrl;
    }

    public String getUpdateFormUrl() 
    {
        return updateFormUrl;
    }
    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
    {
        return delFlag;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("processMark", getProcessMark())
            .append("processName", getProcessName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("formName", getFormName())
            .append("approvalFormUrl", getApprovalFormUrl())
            .append("updateFormUrl", getUpdateFormUrl())
            .append("delFlag", getDelFlag())
            .append("status", getStatus())
            .toString();
    }
}

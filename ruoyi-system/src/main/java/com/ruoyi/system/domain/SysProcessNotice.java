package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 流程记录对象 sys_process_notice
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
public class SysProcessNotice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程实例id */
    @Excel(name = "流程实例id")
    private Long processRuntimeId;

    /** 审批人 */
    @Excel(name = "审批人")
    private Long approverId;

    /** 审批时间  */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间 ", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approveTime;

    /** 审批信息 */
    @Excel(name = "审批信息")
    private String approveMsg;

    /** 审批状态 1:通过  2:驳回 */
    @Excel(name = "审批状态 1:通过  2:驳回")
    private Integer approveStatus;

    /** 节点名称 */
    @Excel(name = "节点名称")
    private String nodeName;

    public void setProcessRuntimeId(Long processRuntimeId) 
    {
        this.processRuntimeId = processRuntimeId;
    }

    public Long getProcessRuntimeId() 
    {
        return processRuntimeId;
    }
    public void setApproverId(Long approverId) 
    {
        this.approverId = approverId;
    }

    public Long getApproverId() 
    {
        return approverId;
    }
    public void setApproveTime(Date approveTime) 
    {
        this.approveTime = approveTime;
    }

    public Date getApproveTime() 
    {
        return approveTime;
    }
    public void setApproveMsg(String approveMsg) 
    {
        this.approveMsg = approveMsg;
    }

    public String getApproveMsg() 
    {
        return approveMsg;
    }
    public void setApproveStatus(Integer approveStatus) 
    {
        this.approveStatus = approveStatus;
    }

    public Integer getApproveStatus() 
    {
        return approveStatus;
    }
    public void setNodeName(String nodeName) 
    {
        this.nodeName = nodeName;
    }

    public String getNodeName() 
    {
        return nodeName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("processRuntimeId", getProcessRuntimeId())
            .append("approverId", getApproverId())
            .append("approveTime", getApproveTime())
            .append("approveMsg", getApproveMsg())
            .append("approveStatus", getApproveStatus())
            .append("nodeName", getNodeName())
            .toString();
    }
}

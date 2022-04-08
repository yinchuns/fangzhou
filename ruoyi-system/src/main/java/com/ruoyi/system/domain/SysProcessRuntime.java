package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 流程实例对象 sys_process_runtime
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
public class SysProcessRuntime extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程实例id */
    private Long id;

    /** 流程标识 */
    private String processMark;

    /** 流程状态：1:流程审批中      2：退回      3：流程结束   99:失败 */
    @Excel(name = "流程状态：1:流程审批中      2：退回      3：流程结束   99:失败")
    private Integer status;

    /** 上一个节点   */
    @Excel(name = "上一个节点  ")
    private Integer previousNode;

    /** 上一个审批人ID */
    @Excel(name = "上一个审批人ID")
    private Long previousApproverId;

    /** 当前审批人ID  */
    @Excel(name = "当前审批人ID ")
    private Long approverId;

    /** 对应表单记录ID  */
    @Excel(name = "对应表单记录ID ")
    private Long formId;

    /** 流程名称 */
    @Excel(name = "流程名称")
    private String processName;

    /** 当前节点 */
    @Excel(name = "当前节点")
    private Integer currentNode;

    /** 删除标识      （0代表存在 2代表删除） */
    private Integer delFlag;

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
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setPreviousNode(Integer previousNode) 
    {
        this.previousNode = previousNode;
    }

    public Integer getPreviousNode() 
    {
        return previousNode;
    }
    public void setPreviousApproverId(Long previousApproverId) 
    {
        this.previousApproverId = previousApproverId;
    }

    public Long getPreviousApproverId() 
    {
        return previousApproverId;
    }
    public void setApproverId(Long approverId) 
    {
        this.approverId = approverId;
    }

    public Long getApproverId() 
    {
        return approverId;
    }
    public void setFormId(Long formId) 
    {
        this.formId = formId;
    }

    public Long getFormId() 
    {
        return formId;
    }
    public void setProcessName(String processName) 
    {
        this.processName = processName;
    }

    public String getProcessName() 
    {
        return processName;
    }
    public void setCurrentNode(Integer currentNode) 
    {
        this.currentNode = currentNode;
    }

    public Integer getCurrentNode() 
    {
        return currentNode;
    }
    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("processMark", getProcessMark())
            .append("status", getStatus())
            .append("previousNode", getPreviousNode())
            .append("previousApproverId", getPreviousApproverId())
            .append("approverId", getApproverId())
            .append("formId", getFormId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("processName", getProcessName())
            .append("currentNode", getCurrentNode())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
    }
}

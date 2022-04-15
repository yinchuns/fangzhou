package com.ruoyi.system.module.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 节点审核人对象 sys_process_node_approver
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
public class SysProcessNodeApprover extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 节点id  */
    private Long nodeId;

    /** 节点审核人 */
    private Long approverId;

    /** 审核人名称 */
    private String approverName;

    /** 删除标识  （0代表存在 2代表删除） */
    private Integer delFlag;

    /** 多个id */
    private List<Long> approverIds;


    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setNodeId(Long nodeId) 
    {
        this.nodeId = nodeId;
    }

    public Long getNodeId() 
    {
        return nodeId;
    }
    public void setApproverId(Long approverId) 
    {
        this.approverId = approverId;
    }

    public Long getApproverId() 
    {
        return approverId;
    }
    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
    {
        return delFlag;
    }
    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("nodeId", getNodeId())
            .append("approverId", getApproverId())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("approverName", getApproverName())
            .append("approverIds", getApproverIds())
            .toString();
    }


    public List<Long> getApproverIds() {
        return approverIds;
    }

    public void setApproverIds(List<Long> approverIds) {
        this.approverIds = approverIds;
    }
}

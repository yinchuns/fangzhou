package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;

/**
 * 流程节点对象 sys_process_node
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
public class SysProcessNode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程名称 **/
    private String processName;

    /** 节点ID */
    private Long id;

    /** 流程标识   */
    @Excel(name = "流程标识  ")
    private String processMark;

    /** 节点名称 */
    @NotBlank(message = "节点名称不能为空")
    @Excel(name = "节点名称")
    private String nodeName;

    /** 节点序号 */
    @Excel(name = "节点序号")
    private Integer step;

    /** 删除标识  （0代表存在 2代表删除） */
    private Integer delFlag;

    /** 节点选中状态 0：未选中  1：选中*/
    private String checkStatus;



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
    public void setNodeName(String nodeName) 
    {
        this.nodeName = nodeName;
    }

    public String getNodeName() 
    {
        return nodeName;
    }
    public void setStep(Integer step) 
    {
        this.step = step;
    }

    public Integer getStep() 
    {
        return step;
    }
    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
    {
        return delFlag;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("processMark", getProcessMark())
            .append("nodeName", getNodeName())
            .append("step", getStep())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("delFlag", getDelFlag())
            .append("processName", getProcessName())
            .append("checkStatus", getCheckStatus())
            .toString();
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }
}

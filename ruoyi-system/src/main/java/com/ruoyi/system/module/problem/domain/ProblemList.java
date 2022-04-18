package com.ruoyi.system.module.problem.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 问题清单对象 problem_list
 * 
 * @author ruoyi
 * @date 2022-04-15
 */
public class ProblemList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 问题名称 */
    @Excel(name = "问题名称")
    private String problemName;

    /** 问题内容 */
    @Excel(name = "问题内容")
    private String problemDetail;

    /** 问题编号 */
    @Excel(name = "问题编号")
    private String problemNum;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProblemName(String problemName) 
    {
        this.problemName = problemName;
    }

    public String getProblemName() 
    {
        return problemName;
    }
    public void setProblemDetail(String problemDetail) 
    {
        this.problemDetail = problemDetail;
    }

    public String getProblemDetail() 
    {
        return problemDetail;
    }
    public void setProblemNum(String problemNum) 
    {
        this.problemNum = problemNum;
    }

    public String getProblemNum() 
    {
        return problemNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("problemName", getProblemName())
            .append("problemDetail", getProblemDetail())
            .append("problemNum", getProblemNum())
            .toString();
    }
}

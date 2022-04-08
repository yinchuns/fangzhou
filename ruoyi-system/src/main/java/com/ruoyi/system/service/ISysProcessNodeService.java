package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysProcessNode;

/**
 * 流程节点Service接口
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
public interface ISysProcessNodeService 
{
    /**
     * 查询流程节点
     * 
     * @param id 流程节点主键
     * @return 流程节点
     */
    public SysProcessNode selectSysProcessNodeById(Long id);

    /**
     * 查询流程节点列表
     * 
     * @param sysProcessNode 流程节点
     * @return 流程节点集合
     */
    public List<SysProcessNode> selectSysProcessNodeList(SysProcessNode sysProcessNode);

    /**
     * 新增流程节点
     * 
     * @param sysProcessNode 流程节点
     * @return 结果
     */
    public int insertSysProcessNode(SysProcessNode sysProcessNode);

    /**
     * 修改流程节点
     * 
     * @param sysProcessNode 流程节点
     * @return 结果
     */
    public int updateSysProcessNode(SysProcessNode sysProcessNode);

    /**
     * 批量删除流程节点
     * 
     * @param ids 需要删除的流程节点主键集合
     * @return 结果
     */
    public int deleteSysProcessNodeByIds(Long[] ids);

    /**
     * 删除流程节点信息
     * 
     * @param id 流程节点主键
     * @return 结果
     */
    public int deleteSysProcessNodeById(Long id);
}

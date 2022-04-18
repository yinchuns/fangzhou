package com.ruoyi.system.module.process.mapper;

import java.util.List;
import com.ruoyi.system.module.process.domain.SysProcessNodeApprover;

/**
 * 节点审核人Mapper接口
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
public interface SysProcessNodeApproverMapper
{

    /**
     * 根据节点id获取该节点下所有的审核人数量
     * @param nodeId
     * @return
     */
    public Integer selectApproverCountByNode(Long nodeId);

    /**
     * 查询节点审核人
     * 
     * @param id 节点审核人主键
     * @return 节点审核人
     */
    public SysProcessNodeApprover selectSysProcessNodeApproverById(Long id);

    /**
     * 查询节点审核人列表
     * 
     * @param sysProcessNodeApprover 节点审核人
     * @return 节点审核人集合
     */
    public List<SysProcessNodeApprover> selectSysProcessNodeApproverList(SysProcessNodeApprover sysProcessNodeApprover);

    /**
     * 新增节点审核人
     * 
     * @param sysProcessNodeApprover 节点审核人
     * @return 结果
     */
    public int insertSysProcessNodeApprover(SysProcessNodeApprover sysProcessNodeApprover);

    /**
     * 修改节点审核人
     * 
     * @param sysProcessNodeApprover 节点审核人
     * @return 结果
     */
    public int updateSysProcessNodeApprover(SysProcessNodeApprover sysProcessNodeApprover);

    /**
     * 删除节点审核人
     * 
     * @param id 节点审核人主键
     * @return 结果
     */
    public int deleteSysProcessNodeApproverById(Long id);

    /**
     * 批量删除节点审核人
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysProcessNodeApproverByIds(Long[] ids);
}

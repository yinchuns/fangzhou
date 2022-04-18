package com.ruoyi.system.module.process.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.module.process.mapper.SysProcessNodeApproverMapper;
import com.ruoyi.system.module.process.domain.SysProcessNodeApprover;
import com.ruoyi.system.module.process.service.ISysProcessNodeApproverService;

/**
 * 节点审核人Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
@Service
public class SysProcessNodeApproverServiceImpl implements ISysProcessNodeApproverService 
{
    @Autowired
    private SysProcessNodeApproverMapper sysProcessNodeApproverMapper;

    @Override
    public Integer selectApproverCountByNode(Long nodeId) {
        return sysProcessNodeApproverMapper.selectApproverCountByNode(nodeId);
    }

    /**
     * 查询节点审核人
     * 
     * @param id 节点审核人主键
     * @return 节点审核人
     */
    @Override
    public SysProcessNodeApprover selectSysProcessNodeApproverById(Long id)
    {
        return sysProcessNodeApproverMapper.selectSysProcessNodeApproverById(id);
    }

    /**
     * 查询节点审核人列表
     * 
     * @param sysProcessNodeApprover 节点审核人
     * @return 节点审核人
     */
    @Override
    public List<SysProcessNodeApprover> selectSysProcessNodeApproverList(SysProcessNodeApprover sysProcessNodeApprover)
    {
        return sysProcessNodeApproverMapper.selectSysProcessNodeApproverList(sysProcessNodeApprover);
    }

    /**
     * 新增节点审核人
     * 
     * @param sysProcessNodeApprover 节点审核人
     * @return 结果
     */
    @Override
    public int insertSysProcessNodeApprover(SysProcessNodeApprover sysProcessNodeApprover)
    {
        return sysProcessNodeApproverMapper.insertSysProcessNodeApprover(sysProcessNodeApprover);
    }

    /**
     * 修改节点审核人
     * 
     * @param sysProcessNodeApprover 节点审核人
     * @return 结果
     */
    @Override
    public int updateSysProcessNodeApprover(SysProcessNodeApprover sysProcessNodeApprover)
    {
        return sysProcessNodeApproverMapper.updateSysProcessNodeApprover(sysProcessNodeApprover);
    }

    /**
     * 批量删除节点审核人
     * 
     * @param ids 需要删除的节点审核人主键
     * @return 结果
     */
    @Override
    public int deleteSysProcessNodeApproverByIds(Long[] ids)
    {
        return sysProcessNodeApproverMapper.deleteSysProcessNodeApproverByIds(ids);
    }

    /**
     * 删除节点审核人信息
     * 
     * @param id 节点审核人主键
     * @return 结果
     */
    @Override
    public int deleteSysProcessNodeApproverById(Long id)
    {
        return sysProcessNodeApproverMapper.deleteSysProcessNodeApproverById(id);
    }
}

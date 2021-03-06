package com.ruoyi.system.module.process.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.module.process.mapper.SysProcessNodeMapper;
import com.ruoyi.system.module.process.domain.SysProcessNode;
import com.ruoyi.system.module.process.service.ISysProcessNodeService;

/**
 * 流程节点Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
@Service
public class SysProcessNodeServiceImpl implements ISysProcessNodeService 
{
    @Autowired
    private SysProcessNodeMapper sysProcessNodeMapper;

    @Override
    public SysProcessNode selectNodeByProcessMarkAndStep(SysProcessNode sysProcessNode) {
        return sysProcessNodeMapper.selectNodeByProcessMarkAndStep(sysProcessNode);
    }

    @Override
    public String selectNodeName(SysProcessNode sysProcessNode) {
        return sysProcessNodeMapper.selectNodeName(sysProcessNode);
    }

    @Override
    public Integer selectMaxNode(String processMark) {
        return sysProcessNodeMapper.selectMaxNode(processMark);
    }

    @Override
    public Integer selectNodeIsAlready(SysProcessNode sysProcessNode) {
        return sysProcessNodeMapper.selectNodeIsAlready(sysProcessNode);
    }

    @Override
    public Integer selectProcessNodeCount(String processMark) {
        return sysProcessNodeMapper.selectProcessNodeCount(processMark);
    }

    /**
     * 查询流程节点
     * 
     * @param id 流程节点主键
     * @return 流程节点
     */
    @Override
    public SysProcessNode selectSysProcessNodeById(Long id)
    {
        return sysProcessNodeMapper.selectSysProcessNodeById(id);
    }

    /**
     * 查询流程节点列表
     * 
     * @param sysProcessNode 流程节点
     * @return 流程节点
     */
    @Override
    public List<SysProcessNode> selectSysProcessNodeList(SysProcessNode sysProcessNode)
    {
        return sysProcessNodeMapper.selectSysProcessNodeList(sysProcessNode);
    }

    /**
     * 新增流程节点
     * 
     * @param sysProcessNode 流程节点
     * @return 结果
     */
    @Override
    public int insertSysProcessNode(SysProcessNode sysProcessNode)
    {
        sysProcessNode.setCreateTime(DateUtils.getNowDate());
        return sysProcessNodeMapper.insertSysProcessNode(sysProcessNode);
    }

    /**
     * 修改流程节点
     * 
     * @param sysProcessNode 流程节点
     * @return 结果
     */
    @Override
    public int updateSysProcessNode(SysProcessNode sysProcessNode)
    {
        return sysProcessNodeMapper.updateSysProcessNode(sysProcessNode);
    }

    /**
     * 批量删除流程节点
     * 
     * @param ids 需要删除的流程节点主键
     * @return 结果
     */
    @Override
    public int deleteSysProcessNodeByIds(Long[] ids)
    {
        return sysProcessNodeMapper.deleteSysProcessNodeByIds(ids);
    }

    /**
     * 删除流程节点信息
     * 
     * @param id 流程节点主键
     * @return 结果
     */
    @Override
    public int deleteSysProcessNodeById(Long id)
    {
        return sysProcessNodeMapper.deleteSysProcessNodeById(id);
    }
}

package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysProcessMapper;
import com.ruoyi.system.domain.SysProcess;
import com.ruoyi.system.service.ISysProcessService;

/**
 * 流程Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
@Service
public class SysProcessServiceImpl implements ISysProcessService 
{
    @Autowired
    private SysProcessMapper sysProcessMapper;

    @Override
    public SysProcess selectProcessByCondition(SysProcess sysProcess) {
        return sysProcessMapper.selectProcessByCondition(sysProcess);
    }

    /**
     * 查询流程
     * 
     * @param id 流程主键
     * @return 流程
     */
    @Override
    public SysProcess selectSysProcessById(Long id)
    {
        return sysProcessMapper.selectSysProcessById(id);
    }

    /**
     * 查询流程列表
     * 
     * @param sysProcess 流程
     * @return 流程
     */
    @Override
    public List<SysProcess> selectSysProcessList(SysProcess sysProcess)
    {
        return sysProcessMapper.selectSysProcessList(sysProcess);
    }

    /**
     * 新增流程
     * 
     * @param sysProcess 流程
     * @return 结果
     */
    @Override
    public int insertSysProcess(SysProcess sysProcess)
    {
        sysProcess.setCreateTime(DateUtils.getNowDate());
        return sysProcessMapper.insertSysProcess(sysProcess);
    }

    /**
     * 修改流程
     * 
     * @param sysProcess 流程
     * @return 结果
     */
    @Override
    public int updateSysProcess(SysProcess sysProcess)
    {
        sysProcess.setUpdateTime(DateUtils.getNowDate());
        return sysProcessMapper.updateSysProcess(sysProcess);
    }

    /**
     * 批量删除流程
     * 
     * @param ids 需要删除的流程主键
     * @return 结果
     */
    @Override
    public int deleteSysProcessByIds(Long[] ids)
    {
        return sysProcessMapper.deleteSysProcessByIds(ids);
    }

    /**
     * 删除流程信息
     * 
     * @param id 流程主键
     * @return 结果
     */
    @Override
    public int deleteSysProcessById(Long id)
    {
        return sysProcessMapper.deleteSysProcessById(id);
    }
}

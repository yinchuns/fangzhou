package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysProcessRuntimeMapper;
import com.ruoyi.system.domain.SysProcessRuntime;
import com.ruoyi.system.service.ISysProcessRuntimeService;

/**
 * 流程实例Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
@Service
public class SysProcessRuntimeServiceImpl implements ISysProcessRuntimeService 
{
    @Autowired
    private SysProcessRuntimeMapper sysProcessRuntimeMapper;

    /**
     * 根据formId查询流程实例
     *
     * @param formId 表单id
     * @return 流程实例
     */
    @Override
    public SysProcessRuntime selectProcessRuntimeByFormId(Long formId) {
        return sysProcessRuntimeMapper.selectProcessRuntimeByFormId(formId);
    }

    /**
     * 查询流程实例
     * 
     * @param id 流程实例主键
     * @return 流程实例
     */
    @Override
    public SysProcessRuntime selectSysProcessRuntimeById(Long id)
    {
        return sysProcessRuntimeMapper.selectSysProcessRuntimeById(id);
    }

    /**
     * 查询流程实例列表
     * 
     * @param sysProcessRuntime 流程实例
     * @return 流程实例
     */
    @Override
    public List<SysProcessRuntime> selectSysProcessRuntimeList(SysProcessRuntime sysProcessRuntime)
    {
        return sysProcessRuntimeMapper.selectSysProcessRuntimeList(sysProcessRuntime);
    }

    /**
     * 新增流程实例
     * 
     * @param sysProcessRuntime 流程实例
     * @return 结果
     */
    @Override
    public int insertSysProcessRuntime(SysProcessRuntime sysProcessRuntime)
    {
        sysProcessRuntime.setCreateTime(DateUtils.getNowDate());
        return sysProcessRuntimeMapper.insertSysProcessRuntime(sysProcessRuntime);
    }

    /**
     * 修改流程实例
     * 
     * @param sysProcessRuntime 流程实例
     * @return 结果
     */
    @Override
    public int updateSysProcessRuntime(SysProcessRuntime sysProcessRuntime)
    {
        return sysProcessRuntimeMapper.updateSysProcessRuntime(sysProcessRuntime);
    }

    /**
     * 批量删除流程实例
     * 
     * @param ids 需要删除的流程实例主键
     * @return 结果
     */
    @Override
    public int deleteSysProcessRuntimeByIds(Long[] ids)
    {
        return sysProcessRuntimeMapper.deleteSysProcessRuntimeByIds(ids);
    }

    /**
     * 删除流程实例信息
     * 
     * @param id 流程实例主键
     * @return 结果
     */
    @Override
    public int deleteSysProcessRuntimeById(Long id)
    {
        return sysProcessRuntimeMapper.deleteSysProcessRuntimeById(id);
    }
}

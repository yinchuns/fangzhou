package com.ruoyi.system.module.service;

import java.util.List;
import com.ruoyi.system.module.domain.SysProcessRuntime;

/**
 * 流程实例Service接口
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
public interface ISysProcessRuntimeService 
{
    /**
     * 根据formId查询流程实例
     *
     * @param formId 表单id
     * @return 流程实例
     */
    public SysProcessRuntime selectProcessRuntimeByFormId(Long formId);

    /**
     * 查询流程实例
     * 
     * @param id 流程实例主键
     * @return 流程实例
     */
    public SysProcessRuntime selectSysProcessRuntimeById(Long id);

    /**
     * 查询流程实例列表
     * 
     * @param sysProcessRuntime 流程实例
     * @return 流程实例集合
     */
    public List<SysProcessRuntime> selectSysProcessRuntimeList(SysProcessRuntime sysProcessRuntime);

    /**
     * 新增流程实例
     * 
     * @param sysProcessRuntime 流程实例
     * @return 结果
     */
    public int insertSysProcessRuntime(SysProcessRuntime sysProcessRuntime);

    /**
     * 修改流程实例
     * 
     * @param sysProcessRuntime 流程实例
     * @return 结果
     */
    public int updateSysProcessRuntime(SysProcessRuntime sysProcessRuntime);

    /**
     * 批量删除流程实例
     * 
     * @param ids 需要删除的流程实例主键集合
     * @return 结果
     */
    public int deleteSysProcessRuntimeByIds(Long[] ids);

    /**
     * 删除流程实例信息
     * 
     * @param id 流程实例主键
     * @return 结果
     */
    public int deleteSysProcessRuntimeById(Long id);
}
package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysProcess;

/**
 * 流程Mapper接口
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
public interface SysProcessMapper 
{

    /**
     * 根据条件查询流程数据
     * @param sysProcess 流程
     * @return 流程
     */
    public SysProcess selectProcessByCondition(SysProcess sysProcess);

    /**
     * 查询流程
     * 
     * @param id 流程主键
     * @return 流程
     */
    public SysProcess selectSysProcessById(Long id);

    /**
     * 查询流程列表
     * 
     * @param sysProcess 流程
     * @return 流程集合
     */
    public List<SysProcess> selectSysProcessList(SysProcess sysProcess);

    /**
     * 新增流程
     * 
     * @param sysProcess 流程
     * @return 结果
     */
    public int insertSysProcess(SysProcess sysProcess);

    /**
     * 修改流程
     * 
     * @param sysProcess 流程
     * @return 结果
     */
    public int updateSysProcess(SysProcess sysProcess);

    /**
     * 删除流程
     * 
     * @param id 流程主键
     * @return 结果
     */
    public int deleteSysProcessById(Long id);

    /**
     * 批量删除流程
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysProcessByIds(Long[] ids);
}

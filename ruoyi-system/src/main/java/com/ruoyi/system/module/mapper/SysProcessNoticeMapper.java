package com.ruoyi.system.module.mapper;

import java.util.List;
import com.ruoyi.system.module.domain.SysProcessNotice;

/**
 * 流程记录Mapper接口
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
public interface SysProcessNoticeMapper 
{
    /**
     * 查询流程记录
     * 
     * @param processRuntimeId 流程记录主键
     * @return 流程记录
     */
    public List<SysProcessNotice> selectSysProcessNoticeByProcessRuntimeId(Long processRuntimeId);

    /**
     * 查询流程记录列表
     * 
     * @param sysProcessNotice 流程记录
     * @return 流程记录集合
     */
    public List<SysProcessNotice> selectSysProcessNoticeList(SysProcessNotice sysProcessNotice);

    /**
     * 新增流程记录
     * 
     * @param sysProcessNotice 流程记录
     * @return 结果
     */
    public int insertSysProcessNotice(SysProcessNotice sysProcessNotice);

    /**
     * 修改流程记录
     * 
     * @param sysProcessNotice 流程记录
     * @return 结果
     */
    public int updateSysProcessNotice(SysProcessNotice sysProcessNotice);

    /**
     * 删除流程记录
     * 
     * @param processRuntimeId 流程记录主键
     * @return 结果
     */
    public int deleteSysProcessNoticeByProcessRuntimeId(Long processRuntimeId);

    /**
     * 批量删除流程记录
     * 
     * @param processRuntimeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysProcessNoticeByProcessRuntimeIds(Long[] processRuntimeIds);
}

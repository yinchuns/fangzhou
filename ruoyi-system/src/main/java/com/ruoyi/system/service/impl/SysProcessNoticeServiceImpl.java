package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysProcessNoticeMapper;
import com.ruoyi.system.domain.SysProcessNotice;
import com.ruoyi.system.service.ISysProcessNoticeService;

/**
 * 流程记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
@Service
public class SysProcessNoticeServiceImpl implements ISysProcessNoticeService 
{
    @Autowired
    private SysProcessNoticeMapper sysProcessNoticeMapper;

    /**
     * 查询流程记录
     * 
     * @param processRuntimeId 流程记录主键
     * @return 流程记录
     */
    @Override
    public SysProcessNotice selectSysProcessNoticeByProcessRuntimeId(Long processRuntimeId)
    {
        return sysProcessNoticeMapper.selectSysProcessNoticeByProcessRuntimeId(processRuntimeId);
    }

    /**
     * 查询流程记录列表
     * 
     * @param sysProcessNotice 流程记录
     * @return 流程记录
     */
    @Override
    public List<SysProcessNotice> selectSysProcessNoticeList(SysProcessNotice sysProcessNotice)
    {
        return sysProcessNoticeMapper.selectSysProcessNoticeList(sysProcessNotice);
    }

    /**
     * 新增流程记录
     * 
     * @param sysProcessNotice 流程记录
     * @return 结果
     */
    @Override
    public int insertSysProcessNotice(SysProcessNotice sysProcessNotice)
    {
        return sysProcessNoticeMapper.insertSysProcessNotice(sysProcessNotice);
    }

    /**
     * 修改流程记录
     * 
     * @param sysProcessNotice 流程记录
     * @return 结果
     */
    @Override
    public int updateSysProcessNotice(SysProcessNotice sysProcessNotice)
    {
        return sysProcessNoticeMapper.updateSysProcessNotice(sysProcessNotice);
    }

    /**
     * 批量删除流程记录
     * 
     * @param processRuntimeIds 需要删除的流程记录主键
     * @return 结果
     */
    @Override
    public int deleteSysProcessNoticeByProcessRuntimeIds(Long[] processRuntimeIds)
    {
        return sysProcessNoticeMapper.deleteSysProcessNoticeByProcessRuntimeIds(processRuntimeIds);
    }

    /**
     * 删除流程记录信息
     * 
     * @param processRuntimeId 流程记录主键
     * @return 结果
     */
    @Override
    public int deleteSysProcessNoticeByProcessRuntimeId(Long processRuntimeId)
    {
        return sysProcessNoticeMapper.deleteSysProcessNoticeByProcessRuntimeId(processRuntimeId);
    }
}

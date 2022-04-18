package com.ruoyi.system.module.process.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.module.process.domain.SysProcessNotice;
import com.ruoyi.system.module.process.service.ISysProcessNoticeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 流程记录Controller
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
@RestController
@RequestMapping("/system/process/processNotice")
public class SysProcessNoticeController extends BaseController
{
    @Autowired
    private ISysProcessNoticeService sysProcessNoticeService;

    /**
     * 查询流程记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysProcessNotice sysProcessNotice)
    {
        startPage();
        List<SysProcessNotice> list = sysProcessNoticeService.selectSysProcessNoticeList(sysProcessNotice);
        return getDataTable(list);
    }

    /**
     * 导出流程记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:notice:export')")
    @Log(title = "流程记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysProcessNotice sysProcessNotice)
    {
        List<SysProcessNotice> list = sysProcessNoticeService.selectSysProcessNoticeList(sysProcessNotice);
        ExcelUtil<SysProcessNotice> util = new ExcelUtil<SysProcessNotice>(SysProcessNotice.class);
        util.exportExcel(response, list, "流程记录数据");
    }

    /**
     * 获取流程记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/{processRuntimeId}")
    public AjaxResult getInfo(@PathVariable("processRuntimeId") Long processRuntimeId)
    {
        return AjaxResult.success(sysProcessNoticeService.selectSysProcessNoticeByProcessRuntimeId(processRuntimeId));
    }

    /**
     * 新增流程记录
     */
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "流程记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysProcessNotice sysProcessNotice)
    {
        return toAjax(sysProcessNoticeService.insertSysProcessNotice(sysProcessNotice));
    }

    /**
     * 修改流程记录
     */
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "流程记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProcessNotice sysProcessNotice)
    {
        return toAjax(sysProcessNoticeService.updateSysProcessNotice(sysProcessNotice));
    }

    /**
     * 删除流程记录
     */
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "流程记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{processRuntimeIds}")
    public AjaxResult remove(@PathVariable Long[] processRuntimeIds)
    {
        return toAjax(sysProcessNoticeService.deleteSysProcessNoticeByProcessRuntimeIds(processRuntimeIds));
    }
}

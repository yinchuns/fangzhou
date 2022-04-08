package com.ruoyi.system.controller;

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
import com.ruoyi.system.domain.SysProcess;
import com.ruoyi.system.service.ISysProcessService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 流程Controller
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
@RestController
@RequestMapping("/system/process/process")
public class SysProcessController extends BaseController
{
    @Autowired
    private ISysProcessService sysProcessService;

    /**
     * 查询流程列表
     */
    @PreAuthorize("@ss.hasPermi('system:process:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysProcess sysProcess)
    {
        startPage();
        List<SysProcess> list = sysProcessService.selectSysProcessList(sysProcess);
        return getDataTable(list);
    }

    /**
     * 导出流程列表
     */
    @PreAuthorize("@ss.hasPermi('system:process:export')")
    @Log(title = "流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysProcess sysProcess)
    {
        List<SysProcess> list = sysProcessService.selectSysProcessList(sysProcess);
        ExcelUtil<SysProcess> util = new ExcelUtil<SysProcess>(SysProcess.class);
        util.exportExcel(response, list, "流程数据");
    }

    /**
     * 获取流程详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:process:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysProcessService.selectSysProcessById(id));
    }

    /**
     * 新增流程
     */
    @PreAuthorize("@ss.hasPermi('system:process:add')")
    @Log(title = "流程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysProcess sysProcess)
    {
        sysProcess.setCreateBy(getUsername());
        return toAjax(sysProcessService.insertSysProcess(sysProcess));
    }

    /**
     * 修改流程
     */
    @PreAuthorize("@ss.hasPermi('system:process:edit')")
    @Log(title = "流程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProcess sysProcess)
    {
        sysProcess.setUpdateBy(getUsername());
        return toAjax(sysProcessService.updateSysProcess(sysProcess));
    }

    /**
     * 删除流程
     */
    @PreAuthorize("@ss.hasPermi('system:process:remove')")
    @Log(title = "流程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysProcessService.deleteSysProcessByIds(ids));
    }
}

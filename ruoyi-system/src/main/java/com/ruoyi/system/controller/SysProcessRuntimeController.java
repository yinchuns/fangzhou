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
import com.ruoyi.system.domain.SysProcessRuntime;
import com.ruoyi.system.service.ISysProcessRuntimeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 流程实例Controller
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
@RestController
@RequestMapping("/system/process/processRuntime")
public class SysProcessRuntimeController extends BaseController
{
    @Autowired
    private ISysProcessRuntimeService sysProcessRuntimeService;

    /**
     * 查询流程实例列表
     */
    @PreAuthorize("@ss.hasPermi('system:runtime:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysProcessRuntime sysProcessRuntime)
    {
        startPage();
        List<SysProcessRuntime> list = sysProcessRuntimeService.selectSysProcessRuntimeList(sysProcessRuntime);
        return getDataTable(list);
    }

    /**
     * 导出流程实例列表
     */
    @PreAuthorize("@ss.hasPermi('system:runtime:export')")
    @Log(title = "流程实例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysProcessRuntime sysProcessRuntime)
    {
        List<SysProcessRuntime> list = sysProcessRuntimeService.selectSysProcessRuntimeList(sysProcessRuntime);
        ExcelUtil<SysProcessRuntime> util = new ExcelUtil<SysProcessRuntime>(SysProcessRuntime.class);
        util.exportExcel(response, list, "流程实例数据");
    }

    /**
     * 获取流程实例详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:runtime:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysProcessRuntimeService.selectSysProcessRuntimeById(id));
    }

    /**
     * 新增流程实例
     */
    @PreAuthorize("@ss.hasPermi('system:runtime:add')")
    @Log(title = "流程实例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysProcessRuntime sysProcessRuntime)
    {
        sysProcessRuntime.setCreateBy(getUsername());
        return toAjax(sysProcessRuntimeService.insertSysProcessRuntime(sysProcessRuntime));
    }

    /**
     * 修改流程实例
     */
    @PreAuthorize("@ss.hasPermi('system:runtime:edit')")
    @Log(title = "流程实例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProcessRuntime sysProcessRuntime)
    {
        return toAjax(sysProcessRuntimeService.updateSysProcessRuntime(sysProcessRuntime));
    }

    /**
     * 删除流程实例
     */
    @PreAuthorize("@ss.hasPermi('system:runtime:remove')")
    @Log(title = "流程实例", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysProcessRuntimeService.deleteSysProcessRuntimeByIds(ids));
    }
}

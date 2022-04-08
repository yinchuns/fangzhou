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
import com.ruoyi.system.domain.SysProcessNodeApprover;
import com.ruoyi.system.service.ISysProcessNodeApproverService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 节点审核人Controller
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
@RestController
@RequestMapping("/system/process/approver")
public class SysProcessNodeApproverController extends BaseController
{
    @Autowired
    private ISysProcessNodeApproverService sysProcessNodeApproverService;

    /**
     * 查询节点审核人列表
     */
    @PreAuthorize("@ss.hasPermi('system:approver:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysProcessNodeApprover sysProcessNodeApprover)
    {
        startPage();
        List<SysProcessNodeApprover> list = sysProcessNodeApproverService.selectSysProcessNodeApproverList(sysProcessNodeApprover);
        return getDataTable(list);
    }

    /**
     * 导出节点审核人列表
     */
    @PreAuthorize("@ss.hasPermi('system:approver:export')")
    @Log(title = "节点审核人", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysProcessNodeApprover sysProcessNodeApprover)
    {
        List<SysProcessNodeApprover> list = sysProcessNodeApproverService.selectSysProcessNodeApproverList(sysProcessNodeApprover);
        ExcelUtil<SysProcessNodeApprover> util = new ExcelUtil<SysProcessNodeApprover>(SysProcessNodeApprover.class);
        util.exportExcel(response, list, "节点审核人数据");
    }

    /**
     * 获取节点审核人详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:approver:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysProcessNodeApproverService.selectSysProcessNodeApproverById(id));
    }

    /**
     * 新增节点审核人
     */
    @PreAuthorize("@ss.hasPermi('system:approver:add')")
    @Log(title = "节点审核人", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysProcessNodeApprover sysProcessNodeApprover)
    {
        sysProcessNodeApprover.setCreateBy(getUsername());
        return toAjax(sysProcessNodeApproverService.insertSysProcessNodeApprover(sysProcessNodeApprover));
    }

    /**
     * 修改节点审核人
     */
    @PreAuthorize("@ss.hasPermi('system:approver:edit')")
    @Log(title = "节点审核人", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProcessNodeApprover sysProcessNodeApprover)
    {
        sysProcessNodeApprover.setUpdateBy(getUsername());
        return toAjax(sysProcessNodeApproverService.updateSysProcessNodeApprover(sysProcessNodeApprover));
    }

    /**
     * 删除节点审核人
     */
    @PreAuthorize("@ss.hasPermi('system:approver:remove')")
    @Log(title = "节点审核人", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysProcessNodeApproverService.deleteSysProcessNodeApproverByIds(ids));
    }
}

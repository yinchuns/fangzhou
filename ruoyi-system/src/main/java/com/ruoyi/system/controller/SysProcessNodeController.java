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
import com.ruoyi.system.domain.SysProcessNode;
import com.ruoyi.system.service.ISysProcessNodeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 流程节点Controller
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
@RestController
@RequestMapping("/system/process/processNode")
public class SysProcessNodeController extends BaseController
{
    @Autowired
    private ISysProcessNodeService sysProcessNodeService;

    /**
     * 查询流程节点列表
     */
    @PreAuthorize("@ss.hasPermi('system:node:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysProcessNode sysProcessNode)
    {
        startPage();
        List<SysProcessNode> list = sysProcessNodeService.selectSysProcessNodeList(sysProcessNode);
        return getDataTable(list);
    }

    /**
     * 导出流程节点列表
     */
    @PreAuthorize("@ss.hasPermi('system:node:export')")
    @Log(title = "流程节点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysProcessNode sysProcessNode)
    {
        List<SysProcessNode> list = sysProcessNodeService.selectSysProcessNodeList(sysProcessNode);
        ExcelUtil<SysProcessNode> util = new ExcelUtil<SysProcessNode>(SysProcessNode.class);
        util.exportExcel(response, list, "流程节点数据");
    }

    /**
     * 获取流程节点详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:node:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysProcessNodeService.selectSysProcessNodeById(id));
    }

    /**
     * 新增流程节点
     */
    @PreAuthorize("@ss.hasPermi('system:node:add')")
    @Log(title = "流程节点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysProcessNode sysProcessNode)
    {
        sysProcessNode.setCreateBy(getUsername());
        return toAjax(sysProcessNodeService.insertSysProcessNode(sysProcessNode));
    }

    /**
     * 修改流程节点
     */
    @PreAuthorize("@ss.hasPermi('system:node:edit')")
    @Log(title = "流程节点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProcessNode sysProcessNode)
    {
        sysProcessNode.setUpdateBy(getUsername());
        return toAjax(sysProcessNodeService.updateSysProcessNode(sysProcessNode));
    }

    /**
     * 删除流程节点
     */
    @PreAuthorize("@ss.hasPermi('system:node:remove')")
    @Log(title = "流程节点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysProcessNodeService.deleteSysProcessNodeByIds(ids));
    }
}

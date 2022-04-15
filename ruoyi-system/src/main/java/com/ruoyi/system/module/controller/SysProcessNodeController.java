package com.ruoyi.system.module.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.module.domain.SysProcess;
import com.ruoyi.system.module.service.ISysProcessService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
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
import com.ruoyi.system.module.domain.SysProcessNode;
import com.ruoyi.system.module.service.ISysProcessNodeService;
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

    @Autowired
    private ISysProcessService sysProcessService;

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
    public AjaxResult add(@RequestBody @Validated  SysProcessNode sysProcessNode)
    {
        sysProcessNode.setCreateBy(getUsername());
        //判断对应流程下的节点名称是否已存在
        Integer isAlready=sysProcessNodeService.selectNodeIsAlready(sysProcessNode);
        if(isAlready>0){
            return AjaxResult.error("该流程下节点名称已存在！");
        }
        //生成节点序号
        Integer step=sysProcessNodeService.selectProcessNodeCount(sysProcessNode.getProcessMark())+1;
        sysProcessNode.setStep(step);
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
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        String processMark="";
        for(Long id:ids){
            SysProcessNode n=sysProcessNodeService.selectSysProcessNodeById(id);
            processMark=n.getProcessMark();
            SysProcess p=new SysProcess();
            p.setProcessMark(n.getProcessMark());
            p=sysProcessService.selectProcessByCondition(p);
            if(p!=null && p.getStatus().equals(1)){
                return AjaxResult.error("当前流程已开启，节点不能删除！");
            }
        }
        try {
            sysProcessNodeService.deleteSysProcessNodeByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        //获取对应流程所有节点
        SysProcessNode n1=new SysProcessNode();
        n1.setProcessMark(processMark);
        List<SysProcessNode> nodeList=sysProcessNodeService.selectSysProcessNodeList(n1);
        Integer genStep=0;
        //重新排列序号
        for(SysProcessNode n:nodeList){
            genStep++;
            n.setStep(genStep);
            sysProcessNodeService.updateSysProcessNode(n);
        }
        return AjaxResult.success("删除成功！");
    }
}

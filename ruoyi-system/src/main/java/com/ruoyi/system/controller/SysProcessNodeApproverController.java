package com.ruoyi.system.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.SysProcess;
import com.ruoyi.system.domain.SysProcessNode;
import com.ruoyi.system.domain.SysProcessRuntime;
import com.ruoyi.system.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysProcessNodeApprover;
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
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private ISysProcessRuntimeService sysProcessRuntimeService;
    @Autowired
    private ISysProcessNodeService sysProcessNodeService;
    @Autowired
    private ISysProcessService sysProcessService;

    /**
     * 获取对应节点下的所有审核人
     * 需要携带参数：
     * 流程标识：processMark
     * 当前节点：currentNode
     * */
    @GetMapping("/getAproverlist")
    public TableDataInfo getAproverlist(SysProcessRuntime sysProcessRuntime)
    {
        //获取下一个节点信息
        SysProcessNode node=new SysProcessNode();
        node.setProcessMark(sysProcessRuntime.getProcessMark());
        Integer nextNode=sysProcessRuntime.getCurrentNode()+1;
        node.setStep(nextNode);
        node=sysProcessNodeService.selectNodeByProcessMarkAndStep(node);

        //根据nodeId获取对应审核人
        SysProcessNodeApprover a=new SysProcessNodeApprover();
        a.setNodeId(node.getId());
        return getDataTable(sysProcessNodeApproverService.selectSysProcessNodeApproverList(a));
    }

    /**
     * 通过部门和用户名获取用户信息
     */
    @GetMapping("/getUserlist")
    public TableDataInfo list(SysUser u)
    {
        startPage();
        List<SysUser> uList=sysUserService.selectUserList(u);
        if(uList.size()>0){
            for(SysUser u1:uList){
                String deptName="";
                //获取部门信息
               SysDept dept= sysDeptService.selectDeptById(u1.getDeptId());
               if(dept.getAncestors().contains(",")){
                   List<String> deptIds = Arrays.asList(dept.getAncestors().split(","));
                   for(String id:deptIds){
                       if(!id.equals("0")){
                           SysDept dept1= sysDeptService.selectDeptById(Long.parseLong(id));
                           deptName+=dept1.getDeptName()+"—";
                       }
                   }
               }
                deptName=deptName+dept.getDeptName();
                u1.setDeptName(deptName);

                //岗位角色
                String postName=sysUserService.selectUserPostGroup(u1.getUserName());
                u1.setPostName(postName);
            }
        }
        return getDataTable(uList);
    }

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
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult add(@RequestBody @Validated SysProcessNodeApprover sysProcessNodeApprover)
    {
        if(sysProcessNodeApprover.getApproverIds()!=null && sysProcessNodeApprover.getApproverIds().size()!=0){
            for(Long i:sysProcessNodeApprover.getApproverIds()){
                SysProcessNodeApprover a=new SysProcessNodeApprover();
                a.setApproverId(i);
                a.setNodeId(sysProcessNodeApprover.getNodeId());
                List<SysProcessNodeApprover> aList=sysProcessNodeApproverService.selectSysProcessNodeApproverList(a);
                if(aList!=null && aList.size()!=0){
                    continue;
                }
                if(sysProcessNodeApprover.getRemark()!=null){
                    a.setRemark(sysProcessNodeApprover.getRemark());
                }
                a.setCreateBy(getUsername());
                sysProcessNodeApproverService.insertSysProcessNodeApprover(a);
            }
        }else{
            return AjaxResult.error("请选择审核人！");
        }

        return AjaxResult.success("新增成功！");
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
    @DeleteMapping("/{ids}/{nodeId}")
    public AjaxResult remove(@PathVariable("ids") Long[] ids,@PathVariable("nodeId") Long nodeId)
    {
        //获取节点信息
        SysProcessNode node=sysProcessNodeService.selectSysProcessNodeById(nodeId);
        //获取流程，判断流程是否已开启
        SysProcess p=new SysProcess();
        p.setProcessMark(node.getProcessMark());
        p=sysProcessService.selectProcessByCondition(p);
        //判断流程是否已开启
        if(p.getStatus().equals(1)){//若已开启
            Integer approverCount=sysProcessNodeApproverService.selectApproverCountByNode(nodeId);
            if(approverCount-ids.length<1){
                return AjaxResult.error("当前流程已开启,该节点必须保留一个审核人！");
            }
        }
        return toAjax(sysProcessNodeApproverService.deleteSysProcessNodeApproverByIds(ids));
    }
}

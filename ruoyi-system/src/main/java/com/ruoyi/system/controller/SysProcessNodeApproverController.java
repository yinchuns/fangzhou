package com.ruoyi.system.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
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

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 通过部门和用户名获取用户信息
     */
    @GetMapping("/getUserlist")
    public TableDataInfo list(SysUser u)
    {
        /*if((u.getUserName()==null || ("").equals(u.getUserName())) && ( u.getDeptId()==null || ("").equals(u.getDeptId()))){
            u.setUserName("0");
        }*/
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
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysProcessNodeApproverService.deleteSysProcessNodeApproverByIds(ids));
    }
}

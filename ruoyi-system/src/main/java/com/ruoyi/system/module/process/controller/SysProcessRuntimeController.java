package com.ruoyi.system.module.process.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.module.process.domain.SysProcess;
import com.ruoyi.system.module.process.domain.SysProcessNode;
import com.ruoyi.system.module.process.domain.SysProcessNotice;
import com.ruoyi.system.module.process.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.ruoyi.system.module.process.domain.SysProcessRuntime;
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
    @Autowired
    private ISysProcessService sysProcessService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysProcessNodeService sysProcessNodeService;
    @Autowired
    private ISysProcessNoticeService sysProcessNoticeService;

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
     * 根据formId获取流程实例详细信息
     * @param  formId
     */
    @PreAuthorize("@ss.hasPermi('system:runtime:query')")
    @GetMapping(value = "/getInfoByFormId/{formId}")
    public AjaxResult getInfoByFormId(@PathVariable("formId") Long formId)
    {
        SysProcessRuntime sysProcessRuntime=sysProcessRuntimeService.selectProcessRuntimeByFormId(formId);
        //根据流程标志获取对应所有流程节点
        SysProcessNode node=new SysProcessNode();
        node.setProcessMark(sysProcessRuntime.getProcessMark());
        List<SysProcessNode> nodeList= sysProcessNodeService.selectSysProcessNodeList(node);

        //生成流程节点树
        genProcessNodeTree(sysProcessRuntime, nodeList);

        //根据流程实例id获取流程审核日志
        List<SysProcessNotice> noticeList=sysProcessNoticeService.selectSysProcessNoticeByProcessRuntimeId(sysProcessRuntime.getId());
        sysProcessRuntime.setSysProcessNoticeList(noticeList);

        return AjaxResult.success(sysProcessRuntime);
    }

    /**
     * 根据流程实例Id获取流程实例详细信息
     * 传入参数id
     */
    @PreAuthorize("@ss.hasPermi('system:runtime:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        SysProcessRuntime sysProcessRuntime=sysProcessRuntimeService.selectSysProcessRuntimeById(id);
        //根据流程标志获取对应所有流程节点
        SysProcessNode node=new SysProcessNode();
        node.setProcessMark(sysProcessRuntime.getProcessMark());
        List<SysProcessNode> nodeList= sysProcessNodeService.selectSysProcessNodeList(node);

        //生成流程节点树
        genProcessNodeTree(sysProcessRuntime, nodeList);

        //根据流程实例id获取流程审核日志
        List<SysProcessNotice> noticeList=sysProcessNoticeService.selectSysProcessNoticeByProcessRuntimeId(id);
        sysProcessRuntime.setSysProcessNoticeList(noticeList);

        return AjaxResult.success(sysProcessRuntime);
    }

    /**
     * 生成流程节点树
     * @param sysProcessRuntime  流程实例对象
     * @param nodeList   对应流程所有节点
     */
    private void genProcessNodeTree(SysProcessRuntime sysProcessRuntime, List<SysProcessNode> nodeList) {
        //生成流程节点树
        List<SysProcessNode> showNodeList=new ArrayList<SysProcessNode>();
        SysProcessNode node0=new SysProcessNode();
        //发起人节点
        node0.setNodeName("发起人");
        node0.setStep(0);
        if(sysProcessRuntime.getCurrentNode().equals(0)){
            node0.setCheckStatus("1");
        }else{
            node0.setCheckStatus("0");
        }
        showNodeList.add(node0);
        //流程节点
        for(SysProcessNode n: nodeList){
            if(sysProcessRuntime.getCurrentNode().equals(n.getStep())){
                n.setCheckStatus("1");
            }else{
                n.setCheckStatus("0");
            }
            showNodeList.add(n);
        }
        //通过节点
        SysProcessNode nodePass=new SysProcessNode();
        nodePass.setNodeName("通过");
        if(sysProcessRuntime.getStatus().equals(3)){
            nodePass.setCheckStatus("1");
        }
        showNodeList.add(nodePass);
        sysProcessRuntime.setSysProcessNodeList(showNodeList);
    }

    /**
     * 新增流程实例
     * 携带参数:
     * formId (表单id)
     * processMark （流程标识）
     */
    @PreAuthorize("@ss.hasPermi('system:runtime:add')")
    @Log(title = "流程实例", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult add(@RequestBody SysProcessRuntime sysProcessRuntime)
    {
        //获取对应流程信息
        SysProcess p=new SysProcess();
        p.setProcessMark(sysProcessRuntime.getProcessMark());
        p=sysProcessService.selectProcessByCondition(p);
        //判断是否需要开启
        if(p.getStatus().equals(0)){
            //开启流程
            SysProcess p1=new SysProcess();
            p1.setStatus(1);
            p1.setId(p.getId());
            sysProcessService.updateSysProcess(p1);
        }
        //初始化流程实例状态： 审批中
        sysProcessRuntime.setStatus(1);
        //写入流程名称
        sysProcessRuntime.setProcessName(p.getProcessName());
        //写入创建人
        sysProcessRuntime.setCreateBy(getUsername());
        //初始化上一个节点
        sysProcessRuntime.setPreviousNode(0);
        //初始化当前节点
        sysProcessRuntime.setCurrentNode(1);
        return toAjax(sysProcessRuntimeService.insertSysProcessRuntime(sysProcessRuntime));
    }

    /**
     * 修改流程实例
     * 携带参数：
     * id
     * status,当退回时设置该参数为2，其他时候不用传入值
     */
    @PreAuthorize("@ss.hasPermi('system:runtime:edit')")
    @Log(title = "流程实例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProcessRuntime sysProcessRuntime)
    {
        //获取当前流程信息
        SysProcessRuntime pr=sysProcessRuntimeService.selectSysProcessRuntimeById(sysProcessRuntime.getId());
        //判断当前是否是被退回，若是退回，就退回给发起人
        if(sysProcessRuntime.getStatus()!=null && sysProcessRuntime.getStatus().equals(2)){
            sysProcessRuntime.setCurrentNode(0);
            //获取发起人信息
            SysUser u=sysUserService.selectUserByUserName(pr.getCreateBy());
            sysProcessRuntime.setApproverId(u.getUserId());
        }

        //判断当前流程是否是退回后再提交（表单被退回后发起人修改完成了再提交）
        if(pr.getStatus().equals(2)){
            sysProcessRuntime.setCurrentNode(pr.getPreviousNode());
            sysProcessRuntime.setApproverId(pr.getPreviousApproverId());
        }

        //既不是退回，也不是退回后再提交，判断当前节点是否已结束
        if(!sysProcessRuntime.getStatus().equals(2) && !pr.getStatus().equals(2)){
            Integer nextNode=pr.getCurrentNode()+1;
            //获取当前流程最大节点
            Integer maxNode=sysProcessNodeService.selectMaxNode(pr.getProcessMark());
            if(nextNode>maxNode){
                sysProcessRuntime.setStatus(3);
            }
            sysProcessRuntime.setCurrentNode(nextNode);
        }
        //写入上一个审批节点
        sysProcessRuntime.setPreviousNode(pr.getCurrentNode());
        //写入上一个审批人
        sysProcessRuntime.setPreviousApproverId(pr.getApproverId());

        //写入流程审批日志
        setProcesLog(sysProcessRuntime, pr);

        return toAjax(sysProcessRuntimeService.updateSysProcessRuntime(sysProcessRuntime));
    }

    /**
     * 写入流程审批日志
     * @param sysProcessRuntime 审批中流程实例
     * @param pr  当前流程实例
     */
    private void setProcesLog(SysProcessRuntime sysProcessRuntime, SysProcessRuntime pr) {
        //流程审批日志 sysProcessNoticeService
        SysProcessNotice notice=new SysProcessNotice();
        notice.setProcessRuntimeId(pr.getId());
        //写入审批状态
        if(sysProcessRuntime.getStatus()!=null && sysProcessRuntime.getStatus().equals(2)){
            notice.setApproveStatus(2);
        }else{
            notice.setApproveStatus(1);
        }
        //写入审批人
        SysUser u=sysUserService.selectUserById(pr.getApproverId());
        notice.setApprover(u.getUserName());
        //写入审批时间
        notice.setApproveTime(new Date());
        //写入审批信息
        if(sysProcessRuntime.getApproveMsg()!=null){
            notice.setApproveMsg(sysProcessRuntime.getApproveMsg());
        }
        //写入节点名称
        //根据流程标识和当前节点，获取节点名称
        SysProcessNode node=new SysProcessNode();
        node.setProcessMark(pr.getProcessMark());
        node.setStep(pr.getCurrentNode());
        String nodeName=sysProcessNodeService.selectNodeName(node);
        notice.setNodeName(nodeName);
        sysProcessNoticeService.insertSysProcessNotice(notice);
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

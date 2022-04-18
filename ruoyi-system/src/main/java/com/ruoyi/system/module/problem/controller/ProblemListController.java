package com.ruoyi.system.module.problem.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.module.problem.domain.ProblemList;
import com.ruoyi.system.module.problem.service.IProblemListService;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 问题清单Controller
 * 
 * @author ruoyi
 * @date 2022-04-15
 */
@RestController
@RequestMapping("/common/module/problemlist")
public class ProblemListController extends BaseController
{
    @Autowired
    private IProblemListService problemListService;

    /**
     * 查询问题清单列表
     */
    @PreAuthorize("@ss.hasPermi('module:list:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProblemList problemList)
    {
        startPage();
        List<ProblemList> list = problemListService.selectProblemListList(problemList);
        return getDataTable(list);
    }

    /**
     * 导出问题清单列表
     */
    @PreAuthorize("@ss.hasPermi('module:list:export')")
    @Log(title = "问题清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProblemList problemList)
    {
        List<ProblemList> list = problemListService.selectProblemListList(problemList);
        ExcelUtil<ProblemList> util = new ExcelUtil<ProblemList>(ProblemList.class);
        util.exportExcel(response, list, "问题清单数据");
    }

    /**
     * 获取问题清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('module:list:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(problemListService.selectProblemListById(id));
    }

    /**
     * 新增问题清单
     */
    @PreAuthorize("@ss.hasPermi('module:list:add')")
    @Log(title = "问题清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProblemList problemList)
    {
        return toAjax(problemListService.insertProblemList(problemList));
    }

    /**
     * 修改问题清单
     */
    @PreAuthorize("@ss.hasPermi('module:list:edit')")
    @Log(title = "问题清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProblemList problemList)
    {
        return toAjax(problemListService.updateProblemList(problemList));
    }

    /**
     * 删除问题清单
     */
    @PreAuthorize("@ss.hasPermi('module:list:remove')")
    @Log(title = "问题清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(problemListService.deleteProblemListByIds(ids));
    }
}

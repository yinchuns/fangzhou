package com.ruoyi.system.module.problem.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.module.problem.domain.ProblemList;
import com.ruoyi.system.module.problem.mapper.ProblemListMapper;
import com.ruoyi.system.module.problem.service.IProblemListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 问题清单Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-04-15
 */
@Service
public class ProblemListServiceImpl implements IProblemListService
{
    @Autowired
    private ProblemListMapper problemListMapper;

    /**
     * 查询问题清单
     * 
     * @param id 问题清单主键
     * @return 问题清单
     */
    @Override
    public ProblemList selectProblemListById(Long id)
    {
        return problemListMapper.selectProblemListById(id);
    }

    /**
     * 查询问题清单列表
     * 
     * @param problemList 问题清单
     * @return 问题清单
     */
    @Override
    public List<ProblemList> selectProblemListList(ProblemList problemList)
    {
        return problemListMapper.selectProblemListList(problemList);
    }

    /**
     * 新增问题清单
     * 
     * @param problemList 问题清单
     * @return 结果
     */
    @Override
    public int insertProblemList(ProblemList problemList)
    {
        problemList.setCreateTime(DateUtils.getNowDate());
        return problemListMapper.insertProblemList(problemList);
    }

    /**
     * 修改问题清单
     * 
     * @param problemList 问题清单
     * @return 结果
     */
    @Override
    public int updateProblemList(ProblemList problemList)
    {
        problemList.setUpdateTime(DateUtils.getNowDate());
        return problemListMapper.updateProblemList(problemList);
    }

    /**
     * 批量删除问题清单
     * 
     * @param ids 需要删除的问题清单主键
     * @return 结果
     */
    @Override
    public int deleteProblemListByIds(Long[] ids)
    {
        return problemListMapper.deleteProblemListByIds(ids);
    }

    /**
     * 删除问题清单信息
     * 
     * @param id 问题清单主键
     * @return 结果
     */
    @Override
    public int deleteProblemListById(Long id)
    {
        return problemListMapper.deleteProblemListById(id);
    }
}

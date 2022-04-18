package com.ruoyi.system.module.problem.mapper;

import java.util.List;
import com.ruoyi.system.module.problem.domain.ProblemList;

/**
 * 问题清单Mapper接口
 * 
 * @author ruoyi
 * @date 2022-04-15
 */
public interface ProblemListMapper 
{
    /**
     * 查询问题清单
     * 
     * @param id 问题清单主键
     * @return 问题清单
     */
    public ProblemList selectProblemListById(Long id);

    /**
     * 查询问题清单列表
     * 
     * @param problemList 问题清单
     * @return 问题清单集合
     */
    public List<ProblemList> selectProblemListList(ProblemList problemList);

    /**
     * 新增问题清单
     * 
     * @param problemList 问题清单
     * @return 结果
     */
    public int insertProblemList(ProblemList problemList);

    /**
     * 修改问题清单
     * 
     * @param problemList 问题清单
     * @return 结果
     */
    public int updateProblemList(ProblemList problemList);

    /**
     * 删除问题清单
     * 
     * @param id 问题清单主键
     * @return 结果
     */
    public int deleteProblemListById(Long id);

    /**
     * 批量删除问题清单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProblemListByIds(Long[] ids);
}

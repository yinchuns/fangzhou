package com.ruoyi.system.module.process.mapper;

import java.util.List;
import com.ruoyi.system.module.process.domain.SysProcessNode;
import org.apache.ibatis.annotations.Param;

/**
 * 流程节点Mapper接口
 * 
 * @author ruoyi
 * @date 2022-04-08
 */
public interface SysProcessNodeMapper 
{
    /**
     * 根据流程标识和节点序号，获取对应节点详情
     * @param sysProcessNode
     * @return nodeName   节点详情
     */
    public SysProcessNode selectNodeByProcessMarkAndStep(SysProcessNode sysProcessNode);

    /**
     * 根据流程标识和节点序号，获取对应节点名称
     * @param sysProcessNode
     * @return nodeName   节点名称
     */
    String selectNodeName (SysProcessNode sysProcessNode);

    /**
     * 获取对应流程最大节点
     * @param processMark 流程标识
     * @return 节点序号
     */
    Integer selectMaxNode(@Param("processMark") String processMark);

    /**
     * 根据节点名称和流程标识查询 该节点名称在对应流程下是否已经存在
     * @param sysProcessNode
     * @return
     */
    Integer selectNodeIsAlready (SysProcessNode sysProcessNode);

    /**
     * 根据流程标识获取对应流程下节点数量
     * @param processMark
     * @return
     */
    Integer selectProcessNodeCount(@Param("processMark") String processMark);
    
    /**
     * 查询流程节点
     * 
     * @param id 流程节点主键
     * @return 流程节点
     */
    public SysProcessNode selectSysProcessNodeById(Long id);

    /**
     * 查询流程节点列表
     * 
     * @param sysProcessNode 流程节点
     * @return 流程节点集合
     */
    public List<SysProcessNode> selectSysProcessNodeList(SysProcessNode sysProcessNode);

    /**
     * 新增流程节点
     * 
     * @param sysProcessNode 流程节点
     * @return 结果
     */
    public int insertSysProcessNode(SysProcessNode sysProcessNode);

    /**
     * 修改流程节点
     * 
     * @param sysProcessNode 流程节点
     * @return 结果
     */
    public int updateSysProcessNode(SysProcessNode sysProcessNode);

    /**
     * 删除流程节点
     * 
     * @param id 流程节点主键
     * @return 结果
     */
    public int deleteSysProcessNodeById(Long id);

    /**
     * 批量删除流程节点
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysProcessNodeByIds(Long[] ids);
}

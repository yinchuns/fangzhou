<template>
  <el-form ref="submitForm" size="small" :inline="true" v-show="showSumb" label-width="68px">
    <el-button type="primary"    plain
               icon="el-icon-plus"  @click="submitForm()">提交</el-button>
  </el-form>

    <el-form ref="submitFormA" size="small" :inline="true" v-show="showApprove" label-width="68px">
      <el-form-item label="审批意见" prop="approveMsg">
        <el-input   v-model="approveMsg" placeholder="请输入审批意见"   style="width:50%;margin-left: 28px"/>
      </el-form-item>
      <el-button type="primary"    plain
                 icon="el-icon-plus"  @click="submitForm(1)">通过</el-button>
      <el-button type="primary"    plain
                 icon="el-icon-plus"  @click="submitForm(2)">退回</el-button>
    </el-form>

    <el-row :gutter="10" >
      <el-col :span="1.5" v-for="(item,index) of nodeList" >
        <view :class="item.checkStatus=1?'flowCheckBox':'flowBox'">{{item.nodeName}}</view>>
        <view v-if="index > nodeList.length-1" >"————>"</view>>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="noticeList">
      <el-table-column label="节点名称" align="center" prop="nodeName" />
      <el-table-column label="审批时间 " align="center" prop="approveTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.approveTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审批人" align="center" prop="approver" />
      <el-table-column label="审批意见  " align="center" prop="approveMsg" />
      <el-table-column label="审批状态 " align="center" prop="approveStatus=1？'通过'：'驳回'"/>
    </el-table>

  <!-- 指派节点审核人 -->
  <el-dialog :title="title" :visible.sync="openApprover" width="600px" append-to-body>
    <el-table v-loading="approverLoading" :data="approverList" @current-change="clickChange">
      <el-table-column label="选择" width="55">
        　　　　<template slot-scope="scope">
        　　　　　　<el-radio v-model="tableRadio" :label="scope.row"><i></i></el-radio>
        　　　　</template>
      </el-table-column>
      <el-table-column label="节点审核人" align="center" prop="approverName" />
      <el-table-column label="备注 " align="center" prop="remark" />
    </el-table>
    <el-button type="primary"   plain
               icon="el-icon-plus"
               @click="confirmApprover()">选择</el-button>
  </el-dialog>
</template>
<style>
.flowCheckBox{
  border-width: 1px;
  padding: 8px;
  border-style: solid;
  border-color: #ECECEC;
  background-color: #F08080;
  color:white;
}

.flowBox{
  border-width: 1px;
  padding: 8px;
  border-style: solid;
  border-color: #ECECEC;
  background-color: #D8E2EB;
  color:white;
}

</style>


<script>

import { listRuntime, getRuntime, delRuntime, addRuntime, updateRuntime } from "@/api/system/runtime";
import {getRuntimeByFormId} from "../../../api/system/runtime";
import {getAproverlist} from "../../../api/system/approver";


export default {
  name: "Runtime",
  data() {
    return {
      approverLoading: false,
      openApprover:false,
      //显示提交按钮
      showSumb: false,
      //显示审核页面
      showApprove: false,
      //节点数据树
      nodeList:[],
      //流程日志列表
      noticeList: [],
      // 流程实例对象
      processRunTime: {},
      //审核意见
      approveMsg:"",
      //审核人列表
      approverList: [],
      //选中id
      tableRadio:"",
      rules: {
      }
    };
  },
  created() {
    this.getProcessRunTime();
  },
  methods: {
    clickChange (item) {
      this.tableRadio = item
    },
    /** 选中审核人*/
    confirmApprover(){
      this.processRunTime.approveId=tableRadio;
          if(processRunTime.formId!=null){
           //执行流程提交创建动作
            addRuntime(this.processRunTime).then(response => {
              this.$modal.msgSuccess("成功");
              this.open = false;
             // this.getProcessRunTime();
            });
          }else if(processRunTime.id!=null){
            //执行流程通过动作
            updateRuntime(this.processRunTime).then(response => {
              this.$modal.msgSuccess("成功");
              this.open = false;
             // this.getProcessRunTime();
            });
          }
          this.openApprover = false;

    },
    /** 获取审核人列表 */
    getNodeApproverList() {
        getAproverlist(this.processRunTime).then(response => {
          if(response.rows!=null){
            this.approverList = response.rows;
            this.approverLoading=false;
          }
        });
    },
    /** 查询流程实例列表 */
    getProcessRunTime() {
      if(this.processRunTime.id!=null){
        getRuntime(this.processRunTime.id).then(response => {
          if(response.rows!=null){
            this.processRunTime = response.rows;
            this.nodeList=this.processRunTime.nodeList;
            this.noticeList=this.processRunTime.noticeList;
            if(this.processRunTime.currentNode==null || this.processRunTime.currentNode==1){
              this.showSumb=true;
            }else{
              this.showApprove=true;
            }
          }
        });
      }else if(this.processRunTime.formId!=null){
        this.showSumb=true;
        getRuntimeByFormId(this.processRunTime.formId).then(response => {
          if(response.rows!=null){
            this.processRunTime = response.rows;
            this.nodeList=this.processRunTime.nodeList;
            this.noticeList=this.processRunTime.noticeList;
            if(this.processRunTime.currentNode==null || this.processRunTime.currentNode==1){}else{
              this.showApprove=true;
            }
          }
        });
      }
    },
    /** 提交按钮 */
    submitForm(status) {
          if (this.processRunTime.id != null) {
            this.processRunTime.approveMsg=this.approveMsg;
            if(status==2){//退回
              this.processRunTime.status=status;
              //执行退回动作
              updateRuntime(this.processRunTime).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                //this.getProcessRunTime();
              });
            }else{
              //判断是否是最后一个节点
              if(this.processRunTime.currentNode===this.processRunTime.maxNode){
                //执行流程通过动作
                updateRuntime(this.processRunTime).then(response => {
                  this.$modal.msgSuccess("修改成功");
                  this.open = false;
                 // this.getProcessRunTime();
                });
              }else{
                //获取审核人弹框，选择审核人
                this.openApprover = true;
                this.approverLoading=true;
                this.title = "选择审核人";
                this.getNodeApproverList();
              }
            }
          } else {
            //选择审核人弹框，选择审核人后进行下一步操作
            this.openApprover = true;
            this.approverLoading=true;
            this.title = "选择审核人";
            this.getNodeApproverList();
          }
    }
  }
};
</script>

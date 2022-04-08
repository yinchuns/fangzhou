<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程名称" prop="processName">
        <el-input
          v-model="queryParams.processName"
          placeholder="请输入流程名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:process:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:process:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:process:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:process:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!--<el-table-column label="KEY" align="center" prop="id" />-->
      <el-table-column label="流程名称" align="center" prop="processName" />
      <el-table-column label="流程标识 " align="center" prop="processMark" />
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="创建时间 " align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="修改人" align="center" prop="updateBy" />
      <el-table-column label="修改时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="对应表单名称" align="center" prop="formName" />
      <el-table-column label="审批表单页面地址" align="center" prop="approvalFormUrl" />
      <el-table-column label="修改表单页面地址" align="center" prop="updateFormUrl" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleNodeSit(scope.row)"
            v-hasPermi="['system:node:list']"
          >节点配置</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:process:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:process:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改流程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="流程名称" prop="processName">
          <el-input v-model="form.processName" placeholder="请输入流程名称" />
        </el-form-item>
        <el-form-item label="流程标识" prop="processMark">
           <el-input v-model="form.processMark" placeholder="请输入流程标识" />
        </el-form-item>
        <el-form-item label="对应表单名称" prop="formName">
          <el-input v-model="form.formName" placeholder="请输入对应表单名称" />
        </el-form-item>
        <el-form-item label="审批表单页面地址" prop="approvalFormUrl">
          <el-input v-model="form.approvalFormUrl" placeholder="请输入审批表单页面地址" />
        </el-form-item>
        <el-form-item label="修改表单页面地址" prop="updateFormUrl">
          <el-input v-model="form.updateFormUrl" placeholder="请输入修改表单页面地址" />
        </el-form-item>
        <!--<el-form-item label="删除标识" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标识" />
        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>




    <!-- 添加或修改节点对话框 -->
    <el-dialog :title="title" :visible.sync="openNode" width="1200px" append-to-body>
      <el-form ref="formNode" :model="formNode" :rules="rules" label-width="80px">
        <el-form-item label="节点名称" prop="processName">
          <el-input v-model="formNode.nodeName" placeholder="请输入节点名称" />
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="submitNodeForm">新增</el-button>
      <el-table v-loading="nodeLoading" :data="processNodeList" @selection-change="handleSelectionNodeChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="节点名称" align="center" prop="nodeName" />
        <el-table-column label="节点序号 " align="center" prop="step" />
        <el-table-column label="流程标识 " align="center" prop="processMark" />
        <el-table-column label="创建人" align="center" prop="createBy" />
        <el-table-column label="创建时间 " align="center" prop="createTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleNodeDelete(scope.row)"
              v-hasPermi="['system:node:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>

<script>
import { delNode,addNode,listNode,listProcess, getProcess, delProcess, addProcess, updateProcess } from "@/api/system/process";

export default {
  name: "Process",
  data() {
    return {
      //流程标识
      processMarks:"",
      // 遮罩层
      loading: true,
      //节点页面遮罩
      nodeLoading:true,
      // 选中数组
      ids: [],
      nodeIds:[],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 流程表格数据
      processList: [],
      // 流程表格数据
      processNodeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //节点弹框显示状态
      openNode:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processName: null,
      },
      // 表单参数
      form: {},
      //节点表单对象
      formNode:{},
      // 表单校验
      rules: {
      }
    };
  },
 async created() {
   await this.getList();
   await this.getNodeList();
  },
  methods: {
    getNodeList(processMark){
      this.nodeLoading = true;
      const param= {
        processMark: processMark
      }
      listNode(param).then(response => {
        console.log(response.rows);
        if(response.rows){
          this.processNodeList = response.rows;
        }
        //this.total = response.total;
        this.nodeLoading = false;
      });
    },
    /** 查询流程列表 */
    getList() {
      this.loading = true;
      listProcess(this.queryParams).then(response => {
        this.processList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // node取消按钮
    cancelNode() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        processMark: null,
        processMarks:null,
        processName: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        formName: null,
        approvalFormUrl: null,
        updateFormUrl: null,
        delFlag: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    // 多选框选中数据
    handleSelectionNodeChange(selection) {
      this.nodeIds = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加流程";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProcess(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改流程";
      });
    },
    /**配置节点*/
    handleNodeSit(row){
      this.reset();
      const processMark = row.processMark || this.processMarks;
      this.getNodeList(processMark);
      this.processMarks=processMark;
      this.openNode = true;
      this.title = "配置节点";
    },
    /** 提交按钮 */
    submitNodeForm() {
      this.$refs["formNode"].validate(valid => {
        if (valid) {
          if (this.formNode.id != null) {

          } else {
            this.formNode.processMark=this.processMarks;
            addNode(this.formNode).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getNodeList(this.processMarks);
            });
          }
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProcess(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProcess(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /**删除节点操作*/
    handleNodeDelete(row) {
      const ids = row.id || this.nodeIds;
      this.$modal.confirm('是否确认删除流程编号为"' + ids + '"的数据项？').then(function() {
        return delNode(ids);
      }).then(() => {
        this.getNodeList(this.processMark);
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除流程编号为"' + ids + '"的数据项？').then(function() {
        return delProcess(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/process/export', {
        ...this.queryParams
      }, `process_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

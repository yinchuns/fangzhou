<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="上一个节点  " prop="previousNode">
        <el-input
          v-model="queryParams.previousNode"
          placeholder="请输入上一个节点  "
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上一个审批人ID" prop="previousApproverId">
        <el-input
          v-model="queryParams.previousApproverId"
          placeholder="请输入上一个审批人ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="当前审批人ID " prop="approverId">
        <el-input
          v-model="queryParams.approverId"
          placeholder="请输入当前审批人ID "
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="对应表单记录ID " prop="formId">
        <el-input
          v-model="queryParams.formId"
          placeholder="请输入对应表单记录ID "
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流程名称" prop="processName">
        <el-input
          v-model="queryParams.processName"
          placeholder="请输入流程名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="当前节点" prop="currentNode">
        <el-input
          v-model="queryParams.currentNode"
          placeholder="请输入当前节点"
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
          v-hasPermi="['system:runtime:add']"
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
          v-hasPermi="['system:runtime:edit']"
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
          v-hasPermi="['system:runtime:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:runtime:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="runtimeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="流程实例id" align="center" prop="id" />
      <el-table-column label="流程标识" align="center" prop="processMark" />
      <el-table-column label="流程状态：1:流程审批中      2：退回      3：流程结束   99:失败" align="center" prop="status" />
      <el-table-column label="上一个节点  " align="center" prop="previousNode" />
      <el-table-column label="上一个审批人ID" align="center" prop="previousApproverId" />
      <el-table-column label="当前审批人ID " align="center" prop="approverId" />
      <el-table-column label="对应表单记录ID " align="center" prop="formId" />
      <el-table-column label="流程名称" align="center" prop="processName" />
      <el-table-column label="当前节点" align="center" prop="currentNode" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:runtime:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:runtime:remove']"
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

    <!-- 添加或修改流程实例对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="上一个节点  " prop="previousNode">
          <el-input v-model="form.previousNode" placeholder="请输入上一个节点  " />
        </el-form-item>
        <el-form-item label="上一个审批人ID" prop="previousApproverId">
          <el-input v-model="form.previousApproverId" placeholder="请输入上一个审批人ID" />
        </el-form-item>
        <el-form-item label="当前审批人ID " prop="approverId">
          <el-input v-model="form.approverId" placeholder="请输入当前审批人ID " />
        </el-form-item>
        <el-form-item label="对应表单记录ID " prop="formId">
          <el-input v-model="form.formId" placeholder="请输入对应表单记录ID " />
        </el-form-item>
        <el-form-item label="流程名称" prop="processName">
          <el-input v-model="form.processName" placeholder="请输入流程名称" />
        </el-form-item>
        <el-form-item label="当前节点" prop="currentNode">
          <el-input v-model="form.currentNode" placeholder="请输入当前节点" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="删除标识      " prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标识      " />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRuntime, getRuntime, delRuntime, addRuntime, updateRuntime } from "@/api/system/runtime";

export default {
  name: "Runtime",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 流程实例表格数据
      runtimeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        status: null,
        previousNode: null,
        previousApproverId: null,
        approverId: null,
        formId: null,
        processName: null,
        currentNode: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询流程实例列表 */
    getList() {
      this.loading = true;
      listRuntime(this.queryParams).then(response => {
        this.runtimeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        processMark: null,
        status: 0,
        previousNode: null,
        previousApproverId: null,
        approverId: null,
        formId: null,
        createBy: null,
        createTime: null,
        processName: null,
        currentNode: null,
        remark: null,
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加流程实例";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRuntime(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改流程实例";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRuntime(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRuntime(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除流程实例编号为"' + ids + '"的数据项？').then(function() {
        return delRuntime(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/runtime/export', {
        ...this.queryParams
      }, `runtime_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">

      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="noticeList">
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
    </el-table>

  </div>
</template>

<script>
import { listRuntime, getRuntime, delRuntime, addRuntime, updateRuntime } from "@/api/system/runtime";

export default {
  name: "Runtime",
  data() {
    return {
      // 总条数
      total: 0,
      //节点数据树
      nodeList:[],
      //流程日志列表
      noticeList: [],
      // 流程实例对象
      processRunTime: {},
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询流程实例列表 */
    getProcessRunTime() {
      if(){}
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

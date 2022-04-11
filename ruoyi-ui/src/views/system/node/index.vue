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
      <el-form-item label="流程标识  " prop="processMark">
        <el-input
          v-model="queryParams.processMark"
          placeholder="请输入流程标识  "
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="节点名称" prop="nodeName">
        <el-input
          v-model="queryParams.nodeName"
          placeholder="请输入节点名称"
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
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:node:edit']"
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
          v-hasPermi="['system:node:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:node:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="nodeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="流程名称" align="center" prop="processName"/>
      <el-table-column label="流程标识  " align="center" prop="processMark" />
      <el-table-column label="节点序号" align="center" prop="step" />
      <el-table-column label="节点名称" align="center" prop="nodeName" />
      <el-table-column label="创建人  " align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="appointApprover(scope.row)"
            v-hasPermi="['system:approver:list']"
          >审核人</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:node:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:node:remove']"
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

    <!-- 添加或修改流程节点对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="流程标识  " prop="processMark">
          <el-input v-model="form.processMark" placeholder="请输入流程标识  " />
        </el-form-item>
        <el-form-item label="节点名称" prop="nodeName">
          <el-input v-model="form.nodeName" placeholder="请输入节点名称" />
        </el-form-item>
        <el-form-item label="节点序号" prop="step">
          <el-input v-model="form.step" placeholder="请输入节点序号" />
        </el-form-item>
        <el-form-item label="删除标识  " prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标识  " />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 指派节点审核人 -->
    <el-dialog :title="title" :visible.sync="openApprover" width="600px" append-to-body>
      <el-button type="primary"   plain
                 icon="el-icon-plus"
                 @click="addApproverQuery">新增审核人</el-button>
      <el-button type="danger"
                 plain
                 icon="el-icon-delete"  :disabled="multiple"
                 @click="handleApproverDelete">删除</el-button>
      <el-table v-loading="approverLoading" :data="nodeApproverList" @selection-change="handleSelectionApproverChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="节点审核人" align="center" prop="approverName" />
        <el-table-column label="备注 " align="center" prop="remark" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleApproverDelete(scope.row)"
              v-hasPermi="['system:approver:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <!--     新增审核人       -->
    <el-dialog :title="title" :visible.sync="openAddApprover" width="1200px" append-to-body>
      <el-form :model="queryUserParams" ref="queryUserForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="用户名称" prop="userName">
          <el-input
            v-model="queryUserParams.userName"
            placeholder="请输入用户名称"
            clearable
            @keyup.enter.native="handleUserQuery"
          />
        </el-form-item>
        <el-form-item label="归属部门" prop="deptId" >
          <treeselect v-model="queryUserParams.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" style="width:50%;margin-left: 70px"/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleUserQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
        </el-form>

      <el-form>
      <el-form-item label="备注" prop="remark">
      <el-input   v-model="remark" placeholder="请输入备注"   style="width:50%;margin-left: 28px"/>
      </el-form-item>
      </el-form>
      <el-button type="primary"    plain
                 icon="el-icon-plus"  @click="addUserToApprover">新增</el-button>
      <el-table v-loading="userLoading" :data="userList" @selection-change="handleSelectionUserChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="用户名" align="center" prop="userName" />
        <el-table-column label="用户昵称 " align="center" prop="nickName" />
        <el-table-column label="用户部门 " align="center" prop="deptName" />
        <el-table-column label="用户岗位 " align="center" prop="postName" />
      </el-table>

      <pagination
        v-show="totalU>0"
        :total="totalU"
        :page.sync="queryUserParams.pageNum"
        :limit.sync="queryUserParams.pageSize"
        @pagination="getUserList"
      />
    </el-dialog>

  </div>
</template>

<script>
import { listNode, getNode, delNode, addNode, updateNode } from "@/api/system/node";
import {addApprover, delApprover, listApprover, listUser} from "../../../api/system/approver";
import {treeselect} from "../../../api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Node",
  components: { Treeselect },
  data() {
    return {
      //新增审核人页面
      // 部门树选项
      deptOptions: undefined,
      remark:"",
      userList:[],
      userLoading:true,
      openAddApprover: false,
      userIds: [],
      //操作审核人页面
      approverLoading:true,
      nodeApproverList: [],
      openApprover: false,
      approverAllIds: [],
      nodeId: "",
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
      //用户列表总条数
      totalU: 0,
      // 流程节点表格数据
      nodeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryUserParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: null,
        userName: null,
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processMark: null,
        nodeName: null,
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
    this.getApproverList();
    this.getTreeselect();
    this.getUserList();
  },
  methods: {
    /** 新增审核人 */
    addUserToApprover() {
      const approverIds = this.userIds;
      const param= {
        approverIds: approverIds,
        remark:this.remark,
        nodeId:this.nodeId
      }
      addApprover(param).then(response => {
        this.$modal.msgSuccess("新增成功");
        this.openAddApprover = false;
        this.getApproverList(this.nodeId);
      });


    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    /** 新增审核人*/
    addApproverQuery() {
      this.openAddApprover = true;
      this.getTreeselect();
      this.getUserList();
      this.title = "新增审核人";
    },
    /** 查询用户列表 */
    getUserList() {
      this.userLoading = true;
      console.log(this.queryUserParams);
      listUser(this.queryUserParams).then(response => {
        this.userList = response.rows;
        this.userLoading = false;
        this.totalU = response.total;
      });
    },
    /** 查询流程节点列表 */
    getList() {
      this.loading = true;
      listNode(this.queryParams).then(response => {
        this.nodeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询流程节点列表 */
    getApproverList(nodeId) {
      this.approverLoading = true;
      const param= {
        nodeId: nodeId
      }
      listApprover(param).then(response => {
        this.nodeApproverList = response.rows;
        this.approverLoading = false;
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
        nodeId: null,
        id: null,
        processMark: null,
        nodeName: null,
        step: null,
        createBy: null,
        createTime: null,
        delFlag: null
      };
      this.resetForm("form");
    },
    /** 搜索用户按钮操作 */
    handleUserQuery() {
      this.getUserList();
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
    /** 重置用户按钮操作 */
    resetUserQuery() {
      this.resetForm("queryUserForm");
      this.handleUserQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleSelectionApproverChange(selection){
      this.approverAllIds = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleSelectionUserChange(selection) {
      console.log("selection------------>"+selection.map(item => item.userId));
      this.userIds = selection.map(item => item.userId);
      console.log("userIds------------>"+this.userIds);
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加流程节点";
    },
    /** 操作审核人 */
    appointApprover(row) {
      this.reset();
      const id = row.id
      this.getApproverList(id);
      this.nodeId=id;
      this.openApprover = true;
        this.title = "操作审核人";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getNode(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改流程节点";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateNode(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addNode(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除流程节点"' + row.nodeName + '"的数据项？').then(function() {
        return delNode(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    //删除节点审核人
    handleApproverDelete(row){
      const ids = row.id || this.approverAllIds;
      const nodeId=this.nodeId;
      this.$modal.confirm('是否确认删除该节点审核人？').then(function() {
        return delApprover(ids,nodeId);
      }).then(() => {
        this.getApproverList(this.nodeId);
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/node/export', {
        ...this.queryParams
      }, `node_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.username" placeholder="请输入岗位名称" style="width: 200px;" class="filter-item"/>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="getList">
        查询
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        添加
      </el-button>
    </div>
    <!--  数据表格-->
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="ID" prop="id" sortable="custom" align="center" width="80px">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="角色名" max-width="150px" align="center" prop="name">
        <template slot-scope="{row}">
          <span>{{ row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="230px" align="center">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            修改
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页工具条  page当前页 total总记录数 limit每页显示多少条 pagination触发自定义事件，查询数据-->
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <!--  绑定了title，是一个数组里取的，表示是修改的标题还是添加的标题
      visible.sync 对话框是否显示
    -->
    <el-dialog :title="title" :visible.sync="dialogFormVisible" style="width: 80%">
      <!--
          rules:校验规则
          model:数据绑定
      -->
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="150px" style="width: 80%; margin-left:50px;">
        <el-form-item label="悬浮触发">
          <el-cascader
            v-model="value"
            :options="deptList"
            :props="{
              value: 'id',
              label:'name',
              children:'items',
              checkStrictly: 'true'
            }"
            :show-all-levels="false"
            clearable
            @change="handleChange"
          ></el-cascader>
        </el-form-item>
        <!--        数据校验要求prop值和temp.属性名一致-->

        <el-form-item label="部门" prop="sex">
          <el-select v-model="temp.deptId" placeholder="请选择">
            <el-option-group
              v-for="group in deptList"
              :key="group.id"
              :label="group.name">
              <el-option-group
                v-for="items in group.items"
                :key="items.id"
                :label="items.name">
                <el-option
                  v-for="item in items.items"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-option-group>
            </el-option-group>
          </el-select>
        </el-form-item>
        <el-form-item label="岗位名称名" prop="username">
          <el-input placeholder="请输入岗位名" v-model="temp.remark" />
        </el-form-item>
        <el-form-item label="岗位简称" prop="password">
          <el-input placeholder="请输入简称" v-model="temp.name"></el-input>
        </el-form-item>
        <el-form-item label="创建者姓名" prop="mobile">
          <el-input v-model="createBy" readonly="readonly"/>
        </el-form-item>
        <el-form-item label="创建时间" prop="mobile">
          <el-input v-model="createTime" readonly />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <!--
          dialogStatus==='create'?createData():updateData()
          dialogStatus需要我们根据情况去改变
        -->
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData(createTime)">
          确认
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  //
  import { add, update, list, deleteUser } from '@/api/sys/menus'
  import {groupDept} from "@/api/sys/dept";
  import waves from '@/directive/waves' // waves directive
  import { parseTime } from '@/utils'
  import Pagination from '@/components/Pagination' // 分页组件
  let id = 0;
  export default {
    name: 'rolesTable',
    components: { Pagination },
    directives: { waves },
    admin:'admin',
    data() {
      return {
        deptList: [], // 后台查询出来，分好组的部门信息
        temp: { // 添加、修改时绑定的表单数据
          id: undefined,
          remark: '',
          name: '',
          createBy: '',
          createTime: '',
          deptId: '',
          describe: '',
        },
        createBy:'超级管理员',
        createTime:'',//当前时间
        tableKey: 0,
        list: null, // 后台返回，给数据表格展示的数据
        total: 5, // 总记录数
        listLoading: true, // 是否使用动画
        listQuery: {
          page: 1, // 分页需要的当前页
          limit: 10, // 分页需要的每页显示多少条
          sex: 1,
          name: ''
        },
        title: '添加', // 对话框显示的提示 根据dialogStatus create
        dialogFormVisible: false, // 是否显示对话框
        dialogStatus: '', // 表示表单是添加还是修改的
        rules: {
          // 校验规则
          name: [{ required: true, message: '用户名必填', trigger: 'blur' }]
        }
      }
    },
    // 创建实例时的钩子函数
    created() {
      this.getList()
      // 在创建时初始化获得部门信息
      this.getGroupDept()
      var _this = this;
      let yy = new Date().getFullYear();
      let mm = new Date().getMonth()+1;
      let dd = new Date().getDate();
      let hh = new Date().getHours();
      let mf = new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes();
      let ss = new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds();
      _this.createTime = yy+'-'+mm+'-'+dd+' '+hh+':'+mf+':'+ss
    },
    methods: {
      handleChange(value) {
        console.log(value);
      },
      // 获得分好组的部门信息
      getGroupDept(){
        groupDept().then((response) => {
          this.deptList = response.data.deptList
        })
      },
      // 去后台取数据的
      getList() {
        // 开始转圈圈
        this.listLoading = true
        // debugger // 调试
        list(this.listQuery).then(response => {
          this.list = response.data.items
          // this.list.push(response.data.items[1])
          this.total = response.data.total
          // 转圈圈结束
          this.listLoading = false
        })
      },
      // 重置表单数据
      resetTemp() {
        this.temp = {
          id: undefined,
          username: '',
          password: '',
          email: '',
          mobile: '',
          deptId: '',
          introduction: '',
        }
      },
      // 显示添加的对话框
      handleCreate () {
        // 重置表单数据
        this.resetTemp()
        // 点击确定时，是执行添加操作
        this.dialogStatus = 'create'
        this.title="添加角色"
        // 显示对话框
        this.dialogFormVisible = true
        this.$nextTick(() => {
          // 表单清除验证
          this.$refs['dataForm'].clearValidate()
        })
      },
      // 添加对话框里，点击确定，执行添加操作
      createData() {
        // 表单校验
        this.$refs['dataForm'].validate((valid) => {
          // 所有的校验都通过
          if (valid) {
            // 调用api里的sys里的user.js的ajax方法
            add(this.temp).then((response) => {
              // 关闭对话框
              this.dialogFormVisible = false
              // 刷新数据表格里的数据
              this.getList()
              // 显示一个通知
              this.$notify({
                title: '成功',
                message: response.data.message,
                type: 'success',
                duration: 2000
              })
            })
          }
        })
      },
      // 显示修改对话框
      handleUpdate(row) {
        // 将row里面与temp里属性相同的值，进行copy
        this.temp = Object.assign({}, row) // copy obj
        // 将对话框里的确定点击时，改为执行修改操作
        this.dialogStatus = 'update'
        // 修改标题
        this.title = '修改用户'
        // 显示修改对话框
        this.dialogFormVisible = true
        this.$nextTick(() => {
          // 清除校验
          this.$refs['dataForm'].clearValidate()
        })
      },
      // 执行修改操作
      updateData(createTime) {
        this.$refs['dataForm'].validate((valid) => {
          // 表单校验通过
          if (valid) {
            // 将temp拷贝到tempData
            const tempData = Object.assign({}, this.temp)
            // 进行ajax提交
            update(tempData).then((response) => {
              // 提交完毕，关闭对话框
              this.dialogFormVisible = false
              // 刷新数据表格
              this.getList()
              // 显示通知
              this.$notify({
                title: '成功',
                message: response.data.message,
                type: 'success',
                duration: 2000
              })
            })
          }
        })
      },
      handleDelete(row) {
        // 先弹确认取消框
        this.$confirm('确认删除角色【'+row.remark+'】吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 调用ajax去后台删除
          deleteUser(row.id).then((response) => {
            // 刷新数据表格
            this.getList()
            // ajax去后台删除
            this.$notify({
              title: '成功',
              message: response.data.message,
              type: 'success',
              duration: 2000
            })
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });

      }
    }
  }
</script>

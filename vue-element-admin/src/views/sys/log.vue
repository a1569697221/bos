<template>
  <div class="app-container">
    <div class="filter-container">
      <font>用户名：</font>
      <el-input v-model="listQuery.username" placeholder="请输入用户名称" style="width: 200px;" class="filter-item" />
      日志标题：
      <el-input v-model="listQuery.title" placeholder="请输入日志标题" style="width: 200px;" class="filter-item" />
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="getList">
        查询
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
      <el-table-column type="selection" />
      <el-table-column label="ID" prop="id" sortable="custom" align="center" width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="日志标题" width="150px" align="center" prop="name">
        <template slot-scope="{row}">
          <span>{{ row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户名称" width="150px">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="执行时间" width="150px">
        <template slot-scope="{row}">
          <span class="link-type">{{ row.time | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
        </template>
      </el-table-column>
      <el-table-column label="请求URL" min-width="150px" align="center">
        <template slot-scope="{row}">
          <span class="link-type">{{ row.url }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作IP地址" width="230px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.ip}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150px" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-link type="primary"
                :underline="true"
                @click="showDetail(row)" >查看详情</el-link>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页工具条  page当前页 total总记录数 limit每页显示多少条 pagination触发自定义事件，查询数据-->
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
  </div>
</template>

<script>
//
import { list, del } from '@/api/sys/log'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // 分页组件
export default {
  name: 'LogTable',
  components: { Pagination },
  directives: { waves },
  admin: 'admin',
  data() {
    return {
      createBy: '超级管理员',
      createTime: '', // 当前时间
      tableKey: 0,
      list: null, // 后台返回，给数据表格展示的数据
      total: 0, // 总记录数
      listLoading: true, // 是否使用动画
      listQuery: {
        page: 1, // 分页需要的当前页
        limit: 10, // 分页需要的每页显示多少条
        sex: 1,
        name: '',
        title: ''
      }
    }
  },
  // 创建实例时的钩子函数
  created() {
    this.getList()
    var _this = this
    const yy = new Date().getFullYear()
    const mm = new Date().getMonth() + 1
    const dd = new Date().getDate()
    const hh = new Date().getHours()
    const mf = new Date().getMinutes() < 10 ? '0' + new Date().getMinutes() : new Date().getMinutes()
    const ss = new Date().getSeconds() < 10 ? '0' + new Date().getSeconds() : new Date().getSeconds()
    _this.createTime = yy + '-' + mm + '-' + dd + ' ' + hh + ':' + mf + ':' + ss
  },
  methods: {
    showDetail(row) {
      console.log(row)
      this.$router.push({ name: 'details', query: { id: row.id }})
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
    }
  }
}
</script>

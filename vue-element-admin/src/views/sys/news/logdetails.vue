<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button style="margin-left: 1150px;" icon="el-icon-back" plain @click="back">返回</el-button>
    </div>
    <el-form ref="dataForm" :model="this.list" label-position="left" label-width="90px" style="width: 80%; margin-left:50px;">
      <el-form-item label="编号" prop="username">
        <el-input v-model="list.id" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="日志标题" prop="username">
        <el-input v-model="list.title" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="访问用户" prop="username">
        <el-input v-model="list.name" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="请求URL" prop="username">
        <el-input v-model="list.url" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="操作方式" prop="username">
        <el-input v-model="list.method" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="IP地址" prop="username">
        <el-input v-model="list.ip" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="服务器" prop="username">
        <el-input v-model="list.data" readonly style="width: 1100px" />
      </el-form-item>
      <el-form-item label="客户端地址" prop="username">
        <el-input v-model="list.terminal" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="浏览器" prop="username">
        <el-input v-model="list.type" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="执行时间" prop="username">
        <el-input v-model="list.time" readonly style="width: 1100px"/>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
//
import { byId } from '@/api/sys/log'
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
        id: ''
      }
    }
  },
  // 创建实例时的钩子函数
  created() {
    this.listQuery.id = this.$route.query.id
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
    // 去后台取数据的
    getList() {
      // 开始转圈圈
      this.listLoading = true
      // debugger // 调试
      byId(this.listQuery).then(response => {
        this.list = response.data.items
        // this.list.push(response.data.items[1])
        this.total = response.data.total
        // 转圈圈结束
        this.listLoading = false
      })
    },
    back() {
      /* this.$store.dispatch('delVisitedViews', this.$route);
       this.$router.go(-1);//返回上一层*/
      // 返回上一级并且关闭tab
      this.$store.state.tagsView.visitedViews.splice(this.$store.state.tagsView.visitedViews.findIndex(item => item.path === this.$route.path), 1)
      this.$router.push(this.$store.state.tagsView.visitedViews[this.$store.state.tagsView.visitedViews.length - 1].path)
    }
  }
}
</script>

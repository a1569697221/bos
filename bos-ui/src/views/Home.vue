<template>
    <div>
      <h3>
        Home页面
      </h3>
      <el-button type="primary" @click="testAxios">测试axios调用</el-button>
      <el-button type="primary" @click="getUsers">获得用户信息</el-button>
      <el-button type="primary" @click="getMenus">获得菜单信息</el-button>
    </div>
</template>

<script>
    import axios from 'axios'
    // 使用mock.js来模拟后台数据
    import mock from '../mocks/mock'
    export default {
      name: "Home",
      methods: {
        testAxios(){
          axios.get('http://localhost:8080/users').then(function(res){
            alert(res.data)
          }).catch(function(res){
            alert(res)
          })
        },
        getUsers(){
          this.$api.getUser().then(function(res){
            console.debug(res.data)
          }).catch(function(res){
            alert(res)
          })
        },
        getMenus(){
          this.$api.getMenu().then(function(res){
            console.debug(res.data)
          }).catch(function(res){
            alert(res)
          })
        }
      }
    }
</script>

<style scoped>

</style>

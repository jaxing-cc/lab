<template>
  <el-container class="home-container">
    <!--头部-->
    <el-header>
      <!--<img src="../assets/mur.jpg">-->
      <div>
        <span class="title">实验室维修管理系统</span>
      </div>
      <el-button type="warning" @click="exit()">登出</el-button>
    </el-header>
    <el-container>
      <!--左侧边-->
      <el-aside width="200px">
        <!--侧边栏菜单-->
        <el-menu
          router
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b">
          <el-menu-item  :index="item.address" v-for="item in menuList" :key="item.id">
            <i class="el-icon-menu"></i>
            <span slot="title">{{item.info}}</span>
          </el-menu-item>
        </el-menu>

      </el-aside>
      <!--右侧边-->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    name: "Home",
    data(){
      return{
        menuList:[],
        // isCollapse:false
      }
    },
    methods:{
      exit:function () {
        window.sessionStorage.clear();
        this.$router.push("/login")
      },
      //获取所有菜单
      getMenuList:async function getMenuList() {
        const user = window.sessionStorage.getItem("user");
        let userobj = JSON.parse(user);
        const res =await this.$http.get("user/getControl?userrank="+userobj.userrank);
         if (!res.data.flag){
           return this.$message.error("请求失败!,您可能未登录")
         }else{
           this.menuList=res.data.extend.control;
         }
      },
    },
    created() {
      this.getMenuList();
    }
  }
</script>

<style scoped>
  .el-header{
    background-color: #373d41;
    display: flex;
    justify-content: space-between;
    padding-left: 0;
    align-items: center;
    color: #fffdef;
    font-size: 20px;
  }
  .el-aside{
    background-color: #333744;
  }
  .el-main{
    background-color: #EAEDF1;
  }
  .home-container{
    height: 100%;
  }
  .title{
  margin-left: 0;
    margin-right: 0;
  }
</style>


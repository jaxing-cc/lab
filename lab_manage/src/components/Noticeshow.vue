<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/noticeshow' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>欢迎！</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <div style="height: 600px">
        <el-row>
          <el-card>
            <el-row >
              <el-col :span="1">
                <div>
                  <el-avatar  src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
                </div>
              </el-col>
              <el-col :span="17">
                <div id="name">
                  <span>{{user_name}}</span>
                </div>
              </el-col>
              <el-col :span="2" style="margin-top: 5px">
                <el-button type="primary" icon="el-icon-edit" @click="openwin()">修改个人信息</el-button>
              </el-col>

            </el-row>
          </el-card>
        </el-row>
        <el-row style="margin-top: 20px">

            <el-card class="box-card" style="width: 100%;height: 500px">
              <div slot="header" class="clearfix">
                <span>公告</span>
              </div>
              <div v-for="item in dataList" :key="item.id" class="text item">
                管理员:{{ item.user.name }}发布以下内容,日期:{{item.date}}<br><br>
                <el-card>
                  {{item.info}}
                </el-card>
              </div>
              <el-pagination
                @size-change="handleSize"
                @current-change="handleCurrent"
                :current-page="queryInfo.pagenum"
                :page-sizes="[100,200]"
                :page-size="pagesize"
                layout="total,  prev, pager, next, jumper"
                :total="total">
              </el-pagination>
            </el-card>

        </el-row>
      </div>
    </el-card>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%">
      <span>
        当前密码:
        <el-input v-model="userinfo.password" placeholder="请输入密码"></el-input>
        当前手机号码:
        <el-input v-model="userinfo.phone" placeholder="请输入手机号码"></el-input>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save()">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'noticeshow',
    data(){
      return{
        dialogVisible:false,
        user_name:'',
        userinfo:{
          phone:'',
          password:'',
          id:''
        },
        dataList:[],
        queryInfo:{
          pagenum:1
        },
        pagesize:3,
        total:0
      }
    },
    methods:{
      openwin(){
       this.dialogVisible=true;
      },
      async save () {
       const res=await this.$http.post("user/updateinfo",this.userinfo);
        if (res.data.flag){
          this.$message.success("信息保存成功")
          this.$router.push("/");
        } else{
          this.$message.error("信息保存失败!数据不可为空")
        }
      },
      //监听页码改变
      handleCurrent(newNum){
        this.queryInfo.pagenum=newNum;
        this.loadData();
      },
      handleSize(newSize){

      },
      async loadData () {
        let response = await this.$http.post("notice/getAll", this.queryInfo);
        this.dataList=response.data.extend.datas;
        this.total=response.data.extend.total;
      }
    },
    created () {
      this.loadData();
      let user = window.sessionStorage.getItem("user");
      let parse = JSON.parse(user);
      this.user_name=parse.name;
      this.userinfo.password=parse.password;
      this.userinfo.phone=parse.phone
      this.userinfo.id=parse.id
    }
  }
</script>

<style scoped>
  .text {
    font-size: 15px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
  }
  #name{
    color: burlywood;
    font-size: 50px;
    margin-top: -10px
  }
</style>

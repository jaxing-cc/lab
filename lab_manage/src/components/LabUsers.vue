<template>
    <div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/noticeshow' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>我的实验室管理</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card>
        <el-row>
          <el-col :span="10">
            请先选择实验室:
              <el-select v-model="addInfo.labid" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            <el-button type="primary" icon="el-icon-search" @click="loadUserdata">搜索</el-button>
          </el-col>
          <el-col :span="10">
            <el-button type="warning" icon="el-icon-edit" @click="openAddUser()">向实验室中添加一名学生</el-button>
          </el-col>
        </el-row>
        <hr style="margin-top: 15px;height: 3px">
        <el-row>
          <!--搜索选项-->
          <el-col>
            <el-form :inline="true" class="demo-form-inline">
              <el-form-item label="学号搜索" >
                <el-input v-model="queryInfo.query" placeholder="学号"></el-input>
              </el-form-item>
              <el-form-item label="班级搜索">
                <el-input v-model="queryInfo.name" placeholder="班级"></el-input>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <el-row>
          <!--学生数据-->
          <el-table
            :data="users"
            height="500"
            style="width: 100%"
            :row-class-name="tableRowClassName">
            <el-table-column type="index">
            </el-table-column>
            <el-table-column prop="user.name" label="用户名" width="180">
            </el-table-column>
            <el-table-column prop="className" label="班级专业" width="180">
            </el-table-column>
            <el-table-column prop="stuNum" label="学号" width="180">
            </el-table-column>
            <el-table-column prop="user.phone" label="联系方式" width="180">
            </el-table-column>
            <el-table-column label="标签" width="180">
              <template slot-scope="scope" v-if="scope.row.tag!=null">
                <el-tag type="success"><div v-text="scope.row.tag"></div></el-tag>
              </template>
            </el-table-column>
            <el-table-column  label="操作" fixed="right" width="200">
              <template slot-scope="scope">
                <div>
                  <el-button type="primary" icon="el-icon-edit" @click="openSetTag(scope.row)">设置标签</el-button>
                  <el-button type="danger" icon="el-icon-delete" @click="openDeleteWin(scope.row)">移除</el-button>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </el-row>
        <el-row>
          <!--分页-->
          <el-pagination
            @size-change="handleSize"
            @current-change="handleCurrent"
            :current-page="queryInfo.pagenum"
            :page-sizes="[100,200]"
            :page-size="queryInfo.pagesize"
            layout="total,  prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </el-row>
      </el-card>

        <!--添加学生对话框-->
      <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose"
        >
        <span>
          <el-form  :model="addInfo" class="demo-form-inline">
            <el-form-item label="账号查找该用户">
              <el-input v-model="username" placeholder="用户账号"></el-input>
            </el-form-item>
             <el-form-item>
                <el-button type="danger" @click="test">测试该用户是否存在</el-button>
            </el-form-item>
            <el-form-item label="设置专业:">
              <el-input v-model="addInfo.className" placeholder="专业名称"></el-input>
            </el-form-item>
            <el-form-item label="设置学号:">
              <el-input v-model="addInfo.stuNum" placeholder="学号"></el-input>
            </el-form-item>
          </el-form>
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false;testRes=true;username='' ">取 消</el-button>
          <el-button type="primary" @click="addUsertoLab" :disabled="testRes">保 存</el-button>
        </span>
      </el-dialog>
      <!--删除对话框-->
      <el-dialog
        title="提示"
        :visible.sync="deleteWin"
        width="30%">
        <span>
          你确定从实验室信息中移除该用户?
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="deleteWin = false">取 消</el-button>
          <el-button type="danger" @click="delUser()">移 除</el-button>
        </span>
      </el-dialog>
      <!--设置对话框-->
      <el-dialog
        title="提示"
        :visible.sync="setWin"
        width="30%">
        <span>
          <el-input type="text" placeholder="请输入标签" v-model="setInfo.tag"></el-input>
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="setWin = false">取 消</el-button>
          <el-button type="danger" @click="setTag()">设 置</el-button>
        </span>
      </el-dialog>
    </div>
</template>

<script>
  export default {
    name: 'LabUsers',
    data(){
      return{
        dialogVisible:false,
        deleteWin:false,
        setWin:false,
        options:[],
        uid:0,
        total:0,
        users:[],
        testRes:true,
        username:'',
        addInfo:{
          labid:'',
          userid:-1,
          className:'',
          stuNum:''
        },
        queryInfo:{
          name:'',
          query:'',
          labid:'',
          pagenum:1,
          pagesize:100,
        },
        delInfo:{
          labid:'',
          userid:''
        },
        setInfo:{
          labid:'',
          userid:'',
          tag:''
        }
      }
    },
    methods: {
    tableRowClassName({row, rowIndex}) {//选中效果
      if (rowIndex === 1) {
        return 'warning-row';
      } else if (rowIndex === 3) {
        return 'success-row';
      }
      return '';
    },
      handleCurrent(newNum){
        this.queryInfo.pagenum=newNum;
        this.loadUserdata();
      },
      handleSize(newSize){

      },
      async loadLabData () {
        let res = await this.$http.post("lab/getLabByUid", {
          uid: this.uid
        });
        if (res.data.flag) {
          this.options = res.data.extend.datas;
        } else {
          this.$message.error("请求数据失败！")
        }
      },
      async loadUserdata () {
        if (this.addInfo.labid === '') {
          this.$message.error("请先选择实验室！");
          return;
        }
        this.queryInfo.labid = this.addInfo.labid;
        let res = await this.$http.post("lab/getUserByLabid", this.queryInfo);
        if (res.data.flag){
          this.users=res.data.extend.datas;
          this.total=res.data.extend.total;
        } else{
          this.$message.error(res.data.msg);
        }
      },
      //添加一个user到本实验室
      async addUsertoLab () {
        if (this.uid == -1) {
          this.$message.error("未找到用户");
        }
        if (this.addInfo.className.length == 0 || this.addInfo.stuNum.length == 0) {
          this.$message.error("内容有空！");
        }
        let res = await this.$http.post("lab/addUserToLab", this.addInfo);
        if(res.data.flag){
          this.$message.success("添加成功")
        }else{
          this.$message.error("添加失败!该用户已经参加实验室")
        }
        this.dialogVisible=false;
        this.loadUserdata()
      },
      //用户是否存在校验
      async test () {
        let res = await this.$http.get("user/getuser?username=" + this.username);
        if (res.data.flag) {
          this.testRes=false;
          this.addInfo.userid = res.data.extend.user.id;
          this.$message.success("已找到该用户，用户名:" + res.data.extend.user.name);
        } else {
          this.$message.error("未找到该用户!")
        }
      },
      openAddUser(){
        if (this.addInfo.labid===''){
          this.$message.error("请先选择实验室！");
        } else{
          this.dialogVisible=true;
        }
      },
      handleClose(done){
        this.testRes=true;
        this.username='';
        done();
      },
      //删除用户
      openDeleteWin(data){
          this.deleteWin=true;
          this.delInfo.labid=data.labid;
        this.delInfo.userid=data.user.id;
      },
      async delUser () {
        let res = await this.$http.post("lab/deleteUser", this.delInfo);
        if (res.data.flag){
          this.$message.success("删除成功");
        } else {
          this.$message.error("删除失败");
        }
        this.loadUserdata();
        this.deleteWin=false;
      },
      //设置标签
      openSetTag(data){
        this.setWin=true;
        this.setInfo.labid=data.labid;
        this.setInfo.userid=data.user.id;
      },
      async setTag () {
        let res = await this.$http.post("lab/setTag", this.setInfo);
        if (res.data.flag){
          this.$message.success("设置成功");
        } else {
          this.$message.error("设置失败");
        }
        this.loadUserdata();
        this.setWin=false;
        this.setInfo.tag=''
      }
    },
    created () {
      let user = window.sessionStorage.getItem("user");
      let parse = JSON.parse(user);
      this.uid=parse.id;
      this.loadLabData();
    }
  }
</script>

<style scoped>

</style>

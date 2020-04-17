<template>
    <div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/noticeshow' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>用户授予</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card>
        <el-row>
          <el-col :span="3" style="margin-top: 5px">
            按姓名模糊查询用户:
          </el-col>
          <el-col :span="5">
            <el-input type="text" placeholder="请输入姓名(该栏为空则查找所有用户)" v-model="queryInfo.name"></el-input>
          </el-col>
          <el-col :span="5">
            <el-button type="warning" @click="getUsers">姓名搜索</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="3" style="margin-top: 15px">
            按帐号精准查询用户:
          </el-col>
          <el-col :span="5"  style="margin-top: 10px">
            <el-input type="text" placeholder="请输入该用户的帐号" v-model="username"></el-input>
          </el-col>
          <el-col :span="5"  style="margin-top: 10px">
            <el-button type="success" @click="getUserByUsername">精准搜索</el-button>
          </el-col>
        </el-row>
        <el-row>
          <hr style="margin-top: 20px">
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-table
              :data="tableData"
              height="500"
              style="width: 100%">
              <el-table-column type ="index" width="100">
              </el-table-column>
              <el-table-column prop="username" label="账号" width="180">
              </el-table-column>
              <el-table-column prop="name" label="姓名" width="180">
              </el-table-column>
              <el-table-column prop="phone" label="电话" width="180">
              </el-table-column>
              <el-table-column label="等级" width="180">
                <template slot-scope="scope">
                 <div>
                   <div v-if="scope.row.userrank==1">
                     root管理员
                   </div>
                   <div v-if="scope.row.userrank==2">
                     实验室管理员
                   </div>
                   <div v-if="scope.row.userrank==3">
                     普通用户
                   </div>
                   <div v-if="scope.row.id==thisUserId">
                     (此账号!)
                   </div>
                 </div>
                </template>
              </el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="primary"
                    @click="opwin1(scope.row)">权限更改</el-button>
                  <el-button
                    size="mini"
                    type="danger"
                    @click="opwin2(scope.row)">用户注销</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              @size-change="handleSize"
              @current-change="handleCurrent"
              :current-page="queryInfo.pagenum"
              :page-sizes="[100,200]"
              :page-size="queryInfo.pagesize"
              layout="total,  prev, pager, next, jumper"
              :total="total">
            </el-pagination>
          </el-col>
        </el-row>
      </el-card>
<!--删除!-->
      <el-dialog
        title="帐号注销"
        :visible.sync="delWindow"
        width="50%">
        <span>
         警告!您确定要注销这个帐号?
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="delWindow = false">取 消</el-button>
          <el-button type="primary" @click="del()">确 定</el-button>
        </span>
      </el-dialog>
<!--修改-->
      <el-dialog
        title="帐号权限修改"
        :visible.sync="setWindow"
        width="50%">
        <span>
          <el-row>
             <el-alert
               title="权限修改说明"
               type="warning"
               description="实验室管理员:将用户设置为实验室管理员后,请在《实验室管理》一栏中授予该用户需要管理的实验室"
               show-icon>
              </el-alert>
          </el-row><br>
          <el-row>
              <el-alert
                title="权限修改说明"
                type="warning"
                description="root管理员:将用户设置为root管理员后,请会失去申报过的维修记录和查询资格"
                show-icon>
              </el-alert>
          </el-row>
          <el-row>
            <div style="margin-top: 10px">
                  请设置权限:
            </div>
          </el-row>
           <el-row>
               <el-select v-model="setInfo.userrank" placeholder="请选择" style="margin-top: 10px">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
           </el-row>
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="setWindow = false">取 消</el-button>
          <el-button type="primary" @click="set()">保 存</el-button>
        </span>
      </el-dialog>
    </div>
</template>

<script>
  export default {
    name: 'Userrank',
    data(){
      return{
        tableData:[],
        username:'',
        delWindow:false,
        setWindow:false,
        delInfo:{
          id:''
        },
        setInfo:{
          id:'',
          userrank:0
        },
        queryInfo:{
          pagenum:1,
          pagesize:100,
          name:''
        },
        total:0,
        thisUserId:'',
        options: [{
          value: 1,
          label: 'root管理员'
        }, {
          value: 2,
          label: '实验室管理员'
        }, {
          value: 3,
          label: '普通用户'
        }],
      }
    },
    methods:{
      async getUsers(){
        let res=await this.$http.post("user/getusers",this.queryInfo);
        if (res.data.flag){
         this.tableData=res.data.extend.datas;
         this.total=res.data.extend.total;
        } else{
          this.$message.error(res.data.msg);
        }
      },
      async getUserByUsername () {
        if (this.username==''||this.username.length==0){
          this.$message.error("账号栏不可为空");
          return;
        }
        this.tableData=[]
        let res=await this.$http.get("user/getuser?username=" + this.username);
        if (res.data.flag){
          this.tableData.push(res.data.extend.user)
          this.setInfo.userrank=res.data.extend.user.userrank;
          this.$message.success("已找到该用户!");
          this.username='';
        } else{
          this.$message.error(res.data.msg);
        }
      },
      //修改  删除
      // crud
      async delpost () {
        let res = await this.$http.post("user/deleteById", this.delInfo);
        return res.data.flag;
      },
      del () {
        let  f=this.delpost();
        if (f) {
          this.$message.success("删除成功!请重新操作");
          this.tableData=[];
        } else {
          this.$message.error("删除失败！")
        }
        this.delWindow=false;

      },
      async setpost () {
        let res = await this.$http.post("user/setUserRank", this.setInfo);
        return res.data.flag;
      },
      async set () {
        let res = await this.$http.post("user/setUserRank", this.setInfo);
        if (res.data.flag==true) {
          this.$message.success(res.data.msg)
        } else {
          this.$message.error(res.data.msg)
        }
        this.tableData = [];
        this.setWindow = false;
      },
      //监听页码改变
      handleCurrent(newNum){
        this.queryInfo.pagenum=newNum;
        this.getUsers();
      },
      handleSize(newSize){

      },
      //操作
      opwin1(data){
        this.setWindow=true;
        this.setInfo.id=data.id;
        this.setInfo.userrank=data.userrank;
      },
      opwin2(data){
        this.delWindow=true;
        this.delInfo.id=data.id;
      }

    },
    created () {
      let user = window.sessionStorage.getItem("user");
      let parse = JSON.parse(user);
      this.thisUserId=parse.id;
    }
  }
</script>

<style scoped>

</style>

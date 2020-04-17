<template>
    <div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/noticeshow' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>实验室管理</el-breadcrumb-item>
      </el-breadcrumb>
      <el-row>
          <el-card>
            <el-row>
              <el-col :span="5">
                <div style="margin-top: 15px;">
                  <el-input placeholder="实验室名称搜索" v-model="queryInfo.name" class="input-with-select">
                    <el-button slot="append" icon="el-icon-search"></el-button>
                  </el-input>
                </div>
              </el-col>
              <el-col :span="10">
                <div style="margin-top: 15px;">
                  <el-button type="primary" round  @click="loadData()">搜索</el-button>
                </div>
              </el-col>
              <el-col :span="5">
                <div style="margin-top: 15px;">
                  <el-button type="primary"   icon="el-icon-edit"  @click="openAdd()">实验室增加</el-button>
                </div>
              </el-col>

            </el-row>
            <el-row>
              <div style="margin-top: 20px">
                <el-table :data="tableData" border style="width: 100%" stripe height="500">
                  <el-table-column type="index">
                  </el-table-column>
                  <el-table-column prop="name" label="实验室名称" width="200">
                  </el-table-column>
                  <el-table-column prop="master.name" label="管理员名称" width="200">
                  </el-table-column>
                  <el-table-column prop="master.phone" label="管理员电话" >
                  </el-table-column>
                  <el-table-column  label="操作">
                    <template slot-scope="scope">
                      <el-button type="primary" icon="el-icon-edit" @click="openSet(scope.row)">实验室设置</el-button>
                      <el-button type="danger " icon="el-icon-delete" @click="opendel(scope.row)">实验室删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
            </el-row>
            <el-row>
              <el-pagination
                @size-change="handleSize"
                @current-change="handleCurrent"
                :current-page="queryInfo.pagenum"
                :page-sizes="[100,200]"
                :page-size="pagesize"
                layout="total,  prev, pager, next, jumper"
                :total="total">
              </el-pagination>
            </el-row>
          </el-card>
        <!--对话框区-->
        <!--修改对话框-->
        <el-dialog
          title="实验室设置"
          :visible.sync="setDialog"
          width="50%">
        <span>
            <el-row>
              管理员账号绑定设置:
            </el-row>
            <el-row style="margin-top: 10px">
                <el-radio v-model="setform.query" label="1" border @change="valuechange">不做更改</el-radio>
                <el-radio v-model="setform.query" label="2" border @change="valuechange">解绑目前管理员</el-radio>
                <el-radio v-model="setform.query" label="3" border @change="valuechange">更改绑定</el-radio>
            </el-row>
            <el-row style="margin-top: 10px" v-if="flag">
              <el-col :span="6" style="margin-top: 5px">
                    请输入新实验室管理员帐号:
              </el-col>
              <el-col :span="12">
                    <el-input v-model="usernameByRow"></el-input>
              </el-col>
               <el-col :span="6">
                    <el-button type="danger" icon="el-icon-edit" @click="test">test</el-button>
               </el-col>
            </el-row>
           <el-row style="margin-top: 10px">
              <el-col :span="6" style="margin-top: 5px">
                    当前实验室姓名:
              </el-col>
              <el-col :span="18">
                    <el-input v-model="setform.name"></el-input>
              </el-col>
            </el-row>
        </span>
          <span slot="footer" class="dialog-footer">
            <el-button @click="setDialog = false">取 消</el-button>
            <el-button type="primary" @click="setDialog_2 = true" :disabled="flag_2">保 存</el-button>
          </span>
        </el-dialog>
        <el-dialog
          title="警告"
          :visible.sync="setDialog_2"
          width="50%">
        <span>
          您确定要保存这个记录?
        </span>
          <span slot="footer" class="dialog-footer">
          <el-button @click="setDialog_2 = false">取 消</el-button>
          <el-button type="primary" @click="set()">确 定</el-button>
        </span>
        </el-dialog>
        <!--添加实验室-->
        <el-dialog
          title="添加实验室"
          :visible.sync="addDialog"
          width="50%">
        <span>
            <el-row>
              请为实验室设置名称:
            </el-row>
            <el-row style="margin-top: 10px">
              <el-input v-model="addform.name" placeholder="请输入名称"></el-input>
            </el-row>
        </span>
          <span slot="footer" class="dialog-footer">
          <el-button @click="addDialog = false">取 消</el-button>
          <el-button type="primary" @click="addDialog_2 = true">提 交</el-button>
        </span>
        </el-dialog>
        <el-dialog
          title="提示"
          :visible.sync="addDialog_2"
          width="50%">
        <span>
          您确定要增加这个记录?
        </span>
          <span slot="footer" class="dialog-footer">
          <el-button @click="addDialog_2 = false">取 消</el-button>
          <el-button type="primary" @click="add()">确 定</el-button>
        </span>
        </el-dialog>
        <!--注销实验室-->
        <el-dialog
          title="警告"
          :visible.sync="delDialog"
          width="50%">
        <span>
          您确定要注销这个实验室记录?
        </span>
          <span slot="footer" class="dialog-footer">
          <el-button @click="delDialog = false">取 消</el-button>
          <el-button type="primary" @click="del()">确 定</el-button>
        </span>
        </el-dialog>

      </el-row>
    </div>
</template>

<script>
  export default {
    name: 'Lab',
    data(){
      return{
        flag:false,
        flag_2:false,
        setDialog:false,
        setDialog_2:false,
        addDialog:false,
        addDialog_2:false,
        delDialog:false,
        tableData:[],
        queryInfo:{
          pagenum:1,
          name:''
        },
        pagesize:100,
        total:0,
        usernameByRow:'',
        setform:{
          id:'',
          query:'1',
          uid:-1,
          name:''
        },
        addform:{
          name:''
        },
        delform:{
          id:''
        }
      }
    },
    methods:{
      //判断单选框
      valuechange(){
        if (this.setform.query=='1'){
          // this.$refs.usernameinput.disabled=true;
          this.flag=false;
          this.flag_2=false;
          this.usernameByRow='';
        }
        if (this.setform.query=='2'){
          // this.$refs.usernameinput.disabled=true;
          this.flag=false;
          this.flag_2=false;
          this.usernameByRow='';
        }
        if (this.setform.query=='3'){
          // this.$refs.usernameinput.disabled=true;
          this.flag=true;
          this.flag_2=true;
          this.usernameByRow='';
        }
      },
      //加载用户
      async loadData () {
        let res = await this.$http.post("lab/getAllLabByPage", this.queryInfo);
        this.tableData=res.data.extend.datas;
        this.total=res.data.extend.total;
      },
      //用户是否存在校验
      async test() {
        let res = await this.$http.get("user/getuserTest?username=" + this.usernameByRow);
        if (res.data.flag){
          this.setform.uid=res.data.extend.user.id;
          console.log( this.setform.uid)
          this.$message.success(res.data.msg);
          this.flag_2=false;
        } else{
          this.$message.error(res.data.msg)
          this.flag_2=true;
        }
      },
      //打开修改窗
      async set () {
        let res=await this.$http.post("lab/setLabInfo", this.setform);
        if (res.data.flag){
          this.loadData();
          this.$message.success("保存成功!")
        } else{
          this.loadData();
          this.$message.error("保存失败!")
        }
        this.setDialog=false;
        this.setDialog_2=false;
      },
      openSet(data){
          this.setform.name=data.name;
          this.setform.id=data.id;
          if (data.master==null){
            this.setform.uid=-1
          } else{
            this.setform.uid=data.master.id;
          }
          if (this.setform.query=='3'){
            this.flag_2=true;
          }
          this.setDialog=true;
      },
      //添加
      async add(){
        let  res= await this.$http.post("lab/addLab",this.addform)
        if (res.data.flag){
          this.$message.success('实验室创建成功,请为实验室绑定管理员');
        } else {
          this.$message.error('实验室创建失败');
        }
        this.loadData();
        this.addDialog=false;
        this.addDialog_2=false;
      },
      openAdd(){
        this.addDialog=true;
        this.addform.name='';
      },
      //删除
      opendel(data){
        this.delDialog=true;
        this.delform.id=data.id;
      },
      async del(){
        let res=await this.$http.post('lab/deleteLab',this.delform);
        if (res.data.flag){
          this.$message.success('实验室注销成功！');
        } else {
          this.$message.error('实验室注销失败,该实验室可能还绑定有设备');
        }
        this.loadData();
      },
      //监听页码改变
      handleCurrent(newNum){
        this.queryInfo.pagenum=newNum;
        this.loadData();
      },
      handleSize(newSize){

      }
    },
    created () {
      this.loadData();
    }
  }
</script>

<style scoped>

</style>

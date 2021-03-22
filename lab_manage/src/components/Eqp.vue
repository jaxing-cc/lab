<template>
    <div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/noticeshow' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>实验室设备查询</el-breadcrumb-item>
        <el-breadcrumb-item>请选择先选择要申报的实验室！</el-breadcrumb-item>
      </el-breadcrumb>

      <el-card class="box-card">
        <el-row :gutter="20">
          <el-col :span="3">
            <div style="margin-top: 15px;">

              <el-autocomplete
                class="inline-input"
                v-model="lab"
                :fetch-suggestions="querySearch"
                :trigger-on-focus="true"
                placeholder="请选择实验室"
                :select-when-unmatched="true"
                @select="handleSelect"
              ><template slot-scope="{ item }">
                  <span style="float:left;">{{ item.name }}</span>
                </template>
              </el-autocomplete>
            </div>
          </el-col>
          <el-col :span="5">
            <div style="margin-top: 15px;">
              <el-input placeholder="设备名称关键字" v-model="queryInfo.query" class="input-with-select">
                <el-button slot="append" icon="el-icon-search"></el-button>
              </el-input>
            </div>
          </el-col>
          <el-col :span="4">
            <div style="margin-top: 15px;">
              <el-button type="primary" round  @click="getEqp()">搜索</el-button>
            </div>
          </el-col>
        </el-row>
        <!--table数据-->
        <el-row class="eqptable">
          <el-table :data="EqpList" border style="width: 100%" stripe height="500">
            <el-table-column type="index">
            </el-table-column>
            <el-table-column prop="name" label="设备名" width="180">
            </el-table-column>
            <el-table-column prop="date" label="购入时间" width="180">
            </el-table-column>
            <el-table-column prop="state" label="设备状态" width="180">
              <template slot-scope="scope">
                {{scope.row.state==1?"设备健康":"设备故障"}}
              </template>
            </el-table-column>
            <el-table-column prop="lab.name" label="所属实验室">
            </el-table-column>
            <el-table-column  label="故障申报">
              <template slot-scope="scope">
                <el-button @click="apply(scope.row)" type="primary" size="small"  icon="el-icon-edit">我要申报故障</el-button>
              </template>
            </el-table-column>
          </el-table>
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
<!--申报对话框-->
      <el-dialog
        title="设备维修申报"
        :visible.sync="dialogVisible"
        width="50%">
        <span>
          <el-row>
            申请人名称:
          </el-row>
          <el-row style="margin-top: 10px">
            <el-input v-model="uname" placeholder="请输入内容" disabled></el-input><br><br>
          </el-row>
          <el-row>
            请输入设备损坏的原因:
          </el-row>
          <el-row style="margin-top: 10px">
            <el-input
              type="textarea"
              :rows="2"
              placeholder="请输入内容"
              v-model="reason" >
            </el-input>
          </el-row>
        </span>
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="open()">确 定</el-button>
  </span>
      </el-dialog>
    </div>
</template>

<script>
  export default {
    name: 'Eqp',
    data () {
      return {
        dialogVisible:false,//对话框的现实和隐藏
        EqpList:[],
        total:0,
        restaurants: [],
        lab: '',
        pagesize:100,//每页size
        queryInfo:{
          query:'',
          pagenum:1,//当前页数
          labid:'',
        },
        uname:'',
        uid:'',
        eqpid:'',
        reason:'',
        Rules:{//验证规则对象
          reason:[
            { required: true, message: '请输入故障原因', trigger: 'blur' },
            { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
          ],
        }
      }
    },
    methods: {
      querySearch (queryString, cb) {
        var restaurants = this.restaurants;
        var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
        // 调用 callback 返回建议列表的数据
        cb(results);
      },
      createFilter(queryString) {
        return (restaurant) => {
          return (restaurant.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      async loadAll () {
        const res = await this.$http.post('lab/getAllLab');
        this.restaurants=res.data.extend.lab;
      },
      handleSelect (item) {
        this.lab=item.name;
        this.queryInfo.labid=item.id;
      },
      async getEqp(){//获取设备
        const res = await this.$http.post('eqp/getEqpByLabid',this.queryInfo);
        if (!res.data.flag){
          this.$message.error("请求失败！请检查是否输入了的参数!")
          return;
        }
        const list=res.data.extend.eqps;
        this.total=res.data.extend.total;
        this.EqpList=list;
      },
      //申报功能
      apply(data){//打开窗口准备数据
        this.eqpid=data.id;//设备id
        this.reason='';
        this.dialogVisible=!this.dialogVisible;
        let user = window.sessionStorage.getItem("user");
        let parse = JSON.parse(user)
        console.log(parse.name)
        this.uname=parse.name;
        this.uid=parse.id;
      },
      async sendDeclare () {
        let response = await this.$http.post("declare/sendDeclare",{
          uid:this.uid,
          reason:this.reason,
          eid:this.eqpid
        });
       return response.data.flag;
      },
      open() {
        if (this.reason==''||this.reason.length==0){
          this.$message({
            type: 'error',
            message: '理由不能为空!'
          })
          return;
        }
        this.$confirm('此操作将提交申报, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let flag = this.sendDeclare();
          if (flag) {
            this.$message({
              type: 'success',
              message: '申报成功!请前往申报进度查询处上传图片!'
            });
          }
          this.dialogVisible=false;
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消申报'
          });
          this.dialogVisible=false;
        });
      },
      //分页处理区
      //分页size控制,监听一页的大小改变
      // handleSize(newSize){
      //   this.queryInfo.pagesize=newSize;
      // },
      //监听页码改变
      handleCurrent(newNum){
        this.queryInfo.pagenum=newNum;
        this.getEqp();
      },
      handleSize(newSize){

      }
    },
    created () {
      this.loadAll();
    }
  }
</script>

<style scoped>
  .input-with-select{
    height: 30px;
  }
  .eqptable{
    margin-top: 40px;
  }
</style>


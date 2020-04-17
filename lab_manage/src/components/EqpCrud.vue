<template>
    <div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/noticeshow' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>实验室设备管理</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card>
        <el-row>
          <el-col :span="4" style="margin-top: 5px">
            您所管理的实验室列表:
          </el-col>
          <el-col :span="5">
            <el-select v-model="queryInfo.labid" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="5">
              <el-input placeholder="设备名称关键字" v-model="queryInfo.query" class="input-with-select">
                <el-button slot="append" icon="el-icon-search"></el-button>
              </el-input>
          </el-col>
          <el-col :span="4">
            <div>
              <el-button type="primary"   @click="getEqp()">搜索</el-button>
            </div>
          </el-col>
          <el-col :span="4">
            <div>
              <el-button type="primary"   @click="openAddWin()">添加设备</el-button>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <hr>
        </el-row>
        <el-row>
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
            <el-table-column  label="操作"   fixed="right" width="300">
              <template slot-scope="scope">
                <el-button type="primary" icon="el-icon-edit" @click="openUpdate(scope.row)">修改信息</el-button>
                <el-button type="danger " icon="el-icon-delete" @click="opendelete(scope.row.id)">设备报废删除</el-button>
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
      <!--对话框区-->
      <!--删除-->
      <el-dialog
        title="报废设备删除"
        :visible.sync="delWindow"
        width="50%">
        <span>
         警告!您确定要删除这个记录?
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="delWindow = false">取 消</el-button>
          <el-button type="primary" @click="del()">确 定</el-button>
        </span>
      </el-dialog>

      <!--修改-->
      <el-dialog
        title="修改设备信息"
        :visible.sync="setWindow"
        width="30%">
        <span>
          <el-row>
            修改设备所属实验室(仅支持您管理的实验室):
          </el-row>
          <el-row>
             <el-select v-model="setinfo.labid" placeholder="请选择" style="margin-top: 10px">
              <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-row>
            <el-row>
              修改设备名称:
            </el-row>
           <el-row>
             <el-input placeholder="请输入名称" v-model="setinfo.name" clearable style="margin-top: 10px;"></el-input>
            </el-row>
           <el-row>
             修改设备状态:
           </el-row>
           <el-row>
              <el-select v-model="setinfo.state" placeholder="请选择" style="margin-top: 10px">
              <el-option
                v-for="item in states"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            </el-row>
          <el-row>

          </el-row>
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="setWindow = false">取 消</el-button>
          <el-button type="primary" @click="set()">确 定</el-button>
        </span>
      </el-dialog>
      <!--添加-->
      <el-dialog
        title="添加新设备"
        :visible.sync="addWindow"
        width="50%">
        <span>
          <el-row>
            请绑定该设备实验室
          </el-row>
          <el-row>
             <el-select v-model="addinfo.labid" placeholder="请选择" style="margin-top: 10px">
              <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-row>
         <el-row>
           请输入设备的名称(编号)，建议不要和其他设备重复
         </el-row>
          <el-row>
            <el-input placeholder="请输入设备名称" v-model="addinfo.name" clearable style="margin-top: 10px;"></el-input>
          </el-row>
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addWindow = false">取 消</el-button>
          <el-button type="primary" @click="add()">确 定</el-button>
        </span>
      </el-dialog>
    </div>
</template>

<script>
  export default {
    name: 'EqpCrud',
    data(){
      return{
        tableData:[],
        delWindow:false,
        addWindow:false,
        setWindow:false,
        getInfo:{
          uid:'',
        },
        setinfo:{
          id:'',
          labid:'',
          state:'',
          name:'',
        },
        queryInfo:{
          query:'',
          pagenum:1,//当前页数
          labid:'',
        },
        addinfo:{
          labid:'',
          name:''
        },
        options: [],
        EqpList:[],
        total:0,
        pagesize:100,
        eqpid:0,
        states: [{
          value:  0,
          label: '设备故障'
        }, {
          value: 1,
          label: '设备正常'
        }]
      }
    },
    methods:{
      async loadLabData () {
        let res= await this.$http.post("lab/getLabByUid",this.getInfo);
        if (res.data.flag){
          this.options=res.data.extend.datas;
        } else{
          this.$message.error("请求数据失败！")
        }
      },
      async getEqp(){//获取设备
        const res = await this.$http.post('eqp/getEqpByLabid',this.queryInfo);
        if (!res.data.flag){
          this.$message.error("请先选择实验室！")
          return;
        }
        const list=res.data.extend.eqps;
        this.total=res.data.extend.total;
        this.EqpList=list;
      },
      //监听页码改变
      handleCurrent(newNum){
        this.queryInfo.pagenum=newNum;
        this.getEqp();
      },
      handleSize(newSize){

      },
      // crud
      async delpost () {
        let res = await this.$http.post("eqp/delEqp", {
          id: this.eqpid
        });
        return res.data.flag;
      },
      del () {
          let  f=this.delpost();
        if (f) {
          this.$message.success("删除成功!");
          this.getEqp();
        } else {
          this.$message.error("删除失败！")
        }
      },
      async setpost () {
        let res = await this.$http.post("eqp/setEqp", this.setinfo);
        console.log(res.data.flag)
        return res.data.flag;
      },
      set(){
        this.$confirm('是否保存当前页面所编辑的内容？', '确认信息', {
          distinguishCancelAndClose: true,
          confirmButtonText: '保存',
          cancelButtonText: '放弃修改'
        }).then(() => {
          let  f=this.setpost();
          if(f){
            this.$message({
              type: 'info',
              message: '保存成功'
            });
          }else{
            this.$message({
              type: 'info',
              message: '保存失败'
            });
          }
          this.getEqp();
          this.setWindow=false;
          })
          .catch(action => {
            this.$message({
              type: 'info',
              message: action === 'cancel'
                ? '放弃保存并离开页面'
                : '停留在当前页面'
            })
          });
        if (f) {
          this.$message.success("保存成功!");
          this.getEqp();
        } else {
          this.$message.error("保存失败！")
        }
      },
      async addpost(){
        let res=this.$http.post("eqp/addEqp",this.addinfo);
        return res.data.flag;
      },
      add(){
        if (this.addinfo.name==null||this.addinfo.name==''){
          this.$message.error("内容不能为空!")
          return;
        }
        if (this.addinfo.labid==''||this.addinfo.labid==null){
          this.$message.error("内容不能为空!")
          return;
        }
        this.$confirm('此操作将增加一个新设备, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let f=this.addpost();
          if (f){
            this.$message({
              type: 'success',
              message: '保存成功!'
            });
          } else{
            this.$message({
              type: 'error',
              message: '保存失败!'
            });
          }
          if (this.queryInfo.labid==''||this.queryInfo.labid.length==0) {
            return;
          }else{
            this.getEqp();
          }
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消新增'
          });
        });
      },
      openAddWin(){
        this.addWindow=true;
        this.loadLabData();
      },
      opendelete(id){
        this.delWindow=true;
        this.eqpid=id;
      },
      openUpdate(data){
        this.setWindow=true;
        this.setinfo.labid=data.lab.id
        this.setinfo.id=data.id;
        this.setinfo.state=data.state;
        this.setinfo.name=data.name;
        this.loadLabData();
      }
    },
    created () {
      let user = window.sessionStorage.getItem("user");
      let parse = JSON.parse(user);
      this.getInfo.uid=parse.id;
      this.loadLabData();
    }
  }
</script>

<style scoped>

</style>

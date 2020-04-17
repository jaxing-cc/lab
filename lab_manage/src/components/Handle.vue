<template>
    <div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/noticeshow' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>申报处理</el-breadcrumb-item>
      </el-breadcrumb>

      <el-card class="box-card">
        <el-row style="margin-top: 15px;">
          <el-form>
            <el-col :span="6">
              <span>
                申报状态:
              </span>
              <el-select v-model="queryInfo.done" placeholder="请选择" @change="loadData">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="2" style="margin-top: 3px;">
               <span >
                    查询起始日期:
               </span>
            </el-col>
            <el-col :span="5">
              <div class="block">
                <el-date-picker
                  v-model="datetime"
                  align="right"
                  type="date"
                  placeholder="该条件为查询起始日期"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </div>
            </el-col>
            <el-col :span="3">
              <el-button type="success" @click="loadData">搜索</el-button>
            </el-col>
          </el-form>
        </el-row>
        <!--table数据-->
        <el-row class="eqptable" style="margin-top: 30px;">
          <el-table :data="dataList" border style="width: 100%" stripe height="500">
            <el-table-column type="index">
            </el-table-column>
            <el-table-column prop="user.name" label="申报人" width="150">
            </el-table-column>
            <el-table-column prop="date" label="申报时间" width="150">
            </el-table-column>
            <el-table-column prop="eqp.name" label="申报设备" width="100">
            </el-table-column>
            <el-table-column prop="reason" label="原因" width="200">
            </el-table-column>
            <el-table-column prop="eqp.lab.name" label="设备所属实验室" width="150">
            </el-table-column>
            <el-table-column prop="mast.name" label="实验室管理员" width="100">
            </el-table-column>
            <el-table-column prop="mast.phone" label="实验室管理员电话" width="180">
            </el-table-column>
            <el-table-column  label="操作"   fixed="right">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="danger"
                  @click="handle(scope.row)"
                  v-if="queryInfo.done!=4"
                >处理</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-row>
        <!--分页-->
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
      <el-dialog
        title="处理维修申报"
        :visible.sync="dialogVisible"
        width="50%">
        <span>
          <el-row>
             <el-alert
               title="警告"
               type="warning"
               description="请确认已经实际考察设备或已经联系实验室管理员"
               :closable="false">
            </el-alert><br><br>
          </el-row>
          <el-row>
            <el-col :span="8">
              设备名:
            </el-col>
            <el-col :span="8">
                  <el-input v-model="eqpname" disabled></el-input>
            </el-col>
          </el-row> <br>
          <el-row>
            <el-col :span="8">
              管理员名:
            </el-col>
            <el-col :span="8">
                    <el-input v-model="eqpmanager" disabled></el-input>
            </el-col>
          </el-row> <br>
          <el-row>
            <el-col :span="8">
              管理员联系方式:
            </el-col>
            <el-col :span="8">
              <el-input v-model="eqpmanagerphone" disabled></el-input>
            </el-col>
          </el-row><br>
          <el-row>
             <el-alert
               title="请修改目前设备维修进度"
               type="warning"
               :closable="false">
            </el-alert><br>
             <el-alert
               title="修改到步骤5会生成相应维修日志！"
               type="warning"
               :closable="false">
            </el-alert><br>
          </el-row>
          <el-row>
            <el-steps :active="active" finish-status="success">
              <el-step title="步骤 1"  description="尚未证实故障"></el-step>
              <el-step title="步骤 2"  description="已经证实尚未处理"></el-step>
              <el-step title="步骤 3"  description="已联系人员处理"></el-step>
              <el-step title="步骤 4"  description="维修中"></el-step>
              <el-step title="步骤 5"  description="处理完毕"></el-step>
            </el-steps>
            <el-button style="margin-top: 12px;" @click="last" v-if="active != 1 && active!=0">上一步</el-button>
            <el-button style="margin-top: 12px;" @click="next" v-if="active != 5">下一步</el-button>
          </el-row>
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="openwin">保存该进度</el-button>
        </span>
      </el-dialog>
    </div>
</template>

<script>
  export default {
    name: 'Handle',
    data () {
      return {
        eqpname:'',
        eqpmanager:'',
        eqpmanagerphone:'',
        active: 100,
        dialogVisible:false,
        dataList:[],
        options: [{
          value: 0,
          label: '尚未证实故障'
        }, {
          value: 1,
          label: '已经证实尚未处理',
        }, {
          value: 2,
          label: '已联系人员处理'
        }, {
          value: 3,
          label: '维修中'
        }, {
          value: 4,
          label: '处理完毕'
        }],
        datetime:'2020-01-01',
        queryInfo:{
          done:0,
          date:'2000-01-01 00:00:00',
          pagenum:1
        },
        setInfo:{
          // done:0,
          id:0,
          uid:0,
          eid:0,
        },
        pagesize:100,
        total:0
      }
    },
    methods:{
      next() {
        this.active++;
        // this.done_=this.active-1;
      },
      last() {
        this.active--;
        // this.done_=this.active-1;

      },
      async loadData () {
        if (this.datetime!==''){
          this.queryInfo.date=this.datetime+" 00:00:00"
        }
        let response = await this.$http.post("declare/getAllByDone", this.queryInfo);
        this.dataList=response.data.extend.datas;
        this.total=response.data.extend.total;
      },
      async loadDataforone () {
        if (this.datetime!==''){
          this.queryInfo.date=this.datetime+" 00:00:00"
        }
        this.queryInfo.done=0;
        let response = await this.$http.post("declare/getAllByDone", this.queryInfo);
        this.dataList=response.data.extend.datas;
        this.total=response.data.extend.total;
      },
      //监听页码改变
      handleCurrent(newNum){
        this.queryInfo.pagenum=newNum;
        this.loadData();
      },
      handleSize(newSize){

      },
      handle(row){
        this.active=row.done+1;
        this.dialogVisible=true;
        this.eqpname=row.eqp.name;
        this.eqpmanager=row.mast.name;
        this.eqpmanagerphone=row.mast.phone;
        this.done_=row.done;
        //设置发送参数
        let user = window.sessionStorage.getItem("user");
        let parse = JSON.parse(user);
        this.setInfo.uid=parse.id;
        this.setInfo.id=row.id;
        this.setInfo.eid=row.eqp.id;
      },
      async setDone () {
        this.setInfo.done=this.active-1;
        let res = await this.$http.post("declare/setDone", this.setInfo);
        console.log(res.data.flag)
        return res.data.flag;
      },
      openwin(){
        this.$confirm('此操作将保存当前所选申报表的维修进度, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let f=this.setDone();
          if (f) {
            this.$message({
              type: 'success',
              message: '保存成功!'
            })
            let i;
            for (i = 0; i <this.dataList.length ; i++) {
              if (this.setInfo.id==this.dataList[i].id) {
                break;
              }
            }
            this.dataList.splice(i,1);
            // this.loadDataforone()
          }else{
            this.$message({
              type: 'error',
              message: '保存失败!'
            })
          }
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消保存'
          });
        });
        // this.loadDataforone()
      }
    },
    created () {
      this.loadData();
    }
  }
</script>

<style scoped>

</style>

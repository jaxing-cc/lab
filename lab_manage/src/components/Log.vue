<template>
    <div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/noticeshow' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>维修成功设备信息日志</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card class="box-card">
        <el-row>
          <el-col :span="6">
            <el-alert
              title="系统会查询这个时间后的记录--->>>"
              type="info"
              :closable="false">
            </el-alert><br>
          </el-col>
          <el-col :span="5">
            <el-date-picker
              v-model="datetime"
              align="right"
              type="date"
              placeholder="该条件为查询起始日期"
              value-format="yyyy-MM-dd">
            </el-date-picker>
          </el-col>
          <el-col :span="2">
            <el-button type="success" @click="loadData">搜索</el-button>
          </el-col>
        </el-row>
          <el-row>


            <el-table :data="tableData" border style="width: 100%" stripe height="500">
              <el-table-column type="index">
              </el-table-column>
              <el-table-column prop="date" label="日期" width="150">
              </el-table-column>
              <el-table-column label="日志内容">
                <template slot-scope="scope">
                  <div v-if="scope.row.declare!=null">
                    <el-tag type="success">由用户:</el-tag><el-link type="danger">{{scope.row.declare.user.name}}</el-link> <el-tag type="success">申报的,来自:  </el-tag><el-link type="danger">{{scope.row.declare.eqp.lab.name}}</el-link><el-tag type="success">的设备:</el-tag><el-link type="danger">{{scope.row.declare.eqp.name}}</el-link><el-tag type="success">的维修信息,</el-tag>
                    <el-tag type="success"> 已经被管理员:</el-tag>  <el-link type="danger">{{scope.row.user.name}} </el-link><el-tag type="success">处理完成,</el-tag><el-tag type="success">设备已经恢复正常</el-tag>
                  </div>
                  <div v-if="scope.row.declare==null">
                    该申报表已经被删除
                  </div>
                </template>
              </el-table-column>
            </el-table>

          </el-row>
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

    </div>
</template>

<script>
  export default {
    name: 'Log',
    data(){
      return{
        tableData:[],
        datetime:'2020-01-01',
        queryInfo:{
          pagenum:1,
          date:'2020-01-01 00:00:00',
        },
        pagesize:100,
        total:0
      }
    },
    methods:{
      async loadData () {
        if (this.datetime!==''){
          this.queryInfo.date=this.datetime+' 00:00:00';
        }
        const res = await this.$http.post("log/getAll", this.queryInfo);
        this.tableData=res.data.extend.datas;
        this.total=res.data.extend.total;
        console.log(this.tableData)
      },
      //监听页码改变
      handleCurrent(newNum){
        this.queryInfo.pagenum=newNum;
        this.loadData();
      },
      handleSize(newSize){

      },
    },
    created () {
      this.loadData();
    }
  }
</script>

<style scoped>
.maintext{
  font-size: 20px;
  color: blue;
}
</style>

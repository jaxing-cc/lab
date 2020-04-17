<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/noticeshow' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>实验室设备查询</el-breadcrumb-item>
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
              placeholder="请选择实验室名称"
              :select-when-unmatched="true"
              @select="handleSelect"
            ><template slot-scope="{ item }">
              <span style="float:left;">{{ item.name }}</span>
            </template>
            </el-autocomplete>
          </div>
        </el-col>
        <el-col :span="4">
          <div style="margin-top: 15px;">
            <el-button type="primary" round  @click="getEqp()">搜索</el-button>
          </div>

        </el-col>
        <el-col :span="5">
          <div style="margin-top: 15px;">
            <el-input placeholder="设备搜索" v-model="queryInfo.query" class="input-with-select">
              <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
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
          <el-table-column prop="lab.master.name" label="管理员名称">
          </el-table-column>
          <el-table-column prop="lab.master.phone" label="管理员电话">
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

  </div>

</template>

<script>
  export default {
    name: 'Eqpfind',
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
          this.$message.error("请求失败！")
          return;
        }
        const list=res.data.extend.eqps;
        this.total=res.data.extend.total;
        this.EqpList=list;
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

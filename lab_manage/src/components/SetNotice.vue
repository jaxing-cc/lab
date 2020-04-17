<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/noticeshow' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>公告管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20" style="margin-top: 15px">
        <el-col :span="2" style="margin-top: 5px">
          添加新公告:
        </el-col>
        <el-col :span="10">
          <!--<el-input v-model="saveInfo.info" placeholder="请输入公告内容！"></el-input>-->
          <el-input
            type="text"
            placeholder="请输入内容"
            v-model="saveInfo.info"
            maxlength="40"
            show-word-limit
          ></el-input>
        </el-col>
        <el-col :span="5">
          <el-button type="success" @click="open()">添加这个公告</el-button>
        </el-col>
      </el-row>
       <el-row>
         <hr style="margin-top: 40px">
         <el-table
           :data="dataList"
           height="500"
           style="width: 100%">
           <el-table-column type="index">
           </el-table-column>
           <el-table-column prop="date" label="日期" width="180">
           </el-table-column>
           <el-table-column prop="user.name" label="发布人" width="180">
           </el-table-column>
           <el-table-column prop="info" label="内容">
           </el-table-column>
           <el-table-column  label="操作"  fixed="right" width="80">
             <template slot-scope="scope">
               <el-button
                 size="mini"
                 type="danger"
                 @click="opwin(scope.row)">删除</el-button>

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
       </el-row>
    </el-card>
    <el-dialog
      title="警告"
      :visible.sync="visible"
      width="30%">
      <span>你确认要删除这条公告？</span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="visible = false">取 消</el-button>
    <el-button type="primary" @click="deletebyid">确 定</el-button>
  </span>
    </el-dialog>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%">
      <span>
          你确定要提交这份公告？
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save()">确 定</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
  export default {
    name: 'SetNotice',
    data(){
      return{
        dialogVisible:false,
        visible:false,
        saveInfo:{
          uid:'',
          info:''
        },
        dataList:[],
        queryInfo:{
          pagenum:1,
          pagesize:100
        },
        delInfo:{
          id:''
        },
        total:0
      }
    },
    methods:{
      open(){
        if (this.saveInfo.info===''){
          this.$message.error("公告内容输入框不能为空！")
        }else{
          this.dialogVisible=true;
        }
      },
      deletebyid(){
        let f=this.delete();
        if (f){
          this.$message.success("删除成功！")
        } else{
          this.$message.error("请求失败！")
        }
        this.loadData();
      },
      async delete(){
        const res=await this.$http.post("notice/del",this.delInfo)
        return res.data.flag;
      },
      async add () {
       const res=await this.$http.post("notice/add",this.saveInfo)
        return res.data.flag;
      },
      async loadData () {
        let response = await this.$http.post("notice/getAll", this.queryInfo);
        this.dataList=response.data.extend.datas;
        this.total=response.data.extend.total;
      },
      opwin(data){
        this.visible=true;
          this.delInfo.id=data.id;
      },
      save(){
        let f=this.add();
        if (f){
          this.$message.success("发送成功！")
        } else{
          this.$message.error("请求失败！")
        }
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
      let user = window.sessionStorage.getItem("user");
      let parse = JSON.parse(user);
      this.saveInfo.uid=parse.id;
      this.loadData();
    }
  }
</script>

<style scoped>

</style>

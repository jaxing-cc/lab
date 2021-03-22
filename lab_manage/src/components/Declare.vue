<template>
    <div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/noticeshow' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>我的申报记录与进度</el-breadcrumb-item>
      </el-breadcrumb>

      <el-card class="box-card">
        <el-row :gutter="20">
          <el-col :span="5">
          </el-col>
          <el-col :span="10">
            我的申报列表
          </el-col>
          <el-col :span="5">
          </el-col>
        </el-row>
        <!--table数据-->
        <el-row class="declareTable" style="margin-top: 10px">
          <el-table :data="declareList" border style="width: 100%" stripe height="500">
            <el-table-column type="index">
            </el-table-column>
            <el-table-column prop="user.name" label="申报人" width="100">
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
            <el-table-column prop="mast.phone" label="管理员电话" width="120">
            </el-table-column>
            <!--<el-table-column  label="申报进度" :formatter="doneFilter" width="180">-->
            <!--</el-table-column>-->
            <el-table-column  label="申报进度"  width="180">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="success"
                  @click="openwin(scope.row)">点进查看申报进度</el-button>
              </template>
            </el-table-column>
            <el-table-column  label="申报图片上传"  width="180">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="primary"
                  @click="openUpload(scope.row)">上传设备图片</el-button>
              </template>
            </el-table-column>
            <el-table-column  label="图片管理"  width="120">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="primary"
                  @click="openImages(scope.row)">管理</el-button>
              </template>
            </el-table-column>
            <!--<el-table-column  label="操作"   fixed="right">-->
              <!--<template slot-scope="scope">-->
                <!--<el-button-->
                  <!--size="mini"-->
                  <!--type="danger"-->
                  <!--@click="handleDelete(scope.row)">删除</el-button>-->
              <!--</template>-->
            <!--</el-table-column>-->
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

<!--对话框-->
      <el-dialog
        title="处理维修申报"
        :visible.sync="dialogVisible"
        width="50%">
        <span>
          <el-row>
             <el-alert
               title="以下目前设备维修进度"
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
          </el-row>
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">关 闭</el-button>
        </span>
      </el-dialog>
<!--     上传图片对话框 -->
      <el-dialog
        title="设备损坏图片上传"
        :visible.sync="uploadDialog"
        width="20%">
        <span>
          <el-row>
                <el-upload
                  ref="imgUploadRef"
                  class="upload-demo"
                  :action="action+'img/upload'"
                  :before-upload="beforeUpload"
                  :before-remove="beforeRemove"
                  :headers="importHeaders"
                  multiple
                  :limit="1"
                  :on-exceed="handleExceed"
                  :on-success="handleUploadSuccess"
                  :on-error="handleUploadError"
                  name="file"
                  :auto-upload="false">
                    <el-button size="small" type="primary">点击上传</el-button>
                    <div slot="tip" class="el-upload__tip">只能上传jpg文件，且不超过2M</div>
                  </el-upload>
          </el-row>
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="uploadDialog = false">关 闭</el-button>
          <el-button @click="saveImg">保 存</el-button>
        </span>
      </el-dialog>
<!--      上传管理-->
      <el-drawer
        title="图片管理"
        :visible.sync="imgDialog"
        direction="rtl"
        @close="url = '#'"
        width="30%">
      <span>
        <div v-if="imgs.length !== 0">
          <ul>
            <li v-for="(item,index) in imgs" :key="item.id">
             图片_{{index + 1 }} &nbsp;&nbsp;
              <el-button icon="el-icon-delete" circle @click="remove(item)"></el-button>
              <el-button icon="el-icon-search" circle @click="look(item.path)"></el-button>
            </li>
          </ul>
          <hr>
          <el-image
            style="width: 300px; height: 300px; margin-left: 10px"
            :src="url"
            fit="contain">
          </el-image>
        </div>
        <div v-if="imgs.length === 0">
          <ul>
            <li>
              还没有上传图片
            </li>
          </ul>
        </div>
      </span>
      </el-drawer>
    </div>
</template>

<script>
  export default {
    name: 'Declare',
    data () {
      return{
        imgDialog:false,
        uploadDialog:false,
        dialogVisible:false,
        active:0,
        state:"",
        declareList:[],
        importHeaders: {
          token:window.sessionStorage.getItem("token")
        },
        url:'#',
        total:0,
        queryInfo:{
          pagenum:1,//当前页数
          uid:'',
        },
        action:"http://localhost:8080/",
        pagesize:100,
        imgDeclare:{
          did:0,
          path:"",
        },
        imgs:[]
      }
    },
    methods:{
      look(path){
        this.$http.get('img/url?key='+path).then( res =>{
          this.url = res.data.extend.img;
        })
      },
      remove(item){
          this.$http.delete('img/delete', {data:item}).then(res =>{
            if (res.data.flag){
              this.$message.success("删除成功")
              this.imgDialog = false;
            }else{
              this.$message.error("删除失败")
              this.imgDialog = false;

            }
          })
      },
      openImages(row){
        this.loadImgs(row.id);
        this.imgDialog = true;
      },
      async loadImgs(id) {
        const res = await this.$http.get('img/getImg/'+id);
        this.imgs = res.data.extend.imgs;
      },
      saveImg(){
        if (this.imgs.length < 3){
          this.$refs.imgUploadRef.submit();
        }else{
          this.$message.error("图片最多上传三张!");
        }
      },
      handleUploadError(){
        this.$message.error("服务器出现小问题...");
      },
      //上传成功
      handleUploadSuccess(response){
        if (response.flag){
          this.imgDeclare.path = response.extend.path
          this.$http.post("img/saveKey",this.imgDeclare).then(res =>{
            if (res.data.flag){
              this.$message.success("上传成功!");
              this.uploadDialog = false;
            }else{
              this.$message.error("上传失败，请稍候再试!");
            }
          })
        }else{
          this.$message.error("上传失败，请稍候再试!");
        }
      },
      //文件超出个数限制时的钩子
      handleExceed(){
        this.$message.error("每次只能上传一张图片，可上传三次");
      },
      beforeRemove(){
        return true;
      },
      beforeUpload(file){
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size <= (1024 * 1024 * 2);
        if (!isJPG) {
          this.$message.error('上传商品图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传商品图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },
      openUpload(row){
        this.loadImgs(row.id);
        this.imgDeclare.did = row.id
        this.uploadDialog = true;
      },
      async loadAll () {
        let user = window.sessionStorage.getItem("user");
        let parse = JSON.parse(user);
        this.queryInfo.uid=parse.id;
        const res = await this.$http.post('declare/getByUid',this.queryInfo);
        this.declareList = res.data.extend.datas;
        this.total=res.data.extend.total;
      },
      handleCurrent(newNum){
        this.queryInfo.pagenum=newNum;
        this.loadAll();
      },
      handleSize(newSize){
      },
      //处理状态
      doneFilter(row,column){
          if (row.done==0){
            return "管理员未证实！"
          }else if(row.done==1){
            return "管理员已证实！"

          }else if(row.done==2){
            return "已经联系相关人员处理！"

          }else if(row.done==3){
            return "维修中！"

          }else if(row.done==4){
            return "维修完毕，设备可以使用！"
          }
      },
      async deleteByid(id){
        console.log(id)
        let newVar = await this.$http.post("declare/deleteById",{
          id:id
        })
        return newVar.data.flag;
      },
      openwin(data){
        this.dialogVisible=true;
        this.active=data.done+1;
      },
       handleDelete (data) {
         this.$confirm('此操作将删除申报, 是否继续?', '提示', {
           confirmButtonText: '确定',
           cancelButtonText: '取消',
           type: 'warning'
         }).then(() => {
           let flag = this.deleteByid(data.id);
           if (flag) {
             this.$message({
               type: 'success',
               message: '删除成功!'
             })
             this.loadAll();
           }else{
             this.$message({
               type: 'error',
               message: '删除失败!'
             })
           }
         }).catch(() => {
           this.$message({
             type: 'info',
             message: '已取消删除'
           });
         });

      }
    },
    created () {
      this.loadAll();
    }

  }
</script>

<style scoped>

</style>

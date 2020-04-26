<template>
    <div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/noticeshow' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>技术交流</el-breadcrumb-item>
      </el-breadcrumb>
        <br>
      <div v-if="!isOpen">
        <el-row :gutter="20" style="height: 50px">
          <el-col :span="10" style="border-radius: 5px;height: 30px">
            <el-button type="warning" plain @click="manager">管理我的博客</el-button>
          </el-col>

          <el-col :span="5">
            <el-input type="text" placeholder="请输入关键字" v-model="queryInfo.query"></el-input>
          </el-col>
          <el-col :span="2">
          <el-button type="primary" plain @click="getBlogs">查询</el-button>
        </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <mavon-editor v-model="data" :ishljs = "true"
                          ref="md"
                          @change="change"
                          @save="submit"
                          :toolbars="toolbars"
                          :subfield="false"
                          placeholder='分享你的技术和心得...'
                          style="height: 575px"/>
          </el-col>
          <el-col :span="14">
            <el-card>
              <el-row>
                <el-table
                  :data="blog"
                  stripe
                  :height="500"
                  @row-dblclick="openBlog"
                  style="width: 100%;">

                  <el-table-column prop="data" label="博客列表">
                    <template slot-scope="scope">
                      <div>
                        <h3 v-text="scope.row.titile"></h3>
                        <div v-text="scope.row.data"></div>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column prop="good" width="100px" fixed="right">
                    <template slot-scope="scope">
                      <div>
                        <div v-text="scope.row.user.name"></div>
                        <div v-text="'good:'+scope.row.good"></div>
                      </div>
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
                  :page-size="queryInfo.pagesize"
                  layout="total,  prev, pager, next, jumper"
                  :total="total">
                </el-pagination >
              </el-row>
            </el-card>

          </el-col>
        </el-row>
      </div>
      <div v-if="isOpen">
        <el-card>
          <el-row>
            <el-col :span="5">
              <el-button @click="retuenMain" type="info">返回上一页</el-button>
            </el-col>
            <el-col :span="5">
              <el-button :type="goodStatus?'success':'warning'" icon="el-icon-thumb" @click="goodup">点赞:<span v-text="good"></span></el-button>
            </el-col>
          </el-row>
          <el-row :gutter="22">
            <hr>
            <el-col :span="6">
            <h3>作者:<div v-text="'---------'+name"></div></h3>
            <h3>电话:<div v-text="'---------'+phone"></div></h3>
          </el-col>
            <el-col :span="16">

                <div v-html="this.html">

                </div>

            </el-col>

          </el-row>

        </el-card>
      </div>


      <el-dialog
        title="添加标题"
        :visible.sync="addWindow"
        width="30%">
        <span>
          <el-input type="text" placeholder="请输入标题" v-model="addinfo.titile"></el-input>
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addWindow = false">取 消</el-button>
          <el-button type="primary" @click="add">保 存</el-button>
        </span>
      </el-dialog>
      <el-drawer
        title="管理"
        :visible.sync="drawer"
        size="50%"
        :before-close="handleFlush"
        :direction="'ltr'">
        <span>
               <el-row>
                <el-table
                  :data="manage.blog"
                  stripe
                  :height="500"
                  style="width: 100%;">

                  <el-table-column prop="data" label="日期" width="180px">
                  </el-table-column>
                  <el-table-column prop="titile" label="标题" width="300px">
                  </el-table-column>
                  <el-table-column  label="操作" fixed="right" width="200">
                    <template slot-scope="scope">
                        <el-button type="danger" icon="el-icon-delete" @click="del(scope.row.id)">移除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </el-row>
              <el-row>
                <el-pagination
                  @size-change="handleSize_m"
                  @current-change="handleCurrent_m"
                  :current-page="manage.queryInfo.pagenum"
                  :page-sizes="[100,200]"
                  :page-size="manage.queryInfo.pagesize"
                  layout="total,  prev, pager, next, jumper"
                  :total="manage.total">
                </el-pagination >
              </el-row>
        </span>
      </el-drawer>
    </div>

</template>

<script>
  export default {
    name: 'LabBlog',

    data(){
      return{
        id:0,
        goodStatus:false,
        isOpen:false,
        html:'',
        name:'',
        good:0,
        goodcache:0,
        phone:'',
        drawer:false,
        addWindow:false,
        blog:[],
        apply:{
          uid:0,
        },
        queryInfo:{
          pagenum:1,//当前页数
          pagesize:10,
          query:'',
        },
        addinfo:{
          text:'',
          titile:'',
          uid:-1
        },
        total:0,
        data:'',
        toolbars: {
          bold: true, // 粗体
          italic: true, // 斜体
          header: true, // 标题
          underline: true, // 下划线
          strikethrough: true, // 中划线
          mark: true, // 标记
          superscript: true, // 上角标
          subscript: true, // 下角标
          quote: true, // 引用
          ol: true, // 有序列表
          ul: true, // 无序列表
          link: true, // 链接
          code: true, // code
          table: true, // 表格
          fullscreen: true, // 全屏编辑
          readmodel: true, // 沉浸式阅读
          htmlcode: true, // 展示html源码
          help: true, // 帮助
          /* 1.3.5 */
          undo: true, // 上一步
          redo: true, // 下一步
          trash: true, // 清空
          save: true, // 保存（触发events中的save事件）
          /* 1.4.2 */
          navigation: true, // 导航目录
          /* 2.1.8 */
          alignleft: true, // 左对齐
          aligncenter: true, // 居中
          alignright: true, // 右对齐
          /* 2.2.1 */
          subfield: true, // 单双栏模式
          preview: true, // 预览
        },
        manage:{
          blog:[],
          queryInfo:{
            pagenum:1,//当前页数
            pagesize:10,
            uid:0,
          },
          total:0,
        }
      }
    },
    methods: {
      handleFlush(){
        this.getBlogs();
        this.drawer=false;
      },
      manager(){
        this.drawer=true;
        let user = window.sessionStorage.getItem("user");
        let parse = JSON.parse(user);
        this.manage.queryInfo.uid = parse.id;
        this.getMyBolg();
      },
      async del (data) {
        let res = await this.$http.post("blog/delete", {
          id:data
        });
        if (res.data.flag){
          this.getMyBolg();
          this.$message.success("移除成功");
        }else{
          this.$message.error("移除失败");
        }
      },
      async getMyBolg () {
        let res = await this.$http.post("blog/getByUid", this.manage.queryInfo);
        this.manage.blog = res.data.extend.datas;
        this.manage.total = res.data.extend.total;
      },
      goodup(){
        this.goodStatus=true;
        this.good=this.goodcache+1;
      },
      async retuenMain () {
        if (this.good != this.goodcache) {
          await this.$http.post("blog/good", {
            id: this.id
          })
          this.getBlogs();
        }
        this.isOpen = false;
        this.html = ''
        this.name = ''
        this.goodcache = 0,
          this.good = 0;
        this.phone = ''
        this.id = 0;
      },
      openBlog(row, column, event){
        this.html=row.text;
        this.name=row.user.name;
        this.good=row.good;
        this.goodcache=row.good;
        this.phone=row.user.phone;
        this.id=row.id;
        this.isOpen=true;
      },
      async getBlogs () {//getAll
        let res = await this.$http.post("blog/getAll", this.queryInfo);
        this.blog=res.data.extend.datas;
        this.total=res.data.extend.total;
      },
      handleCurrent (newNum) {
        this.queryInfo.pagenum = newNum;
        this.getBlogs();
      },
      handleSize (newSize) {
      },
      handleCurrent_m (newNum) {
        this.manage.queryInfo.pagenum = newNum;
        this.getMyBolg();
      },
      handleSize_m (newSize) {
      },
      // 所有操作都会被解析重新渲染
      change (value, render) {
        // render 为 markdown 解析后的结果[html]
        this.addinfo.text = render;
      },
      // 提交
      submit () {
        let user = window.sessionStorage.getItem("user");
        let parse = JSON.parse(user)
        this.addinfo.uid = parse.id;
        if (this.data.length==0){
            this.$message.info("内容不能为空")
        } else{
            this.addWindow=true;
        }
      },
      async add () {
        let res = await this.$http.post("blog/add", this.addinfo);
        if (res.data.flag){
          this.$message.success("保存成功")
        } else{
          this.$message.error("保存失败")
        }this.addWindow=false;
        this.getBlogs();
      },
    },
    created () {
      this.getBlogs();
    }
  }
</script>

<style scoped>


</style>

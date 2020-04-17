<template>
  <div class="logindiv">
    <div class="loginbox">
      <div id="logintheme">
        实验室管理系统
      </div>
      <div class="logininp">
        <!--表单-->
        <el-form label-width="80px" :model="login_From" :rules="loginRules" ref="loginFromRef">
          <el-form-item label="用户名" prop="username">
            <el-input type="text" v-model="login_From.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="login_From.password"></el-input>
          </el-form-item>
        </el-form>
        <div class="btns">
          <el-button type="primary" @click="login()">登录</el-button>
          <el-button type="warning" @click="reset()">重置</el-button>
          <el-button type="success" @click="opregister()">注册</el-button>
        </div>
      </div>
    </div>
    <el-dialog
      title="注册页面"
      :visible.sync="dialogVisible"
      width="50%">
      <span>
        <el-form label-width="80px" :model="addinfo" :rules="addRules" ref="addFromRef">
         <el-row>
             <el-form-item label="姓名" prop="name">
                <el-input v-model="addinfo.name" placeholder="请输入姓名" style="margin-top: 10px"></el-input>
             </el-form-item>
        </el-row>
         <el-row>
              <el-form-item label="用户名" prop="username">
                  <el-input v-model="addinfo.username" placeholder="请输入用户名" style="margin-top: 10px"></el-input>
              </el-form-item>
        </el-row>
         <el-row>
              <el-form-item label="密码" prop="password">
                <el-input v-model="addinfo.password" placeholder="请输入密码" type="password" style="margin-top: 10px"></el-input>
              </el-form-item>
        </el-row>
         <el-row>
              <el-form-item label="电话" prop="phone">
                  <el-input v-model="addinfo.phone" placeholder="请输入电话" style="margin-top: 10px"></el-input>
              </el-form-item>
        </el-row>
        </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="opregister2()">注册</el-button>
        </span>
    </el-dialog>

    <el-dialog
      title="提示"
      :visible.sync="dialogVisible2"
      width="30%">
      <span>您确定要提交?</span>
      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible2 = false">取 消</el-button>
          <el-button type="primary" @click="register()">注册</el-button>
        </span>
    </el-dialog>
    </div>
</template>

<script>
  export default {
    name:'Login',
    data(){
      return{
        login_From: {
          username: '',
          password: '',

        },
        loginRules:{//验证规则对象
          username:[
            { required: true, message: '请输入登陆名称', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
          ],
          password:[
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
          ],
        },
        dialogVisible:false,
        dialogVisible2:false,
        addinfo:{
          name:'',
          username:'',
          password:'',
          phone:'',
        },
        addRules:{
          username:[
            { required: true, message: '请输入登陆名称', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
          ],
          password:[
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
          ],
          phone:[
            { required: true, message: '请输入电话号码', trigger: 'blur' },
            { min: 9, max: 12, message: '长度在 9 到 12 个字符', trigger: 'blur' }
          ],
          name:[
            { required: true, message: '请输入姓名', trigger: 'blur' },
          ],
        }
      }
    },
    methods:{
      reset:function () {
        this.$refs.loginFromRef.resetFields();
      },
      login:function () {
        this.$refs.loginFromRef.validate(async valid=>{
          if (!valid) {
            return;
          }
          const res= await this.$http.post('user/login',this.login_From);
          if (res.data.flag){
            this.$message('登陆成功！');
            window.sessionStorage.setItem("token",res.data.extend.token);
            let user = JSON.stringify(res.data.extend.user)
            window.sessionStorage.setItem("user",user);
            this.$router.push("/home");
          } else{
            this.$message('账号或密码错误!请重试');
          }
        });
      },
      opregister:function () {
        this.dialogVisible=true;
      },
      opregister2:function () {
        this.dialogVisible2=true;
      },
      register:function () {
        this.$refs.addFromRef.validate(async valid=>{
          if (!valid) {
            return;
          }
          const res= await this.$http.post('user/register',this.addinfo);
          if (res.data.flag){
            this.$message.success("注册成功")
            this.dialogVisible2=false;
            this.dialogVisible=false;
            this.addinfo.phone='';
            this.addinfo.name='';
            this.addinfo.password='';
            this.addinfo.username='';
          } else{
            this.$message.error(res.data.msg);
            this.dialogVisible2=false;
          }
        });

      }
    }
  }
</script>

<style scoped>
  .logininp{
    position: absolute;
    left: 0;
    bottom: 20px;
    padding: 0 20px;
    box-sizing: border-box;
    width: 100%;
  }
  .logindiv{
    height: 100%;
    width: 100%;
    background: bisque;
    position: relative;
  }
  .loginbox{
    width: 450px;
    height: 300px;
    background: burlywood;
    border-radius: 3px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%,-50%);
  }
  .btns{
    display: flex;
    justify-content: flex-end;
  }
  #logintheme{
    position: relative;
    left: 100px;
    top: 50px;
    font-weight: bolder;
    font-size: 30px;
    color: aliceblue;
  }
</style>

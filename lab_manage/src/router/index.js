import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'//登录组件
import Home from '../components/Home.vue'//主页面
import Noticeshow from'../components/Noticeshow.vue'//公告页面
import Eqp from '../components/Eqp.vue'//实验室设备查询申报页面
import Eqpfind from '../components/Eqpfind.vue'//实验室设备查询页面
import Declare from '../components/Declare.vue'//申报查询页面
import Handle from '../components/Handle.vue'//处理申报
import Log from '../components/Log.vue'//更新日志
import SetNotice from '../components/SetNotice.vue'//设置公告
import EqpCrud from '../components/EqpCrud.vue'//设备修改
import Userrank from '../components/Userrank.vue'//设备修改
import Lab from '../components/Lab.vue'//设备修改
import LabBlog from '../components/LabBlog.vue'//实验室交流
import LabUsers from '../components/LabUsers.vue'//实验室人员管理
import mavonEditor from 'mavon-editor';//安装markdown编辑器
import 'mavon-editor/dist/css/index.css'

Vue.use(mavonEditor)
Vue.use(VueRouter)

const routes = [
//登录组件的路由规则
  { path:'/', component:Login },
  { path:'/login', component:Login },
  // { path:'/home', component:Home },
  // 嵌套路由
  {
    path:'/home',
    component:Home,
    redirect:'/noticeshow',
    children:[
      {path:'/noticeshow', component:Noticeshow},
      {path:'/eqp', component:Eqp},
      {path:'/eqpfind',component:Eqpfind},
      {path:'/level',component:Declare},
      {path:'/handle',component:Handle},
      {path:'/log',component:Log},
      {path:'/notice',component:SetNotice},
      {path:'/eqpcrud',component:EqpCrud},
      {path:'/userrank',component:Userrank},
      {path:'/lab',component:Lab},
      {path:'/lab_blog',component:LabBlog},
      {path:'/lab_users',component:LabUsers}
    ]
  }
]
//挂载导航守卫

const router = new VueRouter({
  routes
})
router.beforeEach( (to,from,next) =>{
  //to从哪来
  //from去哪
  //next放行函数
  if (to.path==='/login'){return next();}
  const token = window.sessionStorage.getItem("token")
  if (!token) {
    return next('/login');
  }
  next();
})
export default router

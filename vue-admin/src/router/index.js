import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

import Layout from '@/views/layout/Layout'
import Common from '@/views/common/common'

export const constantRouterMap = [
  {
    path: '/login',
    name: 'Login',
    component: () =>
      import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/index',
    name: '首页',
    hidden: false,
    children: [{
      path: 'index',
      meta:{
        resources:"index",
        title:"首页",
        icon:"sys_control"
      },
      component: () =>
        import('@/views/index/info')
      },
      {
      path: 'userinfo',
      name: 'UserInfo',
      hidden: true,
      component: () =>
        import('@/views/index/userinfo')
    }]
  },
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/error',
    component: () => import('@/views/error/404/index'),
    hidden: true,
  }

]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

// 异步挂载的路由
// 动态需要根据权限加载的路由表
export const asyncRouterMap = [
  {
    path: '/pre',
    component: Layout,
    redirect:"/pre/perm/index",
    name: 'pre',
    meta: {
      resources: 'pre',
      title: '权限管理'
    },
    children: [
      {
        path: 'perm',
        component: () =>import('@/views/pre/perm/index'),
        name: 'perm',
        meta: {
          resources: 'perm'
        }
      },
      {
        path: 'user',
        component: Common,
        redirect: {name:'userList'},
        name: 'user',
        meta: {
          resources: 'user'
        },
        children: [
          {
            path: 'index',
            hidden: true,
            component: () =>import('@/views/pre/user/index'),
            name:'userList',
          },
          {
            path: 'add',
            hidden: true,
            component: () =>import('@/views/pre/user/add'),
            name:'addUser',
            meta: {
              resources: 'user',
              title: "新增用户"
            },
          },
          {
            path: 'edit',
            hidden: true,
            component: () =>import('@/views/pre/user/edit'),
            name:'editUser',
            meta: {
              resources: 'user',
              title: "编辑用户"
            },
          }
        ]
      },
      {
        path: 'role',
        component: Common,
        redirect: {name:'roleList'},
        name: 'role',
        meta: {
          resources: 'role',
        },
        children: [
          {
            path: 'index',
            hidden: true,
            component: () =>import('@/views/pre/role/index'),
            name:'roleList',

          },
          {
            path: 'add',
            hidden: true,
            component: () =>import('@/views/pre/role/add'),
            name:'addRole',
            meta: {
              resources: 'role',
              title: "新增角色"
            },
          },
          {
            path: 'edit',
            hidden: true,
            component: () =>import('@/views/pre/role/edit'),
            name:'editRole',
            meta: {
              resources: 'role',
              title: "编辑角色"
            },
          }
        ]

      },
      {
        path: 'dept',
        component: Common,
        redirect: {name:'deptList'},
        name: 'dept',
        meta: {
          resources: 'dept'
        },
        children: [
          {
            path: 'index',
            hidden: true,
            component: () =>import('@/views/pre/dept/index'),
            name:'deptList',
          },
          {
            path: 'add',
            hidden: true,
            component: () =>import('@/views/pre/dept/add'),
            name:'addDept',
            meta: {
              resources: 'dept',
              title: "新增部门"
            },
          },
          {
            path: 'edit',
            hidden: true,
            component: () =>import('@/views/pre/dept/edit'),
            name:'editDept',
            meta: {
              resources: 'dept',
              title: "编辑部门"
            },
          }
        ]
      }
    ]
  },

  {
    path: '/spare',
    component: Layout,
    name: 'spare',
    meta: {
      resources: 'spare',
      title: '备品管理'
    },
    children: [
      {
        path: 'sparelist',
        component: Common,
        redirect: {name:'list'},
        name: 'list',
        meta: {
          resources: 'list'
        },
        children: [
          {
            path: 'list',
            hidden: true,
            component: () =>import('@/views/spare/sparelist/index'),
            name:'list',
          },
          {
            path: 'add',
            hidden: true,
            component: () =>import('@/views/spare/sparelist/add'),
            name:'add',
            meta: {
              resources: 'add',
              title: "新增备品"
            },
          },
          {
            path: 'edit',
            hidden: true,
            component: () =>import('@/views/spare/sparelist/edit'),
            name:'edit',
            meta: {
              resources: 'edit',
              title: "编辑备品"
            },
          }
        ]
      },

      {
        path: 'record',
        component: () => import('@/views/spare/sparerecord/index'),
        name: 'record',
        meta: {
          resources: 'record'
        }
      }
    ]
  },

  {
    path: '/maintain',
    component: Layout,
    name: 'maintain',
    meta: {
      resources: 'maintain',
      title: '保养管理'
    },
    children: [
      {
        path: 'manager',
        component: () => import('@/views/maintain/manager/index'),
        name: 'manager',
        meta: {
          resources: 'manager'
        }
      },
      {
        path: 'list',
        component: () => import('@/views/maintain/list/index'),
        name: 'list',
        meta: {
          resources: 'list'
        }
      },
      {
        path: 'record',
        component: () => import('@/views/maintain/list/index'),
        name: 'record',
        meta: {
          resources: 'record'
        }
      }
      
    ]
  },
  {
    path: '/external',
    component: Layout,
    name: 'external',
    meta: {
      resources: 'external',
      title: '保养管理'
    },
    children: [
      {
        path: 'manager',
        component: () => import('@/views/external/manager/index'),
        name: 'manager',
        meta: {
          resources: 'manager'
        }
      },
      {
        path: 'list',
        component: () => import('@/views/external/list/index'),
        name: 'list',
        meta: {
          resources: 'list'
        }
      },
      {
        path: 'record',
        component: () => import('@/views/external/list/index'),
        name: 'record',
        meta: {
          resources: 'record'
        }
      }
      
    ]
  },
  {
    path: '/produce',
    component: Layout,
    name: 'produce',
    meta: {
      resources: 'produce',
      title: '生产管理'
    },
    children: [
      {
        path: 'manager',
        component: () => import('@/views/produce/manager/index'),
        name: 'manager',
        meta: {
          resources: 'manager'
        }
      },
      {
        path: 'list',
        component: () => import('@/views/produce/list/index'),
        name: 'list',
        meta: {
          resources: 'list'
        }
      },
      {
        path: 'schdule',
        component: () => import('@/views/produce/schdule/index'),
        name: 'schdule',
        meta: {
          resources: 'schdule'
        }
      },
      {
        path: 'upload',
        component: () => import('@/views/produce/upload/index'),
        name: 'upload',
        meta: {
          resources: 'upload'
        }
      }
    ]
  },

  {
    path: '/sys',
    component: Layout,
    name: 'sys',
    meta: {
      resources: 'sys',
      title: '基础设置'
    },
    children: [
      {
        path: 'manager',
        component: () => import('@/views/sys/manager/index'),
        name: 'manager',
        meta: {
          resources: 'manager'
        }
      },
      {
        path: 'type',
        component: () => import('@/views/sys/type/index'),
        name: 'type',
        meta: {
          resources: 'type'
        }
      },
      {
        path: 'state',
        component: () => import('@/views/sys/state/index'),
        name: 'state',
        meta: {
          resources: 'state'
        }
      }
    ]
  },

  {
    path: '/job',
    component: Layout,
    name: 'job',
    meta: {
      resources: 'job',
      title: '任务调度'
    },
    children: [
  {
    path: '/joblist',
    component: Common,
    redirect: {name:'listJob'},
    name: 'list',
    meta: {
      resources: 'list'
    },
    children: [
      {
        path: 'list',
        hidden: true,
        component: () => import('@/views/job/list/index'),
        name: 'listJob',
      },
      {
        path: 'add',
        hidden: true,
        component: () => import('@/views/job/add/index'),
        name: 'addjob',
        meta: {
          resources: 'add'
        }
      },
      {
        path: 'edit',
        hidden: true,
        component: () => import('@/views/job/edit/index'),
        name: 'editjob',
        meta: {
          resources: 'edit'
        }
      }

    ]
  }]},



  {
    path: '/report',
    component: Layout,
    name: 'report',
    meta: {
      resources: 'report',
      title: '报告管理'
    },
    children: [
      {
        path: 'list',
        component: () => import('@/views/report/list/index'),
        name: 'list',
        meta: {
          resources: 'list'
        }
      },
      
    ]
  },

  {
    path: '/external-link',
    component: Layout,
    name: 'Link',
    meta: {
      resources: 'control',
      title: '开发监控',
      icon: 'link'
    },
    children: [{
      path: 'http://localhost:8090/druid/login.html',
      component:Layout,
      meta: {
        resources: 'logs',
        title: 'druid',
        icon: 'link'
      }
    },
    {
      path: 'http://localhost:8090/swagger-ui.html',
      component:Layout,
      meta: {
        resources: 'database',
        title: 'swagger-ui',
        icon: 'link'
      }
    }
    ]
  },
  {
    path: '*',
    component: () => import('@/views/error/404/index'),
    hidden: true,
    meta: {
      resources: '404',
      title: '404',
    },
  }


  

]

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
      // {
      //   path: '/redirect/:path*',
      //   component: () => import('@/views/redirect/index')
      // }
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
    redirect:"/spare/sparelist/index",
    name: 'spare',
    meta: {
      resources: 'spare'
    },
    children: [
      {
        path: 'sparelist',
        component: Common,
        redirect: {name:'sparelist'},
        name: 'list',
        meta: {
          resources: 'list'
        },
        children: [
          {
            path: 'list',
            hidden: true,
            component: () =>import('@/views/spare/sparelist/index'),
            name:'sparelist',

          },
          {
            path: 'detail',
            hidden: true,
            component: () =>import('@/views/spare/sharedetail/index'),
            name:'showdetail',
              meta: {
                title: "互推详情"
              },
          },
          // {
          //   path: 'add',
          //   hidden: true,
          //   component: () =>import('@/views/spare/sparelist/add'),
          //   name:'add',
          //   meta: {
          //     resources: 'add',
          //     title: "互推列表"
          //   },
          // },
          // {
          //   path: 'edit',
          //   hidden: true,
          //   component: () =>import('@/views/spare/sparelist/edit'),
          //   name:'edit',
          //   meta: {
          //     resources: 'edit',
          //     title: "编辑备品"
          //   },
          // }
        ]
      },


      {
        path: 'precord',
        component: () => import('@/views/spare/sparerecord/index'),
        name: 'precord',
        meta: {
          resources: 'precord'
        }
      },
      {
        path: 'crecord',
        component: () => import('@/views/spare/sparecrecord/index'),
        name: 'crecord',
        meta: {
          resources: 'crecord'
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
      title: '任务派发'
    },
    children: [
      {
        path: 'manager',
        component: Common,
        redirect: {name:'managerlist'},
        name: 'manager',
        meta: {
          resources: 'manager'
        },
        children: [
          {
            path: 'list',
            hidden: true,
            component: () =>import('@/views/maintain/manager/index'),
            name:'managerlist',
          },
          {
            path: 'add',
            hidden: true,
            component: () =>import('@/views/maintain/manager/add'),
            name:'add',
            meta: {
              title: '新增派单'
            },
          },
          {
            path: 'edit',
            hidden: true,
            component: () =>import('@/views/maintain/manager/edit'),
            name:'edit',
            meta: {
              title: '编辑派单'
            },
          },

        ]
      },
      {
        path: 'list',
        component: Common,
        redirect: {name:'acceptlist'},
        meta: {
          resources: 'list'
        },
        children: [
          {
            path: 'acceptlist',
            hidden: true,
            component: () =>import('@/views/maintain/list/index'),
            name:'acceptlist',
            meta: {
              title: '接单列表'
            },
          },
          {
            path: 'acceptadd',
            hidden: true,
            component: () =>import('@/views/maintain/list/add'),
            name:'acceptadd',
            meta: {
              title: '新增'
            },
          },
          {
            path: 'acceptedit',
            hidden: true,
            component: () =>import('@/views/maintain/list/edit'),
            name:'acceptedit',
            meta: {
              title: '编辑'
            },
          },

        ]
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
    path: '/enduser',
    component: Layout,
    name: 'enduser',
    meta: {
      resources: 'enduser',
      title: '前台用户'
    },
    children: [
      {
        path: 'userlist',
        component: Common,
        redirect: {name:'userlist'},
        name: 'list',
        meta: {
          resources: 'list'
        },
        children: [
          {
            path: 'list',
            hidden: true,
            component: () =>import('@/views/enduser/userlist/index'),
            name:'userlist',
          },
          {
            path: 'deposit',
            hidden: true,
            component: () =>import('@/views/enduser/userlist/deposit'),
            name:'deposit',
            meta: {
              resources: 'user',
              title: '用户充值'
            },
          },
          {
            path: 'sign',
            hidden: true,
            component: () =>import('@/views/enduser/userlist/sign'),
            name:'sign',
            meta: {
              resources: 'user',
              title: '签到记录'
            },
          },
          {
            path: 'account',
            hidden: true,
            component: () =>import('@/views/enduser/userlist/account'),
            name:'account',
            meta: {
              resources: 'user',
              title: '账号列表'
            },
          },
          {
            path: 'accfreeze',
            hidden: true,
            component: () =>import('@/views/enduser/userlist/accfreeze'),
            name:'accfreeze',
            meta: {
              resources: 'user',
              title: '账号封禁'
            },
          },

        ]
      },
      {
        path: 'rankinglist',
        component: () => import('@/views/enduser/rankinglist/index'),
        name: 'rankinglist',
        meta: {
          resources: 'rankinglist'
        }
      },
      {
        path: 'weiboacc',
        component: () => import('@/views/enduser/weibo/list'),
        name: 'weiboacc',
        meta: {
          resources: 'weiboacc'
        }
      },
      {
        path: 'toutiaoacc',
        component: () => import('@/views/enduser/toutiao/list'),
        name: 'toutiaoacc',
        meta: {
          resources: 'toutiaoacc'
        }
      },
      {
        path: 'weichatacc',
        component: () => import('@/views/enduser/weichat/list'),
        name: 'weichatacc',
        meta: {
          resources: 'weichatacc'
        }
      },
      {
        path: 'subscription',
        component: () => import('@/views/enduser/subscription/list'),
        name: 'subscription',
        meta: {
          resources: 'subscription'
        }
      },
    ]
  },

  {
    path: '/cms',
    component: Layout,
    name: 'cms',
    meta: {
      resources: 'cms',
      title: '资讯'
    },
    children: [
      {
        path: 'news',
        component: Common,
        redirect: {name:'cmslist'},
        name: 'news',
        meta: {
          resources: 'news'
        },
        children: [
          {
            path: 'list',
            hidden: true,
            component: () =>import('@/views/cms/news/index'),
            name:'cmslist',
          },
          {
            path: 'cmsedit',
            hidden: true,
            component: () =>import('@/views/cms/news/edit'),
            name:'cmsedit',
            meta: {
              resources: 'cmsedit',
              title: '编辑资讯'
            },
          },
          {
            path: 'createcms',
            hidden: true,
            component: () =>import('@/views/cms/news/create'),
            name:'createcms',
            meta: {
              resources: 'createcms',
              title: '新增资讯'
            },
          },

        ]
      },
      {
        path: 'ad',
        component: () => import('@/views/cms/ad/index'),
        name: 'ad',
        meta: {
          resources: 'ad'
        }
      },
    ]
  },

  {
    path: '/flow',
    component: Layout,
    name: 'flow',
    meta: {
      resources: 'flow',
      title: '平台流水'
    },
    children: [
      {
        path: 'cash',
        component: () => import('@/views/flow/cash/index'),
        name: 'cash',
        meta: {
          resources: 'cash'
        },
      },
      {
        path: 'points',
        component: () => import('@/views/flow/points/index'),
        name: 'points',
        meta: {
          resources: 'points'
        }
      }
    ]
  },

  {
    path: '/message',
    component: Layout,
    name: 'message',
    meta: {
      resources: 'message',
      title: '消息'
    },
    children: [
      {
        path: 'list',
        component: () => import('@/views/message/index'),
        name: 'list',
        meta: {
          resources: 'list',
          title: '系统消息'
        },
      },
      {
        path: 'feedback',
        component: () => import('@/views/message/feedback'),
        name: 'feedback',
        meta: {
          resources: 'feedback',
          title: '用户反馈'
        }
      },

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
    children: [{
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
            }]
  },

  {
    path: '/shop',
    component: Layout,
    name: 'shop',
    redirect:"/pre/perm/index",
    meta: {
      resources: 'shop',
      title: '商品管理'
    },
    children: [{
      path: '/shoplist',
      component: Common,
      redirect: {name:'shoplist'},
      name: 'shoplist',
      meta: {
        title: '商品列表',
        resources: 'list'
      },
      children: [
        {
          path: 'list',
          hidden: true,
          component: () => import('@/views/shop/list/index'),
          name: 'shoplist',
        },
        {
          path: 'add',
          hidden: true,
          component: () => import('@/views/shop/list/add'),
          name: 'addshop',
          meta: {
            resources: 'add'
          }
        },
        {
          path: 'edit',
          hidden: true,
          component: () => import('@/views/shop/list/edit'),
          name: 'editshop',
          meta: {
            resources: 'edit'
          }
        }

      ]
    },
      {
        path: '/recordlist',
        component: Common,
        redirect: {name:'recordlist'},
        name: 'recordlist',
        meta: {
          title: '兑换列表',
          resources: 'record'
        },
        children: [
          {
            path: 'list',
            hidden: true,
            component: () => import('@/views/shop/record/index'),
            name: 'recordlist',
          },
          {
            path: 'add',
            hidden: true,
            component: () => import('@/views/shop/record/add'),
            name: 'addrecord',
            meta: {
              resources: 'add'
            }
          },
          {
            path: 'edit',
            hidden: true,
            component: () => import('@/views/shop/record/edit'),
            name: 'editrecord',
            meta: {
              resources: 'edit'
            }
          }

        ]
      }
    ]
  },



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

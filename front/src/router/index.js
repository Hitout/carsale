import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/views/layout/Layout'

/** note: Submenu only appear when children.length>=1
 *  detail see  https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 **/

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    roles: ['admin','editor']     will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if true ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
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
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/authredirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/errorPage/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/errorPage/401'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: { title: '首页', icon: 'dashboard', noCache: true }
      }
    ]
  }
]

export default new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '/sale',
    component: Layout,
    alwaysShow: true,
    meta: {
      title: '销售管理',
      icon: 'clipboard',
      roles: ['admin', 'editor']
    },
    children: [
      {
        path: 'addOrder',
        component: () => import('@/views/saleOrder/addOrder'),
        name: 'addOrder',
        meta: { title: '新订单' }
      },
      {
        path: 'order',
        component: () => import('@/views/saleOrder/order'),
        name: 'order',
        meta: { title: '销售订单' }
      },
      {
        path: 'orderDetails',
        component: () => import('@/views/saleOrder/orderDetails'),
        name: 'orderDetails',
        meta: { title: '订单详情' }
      }
    ]
  },
  {
    path: '/customer',
    component: Layout,
    alwaysShow: true,
    meta: {
      title: '客户管理',
      icon: 'customer',
      roles: ['admin', 'editor']
    },
    children: [
      {
        path: 'addCustomer',
        component: () => import('@/views/customer/addCustomer'),
        name: 'addCustomer',
        meta: { title: '添加客户' }
      },
      {
        path: 'message',
        component: () => import('@/views/customer/message'),
        name: 'customerMessage',
        meta: { title: '客户信息' }
      }
    ]
  },
  {
    path: '/store',
    component: Layout,
    alwaysShow: true,
    meta: {
      title: '库存管理',
      // roles: ['admin', 'editor'],
      icon: 'store'
    },
    children: [
      {
        path: 'addStore',
        component: () => import('@/views/store/addStore'),
        name: 'addStore',
        meta: {
          title: '添加库存',
          roles: ['admin']
        }
      },
      {
        path: 'message',
        component: () => import('@/views/store/message'),
        name: 'storeMessage',
        meta: { title: '车辆库存' }
      }
    ]
  },
  {
    path: '/charts',
    component: Layout,
    name: 'Charts',
    meta: {
      title: '财务报表',
      icon: 'chart'
    },
    children: [
      {
        path: 'employee',
        component: () => import('@/views/charts/admin/employee'),
        name: 'employee',
        meta: {
          title: '员工报表',
          // noCache: true,
          roles: ['admin']
        }
      },
      {
        path: 'sales',
        component: () => import('@/views/charts/admin/sales'),
        name: 'sales',
        meta: {
          title: '销量报表',
          // noCache: true,
          roles: ['admin']
        }
      },
      {
        path: 'personal',
        component: () => import('@/views/charts/editor/employee'),
        name: 'personal',
        meta: {
          title: '个人月报表'
        }
      }
    ]
  },
  {
    path: '/employee',
    component: Layout,
    meta: {
      title: '员工管理',
      icon: 'employee',
      roles: ['admin']
    },
    children: [
      {
        path: 'addEmployee',
        component: () => import('@/views/employee/addEmployee'),
        name: 'addEmployee',
        meta: { title: '添加员工' }
      },
      {
        path: 'message',
        component: () => import('@/views/employee/message'),
        name: 'message',
        meta: { title: '员工信息' }
      }
    ]
  },
  {
    path: '/my',
    component: Layout,
    meta: { roles: ['editor'] },
    children: [
      {
        path: 'message',
        name: 'message',
        component: () => import('@/views/about/message'),
        meta: {
          title: '我的信息',
          icon: 'myEmp',
          roles: ['editor']
        }
      }
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

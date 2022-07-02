import Vue from 'vue'
import VueRouter from 'vue-router'
import {store} from "@/store";



Vue.use(VueRouter)

const routes = [

    {
        path: '/',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/index'),
        children: [
            // *****
            // Book
            {
                path: '',
                name: 'About',
                component: () => import(/* webpackChunkName: "about" */ '../views/book/main/About.vue'),
                props: true,
            },
            {
                path: 'search',
                name : 'search',
                component: () => import(/* webpackChunkName: "about" */ '../views/book/main/search.vue'),
                props: true,
            },
            {
                path: 'category/:category',
                name : 'category',
                component: () => import(/* webpackChunkName: "about" */ '../views/book/main/category.vue'),
                props: true,
            },

            {
                path: '/my',
                component: () =>  import('@/views/mypage/My'),
                meta: {
                    auth: true, //는 경로가 로그인 할 필요가 있음을 나타낸다
                },
                children:[
                    {
                        path: 'wish',
                        name: 'WishList',
                        component: () =>  import('@/views/mypage/WishList'),
                    },
                    {
                        path: 'comment',
                        name: 'MyComment',
                        component: () =>  import('@/views/mypage/MyComment')
                    },

                    {
                        path: 'cart',
                        name: 'Cart',
                        component: () =>  import('@/views/mypage/Cart'),
                    },

                    {
                        path: 'order',
                        name: 'MyOrderComponent',
                        component: () =>  import('@/views/mypage/Order'),
                    },
                    {
                        path: 'order/:orderId',
                        name: 'orderDetail',
                        component: () =>  import('@/views/mypage/OrderDetail'),
                    },
                    {
                        path: 'infoEdit',
                        name: 'InfoEdit',
                        component: () =>  import('@/views/mypage/InfoEdit')
                    },
                ]
            },
        ]
    },

    // *****
    // Book


    {
        path: '/mainBook',
        name: 'MainBook',
        component: () =>  import('@/views/book/MainBook')
    },

    {
        path: '/detailView',
        name: 'DetailView',
        component: () =>  import('@/views/book/DetailView')
    },

    // *****
    //Order
    {
        path: '/order',
        name: 'Order',
        component: () =>  import('@/views/order/Order'),
        meta: {
            auth: true, //는 경로가 로그인 할 필요가 있음을 나타낸다
        },
    },

    //mainMenu
    {
        path: '/SearchMenu',
        name: 'SearchMenu',
        component: () =>  import('@/views/SearchMenu')
    },
    {
        path: '/home',
        name: 'Home',
        component: () =>  import('@/views/Home')
    },
    {
        path: '/admin',
        name: 'Admin',
        component: () =>  import('@/views/admin/Admin'),
        children: [
            {
                path: 'order',
                component: () => import(/* webpackChunkName: "about" */ '../views/admin/management/AdminOrder'),
            },
            {
                path: 'order/:orderId',
                component: () =>  import('@/views/admin/management/AdminOrderDetail'),
            },
            {
                path: 'comment',
                component: () => import(/* webpackChunkName: "about" */ '../views/admin/management/AdminComment'),
            },

            {
                path: 'member',
                component: () => import(/* webpackChunkName: "about" */ '../views/admin/management/AdminMember'),
            },

            {
                path: 'postBook',
                component: () =>  import('@/views/admin/editBook/PostBook')
            },
            {
                path: 'writeBook',
                component: () =>  import('@/views/admin/editBook/writebook')
            },
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () =>  import('@/views/Login')
    },

]

const router = new VueRouter({
    routes
});

export default router

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.auth)) {
        if (store.state.member.loginData.loginState) {
            next();
        } else {
            alert("로그인이 필요한 페이지입니다.")
            router.push({path:'/login'})
        }
    }else{
        next();
    }
})

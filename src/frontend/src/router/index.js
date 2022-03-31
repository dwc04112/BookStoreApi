import Vue from 'vue'
import VueRouter from 'vue-router'



Vue.use(VueRouter)

const routes = [
    {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
        props: true,
    },
    {
        path: '/',
        name: 'Home',
        component: () =>  import('@/views/Home')
    },
    {
        path: '/bestseller',
        name: 'BestSeller',
        component: () =>  import('@/views/BestSeller')
    },
    {
        path: '/Menu',
        name: 'Menu',
        component: () =>  import('@/views/Menu')
    },
    {
        path: '/post',
        name: 'PostBook',
        component: () =>  import('@/views/PostBook')
    },
    {
        path: '/login',
        name: 'Login',
        component: () =>  import('@/views/Login')
    },
    {
        path: '/exam',
        name: 'exam',
        component: () =>  import('@/views/exam')
    },
    {
        path: '/mainBook',
        name: 'MainBook',
        component: () =>  import('@/views/MainBook')
    },

    // myPage
    {
        path: '/infoMain',
        name: 'InfoMain',
        component: () =>  import('@/views/mypage/InfoMain')
    },
    {
        path: '/infoNavi',
        name: 'InfoNavi',
        component: () =>  import('@/views/mypage/InfoNavi'),
        props: true
    },
    {
        path: '/infoWishList',
        name: 'InfoWishList',
        component: () =>  import('@/views/mypage/InfoWishList')
    },

    //wishlist item
    {
        path: '/wishList',
        name: 'WishList',
        component: () =>  import('@/views/wishlist/WishList')
    },
    {
        path: '/addWishList',
        name: 'AddWishList',
        component: () =>  import('@/views/wishlist/AddWishList')
    },
]

const router = new VueRouter({
    routes
})

export default router

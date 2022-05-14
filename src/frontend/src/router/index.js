import Vue from 'vue'
import VueRouter from 'vue-router'



Vue.use(VueRouter)

const routes = [
    // Book

    {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/book/About.vue'),
        props: true,
    },
    {
        path: '/mainBook',
        name: 'MainBook',
        component: () =>  import('@/views/book/MainBook')
    },
    {
        path: '/post',
        name: 'PostBook',
        component: () =>  import('@/views/book/PostBook')
    },
    {
        path: '/detailView',
        name: 'DetailView',
        component: () =>  import('@/views/book/DetailView')
    },
    //Book Component ( detail )
    {
        path: '/bookDetailComponent',
        name: 'BookDetailComponent',
        component: () =>  import('@/views/book/bookComponents/BookDetailComponent'),
        props: true,
    },
    {
        path: '/commentComponent',
        name: 'CommentComponent',
        component: () =>  import('@/views/book/bookComponents/CommentComponent'),

    },




    //Menu
    {
        path: '/SearchMenu',
        name: 'SearchMenu',
        component: () =>  import('@/views/SearchMenu')
    },


    {
        path: '/',
        name: 'Home',
        component: () =>  import('@/views/Home')
    },
    {
        path: '/Menu',
        name: 'Menu',
        component: () =>  import('@/views/Menu')
    },
    {
        path: '/login',
        name: 'Login',
        component: () =>  import('@/views/Login')
    },


    // myPage
    {
        path: '/infoNavi',
        name: 'InfoNavi',
        component: () =>  import('@/views/mypage/InfoNavi'),
        props: true
    },

    {
        path: '/infoMain',
        name: 'InfoMain',
        component: () =>  import('@/views/mypage/InfoMain')
    },
    {
        path: '/infoWishList',
        name: 'InfoWishList',
        component: () =>  import('@/views/mypage/InfoWishList')
    },
    {
        path: '/infoEdit',
        name: 'InfoEdit',
        component: () =>  import('@/views/mypage/InfoEdit')
    },



    //Info Component ( MyPage , Sign )
    {
        path: '/infoEditComponent',
        name: 'InfoEditComponent',
        component: () =>  import('@/views/infoComponents/InfoEditComponent')
    },
    {
        path: '/passwordEditComponent',
        name: 'PasswordEditComponent',
        component: () =>  import('@/views/infoComponents/PasswordEditComponent')
    },



    //wishlist component
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


    //ex
    {
        path: '/exam',
        name: 'exam',
        component: () =>  import('@/views/exam')
    },
    {
        path: '/exam2',
        name: 'exam2',
        component: () =>  import('@/views/exam2')
    },
]

const router = new VueRouter({
    routes
})

export default router

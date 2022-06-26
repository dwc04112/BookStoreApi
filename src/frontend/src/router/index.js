import Vue from 'vue'
import VueRouter from 'vue-router'



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
                component: () => import(/* webpackChunkName: "about" */ '../views/book/About.vue'),
                props: true,
            },
            {
                path: 'search',
                name : 'search',
                component: () => import(/* webpackChunkName: "about" */ '../views/book/search.vue'),
                props: true,
            },
            {
                path: 'category/:category',
                name : 'category',
                component: () => import(/* webpackChunkName: "about" */ '../views/book/category.vue'),
                props: true,
            },


            {
                path: '/my',
                component: () =>  import('@/views/mypage/My'),
                children:[
                    {
                        path: 'wish',
                        name: 'InfoWishList',
                        component: () =>  import('@/views/mypage/InfoWishList')
                    },
                    {
                        path: 'comment',
                        name: 'MyCommentComponent',
                        component: () =>  import('@/views/mypage/infoComponents/MyCommentComponent')
                    },
                    {
                        path: 'cart',
                        name: 'InfoCart',
                        component: () =>  import('@/views/mypage/InfoCart'),
                    },

                    {
                        path: 'order',
                        name: 'MyOrderComponent',
                        component: () =>  import('@/views/mypage/infoComponents/MyOrderComponent'),
                    },
                    {
                        path: 'order/:orderId',
                        name: 'orderDetail',
                        component: () =>  import('@/views/mypage/infoComponents/OrderDetail'),
                    },
                    {
                        path: 'infoEdit',
                        name: 'InfoEdit',
                        component: () =>  import('@/views/mypage/InfoEdit')
                    },
                    {
                        path: 'infoProfile',
                        name: 'InfoProfile',
                        component: () =>  import('@/views/mypage/infoComponents/InfoProfile')
                    },

                ]
            },
        ]
    },

    // *****
    // Book
    /*
    {
        path: '/about',
        name: 'About',
        component: () => import( '../views/book/About.vue'),
        props: true,
    },

     */

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

    // *****
    //Order
    {
        path: '/order',
        name: 'Order',
        component: () =>  import('@/views/order/Order'),
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
        path: '/Menu',
        name: 'Menu',
        component: () =>  import('@/views/Menu')
    },
    {
        path: '/login',
        name: 'Login',
        component: () =>  import('@/views/Login')
    },




    // *****
    // myPage
    /*
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
    {
        path: '/infoActivity',
        name: 'InfoActivity',
        component: () =>  import('@/views/mypage/InfoActivity')
    },
    {
        path: '/infoCart',
        name: 'InfoCart',
        component: () =>  import('@/views/mypage/InfoCart'),
    },



    //Info Component (MY Page)
    {
        path: '/infoEditComponent',
        name: 'InfoEditComponent',
        component: () =>  import('@/views/mypage/infoComponents/InfoEditComponent')
    },
    {
        path: '/passwordEditComponent',
        name: 'PasswordEditComponent',
        component: () =>  import('@/views/mypage/infoComponents/PasswordEditComponent')
    },
    {
        path: '/myCommentComponent',
        name: 'MyCommentComponent',
        component: () =>  import('@/views/mypage/infoComponents/MyCommentComponent')
    },
    {
        path: 'myOrder',
        name: 'MyOrderComponent',
        component: () =>  import('@/views/mypage/infoComponents/MyOrderComponent'),
    },



    {
        path: '/orderDetail/:orderId',
        name: 'orderDetail',
        component: () =>  import('@/views/mypage/infoComponents/OrderDetail'),
    },
 */


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


    // *****
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
});

export default router

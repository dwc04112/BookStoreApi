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
        path: '/MainBook',
        name: 'MainBook',
        component: () =>  import('@/views/MainBook')
    },
]

const router = new VueRouter({
    routes
})

export default router



const toOrderStore = {

    state: {
        bookList : [],
    },

    mutations: {
        clearState: function (state){
            state.bookList = null;
        },
        setOrder: function (state, data) {
            state.bookList = data
        },

    },

    actions: {

        // cart에서 주문목록 받아오기
        getOrderByCart({commit}, payload){
            return new Promise((resolve) => {
                setTimeout(() => {
                    commit('clearState');
                    resolve()
                }, 500)
            }).then(()=>  commit('setOrder', payload))
        },

        // detail에서 주문목록 받아오기
        getOrderByDetail({commit}, payload){
            return new Promise((resolve) => {
                setTimeout(() => {
                    commit('clearState');
                    resolve()
                }, 500)
            }).then(()=>  commit('setOrder', payload))
        },

        clearOrderState({commit}){
            return new Promise((resolve) => {
                setTimeout(() => {
                    commit('clearState');
                    resolve()
                }, 500)
            })
        }
    },
}
export default toOrderStore;
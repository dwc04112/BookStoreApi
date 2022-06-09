

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
            commit('clearState')
            commit('setOrder', payload)
        },

        // detail에서 주문목록 받아오기
        getOrderByDetail({commit}, payload){
            commit('clearState')
            commit('setOrder', payload)
        },

    },
}
export default toOrderStore;
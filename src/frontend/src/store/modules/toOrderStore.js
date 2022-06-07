

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
            console.log("after data? : ", state.bookList)
        },

    },

    actions: {

        // cart에서 주문목록 받아오기
        getOrderByCart({commit}, payload){
            commit('clearState')
            commit('setOrder', payload)
        },

    },
}
export default toOrderStore;
<template>
  <v-app-bar
      fixed
      color="rgb(40,40,40)"
      dark
      elevation="0"
      height="80px"
  >
    <v-row>
      <v-col cols="9" offset-md="4" offset="1" sm="7" md="4" class="pt-10" v-click-outside="onClickOutside">
        <v-text-field
            class="main-search"
            v-model="inputMsg"
            filled
            outlined
            rounded
            dense
            @focus="autoSearchList = true"
            @keyup="autoSearchList = true"
        >
        </v-text-field>
        <transition name="top-slide" mode="in-out">
          <div class="search-list-div" >
            <v-list class="pa-0 ma-0 search-list" v-show="autoSearchList" light>
              <v-list-item-group>
                <v-hover v-slot="{ hover }"
                         v-for="(item,index) in completeData"
                         :key="index">
                  <v-list-item
                      class="pa-3 pl-5 top-list"
                      :class="{ 'on-hover': hover }"
                      @click="inputMsg=item.bookTitle">
                    <v-card
                        class="search-list-img"
                        elevation="1"
                        tile>
                      <img
                          :src="item.bookThumb"
                          alt="bookThumb"
                          height="100%"
                          @click="detailView(item.bid)">
                    </v-card>

                    <v-list-item-content class="pl-8">
                      <v-list-item-title>
                        <span class="search-list-title" @click="detailView(item.bid)"> {{item.bookTitle}} </span>
                      </v-list-item-title>

                      <v-list-item-subtitle class="pt-2">
                        <span class="search-list-subtitle"> {{ item.bookAuthor }} | {{item.bookPublisher}}</span>
                      </v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                </v-hover>
              </v-list-item-group>
            </v-list>
          </div>
        </transition>
      </v-col>


      <v-col cols="1" sm="3" md="2" class="pt-9">
        <v-btn class="top-menu-btn" @click="drawMenu" icon>
          <font-awesome-icon
              style="font-size: 24px" icon="fa-solid fa-2x fa-bars"/>
        </v-btn>

        <v-btn class="ml-2 search-icon" @click="mainSearch" icon>
          <v-icon>mdi-magnify</v-icon>
        </v-btn>

        <v-btn class="top-icon" icon @click="pushInfoWishList(3)">
          <v-icon>mdi-heart</v-icon>
        </v-btn>

        <v-btn icon class="top-icon">
          <v-icon>mdi-account</v-icon>
        </v-btn>
      </v-col>
    </v-row>
  </v-app-bar>
</template>

<script>
export default {
  name: "SearchMenu",
  data: () => ({
    completeData : [],
    inputMsg : '',
    autoSearchList : false,
  }),
  watch: {
    inputMsg(val) {
      if (!val) {
        this.completeData=[]
      }
      this.fetchEntriesDebounced()
    },
  },
  methods:{
    mainSearch(){
      let str = this.inputMsg
      str = str.trim()                                             //양끝 공백 제거
      str = str.replace(/\s/g,'+')            //스페이스바 +로 치환
      const reg = /[^가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9|+]/.test(str);         //특문검사 정규식
      if(!reg && str !== "") {
        this.$axios.get("book/search/" + str)
            .then(response => {
              console.log(response.data)
              this.$emit('searchData', response.data )

            }).catch(error => {
          console.log(error.response);
        })
      }else{
        alert("검색어를 입력해주세요")
        this.inputMsg="";
      }
    },
    /*
    * 자동완성
    * */
    //DB에 불필요한 데이터 입력 방지위해 입력 기다리기
    fetchEntriesDebounced() {
      this.completeData = null;
      // cancel pending call
      clearTimeout(this._timerId)
      // delay new call 500ms
      this._timerId = setTimeout(() => {
        // maybe : this.fetch_data()
        this.completeSearch()
      }, 500)
    },
    //자동완성 기능
    completeSearch(){
      let str = this.inputMsg
      str = str.trim()                                             //양끝 공백 제거
      str = str.replace(/\s/g,'+')            //스페이스바 +로 치환
      const reg = /[^가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9|+]/.test(str);         //특문검사 정규식
      if(!reg && str !== ""){
        this.$axios.get("book/complete/" + str)
            .then(response => {
              this.completeData = response.data
            }).catch(error => {
          console.log(error.response);
        })
      }
    },
    //자동검색 리스트에서 바깥부분 클릭시 리스트 닫음
    onClickOutside () {
      this.autoSearchList = false
    },

    drawMenu(){
      this.$emit('drawMenu', true )
    },

    //마이페이지 위시리스트 수정으로 넘어가기
    pushInfoWishList(pushNum){
      // num 0 ~ 3
      console.log("push num : "+ pushNum)
      //현재 페이지
      let nowPage = this.$router.history.current.name
      //만약 현재 위시리스트 페이지라면?
      if(nowPage === 'InfoNavi') {
        this.$emit('moveTabNum',pushNum)
      }else{
        this.$router.push({name: 'InfoNavi', params: {AboutTab: pushNum}})
      }
    },


    //책 보러가기
    detailView(bid){
      this.$router.push({name: 'DetailView' ,query: {bid}});
    },
  },
}
</script>

<style scoped>
.top-chip span{
  font-size: 13px;
  font-weight: lighter;
}

.search-list-div{
  width: 526px;
  position : absolute;
  transform: translateY(-23px) translateX(20px);
}
.search-list{
  width: 95%;
  outline: rgb(40,40,40) solid 2px;
}
.search-list-title{
  color: rgb(40,40,40);
  font-weight: bold;
}
.search-list-title:hover{
  text-decoration: underline;
}
.search-list-subtitle{
  color: rgb(60,60,60);
}
.search-list-img{
  height: 90px;
  overflow: hidden;
}
.top-menu-btn{
  display: none;
  position: absolute;
  left: 13px;
  color: rgb(240,240,240);
}
@media screen and (max-width: 768px){
  /* 최상단 검색 */
  .search-list{
    transform: translateX(12px);
    width: 58%;
  }
  .top-icon{
    display: none;
  }
  .main-search{
    left: 18px;
  }
  .top-menu-btn{
    display: block;
    left: 13px;
  }

  .search-list-title{
    font-size: 14px;
  }
  .search-list-subtitle{
    font-size: 12px;
  }
  .search-list-img{
    height: 60px;
  }
}
</style>
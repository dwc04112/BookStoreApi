<template>
    <v-app-bar
        fixed
        dark
        elevation="0"
        color="rgb(40,40,40)"
        dense
        class="justify-center align-center d-flex flex-column"
    >
        <v-btn class="top-icon" @click="homeLink" icon>
          <v-icon>mdi-home</v-icon>
        </v-btn>


        <v-btn class="top-menu-btn" @click="drawMenu" icon>
          <font-awesome-icon style="font-size: 24px" icon="fa-solid fa-2x fa-bars"/>
        </v-btn>

        <v-card
            elevation="0"
            v-click-outside="onClickOutside"
            min-width="30vw"
            max-width="70vw"
        >
          <v-text-field
              class="main-input"
              hide-details
              flat
              v-model="inputMsg"
              @focus="autoSearchList = true"
              @keyup="autoSearchList = true"
              background-color="rgb(20,20,20)"
          ></v-text-field>
          <v-expand-transition>
            <div class="search-list-div" v-show="autoSearchList">
              <v-list class="pa-0 ma-0 search-list">
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
          </v-expand-transition>
        </v-card>
      <v-spacer></v-spacer>


      <v-btn class="ml-2 search-icon" @click="mainSearch" icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>

      <v-btn class="top-icon" icon @click="myLink('/wish')">
        <v-icon>mdi-heart</v-icon>
      </v-btn>
      <v-btn fab text disabled>
        <v-avatar size="31" color="blue">
          <img
              :src="imgUrl"
              style="object-fit: cover"
              alt=""
          >
        </v-avatar>
      </v-btn>

    </v-app-bar>
</template>

<script>
export default {
  name: "SearchMenu",
  data () {
    return {
      completeData : [],
      inputMsg : '',
      autoSearchList : false,
      imgUrl : this.$store.state.memberStore.userData.profilePicture
   }
  },
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
      const reg = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|+:(),]+$/.test(str);         //특문검사 정규식
      if(reg) {
        // 페이지 이동?
        this.$router.push({name: 'search' , query:{search: str}}).catch(()=>{});
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
      const reg = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|+]+$/.test(str);           //특문검사 정규식
      if(reg) {
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
     // this.$router.push('drawMenu', true ).catch(()=>{});
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
    homeLink() {
      this.inputMsg = ''
      if(this.$router.history.current.name !== 'About') {
        this.$router.push({path: "/"})
      }
    },
    myLink(data){
      this.$router.push({path: "../my"+data})
    },

    //책 보러가기
    detailView(bid){
      this.$router.push({name: 'DetailView' ,query: {bid}});
    },
  },
  mounted() {
  }
}
</script>

<style scoped>
.top-chip span{
  font-size: 13px;
  font-weight: lighter;
}

.search-list-div{
  position : absolute;
  width: 100%;
}
.main-input >>> .v-input__slot::before {
  border-style: none !important;
}

.search-list-title{
  color: rgb(180,180,180);
  font-weight: bold;
}
.search-list-title:hover{
  text-decoration: underline;
}
.search-list-subtitle{
  color: rgb(160,160,160);
}

.search-list-img{
  height: 90px;
  overflow: hidden;
}
.top-menu-btn{
  display: none;
  color: rgb(240,240,240);
}
@media screen and (max-width: 768px){
  /* 최상단 검색 */
  .top-icon{
    display: none;
  }
  .top-menu-btn{
    display: block;
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
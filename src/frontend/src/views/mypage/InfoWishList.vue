<template>
  <v-container fluid class="pa-0 ma-0 align-center" style="background-color: rgb(24,24,24)">

    <v-row class=" pa-0 ma-0 justify-center align-center">
      <v-col cols="11" md="6" class="no-gutters pa-0 ma-0 mt-10" >
        <span class="main-title">나의 찜목록</span>
      </v-col>
    </v-row>

    <v-row class=" pa-0 ma-0 justify-center align-center">
      <v-col cols="11" md="6" class="pa-0 ma-0 mt-6">
        <v-chip-group
            class="ma-0 pa-0 pb-1"
            active-class="primary"
            v-model="selectKeyword"
            dark
        >
          <v-chip
              filter
              v-for="(wish,index) in wishlistTitle"
              :key="index"
              :value="wish.titleNum"
              @click.stop="getWishList(wish.titleNum)"
          >
            {{wish.wishlistTitle}}
          </v-chip>
          <v-chip
              @click.stop="editWishDialog=true"
              class="red lighten-1 white--text"
          >
            <span class="pl-1 pr-1">Edit</span>
            <v-icon class="white--text" size="19">mdi-wrench</v-icon>
          </v-chip>
        </v-chip-group>
      </v-col>
    </v-row>


    <!-- 중간부분 책 리스트 -->
    <v-row class=" pa-0 ma-0 justify-center align-center">
      <v-col cols="12" md="6" class="pa-0 ma-0 pl-1 pr-1 mt-6">

        <v-row style="background-color: rgb(40,40,40)">
          <v-col cols="4" class="white--text mt-2 pl-8">
            <div v-show="showSelect" >
              <v-icon color="white" size="20">mdi-check</v-icon>
              <span class="pl-1"> {{this.selectWidList.length}}권 선택</span>
            </div>
          </v-col>

          <v-col cols="8" class="justify-end d-flex pb-0 mb-0">
            <div class="pb-2 pr-6">
              <v-btn icon>
                <v-icon @click="showSelectSet"  :color=" showSelect ? 'blue' : 'white'"
                >{{ showSelect ? 'mdi-check-circle' : 'mdi-check-circle-outline' }}</v-icon>
              </v-btn>
            </div>
          </v-col>

          <v-col cols="12" class="pa-0 ma-0">
            <v-divider class="ma-1 ml-8 mr-8" style="background-color:rgb(200,200,200)"/>
          </v-col>
          <!--List Card-->
          <v-col cols="12"
                 class="book-list-col no-gutters offset-md-1 pl-4"
                 style="min-height: 57vh"
          >
            <div
                style="display: inline-block;"
                class="pa-4"
                v-for="(book, index) in wishBooks"
                :key="index"
            >
                <v-card
                    style="height: 200px; overflow: hidden; position:relative"
                    elevation="1"
                    tile
                >
                  <img
                      :src="book.bookThumb"
                      alt="bookThumb"
                      class="book-thumb"
                      height="100%"
                  >
                  <v-overlay
                      :absolute="absolute"
                      :value="showSelect"
                      opacity= 0.5
                      style="z-index: 4"
                  >
                    <v-btn
                        class="img-btn"
                        @click="selectBook(book.wid, index)"
                        fab outlined icon
                    >
                      <v-icon size="70" :color=" book.select ? 'blue' : 'white' ">
                        {{ book.select ? 'mdi-check-circle' : 'mdi-check-circle-outline' }}
                      </v-icon>
                    </v-btn>
                  </v-overlay>
                </v-card>
            </div>
          </v-col>
        </v-row>

      </v-col>
    </v-row>




    <v-row class=" pa-0 ma-0 justify-center align-center">
      <v-col cols="11" md="6" class="pa-0 ma-0 mt-12">
        <span class="main-title">관심 키워드</span>
        <v-chip-group
            class="mt-6 pb-4"
            v-model="selectKeyword"
            active-class="deep-purple accent-4 white--text"
            column
        >
          <v-chip>5:30PM</v-chip>

          <v-chip>7:30PM</v-chip>

          <v-chip>8:00PM</v-chip>

          <v-chip>9:00PM</v-chip>
        </v-chip-group>

        <v-btn
            color="deep-purple lighten-2"
            @click="infoEdit"
        >
          회원정보 수정
        </v-btn>
        <div style="height: 1800px"></div>
      </v-col>

















        <!-- 위시리스트 수정 -->
        <v-container fluid>
          <v-dialog
              class="align-center justify-center align-content-center"
              v-model="editWishDialog"
              max-width="600"
              overlay-color="transparent"
          >
            <v-card>
              <v-toolbar
                  elevation="0"
                  class="white--text mb-4"
                  color="rgb(40,40,40)"
              >
                <v-card-title> 위시리스트 관리</v-card-title>
              </v-toolbar>
              <v-card-text>
                <div>나만의 위시리스트를 관리합니다.</div>
                <v-simple-table
                    class="pa-5"
                    style="background-color: transparent"
                    dense>
                  <template v-slot:default>
                    <thead>
                    <tr>
                      <th class="text-left">
                        카테고리 이름
                      </th>
                      <th class="text-left">
                        등록된 책 수
                      </th>
                      <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr
                        v-for="item in wishlistTitle"
                        :key="item.titleNum"
                    >
                      <td>{{ item.wishlistTitle }}</td>
                      <td>{{ item.countTitle }}</td>
                      <td>
                        <v-icon
                            small
                            @click="setDeleteDialog(item.titleNum,item.wishlistTitle, 1)"
                        >
                          mdi-delete
                        </v-icon>
                      </td>
                    </tr>
                    </tbody>
                  </template>
                </v-simple-table>

                <div>
                  <ul>
                    <li>카테고리 추가는 메인페이지 > 책 등록시 가능합니다</li>
                    <li>새로 만들어진 카테고리는 자동으로 공개처리됩니다.</li>
                    <li>카테고리 관리는 마이페이지 > 위시리스트 > 나의 찜목록 으로 이동하시면 가능합니다.</li>
                  </ul>
                </div>
              </v-card-text>

              <v-divider/>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    color="rgb(40,40,40)"
                    text
                    @click="editWishDialog = false"
                >
                  close
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-container>


        <!--정말 삭제하시겠습니까? -->
        <v-container fluid>
          <v-dialog
              class="align-center justify-center align-content-center"
              v-model="deleteDialog"
              max-width="400"
          >
            <v-card color="rgb(40,40,40)">
              <v-card-text>
                <div
                    style="color: rgb(220,220,220); font-size: 15px"
                    class="pt-6 d-flex flex-column align-center"
                >{{ deleteTitleMsg }}
                </div>
              </v-card-text>

              <v-divider class="white"/>

              <v-card-actions class="align-center justify-center">
                <v-btn
                    color=white
                    text
                    style="font-size: 16px"
                    @click="deleteInList()"
                >
                  삭제
                </v-btn>
                <v-btn
                    color=white
                    text
                    style="font-size: 16px"
                    @click="deleteDialog = false"
                >
                  취소
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-container>

        <!-- 삭제 결과 메시지-->
        <v-container fluid>
          <v-dialog
              class="align-center justify-center align-content-center"
              v-model="msgDialog"
              max-width="400"
          >
            <v-card color="rgb(33,33,33)">
              <v-card-text>
                <div
                    style="color: rgb(220,220,220); font-size: 15px"
                    class="pt-6"
                >
                  {{resultMsg}}
                </div>
              </v-card-text>
              <v-divider class="white"/>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    color=rgb(220,220,220)
                    text
                    style="font-size: 16px"
                    @click.stop="closeMsg"
                >
                  확인
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-container>
    </v-row>

    // on off Footer
    <v-footer color="rgb(40,40,40)" fixed style="z-index: 5" v-show="showSelect">
      <v-row class="pa-0 ma-0 mt-2 mb-2 no-gutters justify-center d-flex" >
        <v-col cols="12" md="9" class="justify-center align-center d-flex">
          <v-chip
              @click="addToCart"
              color="blue lighten-1 white--text"
              v-show="showSelect"
              :disabled="selectWidList.length<1"
          >
            <span class="pl-1 pr-2">Add to Cart</span>
            <v-icon color="white" size="20">mdi-cart</v-icon>
          </v-chip>
          <v-spacer></v-spacer>
          <v-chip
              @click="deleteInList"
              color="red lighten-1 white--text"
              text v-show="showSelect"
              :disabled="selectWidList.length<1"
          >
            <span class="pl-1 pr-2">Delete in List</span>
            <v-icon color="white" size="20">mdi-delete</v-icon>
          </v-chip>
        </v-col>
      </v-row>
    </v-footer>

  </v-container>
</template>

<script>
export default {
  name: "InfoWishList",
  data: () => ({

    wishlistTitle :[],
    wishBooks: [],
    selectKeyword: 1, //선택된 키워드

    //책 선택
    showSelect : false, //책 선택 보여줄건지?
    absolute : true,
    selectWidList : [],


    editWishDialog : false,
    editWishData : '',

    //삭제창 관련
    deleteType : null,
    deleteTitleMsg : '',
    deleteNum : null,
    deleteDialog : false,

    //삭제 결과 관련
    resultMsg : '',
    msgDialog : false,


    page : 1,
    pageCount: 0,

    headers: [
      {
        align: 'center',
        sortable: false,
        value: 'bookThumb',

        class:  "grey lighten-3"
      },
      { text: '책 이름', value: 'bookTitle', align: 'start',class: "grey lighten-3"},
      { text: '작가', value: 'bookAuthor', align: 'start',class:  "grey lighten-3"},
      { text: '판매 가격', value: 'bookSalePrice', align: 'start',class:  "grey lighten-3" },
      { sortable: false, value: 'wid' ,align: 'start',class:  "grey lighten-3" },
    ],

  }),
  methods: {
    getWishTitle(){
      this.$axios.get("wish/title/")
          .then(response=>{
            this.wishlistTitle = response.data
          }).catch(error =>{
        console.log(error.response);
      })
    },

    //선택된 위시리스트 목록 받아오기
    getWishList(titleNum){
      this.showSelect = false;  //책 선택영역 닫기
      this.selectWidList = []   // 책 선택 리스트 초기화
      if(titleNum == null){
        titleNum=1;
      }
      this.$axios.get("wish/title/"+titleNum)
          .then(response=>{
            this.wishBooks = response.data
            for(let i =0; i<response.data.length; i++){
              this.$set(this.wishBooks[i],'select',false)
            }
          }).catch(error =>{
        console.log(error.response);
      })
    },

    // 리스트에서 책 수정? true false
    showSelectSet(){
      this.selectWidList = []   // 책 선택 리스트 초기화
      this.wishBooks.map(e => {
        e.select = false;
      })
      this.showSelect = !this.showSelect
    },
    // 책 선택하기
    selectBook(wid, index){
      this.wishBooks[index].select = !this.wishBooks[index].select

      //해당 책 wish id 찾아서 추가 or 제거
      if(this.selectWidList.indexOf(wid) === -1) {
        this.selectWidList.push(wid)
      }else {
        this.selectWidList.splice(this.selectWidList.indexOf(wid), 1)
      }
    },

    // 선택한 책 삭제하기
    deleteInList(){
      alert(this.selectWidList + "번 책을 삭제합니다")
      let widArr = this.selectWidList
      this.$axios.delete("wish/"+widArr)
          .then(response=>{
            console.log(response.data)
            alert("선택한 책이 성공적으로 삭제되었습니다.")
            this.getWishList(this.selectKeyword)  //선택된 키워드 번호로 책목록 다시받기 (리로드)
          }).catch(error =>{
            console.log(error.response);
          })
    },

    addToCart(){
      alert(this.selectWidList + "번 책을 추가합니다")
    },




    //delete Dialog 열고 삭제할 값 세팅
    setDeleteDialog(numId, title, deleteType){
      if(deleteType === 1) {
        this.deleteTitleMsg = "\"" + title + "\" 위시리스트를 삭제하시겠습니까?"
      }else{
        this.deleteTitleMsg =  "\"" + title + "\" 책을 위시리스트에서 삭제하시겠습니까?"
      }
      this.deleteType = deleteType
      this.deleteNum = numId
      this.deleteDialog = true
    },
    // 책을 삭제? 카테고리를 삭제?
    titleOrBook(){
      if(this.deleteType ===1) {
        this.deleteWishList()
      }else {
        this.deleteItem()
      }
    },

    //삭제. 수행후 delete Dialog 닫기
    deleteWishList(){
      let titleNum = this.deleteNum
      this.$axios.delete("wish/title/"+titleNum)
          .then(response=>{
            console.log(response.data)
            this.resultMsg ="위시리스트가 성공적으로 삭제되었습니다."
            this.msgDialog = true
          }).catch(error =>{
            console.log(error.response);
            this.resultMsg = "위시리스트 삭제를 실패했습니다."
            this.msgDialog = true
          })
      this.deleteDialog = false
    },

    // 삭제 결과 메시지 닫기
    closeMsg(){
      this.msgDialog = false
      this.updateComponentKey()   // 컴포넌트 업데이트를 위해 리 렌더링
      this.resetDeleteData()
    },

    // 삭제 수행중 세팅된 데이터 초기화
    resetDeleteData(){
      this.deleteNum = null
      this.deleteTitleMsg = ''
      this.deleteType = null
    },



    infoEdit(){
      console.log("hi")
    },
    editItem(bid){
      alert("edit : "+bid)
    },


    //책 삭제
    deleteItem(){
      let bid = this.deleteNum
      this.$axios.delete("wish/"+bid)
          .then(response=>{
            console.log(response.data)
            this.resultMsg ="선택한 책이 성공적으로 삭제되었습니다."
            this.msgDialog = true
          }).catch(error =>{
            console.log(error.response);
            this.resultMsg = "선택한 책 삭제를 실패했습니다."
            this.msgDialog = true
      })
      this.deleteDialog = false
    },

    // 리 렌더링을 위해 부모 컴포넌트 Data 변경
    updateComponentKey(){
      this.$emit('updateChildKey')
    }
  },

  mounted() {
    this.getWishTitle()
    this.getWishList()
  }
}
</script>

<style scoped>
h4{
  color: white;
}

li{
  font-size: 12px;
}

.main-title{
  font-size: 24px;
  font-weight: bold;
  color: rgb(240,240,240);
}
@media screen and (max-width: 768px) {
  .book-list-col{
    text-align: center;
  }

}



</style>
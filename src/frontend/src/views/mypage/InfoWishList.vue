<template>
  <v-layout wrap row class="pa-4">
    <v-flex md12 sm12>
      <v-card-title>나의 찜목록</v-card-title>
      <v-card-text >
        <v-row>
          <v-col cols="12" md="10" sm="10">
            <v-chip-group
                active-class="deep-purple accent-4 white--text"
                mandatory
                column
            >
              <v-chip
                  v-for="(wish,index) in wishlistTitle"
                  :key="index"
                  @click.stop="getWishList(wish.titleNum)"
              >{{wish.wishlistTitle}}</v-chip>
            </v-chip-group>
          </v-col>

          <v-col cols="12" md="2" sm="2">
            <v-chip
                @click.stop="editWishDialog=true"
            >
              위시리스트 관리
              <v-icon class="pl-1" size="19">mdi-pencil</v-icon>
            </v-chip>
          </v-col>
        </v-row>
      </v-card-text>
      <v-divider/>

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
                class="deep-purple accent-4 white--text mb-4"
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
                  color="deep-purple accent-4"
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
          <v-card color="rgb(33,33,33)">
            <v-card-text>
              <div
                  style="color: rgb(220,220,220); font-size: 15px"
                  class="pt-6 d-flex flex-column align-center"
              >{{deleteTitleMsg}}
              </div>
            </v-card-text>

            <v-divider class="white"/>

            <v-card-actions class="align-center justify-center">
              <v-btn
                  color=white
                  text
                  style="font-size: 16px"
                  @click="titleOrBook()"
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



      <v-card-text>
        <v-layout wrap row justify-center>
          <v-flex
              class="pb-8 pl-2 pr-2"
              xs12 sm12 md12
              style="flex-basis: auto;"
          >
            <v-data-table
                dense
                :headers="headers"
                :items="wishBooks"
                :items-per-page="5"
                item-key="name"
                class="elevation-0"
            >
             <template v-slot:item.bookThumb="{item}" >
               <div class="pa-2">
                <v-img :src="item.bookThumb" width="60" height="87" ></v-img>
               </div>
             </template>
              <template v-slot:item.wid="{ item }">
                <v-btn
                    small
                    outlined
                    text
                    class="mt-2"
                    @click="editItem(item.wid)"
                    width="80"
                    height="22"
                    elevation="0"
                >
                  <h4>장바구니</h4>
                </v-btn>
                <v-btn
                    small
                    outlined
                    text
                    class="mt-2"
                    @click="editItem(item.wid)"
                    width="80"
                    height="22"
                    elevation="0"
                >
                  <h4>바로구매</h4>
                </v-btn>
                <v-btn
                    small
                    outlined
                    @click="setDeleteDialog(item.wid, item.bookTitle, 2)"
                    text
                    class="mt-2"
                    width="80"
                    height="22"
                    elevation="0"
                >
                  <h4>삭제</h4>
                </v-btn>
              </template>
            </v-data-table>
          </v-flex>
        </v-layout>
      </v-card-text>




      <v-divider></v-divider>

      <v-card-title>나의 관심 키워드</v-card-title>

      <v-card-text>
        <v-chip-group
            v-model="selection"
            active-class="deep-purple accent-4 white--text"
            column
        >
          <v-chip>5:30PM</v-chip>

          <v-chip>7:30PM</v-chip>

          <v-chip>8:00PM</v-chip>

          <v-chip>9:00PM</v-chip>
        </v-chip-group>
      </v-card-text>

        <v-card-actions>
          <v-btn
              color="deep-purple lighten-2"
              text
              @click="infoEdit"
          >
            회원정보 수정
          </v-btn>
        </v-card-actions>
    </v-flex>
  </v-layout>
</template>

<script>
export default {
  name: "InfoWishList",
  data: () => ({
    selection: 1,
    wishlistTitle :[],
    wishBooks: [],

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



    headers: [
      {
        align: 'center',
        sortable: false,
        value: 'bookThumb',
        width: 150,
      },
      { text: '책 이름', value: 'bookTitle', width : 550, align: 'center'},
      { text: '작가', value: 'bookAuthor',width : 200 , align: 'start'},
      { text: '판매 가격', value: 'bookSalePrice', width : 150 , align: 'start' },
      { test: '책 선택' ,value: 'wid' ,width : 100 , align: 'center'},
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

    getWishList(titleNum){
      if(titleNum == null){
        titleNum=1;
      }
      this.$axios.get("wish/title/"+titleNum)
          .then(response=>{
            this.wishBooks = response.data
          }).catch(error =>{
        console.log(error.response);
      })
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
  color: #606060;
}

li{
  font-size: 12px;
}
</style>
<template>
  <v-container fluid>
    <v-card-text>
      <div style="color: #505050" class="mb-4"> 해당 도서를 담을 카테고리를 선택해주세요.</div>
    <div class="d-flex flex-column align-center">
      <v-row
          align="center" justify="center"
          style="width: 85%; background-color: #F3E9DD; text-align: center;"
          class="pt-2"
          dense
      >
        <v-col
            cols="6" sm="4" md="4"
            class="pa-3 fill-height d-flex flex-column justify-center align-center"
            v-for="(wishCategory,index) in wishlistTitle"
            :key="index"
        >
          <v-card
              class="book mb-2"
              height="140"
              width="100"
              elevation="2"
              tile
              @click.stop="setWishData(index,wishCategory.wishlistTitle)"
          >
            <v-card-text>( {{wishCategory.countTitle}} /100)</v-card-text>
          </v-card>
          <a class="wish-a">{{wishCategory.wishlistTitle}}</a>
        </v-col>

        <v-col
            cols="6" sm="4" md="4"
            class="pa-3 fill-height d-flex flex-column justify-center align-center">
          <v-card
              class="mb-2 d-flex align-center justify-center"
              height="140"
              width="100"
              elevation="2"
              tile
              color="rgba(100,100,100,0.1)"
              @click="pushAddWishList"
          >
            <font-awesome-icon class="plus-icon" icon="fa-solid fa-plus"/>
          </v-card>
          <a class="wish-a">새 위시리스트 추가</a>
        </v-col>
      </v-row>


      <v-container fluid>
        <v-dialog
            class="align-center justify-center align-content-center"
            v-model="commitDialog"
            max-width="300"
        >
          <v-card color="rgb(33,33,33)">
            <v-card-title><div style="color: white">등록하시겠습니까?</div></v-card-title>
            <v-divider class="white"/>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                  color=white
                  text
                  style="font-size: 16px"
                  @click="pushWishData()"
              >
                등록
              </v-btn>
              <v-btn
                  color=white
                  text
                  style="font-size: 16px"
                  @click="commitDialog = false"
              >
                취소
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-container>

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
                  @click.stop="commitMsg"
              >
                확인
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-container>


    </div>
    </v-card-text>
  </v-container>
</template>

<script>
export default {
  name: "WishList",
  props : ["selectBid"],
  data: () => ({
    wishlistTitle : [],           // wishTitle & count
    selectWishlistTitle : '',
    postData : [],                //bid & wishlistTitle & index
    commitDialog : false,         // 등록하시겠습니까?

    msgDialog : false,            // 결과메시지
    resultMsg : '',
  }),


  methods: {
    getWishTitle(){
      // this.wishData.bid = bid
      this.$axios.get("wish/title/")
          .then(response=>{
            this.wishlistTitle = response.data
          }).catch(error =>{
        console.log(error.response);
      })
    },
    setWishData(titleNum, wishlistTitle){
      this.commitDialog = true
      this.postData.titleNum = titleNum;
      this.postData.wishlistTitle = wishlistTitle;
      this.postData.bid = this.selectBid
    },

    pushWishData(){
      let data = {}
      data.titleNum = this.postData.titleNum
      data.wishlistTitle = this.postData.wishlistTitle;
      data.bid = this.postData.bid;
      this.$axios.post("wish/",JSON.stringify(data),{
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response=>{
        if(response.data.success === true){
          this.resultMsg = "\"" +this.postData.wishlistTitle + "\" 에 성공적으로 등록했습니다!"
          this.msgDialog = true
        }else{
          this.resultMsg = "이미 해당 책이 등록되어 있습니다. 다른 보관함을 이용해주세요."
          this.msgDialog = true
        }
        this.commitDialog=false
      }).catch(error =>{
        console.log(error.response);
      })
    },

    commitMsg(){
      this.msgDialog = false
      this.$emit('childKey')
    },

    //위시리스트 추가화면으로 넘어가기 위해 부모 컴포넌트에게 데이터 전달
    pushAddWishList(){
      let childData = 'AddWishList'
      this.$emit('pushTab', childData)
    }

  },
  mounted() {
    this.getWishTitle();
  }
}
</script>

<style scoped>
.plus-icon{
  font-size: 40px;
  color: rgba(100,100,100,0.3);
}
.wish-a{
  font-size: 14px;
  color : rgba(40,40,40,0.5);
  font-weight: bold;
}
</style>
<template>
  <v-container fluid class="align-center" >

    <v-row>
      <v-col cols=12 md="3">
        <v-card color="transparent" class="justify-center d-flex align-center flex-column" tile elevation="0">
          <v-sheet class="align-center justify-center flex-column d-flex" color="transparent">
            <v-avatar
                color="grey"
                size="110">
              <img :src="this.$store.state.memberStore.userData.profilePicture"
                   style="object-fit: cover"
                   alt=""/>
            </v-avatar>
            <strong class="pt-5" style="color: rgb(190,190,190); font-size: 20px">{{ $store.state.memberStore.userData.nickName }}</strong>
          </v-sheet>
          <v-divider class="ma-2" style="width: 60%" dark></v-divider>

          <div class="d-flex align-center">
            <span class="nav-text pt-3 pa-1">나의 총 댓글 수</span>
            <strong
                class="pa-1"
                :class="totalComment.totalCount > 0 ? 'yellow--text text--darken-2' : 'grey--text text--darken-1'"
                style="font-size: 30px"
            >{{totalComment.totalCount}}</strong>
            <span class="nav-text pt-3 pa-1">개</span>
          </div>

          <v-divider class="ma-2 mt-3" style="width: 60%" dark></v-divider>

          <v-row class="pb-6 pt-4 pr-3">
            <span class="nav-text pt-5 pr-2">평균 별점</span>
            <span class="pt-1 yellow--text text--darken-2" style="font-weight: bold;font-size: 30px">{{totalComment.ratings}}</span>
            <span class="pt-6" style="color:rgb(160,160,160); font-size: 16px"> /4</span>
          </v-row>
            <div
                v-for="(data,index) in ratingList"
                :key="index"
                class="pb-2" style="width: 55%"
            >
              <h5 style="color:rgb(160,160,160);">{{data.rating}} star &nbsp;({{data.per}}%)</h5>
              <v-progress-linear
                  :value="data.value"
                  color="yellow darken-2"
                  background-color="rgb(40,40,40)"
                  height="22"
                  rounded
                  disabled="true"
                  style="border: rgb(40,40,40) 1px solid"
              ></v-progress-linear>
            </div>
          <v-divider class="ma-2 mt-4" style="width: 60%" dark></v-divider>
        </v-card>
      </v-col>


      <v-col cols="12" md="7" class="pb-8 ml-md-4">
        <div class="mb-4">
          <span class="main-title">나의후기 모아보기</span>
        </div>

        <!-- 정렬 선택 -->
        <v-chip-group dark mandatory class="mb-4">
          <v-chip
              filter :filter-icon="sortChip[index].icon"
              color="rgb(220,220,220)"
              style="background-color: rgb(40,40,40)"
              active-class="red darken-4 grey--text text--lighten-2"
              v-for="(data,index) in sortChip"
              :key="index"
              :value="index"
              @click="setSelectSort(index)"
          >
            <span class="top-chip-text">{{data.text}}</span>
          </v-chip>
        </v-chip-group>

        <v-row class="pb-4 align-center pa-0" style="background-color: rgb(30,30,30)">
          <v-col class="pa-0 align-center"  v-show="noComments">
            <v-row class="justify-center mt-6">
             <span style="color:rgb(80,80,80); font-size: 21px; font-weight: bold;">댓글이 없습니다. 첫번째 댓글을 작성해보세요</span>
            </v-row>
          </v-col>

          <v-col cols="12" class="pb-2"></v-col>
          <!-- 댓글 출력 -->
          <v-col
              cols="12" class="pa-0"
              v-for="(data , index) in commentData"
              :key="index"
          >
            <v-row align="center" class="ma-0 pa-0">
              <!--책 이미지 영역-->
              <v-col cols="12" md="3" class="pl-8 align-center d-flex flex-column">
                <v-img
                    :src="data.bookThumb"
                    :lazy-src="data.bookThumb"
                    alt="bookThumb"
                    max-height="150"
                    max-width="100px"
                ><template v-slot:placeholder>
                  <v-row
                      class="fill-height ma-0"
                      align="center"
                      justify="center"
                  >
                    <v-progress-circular
                        indeterminate
                        color="black lighten-5"
                    ></v-progress-circular>
                  </v-row>
                </template>
                </v-img>
              </v-col>

              <!--책 정보 & 댓글 영역-->
              <v-col cols="12" md="9" class="pa-5 mt-5 mb-5">
                <!--삭제 버튼 영역-->
                <v-row class="mr-3">
                  <v-spacer></v-spacer>
                  <v-btn class="mr-3" small outlined color="#BDBDBD" disabled rounded dark>
                    <v-icon size="20">mdi-heart</v-icon>
                    <span class="pl-1">{{data.popularity}}</span>
                  </v-btn>
                  <v-btn class="mr-5" small outlined color="rgb(105,105,105)" icon @click="setDialog(data.cid)">
                    <v-icon size="20">mdi-close</v-icon>
                  </v-btn>
                </v-row>


                <!--책 제목과 저자-->
                <v-row class="book-text ml-1 pa-0 ma-0">
                  <span>{{data.bookTitle}}</span>
                </v-row>
                <v-row class="book-subtext ml-1 pa-0 ma-0">
                  <span>{{data.bookAuthor}} | {{data.bookPublisher}}</span>
                </v-row>

                <!--댓글-->
                <v-row class="align-center ma-0 pa-0">
                  <v-rating
                      :value="data.ratings"
                      color="yellow darken-2"
                      background-color="grey darken-2"
                      dense
                      readonly
                      length="4"
                      size="18"
                      class="pr-1"
                  ></v-rating>
                  <span class="date-text">{{data.commentDate}} 일에 작성됨</span>
                </v-row>

                <v-row class="content-text ml-1 pa-0 ma-0 pt-3">
                  <span>{{data.content}}</span>
                </v-row>
                <!--댓글 끝-->
              </v-col>
            </v-row>
            <v-divider class="ma-4 ml-7 mr-7" style="background-color: rgb(70,70,70); border: rgb(70,70,70) solid 0.5px"></v-divider>
          </v-col>

          <v-col cols="12" class="mt-2">
            <div class="text-center">
              <v-pagination
                  dark
                  v-model="page"
                  :length="totalPages"
                  :total-visible="5"
                  circle
                  class="my-pagination"
                  color="yellow darken-2 black--text"
                  @input="getBookComment"
              ></v-pagination>
            </div>
          </v-col>

          <!--삭제 확인 메시지-->
          <v-dialog
              max-width="400"
              v-model="dialog"
          >
              <v-card rounded color="rgb(55,55,55)" tile dark>
                <div class="pa-4 pb-6 pt-6" style="font-weight: lighter; font-size: 15px">정말 이 댓글을 삭제할까요?</div>
                <v-card-actions class="justify-end" style="background-color: rgb(50,50,50)">
                  <v-btn
                      rounded
                      text
                      @click="setDialog(0)"
                  >Close</v-btn>
                  <v-btn
                      class="ml-2"
                      rounded
                      elevation="0"
                      color="red"
                      @click="deleteComment()"
                  >Commit</v-btn>
                </v-card-actions>
              </v-card>
          </v-dialog>

          <v-snackbar
              transition="dialog-bottom-transition"
              elevation="0"
              color="white"
              rounded
              v-model="snackbar"
          >
            <div>
              <v-icon color="blue" size="30" class="pr-2">mdi-checkbox-marked-circle</v-icon>
              <span class="black--text">삭제되었습니다.</span>
            </div>
          </v-snackbar>
        </v-row>
      </v-col>
    </v-row>
  </v-container>

</template>

<script>
export default {
  name: "MyCommentComponent",
  data: () => ({
        commentData : [],
        totalComment : [], // count & avg ratings
        noComments : false,

        //page
        page : 1,
        size : 5,
        totalPages : 0,

        setCid : 0,         //삭제할 댓글 id
        dialog : false,     //삭제 확인 메시지
        snackbar : false,   //삭제 성공? 실패?

        selectSort:0,
        sortChip :
            [
              {'text' : '최신순', 'icon' : 'mdi-clock' },
              {'text' : '인기순', 'icon' : 'mdi-thumb-up'},
              {'text' : '별점순', 'icon' : 'mdi-star'},
            ],

        //추천도
        interval: {},
        ratingList:[
          {rating:4 ,value: 0, per:0 , count: 0},
          {rating:3 ,value: 0, per:0 , count: 0},
          {rating:2 ,value: 0, per:0 , count: 0},
          {rating:1 ,value: 0, per:0 , count: 0},
        ],
      }
  ),

  watch: {
    snackbar(val) {
      val && setTimeout(() => {
        this.snackbar = false
      }, 4000)
    },
  },

  methods: {
    getTotalInfo(){
      this.$axios.get("comment/myTotal/")
          .then(response => {
            this.totalComment = response.data;
            for(let i = 0; i<4; i++) {
              //역순으로 리스트에 데이터 넣기
              //별이 4개면 -> 0번째 리스트에 , 3개면 -> 1번째
              if(response.data.ratingsCount[i]==null){
                continue;
              }
              this.ratingList[(this.ratingList.length - 1) - (response.data.ratingsCount[i].ratings - 1)].count = response.data.ratingsCount[i].count
              this.ratingList[(this.ratingList.length - 1) - (response.data.ratingsCount[i].ratings - 1)].per = Math.round(response.data.ratingsCount[i].count / response.data.totalCount * 100);
            }
            this.startBuffer()
          }).catch(error => {
            console.log(error.response);
      })
    },
    //그래프 동작
    startBuffer(){
      for(let i = 0 ; i<4; i++) {
        this.interval = setInterval(() => {
          if (this.ratingList[i].value === this.ratingList[i].per) {
            return (this.ratingList[i].per)
          }this.ratingList[i].value += 1
        }, 25)
      }
    },

    //정렬 설정 후 불러오기
    //설정 안하고 바로 불러오면 다시 불러올때 (페이지 넘기거나할때) 초기화돼서
    setSelectSort(index){
      this.selectSort = index
      this.page = 1   //페이지도 초기화
      this.getBookComment();
    },

    getBookComment() {
      let data = {}
      data.page = this.page -1
      data.size = this.size
      data.sortType = this.selectSort
      this.$axios.post("comment/myComment", JSON.stringify(data), {
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response => {
        this.commentData = response.data.content;
        this.totalPages = response.data.totalPages;

        this.noComments = response.data.content.length === 0;
      }).catch(error => {
        console.log(error.response);
      })
    },

    //댓글 삭제
    deleteComment(){
      let cid = this.setCid
      this.$axios.delete("comment/" + cid)
          .then(response=>{
            console.log(response.data.message)
            this.getBookComment();
            this.setDialog(0);
            this.snackbarDelay();
          }).catch(error =>{
        console.log(error.response);
      })
    },

    setDialog(cid){
      this.setCid = cid
      this.dialog = !this.dialog;
    },

    snackbarDelay(){
      clearTimeout(this._timerId)
      // delay new call 600ms
      this._timerId = setTimeout(() => {
        // maybe : this.fetch_data()
        this.snackbar = true;
      }, 600)
    }

  },

  mounted() {
    this.getTotalInfo();
    this.getBookComment();
    window.scrollTo(0, 0);
  }
}
</script>

<style scoped>
.nav-text{
  color: rgb(170,170,170);
  font-size: 15px;
  font-weight: bold;
}

.main-title{
  font-size: 24px;
  font-weight: bold;
  color: rgb(240,240,240);
}
.book-text{
  color: rgb(120,120,120);
  font-size: 15px;
}
.book-subtext{
  color: rgb(120,120,120);
  font-size: 13px;
}
.top-chip-text{
  font-weight: bold;
  font-size: 16px;
}

.date-text{
  color: rgb(120,120,120);
  font-size: 13px;
}
.content-text{
  color: rgb(160,160,160);
  font-size: 18px;
  font-weight: bold;
}
</style>
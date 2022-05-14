<template>
  <v-container class="ma-0 pa-0">
    <v-row class="yellow darken-2 pa-5">
      <v-col cols="3" md="3" offset-md="1" class="mt-5">
        <v-rating
            :value="totalRating"
            color="rgb(60,60,60)"
            background-color="grey darken-2"
            dense
            readonly
            half-increments
            length="4"
            size="32"
        ></v-rating>
        <span class="pl-2" style="color:rgb(60,60,60); font-weight: bold;font-size: 24px">{{totalRating}}</span>
        <span style="color:rgb(60,60,60); font-size: 16px"> / 4</span>
        <div class="pl-2" style="color:rgb(60,60,60); font-size: 16px" >{{totalCount}} ratings</div>
      </v-col>

      <v-col cols="1" offset="1" md="1" offset-md="0" >
        <v-divider vertical style="background-color:rgb(60,60,60); border: rgb(60,60,60) 1px solid;"></v-divider>
      </v-col>

      <v-col cols="7" md="6">
        <div
            v-for="(data,index) in ratingList"
            :key="index"
        >
          <v-row>
            <v-col cols="9" >
              <v-progress-linear
                  :value="data.value"
                  color="white"
                  height="27"
                  rounded
                  disabled="true"
                  style="border: rgb(60,60,60) 2px solid"
              ><h5 style="color:rgb(60,60,60);">{{data.rating}} star</h5>
              </v-progress-linear>
            </v-col>

            <v-col cols="3" >
              <h4 style="color:rgb(60,60,60);">{{data.per}} %</h4>
            </v-col>
          </v-row>
        </div>
      </v-col>
    </v-row>


    <v-row class="justify-center" style="background-color: rgb(40,40,40)">

      <v-col cols="12" md="11">
        <v-row class="mt-10 ma-0 pa-0 justify-center">

          <!-- 댓글작성 -->
          <v-col cols="12" md="11" class="mt-10 mb-16 pb-12">

            <div class=" align-center flex-column d-flex mb-4">
              <span class="pb-3" style="font-size: 18px; font-weight: bold; color: rgb(180,180,180);">이 책에 대한 리뷰를 남겨주세요!</span>
              <v-rating
                v-model="writeRating"
                color="yellow darken-2"
                background-color="grey darken-2"
                length="4"
                size="45"
              ></v-rating>
            </div>
            <v-textarea
                v-model="writeComment"
                outlined
                color="yellow darken-2"
                dark
                height="150"
                auto-grow
                dense
                placeholder="주제와 무관한 댓글, 욕설 및 비방은 제재의 대상이 될 수 있습니다."
            >
            </v-textarea>
            <v-card-actions style="margin-top: -2%">
              <v-btn color="rgb(180,180,180)" icon><v-icon size="40">mdi-alert-circle-outline</v-icon></v-btn>
              <v-spacer></v-spacer>
              <v-btn
                  :disabled="commitBtn"
                  rounded
                  color="yellow darken-2"
                  id="commit-btn"
                  @click="postComment"
              >
                <span class="top-chip-text pl-2">작성하기</span>
                <v-icon class="pl-1 pr-1">mdi-pencil</v-icon>
              </v-btn>
            </v-card-actions>
          </v-col>


          <!-- 정렬 선택 -->
          <v-col cols="12" md="12" class="justify-end d-flex">
            <v-chip-group
                dark mandatory v-model="selectSort"
            >
              <v-chip
                  filter :filter-icon="sortChip[index].icon"
                  color="rgb(220,220,220)"
                  style="background-color: rgb(40,40,40)"
                  active-class="yellow darken-2"
                  v-for="(data,index) in sortChip"
                  :key="index"
                  :value="index"
                  @click="getBookComment(index)"
              >
                <span class="top-chip-text">{{data.text}}</span>
              </v-chip>
            </v-chip-group>
          </v-col>


          <v-divider dark class="mb-6" style="border: rgb(60,60,60) 1.5px solid;"></v-divider>



          <!-- 댓글 없을때 -->
          <v-col cols="12" class="pa-0 mt-12 mb-3 text-center" v-show="noComments">
            <span style="color:rgb(80,80,80); font-size: 21px; font-weight: bold;">댓글이 없습니다. 첫번째 댓글을 작성해보세요</span>
          </v-col>

          <!-- 댓글 출력 -->
          <v-col
              cols="12" class="pa-0 mt-3 mb-3"
              v-for="(data , index) in commentData"
              :key="index"
          >

            <v-card color="rgb(40,40,40)" elevation="0">
                <v-row align="center" class="ma-0 pa-0">
                  <v-col cols="1" class="justify-center align-center text-center d-flex flex-column">
                    <v-btn color="rgb(220,220,220)" icon class="mb-2" @click="setPopularity('up',data.cid)">
                      <v-icon size="40">mdi-chevron-up</v-icon>
                    </v-btn>
                    <span class="pop-text">{{data.popularity}}</span>
                    <v-btn color="rgb(220,220,220)" icon class="mt-2" @click="setPopularity('down',data.cid)">
                      <v-icon size="40">mdi-chevron-down</v-icon>
                    </v-btn>
                  </v-col>

                  <v-col cols="11" class="pa-5 mt-5 mb-5 ">

                    <v-row class="pl-5 pr-5 align-center">
                      <v-avatar
                        size="40"
                        color="grey"
                      ></v-avatar>
                      <span class="pl-3 name-text">{{data.nickName}}</span>
                    </v-row>

                    <v-row class="pl-4 pr-4 pt-3 align-center">
                      <v-rating
                          :value="data.ratings"
                          color="yellow darken-2"
                          background-color="grey darken-2"
                          dense
                          readonly
                          length="4"
                          size="24"
                          class="pr-1"
                      ></v-rating>
                      <span class="date-text">{{data.commentDate}} 일에 작성됨</span>
                    </v-row>

                    <v-row class="content-text pl-5 pr-5 pt-2">
                      <span>{{data.content}}</span>
                    </v-row>

                  </v-col>
                </v-row>
            </v-card>

            <v-divider class="mt-12 mr-16 ml-16" dark></v-divider>

          </v-col>
        </v-row>

      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "CommentComponent",
  props : ["selectBid"],
  data: function (){
    return{
      commentData:[],
      sortChip :
          [
            {'text' : '최신순', 'icon' : 'mdi-clock' },
            {'text' : '인기순', 'icon' : 'mdi-thumb-up'},
            {'text' : '별점순', 'icon' : 'mdi-star'},
          ],
      selectSort : 0,

      //댓글작성
      writeComment:'',
      writeRating:0,
      commitBtn: true,
      noComments: false, //댓글없을때


      //추천도
      interval: {},
      ratingList:[
        {rating:4 ,value: 0, per:0 , count: 0},
        {rating:3 ,value: 0, per:0 , count: 0},
        {rating:2 ,value: 0, per:0 , count: 0},
        {rating:1 ,value: 0, per:0 , count: 0},
      ],
      totalRating: 0,
      totalCount : 0,
    }
  },
  watch: {
      writeComment(val) {
        this.commitBtn = val.length < 10;
      }
    },
  methods: {
    getBookComment(index) {
      if(index == null){
        index = 0
      }
      let data = {}
      data.bid = this.selectBid
      data.sortType = index

      this.$axios.post("comment/", JSON.stringify(data) ,{
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response=>{
        this.commentData = response.data;
        if(response.data.length === 0){
          this.noComments = true;
        }
      })
      .catch(error =>{
        console.log(error.response);
      })
    },

    postComment(){
      if(this.writeRating<1){
        alert("별점을 입력해주세요")
      }
      else if(this.writeComment<11) {
        alert("내용을 입력해주세요")
      }else {
        let data = {}
        data.bid = this.selectBid;
        data.mid = this.$store.state.memberStore.mid;
        data.nickName = this.$store.state.memberStore.nickName;
        data.ratings = this.writeRating;
        data.content = this.writeComment;
        this.$axios.post("comment/write", JSON.stringify(data), {
          headers: {
            "Content-Type": `application/json`,
          },
        }).then(response => {
          console.log(response.data)
          this.writeComment=null;
          this.writeRating=null;
          this.getBookComment();
        })
        .catch(error => {
          console.log(error.response);
        })
      }
    },

    //추천하기
    setPopularity(data,cid){
      console.log("popularity "+ data +" and "+ cid)
      let comment = {}
      comment.update = data
      comment.cid = cid
      this.$axios.post("comment/pop/", JSON.stringify(comment),{
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response=>{
          console.log(response.data);
          this.getBookComment(this.selectSort);
      }).catch(error =>{
          console.log(error.response);
      })
    },

    //종합 추천도 불러오기
    getTotalRating() {
      //clearInterval(this.interval)
      this.$axios.get("comment/pop/" + this.selectBid)
          .then(response=>{
            console.log(response.data)
            let totalCount = 0;
            let sumMulti = 0;
            for(let i = 0; i< response.data.length; i++) {
              totalCount = totalCount + response.data[i].count
            }
            for(let i = 0; i<4; i++) {
              //역순으로 리스트에 데이터 넣기
              //별이 4개면 -> 0번째 리스트에 , 3개면 -> 1번째
              if(response.data[i]==null){
                continue;
              }
                this.ratingList[(this.ratingList.length - 1) - (response.data[i].ratings - 1)].count = response.data[i].count
                this.ratingList[(this.ratingList.length - 1) - (response.data[i].ratings - 1)].per = Math.round(response.data[i].count / totalCount * 100);
                //전체 별점 구하기
                sumMulti = sumMulti + ( response.data[i].ratings * response.data[i].count )
            }

            this.totalRating = Math.round((sumMulti / totalCount) * 10) / 10;
            this.totalCount = totalCount;

            this.startBuffer()
          }).catch(error =>{
            console.log(error.response);
          })
    },
    //그래프 동작
    startBuffer(){
      for(let i = 0 ; i<4; i++) {
        this.interval = setInterval(() => {
          if (this.ratingList[i].value === this.ratingList[i].per) {
            return (this.ratingList[i].per)
          }
          this.ratingList[i].value += 1
        }, 25)
      }
    }

  },

  mounted() {
    this.getBookComment()
    this.getTotalRating()
  }
}
</script>

<style scoped>

.top-chip-text{
  font-weight: bold;
  font-size: 18px;
}
#commit-btn.v-btn--disabled.theme--light {
  background-color: grey !important;
  color: rgb(70,70,70) !important;
  opacity: 0.4;
}


.pop-text{
  color: #BDBDBD;
  font-weight: bold;
  font-size: 20px;
}
.name-text{
  color: #BDBDBD;
}
.date-text{
  color: #BDBDBD;
  font-size: 13px;
}
.content-text{
  color: #BDBDBD;
  font-size: 15px;
}

@media screen and (max-width: 960px){
  .top-chip-text{
    font-weight: bold;
    font-size: 18px;
  }
}
</style>
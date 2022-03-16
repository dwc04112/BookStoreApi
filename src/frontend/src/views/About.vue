<template>
    <v-container
        fluid
    >
      <v-layout wrap row>
        <v-flex>
          <v-row justify="center">
            <v-col cols="12" sm="8" class="mt-8">
              <v-tabs
                  v-model="tab"
                  class="mb-2"
                  background-color="transparent"
                  color="#6B4F4F"
                  grow
                  centered
              >
                <v-tab
                    v-for="(item,index) in detailTag"
                    :key="index"
                    class="category-tab"
                    @click="mainTag(index)"
                >
                  {{ item.main }}
                </v-tab>
              </v-tabs>

              <v-layout justify-center>
                <v-tabs-items
                    v-model="tab"
                    style="background-color: transparent;"
                    class="mb-3"
                >
                  <v-tab-item
                      v-for="(item,index) in detailTag"
                      :key="index"
                  >
                    <v-chip-group
                        active-class="primary--text"
                        v-model="selectTag"
                    >
                      <v-chip
                          v-for="(subData,subIndex) in item.sub"
                          :key="subIndex"
                          outlined
                          color="#6B4F4F"
                          class="top-chip"
                          @click="searchBySubTag(subIndex)"
                      ><span>{{subData}}</span>
                      </v-chip>


            <!-- 수정 해야함 -->


                    </v-chip-group>
                  </v-tab-item>
                </v-tabs-items>
              </v-layout>
            </v-col>
          </v-row>

          <v-layout wrap row justify-center>
            <v-flex
                class="pb-4 pl-4"
                xs12 sm6 md6
                style="flex-basis: auto;"
                v-for="(book, index) in bookDatas"
                :key="index"
            >
              <v-card
                  width="250px"
                  elevation="2"
                  color="rgba(244, 223, 186, 0.5)"
              >
                <v-img
                    :src="book.bookThumb"
                    width="250px" height="390px"
                    @click="show[index].data = !show[index].data"
                ></v-img>

                <v-expand-transition>
                  <div v-show="show[index].data" style="text-align: center">
                    <v-divider></v-divider>
                    <h3 class="pt-4" style="color: #505050"> {{book.bookTitle}} </h3>
                    <v-card-text style="margin-top: -6%">
                      <v-card-subtitle>{{book.bookAuthor}} | {{book.bookPublisher}}</v-card-subtitle>
                      <v-chip-group
                          active-class="primary--text"
                          multiple
                          column
                          v-model="selection"
                      >
                        <v-chip
                            v-for="keyword in keywords[index]"
                            :key="keyword"
                            :value="keyword"
                            outlined
                            small
                        >{{keyword}}
                        </v-chip>
                      </v-chip-group>
                    </v-card-text>
                  </div>
                </v-expand-transition>

              </v-card>
            </v-flex>
          </v-layout>


          <v-footer
              padless
              fixed
              height="150%"
              color="rgba(255,255,255,0.3)"
          >
            <v-layout
                wrap row
                style="flex-direction: row-reverse; margin-top: 20px"

            >
              <v-flex
                  class="footer-col"
                  xs12 sm1 md1
              >
                <v-btn
                    class="footer-btn"
                    width="90px"
                    height="90px"
                    @click="searchBook"
                    elevation="2"
                    absolute
                    icon
                >
                  <v-icon size="60">mdi-magnify</v-icon>
                </v-btn>
              </v-flex>
              <v-flex
                  class="footer-col"
                  xs12 sm4 md4
              >
                  <v-chip-group column>
                    <v-chip
                        class="footer-chip"
                        v-for="(tag,index) in selection"
                        :key="tag"
                        color="#6B4F4F"
                        close
                        @click:close="removeChip(index)"
                        outlined
                        large
                    >
                      <span>{{tag}}</span>
                    </v-chip>
                  </v-chip-group>

              </v-flex>

            </v-layout>
          </v-footer>
        </v-flex>
      </v-layout>
    </v-container>
</template>

<script>



export default {
  name: "About",
  data: function (){
    return{
      bookDatas : [],
      keywords : [],
      selection : [],

      selectTag : '',
      tab : null,

      absolute: true,
      overlay: false,


      detailTag : [
        {main : '전체'},
        {main : '소설' , sub : ['한국소설', '영미소설' , '일본소설' , '중국소설', '기타나라소설','고전소설','장르소설']},
        {main : '시/에세이',  sub : ['한국시','해외시','나라별 에세이','인물/자전적에세이']},
        {main : '자기계발', sub : ['자기능력계발','비즈니스','능력개발','화술/협상']},
        {main : '인문', sub : ['인문일반학', '심리학','교육학','유아교육','특수교육','임용고시','철학','문학이론','언어학','독서/글쓰기','문헌정보학']},
        {main : '역사/문화', sub : ['역사일반','세계사','서양사','동양사','한국사','신화','민속학']},
        {main : '종교', sub : ['종교일반','기독교','가톨릭','불교','그외종교']},
        {main : '정치/사회', sub : ['정치/외교','행정/정책','국방/군사','법학','사회학','사회문제/복지','언론/신문/방송']},
        {main : '예술/대중문화', sub : ['예술일반','미술','만화/애니메이션','디자인','패션/의류','음악','연극']},
        {main : '과학', sub : ['과학이론','수학','물리학','화학','지구과학','생물과학','천문학']},
        {main : '기술/공학', sub : ['건축','토목/건설','환경/소방/도시/조경','자동차/운전','공학일반','금속/재료']},
        {main : '컴퓨터/IT', sub : ['컴퓨터공학','IT일반','데이터베이스','네트워크','프로그래밍/언어','웹프로그래밍']},
      ],
      selectedMainTag : [],
      selectedSubTag : '',


      rules:{
        max : v => (v && v.length <5) || '최대 5개 키워드까지 선택 가능합니다',
      },

      show: [],
      group: null,
    }},
  watch:{
    group () {
      this.drawer = false
    },
  },
  methods: {
    //전체 책
    getBookInfo(){
      this.$axios.get('book/info')
      .then(response=>{
        this.bookDatas = response.data

        for(let i =0; i<response.data.length; i++){
          this.keywords.push(response.data[i].bookKeyword.split(','))
          this.show.push({data:false})
        }
      })
      .catch(error =>{
        console.log(error.response);
      })
    },

    removeChip(index){
      this.selection.splice(index,1)
    },



    //키워드로 검색
    searchBook(){
      this.selectTag=null     //선택된 detailTag 초기화
      if(this.selection.length>5){
        alert("키워드는 5개 까지 선택 가능합니다")
        this.selection = []
      }else{
        let data = {}
        data.bookKeyword = this.selection.toString()
        this.$axios.post("book/keyword",JSON.stringify(data),{
          headers: {
            "Content-Type": `application/json`,
          },
        }).then(response=>{
          this.bookDatas = response.data
          this.keywords = []
          for(let i =0; i<response.data.length; i++){
            this.keywords.push(response.data[i].bookKeyword.split(','))
          }
        }).catch(error =>{
          console.log(error.response);
        })
      }
    },



    //태그로 검색
    mainTag(index){
      if(index===0){
        this.getBookInfo()  //전체 책
      }else{
        //index = 0 은 전체보기가 할당돼서 index로 할당된 tag Num가 1씩 밀림
        //Tag Num = 소설 0000 ~
        this.searchByMainTag(index-1)
      }
    },

    searchByMainTag(index){
      this.selectTag=null     //선택된 detailTag 초기화
      let mainTag = index
      if(mainTag<10){
        mainTag = '0'+index
      }
      this.$axios.get("book/category/"+mainTag)
          .then(response=>{
              this.bookDatas = response.data
              this.keywords = []
              for(let i =0; i<response.data.length; i++){
                this.keywords.push(response.data[i].bookKeyword.split(','))
              }
          }).catch(error =>{
            console.log(error.response);
          })
    },

    searchBySubTag(subIndex,){
      //위와 같은 이유로 mainTag -1
      let mainTag = Number(this.tab)-1
      let subTag = subIndex
      if(mainTag<10){
        mainTag = '0'+mainTag
      }
      if(subTag<10){
        subTag = '0'+subIndex
      }
      this.$axios.get("book/category/"+mainTag+subTag)
          .then(response=>{
            this.bookDatas = response.data
            this.keywords = []
            for(let i =0; i<response.data.length; i++){
              this.keywords.push(response.data[i].bookKeyword.split(','))
            }
          }).catch(error =>{
        console.log(error.response);
      })
    },

  },
  mounted() {
    this.getBookInfo();
  }
}
</script>

<style scoped>
v-container{
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  align-content: center;
}
.bookTitle{
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  width: 250px;
  color: #505050;
}

.footer-btn{
  margin-right: 50px;
  background-color: rgba(191, 146, 112, 0.5);
}
.footer-col{
  margin-bottom: 60px;
  height: 150px;
}

.category-tab{
  font-size: 20px;
  font-weight: bolder;
  color: #6B4F4F;
}



.footer-chip span{
  font-size: 22px;
  font-weight: bolder;
}
.footer-chip.v-chip--outlined{
  border-width: 3px;
}

.top-chip span{
  font-size: 16px;
}



</style>
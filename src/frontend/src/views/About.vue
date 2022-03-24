<template>
    <v-container
        fluid
    >
      <v-layout wrap row>
        <v-flex>
          <v-row justify="center">
            <v-col cols="12" sm="9" class="mt-8">
              <v-tabs
                  v-model="tab"
                  class="mb-2 main-tabs"
                  background-color="transparent"
                  color="#6B4F4F"
                  style="margin-top: -2%"
                  grow
                  centered

              >
                <v-tab
                    v-for="(item,index) in detailTag"
                    :key="index"
                    class="category-tab"
                    @click="byCategory(item.num)"
                >
                  <span style="font-size: 14px">{{ item.main }}</span>
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
                          @click="byCategory(subData.num)"
                          small
                      ><span>{{subData.subMain}}</span>
                      </v-chip>
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
                  width="160px"
                  elevation="2"
                  color="rgba(244, 223, 186, 0.5)"
              >
                <v-img
                    :src="book.bookThumb"
                    width="160px" height="250px"
                    @click="show[index].data = !show[index].data"
                ></v-img>

                <v-expand-transition>
                  <div v-show="show[index].data" style="text-align: center">
                    <v-divider></v-divider>
                    <h5 class="pt-4" style="color: #505050"> {{book.bookTitle}} </h5>

                      <v-card-subtitle class="expand-font">{{book.bookAuthor}} | {{book.bookPublisher}}</v-card-subtitle>

                    <v-chip-group
                          active-class="primary--text"
                          multiple
                          column
                          v-model="selection"
                      >
                        <v-chip
                            class="inner-chip"
                            v-for="keyword in keywords[index]"
                            :key="keyword"
                            :value="keyword"
                            outlined
                            small
                        ><span>{{keyword}}</span>
                        </v-chip>
                    </v-chip-group>


                    <v-divider></v-divider>
                    <v-card-actions>
                      <v-tooltip top color="pink">
                        <template v-slot:activator="{ on, attrs }">
                          <v-btn
                            v-bind="attrs"
                            v-on="on"
                            icon
                            @click.stop="getWishTitle(book.bid)"
                        >
                          <v-icon color="pink">
                            mdi-heart
                          </v-icon>
                        </v-btn>
                        </template>
                        <span>ADD Wish List</span>
                      </v-tooltip>
                    </v-card-actions>

                  </div>
                </v-expand-transition>
              </v-card>
            </v-flex>
          </v-layout>


          <v-container fluid>
          <v-dialog
              class="align-center justify-center align-content-center"
              v-model="dialog"
              max-width="700"
          >
            <v-card color="#FDF6EC">
              <v-card-title class="text-h5">
                Select Wish List
              </v-card-title>
              <v-card-text>위시리스트 카테고리</v-card-text>
              <div class="d-flex flex-column align-center">
                <v-row
                    align="center" justify="center"
                    style="width: 75%; background-color: #F3E9DD; text-align: center;"
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
                            @click.stop="setWishData(wishCategory.wishlistTitle)"
                        >
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
                    >
                        <font-awesome-icon class="plus-icon" icon="fa-solid fa-plus"/>
                    </v-card>
                    <a class="wish-a">위시리스트 추가</a>
                  </v-col>
                </v-row>
              </div>

              <v-divider class="mt-8"></v-divider>
              <v-card-actions>
                <v-spacer></v-spacer>

                <v-btn
                    color="green darken-1"
                    text
                    @click="dialog = false"
                >
                  Close
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-container>


        <v-container fluid>
          <v-dialog
              class="align-center justify-center align-content-center"
              v-model="commitDialog"
              max-width="300"
          >
            <v-card color="#FDF6EC">
              <v-card-title>등록하시겠습니까?</v-card-title>

              <v-divider/>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    color="green darken-1"
                    text
                    @click="pushWishData()"
                >
                  등록
                </v-btn>
                <v-btn
                    color="green darken-1"
                    text
                    @click="commitDialog = false"
                >
                  취소
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
         </v-container>

          <v-footer
              padless
              fixed
              height="110%"
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
                    width="60px"
                    height="60px"
                    @click="searchBook"
                    elevation="2"
                    absolute
                    icon
                >
                  <v-icon size="40">mdi-magnify</v-icon>
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
                        small
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

      /*
      detailTag : [
        {main : '전체'},
        {main : '소설' , sub : ['한국소설', '영미소설' , '일본소설' , '중국소설', '러시아소설','프랑스소설','독일소설','북유럽소설','기타나라소설','고전소설','장르소설']},
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

       */
      detailTag : [
        {
          main: '전체'
        },
        {
          main: '소설',
          num: '00',
          sub: [
            {num: '0000', subMain: '한국소설'},
            {num: '0001', subMain: '영미소설'},
            {num: '0002', subMain: '일본소설'},
            {num: '0003', subMain: '중국소설'},
            {num: '0004', subMain: '러시아소설'},
            {num: '0005', subMain: '프랑스소설'},
            {num: '0006', subMain: '독일소설'},
            {num: '0007', subMain: '북유럽소설'},
            {num: '0008', subMain: '그외유럽소설'},
            {num: '0009', subMain: '기타나라소설'},
            {num: '0010', subMain: '고전소설'},
            {num: '0011', subMain: '장르소설'},
          ]
        },
        {
          main : '시/에세이',
          num: '01',
          sub : [
            {num: '0100', subMain: '한국시'},
            {num: '0101', subMain: '해외시'},
            {num: '0102', subMain: '나라별 에세이'},
            {num: '0103', subMain: '인물/자전적에세이'},
            {num: '0104', subMain: '청소년 시/에세이'}
          ]
        },
        {
          main : '자기계발',
          num: '02',
          sub : [
            {num: '0200', subMain: '자기능력계발'},
            {num: '0201', subMain: '비즈니스'},
            {num: '0202', subMain: '능력개발'},
            {num: '0203', subMain: '화술/협상'},
          ]
        },
        {
          main : '인문',
          num: '03',
          sub : [
            {num: '0300', subMain: '인문일반학'},
            {num: '0301', subMain: '심리학'},
            {num: '0302', subMain: '교육학'},
            {num: '0303', subMain: '유아교육'},
            {num: '0304', subMain: '특수교육'},
            {num: '0305', subMain: '임용고시'},
            {num: '0306', subMain: '철학'},
            {num: '0307', subMain: '문학이론'},
            {num: '0308', subMain: '언어학'},
            {num: '0309', subMain: '독서/글쓰기'},
            {num: '0310', subMain: '문헌정보학'},
          ]
        },
        {
          main : '역사/문화',
          num: '04',
          sub : [
            {num: '0400', subMain: '역사일반'},
            {num: '0401', subMain: '세계사'},
            {num: '0402', subMain: '서양사'},
            {num: '0403', subMain: '동양사'},
            {num: '0404', subMain: '한국사'},
            {num: '0405', subMain: '신화'},
            {num: '0406', subMain: '민속학'},
            {num: '0407', subMain: '문화사'},
            {num: '0408', subMain: '역사인물'},
            {num: '0409', subMain: '역사기행'},
            {num: '0410', subMain: '지리학'},
          ]
        },
        {
          main : '종교',
          num: '05',
          sub : [
            {num: '0500', subMain: '종교일반'},
            {num: '0501', subMain: '기독교'},
            {num: '0502', subMain: '가톨릭'},
            {num: '0503', subMain: '불교'},
            {num: '0504', subMain: '그외종교'},
          ]
        },
        {
          main : '정치/사회',
          num: '06',
          sub : [
            {num: '0600', subMain: '정치/외교'},
            {num: '0601', subMain: '행정/정책'},
            {num: '0602', subMain: '국방/군사'},
            {num: '0603', subMain: '법학'},
            {num: '0604', subMain: '사회학'},
            {num: '0605', subMain: '사회문제/복지'},
            {num: '0606', subMain: '언론/신문/방송'},
            {num: '0607', subMain: '정치/사회문고'},
          ]
        },
        {
          main : '예술/대중문화',
          num: '07',
          sub : [
            {num: '0700', subMain: '예술일반'},
            {num: '0701', subMain: '미술'},
            {num: '0702', subMain: '만화/애니메이션'},
            {num: '0703', subMain: '디자인'},
            {num: '0704', subMain: '패션/의류'},
            {num: '0705', subMain: '음악'},
            {num: '0706', subMain: '연극'},
            {num: '0707', subMain: '영화'},
            {num: '0708', subMain: '예술기행'},
            {num: '0709', subMain: '예술문화'},
          ]
        },
        {
          main : '과학',
          num: '08',
          sub : [
            {num: '0800', subMain: '과학이론'},
            {num: '0801', subMain: '수학'},
            {num: '0802', subMain: '물리학'},
            {num: '0803', subMain: '화학'},
            {num: '0804', subMain: '지구과학'},
            {num: '0805', subMain: '생물과학'},
            {num: '0806', subMain: '천문학'},
            {num: '0807', subMain: '청소년 교향과학'},
          ]
        },
        {
          main : '기술/공학',
          num: '09',
          sub : [
            {num: '0900', subMain: '건축'},
            {num: '0901', subMain: '토목/건설'},
            {num: '0902', subMain: '환경/소방/도시/조경'},
            {num: '0903', subMain: '자동차/운전'},
            {num: '0904', subMain: '공학일반'},
            {num: '0905', subMain: '금속/재료'},
            {num: '0906', subMain: '기계/역학/항공'},
            {num: '0907', subMain: '전기/전자'},
            {num: '0908', subMain: '농수산/축사'},
            {num: '0909', subMain: '생활과학'},
            {num: '0910', subMain: '의학'},
          ]
        },
        {
          main : '컴퓨터/IT',
          num: '10',
          sub : [
            {num: '1000', subMain: '컴퓨터공학'},
            {num: '1001', subMain: 'IT일반'},
            {num: '1002', subMain: '데이터베이스'},
            {num: '1003', subMain: '네트워크'},
            {num: '1004', subMain: '보안/해킹'},
            {num: '1005', subMain: '프로그래밍/언어'},
            {num: '1006', subMain: '웹프로그래밍'},
            {num: '1007', subMain: '게임'},
            {num: '1008', subMain: '모바일 프로그래밍'},
            {num: '1009', subMain: '그래픽'},
            {num: '1010', subMain: '멀티미디어'},
            {num: '1011', subMain: 'CAD'},
            {num: '1012', subMain: '자격증/수험서'},
          ]
        },

      ],
      selectedMainTag : [],
      selectedSubTag : '',

      show: [],             // expand transition
      wishlistTitle : [],   // wishTitle & count
      wishData : [],        //bid & wishlistTItle
      // group: null,
      dialog: false,        //wishlist Dialog
      commitDialog : false, //wishlist post commit/close


      rules:{
        max : v => (v && v.length <5) || '최대 5개 키워드까지 선택 가능합니다',
      },

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

    byCategory(num){
      this.selectTag=null
      if(num==null){
        this.getBookInfo()
      }else{
        this.$axios.get("book/category/"+num)
            .then(response=>{
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

    getWishTitle(bid){
      this.dialog = true
      this.wishData.bid = bid
      this.$axios.get("wish/title/")
          .then(response=>{
            this.wishlistTitle = response.data
          }).catch(error =>{
        console.log(error.response);
      })
    },
    setWishData(wishlistTitle){
      this.commitDialog = true
      this.wishData.wishlistTitle = wishlistTitle;
    },

    pushWishData(){
      let data = {}
      data.wishlistTitle = this.wishData.wishlistTitle;
      data.bid = this.wishData.bid;
      this.$axios.post("wish/",JSON.stringify(data),{
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response=>{
        console.log(response.data.message)
        if(response.data.success == true){
          alert(this.wishData.wishlistTitle+" 에 성공적으로 등록했습니다!")
        }else{
          alert("이미 해당 책이 등록되어 있습니다. 다른 보관함을 이용해주세요.")
        }
        this.commitDialog=false
      }).catch(error =>{
        console.log(error.response);
      })

    },


    //add wishList
    addWish(index){
      alert("wishList" + index)
    },

  },
  created() {
    this.$eventBus.$on('mainKeyword',(payload)=> {
      let index = this.selection.indexOf(payload);
      if(index===-1) {
        this.selection.push(payload)
      }else{
        this.selection.splice(index,1)
      }
    })
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

.expand-font{
  font-size: 10px;
}

.footer-chip span{
  font-size: 15px;
}
.footer-chip.v-chip--outlined{
  border-width: 1.5px;
}

.top-chip span{
  margin-top: 2%;
  font-size: 12px;
  font-weight: bold;
}


.inner-chip span{
  font-size: 10px
}

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
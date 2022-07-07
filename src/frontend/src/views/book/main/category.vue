<template>
  <v-container fluid class="pa-0 ma-0" style="background-color: rgb(20,20,20)">

    <v-row class="justify-center  align-center" style="background-color: rgb(40,40,40);">
      <v-col cols="10" md="8" class="pt-2 pb-1">
        <v-chip-group
            class="top-chip-group"
            mandatory
            color="white"
            next-icon="mdi-chevron-right white--text"
            prev-icon="mdi-chevron-left white--text"
            show-arrows
        >
          <v-chip
              v-for="(data,index) in selectTag"
              :key="index"
              outlined
              color="rgb(60,60,60)"
              text-color="rgb(240,240,240)"
              class="top-chip"
              active-class="white"
              @click="byCategory(data.num)"
          ><span>{{data.subMain}}</span>
          </v-chip>
        </v-chip-group>
      </v-col>
    </v-row>


    <v-row style="background-color: rgb(20,20,20)">
      <!--List Card-->
      <v-col class="no-gutters ml-md-4">
        <div style="text-align: start;">
          <div
              style="display: inline-block;"
              class="pa-3"
              v-for="(book, index) in bookDatas"
              :key="index"
          >
            <v-card
                style="height: 225px; overflow: hidden;"
                elevation="4"
                min-width="120px"
                tile
            >
              <img
                  :src="book.bookThumb"
                  alt="bookThumb"
                  class="book-thumb"
                  height="100%"
                  loading="lazy"
                  @click="widthSize(book)"
              />
            </v-card>
          </div>
        </div>
      </v-col>
      <div style="height: 1000px"></div>


      <!--Transition Select-->
      <transition name="sub-slide" mode="in-out" >
        <v-col
            class="pa-0 ma-0"
            cols="7" sm="5" md="4"
            color="grey lighten-3"
            v-show="show.data"
        >
          <div
              class="select-book"
          >
            <div class="inner-select-book">
              <v-card-actions class="pt-5 pl-5">
                <v-btn
                    icon
                    color="rgb(220,220,220)"
                    @click.stop="show.data =false"
                >
                  <v-icon size="40px">mdi-close</v-icon>
                </v-btn>
              </v-card-actions>

              <div class="align-center d-flex flex-column pt-8">
                <v-img
                    class="select-book-img"
                    :src="selectBook.bookThumb"
                ></v-img>

                <h4 class="pt-4" style="color: rgb(220,220,220)"> {{selectBook.bookTitle}} </h4>

                <v-card-subtitle style="color: rgb(220,220,220)">{{selectBook.bookAuthor}} | {{selectBook.bookPublisher}}</v-card-subtitle>

                <div>
                  <v-chip
                      class="inner-chip ma-1"
                      v-for="keyword in selectKeywords"
                      :key="keyword"
                      :value="keyword"
                      outlined
                      color="rgb(220,220,220)"
                      small
                      @click="keywordSearch(keyword)"
                  >
                    <span>{{keyword}}</span>
                  </v-chip>
                </div>

                <v-divider></v-divider>
                <v-card-actions class="mt-2">
                  <v-col>
                    <v-tooltip top color="green darken-2">
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            class="ma-2"
                            v-bind="attrs"
                            v-on="on"
                            elevation="2"
                            fab color="rgb(50,50,50)"
                            @click.stop="addCart(selectBook.bid)"
                        >
                          <v-icon color="green darken-2" large>
                            mdi-cart
                          </v-icon>
                        </v-btn>
                      </template>
                      <span>장바구니 추가</span>
                    </v-tooltip>

                    <v-tooltip top color="pink">
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            class="ma-2"
                            v-bind="attrs"
                            v-on="on"
                            elevation="2"
                            fab color="rgb(50,50,50)"
                            @click.stop="setComponentData(selectBook.bid)"
                        >
                          <v-icon color="pink" large>
                            mdi-heart
                          </v-icon>
                        </v-btn>
                      </template>
                      <span>위시리스트에 추가</span>
                    </v-tooltip>

                    <v-tooltip top color="yellow darken-2 black--text">
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            class="ma-2"
                            v-bind="attrs"
                            v-on="on"
                            elevation="2"
                            fab color="rgb(50,50,50)"
                            @click.stop="detailView(selectBook.bid)"
                        >
                          <v-icon color="yellow darken-2" large>
                            mdi-book-open-variant
                          </v-icon>
                        </v-btn>
                      </template>
                      <span>책 보러가기</span>
                    </v-tooltip>
                  </v-col>
                </v-card-actions>
              </div>
            </div>
          </div>
        </v-col>
      </transition>


      <!--      컴포넌트 호출      -->
      <v-container fluid class="pa-0 ma-0">
        <v-dialog
            class="align-center justify-center align-content-center"
            v-model="dialog"
            max-width="600"
        >
          <v-card color="#FDF6EC">
            <v-toolbar
                elevation="0"
                class="white--text"
                color="rgb(33,33,33)"
            >
              <v-card-title>나의 위시리스트</v-card-title>
            </v-toolbar>

            <component
                v-bind:selectBid="setBid"
                :key="componentKey"
                :is="component"
                @childKey="updateComponentKey"
                @pushTab="setWishTab"
            ></component>

            <v-card-text>
              <div>
                <ul>
                  <li>카테고리 추가는 메인페이지 > 책 등록시 가능합니다</li>
                  <li>새로 만들어진 카테고리는 자동으로 공개처리됩니다.</li>
                  <li>카테고리 관리는 마이페이지 > 위시리스트 > 나의 찜목록 으로 이동하시면 가능합니다.</li>
                </ul>
              </div>
            </v-card-text>
            <v-divider></v-divider>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                  color=rgb(33,33,33)
                  text
                  @click="pushInfoWishList(3)"
              >
                <h4>내 보관함으로 이동</h4>
              </v-btn>
              <v-btn
                  color=rgb(33,33,33)
                  text
                  @click="dialog = false"
              >
                Close
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-container>

    </v-row>
  </v-container>
</template>

<script>
import SearchMenu from "@/views/SearchMenu";
export default {
  name: "category",
  components: {SearchMenu},
  data: function (){
    return{
      //메인 데이터
      bookDatas : [],         //메인 데이터 (타이틀로 검색)



      //선택된 책 보기
      show : {data:false , bid: null},
      selectBook : '',
      selectKeywords : '',


      //상단 카테고리
      tab : null,
      selectTag : '',
      detailTag : [
        {
          main: '소설',
          num: '00',
          sub: [
            {num: '00',subMain: '전체'},
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
          ],
        },
        {
          main : '시/에세이',
          num: '01',
          sub : [
            {num: '01',subMain: '전체'},
            {num: '0100', subMain: '한국시'},
            {num: '0101', subMain: '해외시'},
            {num: '0102', subMain: '나라별 에세이'},
            {num: '0103', subMain: '인물/자전적에세이'},
            {num: '0104', subMain: '청소년 시/에세이'}
          ],
        },
        {
          main : '자기계발',
          num: '02',
          sub : [
            {num: '02',subMain: '전체'},
            {num: '0200', subMain: '자기능력계발'},
            {num: '0201', subMain: '비즈니스'},
            {num: '0202', subMain: '능력개발'},
            {num: '0203', subMain: '화술/협상'},
          ],
        },
        {
          main : '인문',
          num: '03',
          sub : [
            {num: '03',subMain: '전체'},
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
            {num: '04',subMain: '전체'},
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
            {num: '05',subMain: '전체'},
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
            {num: '06',subMain: '전체'},
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
            {num: '07',subMain: '전체'},
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
            {num: '08',subMain: '전체'},
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
            {num: '09',subMain: '전체'},
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
            {num: '10',subMain: '전체'},
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
      completeData : [],
      inputMsg : '',



      //컴포넌트 관련 데이터 (Dialog)
      dialog: false,              //wishlist Dialog
      componentKey: 0,             // reload component
      wishTab :'WishList',         // 보여줄 컴포넌트 값
      setBid : '',                 // push to component

    }
  },

  watch: {
    $route(to,from){
      if (to.path !== from.path) this.byCategory(this.$route.params.category);
    },
  },

  methods: {
    searchByMenu(val){
      this.$axios.get("book/search/" + val)
          .then(response => {
            this.bookDatas = response.data
          }).catch(error => {
        console.log(error.response);
      })
    },
    //키워드로 검색
    keywordSearch(data){
      this.$axios.get("book/keyword/"+data)
          .then(response=>{
            this.bookDatas = response.data
          }).catch(error =>{
        console.log(error.response);
      })
    },
    //카테고리로 검색
    byCategory(num){
      this.$axios.get("book/category/"+num)
          .then(response=>{
            this.selectTag = this.detailTag.filter(e => num.includes(e.num))[0].sub
            this.bookDatas = response.data
          }).catch(error =>{
        console.log(error.response);
      })
    },


    //화면 크기에 따라 다른 method
    widthSize(book){
      let x = window.innerWidth

      if(x>=600){
        this.openInfo(book)
      }else{
        this.detailView(book.bid)
      }
    },
    //책 보러가기
    detailView(bid){
      this.$router.push({name: 'DetailView' ,query: {bid}});
    },
    //Select Book Info
    openInfo(book){
      if(this.show.data === true){  //책 정보가 열려있을때
        if(this.show.bid === book.bid){ //같은 책이라면 정보 닫기
          this.show.data= !this.show.data
        }
      }else{  //닫혀있으면 열기
        this.show.data= !this.show.data
      }
      this.show.bid = book.bid
      this.selectBook=book
      this.selectKeywords = book.bookKeyword.split(',')
    },
    /*
    * 컴포넌트 관련 메소드
    *
    * */
    setComponentData(bid){
      this.dialog =true;
      this.setBid = bid             //선택한 책 id를 child 컴포넌트에 보내기위해
      this.setWishTab("WishList")   // 디폴트 페이지는 항상 WishList
      this.updateComponentKey()     // 컴포넌트를 리로드 하기위해
    },

    updateComponentKey(){
      this.componentKey +=1        //컴포넌트 리로드
    },
    setWishTab(data){
      this.wishTab = data
    },

    //마이페이지 위시리스트 수정으로 넘어가기
    pushInfoWishList(pushNum){
      // num 0 ~ 3
      console.log("push num : "+ pushNum)
      this.$router.push({name: 'InfoNavi', params: {AboutTab: pushNum}})
    },

    //cart에 담기
    addCart(bid){
      this.$axios.get("cart/add/"+bid
      ).then(response=>{
        console.log(response.data.message);
        alert("성공적으로 장바구니에 추가했습니다")
      }).catch(error =>{
        console.log(error.response);
      })
    },

    // == 컴포넌트관련 끝 ==


  },
  computed:{
    // 컴포넌트에서 페이지 변경
    component() {
      const wishTab = this.wishTab;
      return () => import(`@/views/wishlist/${wishTab}`);
    },
  },
  mounted() {
    this.byCategory(this.$route.params.category)
  }
}
</script>

<style scoped>



/* 1. 상단 카테고리 영역 */

.top-chip span{
  font-size: 13px;
  font-weight: lighter;
}



/* 상단 chip */


.high-chip span{
  color: black;
}

/* 2.책 리스트 영역 */


/* 선택한 책 영역 */
.select-book{
  background-color: rgb(40,40,40);
  text-align: center;
  height: 100vh;
  position: sticky;
  top: 40px;
}
.inner-select-book{
  position: sticky;
  top: 55px;
}
.select-book-img{
  width: 200px;
}

.sub-slide-enter{
  transform: translateX(400px);
  opacity: 1;
}
.sub-slide-enter-active,
.sub-slide-leave-active {
  transition: all 0.4s ease-out;
}
.sub-slide-leave-to {
  transform: translateX(400px);
  opacity: 1;
}


@media screen and (max-width: 600px){


  .select-book-img{
    width: 130px;
    margin-right: 13px;
  }

}

/* 컴포넌트  */
li{
  font-size: 12px;
}


</style>
<template>
  <v-container fluid class="align-center" style="background-color: rgb(25,25,25)">

   <SearchMenu
       @searchData="searchByMenu"
       @drawMenu ="drawMenu"
   ></SearchMenu>

    <v-row class="mt-9 pa-0">

      <v-col cols="2" sm="3" md="2" class="justify-start d-flex pa-0">

        <v-card
            class="main-nav"
            width="100%"
            min-height="100%"
            tile
            elevation="0"
            color="rgb(40,40,40)"
        >

          <v-list color="rgb(40,40,40)">

            <v-list-item to="/">
              <v-list-item-icon>
                <v-icon color="rgb(190,190,190)">mdi-home</v-icon>
              </v-list-item-icon>

              <v-list-item-title style="color: rgb(190,190,190)">Home</v-list-item-title>
            </v-list-item>

            <v-list-group
                :value="true"
                prepend-icon="mdi-account-circle grey--text"
            >
              <template v-slot:activator >
                 <v-list-item-title style="color: rgb(190,190,190)">Category</v-list-item-title>
              </template>


              <v-list-item
                  :value="true"
                  v-for="item in detailTag"
                  :key="item.main"
                  v-model="item.active"
                  link
                  :to="{
                    path: `/category/${item.num}`,
                  }"
              >
                <v-list-item-content>
                  <v-list-item-title style="color: rgb(190,190,190)" v-text="item.main"></v-list-item-title>
                </v-list-item-content>

              </v-list-item>

            </v-list-group>
            <v-divider class="ma-6 white" />

            <v-list-item
                v-for="(data,index) in links"
                :key="index"
                :to="data.link"
                link
            >
              <v-list-item-icon >
                <v-icon color="rgb(190,190,190)">{{ data.icon }}</v-icon>
              </v-list-item-icon>

              <v-list-item-content>
                <v-list-item-title style="color: rgb(190,190,190)">{{ data.name }}</v-list-item-title>
              </v-list-item-content>

            </v-list-item>
          </v-list>

          <div class="d-flex justify-center">
            <v-btn
                outlined
                rounded
                width="200px"
                class="logout-btn"
            >
              로그아웃
            </v-btn>
          </div>
        </v-card>
      </v-col>




      <v-col cols="12" sm="9" md="10" class="pa-0">
        <router-view></router-view>
      </v-col>

    </v-row>

    <v-row>
      <v-footer
          dark
          padless
      >
        <v-card
            flat
            tile
            color="rgb(40,40,40)"
            class="white--text text-center"
        >
          <v-card-text>
            <v-btn
                v-for="icon in icons"
                :key="icon"
                class="mx-4 white--text"
                icon
            >
              <v-icon size="24px">
                {{ icon }}
              </v-icon>
            </v-btn>
          </v-card-text>

          <v-card-text class="white--text pt-0">
            Phasellus feugiat arcu sapien, et iaculis ipsum elementum sit amet. Mauris cursus commodo interdum. Praesent ut risus eget metus luctus accumsan id ultrices nunc. Sed at orci sed massa consectetur dignissim a sit amet dui. Duis commodo vitae velit et faucibus. Morbi vehicula lacinia malesuada. Nulla placerat augue vel ipsum ultrices, cursus iaculis dui sollicitudin. Vestibulum eu ipsum vel diam elementum tempor vel ut orci. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.
          </v-card-text>

          <v-divider></v-divider>

          <v-card-text class="white--text">
            {{ new Date().getFullYear() }} — <strong>Vue & Spring boot</strong>
          </v-card-text>
        </v-card>
      </v-footer>
    </v-row>
  </v-container>
</template>

<script>
import SearchMenu from "@/views/SearchMenu";
export default {
  name: "index",
  components: {SearchMenu},
  data: () => ({
        selectList:0,
        detailTag : [
          { main: '소설', num: '00', },
          { main : '시/에세이', num: '01', },
          { main : '자기계발', num: '02', },
          { main : '인문', num: '03', },
          { main : '역사/문화', num: '04', },
          { main : '종교', num: '05', },
          { main : '정치/사회', num: '06', },
          { main : '예술/대중문화', num: '07', },
          { main : '과학', num: '08', },
          { main : '기술/공학', num: '09', },
          { main : '컴퓨터/IT', num: '10', },
        ],
        links: [
          {icon:'mdi-cart', name:'Cart', link:'/my/cart', color:'orange lighten-1', show:true},
          {icon:'mdi-library', name:'Wish List', link:'/my/wish', color:'green lighten-1', show:true},
          {icon:'mdi-book', name:'Comment', link:'/my/comment', color:'blue lighten-1', show:true},
          {icon:'mdi-book', name:'Order', link:'/my/order', color:'blue lighten-1', show:true},
          {icon:'mdi-book', name:'infoEdit', link:'/my/infoEdit', color:'blue lighten-1', show:true},
        ],

        totalList: [
          {
            name:'Category',
            child: [
              { main: '소설', num: '00', },
              { main : '시/에세이', num: '01', },
              { main : '자기계발', num: '02', },
              { main : '인문', num: '03', },
              { main : '역사/문화', num: '04', },
              { main : '종교', num: '05', },
              { main : '정치/사회', num: '06', },
              { main : '예술/대중문화', num: '07', },
              { main : '과학', num: '08', },
              { main : '기술/공학', num: '09', },
              { main : '컴퓨터/IT', num: '10', },
            ],
          },
          {
            name:'My Page',
            child: [
              {icon:'mdi-cart', main:'Cart', link:'../my/cart'},
              {icon:'mdi-library', main:'Wish List', link:'../my/wish'},
              {icon:'mdi-book', main:'Comment', link:'../my/comment'},
              {icon:'mdi-book', main:'Order', link:'../my/order'},
            ],
          },
        ],

        bySearch: '',

        icons: [
          'mdi-facebook',
          'mdi-twitter',
          'mdi-linkedin',
          'mdi-instagram',
        ],
      }
  ),
  watch:{

  },
  methods: {
    linkTo(data){
      this.selectList = data
      this.$router.replace({path: this.links[data].link })
    },

    searchByMenu(data){
      this.$router.push({name:'search', query:{search:data}}).catch(()=>{})
    },
    drawMenu(data){
      if(this.screenX>780){
        console.log('hi'+data)
      }
    },
    searchByCategory(num){
     this.$router.push({name:'search',query:{category:num}})
    },
  },

  mounted() {
  }
}
</script>

<style scoped>
.logout-btn{
  margin-top : 30%;
  color: white;
}
.v-list-item--active {
  background-color: red;
}


.v-list-group--active {
  color: red !important;
}

.sub-slide-enter{
  transform: translateX(400px);
  opacity: 1;
}
.sub-slide-enter-active,
.sub-slide-leave-active {
  transition: all 0.3s ease-out;
}
.sub-slide-leave-to {
  transform: translateX(400px);
  opacity: 1;
}
.main-nav{
  display: block;
}
@media screen and (max-width: 768px){
  /* 최상단 검색 */
  .main-nav{
    display: none;
  }

}
</style>
<template>
  <v-container fluid class="align-center" style="background-color: rgb(25,25,25)">
   <SearchMenu
       @searchData="searchByMenu"
       @drawMenu ="drawMenu"
   ></SearchMenu>

    <v-row class="mt-9 pa-0">
      <v-col cols="12" sm="11" md="12" class="justify-start d-flex pa-0 main-nav">
        <v-card
            class="main-nav"
            min-height="100%"
            tile v-show="true"
            elevation="0"
        >
          <v-navigation-drawer
              color="rgb(40,40,40)"
              v-model="drawer"
              :mini-variant.sync="mini"
              permanent
          >
            <div class="justify-end d-flex">
              <v-btn icon color="grey lighten-2" @click="mini=!mini" class="mr-2">
                <v-icon>{{ mini ? 'mdi-arrow-right-drop-circle-outline' : 'mdi-arrow-left-drop-circle-outline' }}</v-icon>
              </v-btn>
            </div>

            <v-list color="rgb(40,40,40)">

              <v-list-item to="/">
                <v-list-item-icon>
                  <v-icon color="rgb(190,190,190)">mdi-home</v-icon>
                </v-list-item-icon>

                <v-list-item-title style="color: rgb(190,190,190)">Home</v-list-item-title>
              </v-list-item>

              <v-list-group
                  :value="false"
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
          </v-navigation-drawer>

        </v-card>
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
        componentKey:0,
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
          {icon:'mdi-cart', name:'Cart', link:'/my/cart',  show:true},
          {icon:'mdi-library', name:'Wish List', link:'/my/wish', show:true},
          {icon:'mdi-book', name:'Comment', link:'/my/comment', show:true},
          {icon:'mdi-credit-card', name:'Order', link:'/my/order',  show:true},
          {icon:'mdi-account-box', name:'infoEdit', link:'/my/infoEdit',  show:true},
        ],
        drawer: true,
        mini : true,
        bySearch: '',
        categoryList : false,

        icons: [
          'mdi-facebook',
          'mdi-twitter',
          'mdi-linkedin',
          'mdi-instagram',
        ],
      }
  ),
  watch:{
    categoryList(val){

        console.log(val)

    }
  },
  methods: {
    searchByMenu(data){
      this.$router.push({name:'search', query:{search:data}}).catch(()=>{})
    },
    drawMenu(data){
      console.log(data)
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
  background-color: #D32F2F;
}


.v-list-group--active {
  color: #D32F2F !important;
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
  .main-nav{
    display: none;
  }

}

</style>
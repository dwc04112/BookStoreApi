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
              permanent
          >
            <v-list color="rgb(40,40,40)">

              <v-list-item to="/">
                <v-list-item-icon>
                  <v-icon color="rgb(190,190,190)">mdi-home</v-icon>
                </v-list-item-icon>

                <v-list-item-title style="color: rgb(190,190,190)">Home</v-list-item-title>
              </v-list-item>
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
        <router-view style="min-height: 75vh"></router-view>
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
  name: "Admin",
  components: {SearchMenu},
  data: () => ({
        selectList:0,
        componentKey:0,
        links: [
          {icon:'mdi-credit-card', name:'Order', link:'/admin/order',  show:true},
          {icon:'mdi-book', name:'Comment', link:'/admin/comment', show:true},
          {icon:'mdi-account-box', name:'member', link:'/admin/member',  show:true},
          {icon:'mdi-account-box', name:'postBook', link:'/admin/postBook',  show:true},
        ],
        drawer: true,
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
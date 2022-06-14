<template>
  <v-container fluid class="align-center" style="background-color: rgb(20,20,20)">
    <SearchMenu></SearchMenu>
    <div style="height: 85px"></div>
    <v-row class="justify-center mt-4">

      <v-col cols="4" class="justify-end pr-4 d-flex pa-0 ma-0">
        <v-card
            width="256"
            height="100%"
            tile
            color="rgb(40,40,40)"
        >
          <v-sheet
              class="pa-4"
              color="rgb(40,40,40)"
          >
            <div class="d-flex justify-center pa-4">
              <v-avatar
                  class="mb-4"
                  color="grey darken-1"
                  size="95"
              ></v-avatar>
            </div>

            <div style="font-size: 20px; text-align: center" class="white--text">{{this.$store.state.memberStore.nickName}} 님</div>
          </v-sheet>
          <v-divider class="ma-6 white" />

          <v-list color="rgb(40,40,40)">
            <v-list-item-group
                dark
                v-model="selectList"
                mandatory
            >
              <v-list-item
                  v-for="(data,index) in links"
                  :key="index"
                  class="ml-3"
                  @click="linkTo(data.link)"
              >
                <v-list-item-icon >
                  <v-icon color="white">{{ data.icon }}</v-icon>
                </v-list-item-icon>

                <v-list-item-content>
                  <v-list-item-title style="color: white">{{ data.name }}</v-list-item-title>
                </v-list-item-content>

              </v-list-item>


            </v-list-item-group>
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

      <v-col cols="8" class="pa-0 pl-4">
        <router-view></router-view>
      </v-col>



    </v-row>


    <div style="height: 55px"></div>
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
  name: "My",
  components: { SearchMenu},
  data: () => ({
        selectList : '',
        links: [
          {icon:'mdi-home', name:'My Page', link:'../my', color:'red lighten-1', show:false},
      //    {icon:'mdi-pencil', name:'Edit Info', link:'InfoEdit', color:'red lighten-1', show:true},
          {icon:'mdi-cart', name:'Cart', link:'../my/cart', color:'orange lighten-1', show:true},
          {icon:'mdi-library', name:'Wish List', link:'../my/wish', color:'green lighten-1', show:true},
          {icon:'mdi-book', name:'Comment', link:'../my/comment', color:'blue lighten-1', show:true},
          {icon:'mdi-book', name:'Order', link:'../my/order', color:'blue lighten-1', show:true},
        ],


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
      this.$router.replace({path:data})
    }


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
</style>
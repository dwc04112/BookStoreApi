<template>
<v-app>
  <SearchMenu
      @searchData="searchByMenu"
      :key="searchComponentKey"
  ></SearchMenu>

 <main>
   <v-container fluid class="pa-0">
     <router-view></router-view>
   </v-container>
 </main>

  <v-footer
      dark app
      padless
      class="pa-0 justify-center"
  >
      <v-card-actions>
        <v-btn
            v-for="icon in icons"
            :key="icon.icon"
            class="mx-4 white--text"
            icon @click="linkTo(icon.link)"
        >
          <v-icon size="24px" :color="icon.color">
            {{ icon.icon }}
          </v-icon>
        </v-btn>
        <v-btn @click="updateComponentKey">hello</v-btn>
      </v-card-actions>

  </v-footer>
</v-app>
</template>

<script>

import SearchMenu from "@/views/SearchMenu";
export default {
  name: 'App',
  components: {SearchMenu},
  data: () => ({
    icons: [
      {icon:'mdi-home',link:''},
      {icon:'mdi-heart',color:'red darken-2',link:'my/wish'},
      {icon:'mdi-linkedin'},
      {icon:'mdi-instagram'},
    ],

    searchComponentKey:0,
  }),
  watch:{
    $route(){
      this.searchComponentKey+=1
    }
  },
  methods:{
    searchByMenu(data){
      this.$router.push({name:'search', query:{search:data}}).catch(()=>{})
    },
    linkTo(link){
      this.$router.push({path:'/'+link}).catch(()=>console.log("같은 페이지"))
    },

    updateComponentKey(){
      this.searchComponentKey +=1        //컴포넌트 리로드
    },
  },
  mounted() {
    this.updateComponentKey();
  }
};
</script>

<style>

</style>

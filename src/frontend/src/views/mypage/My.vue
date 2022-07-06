<template>
  <v-container fluid style="height: 100%; background-color: rgb(20,20,20)">
    <v-row>

      <v-col cols=11 md="12" offset="1" offset-md="0" offset-sm="0" class="pa-md-8 mt-12 mb-16 pt-16">
        <router-view class="pa-5" style="min-height: 100vh"></router-view>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
export default {
  name: "My",
  data: () => ({
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
<template>
  <v-container fluid class="align-center pa-0">
    <v-row>
      <v-col cols=12 md="12" class="pa-md-8 mt-12 pt-16">
        <router-view style="min-height: 100vh"></router-view>
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
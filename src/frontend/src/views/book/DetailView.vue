<template>
  <v-app>
    <SearchMenu></SearchMenu>

    <v-container fluid style="height: 100%; background-color: rgb(20,20,20)">
      <v-row class="ma-0 pa-0 justify-center">

        <v-col cols="12" md="6" class="ma-0 pa-0">
          <BookDetailComponent/>
        </v-col>
      </v-row>

      <v-row style="height: 180px"></v-row>

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
  </v-app>
</template>

<script>

import BookDetailComponent from "@/views/book/bookComponents/BookDetail";
import SearchMenu from "@/views/SearchMenu";
export default {
  name: "DetailView",
  components: {SearchMenu, BookDetailComponent},
  data: function (){
    return{
      //Tab model
      moveTabNum : 0,
      icons: [
        'mdi-facebook',
        'mdi-twitter',
        'mdi-linkedin',
        'mdi-instagram',
      ],
    }
  },
  methods: {
  },

  mounted() {

  }
}

</script>

<style scoped>

</style>
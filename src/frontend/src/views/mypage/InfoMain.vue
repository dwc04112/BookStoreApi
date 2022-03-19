<template>
  <v-container
      fluid
  >
    <v-layout wrap row>
      <v-flex sm12 md12 xs12>
        <v-sheet
            color="grey lighten-4"
            class="pa-4"
            height="200px"
        >
          <div>
            <a><span>john@vuetifyjs.com</span> (회원등급)</a>
          </div>
        </v-sheet>
      </v-flex>

      <v-flex sm12 md12 xs12 class="pt-4 pb-4">
        <v-card height="300px"  width="75%" class="mx-auto">
          <div style="height: 30%"></div>
          <v-layout wrap row align-center justify-center>
            <v-flex md4 sm4 style="text-align: center">
              <div>구매한 책</div>
              <v-avatar
                  color="grey darken-1"
                  size="64"
              ></v-avatar>
            </v-flex>
            <v-divider vertical/>
            <v-flex md4 sm4 style="text-align: center">
              <div>나의 회원 등급</div>
              <v-avatar
                  color="grey darken-1"
                  size="64"
              ></v-avatar>
            </v-flex>
            <v-divider vertical/>
            <v-flex md4 sm4 style="text-align: center">
              <div>개인정보 수정</div>
              <v-avatar
                  color="grey darken-1"
                  size="64"
              ></v-avatar>
            </v-flex>


          </v-layout>
        </v-card>
      </v-flex>


      <v-divider class="mx-4"></v-divider>
      <v-flex md12 sm12
      >
        <v-card-title>관심있는 책</v-card-title>
        <v-card-text>

          <v-layout wrap row justify-center>
            <v-flex
                class="pb-4 pt-4 pl-2 pr-2"
                v-for="s in 6"
                :key="s"
                xs12 sm6 md6
                style="flex-basis: auto;"
            >
              <v-card
                  width="160px"
                  height="250px"
                  elevation="2"
                  color="rgba(244, 223, 186, 0.5)"
              >
                <div>book {{s}}</div>
              </v-card>
              <v-row
                  align="center"
                  class="mx-0 pt-4"
              >
                <v-rating
                    :value="4.5"
                    color="amber"
                    dense
                    half-increments
                    readonly
                    size="14"
                ></v-rating>
                <div class="grey--text ms-4">
                  4.5 (413)
                </div>
              </v-row>
            </v-flex>
          </v-layout>


          <div class="my-4 text-subtitle-1">
            $ • Italian, Cafe
          </div>

          <div>Small plates, salads & sandwiches - an intimate setting with 12 indoor seats plus patio seating.</div>
        </v-card-text>
      </v-flex>
      <v-flex>
          <v-divider class="mx-4"></v-divider>

          <v-card-title>관심 키워드</v-card-title>

          <v-card-text>
            <v-chip-group
                v-model="selection"
                active-class="deep-purple accent-4 white--text"
                column
            >
              <v-chip>5:30PM</v-chip>

              <v-chip>7:30PM</v-chip>

              <v-chip>8:00PM</v-chip>

              <v-chip>9:00PM</v-chip>
            </v-chip-group>
          </v-card-text>

          <v-card-actions>
            <v-btn
                color="deep-purple lighten-2"
                text
                @click="infoEdit"
            >
              회원정보 수정
            </v-btn>
          </v-card-actions>

      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: "InfoMain",
  data: () => ({
    loading: false,
    selection: 1,
  }),
  methods: {
    infoEdit(){
      console.log("hi")
    }
  },
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
</style>
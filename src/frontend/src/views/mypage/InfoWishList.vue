<template>
  <v-layout wrap row class="pa-4">
    <v-flex md12 sm12>
        <v-card-title>찜목록</v-card-title>
        <v-divider class="mx-4"></v-divider>
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

        </v-card-text>


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
</template>

<script>
export default {
  name: "InfoWishList",
  data: () => ({
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

</style>
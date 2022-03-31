<template>
  <v-container
      fluid
  >
    <v-layout wrap row class="pa-4">
      <v-flex sm12 md12 xs12>
        <v-card height="800px">
          <v-card height="250px"  width="75%" class="mx-auto" outlined elevation="0">
            <v-layout wrap row align-center justify-center style="margin-top: 5%">
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

            <v-divider style="margin-top: 7%"></v-divider>
            <v-card-actions >
              <v-layout>
                <v-flex>
                  section 1
                </v-flex>
                <v-flex>
                  section 2
                </v-flex>
                <v-flex>
                  section 3
                </v-flex>
              </v-layout>
            </v-card-actions>


          </v-card>

        </v-card>
      </v-flex>
    </v-layout>



  </v-container>
</template>

<script>
export default {
  name: "InfoMain",
  data: () => ({
    loading: false,
  }),
  methods: {

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
<template>
  <v-app>
    <v-container
        fluid
        style="background-color: #FFBBBB;  height: 100vh; width: 100%;"
    >
      <v-layout wrap row>
        <v-flex>
          <v-row>
            <v-col>
            </v-col>
          </v-row>
          <v-row style="display: flex;
                    align-items: center;
                    justify-items: center;"
          >
              <v-col
                  xs12 sm6 md2 class="pb-2"
                  v-for="(book, index) in bookDatas"
                  :key="index"
              >
                  <v-card
                      class="mx-auto"
                      width="300px" height="435px"
                  >
                    <v-img :src="book.bookThumb" ></v-img>
                  </v-card>
                  <v-card
                      class="mx-auto"
                      width="300px"
                      height="300px"
                  >
                    <v-card-title class="bookTitle"> {{book.bookTitle}} </v-card-title>
                    <v-card-text>
                      <v-chip-group
                          active-class="primary--text"
                          column
                          multiple
                          v-model="selection"
                      >
                        <v-chip
                            v-for="keyword in keywords[index]"
                            :key="keyword"
                            :value="keyword"
                            outlined
                        >{{keyword}}
                        </v-chip>
                      </v-chip-group>

                      <v-card-subtitle>{{book.bookAuthor}}</v-card-subtitle>
                    </v-card-text>
                  </v-card>

              </v-col>
          </v-row>
        </v-flex>
      </v-layout>
    </v-container>
  </v-app>
</template>

<script>


export default {
  name: "About",

  data: function (){
    return{
      bookDatas : [],
      keywords : [],
      selection : [],
    }},

  methods: {
    getBookInfo(){

      this.$axios.get('book/info')
      .then(response=>{
        console.log(response.data);
        this.bookDatas = response.data

        for(let i =0; i<response.data.length; i++){
          this.keywords.push(response.data[i].bookKeyword.split(','))
        }
      })
      .catch(error =>{
        console.log(error.response);
      })
    }
  },
  mounted() {
    this.getBookInfo();
  }
}
</script>



<style scoped>

.bookTitle{
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  width: 300px;
  color: #505050;
}
</style>
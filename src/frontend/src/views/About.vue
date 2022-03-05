<template>
  <v-app>
    <v-container
        fluid
        style="background-color: #FFBBBB;  height: 100vh; width: 100%;"
    >
      <v-layout wrap row>
        <v-flex>


          <v-row style="display: flex;
                    align-items: center;
                    justify-items: center;">
            <v-col >
              <v-card
                  elevation="0"
                  style="border-radius: 0"
                  height="80px"
              >
                <v-card-title style="float: left;">선택된 태그</v-card-title>
                <v-chip-group style="float: left; margin-top: 5px">
                  <v-chip
                      v-for="(tag,index) in selection"
                      :key="tag"
                      color="gray"
                      close
                      @click:close="removeChip(index)"
                  >{{tag}}
                  </v-chip>
                </v-chip-group>

                <v-btn
                    style="float: right; margin-top: 5px"
                    @click="searchBook"
                > Search
                </v-btn>
                <v-text-field style="float:right"></v-text-field>

              </v-card>
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
                      width="150px" height="217px"
                      outlined
                      elevation="2"
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
                          multiple
                          v-model="selection"
                      >
                        <v-chip
                            v-for="keyword in keywords[index]"
                            :key="keyword"
                            :value="keyword"
                            outlined
                            small
                        >{{keyword}}
                        </v-chip>
                      </v-chip-group>
                      <v-card-subtitle>{{book.bookAuthor}} | {{book.bookPublisher}}</v-card-subtitle>
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
    },

    removeChip(index){
      this.selection.splice(index,1)
    },

    searchBook(){
      console.log(this.selection)
      let data = {}
      data.bookKeyword = this.selection.toString()
      this.$axios.post("book/keyword",JSON.stringify(data),{
        headers: {
          "Content-Type": `application/json`,
        },
      }).then(response=>{
        console.log(response.data)
        this.bookDatas = response.data
        this.keywords = []
        for(let i =0; i<response.data.length; i++){
          this.keywords.push(response.data[i].bookKeyword.split(','))
        }
      }).catch(error =>{
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
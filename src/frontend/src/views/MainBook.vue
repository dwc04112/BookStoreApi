<template>
<div>
  <v-container>
    <v-carousel
        cycle
        height="1100px"
        hide-delimiter-background
        show-arrows-on-hover
    >
      <v-carousel-item
          v-for="(book, index) in bookDatas"
          :key="index"
          reverse-transition="fade-transition"
      >
        <v-card
            width="300px" height="434px"
            outlined
            elevation="0"
            style="float: left"
        >
          <v-img :src="book.bookThumb"></v-img>
        </v-card>

        <v-card
            style="float: left"
            elevation="0"
        >
          <v-card-title class="bookTitle"> {{book.bookTitle}} </v-card-title>
          <v-card-text>{{book.bookContent}}</v-card-text>
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
      </v-carousel-item>
    </v-carousel>
  </v-container>
</div>
</template>

<script>
export default {
  name: "MainBook",
  data: function (){
    return{
      bookDatas : [],
      keywords : [],
      selection : [],
      colors: [
        'primary',
        'secondary',
        'yellow darken-2',
        'red',
        'orange',
      ],
    }},

  methods: {
    getBookInfo(){
      this.$axios.get('book/main')
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
  },
  mounted() {
    this.getBookInfo();
  }
}
</script>

<style scoped>

</style>
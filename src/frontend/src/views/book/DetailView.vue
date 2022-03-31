<template>
  <div>
    Detail View {{bid}}
    <div>
      {{bookData.bookTitle}}
      <v-img width="200px" :src="bookData.bookThumb"></v-img>
    </div>
    <div>
      <v-chip
          class="ml-2"
          v-for="(data, index) in keyword"
          :key="index"
          :value="data"
          outlined
          small
          color="rgb(20,20,20)"
      >{{data}}
      </v-chip>
    </div>
  </div>
</template>

<script>

export default {
  name: "DetailView",
  data: function (){
    return{
      bid: this.$route.query.bid,
      bookData : [],
      keyword : '',
    }
  },

  methods: {
    getBookDetail(){
      this.$axios.get('book/'+this.bid)
          .then(response=>{
            this.bookData = response.data
            this.keyword = (response.data.bookKeyword.split(','))
          })
          .catch(error =>{
            console.log(error.response);
          })
    }
  },
  mounted() {
    this.getBookDetail()
  }
}

</script>

<style scoped>

</style>
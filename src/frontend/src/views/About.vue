<template>
  <body>


    <div>
      <p>안녕하세요 About page</p>
    </div>

  </body>

</template>

<script>


export default {
  name: "About",

  data: function (){
    return{
      bookData : [],
    }},

  methods: {
    getBookInfo(){
      this.$axios.get('book/info')
      .then(response=>{
        console.log(response.data);
        response.data = this.bookData
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

</style>
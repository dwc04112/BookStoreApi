<template>
  <body>
    <Menu></Menu>
    <v-main>
      <router-view/>
    </v-main>
  </body>
</template>

<script>


import Menu from "@/views/Menu";
export default {
  name: 'App',
  components: {Menu},
  data: () => ({
    //
  }),
};
</script>

<style>

</style>

<template>
  <div>
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>

    <v-main>
      <router-view/>
    </v-main>
  </div>
</template>

<script>

export default {
  name: 'App',


  data: () => ({
    //
  }),
};
</script>

<template>
  <nav
      class="main_nav"
  >
    <div class="navLogo" style="float: left;">
      <router-link to="/">
        <v-img
            src="@/assets/mainLogo.png"
            max-height="25px"
            max-width="25px"
            style="float: left; margin-right: 3px;"
        ></v-img>
        <a>BookStore</a>
      </router-link>
    </div>

    <ul class="menu_nav" style="float: left">
      <li><router-link to="/about"><a>About</a></router-link></li>
      <li><router-link to="/"><a>BestSeller</a></router-link></li>
      <li><router-link to="/"><a>Recommend</a></router-link></li>
      <li><router-link to="/"><a>KeyWord</a></router-link></li>
    </ul>

    <ul class="info_nav" style="float: left">
      <li><router-link to="/"><a>MyPage</a></router-link></li>
      <li><router-link to="/"><a>Login</a></router-link></li>
    </ul>

    <a href="#"
       class="nav_menu_btn"
       @click="navBtn"
    >
      <font-awesome-icon icon="fa-solid fa-bars"/>
    </a>

  </nav>
</template>

<script>
export default {
  name: "Menu",
  data: () => ({
    //
  }),

  methods: {
    navBtn(){
      const menuNav = document.querySelector('.menu_nav');
      const infoNav = document.querySelector('.info_nav');
      menuNav.classList.toggle('active');
      infoNav.classList.toggle('active')
    }
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Source+Sans+Pro&display=swap');
.main_nav{
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 12px;
}
a{
  text-decoration: none;
  color: black;
  font-family: 'Source Sans Pro', sans-serif;
}
v-img{
  color: black;
}

/*main menu*/
.menu_nav{
  display: flex;
  list-style:none;
  padding-left: 0;

}
.menu_nav li{
  margin-top: 3px;
  padding: 10px 10px;
}
.menu_nav li:hover{
  background-color: yellowgreen;
  border-radius: 8px;
  border: 5px;
}

/* mypage menu*/
.info_nav{
  display: flex;
  list-style:none;
  padding-left: 0;
}
.info_nav li{
  padding: 8px 8px;
}


.nav_menu_btn{
  display: none;
  position: absolute;
  right: 32px;
  font-size: 24px;
  color: grey;
}
body{
  margin: 0;
}

@media screen and (max-width: 768px){
  .main_nav{
    flex-direction: column;
    align-items: flex-start;
  }

  .menu_nav{
    display: none;
    flex-direction: column;
    align-items: center;
    width: 100%;
  }
  .menu_nav li{
    width:100%;
    text-align: center;
  }
  .info_nav{
    display: none;
    justify-content: center;
    width: 100%;
  }
  .nav_menu_btn{
    display: block;
  }

  .menu_nav.active,
  .info_nav.active {
    display: flex;
  }
}

</style>
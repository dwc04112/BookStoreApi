<template>
  <div class="container-fluid">
    <div style="justify-content: center">
      <div>
        <nav class="nav" style="position: fixed">
          <v-card
              class = "menu-card"
              height=250px
              width="80px"
              style="margin-top: 100%; margin-left: 50%; background-color: rgba(255,255,255,0.4)"
              elevation="0"
              >
            <ul class="nav-menu">
              <li @click="('li1')"><a id="li1" href="#home">Home</a></li>
              <li @click="('li2')"><a id="li2" href="#bestSeller">bestSeller</a></li>
              <li @click="('li3')"><a id="li3" href="#about">about</a></li>
            </ul>
          </v-card>

        </nav>
      </div>

      <div class="section">
        <section id="home" style="background-color: #F0FFC2">
          <h1>안녕하세요</h1>
        </section>
      </div>
      <div class="section">
        <section id="bestSeller" style="background-color: #FFE4C0">
          <BestSeller/>
        </section>
      </div>
      <div class="section">
        <section id="about" style="background-color: #FFBBBB">
          <PostBook/>
        </section>
      </div>
    </div>
  </div>
</template>
<script>
import $ from 'jquery'

import BestSeller from "@/views/BestSeller";
import PostBook from "@/views/PostBook";


export default {
  name: "Home",
  components: {PostBook, BestSeller},
  data () {
    return {

    }
  },
  methods : {

  },

  created() {
    $(document).ready(function () {

      const $menu_a = $("a", $(".nav-menu"));
      $menu_a.click(function () {
        $("html").animate(
            {scrollTop: $($(this).attr("href")).offset().top},
            {duration: 900, complete: $(this).attr("href")}
        );


      });

      $(".section").each(function () {
        // 개별적으로 Wheel 이벤트 적용 mousewheel(IE/chrome/opera) DOMMouseScroll(FF)
        $(this).on("mousewheel DOMMouseScroll", function (e) {
          e.preventDefault();
          var delta = 0;
          /* IE */
          if (!event) var event = window.event;
          //휠에 대한 정보 얻기 파이어폭스 외 IE/Chrome/Opera = wheelDelta
          if (event.wheelDelta) {
            delta = event.wheelDelta / 50;
            //평균 50~120 사이로 요소의 인식높이에 따라 다름(한 화면(height100%)기준일떄는 120
            if (window.opera) delta = -delta;
            //휠에 대한 정보 얻기 Mozilla FF = detail
          } else if (event.detail) delta = -event.detail / 3;
          var moveTop = null;
          // 마우스휠을 위에서 아래로
          if (delta < 0) {
            if ($(this).next() !== undefined) {
              moveTop = $(this).next().offset().top;

            }
            // 마우스휠을 아래에서 위로
          } else {
            if ($(this).prev() !== undefined) {
              moveTop = $(this).prev().offset().top;

              if($(this).offset().top <100){
                moveTop = 0;
              }
            }
          }
          // 화면 이동 0.8초(800)
          $("html,body").stop().animate({
            scrollTop: moveTop + 'px'
          }, {
            duration: 700, complete: function () {
            }
          });
        });
      });
    })
  },


}

</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Source+Sans+Pro&display=swap');
#home,#bestSeller ,#about{
  height: 100vh;
  width: 100%;
}
.nav-menu{
  list-style : none;
}
div{
  font-family: 'Source Sans Pro', sans-serif;
}


</style>

// you must keep the following lines on when you use this
// original idea from the Geocities Watermark
// © Nicolas - http://www.javascript-page.com

// include fixes for Netscape 7

var window_says  = "Useful code snippets for Java, JS or PB developers!";
var image_width = 20;
var image_height = 20;
var left_from_corner = 0;
var up_from_corner = 0;
var wm ;
var JH = 0;
var JW = 0;
var JX = 0;
var JY = 0;
var left = image_width + left_from_corner + 17;
var up = image_height + up_from_corner + 15;

if (navigator.appVersion.indexOf("MSIE") != -1){
   wm = document.all.jsbrand;
   wm.onmouseover = msover
   wm.onmouseout = msout

}

function watermark() {

 if(document.getElementById && !document.all) {
   // netsape fixes
   wm = document.getElementById("jsbrand");
   wm.visibility = "hide";
   wm.style.top = 
       window.pageYOffset + window.innerHeight - 40;
   wm.style.left = 
       window.innerWidth-55;       
   wm.visibility= "show";
 }

 if (navigator.appVersion.indexOf("MSIE") != -1){
  if (navigator.appVersion.indexOf("Mac") == -1){
   wm.style.display = "none";
   JH = document.body.clientHeight;
   JW = document.body.clientWidth;
   JX = document.body.scrollLeft;
   JY = document.body.scrollTop;
   wm.style.top = (JH+JY-up);
   wm.style.left =(JW+JX-left);
   wm.style.display = "";
  }
 }
}

function msover(){
    window.status = window_says
    return true;
}

function msout(){
    window.status = ""
    return true;
}

setInterval("watermark()",100);


$(document).ready(function(){
	$(".ham-burger").click(function(){
		$(".side-bar").toggleClass("side-bar-toggle");
		$(this).toggleClass("ham-burger-toggle");
		$(".content-div").toggleClass("content-div-toggle");
	});
});
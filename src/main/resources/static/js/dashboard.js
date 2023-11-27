$(document).ready(function(){
	$(".ham-burger").click(function(){
		$(".side-bar").toggleClass("side-bar-toggle");
		$(this).toggleClass("ham-burger-toggle");
		$(".content-div").toggleClass("content-div-toggle");
	});
});

const search = () => {

	let query=$('#search-bar').val();
	let url=`http://localhost:8080/search/${query}`;

	console.log("Search Function");
	if(query == ''){
		$("#search-result").hide();
	}
	else{
	fetch(url).then((response) =>{
		console.log("Fetch Api");
		return response.json();
	}
	).then((result) =>{
		
		console.log(result+"resultant array");

		let text="<div class='search-data'>";
		// result.forEach(contact => {
		// 	text+= `<a href="/user/AllContactDetails/${contact.getContactId}`;
		// });
		result.forEach(element => {
				console.log(element);
				text+= `<a href="/user/AllContactDetails/${element.contactId}"> ${element.name} </a>`
		});
		text+="</div>";

		console.log("data in the text field : "+text);
		$('#search-result').html(text);
		$("#search-result").show();
	});
	}


}
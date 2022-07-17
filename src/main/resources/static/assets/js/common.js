$(function() {
	$(".img-profile").on("click", function() {
		location.href = "/index";
	});
	
	$(".modal-overlay").on("click", function() {
		$(".modal").hide();
	});
});
$(function() {
	$(".postlist-posts").on("click", function() {
		let postId = $(this).attr("post-id");
		location.href = "/blog/study/" + postId;
	});
	
	$("button[btn-usage=write-post]").on("click", function() {
		location.href = "/blog/write"
	});
	
	$("button[btn-usage=modify-post]").on("click", function () {
		location.href = "/blog/modify/" + $("input[name=postId]").val();
	});
	
	$("button[btn-usage=delete-post]").on("click", function () {
		let conf = confirm("정말 삭제하시겠습니까?");
		console.log($("input[name=postId]").val());
		
		if(conf) {
			$.ajax({
			    url: "/blog/delete/" + $("input[name=postId]").val(), 
			    method: "POST",   
			    dataType: "text"
			})
			.done(function(json) {
			    alert("글 삭제에 성공했습니다");
			})
			.fail(function(xhr, status, errorThrown) {
				alert("글 삭제에 실패했습니다");
			});
		}
	});
});

function prevPage(page) {
	location.href = "/blog/study?page=" + (page - 1); 
}

function nextPage(page) {
	location.href = "/blog/study?page=" + (page * 1 + 1); 
}
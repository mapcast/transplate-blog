$(function() {
	$(".postlist-posts").on("click", function() {
		let postId = $(this).attr("post-id");
		location.href = "/blog/study/" + postId;
	});
	
	$("button[btn-usage=write-post]").on("click", function() {
		location.href = "/blog/write"
	});
	
	$("button[btn-usage=modify-post]").on("click", function () {
		location.href = "/blog/modify/" + $("input[name=post-id]").val();
	});
	
	$("button[btn-usage=delete-post]").on("click", function () {
		let conf = confirm("정말 삭제하시겠습니까?");
		console.log($("input[name=post-id]").val());
		
		if(conf) {
			$.ajax({
			    url: "/blog/delete/" + $("input[name=post-id]").val(), 
			    method: "POST",   
			    dataType: "text"
			})
			.done(function(json) {
			    alert("글 삭제에 성공했습니다");
				location.reload();
			})
			.fail(function(xhr, status, errorThrown) {
				alert("글 삭제에 실패했습니다");
				location.reload();
			});
		}
		
	});
	
	$("button[btn-usage=write-comment]").on("click", function() {
		let dto = new Object();
		if(typeof $("input[name=comment-password]").val() == "undefined") {
			dto.writerId = $("input[name=comment-writer-id]").val();
			dto.writer = $("input[name=comment-writer]").val();
		} else {
			dto.writer = $("input[name=comment-writer]").val();
			dto.password = $("input[name=comment-password]").val();
		}
		
		dto.content = $("textarea[name=comment-content]").val();
		dto.postId = $("input[name=post-id]").val();
		
		let parsedDto = JSON.stringify(dto);
		
		$.ajax({
		    url: "/blog/comment/write", 
		    data: parsedDto,  
		    method: "POST",   
		    dataType: "JSON",
		    contentType: "application/json"
		})
		.done(function(json) {
		    alert("댓글 작성에 성공했습니다");
		    location.reload();
		})
		.fail(function(xhr, status, errorThrown) {
			alert("댓글 작성에 실패했습니다");
		});
	});
	
	$("button[btn-usage=delete-comment]").on("click", function() {
		let conf = confirm("정말 삭제하시겠습니까?");
		console.log($("input[name=post-id]").val());
		
		let commentId = $(this).parent().parent().parent().attr("comment-id");
		
		if(conf) {
			$.ajax({
			    url: "/blog/comment/delete/" + commentId, 
			    method: "POST",   
			    dataType: "text"
			})
			.done(function(json) {
			    alert("댓글 삭제에 성공했습니다");
				location.reload();
			})
			.fail(function(xhr, status, errorThrown) {
				alert("댓글 삭제에 실패했습니다");
			});
		}
	});
	
	$("button[btn-usage=prev-page]").on("click", function() {
		$("input[name=page]").val($("input[name=page]").val() - 1);
		$("#hiddenForm").submit();
	});
	
	$("button[btn-usage=next-page]").on("click", function() {
		$("input[name=page]").val($("input[name=page]").val() + 1);
		$("#hiddenForm").submit();
	});
});

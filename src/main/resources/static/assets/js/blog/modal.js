$(function() {
	$("button[btn-usage=login]").on("click", function() {
		$(".login-modal").show();
	});
	
	$("button[btn-usage=join]").on("click", function() {
		$(".join-modal").show();
	});
	
	$("button[btn-usage=login-submit]").on("click", function() {
		loginSubmit();
	});
	$("button[btn-usage=join-submit]").on("click", function() {
		joinSubmit();
	});
	$(".login-modal").find("input[data-input=userId]").on("keyup", function(e) {
		if(e.keyCode == 13) {
			loginSubmit();
		}
	});
	$(".login-modal").find("input[data-input=userPassword]").on("keyup", function(e) {
		if(e.keyCode == 13) {
			loginSubmit();
		}
	});
});

function loginSubmit() {
	let loginData = new Object();
	loginData.userId = $(".login-modal").find("input[data-input=userId]").val();
	loginData.password = $(".login-modal").find("input[data-input=userPassword]").val();
	
	let dataString = JSON.stringify(loginData);
	
	$.ajax({
		url: "/login",
		method: "POST",
		data: dataString,
		contentType: 'application/json'
	}).done(function() {
		location.reload();
	}).fail(function() {
		alert("잘못된 아이디 혹은 비밀번호입니다.");
	});
}

function joinSubmit() {
	let joinData = new Object();
	joinData.userId = $(".join-modal").find("input[data-input=userId]").val();
	joinData.userName = $(".join-modal").find("input[data-input=userName]").val();
	joinData.password = $(".join-modal").find("input[data-input=userPassword]").val();
	
	let dataString = JSON.stringify(joinData);
	
	$.ajax({
		url: "/join",
		method: "POST",
		data: dataString,
		contentType: 'application/json'
	}).done(function() {
		alert("회원가입이 완료되었습니다.");
		location.reload();
	}).fail(function() {
		alert("회원가입이 실패했습니다.");
	});
}
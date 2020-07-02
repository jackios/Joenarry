
$(function() {
    validateRule();
    $(".i-checks").iCheck({checkboxClass:"icheckbox_square-green-login"});
	$('.imgcode').click(function() {
		var url = ctx + "captcha/captchaImage?type=" + captchaType + "&s=" + Math.random();
		$(".imgcode").attr("src", url);
	});
});

$.validator.setDefaults({
    submitHandler: function() {
		login();
    }
});

function login() {
	$.modal.loading($("#btnSubmit").data("loading"));
	var username = $.common.trim($("input[name='username']").val());
    var password = $.common.trim($("input[name='password']").val());
    //var csrf = $.common.trim($("input[name='csrf']").val());
    var validateCode = $("input[name='validateCode']").val();
    var rememberMe = $("input[name='rememberme']").is(':checked');

    var encrypt = new JSEncrypt();
    encrypt.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnBUXlQTb8UIQLeXhjj+IpsXZJsJ8SdChFuurrnq4R6UxY9UFhgBBpNivFmmtb1yVkYMRbCGF10mDH69jcujGLWUixA6QRqc0CczdnC7BI3gQOv7LLgl8PaeKyeMnCK+9zQckwvuywUOcJ2C8/LwGoyNZ4wrVbEnwDeapFT9M+xAqGBVfW/g93DRyrWZ6n/umPS97z6QXLn6Yyn2fyjBdwW2M8cFw5GhMdgyObPfywJLV4VbgIV2EyLJJHz/vn4EtFs6BN9gAXlcJnaLhobSJw6E+WqRdWiQMcHatgVCDzeMXR/+k6Mwy8Pc7VaOxXbrusB+YNbV6V1OgFBOV7rT89wIDAQAB");
    var data = encrypt.encrypt(password);
    
    $.ajax({
        type: "post",
        url: ctx + "login",
        data: {
            //"_csrf" : csrf.token,
            "username": username,
            "password": encodeURI(data).replace(/%/g, '%25').replace(/\+/g, '%2B').replace(/=/g, '%3D'),
            "validateCode" : validateCode,
            "rememberMe": rememberMe,
            //"csrf_code": csrf
        },
        success: function(r) {
            if (r.code == 0) {
                location.href = ctx + 'index';
            } else {
            	$.modal.closeLoading();
            	$('.imgcode').click();
            	$.modal.msg(r.msg);
            }
        }
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            username: {
                required: icon + "请输入您的用户名",
            },
            password: {
                required: icon + "请输入您的密码",
            }
        }
    })
}

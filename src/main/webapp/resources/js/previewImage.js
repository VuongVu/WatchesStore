/*
$(function () {
    $(":file").change(function () {
        if (this.files && this.files[0]) {
            var reader = new FileReader();
            reader.onload = imageIsLoaded;
            reader.readAsDataURL(this.files[0]);
        }
    });
});

function imageIsLoaded(e) {
    $('#myImg').attr('src', e.target.result);
    $('#urlImg').attr('value', e.target.result);
};
*/
$(function () {
	$(":file").on("change", function() {
	  $("[for=file]").html(this.files[0].name);
	  $("#myImg").attr("src", URL.createObjectURL(this.files[0]));
	})
})
$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}

	$("#alertError").hide();
});



// SAVE ============================================
$(document).on("click", "#btnSave", function(_event) {

	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateItemForm();
	
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		
		return;
	}

	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	$.ajax(
		{
			url: "EmployeesAPI",
			type: type,
			data: $("#formItem").serialize(),
			dataType: "text",
			
			complete: function(response, status) {
				onItemSaveComplete(response.responseText, status);
			}
		});
});

function onItemSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);

		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();

	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}



// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(_event) {

	$("#hidItemIDSave").val($(this).data("itemid"));
	$("#ename").val($(this).closest("tr").find('td:eq(0)').text());
	$("#email").val($(this).closest("tr").find('td:eq(1)').text());
	$("#phone").val($(this).closest("tr").find('td:eq(2)').text());
	$("#gender").val($(this).closest("tr").find('td:eq(3)').text());

});



// DELETE==========================================
$(document).on("click", ".btnRemove", function(_event) {

	$.ajax(
		{
			url: "EmployeesAPI",
			type: "DELETE",
			data: "eid=" + $(this).data("itemid"),
			dataType: "text",

			complete: function(response, status) {
				onItemDeleteComplete(response.responseText, status);
			}
		});
});

function onItemDeleteComplete(response, status) {
	if (status == "success") {
		
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
			
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
		
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
		
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}



// CLIENT-MODEL================================================================
function validateItemForm() {

	// NAME
	if ($("#ename").val().trim() == "") {

		return "Insert Employee Name.";
	}

	// EMAIL
	if ($("#email").val().trim() == "") {

		return "Insert Employee Email.";
	}

	// PHONE
	if ($("#phone").val().trim() == "") {

		return "Insert Employee Phone Number.";
	}

	// GENDER
	if ($("#gender").val().trim() == "") {

		return "Insert Gender.";
	}

	return true;
}
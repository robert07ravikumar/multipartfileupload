$("#table-div").hide();
$("#error-div").hide();

$('#uploadForm').submit(function(e) {

    var formData = new FormData($(this)[0]);
    //var formData = new FormData();
    //formData.append( 'file', input.files[0]);
    formData.append('dateFilter',$('#dateFilter').val());

    $("#shopTable").html("");
    $("#error-div").html("");
    $("#error-div").hide();
    $("#table-div").hide();

    $.ajax({
       url: '/uploadFile',
       type: 'POST',
       data: formData,
       async: false,
       success: function (data) {
           drawTable(data);
           $("#table-div").show();
       },
        error: function (xhr, ajaxOptions,thrownError) {
            $("#error-div").show();
            $("#error-div").append(xhr.responseText);
         },
       cache: false,
       contentType: false,
       processData: false
   });

    e.preventDefault(); // prevent actual form submit and page reload
});

function drawTable(data) {
    for (var i = 0; i < data.length; i++) {
        drawRow(data[i]);
    }
}

function drawRow(rowData) {
    var row = $("<tr />")
    $("#shopTable").append(row);
    row.append($("<td>" + rowData.shopId + "</td>"));
    row.append($("<td>" + rowData.startDate + "</td>"));
    row.append($("<td>" + rowData.endDate + "</td>"));
    console.log(row);
}
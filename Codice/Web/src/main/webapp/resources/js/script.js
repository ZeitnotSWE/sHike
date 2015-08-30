$(document).ready(function() {
    /* cache td elements to improve search performance*/
    var $rows=$(".table tbody>tr"), $cells=$rows.children();
    // Write on keyup event of keyword input element
    $("#inputFilter").keyup(function() {
        var term = $(this).val()
        // When value of the input is not blank
        if(term != "") {
            // Show only matching TR, hide rest of them
            $rows.hide();
            $cells.filter(function() {
                return $(this).text().toLowerCase().indexOf(term) > -1;
            }).parent("tr").show();
        } else {
            // When there is no input or clean again, show everything back
            $rows.show();
        }
        
    });
});
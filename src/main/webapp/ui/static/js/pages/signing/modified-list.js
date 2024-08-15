var $ = jQuery.noConflict();

function parseActionButtons() {

    var tableRows = $('#msTable tbody tr');

    tableRows.each(function() {

        var id = $(this).attr('id');

        if (!id) {
            return;
        }

        var lastColumn = $(this).children().last();
        var emList = lastColumn.children();

        emList.each(function(index) {

            if (index === 0) {
                $(this).attr('onclick', 'approveModifiedSigning(' + id + ')');
            } else if (index === 1) {
                $(this).attr('onclick', 'declineModifiedSigning(' + id + ')');
            }
        });
    })
}
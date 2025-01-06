function parseActionButtons(trashVisible) {

    var tableRows = $('#dTable tbody tr');
    var table = $('#dTable').DataTable();

    tableRows.each(function() {

        var id = $(this).attr('id');

        if (!id) {
            return;
        }

        var data = table.row($(this)).data();
        var hasFile = data.ums_hasFile;

        var lastColumn = $(this).children().last();
        var emList = lastColumn.children();

        emList.each(function(index) {

            if (index === 0) {

                if (!hasFile) {
                    $(this).remove();
                }

                $(this).wrap('<a href="/signing/manual/' + id + '/file" target="_blank"></a>');

            } else if (index === 1) {
                $(this).attr('onclick', 'deleteManualSigning(' + id + ')');
            }
        });
    })
}
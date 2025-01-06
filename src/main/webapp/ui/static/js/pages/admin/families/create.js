function parseActionButtons() {
	var tableRows = $('#dTable tbody tr');
	var table = $('#dTable').DataTable();
	
	var info = table.page.info();
	var pageNumber = info.page;
	var pageLength = info.length;
	
	tableRows.each(function(i) {
		
		var lastColumn = $(this).children().last();
		var emList = lastColumn.children();
		
		var rowId = ((pageNumber * pageLength) + i);
		var row = 'row' + rowId;
		$(this).attr('id', row);

		emList.each(function(index) {

			if (index == 0) { // edit
				$(this).attr('data-toggle', 'modal');
				$(this).attr('data-target', '.subfamilies-modal');
				$(this).attr('data-action', 'edit');
				$(this).attr('data-id', rowId);
			} else if(index == 1) { // delete
				$(this).attr('onclick', 'deleteSubFamily(' + rowId + ')');
			}
		});
	});
}
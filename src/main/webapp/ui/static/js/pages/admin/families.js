var $ = jQuery.noConflict();

function parseActionButtons() {
	var tableRows = $('#dTable tbody tr');
	var table = $('#dTable').DataTable();
	
	tableRows.each(function() {
		
		var familyId = $(this).attr('id');
		
		if (!familyId) {
			return;
		}
		
		var lastColumn = $(this).children().last();
		var emList = lastColumn.children();

		emList.each(function(index) {
			
			if(index == 0) { // edit
				$(this).wrap('<a href="/admin/families/' + familyId + '/edit"></a>');
			} else if(index == 1) { // delete
				$(this).attr('onclick', 'deleteFamily(' + familyId + ')');
			}
		});
	});
}
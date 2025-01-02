function parseActionButtons() {
	var tableRows = $('#dTable tbody tr');
	
	tableRows.each(function() {
		
		var projectId = $(this).attr('id');
		
		if (!projectId) {
			return;
		}
		
		var lastColumn = $(this).children().last();
		var emList = lastColumn.children();
		
		emList.each(function(index) {
			
			if (index == 0) { // view
				$(this).attr('onclick', 'showProjectResponsables(' + projectId + ')');
			}
		});
	})
}
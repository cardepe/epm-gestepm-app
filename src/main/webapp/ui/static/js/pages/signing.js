function parseActionButtons(trashVisible) {
	var tableRows = $('#dTable tbody tr');
	
	tableRows.each(function() {
		
		var userSigningId = $(this).attr('id');
		
		if (!userSigningId) {
			return;
		}
		
		var lastColumn = $(this).children().last();
		var emList = lastColumn.children();
		
		emList.each(function(index) {
			
			if (index == 0) { // view
				$(this).wrap('<a href="/signing/' + userSigningId + '"></a>');
			} else if (index == 1) { // remove
				
				if (!trashVisible) {
					$(this).remove();
				}
				
				$(this).attr('onclick', 'deleteSigning(' + userSigningId + ')');
			}
		});
	})
}
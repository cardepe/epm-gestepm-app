var $ = jQuery.noConflict();

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
			
			if (index == 0) { // pdf
				$(this).attr('onclick', 'excelProject(' + projectId + ')');
			} else if (index == 1) { // view
				$(this).wrap('<a href="/projects/' + projectId + '"></a>');
			} else if(index == 2) { // edit
				$(this).remove();
			} else if(index == 3) { // delete
				$(this).attr('onclick', 'deleteProject(' + projectId + ')');
			}
		});
	})
}
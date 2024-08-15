var $ = jQuery.noConflict();

function parseActionButtons() {
	var tableRows = $('#dTable tbody tr');
	
	tableRows.each(function() {
		
		var disShareId = $(this).attr('id');
		
		if (!disShareId) {
			return;
		}
		
		var lastColumn = $(this).children().last();
		var emList = lastColumn.children();
		
		emList.each(function(index) {
			
			if (index == 0) { // view
				$(this).attr('onclick', 'viewShare(' + disShareId + ')');
			}
		});
	})
}
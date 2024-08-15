var $ = jQuery.noConflict();

function parseActionButtons() {
	var tableRows = $('#dTable tbody tr');
	
	tableRows.each(function() {
		
		var timeControlId = $(this).attr('id');
		
		if (!timeControlId) {
			return;
		}
		
		var lastColumn = $(this).children().last();
		var emList = lastColumn.children();	
		var reasonCell = $(this).children().eq(2).children();
		
		var userId = timeControlId.split(';')[0];
		var date = timeControlId.split(';')[1];
		
		emList.each(function(index) {
			
			if (index == 0) { // details
				if (reasonCell.hasClass('green-circle')) {
					$(this).wrap('<a href="/signing/personal/time-control/' + userId + '?date=' + date + '"></a>');
				} else {
					$(this).remove();
				}
			}
		});
	})
}
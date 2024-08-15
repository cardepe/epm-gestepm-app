var $ = jQuery.noConflict();

function parseActionButtons() {
	var tableRows = $('#dTable tbody tr');
	var table = $('#dTable').DataTable();
	
	tableRows.each(function() {
		
		var displacementId = $(this).attr('id');
		
		if (!displacementId) {
			return;
		}
		
		var data = table.row($(this)).data();
		var title = data.di_title;
		var activityCenter = data.di_activityCenter;
		var displacementType = data.di_displacementType;
		var totalTime = data.di_totalTime;
		var lastColumn = $(this).children().last();
		var emList = lastColumn.children();

		emList.each(function(index) {
			
			if(index == 0) { // edit
				$(this).attr('onclick', 'editDisplacement(' + displacementId + ', "' + title + '", "' + activityCenter + '", "' + displacementType + '", "' + totalTime + '")');
			} else if(index == 1) { // delete
				$(this).attr('onclick', 'deleteDisplacement(' + displacementId + ')');
			}
		});
	});
}
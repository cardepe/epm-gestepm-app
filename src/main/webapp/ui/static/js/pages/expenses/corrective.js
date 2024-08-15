var $ = jQuery.noConflict();

function parseActionButtons() {
	var tableRows = $('#dTable tbody tr');
	var table = $('#dTable').DataTable();
	
	tableRows.each(function() {
		
		var correctiveId = $(this).attr('id');
		
		if (!correctiveId) {
			return;
		}
		
		var data = table.row($(this)).data();
		var cost = data.ec_cost;
		var description = data.ec_description;
		
		var lastColumn = $(this).children().last();
		var emList = lastColumn.children();
		
		emList.each(function(index) {
			
			if (index == 0) { // view
				$(this).attr('onclick', 'editCorrective(' + correctiveId + ', ' + cost + ', "' + description + '")');
			} else if(index == 1) { // delete
				$(this).attr('onclick', 'deleteCorrective(' + correctiveId + ')');
			}
		});
	})
}
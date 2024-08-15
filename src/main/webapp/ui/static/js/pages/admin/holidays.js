var $ = jQuery.noConflict();

function parseActionButtons() {
	var tableRows = $('#dTable tbody tr');
	var table = $('#dTable').DataTable();
	
	tableRows.each(function() {
		
		var holidayId = $(this).attr('id');
		
		if (!holidayId) {
			return;
		}
		
		var data = table.row($(this)).data();
		var name = data.ho_name;
		var date = data.ho_date;
		var country = data.ho_country;
		var activityCenter = data.ho_activityCenter;
		var lastColumn = $(this).children().last();
		var emList = lastColumn.children();

		emList.each(function(index) {
			
			if(index == 0) { // edit
				$(this).attr('onclick', 'editHoliday(' + holidayId + ', "' + name + '", "' + date + '", "' + country + '", "' + activityCenter + '")');
			} else if(index == 1) { // delete
				$(this).attr('onclick', 'deleteHoliday(' + holidayId + ')');
			}
		});
	});
}
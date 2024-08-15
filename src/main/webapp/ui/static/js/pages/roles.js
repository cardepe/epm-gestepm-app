var $ = jQuery.noConflict();

function parseActionButtons() {
	var tableRows = $('#rTable tbody tr');
	var table = $('#rTable').DataTable();
	
	tableRows.each(function() {
		
		var roleId = $(this).attr('id');
		
		if (!roleId) {
			return;
		}
		
		var data = table.row($(this)).data();
		var name = data.sr_rol;
		var price = data.sr_price;
		var lastColumn = $(this).children().last();
		var emList = lastColumn.children();
		
		emList.each(function(index) {
			
			if(index == 0) { // edit
				$(this).attr('onclick', 'editRole(' + roleId + ', "' + name + '", ' + price + ')');
			} else if(index == 1) { // delete
				$(this).attr('onclick', 'deleteRole(' + roleId + ')');
			}
		});
	})
}
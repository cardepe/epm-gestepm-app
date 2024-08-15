var $ = jQuery.noConflict();

function parseActionButtons(roleId) {
	var tableRows = $('#uTable tbody tr');
	
	tableRows.each(function() {
		
		var userId = $(this).attr('id');
		
		if (!userId) {
			return;
		}
		
		var lastColumn = $(this).children().last();
		var emList = lastColumn.children();
		
		emList.each(function(index) {
			
			if (index == 0) { // pdf
				$(this).remove();
			} else if (index == 1) { // view
				$(this).wrap('<a href="/users/' + userId + '"></a>');
			} else if(index == 2) { // edit
				$(this).remove();
			} else if(index == 3) { // delete
				if (roleId > 4){
					$(this).attr('onclick', 'deleteUser(' + userId + ')');
				} else {
					$(this).remove();
				}
			}
		});
	})
}
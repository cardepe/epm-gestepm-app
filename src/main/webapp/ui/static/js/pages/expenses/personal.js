var $ = jQuery.noConflict();

function parseActionButtons() {
	var tableRows = $('#dTable tbody tr');
	
	tableRows.each(function() {
		
		var expenseId = $(this).attr('id');
		
		if (!expenseId) {
			return;
		}
		
		var status = jQuery($(this).children().get(3));
		var lastColumn = $(this).children().last();
		var emList = lastColumn.children();
		
		emList.each(function(index) {
			
			if (index == 0) { // pdf
				$(this).wrap('<a href="/expenses/' + expenseId + '/pdf"></a>');
			} else if (index == 1) { // view
				
				$(this).wrap('<a href="/expenses/personal/view/' + expenseId + '"></a>');
				
			} else if(index == 2) { // edit
				$(this).remove();				
			} else if(index == 3) { // delete
				
				$(this).attr('onclick', 'deleteExpense(' + expenseId + ')');
				
				if (status.children().hasClass('badge-success')) {
					$(this).remove();
				}
			}
		});
	})
}
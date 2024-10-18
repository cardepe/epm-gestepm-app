function parseActionButtons(roleId) {
	const tableRows = $('#dTable tbody tr');

	tableRows.each(function() {
		const userId = $(this).attr('id');
		if (!userId) {
			return;
		}

		const lastColumn = $(this).children().last();
		const emList = lastColumn.children();
		
		emList.each(function(index) {
			
			if (index === 0) {
				$(this).remove();
			} else if (index === 1) {
				$(this).wrap('<a href="/users/' + userId + '"></a>');
			} else if(index === 2) {
				$(this).remove();
			} else if(index === 3) {
				if (roleId > 4){
					$(this).attr('onclick', 'deleteUser(' + userId + ')');
				} else {
					$(this).remove();
				}
			}
		});
	})
}

function filterTable(pageNumber) {
	const userId = $('#usersDropdown').val();
	const state = $('#stateFilterDropdown').val();

	const currentURL = new URL(location.protocol + '//' + location.host + location.pathname);

	if (userId) { currentURL.searchParams.set('userId', userId); }
	if (state) { currentURL.searchParams.set('state', state); }
	if (pageNumber) { currentURL.searchParams.set('pageNumber', pageNumber); }

	window.history.pushState(null, '', currentURL.toString());

	currentURL.pathname = currentURL.pathname + '/dt';

	dTable.ajax.url(currentURL.toString()).load();
}

function resetTable() {
	const filterForm = document.querySelector('[data-table-filter="form"]');
	const inputsSelect = filterForm.querySelectorAll('select');

	inputsSelect.forEach(input => { $(input).val('').trigger('change'); })

	dTable.ajax.url('/users/dt').load();
	window.history.replaceState({ }, '', '/users');
}
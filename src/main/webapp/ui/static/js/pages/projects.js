const $ = jQuery.noConflict();

function parseActionButtons() {
	const tableRows = $('#dTable tbody tr');

	tableRows.each(function() {
		const projectId = $(this).attr('id');
		if (!projectId) {
			return;
		}

		const lastColumn = $(this).children().last();
		const emList = lastColumn.children();

		emList.each(function(index) {
			
			if (index === 0) { // pdf
				$(this).attr('onclick', 'excelProject(' + projectId + ')');
			} else if (index === 1) { // view
				$(this).wrap('<a href="/projects/' + projectId + '"></a>');
			} else if(index === 2) { // edit
				$(this).remove();
			} else if(index === 3) { // delete
				$(this).attr('onclick', 'deleteProject(' + projectId + ')');
			}
		});
	})
}

function filterTable(pageNumber) {
	const projectId = $('#projectDropdown').val();
	const responsibleId = $('#responsibleDropdown').val();
	const isStation = $('#checkboxIsStation').prop('checked') ? 1 : null;

	const currentURL = new URL(location.protocol + '//' + location.host + location.pathname);

	if (projectId) { currentURL.searchParams.set('projectId', projectId); }
	if (responsibleId) { currentURL.searchParams.set('responsibleId', responsibleId); }
	if (isStation) { currentURL.searchParams.set('station', isStation.toString()); }
	if (pageNumber) { currentURL.searchParams.set('pageNumber', pageNumber); }

	window.history.pushState(null, '', currentURL.toString());

	currentURL.pathname = currentURL.pathname + '/dt';

	dTable.ajax.url(currentURL.toString()).load();
}

function resetTable() {
	const filterForm = document.querySelector('[data-table-filter="form"]');
	const inputsCheckbox = filterForm.querySelectorAll('input[type="checkbox"]');
	const inputsSelect = filterForm.querySelectorAll('select');

	inputsCheckbox.forEach(input => { $(input).prop('checked', false); })
	inputsSelect.forEach(input => { $(input).val('').trigger('change'); })

	dTable.ajax.url('/projects/dt').load();
	window.history.replaceState({ }, '', '/projects');
}
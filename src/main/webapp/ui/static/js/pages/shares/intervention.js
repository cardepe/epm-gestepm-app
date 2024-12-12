var $ = jQuery.noConflict();

function parseId(data) {
	return data.split('_')[0];
}

function parseActionButtons(userRole, userSigningState, hasPrivileges, projectId) {
	var tableRows = $('#dTable tbody tr');
	var table = $('#dTable').DataTable();

	tableRows.each(function() {
		
		var intShareId = $(this).attr('id');		
		
		if (!intShareId) {
			return;
		}
		
		var id = intShareId.split('_')[0];
		var shareType = intShareId.split('_')[1];
		
		var data = table.row($(this)).data();
		
		var lastColumn = $(this).children().last();
		var emList = lastColumn.children();
		
		emList.each(function(index) {
			
			if (index == 0) { // view
				
				if (shareType === 'ips' || shareType === 'cs' || shareType === 'ws') {
					$(this).remove();
				} else if (shareType === 'is') {
					$(this).wrap('<a href="/shares/no-programmed/' + id + '"></a>');
				}
				
			} else if (index == 1) { // calendar (close)
				
				if (shareType === 'is') {
					$(this).remove();
				} else {
					
					if (data.st_endDate) {
						$(this).remove();
					} else {
						
						if (!hasPrivileges && (!userSigningState || projectId !== data.st_projectId)) {
							$(this).attr('disabled', 'disabled');
						} else {
							$(this).attr('onclick', 'closeShare(' + id + ', "' + shareType + '", "' + data.st_startDate + '")');
						}						
					}					
				}
			} else if (index == 2) {
				
				if (shareType === 'is') {
					$(this).remove();
				} else {
					
					if (!data.st_endDate) {
						$(this).remove();
					} else {
						
						if (shareType === 'ips') {
							$(this).wrap('<a href="/shares/intervention/programmed/' + id + '/pdf"></a>');
						} else if (shareType === 'cs') {
							$(this).wrap('<a href="/shares/intervention/construction/' + id + '/pdf"></a>');
						} else if (shareType === 'ws') {
							$(this).wrap('<a href="/shares/work/' + id + '/pdf"></a>');
						}
					}					
				}
			} else if (index == 3) {
				
				if (userRole !== 'ROLE_JEFE_PROYECTO' && userRole !== 'ROLE_ADMIN') {
					$(this).remove();
				}
				
				$(this).attr('onclick', 'deleteFault(' + id + ', "' + shareType + '")');
			}
		});
	})
}

function filterShares(pageNumber) {
	const id = $('#shareIdInput').val();
	const type = $('#sharesTypeDropdown').val();
	const project = $('#projectFilterDropdown').val();
	const progress = $('#stateFilterDropdown').val();
	const user = $('#usersDropdown').val();

	const currentURL = new URL(location.protocol + '//' + location.host + location.pathname);

	if (id) { currentURL.searchParams.set('id', id); }
	if (type) { currentURL.searchParams.set('type', type); }
	if (project) { currentURL.searchParams.set('project', project); }
	if (progress) { currentURL.searchParams.set('progress', progress); }
	if (user) { currentURL.searchParams.set('user', user); }
	if (pageNumber) { currentURL.searchParams.set('pageNumber', pageNumber); }

	window.history.pushState(null, '', currentURL.toString());

	currentURL.pathname = currentURL.pathname + '/dt';

	dTable.ajax.url(currentURL.toString()).load();
}

function createNoProgrammedShare(userId, projectId) {
	showLoading();

	axios.post('/v1/shares/no-programmed', {
		userId: userId,
		projectId: projectId
	}).then((response) => {
		const shareId = response.data.data.id;
		window.location.href = '/shares//no-programmed/' + shareId;
	}).catch(error => showNotify(error, 'danger'))
		.finally(() => {
			hideLoading();
		});
}
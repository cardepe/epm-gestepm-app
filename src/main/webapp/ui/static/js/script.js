let messages;

function getI18n(locale) {

	let messages;

	$.ajax({
		url: '/ui/static/js/i18n/' + locale + '.json',
		dataType: 'json',
		async: false,
		success: function (data) {
			messages = data;
		}
	});

	return messages;
}

function parseStatus(data) {

	if (data === 'Pendiente' || data === 'Approbation en attente') {
		return '<div class="badge badge-secondary">' + data + '</div>';
	} else if (data === 'Aprobado por administración' || data === 'Approuvé') {
		return '<span class="badge badge-primary">' + data + '</span>';
	} else if (data === 'Aprobado' || data === 'Payé') {
		return '<span class="badge badge-success">' + data + '</span>';
	} else if (data === 'Cancelado' || data === 'Rejeté') {
		return '<span class="badge badge-danger">' + data + '</span>';
	}
	
	return '';
}

function showNotify(msg, type) {
	$.notify({ message: msg }, { type: type });
}

function isValidEmailAddress(emailAddress) {
    let pattern = new RegExp(/^(("[\w-+\s]+")|([\w-+]+(?:\.[\w-+]+)*)|("[\w-+\s]+")([\w-+]+(?:\.[\w-+]+)*))(@((?:[\w-+]+\.)*\w[\w-+]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][\d]\.|1[\d]{2}\.|[\d]{1,2}\.))((25[0-5]|2[0-4][\d]|1[\d]{2}|[\d]{1,2})\.){2}(25[0-5]|2[0-4][\d]|1[\d]{2}|[\d]{1,2})\]?$)/i);
    return pattern.test(emailAddress);
}

function showLoading() {
	$('.loading').show();
}

function hideLoading() {
	$('.loading').hide();
}

function timePassed(time) {
	let[hours, mins] = time.split(":");
	return parseInt(hours) * 60 + parseInt(mins);
}

function minutesToTime(minutes) {
	return new Date(minutes * 60 * 1000).toISOString().substring(11, 16);
}

function loadDisplacements(activityCenterId, callback) {

	axios.get('/v1/displacements/', { params: { activityCenterIds: activityCenterId, order: 'ASC', orderBy: 'name' }}).then((response) => {

		let html = '<option disabled selected="selected">-</option>';

		response.data.data.forEach(displacement => {

			let displacementType = formatType(displacement.type);
			let totalTime = minutesToTime(displacement.totalTime);

			html += '<option value="' + displacement.id + '">' + displacement.name + ' (' + displacementType + ' - ' + totalTime + ')</option>';
		})

		callback(html);
	});
}

window.addEventListener("load", function(){
	messages = getI18n('es');
	hideLoading();
});


let messages;
let $ = jQuery.noConflict();

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

function parseDate(date, format) {
	return moment(date).format(format)
}

function parseStatus(data) {

	if (data === 'status.pending') {
		return '<div class="badge badge-secondary">' + messages.status.pending + '</div>';
	} else if (data === 'status.approved') {
		return '<span class="badge badge-primary">' + messages.status.approved + '</span>';
	} else if (data === 'status.paid') {
		return '<span class="badge badge-success">' + messages.status.paid + '</span>';
	} else if (data === 'status.rejected') {
		return '<span class="badge badge-danger">' + messages.status.rejected + '</span>';
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

$(document).ready(function () {
	messages = getI18n('es');
	hideLoading();
});

/* NEW */

function disableForm(formId) {
	const form = document.querySelector(formId);
	const elements = form.querySelectorAll('input, textarea, select');
	elements.forEach(element => element.disabled = true);

	if (typeof $.fn.selectpicker === 'function') {
		const selects = form.querySelectorAll('select');
		selects.forEach(select => $(select).selectpicker('refresh'));
	}
}

function toBase64(file) {
	return new Promise((resolve, reject) => {
		const reader = new FileReader();
		reader.onload = () => resolve(reader.result.split(",")[1]);
		reader.onerror = (error) => reject(error);
		reader.readAsDataURL(file);
	});
}
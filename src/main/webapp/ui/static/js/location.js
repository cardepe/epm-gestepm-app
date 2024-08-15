function showLocation(position) {

    const latitude = position.coords.latitude;
    const longitude = position.coords.longitude;

    $('#geolocationInput').val(latitude + '+' + longitude);
}

function errorHandler(err) {
    console.log("[navigator.geolocation.getCurrentPosition] Error code: " + err.code);
}

function getLocation() {

    if (navigator.geolocation) {

        const options = {timeout: 60000};
        navigator.geolocation.getCurrentPosition(showLocation, errorHandler, options);

    } else {
        showNotify('Sorry, browser does not support geolocation!', 'danger');
    }
}
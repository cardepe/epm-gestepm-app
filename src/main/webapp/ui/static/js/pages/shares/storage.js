function addShareStorage(shareId, filters) {
    sessionStorage.setItem(shareId, filters);
}

function getShareStorage(shareId) {
    return sessionStorage.getItem(shareId);
}

function removeShareStorage(shareId) {
    return sessionStorage.removeItem(shareId);
}

function getLastPageQueryParams() {
    return document.referrer.split('?')[1];
}

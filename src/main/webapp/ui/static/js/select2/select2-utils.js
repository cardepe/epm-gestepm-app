function createSelect2(input, endpoint, customParams, customOrderBy, getCustomName, modal) {
    return input.select2({
        allowClear: true,
        placeholder: messages.select2.placeholder,
        dropdownCssClass: 'selectStyle',
        dropdownParent: modal ? $('#' + modal) : null,
        ajax: {
            url: endpoint,
            dataType: 'json',
            data: function (params) {
                const query = {
                    order: 'ASC',
                    orderBy: customOrderBy ? customOrderBy : 'name',
                    offset: (params.page - 1) * 10 || 0,
                    limit: 10,
                    ...customParams
                };

                if (params.term) {
                    query.nameContains = params.term || '';
                }

                return query;
            },
            // headers: {
            //     'Authorization': 'Bearer ' + getToken()
            // },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {
                    results: data.data.map(item => ({ id: item.id, text: getCustomName ? getCustomName(item) : item.name })),
                    pagination: { more: data.metadata.pagination.itemsTotal > (params.page * 10) }
                };
            }
        }
    })
}

function createBasicSelect2(input, modal) {
    return input.select2({
        allowClear: true,
        dropdownCssClass: 'selectStyle',
        dropdownParent: modal ? $('#' + modal) : null,
        placeholder: messages.select2.placeholder
    })
}
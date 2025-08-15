const endpoint = '/v1/signings/warehouse';

let currentWarehouse = null;

function initializeDataTables() {
    const columns = ['id', 'project.name', 'startedAt', 'closedAT'];

    const actions = [
        {
            action: 'view'
            , url: 'signing/warehouse/{id}'
            , permission: 'edit_warehouse_signings'
        }
    ];

    const expand = ['project'];

    const filter = [{'userIds': userId, 'current': false}];

    const columnDef = [
        {
            targets: [2, 3]
            , render: function(data) {
                return data ? moment(data).format('DD-MM-YYYY HH:mm') : null;
            }
        }
        , {
            targets: 4
            , render: function(data) {
                return new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' }).format(data);
            }
        }
    ];
}
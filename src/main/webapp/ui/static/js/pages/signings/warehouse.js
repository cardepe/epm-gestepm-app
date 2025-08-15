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
}
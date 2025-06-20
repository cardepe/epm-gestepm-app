$(document).ready(function() {
    initializeDataTables();
});

function initializeDataTables() {
    let columns = ['id', 'user.name', 'startDate', 'endDate', 'type', 'id']
    let endpoint = '/v1/shares';
    let actions = [
        {
            action: 'file-pdf',
            url: '/v1{detailUrl}/export',
            permission: 'edit_shares',
            conditionGroups: [
                {
                    conditions: [
                        { key: 'shareType', value: [ 'CONSTRUCTION_SHARES', 'INSPECTIONS', 'PROGRAMMED_SHARES', 'WORK_SHARES' ], operation: '===' },
                        { key: 'endDate', value: [ undefined ], operation: '!==' }
                    ]
                }
            ]
        },
        {
            action: 'file',
            url: '/v1{detailUrl}/export-materials',
            permission: 'edit_shares',
            conditionGroups: [
                {
                    conditions: [
                        { key: 'shareType', value: [ 'INSPECTIONS' ], operation: '===' },
                        { key: 'endDate', value: [ undefined ], operation: '!==' }
                    ]
                }
            ]
        },
        {
            action: 'view',
            url: '{detailUrl}'
        },
        {
            action: 'delete',
            permission: 'edit_shares'
        }
    ]
    let expand = ['user,project']
    let filters = [{'projectIds': currentProject.id}]
    let columnsDef = [
        {
            targets: [2, 3],
            render: function (data) {
                return data ? moment(data).format('DD-MM-YYYY HH:mm') : null;
            }
        },
        {
            targets: 4,
            render: function (data) {
                return parseShareType(data);
            }
        }
    ]

    let signingsDataTable = new CustomDataTable(columns, endpoint, null, actions, expand, filters, columnsDef);
    dTable = createDataTable('#dTable', signingsDataTable, locale);
    signingsDataTable.setCurrentTable(dTable);
}
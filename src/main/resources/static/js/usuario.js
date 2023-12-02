$(document).ready(function () {
    dataTable = $('#data-table').DataTable({
        responsive: true,
        dom: 'Blfrtip',
        buttons: [
            {
                extend:    'pdf',
                text:      '<i class="fas fa-file-pdf"></i> ',
                titleAttr: 'Exportar para PDF',
                className: 'btn btn-danger',
                orientation:'landscape',
                title: function () { return 'Gestão de Imóveis - Usuários'; },
                exportOptions: {
                    columns: ':visible :not(:last-child)'
                },
                customize: function (doc) {
                    doc.content[1].table.widths = "*";
                }
            },
            {
                extend:    'excel',
                text:      '<i class="fas fa-file-excel"></i> ',
                titleAttr: 'Exportar para Excel',
                className: 'btn btn-success',
                orientation:'landscape',
                title: function () { return 'Gestão de Imóveis - Usuários'; },
                exportOptions: {
                    columns: ':visible :not(:last-child)'
                }
            },
            {
                extend:    'csv',
                text:      '<i class="fas fa-file-csv"></i> ',
                titleAttr: 'Exportar para CSV',
                className: 'btn btn-info',
                exportOptions: {
                    columns: ':visible :not(:last-child)'
                }
            },
            {
                extend:    'print',
                text:      '<i class="fa fa-print"></i> ',
                titleAttr: 'Imprimir',
                className: 'btn btn-info',
                orientation:'landscape',
                title: function () { return 'Gestão de Imóveis - Usuários'; },
                exportOptions: {
                    columns: ':visible :not(:last-child)'
               }
            }
        ],
        ajax: {
            url: '/api/usuario/usuarios',
            type: 'GET',
            datatype: 'json',
            dataSrc: ''
        },
        initComplete: function() {
            $('#data-table_length').each(function () {
                $(this).append('<a class="btn btn-success btn-add" href="/usuario/cadastro">' +
                '<i class="fas fa-plus"></i> Adicionar</a>');
            });
            $('[type=search]').each(function () {
                $(this).after('<i class="fa fa-search"></i>');
            });
        },
        columns: [
            { "data": "id" },
            { "data": "name" },
            { "data": "username" },
            {
                "data": "roles", "render": function (data) {
                    if (data == 'ROLE_ADMIN') {
                        return '<span class="badge bg-primary">Administrador</span>'
                    } else {
                        return '<span class="badge bg-secondary">Usuário</span>'
                    }
                }
            },
            {
                "data": "active", "render": function (data) {
                    if (data) {
                        return '<span class="badge bg-success">Ativo</span>'
                    } else {
                        return '<span class="badge bg-danger">Inativo</span>'
                    }
                }
            },
            {
                "data": null, "render": function (data) {
                    return '<div class="actions"><a class="btn btn-primary" href="/usuario/editar/'+data.id+'">'+
                    '<i class="fas fa-pen"></i> Editar</a><a class="btn btn-danger" '+
                    'onclick="return confirm(\'Tem certeza que deseja excluir esse registro?\');" '+
                    'href="/usuario/excluir/'+data.id+'"><i class="fas fa-trash"></i> Excluir</a></div>'
                }
            }
        ],
        language: {
            "url": "../json/Portuguese.json",
            "searchPlaceholder": "Pesquisar"
        }
    });
});

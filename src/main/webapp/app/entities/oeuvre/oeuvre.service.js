(function() {
    'use strict';
    angular
        .module('bibliothequeApp')
        .factory('Oeuvre', Oeuvre);

    Oeuvre.$inject = ['$resource'];

    function Oeuvre ($resource) {
        var resourceUrl =  'api/oeuvres/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

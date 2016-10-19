(function() {
    'use strict';
    angular
        .module('bibliothequeApp')
        .factory('Emprunt', Emprunt);

    Emprunt.$inject = ['$resource', 'DateUtils'];

    function Emprunt ($resource, DateUtils) {
        var resourceUrl =  'api/emprunts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.debut = DateUtils.convertDateTimeFromServer(data.debut);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('bibliothequeApp')
        .factory('Reservation', Reservation);

    Reservation.$inject = ['$resource', 'DateUtils'];

    function Reservation ($resource, DateUtils) {
        var resourceUrl =  'api/reservations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateDemande = DateUtils.convertDateTimeFromServer(data.dateDemande);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('bibliothequeApp')
        .factory('Reservation', Reservation);

    Reservation.$inject = ['$resource', 'DateUtils'];

    function Reservation ($resource, DateUtils) {
        var resourceUrl =  'api/reservations/:id/';

        return $resource(resourceUrl, {
            usagerId: '@usagerId',
            oeuvreId: '@oeuvreId'
        },{
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
            'update': { method:'PUT' },


            'isReserved': {method: 'GET',
                isArray:false,
                url:'api/reservations/:id/:usagerId/:oeuvreId/',
                transformResponse: function (data) {return {reserved: angular.fromJson(data)}}
            }

        });
    }
})();

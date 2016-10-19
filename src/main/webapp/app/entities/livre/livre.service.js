(function() {
    'use strict';
    angular
        .module('bibliothequeApp')
        .factory('Livre', Livre);

    Livre.$inject = ['$resource', 'DateUtils'];

    function Livre ($resource, DateUtils) {
        var resourceUrl =  'api/livres/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateEdition = DateUtils.convertLocalDateFromServer(data.dateEdition);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateEdition = DateUtils.convertLocalDateToServer(copy.dateEdition);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateEdition = DateUtils.convertLocalDateToServer(copy.dateEdition);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();

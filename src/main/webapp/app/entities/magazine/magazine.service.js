(function() {
    'use strict';
    angular
        .module('bibliothequeApp')
        .factory('Magazine', Magazine);

    Magazine.$inject = ['$resource', 'DateUtils'];

    function Magazine ($resource, DateUtils) {
        var resourceUrl =  'api/magazines/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.parution = DateUtils.convertLocalDateFromServer(data.parution);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.parution = DateUtils.convertLocalDateToServer(copy.parution);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.parution = DateUtils.convertLocalDateToServer(copy.parution);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();

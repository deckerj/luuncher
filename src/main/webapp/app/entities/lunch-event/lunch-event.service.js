(function() {
    'use strict';
    angular
        .module('luuncherApp')
        .factory('LunchEvent', LunchEvent);

    LunchEvent.$inject = ['$resource', 'DateUtils'];

    function LunchEvent ($resource, DateUtils) {
        var resourceUrl =  'api/lunch-events/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.startDate = DateUtils.convertLocalDateFromServer(data.startDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocalDateToServer(data.startDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.startDate = DateUtils.convertLocalDateToServer(data.startDate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();

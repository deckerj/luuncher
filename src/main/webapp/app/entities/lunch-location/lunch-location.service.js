(function() {
    'use strict';
    angular
        .module('luuncherApp')
        .factory('LunchLocation', LunchLocation);

    LunchLocation.$inject = ['$resource'];

    function LunchLocation ($resource) {
        var resourceUrl =  'api/lunch-locations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

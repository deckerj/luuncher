(function() {
    'use strict';
    angular
        .module('luuncherApp')
        .factory('LunchGroup', LunchGroup);

    LunchGroup.$inject = ['$resource'];

    function LunchGroup ($resource) {
        var resourceUrl =  'api/lunch-groups/:id';

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

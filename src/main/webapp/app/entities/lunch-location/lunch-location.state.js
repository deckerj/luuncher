(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lunch-location', {
            parent: 'entity',
            url: '/lunch-location',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'luuncherApp.lunchLocation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lunch-location/lunch-locations.html',
                    controller: 'LunchLocationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lunchLocation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lunch-location-detail', {
            parent: 'entity',
            url: '/lunch-location/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'luuncherApp.lunchLocation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lunch-location/lunch-location-detail.html',
                    controller: 'LunchLocationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lunchLocation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LunchLocation', function($stateParams, LunchLocation) {
                    return LunchLocation.get({id : $stateParams.id});
                }]
            }
        })
        .state('lunch-location.new', {
            parent: 'lunch-location',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lunch-location/lunch-location-dialog.html',
                    controller: 'LunchLocationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                lunchLocationId: null,
                                lunchLocationName: null,
                                streetAddress: null,
                                postalCode: null,
                                city: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lunch-location', null, { reload: true });
                }, function() {
                    $state.go('lunch-location');
                });
            }]
        })
        .state('lunch-location.edit', {
            parent: 'lunch-location',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lunch-location/lunch-location-dialog.html',
                    controller: 'LunchLocationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LunchLocation', function(LunchLocation) {
                            return LunchLocation.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('lunch-location', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lunch-location.delete', {
            parent: 'lunch-location',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lunch-location/lunch-location-delete-dialog.html',
                    controller: 'LunchLocationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LunchLocation', function(LunchLocation) {
                            return LunchLocation.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('lunch-location', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

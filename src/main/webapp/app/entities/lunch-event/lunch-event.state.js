(function() {
    'use strict';

    angular
        .module('luuncherApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lunch-event', {
            parent: 'entity',
            url: '/lunch-event',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'luuncherApp.lunchEvent.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lunch-event/lunch-events.html',
                    controller: 'LunchEventController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lunchEvent');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lunch-event-detail', {
            parent: 'entity',
            url: '/lunch-event/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'luuncherApp.lunchEvent.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lunch-event/lunch-event-detail.html',
                    controller: 'LunchEventDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lunchEvent');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LunchEvent', function($stateParams, LunchEvent) {
                    return LunchEvent.get({id : $stateParams.id});
                }]
            }
        })
        .state('lunch-event.new', {
            parent: 'lunch-event',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lunch-event/lunch-event-dialog.html',
                    controller: 'LunchEventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lunch-event', null, { reload: true });
                }, function() {
                    $state.go('lunch-event');
                });
            }]
        })
        .state('lunch-event.edit', {
            parent: 'lunch-event',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lunch-event/lunch-event-dialog.html',
                    controller: 'LunchEventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LunchEvent', function(LunchEvent) {
                            return LunchEvent.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('lunch-event', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lunch-event.delete', {
            parent: 'lunch-event',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lunch-event/lunch-event-delete-dialog.html',
                    controller: 'LunchEventDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LunchEvent', function(LunchEvent) {
                            return LunchEvent.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('lunch-event', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

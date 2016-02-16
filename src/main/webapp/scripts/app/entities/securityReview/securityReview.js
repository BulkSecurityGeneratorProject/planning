'use strict';

angular.module('planningApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('securityReview', {
                parent: 'entity',
                url: '/securityReviews',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SecurityReviews'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/securityReview/securityReviews.html',
                        controller: 'SecurityReviewController'
                    }
                },
                resolve: {
                }
            })
            .state('securityReview.detail', {
                parent: 'entity',
                url: '/securityReview/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SecurityReview'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/securityReview/securityReview-detail.html',
                        controller: 'SecurityReviewDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'SecurityReview', function($stateParams, SecurityReview) {
                        return SecurityReview.get({id : $stateParams.id});
                    }]
                }
            })
            .state('securityReview.new', {
                parent: 'securityReview',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/securityReview/securityReview-dialog.html',
                        controller: 'SecurityReviewDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    reviewDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('securityReview', null, { reload: true });
                    }, function() {
                        $state.go('securityReview');
                    })
                }]
            })
            .state('securityReview.edit', {
                parent: 'securityReview',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/securityReview/securityReview-dialog.html',
                        controller: 'SecurityReviewDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SecurityReview', function(SecurityReview) {
                                return SecurityReview.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('securityReview', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('securityReview.delete', {
                parent: 'securityReview',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/securityReview/securityReview-delete-dialog.html',
                        controller: 'SecurityReviewDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SecurityReview', function(SecurityReview) {
                                return SecurityReview.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('securityReview', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

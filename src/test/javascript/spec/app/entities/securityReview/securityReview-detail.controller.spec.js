'use strict';

describe('Controller Tests', function() {

    describe('SecurityReview Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSecurityReview, MockNote, MockUser, MockLink;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSecurityReview = jasmine.createSpy('MockSecurityReview');
            MockNote = jasmine.createSpy('MockNote');
            MockUser = jasmine.createSpy('MockUser');
            MockLink = jasmine.createSpy('MockLink');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SecurityReview': MockSecurityReview,
                'Note': MockNote,
                'User': MockUser,
                'Link': MockLink
            };
            createController = function() {
                $injector.get('$controller')("SecurityReviewDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'planningApp:securityReviewUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

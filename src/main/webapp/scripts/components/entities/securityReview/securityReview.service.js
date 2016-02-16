'use strict';

angular.module('planningApp')
    .factory('SecurityReview', function ($resource, DateUtils) {
        return $resource('api/securityReviews/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.reviewDate = DateUtils.convertLocaleDateFromServer(data.reviewDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.reviewDate = DateUtils.convertLocaleDateToServer(data.reviewDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.reviewDate = DateUtils.convertLocaleDateToServer(data.reviewDate);
                    return angular.toJson(data);
                }
            }
        });
    });

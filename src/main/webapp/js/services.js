'use strict';

var services = angular.module('services', ['ngResource']);

services.factory('Contact', ['$resource', function($resource) {
	return $resource('rest/contacts/:id', {id:'@id'}, {
		query: {
			method: 'GET',
			params: {id:'list'},
			isArray: true
		},
		update: {
			method: 'PUT'
		}
	});
}]);

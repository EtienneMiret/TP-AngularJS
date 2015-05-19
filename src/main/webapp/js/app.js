'use strict';

var contactApp = angular.module('contacts', ['ngRoute', 'controllers', 'directives', 'filters']);

contactApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider.
		when('/', {
			templateUrl: 'views/contact-list.html',
			controller: 'ContactListCtrl'
		}).
		when('/new', {
			templateUrl: 'views/contact-edit.html',
			controller: 'ContactNewCtrl'
		}).
		when('/:id/edit', {
			templateUrl: 'views/contact-edit.html',
			controller: 'ContactEditCtrl'
		}).
		when('/:id', {
			templateUrl: 'views/contact-detail.html',
			controller: 'ContactDetailCtrl'
		}).
		otherwise({
			redirectTo: '/'
		});
}]);

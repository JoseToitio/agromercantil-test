(function() {
    'use strict';

    angular
        .module('truckManagementApp', ['ngRoute'])
        .config(config);

    config.$inject = ['$routeProvider', '$locationProvider'];

    function config($routeProvider, $locationProvider) {

        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
        });

        $routeProvider
            .when('/', {
                templateUrl: '/views/home.html'
            })
            .when('/trucks', {
                templateUrl: '/views/truck-list.html',
                controller: 'TruckListController',
                controllerAs: 'vm'
            })
            .when('/trucks/new', {
                templateUrl: '/views/truck-form.html',
                controller: 'TruckFormController',
                controllerAs: 'vm'
            })
            .when('/trucks/edit/:id', {
                templateUrl: '/views/truck-form.html',
                controller: 'TruckFormController',
                controllerAs: 'vm'
            })
            .when('/fipe-test', {
                templateUrl: '/views/fipe-test.html',
                controller: 'FipeTestController',
                controllerAs: 'vm'
            })
            .when('/fipe-help', {
                templateUrl: '/views/fipe-help.html'
            })
            .otherwise({
                redirectTo: '/'
            });
    }
})();

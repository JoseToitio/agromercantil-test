(function() {
    'use strict';

    angular
        .module('truckManagementApp')
        .controller('TruckListController', TruckListController);

    TruckListController.$inject = ['TruckService', '$location'];

    function TruckListController(TruckService, $location) {
        var vm = this;

        vm.trucks = [];
        vm.loading = true;
        vm.error = null;
        vm.editTruck = editTruck;

        activate();

        function activate() {
            loadTrucks();
        }

        /**
         * Carrega a lista de caminhões
         */
        function loadTrucks() {
            vm.loading = true;
            vm.error = null;

            TruckService.findAll()
                .then(function(trucks) {
                    vm.trucks = trucks;
                })
                .catch(function(error) {
                    vm.error = 'Erro ao carregar a lista de caminhões. Por favor, tente novamente.';
                    console.error('Erro ao carregar caminhões:', error);
                })
                .finally(function() {
                    vm.loading = false;
                });
        }

        /**
         * Redireciona para a tela de edição de um caminhão
         * @param {number} id ID do caminhão
         */
        function editTruck(id) {
            $location.path('/trucks/edit/' + id);
        }
    }
})();
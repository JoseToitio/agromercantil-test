(function() {
    'use strict';

    angular
        .module('truckManagementApp')
        .controller('TruckFormController', TruckFormController);

    TruckFormController.$inject = ['TruckService', 'FipeService', '$routeParams', '$location'];

    function TruckFormController(TruckService, FipeService, $routeParams, $location) {
        var vm = this;

        vm.truck = {};
        vm.isEditing = false;
        vm.loadingTruck = false;
        vm.loadingBrands = false;
        vm.loadingModels = false;
        vm.loadingYears = false;
        vm.saving = false;
        vm.validating = false;
        vm.error = null;
        vm.success = null;

        vm.brands = [];
        vm.models = [];
        vm.years = [];

        vm.saveTruck = saveTruck;
        vm.cancel = cancel;
        vm.validateFipe = validateFipe;
        vm.loadModels = loadModels;
        vm.loadYears = loadYears;

        activate();

        function activate() {
            loadBrands();
            var truckId = $routeParams.id;
            if (truckId) {
                vm.isEditing = true;
                loadTruck(truckId);
            }
        }

        function loadBrands() {
            vm.loadingBrands = true;
            vm.error = null;
            FipeService.getBrands()
                .then(function(brands) { vm.brands = brands; })
                .catch(function(error) { vm.error = 'Erro ao carregar marcas'; console.error(error); })
                .finally(function() { vm.loadingBrands = false; });
        }

        function loadModels() {
            if (!vm.truck.brand) return;

            vm.loadingModels = true;
            vm.models = [];
            vm.years = [];
            vm.truck.model = null;
            vm.truck.manufacturingYear = null;
            vm.truck.fipePrice = null;

            FipeService.getModels(vm.truck.brand)
                .then(function(models) { vm.models = models; })
                .catch(function(error) { vm.error = 'Erro ao carregar modelos'; console.error(error); })
                .finally(function() { vm.loadingModels = false; });
        }

        function loadYears() {
            if (!vm.truck.brand || !vm.truck.model) return;

            vm.loadingYears = true;
            vm.years = [];
            vm.truck.manufacturingYear = null;
            vm.truck.fipePrice = null;

            FipeService.getYears(vm.truck.brand, vm.truck.model)
                .then(function(years) { vm.years = years; })
                .catch(function(error) { vm.error = 'Erro ao carregar anos'; console.error(error); })
                .finally(function() { vm.loadingYears = false; });
        }

        function loadTruck(id) {
            vm.loadingTruck = true;
            vm.error = null;

            TruckService.findById(id)
                .then(function(truck) { vm.truck = truck; })
                .catch(function(error) { vm.error = 'Erro ao carregar dados do caminhão'; console.error(error); })
                .finally(function() { vm.loadingTruck = false; });
        }

        function saveTruck() {
            vm.saving = true;
            vm.error = null;
            vm.success = null;

            var payload = {
                licensePlate: vm.truck.licensePlate,
                brand: vm.truck.brand,
                model: vm.truck.model,
                manufacturingYear: vm.truck.manufacturingYear
            };

            var promise = vm.isEditing ? TruckService.update(vm.truck.id, payload) : TruckService.save(payload);

            promise
                .then(function(truck) {
                    vm.success = 'Caminhão ' + (vm.isEditing ? 'atualizado' : 'cadastrado') + ' com sucesso!';
                    $location.path('/trucks');
                })
                .catch(function(error) {
                    if (error.status === 409) vm.error = error.data.message || 'Placa já cadastrada.';
                    else if (error.status === 400) vm.error = error.data.message || 'Dados inválidos. Verifique os campos.';
                    else vm.error = 'Erro ao ' + (vm.isEditing ? 'atualizar' : 'cadastrar') + ' caminhão.';
                    console.error(error);
                })
                .finally(function() { vm.saving = false; });
        }

        function validateFipe() {
            if (!vm.truck.brand || !vm.truck.model || !vm.truck.manufacturingYear) {
                vm.error = 'Preencha marca, modelo e ano.';
                return;
            }

            vm.validating = true;
            vm.error = null;

            FipeService.getFipeInfo(vm.truck.brand, vm.truck.model, vm.truck.manufacturingYear)
                .then(function(fipeInfo) {
                    vm.truck.fipePrice = fipeInfo.price;
                    vm.success = 'FIPE validada: R$ ' + fipeInfo.price;
                })
                .catch(function(error) {
                    vm.error = 'Erro ao validar FIPE.';
                    console.error(error);
                })
                .finally(function() { vm.validating = false; });
        }

        function cancel() {
            $location.path('/trucks');
        }
    }
})();

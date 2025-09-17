import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { TruckService } from '../services/truck.service';
import { FipeService } from '../services/fipe.service';

@Component({
  selector: 'app-truck-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './truck-form.html',
})
export class TruckForm implements OnInit {
  form!: FormGroup;

  isEditing = false;
  loadingTruck = false;
  loadingBrands = false;
  loadingModels = false;
  loadingYears = false;
  saving = false;
  validating = false;

  error: string | null = null;
  success: string | null = null;

  brands: any[] = [];
  models: any[] = [];
  years: any[] = [];

  constructor(
    private fb: FormBuilder,
    private truckService: TruckService,
    private fipeService: FipeService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // cria o formulário com validações
    this.form = this.fb.group({
      id: [null],
      licensePlate: ['', [Validators.required, Validators.pattern(/[A-Z]{3}[0-9][0-9A-Z][0-9]{2}/)]],
      brand: ['', Validators.required],
      model: ['', Validators.required],
      manufacturingYear: ['', Validators.required],
      fipePrice: [{ value: '', disabled: true }],
    });

    this.loadBrands();

    const truckId = this.route.snapshot.paramMap.get('id');
    if (truckId) {
      this.isEditing = true;
      this.loadTruck(+truckId);
    }
  }

  loadBrands() {
    this.loadingBrands = true;
    this.fipeService.getBrands().subscribe({
      next: (brands) => (this.brands = brands),
      error: () => (this.error = 'Erro ao carregar marcas'),
      complete: () => (this.loadingBrands = false),
    });
  }

  loadModels() {
    const brand = this.form.value.brand;
    if (!brand) return;

    this.loadingModels = true;
    this.models = [];
    this.years = [];
    this.form.patchValue({ model: '', manufacturingYear: '', fipePrice: '' });

    this.fipeService.getModels(brand).subscribe({
      next: (models) => (this.models = models),
      error: () => (this.error = 'Erro ao carregar modelos'),
      complete: () => (this.loadingModels = false),
    });
  }

  loadYears() {
    const { brand, model } = this.form.value;
    if (!brand || !model) return;

    this.loadingYears = true;
    this.years = [];
    this.form.patchValue({ manufacturingYear: '', fipePrice: '' });

    this.fipeService.getYears(brand, model).subscribe({
      next: (years) => (this.years = years),
      error: () => (this.error = 'Erro ao carregar anos'),
      complete: () => (this.loadingYears = false),
    });
  }

  loadTruck(id: number) {
    this.loadingTruck = true;

    this.truckService.findById(id).subscribe({
      next: (truckFromApi) => {
        // Aplica os valores principais
        this.form.patchValue({
          id: truckFromApi.id,
          licensePlate: truckFromApi.licensePlate,
          fipePrice: truckFromApi.fipePrice.toLocaleString('pt-BR', {
              style: 'currency',
              currency: 'BRL',
            })
        });

        // Carrega marcas
        this.fipeService.getBrands().subscribe({
          next: (brands) => {
            this.brands = brands;

            const brandObj = this.brands.find(b => b.name === truckFromApi.brand);
            if (brandObj) {
              this.form.patchValue({ brand: brandObj.code });

              this.fipeService.getModels(brandObj.code).subscribe({
                next: (models) => {
                  this.models = models;

                  const modelObj = this.models.find(m => m.name === truckFromApi.model);
                  if (modelObj) {
                    this.form.patchValue({ model: modelObj.code });

                    this.fipeService.getYears(brandObj.code, modelObj.code).subscribe({
                      next: (years) => {
                        this.years = years;

                        const yearObj = this.years.find(y => y.name == truckFromApi.manufacturingYear.toString());
                        if (yearObj) {
                          this.form.patchValue({ manufacturingYear: yearObj.code });
                        }
                      }
                    });
                  }
                }
              });
            }
          }
        });
      },
      error: () => (this.error = 'Erro ao carregar caminhão'),
      complete: () => (this.loadingTruck = false),
    });
  }




  saveTruck() {
    if (this.form.invalid) return;

    this.saving = true;
    this.error = null;
    this.success = null;

    const payload = this.form.getRawValue();

    const request = this.isEditing
      ? this.truckService.update(payload.id, payload)
      : this.truckService.save(payload);

    request.subscribe({
      next: () => {
        this.success = this.isEditing ? 'Caminhão atualizado com sucesso!' : 'Caminhão cadastrado com sucesso!';
        this.router.navigate(['/trucks']);
      },
      error: (err) => {
        if (err.status === 409) this.error = err.error.message || 'Placa já cadastrada.';
        else if (err.status === 400) this.error = err.error.message || 'Dados inválidos.';
        else this.error = 'Erro ao salvar caminhão.';
        this.saving = false
      },
      complete: () => (this.saving = false),
    });
  }

  validateFipe() {
    const { brand, model, manufacturingYear } = this.form.value;
    if (!brand || !model || !manufacturingYear) return;

    this.validating = true;
    this.fipeService.getFipeInfo(brand, model, manufacturingYear).subscribe({
      next: (info) => {
        this.form.patchValue({ fipePrice: info.price });
        this.success = `FIPE validada: ${info.price}`;
        this.error = null
      },
      error: () => {
        this.error = 'Erro ao validar FIPE. Fipe não encontrada.';
        this.validating = false;
        this.success = null;
      },
      complete: () => (this.validating = false),
    });
  }

  cancel() {
    this.router.navigate(['/trucks']);
  }
}

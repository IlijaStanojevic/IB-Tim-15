import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateCertComponent } from './generate-cert.component';

describe('GenerateCertComponent', () => {
  let component: GenerateCertComponent;
  let fixture: ComponentFixture<GenerateCertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GenerateCertComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GenerateCertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

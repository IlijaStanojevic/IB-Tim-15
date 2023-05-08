import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllCertsComponent } from './all-certs.component';

describe('AllCertsComponent', () => {
  let component: AllCertsComponent;
  let fixture: ComponentFixture<AllCertsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllCertsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllCertsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

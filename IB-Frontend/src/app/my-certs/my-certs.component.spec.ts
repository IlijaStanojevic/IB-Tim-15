import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyCertsComponent } from './my-certs.component';

describe('MyCertsComponent', () => {
  let component: MyCertsComponent;
  let fixture: ComponentFixture<MyCertsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyCertsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyCertsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
